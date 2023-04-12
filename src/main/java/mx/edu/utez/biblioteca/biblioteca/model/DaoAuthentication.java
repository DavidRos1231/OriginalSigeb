package mx.edu.utez.biblioteca.biblioteca.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import mx.edu.utez.biblioteca.biblioteca.utils.Conn;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DaoAuthentication {

    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

    public BeanAuthentication login(String id,String password) {
        List<Bson> pipeline;
        try (MongoClient mongoClient = Conn.getConnection()) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            pipeline = Arrays.asList(
                    Aggregates.lookup("prestamos_libros", "_id", "iduser", "prestamos"),
                    Aggregates.addFields(
                            new Field<>("noprestamos",
                                    new Document("$size",
                                            new Document("$filter",
                                                    new Document("input", "$prestamos")
                                                            .append("as", "prestamo")
                                                            .append("cond", new Document("$and",
                                                                    Arrays.asList(
                                                                            new Document("$ne", Arrays.asList("$$prestamo.status", 2)),
                                                                            new Document("$ne", Arrays.asList("$$prestamo.status", 3))
                                                                    )
                                                            ))
                                            )
                                    )
                            ),
                            new Field<>("disponibilidad",
                                    new Document("$divide",
                                            Arrays.asList(
                                                    new Document("$subtract",
                                                            Arrays.asList(new Date(), "$tiempo")
                                                    ),
                                                    60000
                                            )
                                    )
                            )
                    ),
                    Aggregates.project(
                            new Document("_id", 0)
                                    .append("id", "$_id")
                                    .append("password", "$password")
                                    .append("tipo", "$tipo")
                                    .append("noprestamos", "$noprestamos")
                                    .append("disponibilidad", "$disponibilidad")
                                    .append("enable", "$enable")
                    )
            );
            List<Document> results = usuarios.aggregate(pipeline).into(new ArrayList<>());
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i).toString());
                if(results.get(i).get("id").equals(id) && results.get(i).get("password").equals(password) && (int)results.get(i).get("enable")==1){
                    BeanAuthentication userAuthentication = new BeanAuthentication();
                    userAuthentication.setNickname(results.get(i).get("id").toString());
                    userAuthentication.setRol((int)results.get(i).get("tipo"));
                    userAuthentication.setNoprestamos((int)results.get(i).get("noprestamos"));
                    userAuthentication.setSalaprestada((double)results.get(i).get("disponibilidad")>=0);
                    return userAuthentication;
                }
            }
        }



            return new BeanAuthentication();
    }

}

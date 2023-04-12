package mx.edu.utez.biblioteca.biblioteca.model;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import mx.edu.utez.biblioteca.biblioteca.utils.Conn;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.*;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DaoSala {
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));



    public List<BeanSala> listSalas(){
        List<BeanSala> listSalas=new ArrayList<>();
        List<Bson> pipeline ;
        try(MongoClient mongoClient= Conn.getConnection() ){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> salas= database.getCollection("salas");
            pipeline= Arrays.asList(
                    Aggregates.project(
                            new Document("_id",0)
                                    .append("id","$_id")
                                    .append("lugares","$lugares")
                                    .append("disponibilidad",new Document("$divide",Arrays.asList(new Document("$subtract",Arrays.asList(new Date(),"$hora")),60000)))
                                    .append("descripcion","$descripcion")
                                    .append("enable","$enable")
                    )

            );
            List<Document> result = salas.aggregate(pipeline).into(new ArrayList<>());
            for (Document document:result){
                BeanSala sala = new BeanSala();
                sala.setId(document.getInteger("id"));
                sala.setLugares(document.getInteger("lugares"));
                sala.setDisponibilidad(document.getDouble("disponibilidad")!=null?document.getDouble("disponibilidad")>=0.0:true);
                sala.setDescripcion(document.getString("descripcion"));
                listSalas.add(sala);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listSalas;
    }

    public boolean saveSala(BeanSala sala){
        boolean result=false;
        int id=0;
        try(MongoClient mongoClient= Conn.getConnection() ){
            List<BeanSala> listSalas=listSalas();
            for (BeanSala beanSala:listSalas){
                if (beanSala.getId()>id){
                    id= (int) beanSala.getId();
                }
            }
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> salas= database.getCollection("salas");
            Document document = new Document();
            document.append("_id",id+1);
            document.append("lugares",(int)sala.getLugares());
            document.append("descripcion",sala.getDescripcion());
            document.append("enable",1);
            salas.insertOne(document);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public boolean pedirSala(int tiempo,int idsala,String idusuario){
        boolean result =false;
        boolean prestada=false;
        List<Bson> pipeline;
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> salas= database.getCollection("salas");
            MongoCollection<Document> usuarios= database.getCollection("usuarios");
            MongoCollection<Document> prestamos= database.getCollection("prestamos");
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
                    ),
                    Aggregates.match(Filters.eq("id", idusuario)

            ));
            List<Document> results = usuarios.aggregate(pipeline).into(new ArrayList<>());
            for (Document document:results){
                prestada = (document.getDouble("disponibilidad")>=0.0);
            }
            if (prestada){
                pipeline= Arrays.asList(
                        Aggregates.project(
                                new Document("_id",0)
                                        .append("id","$_id")
                                        .append("lugares","$lugares")
                                        .append("disponibilidad",new Document("$divide",Arrays.asList(new Document("$subtract",Arrays.asList(new Date(),"$hora")),60000)))
                                        .append("descripcion","$descripcion")
                                        .append("enable","$enable")
                        ),
                        Aggregates.match(Filters.eq("id",idsala))
                );
                results = salas.aggregate(pipeline).into(new ArrayList<>());
                for (Document document:results){
                    prestada = (document.getDouble("disponibilidad")>=0.0);
                }
                Date fecha = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(fecha);
                calendar.add(Calendar.MINUTE, tiempo*30);
                Date fechafinal=calendar.getTime();
                if (prestada){
                    Document document = new Document();
                    document.append("tiempo",fechafinal);
                    usuarios.updateOne(Filters.eq("_id",idusuario),new Document("$set",document));

                    Document document1 = new Document();
                    document1.append("user",idusuario);
                    document1.append("hora",fechafinal);
                    salas.updateOne(Filters.eq("_id",idsala),new Document("$set",document1));
                    result=true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }


}

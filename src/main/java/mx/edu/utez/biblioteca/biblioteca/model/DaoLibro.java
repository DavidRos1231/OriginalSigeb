package mx.edu.utez.biblioteca.biblioteca.model;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.*;
import com.mongodb.client.model.Sorts;
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

public class DaoLibro {
    CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
    CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));


                                //L     I   BBBB    RRRR     OOO     SSSS
                                //L     I   B   B   R   R   O   O   S
                                //L     I   BBBB    RRRR    O   O    SSS
                                //L     I   B   B   R R     O   O       S
                                //LLLLL I   BBBB    R  R     Ooo    SSSS

    //pasar list<document> a list<beanlibro>
    public List<BeanLibro> parseListLibros(List<Document> listDocument){
        List<BeanLibro> listLibros=new ArrayList<>();
        for (Document document : listDocument) {
            if((int)document.get("enable")==0){
                continue;
            }
            BeanLibro beanLibro=new BeanLibro();
            beanLibro.setId((long)((int)document.get("id")));
            beanLibro.setName(document.getString("nombre"));
            beanLibro.setAutor(document.getString("autor"));
            beanLibro.setStock(document.getInteger("stock"));
            beanLibro.setCategoria(document.getString("categoria"));
            listLibros.add(beanLibro);
        }
        return listLibros;
    }

    //pasar list<document> a list<BeanEjemplar>
    public List<BeanEjemplar> parseListEjemplares(List<Document> listDocument){
        List<BeanEjemplar> listEjemplares=new ArrayList<>();
        for (Document document : listDocument) {
            BeanEjemplar beanEjemplar=new BeanEjemplar();
            beanEjemplar.setId(((int)document.get("id")));
            beanEjemplar.setName(document.getString("nombre"));
            beanEjemplar.setEstado(document.getInteger("estado")==1);
            beanEjemplar.setIdlibro(((int)document.get("idlibro")));
            beanEjemplar.setDescripcion(document.getString("descripcion"));
            beanEjemplar.setEditorial(document.getString("editorial"));
            listEjemplares.add(beanEjemplar);
        }
        return listEjemplares;
    }


    public List<BeanLibro> listLibros(){
        List<BeanLibro> listLibros=new ArrayList<>();
        List<Bson> pipeline;
        try(MongoClient mongoClient = Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> libros = database.getCollection("libros");
            pipeline = Arrays.asList(
                    Aggregates.lookup("ejemplar", "_id", "idlibro", "ejemplares"),
                    Aggregates.addFields(
                            new Field<>("stock",
                                    new Document("$size",
                                            new Document("$filter",
                                                    new Document("input", "$ejemplares")
                                                            .append("as", "ejemplar")
                                                            .append("cond", new Document("$eq",
                                                                    Arrays.asList("$$ejemplar.estado", 1)
                                                            ))
                                            )
                                    )
                            )
                    ),
                    Aggregates.project(
                            new Document("_id", 0)
                                    .append("id", "$_id")
                                    .append("nombre", "$nombre")
                                    .append("autor", "$autor")
                                    .append("categoria", "$categoria")
                                    .append("enable", "$enable")
                                    .append("stock", "$stock")
                    ),
                    Aggregates.sort(Sorts.ascending("nombre"))
            );

            List<Document> results = libros.aggregate(pipeline).into(new ArrayList<>());
            listLibros=parseListLibros(results);
        }
        return listLibros;
    }

    public BeanLibro editarLibro(int id){
        try (MongoClient mongoClient = Conn.getConnection();) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> collection = database.getCollection("libros");
            MongoCursor<Document> cursor = collection.find().iterator();
            while (cursor.hasNext()) {
                Document iter = cursor.next();
                System.out.println(iter.toString());
                if ((int)iter.get("_id") == id) {
                    System.out.println(iter);
                    BeanLibro libroObject = new BeanLibro();
                    libroObject.setId((long)((int)iter.get("_id")));
                    libroObject.setName(iter.getString("nombre"));
                    libroObject.setAutor(iter.getString("autor"));
                    libroObject.setCategoria(iter.getString("categoria"));
                    return libroObject;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
    public int saveLibro(BeanLibro libro){
        int id=0;
        List<BeanLibro> listLibros=listLibros();
        //found the greater id
        for (BeanLibro beanLibro : listLibros) {
            if(beanLibro.getId()>id){
                id=(int)beanLibro.getId();
            }
        }
        id++;
        try (MongoClient mongoClient = Conn.getConnection();) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> collection = database.getCollection("libros");
            Document document = new Document();
            document.append("_id", id);
            document.append("nombre", libro.getName());
            document.append("autor", libro.getAutor());
            document.append("categoria", libro.getCategoria());
            document.append("enable", 1);
            collection.insertOne(document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public boolean updateLibro(BeanLibro libro){
        boolean result=false;
        try (MongoClient mongoClient = Conn.getConnection();) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> collection = database.getCollection("libros");
            Document document = new Document();
            document.append("nombre", libro.getName());
            document.append("autor", libro.getAutor());
            document.append("categoria", libro.getCategoria());
            Document update = new Document();
            update.append("$set", document);
            collection.updateOne(Filters.eq("_id", libro.getId()), update);
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public boolean eliminarLibro(int id){
        boolean result=false;
        try (MongoClient mongoClient = Conn.getConnection();) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> collection = database.getCollection("libros");
            Document document = new Document();
            document.append("enable", 0);
            Document update = new Document();
            update.append("$set", document);
            collection.updateOne(Filters.eq("_id", id), update);
            result=true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    public List<BeanEjemplar> listEjemplar(int id){
        List<BeanEjemplar> listEjemplar=new ArrayList<>();
        try(MongoClient mongoClient= Conn.getConnection();){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> ejemplar = database.getCollection("ejemplar");
            List<Bson> pipeline = Arrays.asList(
                    Aggregates.lookup("libros", "idlibro", "_id", "libro"),
                    Aggregates.project(
                            new Document("_id", 0)
                                    .append("id", "$_id")
                                    .append("idlibro", "$idlibro")
                                    .append("nombre", new Document("$arrayElemAt", Arrays.asList("$libro.nombre", 0)))
                                    .append("estado", "$estado")
                                    .append("descripcion", "$descripcion")
                                    .append("enable", "$enable")
                                    .append("editorial", "$editorial")
                    )
            );
            List<Document> results = ejemplar.aggregate(pipeline).into(new ArrayList<>());
            List<BeanEjemplar> resultados =parseListEjemplares(results);
            for (BeanEjemplar beanEjemplar : resultados) {
                if(beanEjemplar.getIdlibro()==id){
                    listEjemplar.add(beanEjemplar);
                }
            }
        }
        return listEjemplar;
    }


    //U   U  SSSS   U   U    AAA    RRRR    I    OOO     SSSS
    //U   U S       U   U   A   A   R   R   I   O   O   S
    //U   U  SSS    U   U   AAAAA   RRRR    I   O   O    SSS
    //U   U     S   U   U   A   A   R R     I   O   O       S
    // UUU  SSSS     UUU    A   A   R  R    I    OOO    SSSS
    public MongoCursor cursorUsuarios(){
        MongoCursor cursor=null;
        try(MongoClient mongoClient= Conn.getConnection();){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            //cursor
            cursor=usuarios.find().iterator();
        }
        return cursor;
    }

    public BeanUsuario editarUsuario(String id){
        BeanUsuario usuario=new BeanUsuario();
        try{
            //cursor
            MongoCursor cursor=cursorUsuarios();
            while(cursor.hasNext()){
                Document doc=(Document)cursor.next();
                if(doc.getString("_id").equals(id)){
                    usuario.setId(doc.getString("_id"));
                    usuario.setName(doc.getString("name"));
                    usuario.setMidname(doc.getString("midname"));
                    usuario.setLastname(doc.getString("lastname"));
                    usuario.setCorreo(doc.getString("correo"));
                    usuario.setPassword(doc.getString("password"));
                    usuario.setTipo(doc.getInteger("tipo"));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }


    public List<BeanUsuario> listUsuarios(String id){
        MongoCursor cursor=cursorUsuarios();
        List<BeanUsuario> listUsuarios=new ArrayList<>();
        if(id.equals("")){
            while(cursor.hasNext()){
                Document doc=(Document)cursor.next();
                if(doc.getInteger("enable")==1){
                    BeanUsuario usuario=new BeanUsuario();
                    usuario.setId(doc.getString("_id"));
                    usuario.setName(doc.getString("name"));
                    usuario.setMidname(doc.getString("midname"));
                    usuario.setLastname(doc.getString("lastname"));
                    usuario.setCorreo(doc.getString("correo"));
                    usuario.setPassword(doc.getString("password"));
                    usuario.setTipo(doc.getInteger("tipo"));
                    listUsuarios.add(usuario);
                }
            }
        }else{
            BeanUsuario usuario=editarUsuario(id);
            listUsuarios.add(usuario);
        }
        for (BeanUsuario beanUsuario : listUsuarios) {
            System.out.println(beanUsuario.toString());
        }
    return listUsuarios;
    }

    public boolean saveUsuario(BeanUsuario usuario){
        Boolean result=false;
        try(MongoClient mongoClient= Conn.getConnection();){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            Document document = new Document();
            document.append("_id", usuario.getId());
            document.append("name", usuario.getName());
            document.append("midname", usuario.getMidname());
            document.append("lastname", usuario.getLastname());
            document.append("correo", usuario.getCorreo());
            document.append("password", usuario.getPassword());
            document.append("tipo", usuario.getTipo());
            document.append("enable", 1);
            document.append("tiempo", new Date());
            usuarios.insertOne(document);
            result=true;
            }catch (Exception e) { e.printStackTrace(); }
            return result;
    }

    public boolean actualizarUsuario(BeanUsuario usuario){
        Boolean result=false;
        try(MongoClient mongoClient= Conn.getConnection();){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            Document document = new Document();
            document.append("_id", usuario.getId());
            document.append("name", usuario.getName());
            document.append("midname", usuario.getMidname());
            document.append("lastname", usuario.getLastname());
            document.append("correo", usuario.getCorreo());
            document.append("password", usuario.getPassword());
            document.append("tipo", usuario.getTipo());
            usuarios.updateOne(Filters.eq("_id", usuario.getId()), new Document("$set", document));
            result=true;
            }catch (Exception e) { e.printStackTrace(); }
            return result;
    }



    public boolean eliminarUsuario(String id) {
        boolean result = false;
        try (MongoClient mongoClient = Conn.getConnection();) {
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            usuarios.updateOne(Filters.eq("_id", id), new Document("$set", new Document("enable", 0)));
            result = true;
        }catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public boolean solicitarEjemplar(int id,String userid){
        boolean result=false;
        boolean pedido=false;
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> prestamosLibros = database.getCollection("prestamos_libros");
            List<Bson> pipeline = Arrays.asList(
                    Aggregates.lookup("ejemplar", "idejemplar", "_id", "ejemplar"),
                    Aggregates.unwind("$ejemplar"),
                    Aggregates.lookup("libros", "ejemplar.idlibro", "_id", "libro"),
                    Aggregates.unwind("$libro"),
                    Aggregates.project(
                            new Document("_id",0)
                                    .append("id", "$_id")
                                    .append("idejemplar", "$idejemplar")
                                    .append("estado", "$ejemplar.estado")
                                    .append("nomejemplar", "$libro.nombre")
                                    .append("iduser", "$iduser")
                                    .append("fechainicial", "$fechainicial")
                                    .append("fechafinal", "$fechafinal")
                                    .append("dias",
                                            new Document("$divide",
                                                    Arrays.asList(
                                                            new Document("$subtract",
                                                                    Arrays.asList(
                                                                            new Date(),
                                                                            "$fechainicial"
                                                                    )
                                                            ),
                                                            86400000
                                                    )
                                            )
                                    )
                                    .append("status", "$status")
                                    .append("descripcion", "$ejemplar.descripcion")
                    )
            );
            List<Document> results = prestamosLibros.aggregate(pipeline).into(new ArrayList<>());
            System.out.println("Hola"+results.size());
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getInteger("idejemplar")==id && results.get(i).getString("iduser").equals(userid)) {
                    pedido=true;
                }
            }
            //encontrar el id mas alto en results
            int idmax=0;
            for (int i = 0; i < results.size(); i++) {
                if (results.get(i).getInteger("id")>idmax) {
                    idmax=results.get(i).getInteger("id");
                }
            }
            if(!pedido){
                Document document = new Document();
                document.append("_id", idmax+1);
                document.append("idejemplar", id);
                document.append("iduser", userid);
                document.append("status", 0);
                prestamosLibros.insertOne(document);
                return true;
            }

        }catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public List<BeanPrestamoLibro> listarPrestamos(String userid){
        List<BeanPrestamoLibro> listPrestamos = new ArrayList<>();
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> prestamosLibros = database.getCollection("prestamos_libros");
            List<Bson> pipeline = Arrays.asList(
                    Aggregates.lookup("ejemplar", "idejemplar", "_id", "ejemplar"),
                    Aggregates.unwind("$ejemplar"),
                    Aggregates.lookup("libros", "ejemplar.idlibro", "_id", "libro"),
                    Aggregates.unwind("$libro"),
                    Aggregates.project(
                            new Document("_id",0)
                                    .append("id", "$_id")
                                    .append("idejemplar", "$idejemplar")
                                    .append("estado", "$ejemplar.estado")
                                    .append("nomejemplar", "$libro.nombre")
                                    .append("iduser", "$iduser")
                                    .append("fechainicial", "$fechainicial")
                                    .append("fechafinal", "$fechafinal")
                                    .append("dias",
                                            new Document("$divide",
                                                    Arrays.asList(
                                                            new Document("$subtract",
                                                                    Arrays.asList(
                                                                            new Date(),
                                                                            "$fechainicial"
                                                                    )
                                                            ),
                                                            86400000
                                                    )
                                            )
                                    )
                                    .append("status", "$status")
                                    .append("descripcion", "$ejemplar.descripcion")
                    )
            );
            List<Document> results = prestamosLibros.aggregate(pipeline).into(new ArrayList<>());
            if(userid.equals("")){
                for (int i = 0; i < results.size(); i++) {
                    BeanPrestamoLibro prestamoLibro = new BeanPrestamoLibro();
                    prestamoLibro.setId(results.get(i).getInteger("id"));
                    prestamoLibro.setIdejemplar(results.get(i).getInteger("idejemplar"));
                    prestamoLibro.setNomejemplar(results.get(i).getString("nomejemplar"));
                    prestamoLibro.setUserid(results.get(i).getString("iduser"));
                    prestamoLibro.setFechainicial(results.get(i).getDate("fechainicial")!=null?results.get(i).getDate("fechainicial").toString():"");
                    prestamoLibro.setFechafinal(results.get(i).getDate("fechafinal")!=null?results.get(i).getDate("fechafinal").toString():"");
                    prestamoLibro.setDias(results.get(i).getDouble("dias")!=null?(int)results.get(i).getDouble("dias").doubleValue():0);
                    prestamoLibro.setStatus(results.get(i).getInteger("status"));
                    prestamoLibro.setDescripcion(results.get(i).getString("descripcion"));
                    listPrestamos.add(prestamoLibro);
                }
            }else {
                for (int i = 0; i < results.size(); i++) {
                    if(results.get(i).getString("iduser").equals(userid)){
                        BeanPrestamoLibro prestamoLibro = new BeanPrestamoLibro();
                        prestamoLibro.setId(results.get(i).getInteger("id"));
                        prestamoLibro.setIdejemplar(results.get(i).getInteger("idejemplar"));
                        prestamoLibro.setNomejemplar(results.get(i).getString("nomejemplar"));
                        prestamoLibro.setUserid(results.get(i).getString("iduser"));
                        prestamoLibro.setFechainicial(results.get(i).getDate("fechainicial")!=null?results.get(i).getDate("fechainicial").toString():"");
                        prestamoLibro.setFechafinal(results.get(i).getDate("fechafinal")!=null?results.get(i).getDate("fechafinal").toString():"");
                        prestamoLibro.setDias(results.get(i).getDouble("dias")!=null?(int)results.get(i).getDouble("dias").doubleValue():0);
                        prestamoLibro.setStatus(results.get(i).getInteger("status"));
                        prestamoLibro.setDescripcion(results.get(i).getString("descripcion"));
                        prestamoLibro.setintstatus(results.get(i).getInteger("estado"));
                        listPrestamos.add(prestamoLibro);
                    }
            }
        }

    }catch (Exception e) { e.printStackTrace(); }
        return listPrestamos;
    }


    public List<BeanPrestamoLibro> listarPrestamosadmin(int stado){
        List<BeanPrestamoLibro> listPrestamos = new ArrayList<>();
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> prestamosLibros = database.getCollection("prestamos_libros");
            List<Bson> pipeline = Arrays.asList(
                    Aggregates.lookup("ejemplar", "idejemplar", "_id", "ejemplar"),
                    Aggregates.unwind("$ejemplar"),
                    Aggregates.lookup("libros", "ejemplar.idlibro", "_id", "libro"),
                    Aggregates.unwind("$libro"),
                    Aggregates.project(
                            new Document("_id",0)
                                    .append("id", "$_id")
                                    .append("idejemplar", "$idejemplar")
                                    .append("estado", "$ejemplar.estado")
                                    .append("nomejemplar", "$libro.nombre")
                                    .append("iduser", "$iduser")
                                    .append("fechainicial", "$fechainicial")
                                    .append("fechafinal", "$fechafinal")
                                    .append("dias",
                                            new Document("$divide",
                                                    Arrays.asList(
                                                            new Document("$subtract",
                                                                    Arrays.asList(
                                                                            new Date(),
                                                                            "$fechainicial"
                                                                    )
                                                            ),
                                                            86400000
                                                    )
                                            )
                                    )
                                    .append("status", "$status")
                                    .append("descripcion", "$ejemplar.descripcion")
                    )
            );
            List<Document> results = prestamosLibros.aggregate(pipeline).into(new ArrayList<>());
                for (int i = 0; i < results.size(); i++) {
                    if(results.get(i).getInteger("status")==stado){
                        BeanPrestamoLibro prestamoLibro = new BeanPrestamoLibro();
                        prestamoLibro.setId(results.get(i).getInteger("id"));
                        prestamoLibro.setIdejemplar(results.get(i).getInteger("idejemplar"));
                        prestamoLibro.setNomejemplar(results.get(i).getString("nomejemplar"));
                        prestamoLibro.setUserid(results.get(i).getString("iduser"));
                        prestamoLibro.setFechainicial(results.get(i).getDate("fechainicial")!=null?results.get(i).getDate("fechainicial").toString():"");
                        prestamoLibro.setFechafinal(results.get(i).getDate("fechafinal")!=null?results.get(i).getDate("fechafinal").toString():"");
                        prestamoLibro.setDias(results.get(i).getDouble("dias")!=null?(int)results.get(i).getDouble("dias").doubleValue():0);
                        prestamoLibro.setStatus(results.get(i).getInteger("status"));
                        prestamoLibro.setDescripcion(results.get(i).getString("descripcion"));
                        prestamoLibro.setintstatus(results.get(i).getInteger("estado"));
                        listPrestamos.add(prestamoLibro);
                    }
                }
        }catch (Exception e) { e.printStackTrace(); }
        return listPrestamos;
    }

    public boolean aceptarPrestamo(int idpedido, int idejemplar,String idusuario){
        boolean result=false;
        int tipo=-1;
        try (MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> usuarios = database.getCollection("usuarios");
            List<Document> results = usuarios.find().into(new ArrayList<>());
            for (int i = 0; i < results.size(); i++) {
                System.out.println(results.get(i).toString());
                if(results.get(i).getString("_id").equals(idusuario)){
                    tipo=results.get(i).getInteger("tipo");
                }
            }
            if (tipo==-1){return false;}
            MongoCollection<Document> ejemplares = database.getCollection("ejemplar");
            ejemplares.updateOne(Filters.eq("_id", idejemplar), new Document("$set", new Document("estado", 0)));
            MongoCollection<Document> prestamosLibros = database.getCollection("prestamos_libros");
            //feecha actual mas 5 dias
            Date fechainicial = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(fechainicial);
            c.add(Calendar.DATE, (5+(5*tipo)));
            Date fechafinal = c.getTime();
            prestamosLibros.updateOne(Filters.eq("_id", idpedido), new Document("$set", new Document("fechainicial", fechainicial).append("fechafinal", fechafinal).append("status", 1)));
            result=true;
        }catch (Exception e) { e.printStackTrace(); }

        return result;
    }

    public boolean rechazarPrestamo(int idpedido){
        boolean result=false;
        try (MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> prestamosLibros = database.getCollection("prestamos_libros");
            prestamosLibros.updateOne(Filters.eq("_id", idpedido), new Document("$set", new Document("status", 2)));
            result=true;
        }catch (Exception e) { e.printStackTrace(); }
        return result;
    }

    public boolean regresarPrestamo(int idpedido,int idejemplar, String descripcion){
        boolean result=false;
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database = mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> ejemplar = database.getCollection("ejemplar");
            MongoCollection<Document> prestamosLibros=database.getCollection("prestamos_libros");
            ejemplar.updateOne(Filters.eq("_id",idejemplar),new Document("$set",new Document("estado",1).append("descripcion",descripcion)));
            prestamosLibros.updateOne(Filters.eq("_id",idpedido), new Document("$set",new Document("fechafinal", new Date()).append("status",3)));
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean guardarEjemplar(BeanEjemplar ejemplar,int cantidad){
        System.out.println("guardarEjemplar"+ejemplar.toString()+cantidad);

        boolean result=false;
        try(MongoClient mongoClient=Conn.getConnection()){
            MongoDatabase database=mongoClient.getDatabase("Tsigeb").withCodecRegistry(pojoCodecRegistry);
            MongoCollection<Document> ejemplarCollection=database.getCollection("ejemplar");
            List<Document> ejemplares=ejemplarCollection.find().into(new ArrayList<>());
            int id=0;
            for (int i = 0; i < ejemplares.size(); i++) {
                if(ejemplares.get(i).getInteger("_id")>id){
                    id=ejemplares.get(i).getInteger("_id");
                }
            }
            for (int i = 0; i < cantidad; i++) {
                id++;
                Document ejemplar1=new Document();
                ejemplar1.append("_id",id)
                        .append("idlibro",ejemplar.getIdlibro())
                        .append("estado",1)
                        .append("descripcion",ejemplar.getDescripcion())
                        .append("editorial",ejemplar.getEditorial())
                        .append("enable",1);
                ejemplarCollection.insertOne(ejemplar1);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}

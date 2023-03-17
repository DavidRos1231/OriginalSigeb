package mx.edu.utez.biblioteca.biblioteca.model;

import mx.edu.utez.biblioteca.biblioteca.utils.MySQLConnection;
import org.w3c.dom.ls.LSOutput;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoLibro {

                                //L     I   BBBB    RRRR     OOO     SSSS
                                //L     I   B   B   R   R   O   O   S
                                //L     I   BBBB    RRRR    O   O    SSS
                                //L     I   B   B   R R     O   O       S
                                //LLLLL I   BBBB    R  R     Ooo    SSSS
    public List<BeanLibro> listLibros(){
        List<BeanLibro> listLibros=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery("SELECT * FROM biblioteca.libro_view where enable=1 order by nombre;");
            while (rs.next()) {
                BeanLibro libro = new BeanLibro();
                libro.setId(rs.getInt("id"));
                libro.setName(rs.getString("nombre"));
                libro.setStock(rs.getInt("stock"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                listLibros.add(libro);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listLibros;
    }
    public BeanLibro editarLibro(int id){
        BeanLibro libro=new BeanLibro();
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("SELECT * FROM biblioteca.libros where id=?;")
        ){
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                libro.setId(rs.getInt("id"));
                libro.setName(rs.getString("nombre"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return libro;
    }


    public List<BeanLibro> listLibros(String nombre,String autor,String categoria){
        List<BeanLibro> listLibros=new ArrayList<>();
        try(Connection connection= MySQLConnection.getConnection();
            PreparedStatement pstm=connection.prepareStatement("SELECT * FROM biblioteca.libro_view where enable=1 and nombre like ? and autor like ? and categoria like ? order by nombre;");
        ){
            nombre="%"+nombre+"%";
            autor="%"+autor+"%";
            categoria="%"+categoria+"%";
            pstm.setString(1,nombre);
            pstm.setString(2,autor);
            pstm.setString(3,categoria);
            ResultSet rs= pstm.executeQuery();
            while (rs.next()) {
                BeanLibro libro = new BeanLibro();
                libro.setId(rs.getInt("id"));
                libro.setName(rs.getString("nombre"));
                libro.setStock(rs.getInt("stock"));
                libro.setAutor(rs.getString("autor"));
                libro.setCategoria(rs.getString("categoria"));
                listLibros.add(libro);
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listLibros;
    }

    public int saveLibro(BeanLibro libro){
        int idGenerated=0;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm= con.prepareStatement(
                        "insert into libros(nombre,autor,categoria,file_name)values(?,?,?,?);",
                Statement.RETURN_GENERATED_KEYS)
        ){
            pstm.setString(1,libro.getName());
            pstm.setString(2,libro.getAutor());
            pstm.setString(3,libro.getCategoria());
            pstm.setString(4,libro.getFile_name());
            if(pstm.executeUpdate()==1){
                try(ResultSet keys=pstm.getGeneratedKeys()){
                    idGenerated=keys.next()?keys.getInt(1):0;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return idGenerated;
    }
    public boolean updateLibro(BeanLibro libro){
        boolean result=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`libros` SET `nombre` = ?, `autor` = ?, `categoria` = ? WHERE (`id` = ?);");
        ){
            pstm.setString(1,libro.getName());
            pstm.setString(2,libro.getAutor());
            pstm.setString(3,libro.getCategoria());
            pstm.setLong(4,libro.getId());
            result = pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public  boolean eliminarLibro(int id){
        boolean result=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm=con.prepareStatement("UPDATE `biblioteca`.`libros` SET `enable` = '0' WHERE (`id` = ?);")
        ){
            pstm.setInt(1,id);
            result=pstm.executeUpdate()==1;

        }catch (Exception e){e.printStackTrace();}
        return result;
    }
    public List<BeanEjemplar> listEjemplar(int id){
        List<BeanEjemplar> listEjemplar=new ArrayList<>();
        try (
            Connection connection= MySQLConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM biblioteca.list_ejemplar_view where idlibro=?;");
                ){
            pstm.setInt(1,id);
            ResultSet rs =pstm.executeQuery();
            while (rs.next()) {
                BeanEjemplar ejemplar = new BeanEjemplar();
                ejemplar.setId(rs.getInt("id"));
                ejemplar.setIdlibro(rs.getInt("idlibro"));
                ejemplar.setName(rs.getString("nombre"));
                ejemplar.setEstado(rs.getBoolean("estado"));
                ejemplar.setDescripcion(rs.getString("descripcion"));
                ejemplar.setEditorial(rs.getString("editorial"));
                listEjemplar.add(ejemplar);
            }
            rs.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listEjemplar;
    }


    //U   U  SSSS   U   U    AAA    RRRR    I    OOO     SSSS
    //U   U S       U   U   A   A   R   R   I   O   O   S
    //U   U  SSS    U   U   AAAAA   RRRR    I   O   O    SSS
    //U   U     S   U   U   A   A   R R     I   O   O       S
    // UUU  SSSS     UUU    A   A   R  R    I    OOO    SSSS

    public BeanUsuario editarUsuario(String id){
        BeanUsuario usuario=new BeanUsuario();
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm = connection.prepareStatement("SELECT * FROM biblioteca.usuarios where id=?;")
                ){
            pstm.setString(1,id);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                usuario.setId(rs.getString("id"));
                usuario.setName(rs.getString("name"));
                usuario.setMidname(rs.getString("midname"));
                usuario.setLastname(rs.getString("lastname"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setPassword(rs.getString("password"));
                usuario.setTipo(rs.getInt("tipo"));
            }
            rs.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return usuario;
    }
    public List<BeanUsuario> listUsuarios(String mat){
        List<BeanUsuario> listUsuarios=new ArrayList<>();
        mat="%"+mat+"%";
        try{
            Connection connection=MySQLConnection.getConnection();
            Statement statement =connection.createStatement();
            PreparedStatement pstm =connection.prepareStatement("SELECT * FROM biblioteca.usuarios where tipo!=2 and enable=1 and id like ?;");
            pstm.setString(1,mat);
            ResultSet rs= pstm.executeQuery();
            while (rs.next()){
                BeanUsuario usuario=new BeanUsuario();
                usuario.setId(rs.getString("id"));
                usuario.setName(rs.getString("name"));
                usuario.setMidname(rs.getString("midname"));
                usuario.setLastname(rs.getString("lastname"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTipo(rs.getInt("tipo"));
                listUsuarios.add(usuario);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    return listUsuarios;
    }

    //Editorial
    public List<BeanEditorial> listEditorial(){
        List<BeanEditorial> listEditorial=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery("SELECT * FROM biblioteca.editorial;");
            while (rs.next()) {
                BeanEditorial editorial = new BeanEditorial();
                editorial.setId(rs.getInt("id"));
                editorial.setName(rs.getString("nombre"));

                listEditorial.add(editorial);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    return listEditorial;
    }
    //Autores
    public List<BeanAutor> listAutores(){
        List<BeanAutor> listAutor=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery("SELECT * FROM biblioteca.autor_view;");
            while (rs.next()) {
                BeanAutor autor = new BeanAutor();
                autor.setId(rs.getInt("id"));
                autor.setName(rs.getString("nombre"));
                listAutor.add(autor);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    return listAutor;
    }
    //Categoria
    public List<BeanCategoria> listCategorias(){
        List<BeanCategoria> listCategoria=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery("SELECT * FROM biblioteca.categoria;");
            while (rs.next()) {
                BeanCategoria categoria = new BeanCategoria();
                categoria.setId(rs.getInt("id"));
                categoria.setName(rs.getString("nombre"));

                listCategoria.add(categoria);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    return listCategoria;
    }

    public boolean saveUsuario(BeanUsuario usuario){
        boolean result=false;
        try(
            Connection con=MySQLConnection.getConnection();
            PreparedStatement pstm= con.prepareStatement("INSERT INTO `biblioteca`.`usuarios` (`id`, `name`, `midname`, `lastname`, `correo`, `password`, `tipo`) VALUES (?, ?, ?, ?, ?, ?, ?);");
            ){
            pstm.setString(1,usuario.getId());
            pstm.setString(2,usuario.getName());
            pstm.setString(3,usuario.getMidname());
            pstm.setString(4,usuario.getLastname());
            pstm.setString(5,usuario.getCorreo());
            pstm.setString(6,usuario.getPassword());
            pstm.setInt(7,usuario.getTipo());
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean actualizarUsuario(BeanUsuario usuario){
        boolean result=false;
        try(
            Connection con=MySQLConnection.getConnection();
            PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`usuarios` SET `name` = ?, `midname` = ?, `lastname` = ?, `correo` = ?, `password` = ? WHERE (`id` = ?);");
        ){
            pstm.setString(1,usuario.getName());
            pstm.setString(2,usuario.getMidname());
            pstm.setString(3,usuario.getLastname());
            pstm.setString(4,usuario.getCorreo());
            pstm.setString(5,usuario.getPassword());
            pstm.setString(6,usuario.getId());
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean eliminarUsuario(String id){
        boolean result=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm=con.prepareStatement("UPDATE `biblioteca`.`usuarios` SET `enable` = '0' WHERE (`id` = ?);\n")
                ){
            pstm.setString(1,id);
            result=pstm.executeUpdate()==1;

        }catch (Exception e){e.printStackTrace();}
        return result;
    }
    public boolean solicitarEjemplar(int id,String userid){
        boolean result=false;
        boolean pedido=false;
        System.out.println("idejemplar "+id+" iduser "+userid);
        try(
                Connection con=MySQLConnection.getConnection();
                /*PreparedStatement pstm2=con.prepareStatement("")*/
                PreparedStatement pstm2=con.prepareStatement("SELECT * FROM biblioteca.prestamos_vista where idejemplar=? and iduser=?;");

        ){
            pstm2.setInt(1,id);
            pstm2.setString(2,userid);
            ResultSet rs= pstm2.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt("pedidoono"));
                pedido=rs.getInt("pedidoono")>=1;
            }
            System.out.println("Pedido= "+pedido);
            if (!pedido) {
                PreparedStatement pstm = con.prepareStatement("INSERT INTO `biblioteca`.`prestamos_libros` (`idejemplar`, `iduser`) VALUES (?, ?);");
                /*PreparedStatement pstm2= con.prepareStatement("UPDATE `biblioteca`.`ejemplar` SET `estado` = '0' WHERE (`id` = ?);")*/
                pstm.setInt(1, id);
                pstm.setString(2, userid);
                /* pstm2.setInt(1,id);*/
                result = (pstm.executeUpdate() == 1) /*&& (pstm2.executeUpdate()==1)*/;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public List<BeanPrestamoLibro> listarPrestamos(String userid){
        List<BeanPrestamoLibro> listPrestamos=new ArrayList<>();
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm= connection.prepareStatement("SELECT * FROM biblioteca.prestamos_vista where iduser like ?;");

                ){
            userid="%"+userid+"%";
            pstm.setString(1,userid);
            ResultSet rs =pstm.executeQuery();
            while(rs.next()){
                BeanPrestamoLibro prestamoLibro=new BeanPrestamoLibro();
                prestamoLibro.setId(rs.getInt("id"));
                prestamoLibro.setIdejemplar(rs.getInt("idejemplar"));
                prestamoLibro.setNomejemplar(rs.getString("nomejemplar"));
                prestamoLibro.setUserid(rs.getString("iduser"));
                prestamoLibro.setFechainicial(rs.getString("fechainicial"));
                prestamoLibro.setFechafinal(rs.getString("fechafinal"));
                prestamoLibro.setDias(rs.getInt("dias"));
                prestamoLibro.setStatus(rs.getInt("status"));
                prestamoLibro.setDescripcion(rs.getString("descripcion"));
                listPrestamos.add(prestamoLibro);
            }
            rs.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listPrestamos;
    }
    public List<BeanPrestamoLibro> listarPrestamosadmin(int estado){
        List<BeanPrestamoLibro> listPrestamos=new ArrayList<>();
        try(
                Connection connection=MySQLConnection.getConnection();
                PreparedStatement pstm= connection.prepareStatement("SELECT * FROM biblioteca.prestamos_vista where status=?;");

        ){
            pstm.setInt(1,estado);
            ResultSet rs =pstm.executeQuery();
            while(rs.next()){
                BeanPrestamoLibro prestamoLibro=new BeanPrestamoLibro();
                prestamoLibro.setId(rs.getInt("id"));
                prestamoLibro.setIdejemplar(rs.getInt("idejemplar"));
                prestamoLibro.setNomejemplar(rs.getString("nomejemplar"));
                prestamoLibro.setUserid(rs.getString("iduser"));
                prestamoLibro.setFechainicial(rs.getString("fechainicial"));
                prestamoLibro.setFechafinal(rs.getString("fechafinal"));
                prestamoLibro.setDias(rs.getInt("dias"));
                prestamoLibro.setStatus(rs.getInt("status"));
                System.out.println(rs.getInt("estado"));
                prestamoLibro.setintstatus(rs.getInt("estado"));
                listPrestamos.add(prestamoLibro);
            }
            rs.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return listPrestamos;
    }
    public boolean aceptarPrestamo(int idpedido,int idejemplar, String idusuario){
        boolean result=false;
        int tipo=0;
        try(
            Connection con=MySQLConnection.getConnection();
            PreparedStatement pstm3=con.prepareStatement("select tipo from usuarios where id=?;");

        ){
            pstm3.setString(1,idusuario);
            ResultSet rs= pstm3.executeQuery();
            while (rs.next()){
                tipo=rs.getInt("tipo");
            }
            PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`ejemplar` SET `estado` = '0' WHERE (`id` = ?);");
            PreparedStatement pstm2=con.prepareStatement("UPDATE `biblioteca`.`prestamos_libros` SET `fechainicial` = curdate(), `fechafinal` = curdate()+5+(5*?), `status` = '1' WHERE (`id` = ?);");
            pstm.setInt(1,idejemplar);
            pstm2.setInt(1,tipo);
            pstm2.setInt(2,idpedido);
            result=pstm.executeUpdate()==1 && pstm2.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean rechazarPrestamo(int idpedido){
        boolean result=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm2=con.prepareStatement("UPDATE `biblioteca`.`prestamos_libros` SET `status` = '2' WHERE (`id` = ?);")
        ){
            pstm2.setInt(1,idpedido);
            result=pstm2.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean regresarPrestamo(int idpedido,int idejemplar, String descripcion){
        boolean result=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`ejemplar` SET `estado` = 1, `descripcion` = ? WHERE (`id` = ?);");
                PreparedStatement pstm2=con.prepareStatement("UPDATE `biblioteca`.`prestamos_libros` SET  `fechafinal` = curdate(), `status` = '3' WHERE (`id` = ?);")
        ){
            pstm.setString(1,descripcion);
            pstm.setInt(2,idejemplar);
            pstm2.setInt(1,idpedido);
            result=pstm.executeUpdate()==1 && pstm2.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public boolean guardarEjemplar(BeanEjemplar ejemplar,int cantidad){
        boolean result=false;
        for (int i = 0; i < cantidad; i++) {


        try (Connection con=MySQLConnection.getConnection();
             PreparedStatement pstm=con.prepareStatement("INSERT INTO `biblioteca`.`ejemplar` (`idlibro`, `descripcion`, `editorial`) VALUES (?, ?, ?);")
                ){
            pstm.setInt(1,ejemplar.getIdlibro());
            pstm.setString(2,ejemplar.getDescripcion());
            pstm.setString(3,ejemplar.getEditorial());
            result=pstm.executeUpdate()==1;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
        return result;
    }

}

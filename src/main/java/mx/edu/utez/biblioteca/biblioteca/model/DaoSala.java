package mx.edu.utez.biblioteca.biblioteca.model;
import mx.edu.utez.biblioteca.biblioteca.utils.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoSala {
    public List<BeanSala> listSalas(){
        List<BeanSala> listSalas=new ArrayList<>();
        try{
            Connection connection= MySQLConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs =statement.executeQuery("SELECT * FROM biblioteca.salas_view;");
            while (rs.next()) {
                BeanSala sala = new BeanSala();
                sala.setId(rs.getInt("id"));
                sala.setLugares(rs.getInt("lugares"));
                sala.setDisponibilidad(rs.getInt("disponibilidad")>=0);
                sala.setDescripcion(rs.getString("descripcion"));
                listSalas.add(sala);
            }
            rs.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return listSalas;
    }
    public boolean saveSala(BeanSala sala){
    boolean result=false;
    try(
        Connection con=MySQLConnection.getConnection();
        PreparedStatement pstm= con.prepareStatement("insert into salas(lugares,descripcion)values(?,?);");
        ){
        pstm.setLong(1, sala.getLugares() );
        pstm.setString(2, sala.getDescripcion());
        result=pstm.executeUpdate()==1;
    }catch(Exception e)
    {
        e.printStackTrace();
    }
    return result;
    }
    public boolean pedirSala(int tiempo,int idsala,String idusuario){
        boolean result =false;
        boolean prestada=false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm3 = con.prepareStatement("SELECT * FROM biblioteca.id_pass_prestamos_view where id=?;");


        ){
            pstm3.setString(1,idusuario);
            ResultSet rs= pstm3.executeQuery();;
            while(rs.next()){
                 prestada =(rs.getInt("disponibilidad")>=0);
            }
            if (prestada){
            PreparedStatement pstm2=con.prepareStatement("UPDATE `biblioteca`.`usuarios` SET `tiempo` = date_add(current_time(),interval ? minute) WHERE (`id` = ?);");
            pstm2.setInt(1, (tiempo*30) );
            pstm2.setString(2,idusuario);
            if (pstm2.executeUpdate()==1){
                try(
                        PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`salas` SET `hora` = date_add(current_time(),interval ? minute), `user` = ? WHERE (`id` = ?);");

                ){
                    pstm.setInt(1, (tiempo*30) );
                    pstm.setString(2,idusuario);
                    pstm.setInt(3, idsala);
                    result=pstm.executeUpdate()==1;
                }catch (Exception e){
                 e.printStackTrace();
                }
            }}
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public boolean devolverSala(int idsala){
        boolean result =false;
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm= con.prepareStatement("UPDATE `biblioteca`.`salas` SET `hora` = now(), `user` = '' WHERE (`id` = ?);");
                PreparedStatement pstm2=con.prepareStatement("UPDATE `biblioteca`.`usuarios` SET `tiempo` = now() WHERE (`id` = ?);")
        ){
            pstm.setInt(1, idsala );
            DaoSala daoSala=new DaoSala();
            pstm2.setString(1,daoSala.getSala(idsala));
            result=pstm.executeUpdate()==1&&pstm2.executeUpdate()==1;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    public String getSala(int id){
        String matricula="";
        try(
                Connection con=MySQLConnection.getConnection();
                PreparedStatement pstm=con.prepareStatement("SELECT user FROM biblioteca.salas where id=?; ")
                )
        {
            pstm.setInt(1,id);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                matricula=(rs.getString("user"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return matricula;
    }

}

package mx.edu.utez.biblioteca.biblioteca.model;

import mx.edu.utez.biblioteca.biblioteca.utils.MySQLConnection ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoAuthentication {
    public BeanAuthentication login(String id, String password) {
        BeanAuthentication userAuthentication = new BeanAuthentication();

        try
                (Connection con  = MySQLConnection.getConnection();
                 PreparedStatement pstm = con.prepareStatement("SELECT * FROM biblioteca.id_pass_prestamos_view where id=? and password =? and enable=1;");
                )

        {
            pstm.setString(1, id);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()){
                userAuthentication.setNickname(rs.getString("id"));
                userAuthentication.setRol(rs.getInt("tipo"));
                userAuthentication.setNoprestamos(rs.getInt("noprestamos"));
                userAuthentication.setSalaprestada(rs.getInt("disponibilidad")>=0);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return userAuthentication;
    }
}

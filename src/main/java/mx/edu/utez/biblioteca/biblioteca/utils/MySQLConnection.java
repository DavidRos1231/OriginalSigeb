package mx.edu.utez.biblioteca.biblioteca.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {

    public static Connection getConnection(){

        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/biblioteca","root","root");
            /*return DriverManager.getConnection("jdbc:mysql://dbs3b.cuplbm5tw32y.us-east-1.rds.amazonaws.com:3306/biblioteca","admin","Root4Ws2022");*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        try {
            Connection conexion = MySQLConnection.getConnection();
            if (conexion != null) {
                System.out.println("Conectado");
                conexion.close();
            }
            else{
                System.out.println("Noconectado");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

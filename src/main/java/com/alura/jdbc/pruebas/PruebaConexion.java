package com.alura.jdbc.pruebas;

import com.alura.jdbc.factory.Conexion_Factory_ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
       Connection con = new Conexion_Factory_ConexionBD().RecuperaConexion();

        System.out.println("Cerrando la conexión");

        con.close();
    }

}

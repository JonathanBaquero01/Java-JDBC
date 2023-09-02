/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alura.jdbc.factory;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Admin
 */
public class Conexion_Factory_ConexionBD {
    
    private DataSource datasource;
    
    public Conexion_Factory_ConexionBD(){//aqui configuraremos el pool de coenxiones, para poner un min y un max de conexiones a la BD
    var pooledDataSource = new ComboPooledDataSource();
    pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
    pooledDataSource.setUser("root");
    pooledDataSource.setPassword("1234");
    
    //MAXIMO NUMERO DE CONEXIONES, si ya hay 10, el prox cliente debe esperar hasta que un cliente salga
    pooledDataSource.setMaxPoolSize(10);
     //MINIMO NUMERO DE CONEXIONES
    pooledDataSource.setMinPoolSize(5);
    
    this.datasource= pooledDataSource;
    }
    
    public Connection RecuperaConexion() throws SQLException{
   return this.datasource.getConnection();
        
        //esta es solo para una conexion
        /*return DriverManager.getConnection(
                "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC", "root", "1234");*/
     
    }
    
}

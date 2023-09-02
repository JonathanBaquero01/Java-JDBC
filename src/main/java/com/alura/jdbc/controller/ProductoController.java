package com.alura.jdbc.controller;

import com.alura.jdbc.factory.Conexion_Factory_ConexionBD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
   
       Connection con = new Conexion_Factory_ConexionBD().RecuperaConexion();
        
       //este ya es um try con recursos, cierra el con y statement automaticamente, los otros son mas macheteados pero mas faciles de entender
        try(con) {
            final PreparedStatement statement = con
                .prepareStatement("UPDATE PRODUCTO SET "
                        + " NOMBRE = ?, "
                        + " DESCRIPCION = ?,"
                        + " CANTIDAD = ?"
                        + " WHERE ID = ?");
            
            try(statement) {
                statement.setString(1, nombre);
                statement.setString(2, descripcion);
                statement.setInt(3, cantidad);
                statement.setInt(4, id);
                statement.execute();
        
                int updateCount = statement.getUpdateCount();
        
                return updateCount;
            }
        }
    }

	public int eliminar(Integer id) throws SQLException {
              Connection con = new Conexion_Factory_ConexionBD().RecuperaConexion();
               //Es para empezar a hacer SELECT y otros comandos    
 PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
            try {
                  con.setAutoCommit(false);         
                 
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                con.commit();
            } catch (Exception e) {
                  //si hay un excepcion, cualquiera, de una un rollback 
            con.rollback();   
             con.close();
     preparedStatement.close();
            }
             //Nos devuelve cuantas filas fueron modificadas, pa q nos diga cuantos registros fueron eliminados
             int updateCount = preparedStatement.getUpdateCount();
              con.close();
     preparedStatement.close();
     
               
               return updateCount;
           
	}

	public List<Map<String, String>> listar() throws SQLException {
	  Connection con = new Conexion_Factory_ConexionBD().RecuperaConexion();
                //Es para empezar a hacer SELECT y otros comandos        
               PreparedStatement  preparedStatement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
        List<Map<String, String>> resultado = new ArrayList<>();
            try {
                  //para q no haga el commit hasta q le digammos, es por si sale un error y hace un commit incompleto
    con.setAutoCommit(false);
                
                
                preparedStatement.execute();

    ResultSet resultSet = preparedStatement.getResultSet();

   
    while (resultSet.next()) {
        Map<String, String> fila = new HashMap<>();
        
        fila.put("ID", String.valueOf(resultSet.getInt("ID")));
        fila.put("NOMBRE", resultSet.getString("NOMBRE"));
        fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
        fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));
        
        resultado.add(fila);
    }
     //aqui ya le digo que haga commit, que guarde
    con.commit();
                
               
            } catch (Exception e) {
                //si hay un excepcion, cualquiera, de una un rollback 
            con.rollback();   
             con.close();
     preparedStatement.close();
            }
                con.close();
     preparedStatement.close();
       
         return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
        Connection con = new Conexion_Factory_ConexionBD().RecuperaConexion();
        
        String insertQuery = "INSERT INTO PRODUCTO (nombre, descripcion, cantidad) VALUES (?, ?, ?)";
        //EL RETURN_GENERATED_KEYS es para que me de los ID q genero la BD ya q es autoincrement
 PreparedStatement preparedStatement = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

        try {
            
    //para q no haga el commit hasta q le digammos, es por si sale un error y hace un commit incompleto
    con.setAutoCommit(false);

    preparedStatement.setString(1, producto.get("NOMBRE"));
    preparedStatement.setString(2, producto.get("DESCRIPCION"));
    preparedStatement.setInt(3, Integer.parseInt(producto.get("CANTIDAD")));

    preparedStatement.executeUpdate();

    //obntego los ID que genero la BD
    ResultSet resultSet = preparedStatement.getGeneratedKeys();


    //con la lista de los ID hago un while para recorrer
    while (resultSet.next()) {
        System.out.println(String.format("Fue insertado el producto de ID: %d", resultSet.getInt(1)));
    }
    //aqui ya le digo que haga commit, que guarde
    con.commit();
   

        } catch (Exception e) {
            //si hay un excepcion, cualquiera, de una un rollback 
            con.rollback();   
             con.close();
     preparedStatement.close();
        }
     con.close();
     preparedStatement.close();

	}

}

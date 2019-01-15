package cad;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javaben.Marca;

/**
 *
 * @author nunez-pc
 */
public class MarcaCad {
    public static ArrayList<Marca> listarTodoDeMarcas() {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia;
            
            sentencia = c.prepareCall("{CALL sp_listarTodoDeMarca()}");
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Marca> lista = new ArrayList<>();
            
            while(resultado.next()){
                Marca marca = new Marca();
                marca.setCodigo(resultado.getInt("codigo"));
                marca.setNombre(resultado.getString("nombre"));
                lista.add(marca);
            }
            return lista;
        } catch (SQLException ex) {
           return null; 
        }
    }
    
    public static int contarMarcas(int cveMarca) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_contarProductosMarca(?)}");
            sentencia.setInt(1, cveMarca);
            ResultSet resultado = sentencia.executeQuery();
            resultado.next();
            return resultado.getInt(1);
        } catch (SQLException ex) {
           return 0; 
        }
    }
}

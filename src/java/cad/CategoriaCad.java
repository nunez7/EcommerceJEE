package cad;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javaben.Categoria;
import java.util.ArrayList;

/**
 *
 * @author nunez-pc
 */
public class CategoriaCad {

    public static ArrayList<Categoria> listarCategoriaSuperior() {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia;
            
            sentencia = c.prepareCall("{CALL sp_listarCategoriaSuperior()}");
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Categoria> lista = new ArrayList<>();
            
            while(resultado.next()){
                Categoria cat = new Categoria();
                cat.setCodigo(resultado.getInt("codigo"));
                cat.setNombre(resultado.getString("nombre"));
                lista.add(cat);
            }
            return lista;
        } catch (SQLException ex) {
           return null; 
        }
    }
    public static ArrayList<Categoria> listarSubCategoria(int cveCategoriaSuperior) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_listarSubCategoria(?)}");
            sentencia.setInt(1, cveCategoriaSuperior);
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Categoria> lista = new ArrayList<>();
            
            while(resultado.next()){
                Categoria cat = new Categoria();
                cat.setCodigo(resultado.getInt("codigo"));
                cat.setNombre(resultado.getString("nombre"));
                lista.add(cat);
            }
            return lista;
        } catch (SQLException ex) {
           return null; 
        }
    }
    
    public static boolean esSuperior(int catSuperior) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_contarSubCategorias(?)}");
            sentencia.setInt(1, catSuperior);
            ResultSet resultado = sentencia.executeQuery();
            
            resultado.next();
            
            return resultado.getInt("cantidad")>0;
        } catch (SQLException ex) {
           return false; 
        }
    }
    public static ArrayList<Categoria> listarTodoDeCategorias() {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia;
            
            sentencia = c.prepareCall("{CALL sp_listarTodoDeCategoria()}");
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Categoria> lista = new ArrayList<>();
            
            while(resultado.next()){
                Categoria cat = new Categoria();
                cat.setCodigo(resultado.getInt("codigo"));
                cat.setNombre(resultado.getString("nombre"));
                lista.add(cat);
            }
            return lista;
        } catch (SQLException ex) {
           return null; 
        }
    }
}

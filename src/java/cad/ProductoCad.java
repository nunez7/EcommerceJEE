package cad;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javaben.Producto;
import javaben.ProductoMoneda;

/**
 *
 * @author nunez-pc
 */
public class ProductoCad {

    public static boolean registrarProducto(Producto p, ProductoMoneda cop, ProductoMoneda usd, ProductoMoneda pen) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_registrarProducto(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            //Producto
            sentencia.setString(1, p.getNombre());
            sentencia.setFloat(2, p.getPrecio());
            sentencia.setFloat(3, p.getPrecioNuevo());
            sentencia.setInt(4, p.getStock());
            sentencia.setBoolean(5, p.isNuevo());

            sentencia.setBoolean(6, p.isRecomendado());
            sentencia.setString(7, p.getDescripcion());
            sentencia.setBoolean(8, p.isVisible());
            sentencia.setInt(9, p.getCodigoMarca());
            sentencia.setInt(10, p.getCodigoCategoria());
            sentencia.setString(11, p.getImagen());
            //Monedas
            sentencia.setString(12, cop.getMoneda());
            sentencia.setFloat(13, cop.getPrecio());
            sentencia.setFloat(14, cop.getNuevoPrecio());

            sentencia.setString(15, usd.getMoneda());
            sentencia.setFloat(16, usd.getPrecio());
            sentencia.setFloat(17, usd.getNuevoPrecio());

            sentencia.setString(18, pen.getMoneda());
            sentencia.setFloat(19, pen.getPrecio());
            sentencia.setFloat(20, pen.getNuevoPrecio());

            return sentencia.executeUpdate() > 0;
        } catch (SQLException ex) {
            return false;
        }
    }

    public static ArrayList<Producto> listarProductosRecomendados(String moneda) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_listarRecomendados(?)}");
            sentencia.setString(1, moneda);
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Producto> lista = new ArrayList<>();

            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setWebId(resultado.getInt("cve_web"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setImagen(resultado.getString("img"));
                producto.setStock(resultado.getInt("stock"));
                producto.setNuevo(resultado.getBoolean("nuevo"));

                if (!moneda.equalsIgnoreCase("MXN")) {
                    producto.setPrecio(resultado.getFloat("precio2"));
                    producto.setPrecioNuevo(resultado.getFloat("precion2"));
                } else {
                    producto.setPrecio(resultado.getFloat("precio"));
                    producto.setPrecioNuevo(resultado.getFloat("precionuevo"));
                }
                lista.add(producto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

    public static ArrayList<Producto> listarProductosPorCategoria(String moneda, int categoria) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_listarPorCategoria(?, ?)}");
            sentencia.setString(1, moneda);
            sentencia.setInt(2, categoria);
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Producto> lista = new ArrayList<>();

            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setWebId(resultado.getInt("cve_web"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setImagen(resultado.getString("img"));
                producto.setStock(resultado.getInt("stock"));
                producto.setNuevo(resultado.getBoolean("nuevo"));

                if (!moneda.equalsIgnoreCase("MXN")) {
                    producto.setPrecio(resultado.getFloat("precio2"));
                    producto.setPrecioNuevo(resultado.getFloat("precion2"));
                } else {
                    producto.setPrecio(resultado.getFloat("precio"));
                    producto.setPrecioNuevo(resultado.getFloat("precionuevo"));
                }
                lista.add(producto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

    public static ArrayList<Producto> listarProductosPorMarca(String moneda, int marca) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_listarPorMarca(?, ?)}");
            sentencia.setString(1, moneda);
            sentencia.setInt(2, marca);
            ResultSet resultado = sentencia.executeQuery();
            ArrayList<Producto> lista = new ArrayList<>();

            while (resultado.next()) {
                Producto producto = new Producto();
                producto.setWebId(resultado.getInt("cve_web"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setImagen(resultado.getString("img"));
                producto.setStock(resultado.getInt("stock"));
                producto.setNuevo(resultado.getBoolean("nuevo"));

                if (!moneda.equalsIgnoreCase("MXN")) {
                    producto.setPrecio(resultado.getFloat("precio2"));
                    producto.setPrecioNuevo(resultado.getFloat("precion2"));
                } else {
                    producto.setPrecio(resultado.getFloat("precio"));
                    producto.setPrecioNuevo(resultado.getFloat("precionuevo"));
                }
                lista.add(producto);
            }
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }

    public static Producto consultarProducto(String moneda, int cveProducto) {
        try {
            //La conexión
            Connection c = Conexion.conectar();
            CallableStatement sentencia = c.prepareCall("{CALL sp_consultarProducto(?, ?)}");
            sentencia.setString(1, moneda);
            sentencia.setInt(2, cveProducto);
            ResultSet resultado = sentencia.executeQuery();

            Producto producto = null;
            if (resultado.next()) {
                producto = new Producto();
                producto.setWebId(resultado.getInt("cve_web"));
                producto.setNombre(resultado.getString("nombre"));
                producto.setImagen(resultado.getString("img"));
                producto.setStock(resultado.getInt("stock"));
                producto.setNuevo(resultado.getBoolean("nuevo"));

                if (!moneda.equalsIgnoreCase("MXN")) {
                    producto.setPrecio(resultado.getFloat("precio2"));
                    producto.setPrecioNuevo(resultado.getFloat("precion2"));
                } else {
                    producto.setPrecio(resultado.getFloat("precio"));
                    producto.setPrecioNuevo(resultado.getFloat("precionuevo"));
                }
            }

            return producto;
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
            return null;
        }
    }
}

package control;

import cad.ProductoCad;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaben.Producto;
import javaben.ProductoMoneda;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author nunez-pc
 */
public class ControlProducto extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControlProducto</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControlProducto at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        recibirDatos(request);

        String url = request.getAttribute("imagen").toString();

        String nombre = request.getAttribute("nombre").toString();
        float precio = Float.parseFloat(request.getAttribute("precio").toString());
        float precioN = Float.parseFloat(request.getAttribute("precio_nuevo").toString());

        float precioCop = Float.parseFloat(request.getAttribute("preciocop").toString());
        float precioNCop = Float.parseFloat(request.getAttribute("precio_nuevocop").toString());

        float precioUs = Float.parseFloat(request.getAttribute("preciousd").toString());
        float precioNUs = Float.parseFloat(request.getAttribute("precio_nuevousd").toString());

        float precioPen = Float.parseFloat(request.getAttribute("preciopen").toString());
        float precioNPen = Float.parseFloat(request.getAttribute("precio_nuevopen").toString());

        int stock = Integer.parseInt(request.getAttribute("cantidad").toString());
        int marca = Integer.parseInt(request.getAttribute("marca").toString());
        int categoria = Integer.parseInt(request.getAttribute("categoria").toString());

        String descripcion = request.getAttribute("descripcion").toString();

        boolean nuevo, recomendado, visible;

        try {
            nuevo = (request.getAttribute("nuevo").toString().equalsIgnoreCase("on"));
        } catch (Exception e) {
            nuevo = false;
        }
        try {
            recomendado = (request.getAttribute("recomendado").toString().equalsIgnoreCase("on"));
        } catch (Exception e) {
            recomendado = false;
        }
        try {
            visible = (request.getAttribute("visible").toString().equalsIgnoreCase("on"));
        } catch (Exception e) {
            visible = false;
        }
        String accion = request.getAttribute("accion").toString();

        Producto p = new Producto();
        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setPrecioNuevo(precioN);
        p.setStock(stock);
        p.setCodigoCategoria(categoria);
        p.setCodigoMarca(marca);
        p.setDescripcion(descripcion);
        p.setNuevo(nuevo);
        p.setRecomendado(recomendado);
        p.setVisible(visible);
        p.setImagen(url);

        ProductoMoneda cop = new ProductoMoneda();
        cop.setMoneda("COP");
        cop.setPrecio(precioCop);
        cop.setNuevoPrecio(precioNCop);

        ProductoMoneda usa = new ProductoMoneda();
        usa.setMoneda("USD");
        usa.setPrecio(precioUs);
        usa.setNuevoPrecio(precioNUs);

        ProductoMoneda pen = new ProductoMoneda();
        pen.setMoneda("PEN");
        pen.setPrecio(precioPen);
        pen.setNuevoPrecio(precioNPen);

        if (accion.equalsIgnoreCase("registrar")) {
            if (ProductoCad.registrarProducto(p, cop, usa, pen)) {
                request.setAttribute("mensaje", "<p style='color:green'>Producto registrado</p>");
            } else {
                request.setAttribute("mensaje", "<p style='color:red'>Producto no registrado</p>");
            }
        } else {
            request.setAttribute("mensaje", "<p style='color:red'>Acción desconocida</p>");
        }
        request.getRequestDispatcher("admin").forward(request, response);
        //response.sendRedirect("foto/"+url);
    }

    private void recibirDatos(HttpServletRequest request) {
        try {
            FileItemFactory fileFactory = new DiskFileItemFactory();

            ServletFileUpload servletUplod = new ServletFileUpload(fileFactory);

            String nombre = "";
            List items = servletUplod.parseRequest(request);
            //Iteramos los campos del form
            for (int i = 0; i < items.size(); i++) {
                FileItem item = (FileItem) items.get(i);
                //Preguntamos si no es archivo de formulario
                if (!item.isFormField()) {
                    String ruta = request.getServletContext().getRealPath("/") + "foto/";
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMyyyyhhmmss");
                    String fecha = sdf.format(new Date());
                    nombre = fecha + new Random().nextLong() + item.getName();
                    String nuevoNombre = ruta + nombre;

                    //Verificar la carpeta
                    File folder = new File(ruta);
                    if (!folder.exists()) {
                        folder.mkdirs();
                    }
                    File imagen = new File(nuevoNombre);
                    //Si es imagen la escribimos
                    if (item.getContentType().contains("image")) {
                        item.write(imagen);
                        //Nombre de la imagen: img y su valor
                        request.setAttribute(item.getFieldName(), nombre);
                    }
                } else {
                    //Asigna los valores a atributos
                    request.setAttribute(item.getFieldName(), item.getString("UTF-8"));
                }
            }

        } catch (FileUploadException ex) {
            request.setAttribute("subida", true);
        } catch (Exception ex) {
            request.setAttribute("subida", true);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

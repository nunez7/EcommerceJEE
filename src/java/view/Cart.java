package view;

import cad.ProductoCad;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javaben.Item;
import javaben.Producto;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author nunez-pc
 */
public class Cart extends HttpServlet {

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
        //PrintWriter pw = response.getWriter();
        if (request.getParameter("action") != null) {
            String a = request.getParameter("action");
            int cveProducto = Integer.parseInt(request.getParameter("id"));
            Producto p;
            HttpSession session = request.getSession();
            ArrayList<Item> cart;
            if (session.getAttribute("cart") == null) {
                cart = new ArrayList<>();
            } else {
                cart = (ArrayList<Item>) session.getAttribute("cart");
            }
            int indice = yaExisteProducto(cveProducto, cart);
            switch (a) {
                case "order":
                    //Si no existe el producto, lo agregamos
                    if (indice == -1) {
                        p = ProductoCad.consultarProducto(session.getAttribute("moneda").toString(), cveProducto);
                        cart.add(new Item(p, 1));
                    } else {
                        //Si ya existe le sumamos 1 a la cantidad
                        int cantidad = cart.get(indice).getCantidad() + 1;
                        cart.get(indice).setCantidad(cantidad);
                    }
                    break;
                case "delete":
                    if (indice >= 0) {
                        cart.remove(indice);
                    }
                    break;
                case "plus":
                    int cantidad = cart.get(indice).getCantidad() + 1;
                    cart.get(indice).setCantidad(cantidad);
                    break;
                case "min":
                    if (cart.get(indice).getCantidad() == 1) {
                        cart.remove(indice);
                    } else {
                        cart.get(indice).setCantidad(cart.get(indice).getCantidad() - 1);
                    }
                    break;
                case "finish":
                    cart.clear();
                    break;
            }
            if (cart.size() > 0) {
                session.setAttribute("cart", cart);
            }
        }
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("WEB-INF/cart.jsp").forward(request, response);
    }

    private int yaExisteProducto(int webid, ArrayList<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getP().getWebId() == webid) {
                return i;
            }
        }
        return -1;
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
        processRequest(request, response);
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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="cad.MarcaCad"%>
<%@page import="cad.CategoriaCad"%>
<%@page import="java.util.ArrayList"%>
<%@page import="javaben.Categoria"%>
<div class="left-sidebar">
    <h2>Categorías</h2>
    <div class="panel-group category-products" id="accordian"><!--category-productos-->
        <%! ArrayList<Categoria> listaCategoriaSuperior = CategoriaCad.listarCategoriaSuperior();%>
        <%
            for (int i = 0; listaCategoriaSuperior.size() > i; i++) {
                int codigo = listaCategoriaSuperior.get(i).getCodigo();
        %>
        <div class="panel panel-default">
            <div class="panel-heading">
                <h4 class="panel-title">
                    <a <% if (CategoriaCad.esSuperior(codigo)) { %> data-toggle="collapse" data-parent="#accordian" <%}%> href="#<%=listaCategoriaSuperior.get(i).getCodigo()%>">
                        <% if (CategoriaCad.esSuperior(codigo)) { %><span class="badge pull-right"><i class="fa fa-plus"></i></span> <%}%>  
                        <a href="?category=<%=codigo%>"><%=listaCategoriaSuperior.get(i).getNombre()%></a>
                    </a>
                </h4>
            </div>
            <div id="<%=listaCategoriaSuperior.get(i).getCodigo()%>" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul>
                        <%
                            ArrayList<Categoria> listaSubCategoria = CategoriaCad.listarSubCategoria(codigo);
                            for (int j = 0; listaSubCategoria.size() > j; j++) {
                        %>
                        <li><a  href="?category=<%=codigo%>"><%=listaSubCategoria.get(j).getNombre()%> </a></li>
                            <%
                                }
                            %>
                    </ul>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div><!--/category-products-->

    <div class="brands_products"><!--brands_products-->
        <h2>Marcas</h2>
        <div class="brands-name">
            <ul class="nav nav-pills nav-stacked">
                <c:forEach var="m" items="<%=MarcaCad.listarTodoDeMarcas()%>">
                    <c:set var="cod" value="${m.codigo}"></c:set>
                    <%int cod = Integer.parseInt(pageContext.getAttribute("cod").toString()); %>
                    <li><a href="?brand=${m.codigo}"> <span class="pull-right">(<%=MarcaCad.contarMarcas(cod) %>)</span>${m.nombre}</a></li>
                </c:forEach>
        </ul>
    </div>
</div><!--/brands_products-->

<div class="shipping text-center"><!--shipping-->
    <img src="images/home/shipping.jpg" alt="" />
</div><!--/shipping-->

</div>
<%-- 
    Document   : index
    Created on : 12/01/2019, 10:31:58 AM
    Author     : nunez-pc
--%>
<%@page import="cad.MarcaCad"%>
<%@page import="javaben.Marca"%>
<%@page import="cad.CategoriaCad"%>
<%@page import="javaben.Categoria"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Gestión de productos | Crea e-Commerce JAVA EE con pagos Online Paypal y Payu</title>
    <%@include file="../../WEB-INF/css.jsp" %>
</head><!--/head-->

<body>
    <%@include file="../../WEB-INF/header.jsp" %>  
        <section>
		<div class="container">
			<div class="row">
				<div class="col-sm-12 clearfix">
                                    ${mensaje}
                                    <h3>Gestionar producto</h3>
                                    <form action="ControlProducto" enctype="multipart/form-data" method="post">
                                    <div class="form-one">
                                        <label>Producto:<input type="text" required name="nombre" value="" placeholder="Nombre producto" /></label>
                                        <hr />
                                        <label>Precio (MXN):<input type="number" name="precio" value="0" min="0" placeholder="Precio" /></label>
                                        <label>Precio promo (MXN):<input type="number" name="precio_nuevo" value="0" min="0" placeholder="Precio nuevo" /></label>
                                        <hr />
                                        <label>Precio (USD):<input type="number" name="preciousd" value="0" min="0" placeholder="Precio" /></label>
                                        <label>Precio promo (USD):<input type="number" name="precio_nuevousd" value="0" min="0" placeholder="Precio nuevo" /></label>
                                        <hr />
                                        <label>Precio (COP):<input type="number" name="preciocop" value="0" min="0" placeholder="Precio" /></label>
                                        <label>Precio promo (COP):<input type="number" name="precio_nuevocop" value="0" min="0" placeholder="Precio nuevo" /></label>
                                        <hr />
                                        <label>Precio (PEN):<input type="number" name="preciopen" value="0" min="0" placeholder="Precio" /></label>
                                        <label>Precio promo (PEN):<input type="number" name="precio_nuevopen" value="0" min="0" placeholder="Precio nuevo" /></label>
                                        <hr />
                                        <label>Stock:<input type="number" name="cantidad" value="1" min="1" placeholder="Cantidad" /></label>
                                        <br />
                                        <label>Marca:</label>
                                        <select name="marca" required>
                                            <option value="">- Selecciona la marca -</option>
                                            <%
                                            for(Marca m: MarcaCad.listarTodoDeMarcas()){
                                            %>
                                            <option value="<%=m.getCodigo()%>"><%=m.getNombre()%></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                        <label>Categoria:</label>
                                        <select name="categoria" required>
                                            <option value="">- Selecciona la categoria -</option>
                                            <%
                                            for(Categoria c: CategoriaCad.listarTodoDeCategorias()){
                                            %>
                                            <option value="<%=c.getCodigo()%>"><%=c.getNombre()%></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                        <label>Descripción:</label>
                                        <textarea name="descripcion" required rows="4" placeholder="Descripción" cols="20">
                                        </textarea>
                                        <label>Nuevo?:<input type="checkbox" name="nuevo" value="ON" checked /></label>
                                        <label>Recomendado?:<input type="checkbox" name="recomendado" value="ON" /></label>
                                        <label>Visible?:<input type="checkbox" name="visible" value="ON" checked /></label>
                                        <br />
                                        <label>Seleccionar imagen del producto</label>
                                        <input type="file" name="imagen" value="Selecciona una imagen" required />
                                        <hr />
                                        <input class="btn btn-success" type="submit" name="accion" value="Registrar" />
                                        <input class="btn btn-default" type="submit" name="accion" value="Consultar" />
                                        <input class="btn btn-warning" type="submit" name="accion" value="Actualizar" />
                                        <input class="btn btn-danger" type="submit" name="accion" value="Borrar" />
                                        
                                    </div>
                                    </form>
				</div>
			</div>
		</div>
        </section>
        <%@include file="../../WEB-INF/footer.jsp" %>
        <%@include file="../../WEB-INF/js.jsp" %>
</body>
</html>

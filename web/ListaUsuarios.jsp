<%-- 
    Document   : ListaUsuarios.jsp
    Created on : 08-oct-2012, 1:14:08
    Author     : Guillermo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*,Entidades.Usuario" %>

<%
//Obtener el arreglo de estudiantes enviado en la solicitud
    ArrayList<Usuario> usuarios = (ArrayList<Usuario>) request.getAttribute("usuarios");
    int contador = 0;

    //mensaje de datos eliminados
    /*
     * equivalente a if(request.getAttribute("mensaje")==null) mensaje=""; else
     * mensaje=request.getAttribute("mensaje");
     */
    String mensaje = request.getAttribute("mensaje") == null ? "" : (String) request.getAttribute("mensaje");
    String estado = "";
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/styles.css" type="text/css" >        
        <link rel="stylesheet" href="css/bluedream.css" type="text/css" >        
        <link rel="stylesheet" type="text/css" href="css/jMenu.jquery.css" media="screen" /> 
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.23.custom.css"/> 
        <script src = "js/jquery-1.8.0.min.js"></script>
        <script src = "js/jquery-ui-1.8.23.custom.min.js"></script>
        <script src="js/jMenu.jquery.js"></script>         
        <script type="text/javascript"> 
            $(document).ready(function(){ 
                // simple jMenu plugin called 
                $("#jMenu").jMenu(); 
                // more complex jMenu plugin called 
                $("#jMenu").jMenu({ 
                    ulWidth : 'auto', 
                    effects : { 
                        effectSpeedOpen : 300, 
                        effectTypeClose : 'hide' 
                    }, 
                    animatedText : false 
                }); 
                
                $("#menuAdminOP1").click(function(){
                    alert($("#menuAdminOP1").html())
                });
            }); 
        </script>             
    </head>    
    <body>
        <div id='header'>
            <div id='login'>                
            </div>
            <div id='banner'>
                <img src='imagenes/logo_unac.jpg' width='282' height='91' alt='logo_unac'/>
            </div>
        </div>
        <jsp:include page="menuAdmin.jsp"/>        
        <br>
        <hr>
        <table align="center">            
            <caption>Usuarios Registrados</caption>
            <thead>
                <tr>
                    <th scope="col">email</th>
                    <th scope="col">Clave</th>
                    <th scope="col">Nombre</th>
                    <th scope="col">Apellido</th>
                    <th scope="col">Teléfono</th>
                    <th scope="col">Género</th>
                    <th scope="col">Perfil</th>
                    <th scope="col">Estado</th>
                </tr>
            </thead>
            <%
                if (usuarios != null && usuarios.size() > 0) {
                    for (Iterator iterator = usuarios.iterator(); iterator.hasNext();) {
                        Usuario u = (Usuario) iterator.next();
                        contador++;
            %>
            <tbody>
                <%
                    if (contador % 2 == 0) {
                %>
                <tr>
                    <%} else {%>
                <tr class="odd">                               
                    <%}%>
                    <td>
                        <%= u.getEmail()%>
                    </td>
                    <td>
                        <%= u.getClave()%>
                    </td>
                    <td>
                        <%= u.getNombre()%>
                    </td>
                    <td>
                        <%= u.getApellido()%>
                    </td>
                    <td>
                        <%= u.getTelefono()%>
                    </td>
                    <td>
                        <%= u.getDescripcionGenero()%>
                    </td>
                    <td>
                        <%= u.getDescripcionPerfil()%>
                    </td>
                    <td>
                       <%= u.getDescripcionEstado()%>
                    </td>
                    <td>
                        <a href="ControladorUsuarios?accion=eliminar&ID=<%=u.getEmail()%>">Eliminar</a>
                    </td>
                    <td>
                        <a href="ControladorUsuarios?accion=editar&ID=<%=u.getEmail()%>">Editar</a>
                    </td>
                </tr>
            </tbody>
            <%
                    }
                }
            %>
        </table>
        <br>       
        <p style="text-align:center">
            <a href="ControladorUsuarios?accion=nuevo">Nuevo Usuario</a>  
        </p>
        <p style="text-align:center;color: red">
            <%=mensaje%>
        </p>        
        <hr>   
    </body>
</html>
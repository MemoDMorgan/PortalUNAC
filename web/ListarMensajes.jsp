<%-- 
    Document   : ListaUsuarios.jsp
    Created on : 08-oct-2012, 1:14:08
    Author     : Guillermo
--%>


<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@ page language="java" import="java.util.*,Entidades.Usuario" %>
<%@page import="Entidades.Mensaje"%>

<%

//obtener datos de la sesión sino existen rediridir a index para login
    String nombre = "";
    String perfil = "";
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("usuario") == null) {
        request.setAttribute("error", "Es obligatorio identificarse");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    } else {
        nombre = (String) sesionOk.getAttribute("usuario");
        perfil = (String) sesionOk.getAttribute("perfil");
    }
    //Obtener el arreglo de mensajes enviado en la solicitud
    ArrayList<Mensaje> mensajes = (ArrayList<Mensaje>) request.getAttribute("Mensajes");
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
        <title>Listar Mensajes</title>
        <link rel="stylesheet" href="css/styles.css" type="text/css" >        
        <link rel="stylesheet" href="css/bluedream.css" type="text/css" >        
        <link rel="stylesheet" type="text/css" href="css/jMenu.jquery.css" media="screen" /> 
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.23.custom.css"/> 
        <script src = "js/jquery-1.8.0.min.js"></script>
        <script src = "js/jquery-ui-1.8.23.custom.min.js"></script>
        <script src = "js/myscripts.js"></script>
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
            <div id='mensajeIN'>                
                <b>Bienvenido <%=nombre%>&nbsp;&nbsp;</b>
                <a href="ControladorUsuarios?accion=salir"><img src="imagenes/salir.gif" width="48" height="48" alt="salir"/></a>
            </div>
            <div id='banner'>
                <img src='imagenes/logo_unac.jpg' width='282' height='91' alt='logo_unac'/>
            </div>
        </div>
        <jsp:include page="menuProfe.jsp"/>
        <p class="verMensajes">Mensajes sin leer:
            <a href="ControladorUsuarios?accion=verMensajesNoRead" id="nmensajes" ></a></p>
        <br>
        <hr>
        <table align="center">            
            <caption>Mensajes Enviados</caption>
            <thead>
                <tr>
                    <th scope="col">De</th>
                    <th scope="col">Correo</th>
                    <th scope="col">Mensaje</th>                    
                    <th scope="col">Fecha</th>
                    <th scope="col">Estado</th>
                </tr>
            </thead>
            <%
                if (mensajes != null && mensajes.size() > 0) {
                    Calendar fecha;
                    String sfecha;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    for (Iterator iterator = mensajes.iterator(); iterator.hasNext();) {
                        Mensaje u = (Mensaje) iterator.next();
                        fecha = u.getFechaMensaje();
                        sfecha = sdf.format(fecha.getTime());
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
                        <%= u.getNombreDe()%>
                    </td>
                    <td>
                        <%= u.getMailDe()%>
                    </td>
                    <td>
                        <%= u.getMensaje()%>
                    </td>                    
                    <td>
                        <%= sfecha%>
                    </td>                    
                    <td>
                        <%= u.getDescripcionEstado()%>
                    </td>   
                </tr>
            </tbody>
            <%
                    }
                }
            %>
        </table>        
        <hr>   
    </body>
</html>
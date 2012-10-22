<%-- 
    Document   : Home
    Created on : 07-sep-2012, 2:09:12
    Author     : Guillermo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%> 
<%
    //String nombre = (String) request.getAttribute("usuario");
    //String perfil = (String) request.getAttribute("perfil");
    String nombre = "";
    String perfil = "";
    String mensaje = "";
    String sperfil="";
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("usuario") == null) {
        request.setAttribute("error", "Es obligatorio identificarse");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    } else {
        nombre = (String) sesionOk.getAttribute("usuario");
        perfil = (String) sesionOk.getAttribute("perfil");
        sperfil=(String) sesionOk.getAttribute("sperfil");
        mensaje = request.getAttribute("mensaje") == null ? "" : (String) request.getAttribute("mensaje");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/styles.css" type="text/css" >
        <title>JSP Page</title>        
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
                <b>Bienvenido <%=sperfil%>:<%=nombre%>&nbsp;&nbsp;</b>
                <a href="ControladorUsuarios?accion=salir">
                    <img src="imagenes/salir.gif" width="48" height="48" alt="salir"/>
                </a>                
            </div>
            <div id='banner'>
                <img src='imagenes/logo_unac.jpg' width='282' height='91' alt='logo_unac'/>
            </div>
        </div>
        <%
            if (perfil.equals("1")) {
        %>
        <jsp:include page="menuAdmin.jsp"/>
        <%} else if (perfil.equals("2")) {
        %>
        <jsp:include page="menuEstudiante.jsp"/>
        <%} else if (perfil.equals("3")) {
        %>
        <jsp:include page="menuProfe.jsp"/>
        <p class="verMensajes">Mensajes sin leer:
            <a href="ControladorUsuarios?accion=verMensajesNoRead" id="nmensajes" ></a></p>
            <%}
            %>
        <div id='content' style='text-align:center'>
            <h1>Bienvenido al PORTAL UNAC </h1>
            <p class='mensajeT2'>Hola <%=nombre%>, has ingresado exitosamente !</p><br>            
            <%=mensaje%>
        </div>
    </body>
</html>

<%-- 
    Document   : Home
    Created on : 07-sep-2012, 2:09:12
    Author     : Guillermo
--%>
<%
    String nombre = (String)request.getAttribute("usuario");    
    String perfil = (String)request.getAttribute("perfil");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <%
            if (perfil.equals("1")) {
        %>
        <jsp:include page="menuAdmin.jsp"/>
        <%} else if (perfil.equals("2")) {
        %>
        <jsp:include page="menuEstudiante.jsp"/>
        <%            }
        %>
        <div id='content' style='text-align:center'>
            <h1>Bienvenido al PORTAL UNAC </h1>
            <p class='mensajeT2'>Hola <%=nombre%>, has ingresado exitosamente !</p><br>            
        </div>
    </body>
</html>

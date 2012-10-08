<%-- 
    Document   : Error
    Created on : 07-sep-2012, 2:10:01
    Author     : Guillermo
--%>
<%
    String mensaje = (request.getAttribute("mensaje")!=null)? (String)request.getAttribute("mensaje"):"Error !!";        
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <link rel="stylesheet" href="css/styles.css" type="text/css" >
    </head>
    <body>
        <div id='header'>
            <div id='login'>                
            </div>
            <div id='banner'>
                <img src='imagenes/logo_unac.jpg' width='282' height='91' alt='logo_unac'/>
            </div>
        </div>
        <div id='content' style='text-align:center'>
            <h1>Validación del Login </h1>
            <p class='mensajeT1'><%=mensaje%></p><br>
            <a href='/ProyectoEjemploPortalUNACV6'>Home</a>
        </div>
    </body>
</html>

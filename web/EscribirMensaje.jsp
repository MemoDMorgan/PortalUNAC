<%-- 
    Document   : EscribirMensaje
    Created on : 21-oct-2012, 18:13:50
    Author     : Guillermo
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="true"%>
<%@page import="Entidades.*"%>
<%
    //obtener datos de la sesión sino existen rediridir a index para login
    String nombre = "";
    String perfil = "";
    String sperfil="";
    HttpSession sesionOk = request.getSession();
    if (sesionOk.getAttribute("usuario") == null) {
        request.setAttribute("error", "Es obligatorio identificarse");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    } else {
        nombre = (String) sesionOk.getAttribute("usuario");
        perfil = (String) sesionOk.getAttribute("perfil");
        sperfil=(String) sesionOk.getAttribute("sperfil");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
        <title>JSP Page</title>        
        <link rel="stylesheet" type="text/css" href="css/jMenu.jquery.css" media="screen" /> 
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.23.custom.css"/> 
        <link rel="stylesheet" href="css/styles.css" type="text/css" >
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
                <a href="ControladorUsuarios?accion=salir"><img src="imagenes/salir.gif" width="48" height="48" alt="salir"/></a>
            </div>
            <div id='banner'>
                <img src='imagenes/logo_unac.jpg' width='282' height='91' alt='logo_unac'/>
            </div>
        </div>
        <jsp:include page="menuEstudiante.jsp"/>        
        <br>
        <hr>        
        <div id="Main-Mensaje">            
            <h2>Escibir Mensaje</h2>
            <form method="post" name="frm" id="frmIngreso" action="ControladorUsuarios">                
                <div class="campo">
                    <label class="etiquetaNew">Para:</label>                    
                    <select name="listaProfesores">
                        <option value="0" selected>
                            [Seleccione el Profesor]
                        </option>
                        <%List ListaProfesores = (List) request.getAttribute("Profesores");//se recibe el arreglo                            
                            Usuario oUser = null;//se define un objeto 
                            String nombreProfe = "";
                            for (int i = 0; i < ListaProfesores.size(); i++) {
                                oUser = (Usuario) ListaProfesores.get(i);
                                nombreProfe = oUser.getNombre() + " " + oUser.getApellido();%>
                        <option value="<%=oUser.getEmail()%>">
                            <%=nombreProfe%>
                        </option>
                        <%}%>
                    </select>
                </div>
                <div class="campo">
                    <label class="etiquetaNew">Mensaje:</label>
                    <textarea name="txtMensaje" rows="5" cols="20" placeholder="Escriba su mensaje..." required></textarea>
                </div>                    
                <div style="left: 30%;position: relative">
                    <br>
                    <input type="submit" value="Enviar" name="btnModificar" id="btnModificar" class="boton"/>
                    <input type="submit" value="Cancelar" name="btnCancelar" id="btnCancelarHome" class="boton"/>
                    <br>
                    <br>                                            
                </div>        
                <input type="hidden" name="accion" value="guardarMensaje" />    
            </form>
        </div>
        <hr>
    </body>
</html>

<%-- 
    Document   : NewEditUsuario
    Created on : 11-oct-2012, 23:08:39
    Author     : Guillermo
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Entidades.*"%>
<%
//Parámetros de entrada
    Usuario e = (Usuario) request.getAttribute("usuario");

    String snombre = "";
    String sapellido = "";
    String semail = "";
    String sclave = "";
    String stelefono = "";
    String sgenero = "";
    int idperfil = -1;
    String sestado = "";

    String accion = "insertar";//por defecto es un nuevo registro
    String titulo="Nuevo Usuario";
    if (e != null) //si el usuario no es nulo significa que es modificación
    {
        snombre = e.getNombre();
        sapellido = e.getApellido();
        semail = e.getEmail();
        sclave = e.getClave();
        stelefono = e.getTelefono();
        sgenero = e.getGenero();
        idperfil = e.getIdPerfil();
        accion = "modificar";
        sestado = Integer.toString(e.getEstado());
        titulo="Modificar Usuario";
    }   
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
        <div id="Main-formulario2">            
            <h2><%=titulo%></h2>
            <form method="post" name="frm" id="frmRegistro" action="ControladorUsuarios">
                <div class="campo">
                    <label class="etiquetaNew">Nombre:</label>
                    <input type="text" class="texto" id="txtNombre" name="txtNombre" value="<%=snombre%>"/>
                </div>
                <div class="campo">
                    <label class="etiquetaNew">Apellido:</label>
                    <input type="text" class="texto" id="txtApellido" name="txtApellido" value="<%=sapellido%>"/>
                </div>
                <div class="campo2">
                    <label class="etiquetaNew">Correo Electrónico:</label>                    
                    <input type="text" class="texto" id="txtCorreo" name="txtCorreo" value="<%=semail%>" <%=e != (null) ? " disabled" : ""%>/>
                </div>
                <div class="campo">
                    <label class="etiquetaNew">Contraseña:</label>
                    <input type="text" class="texto" id="txtPass" name="txtPass" value="<%=sclave%>"/>
                </div>                
                <div class="campo">
                    <label class="etiquetaNew">Teléfono:</label>
                    <input type="text" class="texto" id="txtTel" name="txtTel" value="<%=stelefono%>"/>
                </div>                    
                <div class="campo">
                    <label class="etiquetaNew">Género:</label>                    
                    <select name="cbgenero" class="texto" id="cbgenero">
                        <option value="2">Seleccione el Género</option>
                        <%
                            if (accion.equals("modificar")) {
                                if (sgenero.equals("0")) {%>
                        <option value="0" selected>Femenino</option>
                        <option value="1">Masculino</option>
                        <%} else {
                        %>
                        <option value="0" >Femenino</option>
                        <option value="1" selected>Masculino</option>
                        <%}
                        } else { //es un nuevo registro
%>
                        <option value="2">Seleccione el Género</option>
                        <option value="0">Femenino</option>
                        <option value="1">Masculino</option>
                        <%}//fin accion
%>
                    </select>
                </div>
                <div class="campo">
                    <label class="etiquetaNew">Perfil:</label>                    
                    <select name="listaPerfil">
                        <option value="0" selected>
                            Seleccionar el Perfil
                        </option>
                        <%List ListaPerfiles = (List) request.getAttribute("Perfiles");//se recibe el arreglo
                            System.out.print("Cargando Perfiles...");
                            Perfil oPerfil = null;//se define un objeto 
                            for (int i = 0; i < ListaPerfiles.size(); i++) {
                                        oPerfil = (Perfil) ListaPerfiles.get(i);%>
                        <option value="<%=oPerfil.getId()%>" <%=idperfil == (oPerfil.getId()) ? " selected" : ""%>>
                            <%=oPerfil.getNombre()%>
                        </option>
                        <%}%>
                    </select>
                </div>                        
                <div class="campo">
                    <label class="etiquetaNew">Estado:</label>                    
                    <select name="cbestado" class="texto" id="cbestado">
                        <option value="2">Seleccione el Estado</option>
                        <%
                            if (accion.equals("modificar")) {
                                if (sestado.equals("0")) {%>
                        <option value="0" selected>Inactivo</option>
                        <option value="1">Activo</option>
                        <%} else {
                        %>
                        <option value="0" >Inactivo</option>
                        <option value="1" selected>Activo</option>
                        <%}
                        } else { //es un nuevo registro
%>
                        <option value="2">Seleccione el Estado</option>
                        <option value="0">Inactivo</option>
                        <option value="1">Activo</option>
                        <%}//fin accion
%>
                    </select>
                </div>                        
                <div style="left: 40%;position: relative">
                    <br>
                    <input type="submit" value="Guardar" name="btnModificar" id="btnModificar" class="boton"/>
                    <br>
                    <br>                                            
                </div>
                <input type="hidden" name="accion" value="<%=accion%>" />
                <input type="hidden" name="id" value="<%=semail%>" />
            </form>
        </div>
        <hr>
    </body>
</html>

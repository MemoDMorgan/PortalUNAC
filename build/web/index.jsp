<%-- 
    Document   : index
    Created on : 25-ago-2012, 17:03:53
    Author     : Guillermo
--%>

<%
    String nombre = request.getParameter("tcorreo");
    Integer guardo = (Integer) request.getAttribute("guardoOK");
    String mensaje = "";
    String mensaje2 = "";
    String accion = "insertar";//la acción será un nuevo registro 
    if (nombre != null && guardo == null) {
        mensaje = "Usuario y/o Contraseña incorrecta";
    }
    if (guardo != null) {
        mensaje2 = "Regitro ingresado exitosamente";
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Bienvenido al Portal UNAC</title>
        <script src = "js/jquery-1.8.0.min.js"></script>
        <script src = "js/myscripts.js"></script>
        <script src = "js/jquery-ui-1.8.23.custom.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.23.custom.css"/> 
        <link rel="stylesheet" href="css/styles.css" type="text/css" >
    </head>
    <body>
        <div id="header">
            <div id="login">
                <form action="Validar" method="POST" id="formLogin">    
                    <div id="pass">                                    
                        <label>Contraseña</label><br>
                        <input type="password" id="tPass" name="tPass" />&nbsp;
                        <input type="button" value="Entrar" name="btnIn" class="boton" id="btnIn"/><br>
                        <a href="#" id="recPass">¿Has olvidado tu contraseña?</a>                   
                        <br>
                        <b><font color="RED"><%=mensaje%></font></b>
                    </div>
                    <div id="correo">
                        <label>Correo Electrónico</label><br>
                        <input type="text" id="tcorreo" name="tcorreo" /><br>
                        <a href="Validar?accion=0">Ir a la UNAC</a>
                    </div>                        
                </form>
            </div> 
            <div id="banner">
                <img src="imagenes/logo_unac.jpg" width="282" height="91" alt="logo_unac"/>
            </div>        
        </div>
        <div id="content">
            <div id="Main-formulario">
                <h2>Registrese en nuestro portal</h2>
                <form method="post" name="frm" id="frmRegistro" action="ControladorUsuarios">
                    <div class="campo">
                        <label class="etiqueta">Nombre:</label>
                        <input type="text" class="texto" id="txtNombre" name="txtNombre" value=""/>
                    </div>
                    <div class="campo">
                        <label class="etiqueta">Apellido:</label>
                        <input type="text" class="texto" id="txtApellido" name="txtApellido" value="" />                        
                    </div>
                    <div class="campo2">
                        <label class="etiqueta">Correo Electrónico:</label>
                        <input type="text" class="texto" id="txtCorreo" name="txtCorreo" value="" />
                    </div>
                    <div class="campo">
                        <label class="etiqueta">Contraseña:</label>
                        <input type="password" class="texto" id="txtPass" name="txtPass" value="" />
                    </div>
                    <div class="campo2">
                        <label class="etiqueta">Repita la Contraseña:</label>
                        <input type="password" class="texto" id="txtRePass" name="txtRePass" value="" />
                    </div>
                    <div class="campo">
                        <label class="etiqueta">Teléfono:</label>
                        <input type="text" class="texto" id="txtTel" name="txtTel" value="" />
                    </div>                    
                    <div class="campo">
                        <label class="etiqueta">Género:</label>
                        <select name="cbgenero" class="texto" id="cbgenero">
                            <option value="2">Seleccione el Género</option>
                            <option value="0">Femenino</option>
                            <option value="1">Masculino</option>
                        </select>
                    </div>
                    <div id="btnReg">
                        <br>
                        <input type="submit" value="Registrarse" name="btnRegistrar" id="btnRegistrar" class="boton"/>
                        <br>
                        <br>                        
                        <b><font color="RED"><%=mensaje2%></font></b>
                    </div>
                    <input type="hidden" name="accion" value="<%=accion%>" />
                </form>

                <div id="capaError">
                    Información mal ingresada, por favor corriga lo siguiente:<br>                    
                    <p id="perror"></p>
                </div>
            </div> 
            <div id="Main-texto">
                <!--<h3>Misión</h3>
                <p>La Corporación Universitaria Adventista reconoce a Dios como Creador, Redentor y Sustentador del hombre y del universo; y en armonía con los principios filosóficos y educativos inspirados por el Espíritu Santo, evidenciados en la naturaleza, ejemplificados por Jesucristo, expuestos en las Sagradas Escrituras y tal como los profesa la iglesia Adventista del Séptimo Día, nuestra Institución declara como su misión propiciar y fomentar una significativa relación del hombre con Dios por medio del trabajo en las diferentes disciplinas del conocimiento. En consecuencia, la Corporación se define como una institución universitaria sin ánimo de lucro que desarrolla su labor educativa enmarcada en el servicio a Dios, la comunidad adventista y la sociedad en general.
                    El trabajo del conocimiento se fundamenta en tres pilares: la formación integral, la cultura investigativa y la excelencia en el servicio,en el que el hombre es el agente principal del proceso educativo que persigue el desarrollo armónico de los aspectos físicos, mentales sociales y espirituales. La misión se desarrollará en procura de los altos niveles de calidad educativa, a través de un personal calificado con un profundo sentido de compromiso, apoyado en el uso óptimo de los recursos físicos, financieros y tecnológicos.
                </p>
                <h3>Visión</h3>
                <p>
                    La Corporación Universitaria Adventista será una universidad completamente accesible a la Iglesia Adventista del Séptimo Día en particular, y a la comunidad en general, con el propósito de preparar ciudadanos para este mundo y para la eternidad.
                </p>!-->                
                <div id="tabs">
                    <ul>
                        <li><a href="#tabs-1">Misión</a></li>
                        <li><a href="#tabs-2">Visión</a></li>
                        <li><a href="#tabs-3">Perfiles Institucionales</a></li>
                    </ul>
                    <div id="tabs-1">
                        <p>La Corporación Universitaria Adventista reconoce a Dios como Creador, Redentor y Sustentador del hombre y del universo; y en armonía con los principios filosóficos y educativos inspirados por el Espíritu Santo, evidenciados en la naturaleza, ejemplificados por Jesucristo, expuestos en las Sagradas Escrituras y tal como los profesa la iglesia Adventista del Séptimo Día, nuestra Institución declara como su misión propiciar y fomentar una significativa relación del hombre con Dios por medio del trabajo en las diferentes disciplinas del conocimiento. En consecuencia, la Corporación se define como una institución universitaria sin ánimo de lucro que desarrolla su labor educativa enmarcada en el servicio a Dios, la comunidad adventista y la sociedad en general.<br><br>
                            El trabajo del conocimiento se fundamenta en tres pilares: la formación integral, la cultura investigativa y la excelencia en el servicio,en el que el hombre es el agente principal del proceso educativo que persigue el desarrollo armónico de los aspectos físicos, mentales sociales y espirituales. La misión se desarrollará en procura de los altos niveles de calidad educativa, a través de un personal calificado con un profundo sentido de compromiso, apoyado en el uso óptimo de los recursos físicos, financieros y tecnológicos.
                        </p>
                    </div>
                    <div id="tabs-2">
                        <p>
                            La Corporación Universitaria Adventista será una universidad completamente accesible a la Iglesia Adventista del Séptimo Día en particular, y a la comunidad en general, con el propósito de preparar ciudadanos para este mundo y para la eternidad.
                        </p>
                    </div>
                    <div id="tabs-3">
                        <p>
                            <ol type="1">
                                <li><em>Una sólida formación cristiana.</em> El egresado es una persona íntegra, con altos ideales fundamentados en valores, con capacidad para servir a la sociedad utilizando los conocimientos adquiridos y aplicando los principios recibidos en su Alma Máter</li><br>
                                <li><em>Competencia profesional y laboral.</em> El egresado es un profesional de excelencia. Con competencias cognitivas y laborales que le permiten desempeñarse profesionalmente en las áreas del conocimiento para las cuales fue formado. Con capacidad para crear nuevas propuestas de mejoramiento y alternativas de solución a los problemas que se presenten.</li><br>
                                <li><em>Un compromiso con el servicio.</em> El egresado es un líder servidor. Reconoce en cada ser humano una criatura formada a imagen de Dios y alguien por quien Cristo murió. Por lo tanto, desarrolla una labor social enfocada en el servicio a los demás. </li><br>
                                <li><em>Cuidado personal y de la salud</em>. El egresado reconoce la importancia de mantener su salud física y mental, por ende practica un estilo de vida que incluye la sana alimentación, hábitos de higiene, el ejercicio físico, la recreación y el descanso.</li><br>
                            </ol>
                        </p>
                    </div>
                </div>
            </div>
        </div>
        <div id="dialog">
            <p id="ok"></p>
        </div> 
    </body>
</html>

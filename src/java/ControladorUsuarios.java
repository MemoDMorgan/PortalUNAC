/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Entidades.Mensaje;
import Entidades.Perfil;
import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilidades.Conexion;

/**
 *
 * @author Guillermo
 */
@WebServlet(name = "ControladorUsuarios", urlPatterns = {"/ControladorUsuarios"})
public class ControladorUsuarios extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Conexion conBD = null;//objeto conexión

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        //El parametro acción determina que se debe hacer, 
        //debe ser enviado desde el JSP
        String accion = request.getParameter("accion");
        //registrar nuevo usuario en el portal
        if (accion.equals("insertar")) {
            insertar(request, response);
        } else if (accion.equals("listar")) {
            //ver todos los usuarios del portal
            todos(request, response);
        } else if (accion.equals("eliminar")) {
            //eliminar un usuario
            eliminar(request, response);
        } else if (accion.equals("editar")) {
            //Solicita pagina para editar
            buscar_for_editar(request, response);
        } else if (accion.equals("modificar")) {
            //modificar los datos de un usuario
            modificar(request, response);
        } else if (accion.equals("nuevo")) {
            nuevo(request, response);//atender peticion para nuevo usuario
        } else if (accion.equals("salir")) { //salir de la aplicacion
            salir(request, response);//
        } else if (accion.equals("listarProfesores")) { //Listar los Profesores
            listarProfesores(request, response);//
        } else if (accion.equals("guardarMensaje")) { //Guardar el mensaje de un estudiante a un profe
            guardarMensaje(request, response);//
        } else if (accion.equals("contarMensajes")) { //contar los mensajes no leidos de un profe
            contarMensajes(request, response);//
        } else if (accion.equals("verMensajesNoRead")) { //ver los mensajes no leidos de un profe
            verMensajes(request, response, true);//
        } else if (accion.equals("listarMensajes")) { //ver los mensajes no leidos de un profe
            verMensajes(request, response, false);//
        } else {
            request.getRequestDispatcher("/Error.jsp").include(request, response);
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void insertar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String snombre = request.getParameter("txtNombre");
        String sapellido = request.getParameter("txtApellido");
        String sclave = request.getParameter("txtPass");
        String stelefono = request.getParameter("txtTel");
        String sgenero = request.getParameter("cbgenero");
        String semail = request.getParameter("txtCorreo");

        //si no llega el estado y el perfil por defecto es visitante y esta inhabilitado
        String nestado = request.getParameter("cbestado") == null ? "0" : request.getParameter("cbestado");
        String perfil = request.getParameter("listaPerfil") == null ? "4" : request.getParameter("listaPerfil"); //visitante


        RequestDispatcher vista;
        //Datos de la conexion
       /*
         * String driver = "com.mysql.jdbc.Driver"; String urlDB =
         * "jdbc:mysql://localhost/test"; String userBD = "userprueba"; String
         * passBD = "123456";
         */

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        int resultado = 0;//resultado de las inserción sql
        String sql = "";

        try {
            //CARGAR DRIVER
            //Class.forName(driver);

            //ESTABLECER CONEXION
            //con = DriverManager.getConnection(urlDB, userBD, passBD);
            con = conBD.getCConexion();
            System.out.println("Conectado ...");

            //Definición de Sentencia SQL
            sql = "INSERT INTO usuarios(semail,sclave,nestado,snombre,sapellido,stelefono,sgenero,"
                    + "nidPerfil) VALUES ('" + semail + "','" + sclave + "'," + nestado + ",'" + snombre + "','" + sapellido
                    + "','" + stelefono + "'," + sgenero + "," + perfil + ")";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeUpdate(sql);

            System.out.println(resultado);//numero de filas afectadas

            String paginaRedireccion = "";
            //si fue inserción del administrador o por un visitante en el portal redirigir a la pagina correcta
            if (request.getParameter("cbestado") == null) {
                request.setAttribute("guardoOK", resultado);
                vista = request.getRequestDispatcher("index.jsp");
                vista.forward(request, response);
            } else {
                request.setAttribute("mensaje", "Registro creado exitosamente !");
                todos(request, response);
            }

            /*
             * } catch (ClassNotFoundException ex) { System.out.println("No se
             * ha podido cargar el Driver de MySQL");
             * //request.getRequestDispatcher("/Error.jsp").include(request,
             * response);
             * //request.getRequestDispatcher("/Error.jsp").forward(request,
             * response);
             */
            response.sendRedirect("Error.jsp");
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        } finally {
            try {
                //Liberar recursos
                if (sentencia != null) {
                    sentencia.close();
                }
//                //cerrar conexion, se cierra ya en el destroy del servlet
//                if (con != null) {
//                    con.close();
//                }
            } catch (SQLException ex) {
                request.getRequestDispatcher("/Error.jsp").include(request, response);
            }
        }

    }

    private void todos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";
        try {
            con = conBD.getCConexion();
            //Definición de Sentencia SQL
            //sql = "SELECT semail,sclave,nestado,snombre,sapellido,stelefono,sgenero,nidPerfil FROM usuarios";
            sql = "SELECT semail,sclave,nestado,usuarios.snombre,sapellido,stelefono,sgenero,nidPerfil,"
                    + "(case WHEN nestado=0 THEN 'Inactivo' ELSE 'Activo' END) AS sestado,"
                    + "(case WHEN sgenero=0 THEN 'Femenino' ELSE 'Masculino' END) AS desgenero,"
                    + "perfiles.snombre "
                    + "FROM usuarios,perfiles "
                    + "where usuarios.nidPerfil=perfiles.idPerfil "
                    + "order by usuarios.snombre";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);

            //arreglo donde se gurdaran los usuarios encontrados en la BD
            ArrayList Usuarios = new ArrayList();
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                Usuario e = new Usuario(resultado.getString(1), resultado.getString(2),
                        resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),
                        resultado.getInt(8), resultado.getInt(3));
                //agregamos las descripciones al objeto.
                e.setDescripcionEstado(resultado.getString(9));
                e.setDescripcionGenero(resultado.getString(10));
                e.setDescripcionPerfil(resultado.getString(11));
                //Agregamos el estudiante al arrelo
                Usuarios.add(e);
            }
            // Agregar el arreglo de estudiantes a la solicitud
            request.setAttribute("usuarios", Usuarios);
            //redirigir la solicitu a la página JSP
            request.getRequestDispatcher("/ListaUsuarios.jsp").include(request, response);
            //cerrar la conexion
            //con.close();
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        int resultado = 0;//resultado de las filas borradas sql
        String sql = "";
        try {

            //ESTABLECER CONEXION
            con = conBD.getCConexion();
            System.out.println("Conectado a BD...");

            //OBTENER EL DATO A ELIMINAR
            String emailUsuario = request.getParameter("ID");

            //Definición de Sentencia SQL
            sql = "DELETE FROM USUARIOS WHERE semail='" + emailUsuario + "'";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeUpdate(sql);
            System.out.println("Borrado exitoso !");
            request.setAttribute("mensaje", "Registro borrado exitosamente !");
            //cerrar la conexion
            //con.close();

            //listar de nuevo los datos
            todos(request, response);

        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    /*
     * Método que busca la información de un usuario que será editada.
     */
    private void buscar_for_editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Datos de la conexion
        /*
         * String driver = "com.mysql.jdbc.Driver"; String urlDB =
         * "jdbc:mysql://localhost/test"; String userBD =
         * "userprueba";//userprueba String passBD = "123456";
         */

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";

        //Objeto Usuario, donde se guardará la información del registro a editar
        Usuario e = null;
        try {
            //CARGAR DRIVER
            // Class.forName(driver);
            //ESTABLECER CONEXION
            //con = DriverManager.getConnection(urlDB, userBD, passBD);
            con = conBD.getCConexion();
            System.out.println("Conectado ...");
            //OBTENER EL DATO A CONSULTAR
            String emailUsuario = request.getParameter("ID");

            //Definición de Sentencia SQL
            sql = "SELECT * FROM USUARIOS WHERE semail='" + emailUsuario + "'";
            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);

            // VER SI HAY RESULTADODOS
            while (resultado.next()) {
                e = new Usuario(resultado.getString(1), resultado.getString(2),
                        resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),
                        resultado.getInt(8), resultado.getInt(3));
                break; //debe haber un solo registro.
            }
            // Agregar el usuario a la solicitud
            request.setAttribute("usuario", e);

            //Agregamos los perfiles

            //Definición de Sentencia SQL
            sql = "SELECT idPerfil,snombre FROM perfiles ORDER BY snombre";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);

            //arreglo donde se guardaran los perfiles encontrados en la BD
            ArrayList perfiles = new ArrayList();
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                Perfil p = new Perfil(resultado.getString(2), resultado.getInt(1));
                //Agregamos el perfil (FILA) encontrado al arreglo
                perfiles.add(p);
            }
            // Agregar el arreglo de perfiles a la solicitud
            request.setAttribute("Perfiles", perfiles);

            //redirigir la solicitud a la página JSP
            request.getRequestDispatcher("/NewEditUsuario.jsp").include(request, response);
            //cerrar la conexion
            //con.close();
        /*
             * } catch (ClassNotFoundException ex) { System.out.println("No se
             * ha podido cargar el Driver de MySQL");
             * //request.getRequestDispatcher("/Error.jsp").forward(request,
             * response); response.sendRedirect("Error.jsp");
             */
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    private void modificar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Obtener los datos a modificar.
        String snombre = request.getParameter("txtNombre");
        String sapellido = request.getParameter("txtApellido");
        //el campo txtCorreo esta deshabilitado, no llega al formulario
        String semail = request.getParameter("id");
        String sclave = request.getParameter("txtPass");
        String stelefono = request.getParameter("txtTel");
        String sgenero = request.getParameter("cbgenero");
        String perfil = request.getParameter("listaPerfil");
        String sestado = request.getParameter("cbestado");

        RequestDispatcher vista;
        //Datos de la conexion
        /*
         * String driver = "com.mysql.jdbc.Driver"; String urlDB =
         * "jdbc:mysql://localhost/test"; String userBD = "userprueba"; String
         * passBD = "123456";
         */

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        String sql = "";

        /*
         * try { //CARGAR DRIVER Class.forName(driver); } catch (Exception e) {
         * System.out.println("No se ha podido cargar el Driver de MySql");
         * request.getRequestDispatcher("/Error.jsp").include(request,
         * response); }
         */
        try {
            //ESTABLECER CONEXION
            //con = DriverManager.getConnection(urlDB, userBD, passBD);
            con = conBD.getCConexion();
            System.out.println("Conectado ...");

            //Definición de Sentencia SQL
            sql = "UPDATE USUARIOS SET snombre='" + snombre + "', "
                    + "sapellido='" + sapellido + "', "
                    + "stelefono='" + stelefono + "', "
                    + "sclave='" + sclave + "', "
                    + "sgenero=b'" + sgenero + "', " //el tipo de dato es un bit (0 o 1)
                    + "nestado=b'" + sestado + "', " //el tipo de dato es un bit (0 o 1)
                    + "nidPerfil=" + perfil + " "
                    + "WHERE semail='" + semail + "'";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            int filasafectadas = sentencia.executeUpdate(sql);

            System.out.println("Actualizacion exitosa ! ...");
            request.setAttribute("mensaje", "Registro modificado exitosamente !");
            todos(request, response);
            //request.getRequestDispatcher("/DatosIngresados.jsp").include(request, response);

        } catch (SQLException ex) {
            request.getRequestDispatcher("/Error.jsp").include(request, response);
            System.out.println("No se ha podido Insertar, o el SQL esta mal formado " + sql);
        } finally {
            try {
                //Liberar recursos
                sentencia.close();
                //cerrar conexion
                //con.close();
            } catch (SQLException ex) {
                request.getRequestDispatcher("/Error.jsp").include(request, response);
            }
        }
    }

    private void salir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //destruir la sesion
        HttpSession sesionOk = request.getSession();//se obtiene la sesion creada
        sesionOk.invalidate();//se destruye la sesión
        response.sendRedirect("index.jsp");
    }

    /*
     * Metodo en el cual se buscan los perfiles en BD y se envían a la pagina
     * NewEditUsuario.jsp
     */
    private void nuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Datos de la conexion
        /*
         * String driver = "com.mysql.jdbc.Driver"; String urlDB =
         * "jdbc:mysql://localhost/test"; String userBD = "userprueba"; String
         * passBD = "123456";
         */

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        String sql = "";

        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        try {
            //CARGAR DRIVER
            //Class.forName(driver);
            //ESTABLECER CONEXION
            //con = DriverManager.getConnection(urlDB, userBD, passBD);
            con = conBD.getCConexion();
            System.out.println("Conectado ...");

            //Definición de Sentencia SQL
            sql = "SELECT idPerfil,snombre FROM perfiles ORDER BY snombre";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);

            //arreglo donde se guardaran los perfiles encontrados en la BD
            ArrayList perfiles = new ArrayList();
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                Perfil p = new Perfil(resultado.getString(2), resultado.getInt(1));
                //Agregamos el perfil (FILA) encontrado al arreglo
                perfiles.add(p);
            }
            // Agregar el arreglo de perfiles a la solicitud
            request.setAttribute("Perfiles", perfiles);
            //redirigir la solicitu a la página JSP
            request.getRequestDispatcher("/NewEditUsuario.jsp").forward(request, response);
            //cerrar la conexion
            //con.close();

            /*
             * } catch (ClassNotFoundException ex) { System.out.println("No se
             * ha podido cargar el Driver de MySQL");
             * //request.getRequestDispatcher("/Error.jsp").forward(request,
             * response); response.sendRedirect("Error.jsp");
             */
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        conBD = new Conexion();
        conBD.crearConexion();
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            conBD.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println("Error cerrando conexión... " + ex);
        }
    }

    private void listarProfesores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";
        try {
            con = conBD.getCConexion();
            //Definición de Sentencia SQL
            //sql = "SELECT semail,sclave,nestado,snombre,sapellido,stelefono,sgenero,nidPerfil FROM usuarios";
            sql = "SELECT semail,sclave,nestado,usuarios.snombre,sapellido,stelefono,sgenero,nidPerfil,"
                    + "(case WHEN nestado=0 THEN 'Inactivo' ELSE 'Activo' END) AS sestado,"
                    + "(case WHEN sgenero=0 THEN 'Femenino' ELSE 'Masculino' END) AS desgenero,"
                    + "perfiles.snombre "
                    + "FROM usuarios,perfiles "
                    + "where usuarios.nidPerfil=perfiles.idPerfil and usuarios.nidPerfil=3 "
                    + "order by usuarios.snombre";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);

            //arreglo donde se gurdaran los usuarios encontrados en la BD
            ArrayList Usuarios = new ArrayList();
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                Usuario e = new Usuario(resultado.getString(1), resultado.getString(2),
                        resultado.getString(4), resultado.getString(5), resultado.getString(6), resultado.getString(7),
                        resultado.getInt(8), resultado.getInt(3));
                //agregamos las descripciones al objeto.
                e.setDescripcionEstado(resultado.getString(9));
                e.setDescripcionGenero(resultado.getString(10));
                e.setDescripcionPerfil(resultado.getString(11));
                //Agregamos el profesor al arrelo
                Usuarios.add(e);
            }
            // Agregar el arreglo de profesores a la solicitud
            request.setAttribute("Profesores", Usuarios);
            //redirigir la solicitu a la página JSP
            request.getRequestDispatcher("/EscribirMensaje.jsp").include(request, response);
            //cerrar la conexion
            //con.close();
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    private void guardarMensaje(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String smensaje = request.getParameter("txtMensaje");
        String idPara = request.getParameter("listaProfesores");

        //obtener el id del usuario en sesión, el que envia el mensaje
        HttpSession sesionOk = request.getSession();
        String idDe = (String) sesionOk.getAttribute("email");

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        int resultado = 0;//resultado de las inserción sql
        String sql = "";
        Calendar fecha = Calendar.getInstance();
        //System.out.println(fecha.getTimeInMillis());
        try {
            con = conBD.getCConexion();

            //Definición de Sentencia SQL
            sql = "INSERT INTO mensajes(mensaje,de,para,estado,fecha) VALUES ('" + smensaje + "','"
                    + idDe + "','" + idPara + "',0,'" + new java.sql.Timestamp(fecha.getTimeInMillis()) + "')";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeUpdate(sql);
            request.setAttribute("mensaje", "Mensaje enviado correctamente !");
            request.getRequestDispatcher("Home.jsp").forward(request, response);
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql + ex.toString());
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        } finally {
            try {
                //Liberar recursos
                if (sentencia != null) {
                    sentencia.close();
                }
//                //cerrar conexion, se cierra ya en el destroy del servlet
//                if (con != null) {
//                    con.close();
//                }
            } catch (SQLException ex) {
                request.getRequestDispatcher("/Error.jsp").include(request, response);
            }
        }
    }

    private void contarMensajes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";
        //obtener el id del profesor de la sesión        
        String idpara = (String) request.getSession().getAttribute("email");
        try {
            con = conBD.getCConexion();
            //Definición de Sentencia SQL            
            sql = "SELECT count(id) as nMensajes "
                    + "FROM mensajes "
                    + "where para='" + idpara + "' and estado=0";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);
            int nMensajes = 0;
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                nMensajes = resultado.getInt("nMensajes");
                System.err.println("nmensajes=" + nMensajes);
            }
            //imprimir el numero de mensajes
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println(nMensajes);
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql + "-" + ex);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    private void verMensajes(HttpServletRequest request, HttpServletResponse response, boolean SoloNoLeidos) throws ServletException, IOException {
        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";
        //obtener el id del profesor de la sesión        
        String idpara = (String) request.getSession().getAttribute("email");
        try {
            con = conBD.getCConexion();
            //Definición de Sentencia SQL            
            sql = "SELECT id,mensaje,de,para,estado,concat(d.snombre,' ',d.sapellido) as sde"
                    + ",concat(p.snombre,' ',p.sapellido) as spara,fecha,"
                    + "(case WHEN estado=0 THEN 'No Leido' ELSE 'Leido' END) AS sestado "
                    + "FROM mensajes m,usuarios d, usuarios p "
                    + "WHERE m.de=d.semail and m.para=p.semail "
                    + "and para='" + idpara + "'";
            //ver solo los mensajes no leidos
            if (SoloNoLeidos) {
                sql += " and estado=0";
            }
            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(sql);
            //arreglo donde se gurdaran los usuarios encontrados en la BD
            ArrayList Mensajes = new ArrayList();
            while (resultado.next()) //si el resultado tiene datos empezar a guardarlos.
            {
                Mensaje e = new Mensaje(resultado.getInt(1), resultado.getInt(5), resultado.getString(2), resultado.getString(3), resultado.getString(4));
                //agregamos las descripciones al objeto.  
                //ojo con las fechas nulas
                e.setNombreDe(resultado.getString(6));
                e.setNombrePara(resultado.getString(7));
                e.setDescripcionEstado(resultado.getString(9));
                System.out.println(resultado.getTimestamp(8));
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(resultado.getTimestamp(8).getTime());
                e.setFechaMensaje(c);
                //Agregamos el profesor al arrelo
                Mensajes.add(e);
            }
            // Agregar el arreglo de profesores a la solicitud
            request.setAttribute("Mensajes", Mensajes);

            //actualizar los mensajes a leidos
            sql = "UPDATE mensajes SET estado=1 "
                    + "WHERE estado=0 and para='" + idpara + "'";           

            //Ejecutar sentencia
            sentencia = con.createStatement();
            int filasafectadas = sentencia.executeUpdate(sql);
            System.out.println("Actualizacion exitosa ! ..." + filasafectadas);

            //redirigir la solicitu a la página JSP
            request.getRequestDispatcher("/ListarMensajes.jsp").include(request, response);
            //cerrar la conexion
            //con.close();
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }
}

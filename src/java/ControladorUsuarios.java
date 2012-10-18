/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
        String semail = request.getParameter("txtCorreo");
        String sclave = request.getParameter("txtPass");
        String stelefono = request.getParameter("txtTel");
        String sgenero = request.getParameter("cbgenero");
        String perfil = "4"; //visitante

        RequestDispatcher vista;
        //Datos de la conexion
        String driver = "com.mysql.jdbc.Driver";
        String urlDB = "jdbc:mysql://localhost/test";
        String userBD = "userprueba";
        String passBD = "123456";

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        int resultado = 0;//resultado de las inserción sql
        String sql = "";

        try {
            //CARGAR DRIVER
            Class.forName(driver);

            //ESTABLECER CONEXION
            con = DriverManager.getConnection(urlDB, userBD, passBD);
            System.out.println("Conectado ...");

            //Definición de Sentencia SQL
            sql = "INSERT INTO usuarios(semail,sclave,nestado,snombre,sapellido,stelefono,sgenero,"
                    + "nidPerfil) VALUES ('" + semail + "','" + sclave + "',0,'" + snombre + "','" + sapellido
                    + "','" + stelefono + "'," + sgenero + "," + perfil + ")";

            //Ejecutar sentencia
            sentencia = con.createStatement();
            resultado = sentencia.executeUpdate(sql);

            System.out.println(resultado);//numero de filas afectadas
            request.setAttribute("guardoOK", resultado);
            vista = request.getRequestDispatcher("index.jsp");
            vista.forward(request, response);
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar el Driver de MySQL");
            //request.getRequestDispatcher("/Error.jsp").include(request, response);
            //request.getRequestDispatcher("/Error.jsp").forward(request, response);
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
                //cerrar conexion
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                request.getRequestDispatcher("/Error.jsp").include(request, response);
            }
        }

    }

    private void todos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Datos de la conexion
        String driver = "com.mysql.jdbc.Driver";
        String urlDB = "jdbc:mysql://localhost/test";
        String userBD = "userprueba";
        String passBD = "123456";

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";
        try {
            //CARGAR DRIVER
            Class.forName(driver);

            //ESTABLECER CONEXION
            con = DriverManager.getConnection(urlDB, userBD, passBD);
            System.out.println("Conectado ...");

            //Definición de Sentencia SQL
            sql = "SELECT semail,sclave,nestado,snombre,sapellido,stelefono,sgenero,nidPerfil FROM usuarios";

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
                //Agregamos el estudiante al arrelo
                Usuarios.add(e);
            }
            // Agregar el arreglo de estudiantes a la solicitud
            request.setAttribute("usuarios", Usuarios);
            //redirigir la solicitu a la página JSP
            request.getRequestDispatcher("/ListaUsuarios.jsp").include(request, response);
            //cerrar la conexion
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar el Driver de MySql");
            //request.getRequestDispatcher("/Error.jsp").include(request, response);
            //request.getRequestDispatcher("/Error.jsp").forward(request, response);
            response.sendRedirect("Error.jsp");
        } catch (SQLException ex) {
            System.out.println("No se ha podido establecer la conexión, o el SQL esta mal formado " + sql);
            request.getRequestDispatcher("/Error.jsp").forward(request, response);
        }
    }

    private void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Datos de la conexion
        String driver = "com.mysql.jdbc.Driver";
        String urlDB = "jdbc:mysql://localhost/test";
        String userBD = "userprueba";//userprueba
        String passBD = "123456";

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        int resultado = 0;//resultado de las filas borradas sql
        String sql = "";
        try {
            //CARGAR DRIVER
            Class.forName(driver);

            //ESTABLECER CONEXION
            con = DriverManager.getConnection(urlDB, userBD, passBD);
            System.out.println("Conectado ...");

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
            con.close();

            //listar de nuevo los datos
            todos(request, response);

        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar el Driver de MySql");
            //request.getRequestDispatcher("/Error.jsp").forward(request, response);
            response.sendRedirect("Error.jsp");
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
        String driver = "com.mysql.jdbc.Driver";
        String urlDB = "jdbc:mysql://localhost/test";
        String userBD = "userprueba";//userprueba
        String passBD = "123456";

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        ResultSet resultado = null;//Objeto para obtener los resultados de las consultas sql
        String sql = "";

        //Objeto Usuario, donde se guardará la información del registro a editar
        Usuario e = null;
        try {
            //CARGAR DRIVER
            Class.forName(driver);
            //ESTABLECER CONEXION
            con = DriverManager.getConnection(urlDB, userBD, passBD);
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

            //redirigir la solicitud a la página JSP
            request.getRequestDispatcher("/NewEditUsuario.jsp").include(request, response);
            //cerrar la conexion
            con.close();
        } catch (ClassNotFoundException ex) {
            System.out.println("No se ha podido cargar el Driver de MySQL");
            //request.getRequestDispatcher("/Error.jsp").forward(request, response);
            response.sendRedirect("Error.jsp");
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
        String perfil = request.getParameter("txtPerfil");
        String sestado = request.getParameter("txtEstado");

        RequestDispatcher vista;
        //Datos de la conexion
        String driver = "com.mysql.jdbc.Driver";
        String urlDB = "jdbc:mysql://localhost/test";
        String userBD = "userprueba";
        String passBD = "123456";

        //Objetos para manipular la conexion y los datos
        Connection con = null;//Objeto para la conexion
        Statement sentencia = null;//Objeto para definir y ejecutar las consultas sql
        String sql = "";

        try {
            //CARGAR DRIVER
            Class.forName(driver);
        } catch (Exception e) {
            System.out.println("No se ha podido cargar el Driver de MySql");
            request.getRequestDispatcher("/Error.jsp").include(request, response);
        }
        try {
            //ESTABLECER CONEXION
            con = DriverManager.getConnection(urlDB, userBD, passBD);
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
                con.close();
            } catch (SQLException ex) {
                request.getRequestDispatcher("/Error.jsp").include(request, response);
            }
        }
    }

    private void nuevo(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void salir(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

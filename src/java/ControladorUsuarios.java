/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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
        String userBD = "usuarioPrueba";
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
}

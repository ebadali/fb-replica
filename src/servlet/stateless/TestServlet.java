/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet.stateless;

import dao.LinkDao;
import dao.LinkDaoBean;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import container.Data;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author milandobrota
 */  
@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends HttpServlet {
    
    @EJB
    private LinkDao linkDao;
    
    
    private DataSource dataSource;
    private Connection connection;
    private Statement statement;
     
    public void init() throws ServletException {
        try {
        	   
            // Get DataSource
//            Context initContext  = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//            dataSource = (DataSource)envContext.lookup("jdbc/testdb");
            Context ctx = new InitialContext(); 
            
            dataSource = (DataSource)ctx.lookup("java:/comp/env/jdbc/testdb");
             
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(linkDao.test());
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TestServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
         
        ResultSet resultSet = null;  
        try { 
            // Get Connection and Statement
            connection = dataSource.getConnection();
            statement = connection.createStatement();
//            String query = "SELECT date('now')"; 
            String query = "select * from test";//+Data.USERS_TABLE;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try { if(null!=resultSet)resultSet.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=statement)statement.close();} catch (SQLException e) 
            {e.printStackTrace();}
            try { if(null!=connection)connection.close();} catch (SQLException e) 
            {e.printStackTrace();}
        }
    }
    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.PersonDao;
import dao.UserBean;
import dao.UserDAO;
import entity.Person;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import container.Data;

/**
 *
 * @author milandobrota
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

	@EJB
	private PersonDao personDao;

	private ServletContext ctx;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ctx = getServletConfig().getServletContext();
		request.setAttribute("error", "");
		HttpSession session = request.getSession(false);
		Integer personId ;//= (Integer) session.getAttribute(Data.COOKIE_USERID);
		String email = request.getParameter(Data.email), password = request.getParameter(Data.password);
		// Check if cookie exists
		if (session != null && session.getAttribute("personId") != null) {
			Integer sessionId = (Integer) session.getAttribute("personId");
			response.sendRedirect("wall?ownerId=" + sessionId);
		}

		else if (email == null && password == null) {
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} 
		else if ( session.getAttribute(Data.COOKIE_USERID) == null 
				&& email != null && password != null) {

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {

				Person user = UserDAO._instance.login(email, password);
				if (user == null) {
					request.setAttribute("error", "Email/password combination invalid.");
					request.getRequestDispatcher("index.jsp").forward(request, response);
					// response.sendRedirect("index.jsp");
				} else {
					session.setAttribute(Data.COOKIE_USERID, user.getId());
					session.setAttribute("personId", user.getId());
					session.setAttribute("personName", user.getFirstName() + " " + user.getLastName());
					response.sendRedirect("wall?ownerId=" + user.getId());
				}
			} catch (Exception ex) {
				ex.printStackTrace();

			} finally {
				out.close();
			}
		} else {
			personId = (Integer) session.getAttribute(Data.COOKIE_USERID);
			response.sendRedirect("wall?ownerId=" + personId);
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("---------------Do Get Called");

		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("---------------Do Post Called");
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

	// Custom Methods :

	public void TryLogin(HttpServletRequest request, HttpServletResponse response) {
		try {

			UserBean user = new UserBean();
			user.setUserName(request.getParameter("email"));
			user.setPassword(request.getParameter("password"));
			user = UserDAO._instance.login(user);

			if (user.isValid()) {

				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", user);
				response.sendRedirect("/wall.jsp"); // logged-in page
			} else {
				request.setAttribute("error", "Email/password combination invalid.");
				ctx.getRequestDispatcher("/login.jsp").forward(request, response);
				// request.setAttribute("error", "Invalid User Name Password");
				// response.getWriter().print("Sorry, username or password
				// error!");
				// response.sendRedirect("Signup.jsp"); //error page
			}
		}

		catch (Throwable theException) {
			System.out.println(theException);
			theException.printStackTrace();
		} // - See more at:
			// http://www.codemiles.com/jsp-examples/login-using-jsp-servlets-and-database-following-mvc-t10898.html#sthash.erjdhXpE.dpuf
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.PersonDao;
import dao.UserDAO;
import entity.Person;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author milandobrota
 */
@WebServlet(name = "AllPeopleServlet", urlPatterns = { "/all_people" })
public class AllPeopleServlet extends HttpServlet {

	@EJB
	private PersonDao personDao;

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
		response.setContentType("text/html;charset=UTF-8");
		ServletContext ctx = getServletConfig().getServletContext();
		HttpSession session = request.getSession(false);
		Integer personId;// = (Integer) session.getAttribute("personId");
		if (session == null || session.getAttribute("personId") == null) {
			request.setAttribute("error", "You are Not Logged In");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;

		}

		UserDAO.getInstace(getServletContext().getRealPath("/WEB-INF/"));
		
		PrintWriter out = response.getWriter();
		try {
			personId = (Integer) session.getAttribute("personId");
			List<Person> people = null;
			String friendId = request.getParameter("friendId");
			if (friendId != null) {
				UserDAO._instance.sendFriendRequest(Integer.valueOf(friendId), personId);
			}

			String orderParam = request.getParameter("order");
			boolean order = (orderParam == null || !orderParam.equals("DESC"));
			String orderBy = request.getParameter("orderBy");
			
//			if (orderBy != null && orderBy.equals("place")) {
//				people = UserDAO._instance.findAllSortedByPlace(order);
//			} else if (orderBy != null && orderBy.equals("dateOfBirth")) {
//				people = UserDAO._instance.findAllSortedByDateOfBirth(order);
//			} else {
//				people = UserDAO._instance.findAllSortedByName(order, personId);
//			}

			people = UserDAO._instance.findAllSortedByName(order, personId);
			request.setAttribute("people", people);
			ctx.getRequestDispatcher("/everybody.jsp").forward(request, response);
			

			/*
			 * TODO output your page here out.println("<html>");
			 * out.println("<head>"); out.println(
			 * "<title>Servlet AllPeopleServlet</title>");
			 * out.println("</head>"); out.println("<body>"); out.println(
			 * "<h1>Servlet AllPeopleServlet at " + request.getContextPath () +
			 * "</h1>"); out.println("</body>"); out.println("</html>");
			 */
		} finally {
			out.close();
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

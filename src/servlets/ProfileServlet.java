/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.FriendRequestDao;
import dao.PersonDao;
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

/**
 *
 * @author milandobrota
 */
@WebServlet(name = "ProfileServlet", urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {

	@EJB
	private PersonDao personDao;

	@EJB
	private FriendRequestDao friendRequestDao;

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

		PrintWriter out = response.getWriter();
		ServletContext ctx = getServletConfig().getServletContext();

		HttpSession session = request.getSession(false);
		Integer personId;// = (Integer) session.getAttribute("personId");
		if (session == null || session.getAttribute("personId") == null) {
			request.setAttribute("error", "You are Not Logged In");
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;

		}

		UserDAO.getInstace(getServletContext().getRealPath("/WEB-INF/"));

		personId = (Integer) session.getAttribute("personId");

		// if(request.getParameter("personId") != null) {
		// personId = Integer.parseInt(request.getParameter("personId"));
		// }
		int userId = personId;
		if(request.getParameter("ownerId") != null)
			userId =Integer.parseInt( request.getParameter("ownerId"));
		Person person = UserDAO._instance.GetPerson(userId);
		// person.setIsFriend(true);
		// friendRequestDao.areFriends(
		// (Integer)session.getAttribute("personId"),
		// personId)
		// );
		System.out.println(person.getPicture()+"--------------- ");
		request.setAttribute("person", person);
		request.setAttribute("ownerId", userId);
//		boolean isFriend = friendRequestDao.areFriends(idFromSession, personId);
//
//		boolean isUnanswered = friendRequestDao.areUnanswered(idFromSession, personId);
//
//		boolean isSelf = personId.equals(idFromSession);
//
//		String friendRequestFragment = "";
//		if (!isSelf && !isFriend && !isUnanswered)
//			friendRequestFragment = "<a href='create_friend_request?targetId=" + personId + "'>Add friend</a>";
//		if (!isSelf && !isFriend && isUnanswered)
//			friendRequestFragment = "Friendship pending";
//		if (!isSelf && isFriend)
//			friendRequestFragment = "<a href='remove_friend_request?targetId=" + personId + "'>Remove friend</a>";
//		if (isSelf)
//			friendRequestFragment = "This is you!";

//		request.setAttribute("friendRequestFragment", friendRequestFragment);
//		request.setAttribute("isSelf", isSelf);
		ctx.getRequestDispatcher("/personalInfo.jsp").forward(request, response);

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

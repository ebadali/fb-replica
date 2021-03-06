/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.PostDao;
import dao.UserDAO;
import entity.Post;
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
@WebServlet(name = "WallServlet", urlPatterns = { "/wall" })
public class WallServlet extends HttpServlet {

	@EJB
	private PostDao postDao;

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
		
		personId = (Integer) session.getAttribute("personId");

		Integer ownerId = null, postId;
		boolean shareFlag = false;

		
		
		
		if (request.getParameter("postId") != null && request.getParameter("share") != null ) {
			//ownerId =  Integer.parseInt(request.getParameter("ownerId").trim());
			postId = Integer.parseInt(request.getParameter("postId").trim());
			shareFlag = Boolean.parseBoolean(request.getParameter("share").trim());
			boolean success = false;
			if (shareFlag) {
				success = UserDAO._instance.sharePost(postId, personId);
			} else {
				 UserDAO._instance.addALike(postId, personId);
			}

		} 
		List<Post> posts = null;
		if (request.getParameter("filter") != null && request.getParameter("filter").equals("popular")) {
			posts = UserDAO._instance.PreferentialSearch();// postDao.topTenFor(ownerId);
		} else {
			posts = UserDAO._instance.findAllPost();// postDao.wallFor(ownerId);
		}
		request.setAttribute("posts", posts);
		// sending this so we can use it in links
		request.setAttribute("wallOwnerId", ownerId);  // Not being used so dont worry about context leaking.
		ctx.getRequestDispatcher("/wallposts.jsp").forward(request, response);

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

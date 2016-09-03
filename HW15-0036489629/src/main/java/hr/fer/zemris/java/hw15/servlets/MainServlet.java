package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.Authentication;
import hr.fer.zemris.java.hw15.SHACrypto;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.form.FormBlogUser;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The Servlet that handles the author index and logging in..
 *
 * @author Juraj Juričić
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5917364708362977743L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<BlogUser> users = DAOProvider.getDao().getAllUsers();

		req.setAttribute("authors", users);

		req.getRequestDispatcher("/WEB-INF/pages/main.jsp")
				.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getParameter("submit") == null) {
			doGet(req, resp);
		}

		FormBlogUser fbu = new FormBlogUser();
		String nick = req.getParameter("nick");
		String password = req.getParameter("password");

		fbu.setNick(nick);

		if (nick == null || nick.isEmpty()){
			fbu.getErrors().put("nick", "Nick cannot be empty.");
		}
		if (password == null || password.isEmpty()){
			fbu.getErrors().put("password", "Password cannot be empty.");
		}

		BlogUser user = null;
		if (fbu.getErrors().isEmpty()){
			//check user
			user = DAOProvider.getDao().getBlogUser(nick);

			if (user == null){
				fbu.getErrors().put("nick", "User not found.");
			}else {
				String inputPassHash = SHACrypto.sha1(password);
				String passHash = user.getPasswordHash();

				if(!passHash.equals(inputPassHash)){
					fbu.getErrors().put("password", "Invalid password.");
				}
			}
		}

		if (!fbu.getErrors().isEmpty()) {
			req.setAttribute("entry", fbu);

			doGet(req, resp);
			return;
		}

		//log in
		if (user != null) {
			Authentication.logIn(req, user);
		}

		resp.sendRedirect(req.getContextPath());

	}

}

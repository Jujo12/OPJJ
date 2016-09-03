package hr.fer.zemris.java.hw15.servlets;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.form.FormBlogUser;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Servlet that handles user registration.
 *
 * @author Juraj Juričić
 */
@WebServlet("/servleti/register")
public class RegistrationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4150389968459311335L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp")
				.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (req.getParameter("submit") == null) {
			doGet(req, resp);
			return;
		}

		FormBlogUser fbu;

		fbu = FormBlogUser.fromHttpRequest(req);
		fbu.validate();

		if (req.getParameter("password").isEmpty()){
			fbu.getErrors().put("password", "Password cannot be empty.");
		}

		if (!fbu.getErrors().isEmpty()) {
			req.setAttribute("entry", fbu);

			doGet(req, resp);
			return;
		}

		//save new user
		BlogUser user = fbu.toBlogUser();
		user.setPassword(req.getParameter("password"));

		DAOProvider.getDao().updateOrAdd(user);

		//redirect to home
		resp.sendRedirect(req.getContextPath());
	}

}

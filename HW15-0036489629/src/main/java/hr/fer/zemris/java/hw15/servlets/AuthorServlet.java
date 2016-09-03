package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.Authentication;
import hr.fer.zemris.java.hw15.ServletMisc;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.form.FormBlogEntry;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The servlet that handles author page, blog posts, and comments.
 *
 * @author Juraj Juričić
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1333336395144850286L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pathInfo = req.getPathInfo();
		if (pathInfo == null){
			ServletMisc.error(req, resp, "Unsupported URL.");
			return;
		}

		String[] params = pathInfo.substring(1).split("/");
		if (params.length < 1){
			ServletMisc.error(req, resp, "Must provide author nick.");
			return;
		}
		if (params.length > 2){
			ServletMisc.error(req, resp, "Unsupported URL.");
			return;
		}

		BlogUser user = DAOProvider.getDao().getBlogUser(params[0]);
		if (user == null){
			ServletMisc.error(req, resp, "User not found: " + params[0]);
		}

		req.setAttribute("author", user);

		if (params.length == 2) {
			if (params[1].equals("new")) {
				blogPostNew(req, resp);
			}
			if (params[1].equals("edit")) {
				blogPostEdit(req, resp);
			}

			try {
				Long entryID = Long.parseLong(params[1]);

				BlogEntry entry = DAOProvider.getDao().getBlogEntry(entryID);
				if (entry == null){
					throw new Exception(); //invalid entry ID
				}

				req.setAttribute("entry", entry);

				req.getRequestDispatcher("/WEB-INF/pages/blogEntry.jsp").forward(req, resp);
			}catch(Exception e) {
				ServletMisc.error(req, resp, "Invalid entry ID.");
			}

			return;
		}

		//one param: author index
		req.setAttribute("posts", DAOProvider.getDao().getBlogEntries(user));
		req.getRequestDispatcher("/WEB-INF/pages/authorIndex.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		if (req.getParameter("comment") != null){
			commentNew(req, resp);
			return;
		}

		if (!Authentication.isUserLoggedIn(req)){
			ServletMisc.error(req, resp, "You are not logged in.");
			return;
		}

		//post edit create
		FormBlogEntry fbe = FormBlogEntry.fromHttpRequest(req);
		fbe.validate();

		if (fbe.getErrors().isEmpty()){
			//create or update entry

			BlogEntry entryNew = fbe.toBlogEntry();
			entryNew.setLastModifiedAt(new Date());

			BlogEntry entryCurrent = null;
			if (entryNew.getId() != null){
				entryCurrent = DAOProvider.getDao().getBlogEntry(entryNew.getId());

				if (entryCurrent.getCreator().getId() != Authentication.getCurrentUser(req).getId()){
					ServletMisc.error(req, resp, "Unauthorized edit.");
					return;
				}
			}
			if (entryCurrent == null){
				//new entry
				entryNew.setCreatedAt(new Date());
				entryNew.setCreator(Authentication.getCurrentUser(req));
			}

			DAOProvider.getDao().updateOrAdd(entryNew);
			//redirect to author
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" +
					Authentication.getCurrentUser(req).getNick());
		}
	}

	/**
	 * Displays form for posting a comment. In case of an error, fails silently by redirecting the user to homepage.
	 *
	 * @param req the HttpServletRequest
	 * @param resp the HttpServletResponse
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void commentNew(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		//in case of error, fail silently.
		try {
			String _postId = req.getParameter("entryId");
			if (_postId == null || _postId.isEmpty()) {
				throw new RuntimeException();
			}

			Long postId = Long.parseLong(_postId);

			BlogEntry entry = DAOProvider.getDao().getBlogEntry(postId);
			if (entry == null){
				throw new RuntimeException();
			}

			String message = req.getParameter("message");
			String email = req.getParameter("email");
			if (message == null || message.trim().isEmpty() ||
					email == null || email.trim().isEmpty()) {
				throw new RuntimeException();
			}

			BlogComment comment = new BlogComment();
			comment.setMessage(message);
			comment.setPostedOn(new Date());
			comment.setUsersEMail(email);
			comment.setBlogEntry(entry);

			DAOProvider.getDao().updateOrAdd(comment);

			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + entry.getCreator().getNick() + "/" + entry.getId());
		}catch(RuntimeException e){
			resp.sendRedirect(req.getContextPath());
		}
	}

	/**
	 * Displays form for creating a new post.
	 *
	 * @param req the HttpServletRequest
	 * @param resp the HttpServletResponse
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void blogPostNew(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException{
		FormBlogEntry entryForm = new FormBlogEntry();

		req.setAttribute("entry", entryForm);
		req.getRequestDispatcher("/WEB-INF/pages/blogEntryForm.jsp").forward(req, resp);
	}

	/**
	 * Displays form for editing a post.
	 *
	 * @param req the HttpServletRequest
	 * @param resp the HttpServletResponse
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void blogPostEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
		String id = req.getParameter("id");
		if(id == null) {
			ServletMisc.error(req, resp, "No ID provided for post edit.");
			return;
		}

		Long _id;
		try {
			_id = Long.parseLong(id);
		} catch(Exception e) {
			ServletMisc.error(req, resp, "ID must be numeric.");
			return;
		}

		BlogEntry entry = DAOProvider.getDao().getBlogEntry(_id);

		req.setAttribute("entry", FormBlogEntry.fromBlogEntry(entry));
		req.getRequestDispatcher("/WEB-INF/pages/blogEntryForm.jsp").forward(req, resp);
	}

}

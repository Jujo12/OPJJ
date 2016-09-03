package hr.fer.zemris.java.hw15;

import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;


/**
 * The helper Authentication class that handles user's session.
 *
 * @author Juraj Juričić
 */
public class Authentication {

	/**
	 * Logs the user out.
	 *
	 * @param req the HttpServletRequest
	 */
	public static void logOut(HttpServletRequest req){
		req.getSession().invalidate();
	}

	/**
	 * Logs the user in.
	 *
	 * @param req the HttpServletRequest
	 * @param user the user
	 */
	public static void logIn(HttpServletRequest req, BlogUser user){
		req.getSession().setAttribute("current.user.id", user.getId());
		req.getSession().setAttribute("current.user.fn", user.getFirstName());
		req.getSession().setAttribute("current.user.ln", user.getLastName());
		req.getSession().setAttribute("current.user.nick", user.getNick());
	}

	/**
	 * Checks if is user logged in.
	 *
	 * @param req the HttpServletRequest
	 * @return true, if is user logged in
	 */
	public static boolean isUserLoggedIn(HttpServletRequest req){
		return req.getSession().getAttribute("current.user.id") != null;
	}

	/**
	 * Gets the currently logged in user.
	 *
	 * @param req the HttpServletRequest
	 * @return the current user, null if the user is not logged in
	 */
	public static BlogUser getCurrentUser(HttpServletRequest req){
		if (!isUserLoggedIn(req)){
			return null;
		}
		try {
			BlogUser user = new BlogUser();

			user.setId((Long) req.getSession().getAttribute("current.user.id"));
			user.setNick((String) req.getSession().getAttribute("current.user.nick"));
			user.setFirstName((String) req.getSession().getAttribute("current.user.fn"));
			user.setLastName((String) req.getSession().getAttribute("current.user.ln"));

			return user;
		}catch(Exception e){
			return null;
		}
	}
}

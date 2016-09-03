package hr.fer.zemris.java.hw15.form;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * The BlogUser object form data wrapper.
 *
 * @author Juraj Juričić
 */
public class FormBlogUser extends FormWrapper{
	
	/** The first name. */
	private String firstName;
	
	/** The last name. */
	private String lastName;
	
	/** The nick. */
	private String nick;
	
	/** The email. */
	private String email;

	/**
	 * Creates a new object from existing blog user.
	 *
	 * @param user the user
	 * @return the form blog user
	 */
	public static FormBlogUser fromBlogUser(BlogUser user){
		FormBlogUser fbu = new FormBlogUser();
		fbu.firstName = user.getFirstName();
		fbu.lastName = user.getLastName();
		fbu.nick = user.getNick();
		fbu.email = user.getEmail();

		return fbu;
	}

	/**
	 * Creates new object from http request.
	 *
	 * @param req the HttpServletRequest
	 * @return the form blog user
	 */
	public static FormBlogUser fromHttpRequest(HttpServletRequest req) {
		FormBlogUser fbu = new FormBlogUser();

		fbu.firstName = req.getParameter("firstName");
		if (fbu.firstName == null){
			fbu.firstName = "";
		}

		fbu.lastName = req.getParameter("lastName");
		if (fbu.lastName == null){
			fbu.lastName = "";
		}

		fbu.nick = req.getParameter("nick");
		if (fbu.nick == null){
			fbu.nick = "";
		}

		fbu.email = req.getParameter("email");
		if (fbu.email == null){
			fbu.email = "";
		}

		return fbu;
	}

	/**
	 * Creates a new BlogUser from this object.
	 *
	 * @return the blog user
	 */
	public BlogUser toBlogUser(){
		BlogUser user = new BlogUser();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);

		return user;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the nick.
	 *
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Sets the nick.
	 *
	 * @param nick the new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Validates the form data and stores all errors in errors map.
	 */
	public void validate() {
		//empty fields
		if (nick == null || nick.isEmpty()) {
			errors.put("nick", "Nick cannot be empty.");
		}
		if (firstName == null || firstName.isEmpty()) {
			errors.put("firstName", "First name cannot be empty.");
		}
		if (lastName == null || lastName.isEmpty()) {
			errors.put("lastName", "Last name cannot be empty.");
		}
		if (email == null || email.isEmpty()) {
			errors.put("email", "E-mail cannot be empty.");
		}

		if (errors.isEmpty() && DAOProvider.getDao().getBlogUser(nick) != null) {
			errors.put("nick", "User with nick " + nick + " already exists.");
		}

		//validate email
		boolean validMail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
		if (!validMail){
			errors.put("email", "Email " + email + " is not valid.");
		}
	}
}
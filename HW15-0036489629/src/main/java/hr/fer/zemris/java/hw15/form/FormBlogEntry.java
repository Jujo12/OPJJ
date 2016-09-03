package hr.fer.zemris.java.hw15.form;

import hr.fer.zemris.java.hw15.model.BlogEntry;

import javax.servlet.http.HttpServletRequest;

/**
 * The BlogEntry object form wrapper.
 *
 * @author Juraj Juričić
 */
public class FormBlogEntry extends FormWrapper {
	
	/** The id. */
	private String id;
	
	/** The title. */
	private String title;
	
	/** The text. */
	private String text;

	/**
	 * From blog entry.
	 *
	 * @param entry the entry
	 * @return the form blog entry
	 */
	public static FormBlogEntry fromBlogEntry(BlogEntry entry){
		FormBlogEntry fbe = new FormBlogEntry();

		fbe.id = Long.toString(entry.getId());
		fbe.title = entry.getTitle();
		fbe.text = entry.getText();

		return fbe;
	}

	/**
	 * From http request.
	 *
	 * @param req the req
	 * @return the form blog entry
	 */
	public static FormBlogEntry fromHttpRequest(HttpServletRequest req){
		FormBlogEntry fbe = new FormBlogEntry();

		fbe.id = req.getParameter("id");
		if (fbe.id == null){
			fbe.id = "";
		}

		fbe.title = req.getParameter("title");
		if (fbe.title == null){
			fbe.title = "";
		}

		fbe.text = req.getParameter("text");
		if (fbe.text == null){
			fbe.text = "";
		}

		return fbe;
	}

	/**
	 * To blog entry.
	 *
	 * @return the blog entry
	 */
	public BlogEntry toBlogEntry(){
		BlogEntry entry = new BlogEntry();

		if (id != null && !id.isEmpty()) {
			try {
				entry.setId(Long.parseLong(id));
			} catch (Exception e) {
				entry.setId(null);
			}
		}

		entry.setTitle(title);
		entry.setText(text);

		return entry;
	}

	/**
	 * Validates the form.
	 */
	public void validate(){
		if (title == null || title.isEmpty()){
			errors.put("title", "Title cannot be empty.");
		}
		if (text == null || text.isEmpty()){
			errors.put("text", "Text cannot be empty.");
		}

		if (errors.isEmpty() && id != null && !id.isEmpty()) {
			try {
				Long.parseLong(id);
			} catch (Exception e) {
				errors.put("id", "Id must be numeric.");
			}
		}
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text.
	 *
	 * @param text the new text
	 */
	public void setText(String text) {
		this.text = text;
	}
}

package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.Date;

/**
 * The model of Blog Comment. Used in JPA.
 *
 * @author Juraj Juričić
 */
@Entity
@Table
public class BlogComment implements UpdateableObject {

	/** The id. */
	@Id @GeneratedValue
	private Long id;

	/** The blog entry. */
	@ManyToOne
	@JoinColumn(nullable=false)
	private BlogEntry blogEntry;

	/** The users e mail. */
	@Column(length=100, nullable=false)
	private String usersEMail;

	/** The message. */
	@Column(length = 4*1024, nullable=false)
	private String message;

	/** The posted on. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date postedOn;

	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the blog entry.
	 *
	 * @return the blog entry
	 */
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	/**
	 * Sets the blog entry.
	 *
	 * @param blogEntry the new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Gets the users e mail.
	 *
	 * @return the users e mail
	 */
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Sets the users e mail.
	 *
	 * @param usersEMail the new users e mail
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the posted on date.
	 *
	 * @return the posted on
	 */
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date posted on.
	 *
	 * @param postedOn the new posted on
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
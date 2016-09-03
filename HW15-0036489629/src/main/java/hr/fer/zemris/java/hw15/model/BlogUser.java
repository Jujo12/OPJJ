package hr.fer.zemris.java.hw15.model;

import hr.fer.zemris.java.hw15.SHACrypto;

import javax.persistence.*;
import java.util.List;

/**
 * The model of Blog User. Used in JPA.
 *
 * @author Juraj Juričić
 */
@Entity
@Table
@Cacheable(true)
public class BlogUser implements UpdateableObject{
	
	/** The id. */
	@Id @GeneratedValue
	private Long id;

	/** The first name. */
	@Column(length = 60, nullable = false)
	private String firstName;

	/** The last name. */
	@Column(length = 60, nullable = false)
	private String lastName;

	/** The nick. */
	@Column(length = 24, unique = true)
	private String nick;

	/** The email. */
	@Column
	private String email;

	/** The password hash. */
	@Column(length = 40, nullable = false)
	private String passwordHash;

	/** The blog entries. */
	@OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("createdAt")
	private List<BlogEntry> blogEntries;

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw15.model.UpdateableObject#getId()
	 */
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
	 * Gets the password hash.
	 *
	 * @return the password hash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Sets the password hash.
	 *
	 * @param passwordHash the new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * Gets the blog entries.
	 *
	 * @return the blog entries
	 */
	public List<BlogEntry> getBlogEntries() {
		return blogEntries;
	}

	/**
	 * Sets the blog entries.
	 *
	 * @param blogEntries the new blog entries
	 */
	public void setBlogEntries(List<BlogEntry> blogEntries) {
		this.blogEntries = blogEntries;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BlogUser blogUser = (BlogUser) o;

		if (!id.equals(blogUser.id)) return false;
		if (!firstName.equals(blogUser.firstName)) return false;
		if (!lastName.equals(blogUser.lastName)) return false;
		if (!nick.equals(blogUser.nick)) return false;
		if (!email.equals(blogUser.email)) return false;
		return passwordHash.equals(blogUser.passwordHash);

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = id.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + nick.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + passwordHash.hashCode();
		return result;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.passwordHash = SHACrypto.sha1(password);
	}
}
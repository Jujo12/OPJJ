package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The model of Blog Entry. Used in JPA.
 *
 * @author Juraj Juričić
 */
@Entity
@Table
@Cacheable(true)
public class BlogEntry  implements UpdateableObject{

	/** The id. */
	@Id @GeneratedValue
	private Long id;

	/** The comments. */
	@OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	@OrderBy("postedOn")
	private List<BlogComment> comments = new ArrayList<>();

	/** The created at. */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date createdAt;

	/** The last modified at. */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedAt;

	/** The title. */
	@Column(nullable=false, length=60)
	private String title;

	/** The text. */
	@Column(nullable=false, length=4*1024)
	private String text;

	/** The creator. */
	@ManyToOne
	@JoinColumn(nullable = false)
	private BlogUser creator;

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
	 * Gets the comments.
	 *
	 * @return the comments
	 */
	public List<BlogComment> getComments() {
		return comments;
	}

	/**
	 * Sets the comments.
	 *
	 * @param comments the new comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the last modified at.
	 *
	 * @return the last modified at
	 */
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Sets the last modified at.
	 *
	 * @param lastModifiedAt the new last modified at
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
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

	/**
	 * Gets the creator.
	 *
	 * @return the creator
	 */
	public BlogUser getCreator(){ return creator; }

	/**
	 * Sets the creator.
	 *
	 * @param creator the new creator
	 */
	public void setCreator(BlogUser creator){ this.creator = creator; }

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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
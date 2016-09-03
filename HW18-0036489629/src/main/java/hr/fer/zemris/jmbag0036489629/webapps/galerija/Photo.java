package hr.fer.zemris.jmbag0036489629.webapps.galerija;

/**
 * The class that represents a single photo.
 *
 * @author Juraj Juričić
 */
public class Photo {
	
	/** The filename of the photo. */
	public final String path;
	
	/** The photo title. */
	public final String title;
	
	/** The photo tags. */
	public final String[] tags;
	
	/**
	 * Instantiates a new photo with given attributes.
	 *
	 * @param path the filename (path relative to the images folder)
	 * @param title the title
	 * @param tags the tags
	 */
	public Photo(String path, String title, String[] tags){
		this.path = path;
		this.title = title;
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Photo other = (Photo) obj;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	
	
}

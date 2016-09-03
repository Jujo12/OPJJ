package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * The Enum TagType. Represents different tags.
 */
public enum TagType {
	
	/** The for tag. */
	FOR	("FOR"),
	
	/** The echo tag. */
	ECHO("="),
	
	/** The closing tag (END). */
	END ("END");
	
	/** The tag identifier. */
	private String identifier;
	
	/**
	 * Instantiates a new tag type.
	 *
	 * @param identifier the tag identifier.
	 */
	TagType(String identifier){
		this.identifier = identifier;
	}
	
	/**
	 * Gets the tag with the provided identifier. Returns null if the given identifier is invalid.
	 *
	 * @param identifier the identifier
	 * @return the tag
	 */
	public static TagType getTag(String identifier){
		for (TagType i : TagType.values()){
			if (i.identifier.equals(identifier)){
				return i;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString(){
		return this.name();
	}
}

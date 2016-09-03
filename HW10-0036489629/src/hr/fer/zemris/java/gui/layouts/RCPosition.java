package hr.fer.zemris.java.gui.layouts;

/**
 * The Class RCPosition represents a single position in {@link CalcLayout}.
 *
 * @author Juraj Juričić
 */
public class RCPosition {
	
	/** The row. */
	private int row;
	
	/** The column. */
	private int column;
	
	/**
	 * Constructs a new RCPosition from given string. String should be in format row,column.
	 *
	 * @param toParse the to parse
	 * @return the RC position
	 */
	public static RCPosition fromString(String toParse){
		String[] parts = toParse.split(",");
		if (parts.length != 2){
			throw new IllegalArgumentException("Invalid position string format.");
		}
		
		int row;
		int col;
		try{
			row = Integer.parseInt(parts[0].trim());
		}catch(NumberFormatException e){
			throw new IllegalArgumentException("Invalid position string content");
		}
		
		try{
			col = Integer.parseInt(parts[1].trim());
		}catch(NumberFormatException e){
			throw new IllegalArgumentException("Invalid position string content");
		}
		
		return new RCPosition(row, col);
	}
	
	/**
	 * Instantiates a new RCposition.
	 *
	 * @param row the row
	 * @param column the column
	 */
	public RCPosition(int row, int column) {
		super();
		
		this.row = row;
		this.column = column;
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	
}

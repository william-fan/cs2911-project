
public class Cell {
	private int x;
	private int y;
	private boolean northWall;
	private boolean southWall;
	private boolean eastWall; 
	private boolean westWall;
	
	public Cell (int x, int y, boolean northWall, boolean southWall, boolean eastWall, boolean westWall) {
		this.x = x;
		this.y = y;
		this.northWall = northWall;
		this.southWall = southWall;
		this.eastWall = eastWall;
		this.westWall = westWall;
	}
	
	/**
	 * Gets the x value of the cell.
	 * @return	The x value of the cell.
	 */
	public int getx(){
		return this.x;
	}
	/**
	 * Gets the y value of the cell.
	 * @return	The y value of the cell.
	 */
	public int gety(){
		return this.y;
	}
	
	/**
	 * Gets if the cell has a north wall.
	 * @return	Boolean value of whether the cell has a north wall or not.
	 */
	public boolean getNorthWall(){
		return this.northWall;
	}

	/**
	 * Gets if the cell has a south wall.
	 * @return	Boolean value of whether the cell has a south wall or not.
	 */
	public boolean getSouthWall(){
		return this.southWall;
	}
	
	/**
	 * Gets if the cell has a east wall.
	 * @return	Boolean value of whether the cell has a east wall or not.
	 */
	public boolean getEastWall(){
		return this.eastWall;
	}
	
	/**
	 * Gets if the cell has a west wall.
	 * @return	Boolean value of whether the cell has a west wall or not.
	 */
	public boolean getWestWall(){
		return this.westWall;
	}

	/**
	 * Sets if the cell has a north wall.
	 * @param b	Input boolean value of whether the cell has a north wall or not.
	 */
	public void setNorthWall(boolean b){
		this.northWall = b;
	}
	
	/**
	 * Sets if the cell has a south wall.
	 * @param b	Input boolean value of whether the cell has a south wall or not.
	 */
	public void setSouthWall(boolean b){
		this.southWall = b;
	}
	
	/**
	 * Sets if the cell has a east wall.
	 * @param b	Input boolean value of whether the cell has a east wall or not.
	 */
	public void setEastWall(boolean b){
		this.eastWall = b;
	}
	
	/**
	 * Sets if the cell has a west wall.
	 * @param b	Input boolean value of whether the cell has a west wall or not.
	 */
	public void setWestWall(boolean b){
		this.westWall = b;
	}
}

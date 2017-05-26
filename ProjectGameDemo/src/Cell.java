import java.io.File;

import javafx.scene.image.Image;

/**
 * This class is in charge of the cells in the map. It deals with the 
 * tile type, whether it is a floor or wall etc. 
 * 
 * @authors 
 * William Fan, Bob Guo, Charles Lu, Alexander Ong, Allan Wu
 */	
public class Cell {
	private int x;
	private int y;
	private int type; // 0 floor, 1 wall, 2 target, 3 player, 4 block (3 & 4 are placeholders for starting locations)
	private Image floorImage;

	/**
	 * Initialises the cell in the map
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param type The type of cell in that coordinate
	 */
	public Cell(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = 0;
		this.floorImage = new Image(new File("images/ground.png").toURI().toString());
	}

	/**
	 * Gets the x value of the cell.
	 * @return The x value of the cell.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Gets the y value of the cell.
	 * @return The y value of the cell.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Gets the image of the floor
	 * @return The image of the floor
	 */
	public Image getFloorImage() {
		return floorImage;
	}

	/**
	 * Sets the image of the floor
	 * @param floorImage The image of the floor
	 */
	public void setFloorImage(Image floorImage) {
		this.floorImage = floorImage;
	}

	/**
	 * Changes the image of the cell depending on the tile type
	 */
	private void changeFloorImage() {
		if (this.type == 0) {
			this.floorImage = new Image(new File("images/ground.png").toURI().toString());
		} else if (this.type == 1) {
			this.floorImage = new Image(new File("images/wall.png").toURI().toString());
		} else if (this.type == 2) {
			this.floorImage = new Image(new File("images/target.png").toURI().toString());
		} else {
			this.floorImage = new Image(new File("images/ground.png").toURI().toString());
		}
	}

	/**
	 * Gets the type of cell
	 * @return The type of cell
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Sets the type of cell
	 * @param type The type of cell
	 */
	public void setType(int type) {
		this.type = type;
		changeFloorImage();
	}

}

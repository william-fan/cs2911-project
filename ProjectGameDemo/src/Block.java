import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is in charge of the blocks in the map. This deals with the images
 * of the blocks as well as its movement in tandem with the Player class.
 * 
 * @authors 
 * William Fan, Bob Guo, Charles Lu, Alexander Ong, Allan Wu
 */	
public class Block {
	private int x;
	private int y;
	private int ID;
	private ImageView blockImage;
	
	/**
	 * Initialises the block in the map
	 * @param ID The ID of the block
	 * @param x The x coordinate of the block
	 * @param y The y coordinate of the block
	 */
	public Block(int ID, int x, int y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.blockImage = imageInit();
	}

	/**
	 * Initialises the image of the block
	 * @return The image view of the block
	 */
	private ImageView imageInit() {
		ImageView tempImage = new ImageView(new Image(new File("images/block" + this.ID + ".png").toURI().toString()));
		tempImage.setFitHeight(48);
		tempImage.setFitWidth(48);
		return tempImage;
	}

	/**
	 * Moves the block down
	 */
	public void moveDown() {
		y -= 1;
	}
	
	/**
	 * Moves the block left
	 */
	public void moveLeft() {
		x -= 1;
	}

	/**
	 * Moves the block up
	 */
	public void moveUp() {
		y += 1;
	}
	
	/**
	 * Moves the block right
	 */
	public void moveRight() {
		x += 1;
	}
	
	/**
	 * Gets the x coordinate of the block
	 * @return Gets the x coordinate of the block
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Sets the x coordinate of the block
	 * @param x The x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the block
	 * @return Gets the y coordinate of the block
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Sets the y coordinate of the block
	 * @param y The y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
     * Gets the ID of the block
	 * @return Gets the ID of the block
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Sets the ID of the block
	 * @param ID The ID of the block
	 */
	public void setID(int ID) {
		this.ID = ID;
		this.blockImage = this.imageInit();
	}
	
	/**
	 * Returns the image of the block
	 * @return Returns the image of the block
	 */
	public ImageView getBlockImage() {
		return this.blockImage;
	}
}

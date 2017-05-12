import java.io.File;

import javafx.scene.image.Image;

public class Cell {
	private int x;
	private int y;
	private boolean isWall;
	private Image floorImage;
	
	public Cell (int x, int y, boolean isWall) {
		this.x = x;
		this.y = y;
	  	this.isWall = false;
  		this.floorImage = new Image(new File("images/ground.png").toURI().toString());
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

	public boolean isWall() {
		return isWall;
	}

	public void setWall(boolean isWall) {
		this.isWall = isWall;
  		this.setFloorImage(new Image(new File("images/wall.png").toURI().toString()));
	}

	public Image getFloorImage() {
		return floorImage;
	}

	public void setFloorImage(Image floorImage) {
		this.floorImage = floorImage;
	}
	

}

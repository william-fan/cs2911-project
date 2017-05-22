import java.io.File;

import javafx.scene.image.Image;

public class Cell {
	private int x;
	private int y;
	private int type; // 0 floor, 1 wall, 2 target, 3 player, 4 block (3 & 4 are placeholders for starting locations)
	private Image floorImage;

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

	public Image getFloorImage() {
		return floorImage;
	}

	public void setFloorImage(Image floorImage) {
		this.floorImage = floorImage;
	}

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

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
		changeFloorImage();
	}

}

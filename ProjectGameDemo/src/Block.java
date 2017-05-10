import java.io.File;

import javafx.scene.image.Image;

public class Block {
	private int x;
	private int y;
	private int ID;
	private Image blockImage;
	
	public Block(int ID) {
		this.ID = ID;
	  	this.blockImage = new Image(new File("images/block"+ID+".png").toURI().toString());
	}
	
	public void moveDown() {
		y -= 1;
	}

	public void moveLeft() {
		x -= 1;
	}

	public void moveUp() {
		y += 1;
	}

	public void moveRight() {
		x += 1;
	}

	public int getX() {
		return this.x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getID() {
		return this.ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}
	
	public Image getBlockImage() {
		return this.blockImage;
	}
}

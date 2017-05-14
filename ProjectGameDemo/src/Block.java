import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {
	private int x;
	private int y;
	private int ID;
	private ImageView blockImage;

	public Block(int ID, int x, int y) {
		this.ID = ID;
		this.x = x;
		this.y = y;
		this.blockImage = imageInit();
	}

	public ImageView imageInit() {
		ImageView tempImage = new ImageView(new Image(new File("images/block" + ID + ".png").toURI().toString()));
		tempImage.setFitHeight(64);
		tempImage.setFitWidth(64);
		return tempImage;
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

	public ImageView getBlockImage() {
		return this.blockImage;
	}
}

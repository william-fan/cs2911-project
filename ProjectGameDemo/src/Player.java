import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;

public class Player {
	private int x;
	private int y;
	private int ID;
	private Image playerImage;
	private ArrayList<ArrayList<Image>> sprites;
	
	public Player(int ID) {
		this.x = 0;
		this.y = 0;
		this.ID = ID;
	  	this.playerImage = new Image(new File("images/sprite1.png").toURI().toString());
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
	
	public void updateSprite (int newDirection) {
		
	}
	
	public Image getPlayerImage() {
		return this.playerImage;
	}
}

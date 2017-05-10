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
	
	public Boolean moveDown(ArrayList<Block> blockList, int gameWidth, int gameHeight) {
		if (y + 1 >= gameHeight) {
			return false;
		}
		y += 1;
		return true;
	}

	public Boolean moveLeft(ArrayList<Block> blockList, int gameWidth, int gameHeight) {
		if (x - 1 < 0) {
			return false;
		}
		x -= 1;
		return true;
	}

	public Boolean moveUp(ArrayList<Block> blockList, int gameWidth, int gameHeight) {
		if (y - 1 < 0) {
			return false;
		}
		y -= 1;
		return true;
	}

	public Boolean moveRight(ArrayList<Block> blockList, int gameWidth, int gameHeight) {
		if (x + 1 >= gameWidth) {
			return false;
		}
		x += 1;
		return true;
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
	
	//1 - up, 2 - right, 3 - down, 4 - left
	public void checkCollision(ArrayList<Block> blockList, int direction, int gameWidth, int gameHeight) {
		for (Block block : blockList) {
			if (block.getX() + 1 == this.getX() && direction == 1) {
				
			}
		}
	}
}

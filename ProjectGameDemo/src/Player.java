import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
	private int x;
	private int y;
	private int ID;
	private ImageView playerImage;
	//private ArrayList<ArrayList<Image>> sprites;
	
	public Player(int ID, int x, int y) {
		this.x = x;
		this.y = y;
		this.ID = ID;
	  	this.playerImage = initImage();
	}
	
	public ImageView initImage() {
		ImageView tempImage = new ImageView(new Image(new File("images/sprite"+this.ID+".png").toURI().toString()));
	  	tempImage.setFitHeight(64);
	  	tempImage.setFitWidth(64);
	  	return tempImage;
	}
	
	public Boolean moveDown(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (y + 1 >= grid[0].length || grid[x][y+1].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() && otherPlayer.getY() == this.getY() + 1) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 3, grid)) {
			return false;
		}
		y += 1;
		return true;
	}

	public Boolean moveLeft(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (x - 1 < 0 || grid[x-1][y].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() - 1 && otherPlayer.getY() == this.getY()) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 4, grid)) {
			return false;
		}
		x -= 1;
		return true;
	}

	public Boolean moveUp(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if ((y - 1 < 0 || grid[x][y-1].getType() == 1)) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() && otherPlayer.getY() == this.getY() - 1) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 1, grid)) {
			return false;
		}
		y -= 1;
		return true;
	}

	public Boolean moveRight(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (x + 1 >= grid.length || grid[x+1][y].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() + 1 && otherPlayer.getY() == this.getY()) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 2, grid)) {
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
	
	public ImageView getPlayerImage() {
		return this.playerImage;
	}
	
	//1 - up, 2 - right, 3 - down, 4 - left
	public Boolean checkCollision(ArrayList<Block> blockList, Player otherPlayer, int direction, Cell[][] grid) {
		if (direction == 1) {
			Block tempBlock = findBlock(blockList, this.getX(), this.getY() - 1);
			Block tempBlockSecond = findBlock(blockList, this.getX(), this.getY() - 2);
			if (tempBlock == null) { //no box
				return true;
			}
			if (tempBlockSecond == null) { //if no second box above the first
				if (otherPlayer.getX() == tempBlock.getX() && otherPlayer.getY() + 1 == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX()][tempBlock.getY() - 1].getType() == 1) {
					return false;
				} else {
					tempBlock.setY(this.getY() - 2);
					return true;
				}
			} else { //dont allow two boxes to be pushed
				return false;
			}
		} else if (direction == 2) {
			Block tempBlock = findBlock(blockList, this.getX() + 1, this.getY());
			Block tempBlockSecond = findBlock(blockList, this.getX() + 2, this.getY());
			if (tempBlock == null) { //no box
				return true;
			}
			if (tempBlockSecond == null) { //if no second box above the first
				if (otherPlayer.getX() - 1 == tempBlock.getX() && otherPlayer.getY() == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX() + 1][tempBlock.getY()].getType() == 1) {
					return false;
				} else {
					tempBlock.setX(this.getX() + 2);
					return true;
				}
			} else { //dont allow two boxes to be pushed
				return false;
			}
		} else if (direction == 3) {
			Block tempBlock = findBlock(blockList, this.getX(), this.getY() + 1);
			Block tempBlockSecond = findBlock(blockList, this.getX(), this.getY() + 2);
			if (tempBlock == null) { //no box
				return true;
			}
			if (tempBlockSecond == null) { //if no second box above the first
				if (otherPlayer.getX() == tempBlock.getX() && otherPlayer.getY() - 1 == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX()][tempBlock.getY() + 1].getType() == 1) {
					return false;
				} else {
					tempBlock.setY(this.getY() + 2);
					return true;
				}
			} else { //dont allow two boxes to be pushed
				return false;
			}
		} else if (direction == 4) {
			Block tempBlock = findBlock(blockList, this.getX() - 1, this.getY());
			Block tempBlockSecond = findBlock(blockList, this.getX() - 2, this.getY());
			if (tempBlock == null) { //no box
				return true;
			}
			if (tempBlockSecond == null) { //if no second box above the first
				if (otherPlayer.getX() + 1 == tempBlock.getX() && otherPlayer.getY() == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX() - 1][tempBlock.getY()].getType() == 1) {
					return false;
				} else {
					tempBlock.setX(this.getX() - 2);
					return true;
				}
			} else { //dont allow two boxes to be pushed
				return false;
			}
		}
		return false;
	}
	
	public Block findBlock(ArrayList<Block> blockList, int x, int y) {
		for (Block block : blockList) {
			if (block.getX() == x && block.getY() == y) {
				return block;
			}
		}
		return null;
	}
}

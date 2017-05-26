import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
	private int x;
	private int y;
	private int ID;
	private int moveCount;
	private int blockMoveCount;
	private ImageView playerImage;
	private LinkedList<LinkedList<Integer>> posList = new LinkedList<LinkedList<Integer>>();

	public Player(int ID, int x, int y) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.playerImage = initImage();
	}

	public ImageView initImage() {
		ImageView tempImage = new ImageView(new Image(new File("images/sprite" + this.ID + ".png").toURI().toString()));
		tempImage.setFitHeight(48);
		tempImage.setFitWidth(48);
		return tempImage;
	}

	public Boolean moveDown(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (y + 1 >= grid[0].length || grid[x][y + 1].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() && otherPlayer.getY() == this.getY() + 1) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 3, grid)) {
			return false;
		}
		y += 1;
		this.moveCount++;
		updateSprite(3);
		return true;
	}

	public Boolean moveLeft(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (x - 1 < 0 || grid[x - 1][y].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() - 1 && otherPlayer.getY() == this.getY()) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 4, grid)) {
			return false;
		}
		x -= 1;
		this.moveCount++;
		updateSprite(4);
		return true;
	}

	public Boolean moveUp(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if ((y - 1 < 0 || grid[x][y - 1].getType() == 1)) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() && otherPlayer.getY() == this.getY() - 1) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 1, grid)) {
			return false;
		}
		y -= 1;
		this.moveCount++;
		updateSprite(1);
		return true;
	}

	public Boolean moveRight(ArrayList<Block> blockList, Player otherPlayer, Cell[][] grid) {
		if (x + 1 >= grid.length || grid[x + 1][y].getType() == 1) {
			return false;
		}
		if (otherPlayer.getX() == this.getX() + 1 && otherPlayer.getY() == this.getY()) {
			return false;
		}
		if (!checkCollision(blockList, otherPlayer, 2, grid)) {
			return false;
		}
		x += 1;
		this.moveCount++;
		updateSprite(2);
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

	public void setID(int ID) {
		this.ID = ID;
	}

	private void updateSprite(int newDirection) {
		if (newDirection == 1) {
			ImageView tempImage = new ImageView(
					new Image(new File("images/sprite" + this.ID + "_1.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 2) {
			ImageView tempImage = new ImageView(
					new Image(new File("images/sprite" + this.ID + "_2.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 3) {
			ImageView tempImage = new ImageView(
					new Image(new File("images/sprite" + this.ID + "_3.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 4) {
			ImageView tempImage = new ImageView(
					new Image(new File("images/sprite" + this.ID + "_4.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		}
	}

	public ImageView getPlayerImage() {
		return this.playerImage;
	}

	// 1 - up, 2 - right, 3 - down, 4 - left
	private Boolean checkCollision(ArrayList<Block> blockList, Player otherPlayer, int direction, Cell[][] grid) {
		if (direction == 1) {
			Block tempBlock = findBlock(blockList, this.getX(), this.getY() - 1);
			Block tempBlockSecond = findBlock(blockList, this.getX(), this.getY() - 2);
			if (tempBlock == null) { // no box
				return true;
			}
			if (tempBlockSecond == null) { // if no second box above the first
				if (otherPlayer.getX() == tempBlock.getX() && otherPlayer.getY() + 1 == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX()][tempBlock.getY() - 1].getType() == 1) {
					return false;
				} else {
					addPositionToList(otherPlayer, blockList);
					tempBlock.setY(this.getY() - 2);
					this.blockMoveCount++;
					return true;
				}
			} else { // don't allow two boxes to be pushed
				return false;
			}
		} else if (direction == 2) {
			Block tempBlock = findBlock(blockList, this.getX() + 1, this.getY());
			Block tempBlockSecond = findBlock(blockList, this.getX() + 2, this.getY());
			if (tempBlock == null) { // no box
				return true;
			}
			if (tempBlockSecond == null) { // if no second box above the first
				if (otherPlayer.getX() - 1 == tempBlock.getX() && otherPlayer.getY() == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX() + 1][tempBlock.getY()].getType() == 1) {
					return false;
				} else {
					addPositionToList(otherPlayer, blockList);
					tempBlock.setX(this.getX() + 2);
					this.blockMoveCount++;
					return true;
				}
			} else { // dont allow two boxes to be pushed
				return false;
			}
		} else if (direction == 3) {
			Block tempBlock = findBlock(blockList, this.getX(), this.getY() + 1);
			Block tempBlockSecond = findBlock(blockList, this.getX(), this.getY() + 2);
			if (tempBlock == null) { // no box
				return true;
			}
			if (tempBlockSecond == null) { // if no second box above the first
				if (otherPlayer.getX() == tempBlock.getX() && otherPlayer.getY() - 1 == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX()][tempBlock.getY() + 1].getType() == 1) {
					return false;
				} else {
					addPositionToList(otherPlayer, blockList);
					tempBlock.setY(this.getY() + 2);
					this.blockMoveCount++;
					return true;
				}
			} else { // dont allow two boxes to be pushed
				return false;
			}
		} else if (direction == 4) {
			Block tempBlock = findBlock(blockList, this.getX() - 1, this.getY());
			Block tempBlockSecond = findBlock(blockList, this.getX() - 2, this.getY());
			if (tempBlock == null) { // no box
				return true;
			}
			if (tempBlockSecond == null) { // if no second box above the first
				if (otherPlayer.getX() + 1 == tempBlock.getX() && otherPlayer.getY() == tempBlock.getY()) {
					return false;
				} else if (grid[tempBlock.getX() - 1][tempBlock.getY()].getType() == 1) {
					return false;
				} else {
					addPositionToList(otherPlayer, blockList);
					tempBlock.setX(this.getX() - 2);
					this.blockMoveCount++;
					return true;
				}
			} else { // Don't allow two boxes to be pushed
				return false;
			}
		}
		return false;
	}

	private Block findBlock(ArrayList<Block> blockList, int x, int y) {
		for (Block block : blockList) {
			if (block.getX() == x && block.getY() == y) {
				return block;
			}
		}
		return null;
	}

	private void addPositionToList(Player otherPlayer, ArrayList<Block> blockList) {
		LinkedList<Integer> spritePos = new LinkedList<Integer>();
		spritePos.add(ID);
		spritePos.add(x);
		spritePos.add(y);
		for (Block block : blockList) {
			spritePos.add(block.getX());
			spritePos.add(block.getY());
		}
		this.posList.add(spritePos);
	}

	public int getMoveCount() {
		return this.moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	public int getBlockMoveCount() {
		return this.blockMoveCount;
	}

	public void setBlockMoveCount(int blockMoveCount) {
		this.blockMoveCount = blockMoveCount;
	}

	public LinkedList<LinkedList<Integer>> getPosList() {
		return this.posList;
	}
}

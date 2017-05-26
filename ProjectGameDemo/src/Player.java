import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is in charge of the Player tile in the map. It deals with the 
 * players movement and interactions with its surroundings like moving blocks
 * or not moving into walls. This class also keeps track of the players and
 * blocks location when a block has been moved
 * 
 * @authors 
 * William Fan, Bob Guo, Charles Lu, Alexander Ong, Allan Wu
 */	
public class Player {
	private int x;
	private int y;
	private int ID;
	private int moveCount;
	private int blockMoveCount;
	private ImageView playerImage;
	private LinkedList<LinkedList<Integer>> posList = new LinkedList<LinkedList<Integer>>();
	private ArrayList<ArrayList<Integer>> moveList = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * Initialises the player type
	 * @param ID The player ID, whether player 1 or 2
	 * @param x The x coordinate of the player
	 * @param y The y coordinate of the player
	 */
	public Player(int ID, int x, int y) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.playerImage = initImage();
	}

	/**
	 * Allocates an image to the player depending on it's ID
	 * @return The imageview for player 1 or 2
	 */
	public ImageView initImage() {
		ImageView tempImage = new ImageView(new Image(new File("images/sprite" + this.ID + ".png").toURI().toString()));
		tempImage.setFitHeight(48);
		tempImage.setFitWidth(48);
		return tempImage;
	}

	/**
	 * This method deals with the player moving down
	 * @param blockList The list of blocks within the map
	 * @param otherPlayer The other player type
	 * @param grid The grid layout
	 * @return Whether the player can move down or not due to obstacles
	 */
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

	/**
	 * This method deals with the player moving left
	 * @param blockList The list of blocks within the map
	 * @param otherPlayer The other player type
	 * @param grid The grid layout
	 * @return Whether the player can move left or not due to obstacles
	 */
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

	/**
	 * This method deals with the player moving up
	 * @param blockList The list of blocks within the map
	 * @param otherPlayer The other player type
	 * @param grid The grid layout
	 * @return Whether the player can move up or not due to obstacles
	 */
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

	/**
	 * This method deals with the player moving right
	 * @param blockList The list of blocks within the map
	 * @param otherPlayer The other player type
	 * @param grid The grid layout
	 * @return Whether the player can move right or not due to obstacles
	 */
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

	/**
     * Gets the x coordinate of the player
	 * @return Gets the x coordinate of the player
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Sets the x coordinate of the player
	 * @param x The x coordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y coordinate of the player
	 * @return Gets the y coordinate of the player
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Sets the y coordinate of the player
	 * @param y The y coordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
     * Gets the ID of the player 
	 * @return Gets the ID of the player
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Sets the ID of the player
	 * @param ID The ID of the player
	 */
	public void setID(int ID) {
		this.ID = ID;
	}

	/**
	 * This updates the player sprite depending on the direction it is facing
	 * @param newDirection The new direction the player is facing
	 */
	private void updateSprite(int newDirection) {
		if (newDirection == 1) {
			ImageView tempImage = new ImageView(new Image(new File("images/sprite"+this.ID+"_1.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 2) {
			ImageView tempImage = new ImageView(new Image(new File("images/sprite"+this.ID+"_2.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 3) {
			ImageView tempImage = new ImageView(new Image(new File("images/sprite"+this.ID+"_3.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		} else if (newDirection == 4) {
			ImageView tempImage = new ImageView(new Image(new File("images/sprite"+this.ID+"_4.png").toURI().toString()));
			tempImage.setFitHeight(48);
			tempImage.setFitWidth(48);
			this.playerImage = tempImage;
		}
	}

	/**
	 * Returns the image of the player 
	 * @return Returns the image of the player
	 */
	public ImageView getPlayerImage() {
		return this.playerImage;
	}

	// 1 - up, 2 - right, 3 - down, 4 - left
	/**
	 * Checks the interaction between the player and the block and whether the
	 * block should be moved or not. Also keeps track of the player and block
	 * location if the block should be moved.
	 * @param blockList The list of blocks in the map
	 * @param otherPlayer The other player
	 * @param direction The direction the player is moving
	 * @param grid The grid layout
	 * @return Whether the block can move or not
	 */
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

	/**
	 * Finds the block in a list with given xy coordinates
	 * @param tempBlockList A temporary block list with all the blocks
	 * @param x The x coordinate of block
	 * @param y The y coordinate of block
	 * @return Returns the block with the same xy coordinates
	 */
	private Block findBlock(ArrayList<Block> blockList, int x, int y) {
		for (Block block : blockList) {
			if (block.getX() == x && block.getY() == y) {
				return block;
			}
		}
		return null;
	}

	/**
	 * Adds the player/block location to a list
	 * @param otherPlayer The other player
	 * @param blockList The list of blocks in the map
	 */
	private void addPositionToList(Player otherPlayer, ArrayList<Block> blockList) {
		LinkedList<Integer> spritePos = new LinkedList<Integer>();
		spritePos.add(ID);
		spritePos.add(x);
		spritePos.add(y);
		for (Block block : blockList){
			spritePos.add(block.getX());
			spritePos.add(block.getY());
		}
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		tempList.add(this.blockMoveCount);
		tempList.add(this.moveCount);
		this.moveList.add(tempList);
		this.posList.add(spritePos);
	}
	
	/**
	 * Returns the number of moves made
	 * @return Returns the number of moves made
	 */
	public int getMoveCount() {
		return this.moveCount;
	}

	/**
	 * Sets the number of moves made
	 * @param moveCount The number of moves made
	 */
	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

	/**
	 * Returns the number of blocks moved
	 * @return Returns the number of blocks moved
	 */
	public int getBlockMoveCount() {
		return this.blockMoveCount;
	}

	/**
	 * Sets the number of blocks moved
	 * @param blockMoveCount The number of blocks moved
	 */
	public void setBlockMoveCount(int blockMoveCount) {
		this.blockMoveCount = blockMoveCount;
	}
	
	/**
	 * Returns the list of positions of players/blocks when a block is moved
	 * @return Returns the list of positions of players/blocks when a block is
	 * moved
	 */
	public LinkedList<LinkedList<Integer>> getPosList() {
		return this.posList;
	}

	/**
	 * Get the list move/box move counts.
	 * Stored as a list of (boxMoveCount, moveCount)
	 * @return The list move/box move counts.
	 */
	public ArrayList<ArrayList<Integer>> getMoveList() {
		return this.moveList;
	}
	
	/**
	 * Set the list move/box move counts.
	 * Stored as a list of (boxMoveCount, moveCount)
	 * @param moveList The list move/box move counts.
	 */
	public void setMoveList(ArrayList<ArrayList<Integer>> moveList) {
		this.moveList = moveList;
	}
}

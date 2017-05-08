
public class Player {
	private int x;
	private int y;
	private int ID;
	
	public Player() {
		this.x = 0;
		this.y = 0;
		this.ID = 0;
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
}

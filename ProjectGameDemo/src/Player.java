
public class Player {
	private int x;
	private int y;
	private int ID;
	
	public Player () {
		this.setX(0);
		this.setY(0);
		this.ID = 0;
	}
	
	public void moveDown() {
		this.setY(this.getY() - 1);
	}

	public void moveLeft() {
		this.setX(this.getX() - 1);
	}

	public void moveUp() {
		this.setY(this.getY() + 1);
	}

	public void moveRight() {
		this.setX(this.getX() + 1);
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
}

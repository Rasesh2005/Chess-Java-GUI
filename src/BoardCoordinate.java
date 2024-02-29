public class BoardCoordinate {
    private int x, y;

    public BoardCoordinate(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public boolean equals(Object o) {
		if(!(o instanceof BoardCoordinate)) {
			return false;
		}
		BoardCoordinate other = (BoardCoordinate) o;
		return other.getX() == this.x && other.getY() == this.y;
	}
}

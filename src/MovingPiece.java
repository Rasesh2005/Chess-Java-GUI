public class MovingPiece {
    int TILE_SIZE=100;//dummy value
	private Piece piece;
	private double currentX;
	private double currentY;
	
	public MovingPiece(Piece piece) {
		this.piece = piece;
		this.currentX = piece.getCoordinate().getX() * TILE_SIZE;
		this.currentY = piece.getCoordinate().getY() * TILE_SIZE;
	}
	
	public void update(double x, double y) {
		currentX = x;
		currentY = y;
	}
	
	public double getX() {
		return currentX;
	}
	
	public double getY() {
		return currentY;
	}
	
	public Piece getPiece() {
		return piece;
	}
}
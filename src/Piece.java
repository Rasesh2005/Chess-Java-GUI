import java.util.List;

interface ValidMoveVisitor { // will make a class which has function to find valid moves for all pieces
	List<BoardCoordinate> calculateValidMoves(Piece piece);
	List<BoardCoordinate> calculateValidMoves(King piece);
	List<BoardCoordinate> calculateValidMoves(Queen piece);
	List<BoardCoordinate> calculateValidMoves(Rook piece);
	List<BoardCoordinate> calculateValidMoves(Bishop piece);
	List<BoardCoordinate> calculateValidMoves(Knight piece);
	List<BoardCoordinate> calculateValidMoves(Pawn piece);
}
abstract public class Piece {
    protected Player owner;
    protected String color;
    private BoardCoordinate location;
    public Player getOwner() {
        return owner;
    }
    public String getColor() {
        return color;
    }
    public BoardCoordinate getCoordinate() {
        return location;
    }
    public void setCoordinate(BoardCoordinate location) {
        this.location = location;
    }
    public List<BoardCoordinate> accept(ValidMoveVisitor visitor) {
		return visitor.calculateValidMoves(this);
    }
}


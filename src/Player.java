import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Player {
    private String color;
    private List<Piece> pieceList;
    private Stack<Piece> graveyard;
    private boolean underCheck;
    public void initializePieces(){
        
    }
    public void initialize(String color) {
		pieceList = new ArrayList<Piece>();
		graveyard = new Stack<Piece>();
		this.color = color;
		this.initializePieces();
	}
    public String getColor() {
		return color;
	}
	
	public List<Piece> getPieceList() {
		return pieceList;
	}
	
	public void addToGraveyard(Piece piece) {
		graveyard.push(piece);
		removePiece(piece);
	}
	
	public Stack<Piece> getGraveyard() {
		return graveyard;
	}
	
	public void removePiece(Piece piece) {
		pieceList.remove(piece);
	}
	
	public boolean underCheck() {
		return underCheck;
	}
	
	public void setCheck(boolean underCheck) {
		this.underCheck = underCheck;
	}
}

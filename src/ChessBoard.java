import java.util.Stack;

public class ChessBoard {
	private Piece board[][] = new Piece[8][8];
	private Stack<MoveHistory> previousMoves = new Stack<>();
	
	public Piece[][] getBoard(){
		return board;
    }
    public Piece getPiece(int x, int y) {
		if(x >= 0 && x < board.length && y >= 0 && y < board[0].length) {
			return board[x][y];
		}
		return null;
	}
    // public Stack<MoveHistory> getPreviousMoves(){ // may be used to add undo option later
	// 	return previousMoves;
	// }
	
	public MoveHistory getPreviousMove() {
		if(previousMoves.isEmpty())
			return null;
		
		return previousMoves.peek();
	}
}
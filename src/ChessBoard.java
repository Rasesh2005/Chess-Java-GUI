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
	public void move(Piece piece, BoardCoordinate tile) {
		int sourceX = piece.getCoordinate().getX();
		int sourceY = piece.getCoordinate().getY();
		int destX = tile.getX();
		int destY = tile.getY();
		int direction = 0; 
		Piece tilePiece = getPiece(destX, destY);
		
		if(tilePiece != null) {
			tilePiece.getOwner().addToGraveyard(tilePiece);
		}
		this.previousMoves.push(new MoveHistory(piece,new BoardCoordinate(sourceX, sourceY)));
		if(piece instanceof Pawn) {
			((Pawn)piece).setFirstMove(false);
			String color = piece.getColor();
			if((destX != sourceX) && tilePiece == null) {// en passant case
				if(color.equals("White")) {
					direction = Validator.UP;
					// UP DOWN LEFT RIGHT are static variable stating direction
				}
				else {
					direction = Validator.DOWN;
				}
				
				tilePiece = getPiece(destX, destY-direction);
				tilePiece.getOwner().addToGraveyard(tilePiece);
				board[destX][destY-direction] = null;
			}
		}else if(piece instanceof King) {
			((King)piece).setHasMoved(true);
			
			if(destX == sourceX + 2 ) {// short castling
				Piece rightRook = board[destX+1][destY];
				board[destX - 1][destY] = rightRook;
				rightRook.setCoordinate(new BoardCoordinate(destX-1,destY));
				
				board[destX + 1][destY] = null;
			}
			else if(destX == sourceX - 2) {// long castling
				Piece leftRook = board[destX-2][destY];
				board[destX + 1][destY] = leftRook;
				leftRook.setCoordinate(new BoardCoordinate(destX+1,destY));

				board[destX - 2][destY] = null;
			}
		}else if(piece instanceof Rook) {
			((Rook)piece).setHasMoved(true);
		}
		board[sourceX][sourceY] = null;
		board[destX][destY] = piece;
		piece.setCoordinate(tile);
	}
}
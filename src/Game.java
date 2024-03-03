import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.validation.Validator;

public class Game {

    private Player whitePlayer;
	private Player blackPlayer;
	private Player currentPlayer;
	private Player waitingPlayer;
    private ChessBoard chessBoard;
	private Validator validator;
	private Piece selectedPiece;	
	private int turn;
	private int lastCapture;
	private int lastPawnMove;
	private boolean isRunning;

    public Game(Player p1, Player p2){
        whitePlayer = p1;
        blackPlayer = p2;
        currentPlayer = p1;
        waitingPlayer = p2;

        p1.initialize("WHITE");
        p2.initialize("BLACK");

        chessBoard = new ChessBoard();
        setupBoard();
    }

    private void setupBoard() {
        chessBoard.getBoard()[4][7] = whitePlayer.getPieceList().get(0);	//King
		chessBoard.getBoard()[3][7] = whitePlayer.getPieceList().get(1);	//Queen
		chessBoard.getBoard()[0][7] = whitePlayer.getPieceList().get(2);	//Rook
		chessBoard.getBoard()[2][7] = whitePlayer.getPieceList().get(3);	//Bishop
		chessBoard.getBoard()[1][7] = whitePlayer.getPieceList().get(4);	//Knight
		chessBoard.getBoard()[7][7] = whitePlayer.getPieceList().get(5);	//Rook
		chessBoard.getBoard()[5][7] = whitePlayer.getPieceList().get(6);	//Bishop
		chessBoard.getBoard()[6][7] = whitePlayer.getPieceList().get(7);	//Knight
		for(int i = 0; i < 8; i++) {
			chessBoard.getBoard()[i][6] = whitePlayer.getPieceList().get(8 + i);	//Pawn
	    }

		chessBoard.getBoard()[4][0] = blackPlayer.getPieceList().get(0);	//King
		chessBoard.getBoard()[3][0] = blackPlayer.getPieceList().get(1);	//Queen
		chessBoard.getBoard()[0][0] = blackPlayer.getPieceList().get(2);	//Rook  
		chessBoard.getBoard()[2][0] = blackPlayer.getPieceList().get(3);	//Bishop
		chessBoard.getBoard()[1][0] = blackPlayer.getPieceList().get(4);	//Knight
		chessBoard.getBoard()[7][0] = blackPlayer.getPieceList().get(5);	//Rook  
		chessBoard.getBoard()[5][0] = blackPlayer.getPieceList().get(6);	//Bishop
		chessBoard.getBoard()[6][0] = blackPlayer.getPieceList().get(7);	//Knight
		for(int i = 0; i < 8; i++) {
			chessBoard.getBoard()[i][1] = blackPlayer.getPieceList().get(8 + i);	//Pawn
		}

		chessBoard.getBoard()[4][7].setCoordinate(new BoardCoordinate(4, 7));
		chessBoard.getBoard()[3][7].setCoordinate(new BoardCoordinate(3, 7));
		chessBoard.getBoard()[0][7].setCoordinate(new BoardCoordinate(0, 7));
		chessBoard.getBoard()[2][7].setCoordinate(new BoardCoordinate(2, 7));
		chessBoard.getBoard()[1][7].setCoordinate(new BoardCoordinate(1, 7));
		chessBoard.getBoard()[7][7].setCoordinate(new BoardCoordinate(7, 7));
		chessBoard.getBoard()[5][7].setCoordinate(new BoardCoordinate(5, 7));
		chessBoard.getBoard()[6][7].setCoordinate(new BoardCoordinate(6, 7));
		chessBoard.getBoard()[4][0].setCoordinate(new BoardCoordinate(4, 0));
		chessBoard.getBoard()[3][0].setCoordinate(new BoardCoordinate(3, 0));
		chessBoard.getBoard()[0][0].setCoordinate(new BoardCoordinate(0, 0));
		chessBoard.getBoard()[2][0].setCoordinate(new BoardCoordinate(2, 0));
		chessBoard.getBoard()[1][0].setCoordinate(new BoardCoordinate(1, 0));
		chessBoard.getBoard()[7][0].setCoordinate(new BoardCoordinate(7, 0));
		chessBoard.getBoard()[5][0].setCoordinate(new BoardCoordinate(5, 0));
		chessBoard.getBoard()[6][0].setCoordinate(new BoardCoordinate(6, 0));
		for(int i = 0; i < 8; i++) {
			chessBoard.getBoard()[i][6].setCoordinate(new BoardCoordinate(i, 6));
			chessBoard.getBoard()[i][1].setCoordinate(new BoardCoordinate(i, 1));
		}
    }

    public ChessBoard getChessBoard() {
		return chessBoard;
	}


	//SOME DISPLAY DEPENDANT CODE HERE REMAINING

	private void promote(Piece piece) {
		BoardCoordinate coor = piece.getCoordinate();
		int x = coor.getX(), y = coor.getY();

		String[] promote = {"Queen", "Knight", "Rook", "Bishop"};

		JFrame frame = new JFrame();
		frame.setResizable(true);	

		int n = JOptionPane.showOptionDialog(frame,
				"What do you want to promote to?",
				"Choose a piece:",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, promote, promote[0]);
		
		while(true) {
			if(n == 0) {
				chessBoard.getBoard()[x][y] = new Queen(piece.getOwner(),piece.getColor());
				break;
			}
			else if(n == 1) {
				chessBoard.getBoard()[x][y] = new Knight(piece.getOwner(),piece.getColor());
				break;
			}
			else if(n == 2) {
				chessBoard.getBoard()[x][y] = new Rook(piece.getOwner(),piece.getColor());
				break;
			}
			else if(n == 3) {
				chessBoard.getBoard()[x][y] = new Bishop(piece.getOwner(),piece.getColor());
				break;
			}
			else if(n == JOptionPane.CLOSED_OPTION) {
				n = JOptionPane.showOptionDialog(frame,
						"What do you want to promote to?",
						"Choose a piece:",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, promote, promote[0]);
			}
		}

		currentPlayer.removePiece(piece);
		chessBoard.getBoard()[x][y].setCoordinate(new BoardCoordinate(x,y));
	}

	public boolean isRunning() {
		return this.isRunning;
	}

}

import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game {

    private Player whitePlayer;
	private Player blackPlayer;
	private Player currentPlayer;
	private ChessTimer timerPlayer1;
    private ChessTimer timerPlayer2;
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
		timerPlayer1 = new ChessTimer();
        timerPlayer2 = new ChessTimer();
		// timerPlayer1.stop();
		// timerPlayer2.stop();

        p1.initialize("WHITE");
        p2.initialize("BLACK");

        chessBoard = new ChessBoard();
        setupBoard();
		validator = new Validator(chessBoard);
		selectedPiece = null;
		turn = 1;
		isRunning = true;
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

	public void selectTile(BoardCoordinate tile, Display display) {
		List<BoardCoordinate> moves;
		display.clearHighlights();
		Piece tilePiece = chessBoard.getPiece(tile.getX(), tile.getY());
		if(tilePiece != null && currentPlayer.equals(tilePiece.getOwner()) && selectedPiece != tilePiece //Don't select same piece
		) {
			selectedPiece = tilePiece;
			moves = tilePiece.accept(validator);
			display.setHighlights(moves);
			display.setSourceHighlight(tilePiece.getCoordinate());
			display.setEnemyHighlights(validator.filterForEnemyHighlights(moves, selectedPiece));
		}
		else if(selectedPiece != null) {
			moves = selectedPiece.accept(validator);
			if(moves.contains(tile)) {
				if(turn == 1)
				{
					timerPlayer1.start(display);
				}
				display.drawMove(selectedPiece, tile);
				move(selectedPiece, tile, display);
			}
			selectedPiece = null;
		}
	}

	private void move(Piece piece, BoardCoordinate tile, Display display) {
		int graveyardSize = waitingPlayer.getGraveyard().size();
		chessBoard.move(piece, tile);

		if(piece instanceof Pawn) {
			lastPawnMove = turn;
			if(validator.legalPromotion((Pawn) piece, tile)) {
				promote(piece);
			}
		}

		if(waitingPlayer.getGraveyard().size() > graveyardSize) {
			lastCapture = turn;
		}

		checkGameStatus(display);
		changeTurn(display);
	}


	private void checkGameStatus(Display display) {
		String message1 = null;
		String message2 = null;
		if(validator.underCheckmate(waitingPlayer)) {
			message1 = "Checkmate!";
			message2 = currentPlayer.getColor() + " wins!";
		}
		else if(validator.isStalemate(waitingPlayer)) {
			message1 = "Stalemate!";
			message2 = "Draw!";
		}
		else if(validator.isFiftyMove(turn, lastCapture, lastPawnMove)) {
			message1 = "Fifty-move rule!";
			message2 = "Draw!";
		}
		else {
			return;
		}
		isRunning = false;
		display.setMessages(message1, message2);
		display.repaint();
	}
	private void changeTurn(Display display) {
		if(currentPlayer == whitePlayer) {
			currentPlayer = blackPlayer;
			waitingPlayer = whitePlayer;
			timerPlayer1.stop();
			timerPlayer2.start(display);
			System.out.println(timerPlayer1.getElapsedTimeInSeconds());
		}
		else {
			currentPlayer = whitePlayer;
			waitingPlayer = blackPlayer;
			timerPlayer1.start(display);
			timerPlayer2.stop();
			System.out.println(timerPlayer2.getElapsedTimeInSeconds());
		}
		turn++;
	}


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

	public int getTurn(){
		return turn;
	}

	public boolean isRunning() {
		return this.isRunning;
	}

	public ChessTimer getTimerPlayer1() {
		return timerPlayer1;
	}

	public ChessTimer getTimerPlayer2() {
		return timerPlayer2;
	}
}

public class Game {

    private Player whitePlayer;
	private Player blackPlayer;
	private Player currentPlayer;
	private Player waitingPlayer;
    private ChessBoard chessboard;

    public Game(Player p1, Player p2){
        whitePlayer = p1;
        blackPlayer = p2;
        currentPlayer = p1;
        waitingPlayer = p2;

        p1.initialize("WHITE");
        p2.initialize("BLACK");

        chessboard = new ChessBoard();
        setupBoard();
    }

    private void setupBoard() {
        chessboard.getBoard()[4][7] = whitePlayer.getPieceList().get(0);	//King
		chessboard.getBoard()[3][7] = whitePlayer.getPieceList().get(1);	//Queen
		chessboard.getBoard()[0][7] = whitePlayer.getPieceList().get(2);	//Rook
		chessboard.getBoard()[2][7] = whitePlayer.getPieceList().get(3);	//Bishop
		chessboard.getBoard()[1][7] = whitePlayer.getPieceList().get(4);	//Knight
		chessboard.getBoard()[7][7] = whitePlayer.getPieceList().get(5);	//Rook
		chessboard.getBoard()[5][7] = whitePlayer.getPieceList().get(6);	//Bishop
		chessboard.getBoard()[6][7] = whitePlayer.getPieceList().get(7);	//Knight
		for(int i = 0; i < 8; i++) {
			chessboard.getBoard()[i][6] = whitePlayer.getPieceList().get(8 + i);	//Pawn
	    }

		chessboard.getBoard()[4][0] = blackPlayer.getPieceList().get(0);	//King
		chessboard.getBoard()[3][0] = blackPlayer.getPieceList().get(1);	//Queen
		chessboard.getBoard()[0][0] = blackPlayer.getPieceList().get(2);	//Rook  
		chessboard.getBoard()[2][0] = blackPlayer.getPieceList().get(3);	//Bishop
		chessboard.getBoard()[1][0] = blackPlayer.getPieceList().get(4);	//Knight
		chessboard.getBoard()[7][0] = blackPlayer.getPieceList().get(5);	//Rook  
		chessboard.getBoard()[5][0] = blackPlayer.getPieceList().get(6);	//Bishop
		chessboard.getBoard()[6][0] = blackPlayer.getPieceList().get(7);	//Knight
		for(int i = 0; i < 8; i++) {
			chessboard.getBoard()[i][1] = blackPlayer.getPieceList().get(8 + i);	//Pawn
		}

		chessboard.getBoard()[4][7].setCoordinate(new BoardCoordinate(4, 7));
		chessboard.getBoard()[3][7].setCoordinate(new BoardCoordinate(3, 7));
		chessboard.getBoard()[0][7].setCoordinate(new BoardCoordinate(0, 7));
		chessboard.getBoard()[2][7].setCoordinate(new BoardCoordinate(2, 7));
		chessboard.getBoard()[1][7].setCoordinate(new BoardCoordinate(1, 7));
		chessboard.getBoard()[7][7].setCoordinate(new BoardCoordinate(7, 7));
		chessboard.getBoard()[5][7].setCoordinate(new BoardCoordinate(5, 7));
		chessboard.getBoard()[6][7].setCoordinate(new BoardCoordinate(6, 7));
		chessboard.getBoard()[4][0].setCoordinate(new BoardCoordinate(4, 0));
		chessboard.getBoard()[3][0].setCoordinate(new BoardCoordinate(3, 0));
		chessboard.getBoard()[0][0].setCoordinate(new BoardCoordinate(0, 0));
		chessboard.getBoard()[2][0].setCoordinate(new BoardCoordinate(2, 0));
		chessboard.getBoard()[1][0].setCoordinate(new BoardCoordinate(1, 0));
		chessboard.getBoard()[7][0].setCoordinate(new BoardCoordinate(7, 0));
		chessboard.getBoard()[5][0].setCoordinate(new BoardCoordinate(5, 0));
		chessboard.getBoard()[6][0].setCoordinate(new BoardCoordinate(6, 0));
		for(int i = 0; i < 8; i++) {
			chessboard.getBoard()[i][6].setCoordinate(new BoardCoordinate(i, 6));
			chessboard.getBoard()[i][1].setCoordinate(new BoardCoordinate(i, 1));
		}
    }

    public ChessBoard getChessBoard() {
		return chessboard;
	}

}

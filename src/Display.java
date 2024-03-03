
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;

// images 0 - white, 1- black
// peices order king, queen, rook, bishop, knight, pawn

// drawmovingpiece ke saath sex ho rkha hai abhi

public class Display extends JPanel {

    public static final int TILE_SIZE = 100;
	private static final Color GREEN = new Color(0x96, 0xFF, 0x96);
	private static final Color LIGHT_BLUE = new Color(0xC8, 0xE6, 0xFF);
	private static final Color DARK_BLUE = new Color(0x7D, 0xC8, 0xFF);
	private static final Color LIGHT_RED = new Color(0xFF, 0x50, 0x50);
	private static final Color DARK_RED = new Color(0xC8, 0x00, 0x00);
	private static final Color LIGHT_TILE = new Color(235, 236, 208);
	private static final Color DARK_TILE = new Color(119, 149, 86);
    private Graphics2D g2d;
    private Game game;
    private ChessBoard chessboard;
	private Image images[][] = new Image[2][6];
	private boolean inAnimation;
	private MovingPiece currentMovingPiece;
	private List<BoardCoordinate> highlights;
	private List<BoardCoordinate> enemyHighlights;
	private BoardCoordinate sourceHighlight;
	private Timer timer;
	private String message1;
	private String message2;
    
    public Display(Game game){
        this.setPreferredSize(new Dimension(TILE_SIZE*8, TILE_SIZE*8));
        this.game = game;
		this.chessboard = game.getChessBoard();
		this.highlights = new LinkedList<BoardCoordinate>();
		this.enemyHighlights = new LinkedList<BoardCoordinate>();
		inAnimation = false;
		images[0][0] = getPieceImage("king", "white");
		images[0][1] = getPieceImage("queen", "white");
		images[0][2] = getPieceImage("rook", "white");
		images[0][3] = getPieceImage("bishop", "white");
		images[0][4] = getPieceImage("knight", "white");
		images[0][5] = getPieceImage("pawn", "white");
		images[1][0] = getPieceImage("king", "black");
		images[1][1] = getPieceImage("queen", "black");
		images[1][2] = getPieceImage("rook", "black");
		images[1][3] = getPieceImage("bishop", "black");
		images[1][4] = getPieceImage("knight", "black");
		images[1][5] = getPieceImage("pawn", "black");
    }

	public boolean isAnimating() {
		return inAnimation;
	}

	public Game getGame() {
		return game;
	}

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;
        drawBoard();
		highlightTiles(sourceHighlight, enemyHighlights, highlights);
        drawPieces();
		drawMovingPiece();
    }

	public void drawMove(Piece piece, BoardCoordinate tile) { //Animation
		final double srcX = piece.getCoordinate().getX() * TILE_SIZE;
		final double srcY = piece.getCoordinate().getY() * TILE_SIZE;
		final double desX = tile.getX() * TILE_SIZE;
		final double desY = tile.getY() * TILE_SIZE;

		currentMovingPiece = new MovingPiece(piece);

		final int totalAnimationTime = 250; // 250 milliseconds
		final int FPS = 60;	

		int frameRate = totalAnimationTime / FPS;	//each 10ms will fire an action

		double deltaX = desX - srcX;	//total displacement of
		double deltaY = desY - srcY;	//x and y in pixels

		final double incrementX = deltaX / FPS;
		final double incrementY = deltaY / FPS;
		
		timer = new Timer(frameRate, new ActionListener() {
			private int remainingFrame = FPS;
			//initial location
			private double x = srcX;
			private double y = srcY;
			
            public void actionPerformed(ActionEvent e) {
				if(remainingFrame == 0) {
					inAnimation = false;
            		currentMovingPiece = null;
            		timer.stop();
            	}
            	else {
					inAnimation = true;
            		remainingFrame--;
            		x = x + incrementX;
            		y = y + incrementY;
            	}
				repaint();
            }
        });
		timer.restart();
	}
	

    private void drawMovingPiece() {
		if(currentMovingPiece == null) return;
		drawPiece(currentMovingPiece.getPiece(), currentMovingPiece.getX()/TILE_SIZE, (int) currentMovingPiece.getY()/TILE_SIZE);
	}
	
	private void drawPieces() {
        Piece[][] board = chessboard.getBoard();
        for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board.length; j++) {
				if(board[i][j] != null && (currentMovingPiece == null || currentMovingPiece.getPiece() != board[i][j])){
                    	//if board location is not null and there is not a piece moving or if this is not a moving piece
					drawPiece(board[i][j], i, j);
				}
			}
		}
    }

    private void drawBoard() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
	            if(
	                (i % 2 == 0 && j % 2 == 0) || // Both are even
	                (i % 2 == 1 && j % 2 == 1)    // Both are odd
	            ) {
	                g2d.setColor(DARK_TILE);
	            } else {
	                g2d.setColor(LIGHT_TILE);
	            }
	            g2d.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	        }
	    }
	}

    private void drawPiece(Piece piece, double x, double y) {
        if(piece.getColor().equals("WHITE")) {
			if(Pawn.class.isInstance(piece))
				g2d.drawImage(images[0][5], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Rook.class.isInstance(piece))
				g2d.drawImage(images[0][2], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);		
			else if(Knight.class.isInstance(piece))
				g2d.drawImage(images[0][4], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Bishop.class.isInstance(piece))
				g2d.drawImage(images[0][3], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Queen.class.isInstance(piece))
				g2d.drawImage(images[0][1], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(King.class.isInstance(piece))
				g2d.drawImage(images[0][0], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
		}
		else {
			if(Pawn.class.isInstance(piece))
				g2d.drawImage(images[1][5], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Rook.class.isInstance(piece))
				g2d.drawImage(images[1][2], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);		
			else if(Knight.class.isInstance(piece))
				g2d.drawImage(images[1][4], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Bishop.class.isInstance(piece))
				g2d.drawImage(images[1][3], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(Queen.class.isInstance(piece))
				g2d.drawImage(images[1][1], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
			else if(King.class.isInstance(piece))
				g2d.drawImage(images[1][0], (int)(TILE_SIZE * x), (int)(TILE_SIZE * y), null);	
		}
    }

	private Image getPieceImage(String piece, String color){
		ImageIcon icon = new ImageIcon(getClass().getResource("../bin/images/" + color + "_" + piece + ".png"));
		Image image = icon.getImage().getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
		icon = new ImageIcon(image, icon.getDescription());
		return icon.getImage();
	}

	public void setSourceHighlight(BoardCoordinate sourceHighlight) {
		this.sourceHighlight = sourceHighlight;
	}
	public void setHighlights(List<BoardCoordinate> highlights) {
		this.highlights = highlights;
	}	
	public void setEnemyHighlights(List<BoardCoordinate> enemyHighlights) {
		this.enemyHighlights = enemyHighlights;
	}
	public void clearHighlights() {
		this.sourceHighlight = null;  // source ka default color
		this.highlights.clear();      // 
		this.enemyHighlights.clear(); // 
	}
	
	public void highlightTiles(BoardCoordinate source, List<BoardCoordinate> enemyMoves, List<BoardCoordinate> moves) {
		int x, y;
		if(source != null) {
			g2d.setColor(GREEN);
			g2d.fillRect(source.getX() * TILE_SIZE, source.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
		for(BoardCoordinate move: moves) {
			x = move.getX();
			y = move.getY();
			
			if(
				(x % 2 == 0 && y % 2 == 0) || // Both are even
				(x % 2 == 1 && y % 2 == 1)    // Both are odd
			) {
				g2d.setColor(LIGHT_BLUE);
			} else {
				g2d.setColor(DARK_BLUE);
			}
			g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
		for(BoardCoordinate move: enemyMoves) {
			x = move.getX();
			y = move.getY();
			
			if(
				(x % 2 == 0 && y % 2 == 0) || // Both are even
				(x % 2 == 1 && y % 2 == 1)    // Both are odd
			) {
				g2d.setColor(LIGHT_RED);
			} else {
				g2d.setColor(DARK_RED);
			}
			g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		}
	}
	
	public void setMessages(String message1, String message2) {
		this.message1 = message1;
		this.message2 = message2;
	}
	// dekhna pdega
	public void drawMessage() {
		if(message1 != null && message2 != null) {
			int fontSize = (int)(TILE_SIZE * 8.0 / 7);
			int startingX, startingY;
			g2d.setFont(new Font("Copperplate", Font.PLAIN, fontSize));
			g2d.setColor(Color.BLACK);
			FontMetrics fm = getFontMetrics(g2d.getFont());
			
			startingX = ((TILE_SIZE * 8) - (fm.stringWidth(message1))) / 2;
			startingY = ((TILE_SIZE * 8) - (fm.getHeight() / 2)) / 2;
			g2d.drawString(message1, startingX, startingY);
			startingX = ((TILE_SIZE * 8) - (fm.stringWidth(message2))) / 2;
			startingY += fm.getHeight();
			g2d.drawString(message2, startingX, startingY);
		}
	}

}
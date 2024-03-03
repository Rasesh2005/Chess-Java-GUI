
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

public class Display extends JPanel {

    public static final int TILE_SIZE = 80;
	private static final Color GREEN = new Color(0x96, 0xFF, 0x96);
	private static final Color LIGHT_BLUE = new Color(0xC8, 0xE6, 0xFF);
	private static final Color DARK_BLUE = new Color(0x7D, 0xC8, 0xFF);
	private static final Color LIGHT_RED = new Color(0xFF, 0x50, 0x50);
	private static final Color DARK_RED = new Color(0xC8, 0x00, 0x00);
	private static final Color LIGHT_TILE = new Color(210, 165, 125);
	private static final Color DARK_TILE = new Color(175, 115, 70);
    private Graphics2D g2d;
    
    public Display(){
        
        this.setPreferredSize(new Dimension(this.TILE_SIZE*8, this.TILE_SIZE*8));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;
        drawBoard();
    }

    private void drawBoard() {
	    //paint the board tiles
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
}

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class App {
    public static void main(String args[]) {

		Player p1 = new Player();
		Player p2 = new Player();
		
		// Game game = new Game(p1, p2);
		
		Display gameDisplay = new Display();
		
		// final int BOARD_SIZE = Display.TILE_SIZE * gameDisplay.getGame().getBoard().getBoard().length;
		JFrame gameWindow = new JFrame("Chess");

        ImageIcon logoImage = new ImageIcon("./lib/app_icon.png");
        gameWindow.setIconImage(logoImage.getImage());
        gameWindow.getContentPane().setBackground(new Color(155,155,200));
		
		gameWindow.setResizable(false);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.add(gameDisplay);
		gameWindow.setVisible(true);
		gameWindow.setLocationRelativeTo(null);

        gameWindow.pack();

		new MouseInput();
	}
}

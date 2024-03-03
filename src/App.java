import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

// piece lsit order
// king quees rook bishop knight pawn

public class App {
    public static void main(String args[]) {

		Player p1 = new Player();
		Player p2 = new Player();
		
		Game game = new Game(p1, p2);
		
		Display gameDisplay = new Display(game);
		JFrame gameWindow = new JFrame("Chess");

        ImageIcon logoImage = new ImageIcon("lib/images/app_icon.png");
        gameWindow.setIconImage(logoImage.getImage());
        gameWindow.getContentPane().setBackground(new Color(155,155,200));
		
		gameWindow.setResizable(false);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.add(gameDisplay);
        gameWindow.pack();
		gameWindow.setVisible(true);
		gameWindow.setLocationRelativeTo(null);

		// new MouseInput();
	}
}

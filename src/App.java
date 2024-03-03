import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// piece lsit order
// king quees rook bishop knight pawn

public class App {
    public static void main(String args[]) {

		Player p1 = new Player();
		Player p2 = new Player();
		
		Game game = new Game(p1, p2);
		
		Display gameDisplay = new Display(game);
		JFrame gameWindow = new JFrame("Chess");

        ImageIcon logoImage = new ImageIcon("src/images/app_icon.png");
        gameWindow.setIconImage(logoImage.getImage());
        gameWindow.getContentPane().setBackground(new Color(155,155,200));
		
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.add(gameDisplay);
        gameWindow.pack();
		gameWindow.setVisible(true);
		gameWindow.setLocationRelativeTo(null);
		int n = askTime();
		gameDisplay.setn(n);

		
		new MouseInput(gameDisplay);
	}
	static public int askTime() {

		String[] promote = {"5 minutes", "10 minutes", "15 minutes"};

		JFrame frame = new JFrame();
		frame.setResizable(true);	

		int n = JOptionPane.showOptionDialog(frame,
				"How long should each player play?",
				"Chess",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, promote, promote[0]);
		
		while(true) {
			if(n == JOptionPane.CLOSED_OPTION) {
				n = JOptionPane.showOptionDialog(frame,
						"How long should each player play?",
						"Choose a time option:",
						JOptionPane.DEFAULT_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, promote, promote[0]);
				continue;
			}
			break;
		}
		return n;
	}

}

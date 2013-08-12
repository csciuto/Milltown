package sciuto.corey.milltown.map.swing;

import javax.swing.JOptionPane;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.GameManager;

/**
 * Runs the whole thing.
 * @author Corey
 *
 */
public class GameRunner {

	private static MainScreen screen;
	private static Game game;
	
	protected static void createAndShowGUI(Game g) {
		try {
			screen = new MainScreen(g);
			screen.setVisible(true);
		} catch (Exception e) {
			// TODO: Unify errors and logging.
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, String.format("Uh oh."));
			System.exit(-1);
		}
	}

	/**
	 * Registers all the event listeners and starts the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		game = GameManager.newGame();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(game);
			}
		});
	}
	
}

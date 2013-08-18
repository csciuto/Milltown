package sciuto.corey.milltown.map.swing;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.SaveGameManager;

/**
 * Runs the whole thing.
 * 
 * @author Corey
 * 
 */
public class GameRunner {

	private static MainScreen screen;
	private static Game game;

	private static final Logger LOGGER = Logger.getLogger(GameRunner.class);
	
	protected static void createAndShowGUI(Game g) {
		try {
			screen = new MainScreen(g);
			screen.setVisible(true);
		} catch (Exception e) {
			LOGGER.fatal(e.getLocalizedMessage(), e);
			JOptionPane.showMessageDialog(null, String.format("We have encountered a fatal error and must exit..."));
			System.exit(-1);
		}
	}

	/**
	 * Registers all the event listeners and starts the program.
	 * 
	 * @param args
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

		LOGGER.info("***** Starting Milltown *****");
		
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

		game = SaveGameManager.newGame();

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(game);
			}
		});
	}

}

package sciuto.corey.milltown.map.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import sciuto.corey.milltown.engine.Game;

/**
 * Runs the simulator and displays the output. Also accepts the input.
 * 
 * @author Corey
 * 
 */
public class MainScreen extends JFrame {

	/*
	 * CONSTANTS
	 */

	private static final String HELP_MSG = "Instructions:\n" + "Pressing 's' changes simulation speed\n"
			+ "'p' toggles the simulation on and off\n" + "'h' displays this message\n" + "'CTRL+C' or 'CTRL+X' quits";

	private static final String PROGRAM_NAME = "Milltown!";

	// The size of the screen
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 768;

	// Timer stuff
	private static final int SLOW = 50000;
	private static final int MEDIUM = 10000;
	private static final int FAST = 5000;
	private static final int WICKED_FAST = 1000;

	/*
	 * INSTANCE VARIABLES
	 */

	private Game game;
	
	/**
	 * Runs the simulation itself
	 */
	private Timer simulationTimer;
	
	/**
	 * Updates the GUI. Graphics that need to update register to it.
	 */
	private Timer guiUpdateTimer;

	private GameMap map;
	
	private ToolBox leftBox;
	
	private ToolBox rightBox;
	
	private InfoBar topBar;
	private FieldDisplayer dateLabel;
	
	private InfoBar bottomBar;

	/**
	 * This code controls the simulation/gui interaction.
	 * 
	 * When the Simulation timer ticks, the simulation is run. Once the simulation updates,
	 * the GUI timer ticks once to update the UI. 
	 * 
	 * @author Corey
	 *
	 */
	private class SimulationHandler implements ActionListener{

		private Game game;
		
		public SimulationHandler(Game g){
			this.game = g;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			game.simulate();
			guiUpdateTimer.start();
		}
		
	};
	private SimulationHandler simulationHandler;
	
	/**
	 * Renders Game in the Main Screen
	 */
	public MainScreen(Game g) {

		super(PROGRAM_NAME);
		
		this.game = g;
		
		/*
		 * Set up the timers
		 */
		simulationHandler = new SimulationHandler(this.game);
		simulationTimer = new Timer(MEDIUM, simulationHandler);
		simulationTimer.setInitialDelay(0);
		
		guiUpdateTimer = new Timer(0,null);
		guiUpdateTimer.setRepeats(false);
				 
		/*
		 * GRAPHICS
		 */
		setMinimumSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		final Dimension screenSize = new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT);
		setSize(screenSize);

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);

		BorderLayout mgr = new BorderLayout();
		mgr.setVgap(5);
		mgr.setHgap(5);
		getContentPane().setLayout(mgr);
		getContentPane().setBackground(Color.LIGHT_GRAY);

		leftBox = new ToolBox("left",150);
		getContentPane().add(leftBox,BorderLayout.LINE_START);
		
		map = new GameMap(game.getBoard(), guiUpdateTimer);
		getContentPane().add(map,BorderLayout.CENTER);
		
		rightBox = new ToolBox("right", 221);
		getContentPane().add(rightBox,BorderLayout.LINE_END);
		
		topBar = new InfoBar("top", 35);
		topBar.setLayout(new BoxLayout(topBar, BoxLayout.LINE_AXIS));
		getContentPane().add(topBar,BorderLayout.PAGE_START);
		
		dateLabel = new FieldDisplayer("Date", game.getGameDate(), guiUpdateTimer);
		topBar.add(dateLabel);
		
		bottomBar = new InfoBar("bottom", 35);
		getContentPane().add(bottomBar,BorderLayout.PAGE_END);

		/*
		 * LISTENERS
		 */

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent k) {
				if (k.isControlDown()) {
					if (k.getKeyCode() == KeyEvent.VK_X || k.getKeyCode() == KeyEvent.VK_C) {
						System.exit(0);
					}
				}
			}

			public void keyReleased(KeyEvent k) {
				return;
			}

			public void keyTyped(KeyEvent k) {

				/*
				 * TODO: This needs some refactoring. Most of these shouldn't be popups.
				 */
				
				
				// Message dialogs stop the timer - we need to know if we should
				// restart it.
				boolean timerRunning = simulationTimer.isRunning();

				if (k.getKeyChar() == 'p') {
					if (timerRunning) {
						simulationTimer.stop();
						JOptionPane.showMessageDialog(null, "Simulation: OFF");
					} else {
						simulationTimer.stop();
						JOptionPane.showMessageDialog(null, "Simulation: ON.");
						simulationTimer.start();
					}
				}
				if (k.getKeyChar() == 'h') {
					simulationTimer.stop();
					JOptionPane.showMessageDialog(null, HELP_MSG);
					if (timerRunning) {
						simulationTimer.start();
					}
				} else if (k.getKeyChar() == 's') {
					simulationTimer.stop();
					if (simulationTimer.getDelay() == SLOW) {
						simulationTimer.setDelay(MEDIUM);
						JOptionPane.showMessageDialog(null, "Simulation Speed: MEDIUM");
					} else if (simulationTimer.getDelay() == MEDIUM) {
						simulationTimer.setDelay(FAST);
						JOptionPane.showMessageDialog(null, "Simulation Speed: FAST");
					} else if (simulationTimer.getDelay() == FAST) {
						simulationTimer.setDelay(WICKED_FAST);
						JOptionPane.showMessageDialog(null, "Simulation Speed: WICKED FAST");
					} else if (simulationTimer.getDelay() == WICKED_FAST) {
						simulationTimer.setDelay(SLOW);
						JOptionPane.showMessageDialog(null, "Simulation Speed: SLOW");
					}
					if (timerRunning) {
						simulationTimer.start();
					}
				}
			}
		});
	}

}

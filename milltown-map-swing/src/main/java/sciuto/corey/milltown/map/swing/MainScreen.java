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

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.map.swing.components.GameMap;
import sciuto.corey.milltown.map.swing.components.HorizontalPanel;
import sciuto.corey.milltown.map.swing.components.MultiLineTextField;
import sciuto.corey.milltown.map.swing.components.SpeedButton;
import sciuto.corey.milltown.map.swing.components.TextField;
import sciuto.corey.milltown.map.swing.components.VerticalPanel;

/**
 * Runs the simulator and displays the output. Also accepts the input.
 * 
 * @author Corey
 * 
 */
public class MainScreen extends JFrame {

	private static final String HELP_MSG = "Instructions:\n" + "Pressing 's' changes simulation speed\n"
			+ "'p' toggles the simulation on and off\n" + "'h' displays this message\n" + "'CTRL+C' or 'CTRL+X' quits";

	private static final String PROGRAM_NAME = "Milltown!";

	/*
	 * ENGINE ELEMENTS
	 */

	private final Game game;

	/*
	 * GUI COMPONENTS
	 */
	
	/**
	 * Runs the simulation itself
	 */
	private final Timer simulationTimer;

	/**
	 * Updates the GUI. Graphics that need to update register to it.
	 */
	private final Timer guiUpdateTimer;

	private final GameMap map;

	private final VerticalPanel leftBox;

	private final VerticalPanel rightBox;
	private final MultiLineTextField clickDataBox;

	private final HorizontalPanel topBar;
	private final TextField dateLabel;
	private final TextField populationLabel;
	private final TextField moneyLabel;
	private final TextField economyLabel;
	private final SpeedButton speedButton;
	
	private final HorizontalPanel bottomBar;

	/**
	 * This code controls the simulation/gui interaction.
	 * 
	 * When the Simulation timer ticks, the simulation is run. Once the
	 * simulation updates, the GUI timer ticks once to update the UI.
	 * 
	 * @author Corey
	 * 
	 */
	private class SimulationHandler implements ActionListener {

		private Game game;

		public SimulationHandler(Game g) {
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
	 * Renders Game in the Main Screen and starts the simulation
	 */
	public MainScreen(Game g) {

		super(PROGRAM_NAME);

		this.game = g;

		/*
		 * Set up the timers
		 */
		simulationHandler = new SimulationHandler(this.game);
		simulationTimer = new Timer(Speed.MEDIUM.getSpeedInMilliseconds(), simulationHandler);
		simulationTimer.setInitialDelay(0);

		guiUpdateTimer = new Timer(0, null);
		guiUpdateTimer.setRepeats(false);

		/*
		 * GRAPHICS
		 */

		// The screen itself
		final Dimension screenSize = new Dimension(1024, 768);
		setSize(screenSize);
		setSize(screenSize);

		BorderLayout mgr = new BorderLayout();
		mgr.setVgap(5);
		mgr.setHgap(5);
		getContentPane().setLayout(mgr);
		getContentPane().setBackground(Color.LIGHT_GRAY);

		// The menu bar
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		this.setJMenuBar(menuBar);

		// Left Panel - selection
		leftBox = new VerticalPanel("left", 150);
		getContentPane().add(leftBox, BorderLayout.LINE_START);

		// Right Panel - info
		rightBox = new VerticalPanel("right", 221);
		getContentPane().add(rightBox, BorderLayout.LINE_END);

		clickDataBox = new MultiLineTextField("Current Selection", 221, 100);
		rightBox.add(clickDataBox);

		// Top Panel
		topBar = new HorizontalPanel("top", 35);
		getContentPane().add(topBar, BorderLayout.PAGE_START);

		topBar.add(Box.createHorizontalStrut(10));
		speedButton = new SpeedButton(new Dimension(150, 35), simulationTimer);
		topBar.add(speedButton);

		topBar.add(Box.createHorizontalStrut(10));
		dateLabel = new TextField("Date", game.getGameDate(), new Dimension(150, 35), guiUpdateTimer);
		topBar.add(dateLabel);

		topBar.add(Box.createHorizontalStrut(50));
		populationLabel = new TextField("Population", game.getPopulation(), new Dimension(250, 35), guiUpdateTimer);
		topBar.add(populationLabel);

		topBar.add(Box.createHorizontalStrut(10));
		moneyLabel = new TextField("Funds", game.getMoney(), new Dimension(250, 35), guiUpdateTimer);
		topBar.add(moneyLabel);

		topBar.add(Box.createHorizontalStrut(10));
		economyLabel = new TextField("Economy", game.getEconomy(), new Dimension(250, 35), guiUpdateTimer);
		topBar.add(economyLabel);
		topBar.add(Box.createHorizontalGlue());

		// Bottom Panel
		bottomBar = new HorizontalPanel("bottom", 35);
		getContentPane().add(bottomBar, BorderLayout.PAGE_END);

		// Center Map
		map = new GameMap(game.getBoard(), clickDataBox, guiUpdateTimer);
		getContentPane().add(map, BorderLayout.CENTER);
		
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
				return;
			}
			public void keyReleased(KeyEvent k) {
				return;
			}

			public void keyTyped(KeyEvent k) {

				/*
				 * XXX: This needs some refactoring. Most of these shouldn't be
				 * popups. Seems broken right now entirely.
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
					speedButton.doClick();
				}
			}
		});
		
		simulationTimer.start();
	}

}

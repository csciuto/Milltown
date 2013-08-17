package sciuto.corey.milltown.map.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.map.swing.components.*;

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

	private static final int DEFAULT_MAP_PX = 625;

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
	private final JScrollPane mapScrollPane;

	private final VerticalPanel leftBox;
	private final JScrollPane buildingSelectorScrollPane;
	private final BuildingSelector buildingSelector;

	private final VerticalPanel rightBox;
	private final MultiLineTextField clickDataBox;

	private final HorizontalPanel topBar;
	private final SingleLineTextField dateLabel;
	private final SingleLineTextField populationLabel;
	private final SingleLineTextField moneyLabel;
	private final SingleLineTextField economyLabel;
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
		buildingSelector = new BuildingSelector();
		buildingSelectorScrollPane = new JScrollPane(buildingSelector, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		buildingSelectorScrollPane.setMaximumSize(new Dimension(150, 300));

		leftBox.add(buildingSelectorScrollPane);

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
		dateLabel = new SingleLineTextField("Date", game.getGameDate(), new Dimension(150, 35), guiUpdateTimer);
		topBar.add(dateLabel);

		topBar.add(Box.createHorizontalStrut(50));
		populationLabel = new SingleLineTextField("Population", game.getPopulation(), new Dimension(250, 35),
				guiUpdateTimer);
		topBar.add(populationLabel);

		topBar.add(Box.createHorizontalStrut(10));
		moneyLabel = new SingleLineTextField("Funds", game.getMoney(), new Dimension(250, 35), guiUpdateTimer);
		topBar.add(moneyLabel);

		topBar.add(Box.createHorizontalStrut(10));
		economyLabel = new SingleLineTextField("Economy", game.getEconomy(), new Dimension(250, 35), guiUpdateTimer);
		topBar.add(economyLabel);
		topBar.add(Box.createHorizontalGlue());

		// Bottom Panel
		bottomBar = new HorizontalPanel("bottom", 35);
		getContentPane().add(bottomBar, BorderLayout.PAGE_END);

		// Center Map
		map = new GameMap(game.getBoard(), DEFAULT_MAP_PX, clickDataBox, guiUpdateTimer);
		mapScrollPane = new GameMapScrollPane(map);
		mapScrollPane.setMaximumSize(new Dimension(DEFAULT_MAP_PX, DEFAULT_MAP_PX));
		getContentPane().add(mapScrollPane, BorderLayout.CENTER);

		/*
		 * LISTENERS
		 */

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent k) {
				if (k.getID() == KeyEvent.KEY_PRESSED) {
					/*
					 * TODO: This needs some refactoring
					 */

					// Message dialogs stop the timer - we need to know if we
					// should
					// restart it.
					boolean timerRunning = simulationTimer.isRunning();

					if (k.getKeyCode() == KeyEvent.VK_P) {
						if (timerRunning) {
							simulationTimer.stop();
							JOptionPane.showMessageDialog(null, "Simulation: OFF");
						} else {
							simulationTimer.stop();
							JOptionPane.showMessageDialog(null, "Simulation: ON.");
							simulationTimer.start();
						}
					}
					if (k.getKeyCode() == KeyEvent.VK_H) {
						simulationTimer.stop();
						JOptionPane.showMessageDialog(null, HELP_MSG);
						if (timerRunning) {
							simulationTimer.start();
						}
					} else if (k.getKeyCode() == KeyEvent.VK_S) {
						speedButton.doClick();
					} else if (k.getKeyCode() == KeyEvent.VK_LEFT) {
						JScrollBar sb = mapScrollPane.getHorizontalScrollBar();
						int change = sb.getMaximum() / game.getBoard().getBoardSize();
						int newVal = sb.getValue() - change;
						if (newVal >= 0) {
							sb.setValue(newVal);
						} else {
							sb.setValue(0);
						}
					} else if (k.getKeyCode() == KeyEvent.VK_RIGHT) {
						JScrollBar sb = mapScrollPane.getHorizontalScrollBar();
						int change = sb.getMaximum() / game.getBoard().getBoardSize();
						int newVal = sb.getValue() + change;
						if (newVal < sb.getMaximum()) {
							sb.setValue(newVal);
						} else {
							sb.setValue(sb.getMaximum());
						}
					} else if (k.getKeyCode() == KeyEvent.VK_UP) {
						JScrollBar sb = mapScrollPane.getVerticalScrollBar();
						int change = sb.getMaximum() / game.getBoard().getBoardSize();
						int newVal = sb.getValue() - change;
						if (newVal >= 0) {
							sb.setValue(newVal);
						} else {
							sb.setValue(0);
						}
					} else if (k.getKeyCode() == KeyEvent.VK_DOWN) {
						JScrollBar sb = mapScrollPane.getVerticalScrollBar();
						int change = sb.getMaximum() / game.getBoard().getBoardSize();
						int newVal = sb.getValue() + change;
						if (newVal< sb.getMaximum()) {
							sb.setValue(newVal);
						} else {
							sb.setValue(0);
						}
					}

					return false;
				} else {
					return true;
				}
			}
		});

		simulationTimer.start();
	}

}

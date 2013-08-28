package sciuto.corey.milltown.map.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.map.swing.components.*;

/**
 * Runs the simulator and displays the output. Also accepts the input.
 * 
 * @author Corey
 * 
 */
public class MainScreen extends JFrame {

	private static final long serialVersionUID = -4357328123100158183L;

	private static final int DEFAULT_MAP_PX = 625;

	private static final Logger LOGGER = Logger.getLogger(MainScreen.class);

	private static MainScreen mainScreen = null;

	/*
	 * ENGINE ELEMENTS
	 */

	private Game game;

	/*
	 * GUI COMPONENTS
	 */

	/**
	 * Runs the simulation itself
	 */
	private final Timer simulationTimer;

	/**
	 * Stores the keyboard input controller.
	 */
	private KeyEventDispatcher keyDispatcher;

	/**
	 * Updates the GUI. Graphics that need to update register to it.
	 */
	private final Timer guiUpdateTimer;

	private final UIGameMap map;
	private final GameMapScrollPane mapScrollPane;

	private final VerticalPanel leftBox;
	private final JScrollPane buildingSelectorScrollPane;
	private final ToolSelector toolSelector;

	private final VerticalPanel rightBox;
	private final MultiLineTextField queryBox;

	private final HorizontalPanel topBar;
	private final SingleLineTextField dateLabel;
	private final SingleLineTextField populationLabel;
	private final SingleLineTextField moneyLabel;
	private final SingleLineTextField economyLabel;
	private final SpeedButton speedButton;

	private final HorizontalPanel bottomBar;

	public Timer getGuiUpdateTimer() {
		return guiUpdateTimer;
	}

	public MultiLineTextField getQueryBox() {
		return queryBox;
	}

	public GameMapScrollPane getMapScrollPane() {
		return mapScrollPane;
	}

	public ToolSelector getToolSelector() {
		return toolSelector;
	}

	public Game getGame() {
		return game;
	}

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
	 * Turns off the keyboard dispatcher
	 */
	public void disableKeyEvents() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyDispatcher);
	}

	/**
	 * Turns on the keyboard dispatcher
	 */
	public void enableKeyEvents() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyDispatcher);
	}

	/**
	 * Creates the singleton main screen object
	 * 
	 * @param g
	 */
	public static MainScreen createMainScreen(Game g) {
		mainScreen = new MainScreen(g);
		return mainScreen;
	}

	/**
	 * Reinitializes all state and loads a new game.
	 * 
	 * @param g
	 */
	public static void loadMainScreen(Game g) {
		mainScreen.setVisible(false);
		mainScreen = null;
		System.gc();
		
		mainScreen = new MainScreen(g);
		mainScreen.setVisible(true);
		
	}

	/**
	 * Returns the singleton.
	 * 
	 * @return
	 */
	public static MainScreen instance() {
		return mainScreen;
	}

	/**
	 * Renders Game in the Main Screen and starts the simulation
	 */
	private MainScreen(Game g) {

		super("Milltown");

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
		MainMenu mainMenu = new MainMenu();
		this.setJMenuBar(mainMenu);

		// Left Panel - selection
		leftBox = new VerticalPanel("left", 150);
		getContentPane().add(leftBox, BorderLayout.LINE_START);
		toolSelector = new ToolSelector();
		buildingSelectorScrollPane = new JScrollPane(toolSelector, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		buildingSelectorScrollPane.setMaximumSize(new Dimension(150, 450));

		leftBox.add(buildingSelectorScrollPane);

		// Right Panel - info
		rightBox = new VerticalPanel("right", 221);
		getContentPane().add(rightBox, BorderLayout.LINE_END);

		queryBox = new MultiLineTextField("Current Selection", 221, 100);
		rightBox.add(queryBox);

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

		topBar.add(Box.createHorizontalStrut(20));
		topBar.add(new ZoomButton(ZoomButton.Direction.OUT));
		topBar.add(Box.createHorizontalStrut(5));
		topBar.add(new JLabel("Zoom"));
		topBar.add(Box.createHorizontalStrut(5));
		topBar.add(new ZoomButton(ZoomButton.Direction.IN));

		topBar.add(Box.createHorizontalGlue());

		// Bottom Panel
		bottomBar = new HorizontalPanel("bottom", 35);
		getContentPane().add(bottomBar, BorderLayout.PAGE_END);

		// Center Map
		map = new UIGameMap(game.getBoard(), DEFAULT_MAP_PX);
		mapScrollPane = new GameMapScrollPane(map);
		mapScrollPane.setMaximumSize(new Dimension(DEFAULT_MAP_PX, DEFAULT_MAP_PX));
		getContentPane().add(mapScrollPane, BorderLayout.CENTER);

		/*
		 * LISTENERS
		 */

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				LOGGER.info("***** Quitting game. *****");
				System.exit(0);
			}
		});

		keyDispatcher = new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent k) {
				if (k.getID() == KeyEvent.KEY_PRESSED) {
					if (k.getKeyCode() == KeyEvent.VK_S && k.isAltDown()) {
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
						if (newVal < sb.getMaximum()) {
							sb.setValue(newVal);
						} else {
							sb.setValue(0);
						}
					} else if (k.getKeyCode() == KeyEvent.VK_ESCAPE) {
						map.turnOffAllTools();
					} else if (k.getKeyCode() == KeyEvent.VK_EQUALS && k.isAltDown()) {
						MainScreen.this.getMapScrollPane().doZoom(-1);
					} else if (k.getKeyCode() == KeyEvent.VK_MINUS && k.isAltDown()) {
						MainScreen.this.getMapScrollPane().doZoom(1);
					}

					return false;
				} else {
					return true;
				}
			}
		};
		this.enableKeyEvents();

		simulationTimer.start();
	}

}

package sciuto.corey.milltown.map.swing.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import sciuto.corey.milltown.engine.BuildingConstructor;
import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.PropertiesReader;
import sciuto.corey.milltown.map.swing.BuildingGraphicsRetriever;
import sciuto.corey.milltown.map.swing.ErrorMessageBox;
import sciuto.corey.milltown.map.swing.MainScreen;
import sciuto.corey.milltown.map.swing.SquareMapper;
import sciuto.corey.milltown.map.swing.components.tools.BuildingToolButton;
import sciuto.corey.milltown.map.swing.components.tools.BulldozerToolButton;
import sciuto.corey.milltown.map.swing.components.tools.ToolButton;
import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Land;

/**
 * The main map
 * 
 * @author Corey
 * 
 */
public class UIGameMap extends JPanel implements ActionListener, Scrollable {

	private static final long serialVersionUID = -6881706563458253977L;

	private static final Logger LOGGER = Logger.getLogger(UIGameMap.class);

	protected final Game game;
	protected final BuildingConstructor buildingConstructor;
	protected final SquareMapper squareMapper;
	protected final Dimension preferredViewportSize;
	protected final BuildingGraphicsRetriever graphicsRetriever;

	/**
	 * The tile to highlight Set the active tile to null to hide the highlight.
	 */
	private Tile activeTile = null;

	/**
	 * To help with selection box drawing on mouse move.
	 */
	AbstractBuilding cachedBuilding;
	private Tile hoveredTile = null;

	/**
	 * The current size of the square on the board in pixels
	 */
	protected int squareSize;

	/**
	 * A property that turns off the ground rendering.
	 */
	private boolean disableGround = Boolean.parseBoolean(PropertiesReader.read("milltown.properties").getProperty(
			"milltown.disable.ground"));

	/**
	 * A helper class for handling mouse clicks
	 * 
	 * @author Corey
	 * 
	 */
	protected class MouseListener extends MouseInputAdapter {

		/**
		 * Builds the building.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				Tile clickedTile = squareMapper.mapSquare(e);

				ToolButton activeButton = MainScreen.instance().getToolSelector().getSelectedButton();
				if (activeButton instanceof BuildingToolButton) {
					// Build if there is a selected building.
					Class<? extends AbstractBuilding> classToBuild = MainScreen.instance().getToolSelector()
							.getBuildingToBuild();
					if (classToBuild != null) {
						UIGameMap.this.buildOnTile(clickedTile, classToBuild);
					}
				} else if (activeButton instanceof BulldozerToolButton) {
					UIGameMap.this.demolishBuilding(clickedTile);
				} else {
					UIGameMap.this.activateTile(clickedTile);
				}
			} else if (e.getButton() == MouseEvent.BUTTON3) {
				// Turn off all tools and queries.
				MainScreen.instance().getToolSelector().activateQueryTool();
				UIGameMap.this.turnOffSelectionTool();
			}

		}

		/**
		 * Moves the highlight location
		 */
		@Override
		public void mouseMoved(MouseEvent e) {

			ToolButton activeButton = MainScreen.instance().getToolSelector().getSelectedButton();

			if (activeButton instanceof BuildingToolButton) {
				Class<? extends AbstractBuilding> classToBuild = MainScreen.instance().getToolSelector()
						.getBuildingToBuild();
				Tile oldTile = hoveredTile;
				repaintTiles(oldTile);

				hoveredTile = squareMapper.mapSquare(e);
				if (cachedBuilding == null || !cachedBuilding.getClass().equals(classToBuild)) {
					try {
						cachedBuilding = classToBuild.newInstance();
					} catch (Exception ex) {
						LOGGER.error("Exception in MouseMoved", ex);
						return;
					}
				}
				cachedBuilding.setRootTile(hoveredTile);
				repaintTiles(hoveredTile);
			} else if (activeButton instanceof BulldozerToolButton) {
				Tile oldTile = hoveredTile;
				repaintTiles(oldTile);

				hoveredTile = squareMapper.mapSquare(e);
				try {
					cachedBuilding = hoveredTile.getContents();
				} catch (Exception ex) {
					LOGGER.error("Exception in MouseMoved", ex);
					return;
				}

				hoveredTile = squareMapper.mapSquare(e);
				hoveredTile = hoveredTile.getContents().getRootTile();
				repaintTiles(hoveredTile);
			}
		}

		/**
		 * Turns off the selection highlight.
		 */
		@Override
		public void mouseExited(MouseEvent e) {
			repaintTiles(hoveredTile);
			cachedBuilding = null;
			hoveredTile = null;
		}
	};

	public UIGameMap(final Game g, final int mapDisplaySize) {
		super();

		this.game = g;
		GameBoard board = g.getBoard();
		this.squareSize = 1000 / board.getBoardSize();
		this.squareMapper = new SquareMapper(board, this);
		this.buildingConstructor = new BuildingConstructor(g);
		this.preferredViewportSize = new Dimension(mapDisplaySize, mapDisplaySize);
		this.graphicsRetriever = new BuildingGraphicsRetriever();

		setName("mainMap");
		setBackground(new Color(0, 200, 0));
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(1000, 1000));

		MouseInputListener mouseListener = new MouseListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == MainScreen.instance().getGuiUpdateTimer()) {
			/*
			 * If the timer clicked over, update any tiles that need to be redrawn.
			 */
			Set<Tile> dirtyTiles = game.getTileStateManager().getDirtyTiles();
			for (Tile t : dirtyTiles){
				repaintTiles(t);
			}
			game.getTileStateManager().clearDirtyTiles();
		}
	}

	public int getSquareSize() {
		return squareSize;
	}

	public BuildingGraphicsRetriever getGraphicsRetriever() {
		return graphicsRetriever;
	}

	protected void setSquareSizeAndUpdateMap(int size) {
		GameBoard board = game.getBoard();
		squareSize = size;
		Dimension dimensions = new Dimension(squareSize * board.getBoardSize(), squareSize * board.getBoardSize());
		this.setSize(dimensions);
		this.setPreferredSize(dimensions);
		squareMapper.update();
		repaint();
	}

	/**
	 * Sets the activeTile state to the passed-in tile and updates the UI.
	 * 
	 * @param t
	 */
	protected void activateTile(Tile t) {
		Tile oldTile = activeTile;
		repaintTiles(oldTile);
		activeTile = t;
		if (activeTile == null) {
			// Clicked outside of the map.
			return;
		}
		repaintTiles(activeTile);
		MainScreen.instance().getQueryBox().setObject(activeTile);
	}

	/**
	 * Builds classToBuild on the passed-in tile.
	 * 
	 * @param tile
	 * @param classToBuild
	 */
	protected void buildOnTile(Tile tile, Class<? extends AbstractBuilding> classToBuild) {

		try {
			buildingConstructor.build(tile, classToBuild.newInstance());
			turnOffSelectionTool();
			repaintTiles(tile);
		} catch (Exception ex) {
			String msg = String.format("Error building %s on tile %s", classToBuild.toString(), activeTile.toString());
			LOGGER.error(msg, ex);
			ErrorMessageBox.show(msg);
		}
	}

	protected void demolishBuilding(Tile clickedTile) {

		buildingConstructor.demolish(clickedTile);
		turnOffSelectionTool();
		hoveredTile = clickedTile;
		cachedBuilding = hoveredTile.getContents();
		repaintTiles(hoveredTile);
	}

	/**
	 * Used to redraw whatever was clicked on.
	 * 
	 * @param t
	 *            The tile that was acted on.
	 */
	private void repaintTiles(Tile t) {
		if (t != null) {

			AbstractBuilding b = t.getContents();
			Tile rootTile = b.getRootTile();

			// Pad the repaint to get rid of artifacts...
			repaint(rootTile.getXLoc() * squareSize - 2, rootTile.getYLoc() * squareSize - 2, squareSize * 3 + 4,
					squareSize * 3 + 4);
		}
	}

	protected void turnOffSelectionTool() {

		repaintTiles(hoveredTile);
		hoveredTile = null;
		cachedBuilding = null;

		Tile oldTile = activeTile;
		repaintTiles(oldTile);
		activeTile = null;

		MainScreen.instance().getQueryBox().setObject("");

	}

	/**
	 * Puts the state in query mode.
	 */
	public void turnOffAllTools() {
		MainScreen.instance().getToolSelector().activateQueryTool();
		turnOffSelectionTool();
	}

	@Override
	/**
	 * Draws the whole board.
	 */
	protected void paintComponent(Graphics g) {

		GameBoard board = game.getBoard();
		
		super.paintComponent(g);

		// Draw the board itself.
		if (board == null) {
			// Not ready to render yet.
			return;
		}
		int dimension = board.getBoardSize();

		// First the gridlines...
		int currentX = 0;
		int currentY = 0;
		for (int j = 0; j < dimension; j++) {
			for (int i = 0; i < dimension; i++) {
				g.setColor(new Color(0, 127, 0));
				g.drawRect(currentX, currentY, squareSize, squareSize);
				currentX += squareSize;
			}
			currentY += squareSize;
			currentX = 0;
		}

		// Then the buildings
		currentX = 0;
		currentY = 0;
		for (int j = 0; j < dimension; j++) {
			for (int i = 0; i < dimension; i++) {

				Tile t = board.getTile(i, j);
				AbstractBuilding b = t.getContents();

				if (t.equals(b.getRootTile())) {
					if (!disableGround) {
						// draw the grass first.
						BufferedImage img = graphicsRetriever.retrieveImage(t);
						// subtract from the edges so borders print.
						g.drawImage(img, currentX + 1, currentY + 1, squareSize * b.getSize().getLeft() - 2, squareSize
								* b.getSize().getRight() - 2, null);
					} 
					if (!(b instanceof Land)){
						BufferedImage img = graphicsRetriever.retrieveImage(t);
						// subtract from the edges so borders print.
						g.drawImage(img, currentX + 1, currentY + 1, squareSize * b.getSize().getLeft() - 2, squareSize
								* b.getSize().getRight() - 2, null);
					}
				}
				currentX += squareSize;
			}
			currentY += squareSize;
			currentX = 0;
		}

		if (activeTile != null) {
			// Draw the highlight box
			AbstractBuilding b = activeTile.getContents();

			Graphics2D g2 = (Graphics2D) g;
			drawHighlighter(g2, b.getRootTile(), b.getSize(), Color.YELLOW, 0.3f);
		}

		if (hoveredTile != null && cachedBuilding != null) {
			// Draw the selection box.
			AbstractBuilding b = cachedBuilding;
			Tile t = b.getRootTile();

			Graphics2D g2 = (Graphics2D) g;

			if (MainScreen.instance().getToolSelector().getSelectedButton() instanceof BulldozerToolButton) {
				drawHighlighter(g2, b.getRootTile(), b.getSize(), Color.RED, 0.6f);
			} else {
				if (buildingConstructor.canBuild(t, cachedBuilding)) {
					drawHighlighter(g2, b.getRootTile(), b.getSize(), Color.BLUE, 0.3f);
				} else {
					drawHighlighter(g2, b.getRootTile(), b.getSize(), Color.RED, 0.6f);
				}
			}
		}
	}

	/**
	 * Draws a semi-opaque box around the tile of the specified size, color, and
	 * transparency.
	 * 
	 * @param g2
	 * @param t
	 * @param d
	 * @param color
	 * @param transparency
	 */
	private void drawHighlighter(Graphics2D g2, Tile t, Pair<Integer, Integer> d, Color color, float transparency) {
		g2.setColor(color);
		g2.drawRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * d.getLeft(),
				squareSize * d.getRight());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, transparency));
		g2.fillRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * d.getLeft(),
				squareSize * d.getRight());
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return preferredViewportSize;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return squareSize;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return squareSize * 10;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
}

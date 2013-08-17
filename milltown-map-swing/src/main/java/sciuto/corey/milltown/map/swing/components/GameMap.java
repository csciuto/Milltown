package sciuto.corey.milltown.map.swing.components;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import sciuto.corey.milltown.engine.BuildingConstructor;
import sciuto.corey.milltown.map.swing.SquareMapper;
import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Mill;

/**
 * The main map
 * 
 * @author Corey
 * 
 */
public class GameMap extends JPanel implements ActionListener, Scrollable {

	protected final GameBoard board;
	protected final Timer timer;
	protected final BuildingConstructor buildingConstructor;
	protected final SquareMapper squareMapper;
	protected final MultiLineTextField selectionPanel;
	protected final Dimension preferredViewportSize;

	/**
	 * The tile to highlight Set the active tile to null to hide the highlight.
	 */
	private Tile activeTile = null;

	/**
	 * The size of the map in pixels
	 */
	protected int squareSize;

	/**
	 * A helper class for handling mouse clicks
	 * 
	 * @author Corey
	 * 
	 */
	protected class MouseClickListener extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			Tile oldTile = activeTile;
			repaintTiles(e.getComponent(), oldTile);

			activeTile = squareMapper.mapSquare(e);
			if (activeTile == null) {
				// Clicked outside of the map.
				return;
			}

			if (e.getButton() == MouseEvent.BUTTON3) {
				buildingConstructor.build(activeTile, new Mill());
			}

			repaintTiles(e.getComponent(), activeTile);

			selectionPanel.setText(activeTile.toString());
		}

		/**
		 * Used to redraw whatever was clicked on.
		 * 
		 * @param c
		 *            The component to repaint (that is, this board
		 * @param t
		 *            The tile that was acted on.
		 */
		protected void repaintTiles(Component c, Tile t) {
			if (t != null) {

				AbstractBuilding b = t.getContents();
				Tile rootTile = b.getRootTile();

				// Pad the repaint to get rid of artifacts...
				c.repaint(rootTile.getXLoc() * squareSize - 2, rootTile.getYLoc() * squareSize - 2, squareSize
						* b.getSize().getLeft() + 4, squareSize * b.getSize().getRight() + 4);
			}
		}
	};

	public GameMap(final GameBoard b, final int mapDisplaySize, final MultiLineTextField selectionPanel, final Timer timer) {
		super();

		this.board = b;
		this.squareSize = calculateSquareSize(1024, board.getBoardSize());
		this.squareMapper = new SquareMapper(board, this);
		this.buildingConstructor = new BuildingConstructor(board);
		this.selectionPanel = selectionPanel;
		this.preferredViewportSize = new Dimension(mapDisplaySize, mapDisplaySize);

		setName("mainMap");
		setBackground(new Color(0, 255, 0));
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(1024,1024));
		
		MouseInputListener mouseListener = new MouseClickListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);

		this.timer = timer;

	}

	public int getSquareSize() {
		return squareSize;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			// TODO: Don't repaint the whole board...check to see what's dirty.
			// Only need this if buildings change due to timer...
			// repaint();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Draw the board itself.
		if (board != null) {
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
						Class<? extends AbstractBuilding> buildingClass = b.getClass();
						if (buildingClass.equals(Mill.class)) {
							String fileName = "/map_images/mill.png";
							BufferedImage img = null;
							URL url = this.getClass().getResource(fileName);
							if (url == null) {
								JOptionPane.showMessageDialog(null, String.format("Cannot find image %s", fileName));
								System.exit(-1);
							}
							try {
								img = ImageIO.read(url);
							} catch (IOException e) {
								JOptionPane.showMessageDialog(null, String.format("Cannot render image: % ", url));
								System.exit(-1);
							}
							// subtract from the edges so borders print.
							g.drawImage(img, currentX + 1, currentY + 1, squareSize * b.getSize().getLeft() - 2,
									squareSize * b.getSize().getRight() - 2, null);
						}
					}
					currentX += squareSize;
				}
				currentY += squareSize;
				currentX = 0;
			}
		}

		// Draw the highlight box.
		if (activeTile != null) {
			AbstractBuilding b = activeTile.getContents();
			Tile t = b.getRootTile();

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.MAGENTA);
			g2.drawRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * b.getSize().getLeft(),
					squareSize * b.getSize().getRight());
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.3f));
			g2.fillRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * b.getSize().getLeft(),
					squareSize * b.getSize().getRight());
		}
	}

	private int calculateSquareSize(final int mapSize, final int boardSize) {
		return mapSize / boardSize;
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
		// TODO Auto-generated method stub
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

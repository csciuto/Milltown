package sciuto.corey.milltown.map.swing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import sciuto.corey.milltown.engine.BuildingConstructor;
import sciuto.corey.milltown.engine.GameBoard;
import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Land;
import sciuto.corey.milltown.model.buildings.Mill;

/**
 * The main map
 * 
 * @author Corey
 * 
 */
public class GameMap extends JPanel implements ActionListener {

	/**
	 * The size of the map in pixels
	 */
	public static final int MAP_SIZE_PX = 625;
	private final int squareSize;
	
	private final GameBoard board;
	private final Timer timer;
	private final BuildingConstructor buildingConstructor;
	private final SquareMapper squareMapper;

	/*
	 * The tile to highlight Set the active tile to null to hide the highlight.
	 */
	private Tile activeTile = null;

	public int getSquareSize() {
		return squareSize;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			// TODO: Don't repaint the whole board...check to see what's dirty.
			// Only need this if buildings change due to timer...
			//repaint();
		}
	}

	public GameMap(final GameBoard b, final MultiLineDisplay selectionPanel, final Timer timer) {
		super();

		board = b;

		squareSize = (MAP_SIZE_PX) / board.getBoardSize();

		squareMapper = new SquareMapper(board, MAP_SIZE_PX);

		buildingConstructor = new BuildingConstructor(board);

		setName("mainMap");
		setBackground(new Color(0, 255, 0));
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(MAP_SIZE_PX, MAP_SIZE_PX));

		MouseInputListener listener = new MouseInputAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tile oldTile = activeTile;
				activeTile = squareMapper.mapSquare(e);

				if (e.getButton() == MouseEvent.BUTTON3) {
					buildingConstructor.build(activeTile, new Mill());
				}

				repaintTiles(e.getComponent(), oldTile);
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

					c.repaint(rootTile.getXLoc() * squareSize, rootTile.getYLoc() * squareSize, squareSize * b.getSize().getLeft(),
							squareSize * b.getSize().getRight());
				}
			}
		};
		addMouseListener(listener);
		addMouseMotionListener(listener);

		this.timer = timer;

	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		// Draw the board itself.
		if (board != null) {
			int currentX = 0;
			int currentY = 0;

			int dimension = board.getBoardSize();

			for (int j = 0; j < dimension; j++) {
				for (int i = 0; i < dimension; i++) {

					Tile t = board.getTile(i, j);
					AbstractBuilding b = t.getContents();

					if (t.equals(b.getRootTile())) {
						Class<? extends AbstractBuilding> buildingClass = b.getClass();
						if (buildingClass.equals(Land.class)){
							g.setColor(new Color(0, 127, 0));
							g.drawRect(currentX, currentY, squareSize, squareSize);
						}
						else if (buildingClass.equals(Mill.class)) {
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
							g.drawImage(img, currentX, currentY, squareSize * b.getSize().getLeft(), squareSize
									* b.getSize().getRight(), null);
						}
					}
					currentX += squareSize;
				}

				currentY += squareSize;
				currentX = 0;

			}
		}

		// Draw the highlight box.
		// TODO: This is complex enough for a method and a unit test
		// Especially as its buggy.
		if (activeTile != null) {
			AbstractBuilding b = activeTile.getContents();
			Tile t = b.getRootTile();

			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.PINK);
			g2.drawRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * b.getSize().getLeft(),
					squareSize * b.getSize().getRight());
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
			g2.fillRect(t.getXLoc() * squareSize, t.getYLoc() * squareSize, squareSize * b.getSize().getLeft(),
					squareSize * b.getSize().getRight());
		}
	}
}

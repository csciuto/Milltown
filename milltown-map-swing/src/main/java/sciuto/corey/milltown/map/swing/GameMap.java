package sciuto.corey.milltown.map.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import sciuto.corey.milltown.engine.GameBoard;
import sciuto.corey.milltown.model.board.Tile;

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

	private final GameBoard board;

	private final int squareSize;

	private Timer t;
	
	public int getSquareSize() {
		return squareSize;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t){
			repaint();
		}
	}

	public GameMap(GameBoard b, Timer t) {
		super();

		board = b;

		squareSize = (MAP_SIZE_PX - board.getBoardSize()) / board.getBoardSize();
		
		setName("mainMap");
		setBackground(new Color(0, 127, 0));
		setBorder(BorderFactory.createEtchedBorder());
		setPreferredSize(new Dimension(MAP_SIZE_PX, MAP_SIZE_PX));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Tile activeTole = SquarePicker.mapSquare(e, board, this);
			}
		});
		
		this.t = t;
	}

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		if (board != null) {
			int currentX = 1;
			int currentY = 1;

			int dimension = board.getBoardSize();
			
			for (int j = 0; j < dimension; j++) {
				for (int i = 0; i < dimension; i++) {

					g.setColor(new Color(0, 255, 0));
					g.fillRect(currentX, currentY, squareSize, squareSize);

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
					g.drawImage(img, currentX, currentY, squareSize, squareSize, null);

					currentX += squareSize + 1;
				}

				currentY += squareSize + 1;
				currentX = 1;

			}
		}
	}
}

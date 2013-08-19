package sciuto.corey.milltown.map.swing.components;

import java.awt.Color;
import java.awt.event.MouseWheelEvent;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class GameMapScrollPane extends JScrollPane {

	private static final long serialVersionUID = -806043179505901461L;

	private final GameMap map;

	public GameMapScrollPane(GameMap map) {
		super(map);
		this.map = map;

		JViewport viewport = this.getViewport();

		viewport.setAlignmentX(CENTER_ALIGNMENT);
		viewport.setAlignmentY(CENTER_ALIGNMENT);
		viewport.setBackground(Color.DARK_GRAY);
	}

	@Override
	/**
	 * Zooms the map - this algorithm focuses around the upper-left corner of the screen.
	 */
	protected void processMouseWheelEvent(MouseWheelEvent e) {

		int rotation = e.getWheelRotation();
		for (int i = 1; i <= Math.abs(rotation); i++) {
			double mapWidthPre = map.getWidth();
			double mapHeightPre = map.getHeight();

			if (rotation < 0) {
				map.setSquareSizeAndUpdateMap((int) Math.round(map.squareSize + map.squareSize * 0.10));
			} else {
				map.setSquareSizeAndUpdateMap((int) Math.round(map.squareSize - map.squareSize * 0.10));
			}

			double mapWidthPost = map.getWidth();
			double mapHeightPost = map.getHeight();

			double newX = (map.getX() / mapWidthPre) * mapWidthPost;
			double newY = (map.getY() / mapHeightPre) * mapHeightPost;

			map.setLocation((int) Math.round(newX), (int) Math.round(newY));
		}
	}

}

package sciuto.corey.milltown.map.swing.components;

import java.awt.Color;
import java.awt.event.MouseWheelEvent;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

public class GameMapScrollPane extends JScrollPane {

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
	 * Delegates to the map.
	 */
	protected void processMouseWheelEvent(MouseWheelEvent e) {
		int rotation = e.getWheelRotation();
		if (rotation < 0) {
			for (int i = 1; i <= Math.abs(rotation); i++) {
				map.setSquareSizeAndUpdateMap((int) Math.round(map.squareSize + map.squareSize * 0.10));
			}
		} else {
			for (int i = 1; i <= Math.abs(rotation); i++) {
				map.setSquareSizeAndUpdateMap((int) Math.round(map.squareSize - map.squareSize * 0.10));
			}
		}
	}

}

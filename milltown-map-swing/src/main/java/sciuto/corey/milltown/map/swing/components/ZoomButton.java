package sciuto.corey.milltown.map.swing.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import sciuto.corey.milltown.map.swing.MainScreen;

public class ZoomButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 8414828730237042151L;

	public enum Direction {
		IN, OUT
	};

	private final Direction direction;

	public ZoomButton(Direction d) {
		direction = d;
		this.addActionListener(this);
		this.setFocusable(false);

		if (d == Direction.IN) {
			this.setText("+");
		} else {
			this.setText("-");
		}
	}

	public Direction getDirection() {
		return direction;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this) {
			if (this.direction == Direction.IN) {
				MainScreen.instance().getMapScrollPane().doZoom(-1);
			} else {
				MainScreen.instance().getMapScrollPane().doZoom(1);
			}
		}
	}

}

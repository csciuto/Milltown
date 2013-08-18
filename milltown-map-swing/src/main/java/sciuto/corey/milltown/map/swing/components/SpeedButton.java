package sciuto.corey.milltown.map.swing.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import sciuto.corey.milltown.map.swing.Speed;

public class SpeedButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531319618899226477L;

	private final Timer simulationTimer;

	private Speed speed;

	public SpeedButton(Dimension buttonSize, Timer simulationTimer) {
		this.simulationTimer = simulationTimer;
		this.speed = Speed.MEDIUM;
		this.setText(this.speed.getSpeedName());
		this.setFocusable(false);
		this.setPreferredSize(buttonSize);
		this.setMinimumSize(buttonSize);
		this.setToolTipText("Speed");
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this) {

			switch (this.speed) {
			case SLOW:
				this.speed = Speed.MEDIUM;
				break;
			case MEDIUM:
				this.speed = Speed.FAST;
				break;
			case FAST:
				this.speed = Speed.WICKED_FAST;
				break;
			case WICKED_FAST:
				this.speed = Speed.PAUSE;
				break;
			case PAUSE:
				this.speed = Speed.SLOW;
				break;
			}

			this.setText(this.speed.getSpeedName());

			if (this.speed.getSpeedInMilliseconds() >= 0) {
				this.simulationTimer.setDelay(this.speed.getSpeedInMilliseconds());
				this.simulationTimer.start();
			} else {
				this.simulationTimer.stop();
			}

		}
	}
}

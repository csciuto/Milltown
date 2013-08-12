package sciuto.corey.milltown.map.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

public class SpeedButton extends JButton implements ActionListener {

	private final Timer simulationTimer;

	private enum Speed {
		PAUSE("PAUSE", -1), SLOW("SLOW", 10000), MEDIUM("MEDIUM", 5000), FAST("FAST", 1000), WICKED_FAST("WICKED FAST",
				500);

		private final String speedName;
		private final int speedInMilliseconds;

		private Speed(String txt, int speedInMilliseconds) {
			this.speedName = txt;
			this.speedInMilliseconds = speedInMilliseconds;
		}
	};

	private Speed speed;

	public SpeedButton(Dimension buttonSize, Timer simulationTimer) {
		this.simulationTimer = simulationTimer;
		this.speed = Speed.MEDIUM;
		this.setText(this.speed.speedName);
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
			
			this.setText(this.speed.speedName);
			
			if (this.speed.speedInMilliseconds >= 0){
				this.simulationTimer.setDelay(this.speed.speedInMilliseconds);
				this.simulationTimer.start();
			}
			else {
				this.simulationTimer.stop();
			}

		}
	}
}

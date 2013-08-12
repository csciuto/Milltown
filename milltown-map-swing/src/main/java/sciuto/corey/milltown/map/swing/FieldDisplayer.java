package sciuto.corey.milltown.map.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Displays a field using a name and the associated Object's toString() method.
 * @author Corey
 *
 */
public class FieldDisplayer extends JLabel implements ActionListener {

	private Object field;
	private String displayName;
	private Timer t;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t){
			setText(String.format("%s:  %s", displayName, field));
		}
	}
	
	/**
	 * Creates a FieldDisplayer
	 * @param displayName The name to display the field as
	 * @param field The field to call toString on
	 * @param The timer to listen to updates from.
	 */
	public FieldDisplayer(String displayName, Object field, Timer t){
		this.displayName = displayName;
		this.field = field;
		this.t = t;
		t.addActionListener(this);
	}
}

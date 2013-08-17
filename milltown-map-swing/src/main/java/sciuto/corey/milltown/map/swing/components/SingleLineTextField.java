package sciuto.corey.milltown.map.swing.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * Displays a field using a name and the associated Object's toString() method.
 * @author Corey
 *
 */
public class SingleLineTextField extends JLabel implements ActionListener {

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
	 * @param labelSize The preferred size of the label
	 * @param The timer to listen to updates from.
	 */
	public SingleLineTextField(String displayName, Object field, Dimension labelSize, Timer t){
		this.displayName = displayName;
		this.field = field;
		this.t = t;
		
		this.setPreferredSize(labelSize);
		
		setText(String.format("%s:  %s", displayName, field));
		
		t.addActionListener(this);
	}
	
	/**
	 * Creates a FieldDisplayer
	 * @param displayName The name to display the field as
	 * @param field The field to call toString on
	 * @param The timer to listen to updates from.
	 */
	public SingleLineTextField(String displayName, Object field, Timer t){
		this.displayName = displayName;
		this.field = field;
		this.t = t;
		
		setText(String.format("%s:  %s", displayName, field));
		
		t.addActionListener(this);
	}
}

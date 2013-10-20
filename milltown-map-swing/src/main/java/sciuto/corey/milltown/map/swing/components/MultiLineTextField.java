package sciuto.corey.milltown.map.swing.components;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;

public class MultiLineTextField extends JTextArea implements ActionListener {

	private static final long serialVersionUID = -6896691785187294050L;

	private Timer t;
	
	private Object field;
	
	public MultiLineTextField(String name, int xSize, int ySize, Timer timer){
		this.setName(name);
		this.setMaximumSize(new Dimension(xSize,ySize));
		
		this.setBorder(BorderFactory.createTitledBorder(null, getName(), TitledBorder.CENTER,TitledBorder.TOP));
		
		this.setEditable(false);
		this.setFocusable(false);
		
		t = timer;
		t.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == t){
			setObject(field);
		}
	}
	
	public void setObject(Object o){
		field = o;
		if (o != null){
			setText(o.toString());
		}
	}
}

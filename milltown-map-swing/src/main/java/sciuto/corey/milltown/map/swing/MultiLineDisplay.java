package sciuto.corey.milltown.map.swing;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class MultiLineDisplay extends JTextArea {

	public MultiLineDisplay(String name, int xSize, int ySize){
		this.setName(name);
		this.setMaximumSize(new Dimension(xSize,ySize));
		
		this.setBorder(BorderFactory.createTitledBorder(null, getName(), TitledBorder.CENTER,TitledBorder.TOP));
		
		this.setEditable(false);
	}
	
}

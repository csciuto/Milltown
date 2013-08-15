package sciuto.corey.milltown.map.swing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;

public class Highlight extends JLabel {
	
	public Highlight(int xSize, int ySize, Color color){
		this.setSize(new Dimension(xSize, ySize));
		this.setBackground(color);
		this.setOpaque(false);
	}
	
}

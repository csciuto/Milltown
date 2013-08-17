package sciuto.corey.milltown.map.swing;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Scrollable;
import javax.swing.Timer;

import sciuto.corey.milltown.map.swing.components.SingleLineTextField;

public class BuildingSelector extends JLabel implements Scrollable {
	
	private Dimension viewSize = new Dimension(150,300);
	
	public BuildingSelector(){
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		for (int i=0;i<50;i++){
			add(new SingleLineTextField(String.valueOf(i),String.valueOf(i),new Timer(1000,null)));
		}
		
		setPreferredSize(new Dimension(150,3000));
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return viewSize;
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return this.getHeight() / 50;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return this.getHeight() / 5;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

}

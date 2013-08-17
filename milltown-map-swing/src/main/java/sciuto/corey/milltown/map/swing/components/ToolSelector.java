package sciuto.corey.milltown.map.swing.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Scrollable;


public class ToolSelector extends JLabel implements Scrollable {
	
	private Dimension viewSize = new Dimension(150,300);
	
	public ToolSelector(){
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new ToolButton("Mill", this));
		add(Box.createVerticalStrut(10));
		add(new ToolButton("House", this));
		add(Box.createVerticalStrut(10));
		add(new ToolButton("Road", this));
		
		setPreferredSize(new Dimension(150,300));
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

	/**
	 * Makes the passed-in tool the active one. Called as a callback in the buttons.
	 * @param button
	 */
	public void setNewTool(ToolButton button) {
		
		for (int i=0; i< this.getComponentCount(); i++){
			Component c = this.getComponent(i);
			if (c instanceof ToolButton){
				if (c == button){
					((ToolButton) c).activate();
				} else{
					((ToolButton) c).deactivate();
				}
			}
		}
		repaint();
	}

}

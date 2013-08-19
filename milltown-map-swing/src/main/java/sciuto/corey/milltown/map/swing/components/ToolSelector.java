package sciuto.corey.milltown.map.swing.components;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Scrollable;

import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.buildings.House1;
import sciuto.corey.milltown.model.buildings.Mill;
import sciuto.corey.milltown.model.buildings.Road;

public class ToolSelector extends JLabel implements Scrollable {

	private static final long serialVersionUID = -1193141538972511817L;

	private Dimension viewSize = new Dimension(150, 300);

	private Class<? extends AbstractBuilding> buildingToBuild;
	
	private ToolButton selectedButton = null;

	public ToolSelector() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		add(new ToolButton("Mill", Mill.class, this));
		add(Box.createVerticalStrut(10));
		add(new ToolButton("House", House1.class, this));
		add(Box.createVerticalStrut(10));
		add(new ToolButton("Road", Road.class, this));

		setPreferredSize(new Dimension(150, 300));
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
	 * Makes the passed-in tool the active one. Called as a callback in the
	 * buttons.
	 * 
	 * @param button
	 */
	public void setNewTool(ToolButton button) {

		if (selectedButton != null){
			selectedButton.deactivate();
		}
		button.activate();
		selectedButton = button;

		buildingToBuild = button.getBuildingType();

		repaint();
	}

	public Class<? extends AbstractBuilding> getBuildingToBuild() {
		return buildingToBuild;
	}

}

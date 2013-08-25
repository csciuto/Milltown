package sciuto.corey.milltown.map.swing.components;

import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Scrollable;

import sciuto.corey.milltown.engine.PropertiesReader;
import sciuto.corey.milltown.map.swing.BuildingGraphicsRetriever;
import sciuto.corey.milltown.map.swing.MainScreen;
import sciuto.corey.milltown.map.swing.components.tools.BuildingToolButton;
import sciuto.corey.milltown.map.swing.components.tools.BulldozerToolButton;
import sciuto.corey.milltown.map.swing.components.tools.ToolButton;
import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.buildings.*;

public class ToolSelector extends JLabel implements Scrollable {

	private static final long serialVersionUID = -1193141538972511817L;

	private Dimension viewSize = new Dimension(150, 300);

	/**
	 * Shortcut to the query tool.
	 */
	private final ToolButton queryTool;
	
	/**
	 * The active button
	 */
	private ToolButton selectedButton = null;
	
	public ToolSelector() {

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		this.queryTool = new ToolButton("Query", this);
		add(queryTool);
		queryTool.activate();
		selectedButton = queryTool;

		BuildingGraphicsRetriever graphicsRetriever = new BuildingGraphicsRetriever();
		
		add(Box.createVerticalStrut(10));
		add(new BulldozerToolButton("Demolish", this));

		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("Road", Road.class, this, graphicsRetriever));
		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("Canal", Canal.class, this, graphicsRetriever));
		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("House", House1.class, this, graphicsRetriever));
		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("Mill", Mill.class, this, graphicsRetriever));
		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("Warehouse", Warehouse.class, this, graphicsRetriever));
		add(Box.createVerticalStrut(10));
		add(new BuildingToolButton("Office", Office1.class, this, graphicsRetriever));
		
		if (Boolean.parseBoolean(PropertiesReader.read("milltown.properties").getProperty("milltown.debug"))){
			// Can't reference via the Main Screen instance's game pointer because it's not fully constructed yet!
			add(Box.createVerticalStrut(10));
			add(new BuildingToolButton("Water", Water.class, this, graphicsRetriever));
		}
		
		setPreferredSize(new Dimension(150, 450));
		
		repaint();
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

		if (selectedButton != null) {
			selectedButton.deactivate();
		}
		button.activate();
		selectedButton = button;

		repaint();
	}

	/**
	 * Switches to query mode.
	 * 
	 * @param button
	 */
	public void activateQueryTool() {

		if (selectedButton != null) {
			selectedButton.deactivate();
		}

		queryTool.activate();
		selectedButton = queryTool;
		
		repaint();
	}

	public Class<? extends AbstractBuilding> getBuildingToBuild() {
		if (selectedButton instanceof BuildingToolButton) {
			return ((BuildingToolButton)selectedButton).getBuildingType();
		} else {
			return null;
		}
	}

	public ToolButton getSelectedButton() {
		return selectedButton;
	}

}

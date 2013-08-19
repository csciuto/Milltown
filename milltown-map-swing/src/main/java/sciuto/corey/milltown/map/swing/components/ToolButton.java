package sciuto.corey.milltown.map.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import sciuto.corey.milltown.map.swing.BuildingGraphicsRetriever;
import sciuto.corey.milltown.model.board.AbstractBuilding;

public class ToolButton extends JLabel {

	private static final long serialVersionUID = -2225530348943230026L;
	
	private static final Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK,1);
	private static final Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN,3);
	
	private boolean isSelected = false;
	
	private final Class<? extends AbstractBuilding> buildingType;
	
	protected class MouseClickListener extends MouseInputAdapter {
		/**
		 * The object that holds this button.
		 */
		private final ToolSelector container;
		
		public MouseClickListener(ToolSelector container){
			this.container = container;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			container.setNewTool(ToolButton.this);
		}
	}

	public Class<? extends AbstractBuilding> getBuildingType() {
		return buildingType;
	}

	public ToolButton(String toolName, Class<? extends AbstractBuilding> buildingType, ToolSelector container){
		super(toolName);
		
		this.buildingType = buildingType;
		
		setIcon(BuildingGraphicsRetriever.retrieveIcon(buildingType));
		
		setMaximumSize(new Dimension(125,50));
		setBorder(unselectedBorder);
		
		MouseInputListener mouseListener = new MouseClickListener(container);
		addMouseListener(mouseListener);
	}

	public void deactivate(){
		setBorder(unselectedBorder);
	}
	public void activate(){
		setBorder(selectedBorder);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}

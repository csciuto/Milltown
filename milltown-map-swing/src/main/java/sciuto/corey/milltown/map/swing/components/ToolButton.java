package sciuto.corey.milltown.map.swing.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.map.swing.ErrorMessageBox;
import sciuto.corey.milltown.model.board.AbstractBuilding;

public class ToolButton extends JLabel {

	private static final long serialVersionUID = -2225530348943230026L;
	
	private static final Logger LOGGER = Logger.getLogger(ToolButton.class);
	
	private static final Border unselectedBorder = BorderFactory.createLineBorder(Color.BLACK,1);
	private static final Border selectedBorder = BorderFactory.createLineBorder(Color.GREEN,3);
	
	private boolean isSelected = false;
	
	private final Class<? extends AbstractBuilding> buildingType;
	
	public Class<? extends AbstractBuilding> getBuildingType() {
		return buildingType;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public ToolButton(String toolName, Class<? extends AbstractBuilding> type, ToolSelector container){
		super(toolName);
		
		this.buildingType = type;
		
		String fileName = "/map_images/blank.png";
		if (toolName.equals("Mill")){
			fileName = "/map_images/mill_ico.png";
		} else if (toolName.equals("House")){
			fileName = "/map_images/house_1_ico.png";
		} else if (toolName.equals("Road")){
			fileName = "/map_images/road_ico.png";
		}
		URL url = this.getClass().getResource(fileName);
		if (url == null) {
			String msg = String.format("Cannot find image %s", fileName);
			ErrorMessageBox.show(msg);
			LOGGER.fatal(msg,null);
			System.exit(-1);
		}
		Icon icon = new ImageIcon(url);
		setIcon(icon);
		
		setMaximumSize(new Dimension(125,50));
		setBorder(unselectedBorder);
		
		MouseInputListener mouseListener = new MouseClickListener(container);
		addMouseListener(mouseListener);
	}

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
			container.setNewTool((ToolButton)e.getSource());
		}
	}
	
	public void deactivate(){
		setBorder(unselectedBorder);
	}
	public void activate(){
		setBorder(selectedBorder);
	}
	
}

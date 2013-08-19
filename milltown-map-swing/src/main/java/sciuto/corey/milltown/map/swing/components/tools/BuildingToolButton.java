package sciuto.corey.milltown.map.swing.components.tools;

import sciuto.corey.milltown.map.swing.BuildingGraphicsRetriever;
import sciuto.corey.milltown.map.swing.components.ToolSelector;
import sciuto.corey.milltown.model.board.AbstractBuilding;


public class BuildingToolButton extends ToolButton {

	private static final long serialVersionUID = -2836448499475747799L;

	private final Class<? extends AbstractBuilding> buildingType;
	
	public Class<? extends AbstractBuilding> getBuildingType() {
		return buildingType;
	}

	public BuildingToolButton(String toolName, Class<? extends AbstractBuilding> buildingType, ToolSelector container){
		super(toolName,container);
		
		this.buildingType = buildingType;
		
		setIcon(BuildingGraphicsRetriever.retrieveIcon(buildingType));
	}
	
}

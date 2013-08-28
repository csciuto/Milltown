package sciuto.corey.milltown.model.buildings;

import sciuto.corey.milltown.model.board.AbstractBuilding;



public class Canal extends AbstractBuilding {

	private static final long serialVersionUID = 6042255235136160236L;
	
	private boolean hasWater;
	
	public Canal(){
		super(SMALL_SQUARE);
		setHasWater(false);
	}

	public boolean isHasWater() {
		return hasWater;
	}

	public void setHasWater(boolean hasWater) {
		this.hasWater = hasWater;
	}
}

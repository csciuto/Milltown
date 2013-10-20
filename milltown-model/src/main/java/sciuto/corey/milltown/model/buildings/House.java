package sciuto.corey.milltown.model.buildings;

import sciuto.corey.milltown.model.board.AbstractBuilding;



public class House extends AbstractBuilding {

	private static final long serialVersionUID = 2870508695570731740L;

	public House(){
		super(SMALL_SQUARE,"");
		
		double d = Math.random();
		String fileName;
		if (d <= 0.3) {
			fileName = "house_1";
		} else if (d <= 0.6) {
			fileName = "house_2";
		} else {
			fileName = "house_3";
		}
		this.setFileName(fileName);
	}
}

package sciuto.corey.milltown.model.buildings;

import sciuto.corey.milltown.model.board.AbstractBuilding;



public class Office extends AbstractBuilding {

	private static final long serialVersionUID = -3626051911997231729L;

	public Office(){
		super(MEDIUM_SQUARE, "");
		
		double d = Math.random();
		String fileName;
		if (d <= 0.5) {
			fileName = "office_1";
		} else{
			fileName = "office_2";
		}
		this.setFileName(fileName);
	}
}

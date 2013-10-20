package sciuto.corey.milltown.model.buildings;

import java.io.Serializable;

import sciuto.corey.milltown.model.board.AbstractBuilding;

public class Tenement extends AbstractBuilding implements Serializable{

	private static final long serialVersionUID = -22798594622818398L;

	public Tenement(){
		super(MEDIUM_SQUARE,"");
		
		double d = Math.random();
		String fileName;
		if (d <= 0.3) {
			fileName = "tenement_1";
		} else if (d <= 0.6) {
			fileName = "tenement_2";
		} else {
			fileName = "tenement_3";
		}
		this.setFileName(fileName);
	}

}

package sciuto.corey.milltown.model.buildings;

import java.io.Serializable;

import sciuto.corey.milltown.model.board.AbstractBuilding;

public class Tenement extends AbstractBuilding implements Serializable{

	private static final long serialVersionUID = -22798594622818398L;

	public Tenement(){
		super(SMALL_SQUARE);
	}

}

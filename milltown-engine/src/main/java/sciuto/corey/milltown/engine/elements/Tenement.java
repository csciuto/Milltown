package sciuto.corey.milltown.engine.elements;

import java.io.Serializable;

import sciuto.corey.milltown.engine.board.AbstractBuilding;

public class Tenement extends AbstractBuilding implements Serializable{

	private static final long serialVersionUID = -22798594622818398L;

	public Tenement(){
		super(SMALL_SQUARE);
	}

}

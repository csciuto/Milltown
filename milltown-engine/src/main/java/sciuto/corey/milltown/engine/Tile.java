package sciuto.corey.milltown.engine;

import java.io.Serializable;

import sciuto.corey.milltown.engine.elements.BuildingType;

/**
 * Represents a square on the gameboard
 * @author Corey
 *
 */
public class Tile implements Serializable{
	
	private static final long serialVersionUID = -196994675784444179L;

	private BuildingType contents;

	private final int x;
	private final int y;
	
	/**
	 * Creates a new tile containing the specified element with a reference back to where it is on the board.
	 * @param x
	 * @param y
	 * @param contents
	 */
	protected Tile(int x, int y, BuildingType contents){
		this.x = x;
		this.y = y;
		this.contents = contents;
	}
	
	public BuildingType getContents() {
		return contents;
	}

	public void setContents(BuildingType contents) {
		this.contents = contents;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}

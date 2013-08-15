package sciuto.corey.milltown.model.board;

import java.io.Serializable;

import sciuto.corey.milltown.model.buildings.Land;

/**
 * Represents a square on the gameboard
 * 
 * @author Corey
 * 
 */
public class Tile implements Serializable {

	private static final long serialVersionUID = -196994675784444179L;

	private final int xLoc;
	private final int yLoc;

	private boolean isDirty;

	private AbstractBuilding contents;

	/**
	 * Creates a new land tile with a reference back to where the tile is on the
	 * board
	 * 
	 * @param xLoc
	 * @param yLoc
	 */
	public Tile(int xLoc, int yLoc) {
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		
		Land land = new Land();
		land.setRootTile(this);
		this.setContents(land);
	}

	/**
	 * Returns the building on this square
	 * 
	 * @return
	 */
	public AbstractBuilding getContents() {
		return contents;
	}

	/**
	 * Dangerous! Unit tests only. Otherwise, use the BuildingConstructor.
	 * 
	 * @param contents
	 */
	public void setContents(AbstractBuilding contents) {
		this.contents = contents;
	}

	public int getXLoc() {
		return xLoc;
	}

	public int getYLoc() {
		return yLoc;
	}

	/**
	 * A UI hinter to redraw this tile.
	 * 
	 * @return
	 */
	public boolean isDirty() {
		return isDirty;
	}

	/**
	 * Set if your UI redraws only tiles that have changed.
	 * 
	 * @param isDirty
	 */
	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

}

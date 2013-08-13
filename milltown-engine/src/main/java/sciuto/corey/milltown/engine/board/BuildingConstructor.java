package sciuto.corey.milltown.engine.board;

import org.apache.commons.lang3.tuple.Pair;

import sciuto.corey.milltown.engine.elements.Land;

/**
 * This class ties together the Tile and AbstractBuilding classes.
 * 
 * @author Corey
 * 
 */
public class BuildingConstructor {

	private final GameBoard board;
	
	public BuildingConstructor(GameBoard b){
		this.board = b;
	}
	
	/**
	 * Determines if the building type can be constructed here, using this tile
	 * as the upper-left coordinate
	 * 
	 * @param upperLeftTile
	 * @param building
	 * @return
	 */
	public boolean canBuild(Tile upperLeftTile, AbstractBuilding building) {
		Pair<Integer, Integer> size = null;
		size = building.getSize();

		for (int x = upperLeftTile.getXLoc(); x < upperLeftTile.getXLoc() + size.getLeft(); x++) {
			for (int y = upperLeftTile.getYLoc(); y < upperLeftTile.getYLoc() + size.getRight(); y++) {
				Tile t = board.getTile(x, y);
				if (t == null || !(t.getContents() instanceof Land)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Builds on this spot. Does not check if it can first!
	 * @param t
	 * @param b
	 */
	public void build(Tile t, AbstractBuilding b) {

	}

	/**
	 * Demolish whatever building is on this spot.
	 * @param t
	 */
	public void demolish(Tile t) {

	}

	
}

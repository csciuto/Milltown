package sciuto.corey.milltown.engine.board;

import org.apache.commons.lang3.tuple.Pair;

import sciuto.corey.milltown.engine.elements.Land;
import sciuto.corey.milltown.engine.elements.Water;

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
	 * Builds on this spot. Returns true if the building was built.
	 * @param t
	 * @param b
	 * @return
	 */
	public boolean build(Tile upperLeftTile, AbstractBuilding building) {
		
		if (!canBuild(upperLeftTile,building)){
			return false;
		}
		
		Pair<Integer, Integer> size = null;
		size = building.getSize();

		for (int x = upperLeftTile.getXLoc(); x < upperLeftTile.getXLoc() + size.getLeft(); x++) {
			for (int y = upperLeftTile.getYLoc(); y < upperLeftTile.getYLoc() + size.getRight(); y++) {
				Tile t = board.getTile(x, y);
				t.setContents(building);
				t.setDirty(true);
			}
		}
		
		building.setRootTile(upperLeftTile);
		
		return true;
	}

	/**
	 * Demolish whatever building is on this spot.
	 * @param t
	 */
	public void demolish(Tile tile) {

		AbstractBuilding building = tile.getContents();
		
		if (building instanceof Water || building instanceof Land){
			return;
		}
		Tile rootTile = building.getRootTile();
		
		Pair<Integer, Integer> size = null;
		size = building.getSize();

		for (int x = rootTile.getXLoc(); x < rootTile.getXLoc() + size.getLeft(); x++) {
			for (int y = rootTile.getYLoc(); y < rootTile.getYLoc() + size.getRight(); y++) {
				Tile t = board.getTile(x, y);
				Land land = new Land();
				t.setContents(land);
				land.setRootTile(t);
				t.setDirty(true);
			}
		}
		building = null;
	}

	
}

package sciuto.corey.milltown.engine;

import org.apache.commons.lang3.tuple.Pair;

import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.*;

/**
 * This class ties together the Tile and AbstractBuilding classes.
 * 
 * @author Corey
 * 
 */
public class BuildingConstructor {

	private final GameBoard board;

	public BuildingConstructor(GameBoard b) {
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
				if (t == null) {
					return false;
				}
				if (t.getContents() instanceof Water || t.getContents() instanceof Canal) {
					if (!(building instanceof Road) && !(building instanceof RoadWithStreetcar)
							&& !(building instanceof Rail)) {
						return false;
					}
				} else if (!(t.getContents() instanceof Land)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Builds on this spot. Returns true if the building was built.
	 * 
	 * @param t
	 * @param b
	 * @return
	 */
	public boolean build(Tile upperLeftTile, AbstractBuilding building) {

		if (!canBuild(upperLeftTile, building)) {
			return false;
		}

		Pair<Integer, Integer> size = null;
		size = building.getSize();

		for (int x = upperLeftTile.getXLoc(); x < upperLeftTile.getXLoc() + size.getLeft(); x++) {
			for (int y = upperLeftTile.getYLoc(); y < upperLeftTile.getYLoc() + size.getRight(); y++) {
				Tile t = board.getTile(x, y);
				if (t.getContents() instanceof Water) {
					if (building instanceof Road) {
						building = new RoadBridge();
					} else if (building instanceof RoadWithStreetcar) {
						building = new RoadWithStreetcarBridge();
					} else if (building instanceof Rail) {
						building = new RailBridge();
					}
				} else if (t.getContents() instanceof Canal) {
					if (building instanceof Road) {
						building = new RoadCanalBridge();
					} else if (building instanceof RoadWithStreetcar) {
						building = new RoadWithStreetcarCanalBridge();
					} else if (building instanceof Rail) {
						building = new RailCanalBridge();
					}
				}
				t.setContents(building);
				t.setDirty(true);
			}
		}

		building.setRootTile(upperLeftTile);

		return true;
	}

	/**
	 * Demolish whatever building is on this spot.
	 * 
	 * @param t
	 */
	public void demolish(Tile tile) {

		AbstractBuilding building = tile.getContents();

		if (building instanceof Water || building instanceof Land) {
			return;
		}
		Tile rootTile = building.getRootTile();

		Pair<Integer, Integer> size = null;
		size = building.getSize();

		for (int x = rootTile.getXLoc(); x < rootTile.getXLoc() + size.getLeft(); x++) {
			for (int y = rootTile.getYLoc(); y < rootTile.getYLoc() + size.getRight(); y++) {
				Tile t = board.getTile(x, y);
				AbstractBuilding current = t.getContents();

				AbstractBuilding blank = null;

				if (current instanceof RoadBridge || current instanceof RoadWithStreetcarBridge
						|| current instanceof RailBridge) {
					blank = new Water();
				} else if (current instanceof RoadCanalBridge || current instanceof RoadWithStreetcarCanalBridge
						|| current instanceof RailCanalBridge) {
					blank = new Canal();
				} else {
					blank = new Land();
				}

				t.setContents(blank);
				blank.setRootTile(t);
				t.setDirty(true);
			}
		}
		building = null;
	}

}

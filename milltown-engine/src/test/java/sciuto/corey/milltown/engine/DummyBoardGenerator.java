package sciuto.corey.milltown.engine;

import sciuto.corey.milltown.engine.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Water;

public class DummyBoardGenerator {

	/**
	 * Makes this board:<br><br>
	 * 
	 * LLLLL<br>
	 * LLWLL<br>
	 * LLLWL<br>
	 * LLLLW <br>
	 * LLLLL
	 * 
	 */
	public static GameBoard createDummyBoard1() {

		GameBoard board = new GameBoard(5);

		Tile tile1 = board.getTile(1, 2);
		Tile tile2 = board.getTile(2, 3);
		Tile tile3 = board.getTile(3, 4);

		Water water1 = new Water();
		Water water2 = new Water();
		Water water3 = new Water();

		tile1.setContents(water1);
		tile2.setContents(water2);
		tile3.setContents(water3);

		water1.setRootTile(tile1);
		water2.setRootTile(tile2);
		water3.setRootTile(tile3);
		
		return board;
	}

}

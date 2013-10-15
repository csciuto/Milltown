package sciuto.corey.milltown.test;

import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Water;

public class DummyBoardGenerator {

	/**
	 * Makes this board:<br><br>
	 * 
	 * LLLLL<br>
	 * LLLLL<br>
	 * LWLLL<br>
	 * LLWLL <br>
	 * LLLWL
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
	
	/**
	 * Makes this board:<br><br>
	 * 
	 * LLLLL<br>
	 * LLLLL<br>
	 * LLLLL<br>
	 * LLLLL <br>
	 * WWWWW
	 * 
	 */
	public static GameBoard createDummyBoard2() {

		GameBoard board = new GameBoard(5);

		Tile tile1 = board.getTile(0, 4);
		Tile tile2 = board.getTile(1, 4);
		Tile tile3 = board.getTile(2, 4);
		Tile tile4 = board.getTile(3, 4);
		Tile tile5 = board.getTile(4, 4);

		Water water1 = new Water();
		Water water2 = new Water();
		Water water3 = new Water();
		Water water4 = new Water();
		Water water5 = new Water();

		tile1.setContents(water1);
		tile2.setContents(water2);
		tile3.setContents(water3);
		tile4.setContents(water4);
		tile5.setContents(water5);

		water1.setRootTile(tile1);
		water2.setRootTile(tile2);
		water3.setRootTile(tile3);
		water4.setRootTile(tile4);
		water5.setRootTile(tile5);
		
		return board;
	}

}

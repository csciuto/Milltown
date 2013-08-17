package sciuto.corey.milltown.model.board;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sciuto.corey.milltown.model.buildings.Land;
import sciuto.corey.milltown.model.buildings.Water;

public class GameBoardUnitTest {

	@Test
	public void testGetMethods() {

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
		
		Tile tile;
		
		tile = board.getTile(2, 3);
		assertTrue(tile.getContents() instanceof Water);
		
		tile = board.getTile(3, 3);
		assertTrue(tile.getContents() instanceof Land);
		
		tile = board.getTileEast(tile);
		assertTrue(tile.getContents() instanceof Water);
		
		tile = board.getTileEast(tile);
		assertNull(tile);
		
		tile = board.getTile(5, 5);
		assertNull(tile);
	}

}


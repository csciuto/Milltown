package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Land;
import sciuto.corey.milltown.model.buildings.Water;

public class GameBoardUnitTest {

	private GameBoard board;

	@Test
	public void testGetMethods() {

		board = DummyBoardGenerator.createDummyBoard1();
		
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


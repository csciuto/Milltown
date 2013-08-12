package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import sciuto.corey.milltown.engine.elements.Land;
import sciuto.corey.milltown.engine.elements.Water;

public class GameBoardUnitTest {

	private GameBoard board;

	@Before
	public void setup() {
		board = new GameBoard(5);

		Water water1 = new Water();
		Water water2 = new Water();
		Water water3 = new Water();
		
		Tile tile1 = board.getTile(1, 2);
		Tile tile2 = board.getTile(2, 3);
		Tile tile3 = board.getTile(3, 4);
		
		tile1.setContents(water1);
		tile2.setContents(water2);
		tile3.setContents(water3);

	}

	@Test
	public void testGetMethods() {

		Tile tile;
		
		tile = board.getTile(2, 3);
		assertTrue(tile.getContents() instanceof Water);
		
		tile = board.getTile(3, 3);
		assertTrue(tile.getContents() instanceof Land);
		
		tile = board.getTileEast(tile);
		assertTrue(tile.getContents() instanceof Water);
		
		tile = board.getTileEast(tile);
		assertNull(tile);
		
		try{
			tile = board.getTile(5, 5);
			fail("Should've raised an exception");
		}
		catch (IllegalArgumentException e){
			// Expected
		}
	}

}


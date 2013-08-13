package sciuto.corey.milltown.engine.board;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sciuto.corey.milltown.engine.elements.Land;
import sciuto.corey.milltown.engine.elements.Water;

public class TileUnitTest {

	private GameBoard board;
	
	@Test
	public void testGetContents(){
		
		board = DummyBoardGenerator.createDummyBoard1();
		
		assertTrue(board.getTile(1, 2).getContents() instanceof Water);
		assertTrue(board.getTile(1, 1).getContents() instanceof Land);
	}
	
}

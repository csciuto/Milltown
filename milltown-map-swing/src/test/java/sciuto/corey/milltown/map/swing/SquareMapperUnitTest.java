package sciuto.corey.milltown.map.swing;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import sciuto.corey.milltown.engine.GameBoard;

public class SquareMapperUnitTest {

	@Test
	public void testMapper(){
		GameBoard board = new GameBoard();
		
		SquareMapper p = new SquareMapper(board, 625);
		
		Pair<Integer, Integer> clickLoc;
		
		clickLoc = Pair.of(0, 0);
		assertEquals(Integer.valueOf(0),Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(0),Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(625, 625);
		assertEquals(Integer.valueOf(24),Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(24),Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(594, 27);
		assertEquals(Integer.valueOf(23),Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(1),Integer.valueOf(p.mappingHelper(clickLoc.getRight())));
		
	}
	
}

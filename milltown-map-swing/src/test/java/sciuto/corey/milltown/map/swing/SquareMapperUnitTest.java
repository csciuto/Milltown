package sciuto.corey.milltown.map.swing;

import static org.junit.Assert.assertEquals;

import javax.swing.Timer;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import sciuto.corey.milltown.map.swing.components.GameMap;
import sciuto.corey.milltown.map.swing.components.MultiLineTextField;
import sciuto.corey.milltown.model.board.GameBoard;

public class SquareMapperUnitTest {

	class DummyGameMap extends GameMap {

		DummyGameMap (GameBoard board, int size, MultiLineTextField d, Timer t) {
			super(board, size, d, t);
		}

		@Override
		public int getWidth() {
			return 625;
		}

		@Override
		public int getHeight() {
			return 625;
		}
	}
	
	@Test
	public void testMapper() {
		GameBoard board = new GameBoard();
		
		GameMap map = new DummyGameMap(board, 625, null, null);

		SquareMapper p = new SquareMapper(board, map);

		Pair<Integer, Integer> clickLoc;

		clickLoc = Pair.of(0, 0);
		assertEquals(Integer.valueOf(0), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(0), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(625, 625);
		assertEquals(Integer.valueOf(24), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(24), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(594, 27);
		assertEquals(Integer.valueOf(23), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(1), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

	}

}

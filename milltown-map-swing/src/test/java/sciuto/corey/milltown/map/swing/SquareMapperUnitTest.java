package sciuto.corey.milltown.map.swing;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.SaveGameManager;
import sciuto.corey.milltown.map.swing.components.UIGameMap;
import sciuto.corey.milltown.model.board.GameBoard;

public class SquareMapperUnitTest {

	class DummyGameMap extends UIGameMap {

		private static final long serialVersionUID = -1269610579047429935L;

		DummyGameMap (Game game, int size) {
			super(game, size);
		}

		@Override
		public int getWidth() {
			return 1000;
		}

		@Override
		public int getHeight() {
			return 1000;
		}
	}
	
	@Test
	public void testMapper() {
		Game game = SaveGameManager.newBlankGame();
		
		UIGameMap map = new DummyGameMap(game, 1000);

		SquareMapper p = new SquareMapper(game.getBoard(), map);
		p.update();

		Pair<Integer, Integer> clickLoc;

		clickLoc = Pair.of(0, 0);
		assertEquals(Integer.valueOf(0), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(0), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(1000, 1000);
		assertEquals(Integer.valueOf(49), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(49), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

		clickLoc = Pair.of(975, 25);
		assertEquals(Integer.valueOf(48), Integer.valueOf(p.mappingHelper(clickLoc.getLeft())));
		assertEquals(Integer.valueOf(1), Integer.valueOf(p.mappingHelper(clickLoc.getRight())));

	}

}

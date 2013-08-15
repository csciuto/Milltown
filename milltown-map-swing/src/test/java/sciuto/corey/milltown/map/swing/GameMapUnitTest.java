package sciuto.corey.milltown.map.swing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.GameManager;
import sciuto.corey.milltown.map.swing.GameMap;

public class GameMapUnitTest {

	@Test
	public void testConstruction(){ 
		Game g = GameManager.newGame();
		
		GameMap gm = new GameMap(g.getBoard(), null, null);
		
		assertEquals((GameMap.MAP_SIZE_PX) / g.getBoard().getBoardSize(), gm.getSquareSize());
		
	}
	
}

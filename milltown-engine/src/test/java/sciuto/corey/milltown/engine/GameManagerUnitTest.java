package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import sciuto.corey.milltown.engine.board.DummyBoardGenerator;
import sciuto.corey.milltown.engine.elements.Land;
import sciuto.corey.milltown.engine.elements.Water;

public class GameManagerUnitTest {

	private Game game;
	
	@Test
	public void testSave(){

		game = new Game();
		game.setBoard(DummyBoardGenerator.createDummyBoard1());
		
		GameManager.saveGame(new File("SaveGame1.sav"), game);
		Game loadedGame = GameManager.loadGame(new File("SaveGame1.sav"));
		
		assertEquals(Water.class, loadedGame.getBoard().getTile(1, 2).getContents().getClass());
		
		Game defaultGame = GameManager.newGame();
		assertEquals(Land.class, defaultGame.getBoard().getTile(1, 2).getContents().getClass());
		
	}
	
	@After
	public void cleanUp(){
		File f = new File("SaveGame1.sav");
		if (f.exists()){
			f.delete();
		}
	}
	
}

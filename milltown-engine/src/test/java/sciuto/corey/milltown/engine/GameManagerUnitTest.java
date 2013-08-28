package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import sciuto.corey.milltown.engine.exception.LoadGameException;
import sciuto.corey.milltown.engine.exception.SaveGameException;
import sciuto.corey.milltown.model.buildings.Land;
import sciuto.corey.milltown.model.buildings.Water;
import sciuto.corey.milltown.test.DummyBoardGenerator;

public class GameManagerUnitTest {

	private Game game;
	
	@Test
	public void testSave() throws SaveGameException, LoadGameException{

		game = new Game(DummyBoardGenerator.createDummyBoard1());
		
		SaveGameManager.saveGame(new File("SaveGame1.sav"), game);
		Game loadedGame = SaveGameManager.loadGame(new File("SaveGame1.sav"));
		
		assertEquals(Water.class, loadedGame.getBoard().getTile(1, 2).getContents().getClass());
		
		Game defaultGame = SaveGameManager.newGame();
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

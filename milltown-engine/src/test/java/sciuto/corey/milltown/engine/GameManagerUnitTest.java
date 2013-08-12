package sciuto.corey.milltown.engine;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import sciuto.corey.milltown.engine.elements.Water;

public class GameManagerUnitTest {

	private Game game;
	
	@Before
	public void setup() {
		
		Game game = new Game();
		
		GameBoard board = new GameBoard(5);

		Water water1 = new Water();
		Water water2 = new Water();
		Water water3 = new Water();
		
		Tile tile1 = board.getTile(1, 2);
		Tile tile2 = board.getTile(2, 3);
		Tile tile3 = board.getTile(3, 4);
		
		tile1.setContents(water1);
		tile2.setContents(water2);
		tile3.setContents(water3);

		game.setBoard(board);
		
	}
	
	@Test
	public void testSave(){
		
		GameManager.saveGame(new File("SaveGame1.sav"), game);
		Game loadedGame = GameManager.loadGame(new File("SaveGame1.sav"));
		
		assertEquals(game, loadedGame);
		
		Game defaultGame = new Game();
		assertNotEquals(defaultGame, loadedGame);		
	}
	
}

package sciuto.corey.milltown.engine.board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sciuto.corey.milltown.engine.elements.Canal;
import sciuto.corey.milltown.engine.elements.Mill;
import sciuto.corey.milltown.engine.elements.Road;

public class BuildingConstructorUnitTest {

	private GameBoard board;
	
	@Test
	public void testCanBuildHere(){
		
		board = DummyBoardGenerator.createDummyBoard1();
		
		BuildingConstructor bc = new BuildingConstructor(board);
		
		assertTrue(bc.canBuild(board.getTile(0, 0),new Canal()));
		assertFalse(bc.canBuild(board.getTile(1, 2),new Canal()));
		assertTrue(bc.canBuild(board.getTile(0, 0),new Mill()));
		assertFalse(bc.canBuild(board.getTile(0, 1),new Mill()));
		assertFalse(bc.canBuild(board.getTile(4, 1),new Mill()));
		assertTrue(bc.canBuild(board.getTile(4, 1),new Road()));
	}
	
}

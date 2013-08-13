package sciuto.corey.milltown.engine.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sciuto.corey.milltown.engine.elements.Canal;
import sciuto.corey.milltown.engine.elements.Land;
import sciuto.corey.milltown.engine.elements.Mill;
import sciuto.corey.milltown.engine.elements.Road;
import sciuto.corey.milltown.engine.elements.Water;

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
	
	@Test
	public void testBuild(){
		
		board = DummyBoardGenerator.createDummyBoard1();
		
		BuildingConstructor bc = new BuildingConstructor(board);
		
		Mill mill = new Mill();
		bc.build(board.getTile(3, 1),mill);
		
		AbstractBuilding building = board.getTile(4, 2).getContents();
		assertEquals(mill,building);
		assertTrue(board.getTile(4, 2).isDirty());
		
		assertTrue(board.getTile(3, 0).getContents() instanceof Land);
		assertFalse(board.getTile(3, 0).isDirty());
		assertTrue(board.getTile(2, 3).getContents() instanceof Water);
		assertFalse(board.getTile(2, 3).isDirty());
	}
	
	@Test
	public void testBuildAndDemolish(){
		
		board = DummyBoardGenerator.createDummyBoard1();
		
		BuildingConstructor bc = new BuildingConstructor(board);
		
		Mill mill = new Mill();
		bc.build(board.getTile(3, 1),mill);
		
		AbstractBuilding building = board.getTile(4, 2).getContents();
		assertEquals(mill,building);
		assertTrue(board.getTile(4, 2).isDirty());
		board.getTile(4, 2).setDirty(false);
		
		assertTrue(board.getTile(3, 0).getContents() instanceof Land);
		assertFalse(board.getTile(3, 0).isDirty());
		assertTrue(board.getTile(2, 3).getContents() instanceof Water);
		assertFalse(board.getTile(2, 3).isDirty());
		
		bc.demolish(board.getTile(4, 2));
		assertTrue(board.getTile(4, 2).getContents() instanceof Land);
		assertEquals(board.getTile(4, 2), board.getTile(4, 2).getContents().getRootTile());
		assertTrue(board.getTile(4, 2).isDirty());
		assertTrue(board.getTile(3, 1).getContents() instanceof Land);
		assertEquals(board.getTile(3, 1), board.getTile(3, 1).getContents().getRootTile());
		assertTrue(board.getTile(3, 1).isDirty());
		
		bc.demolish(board.getTile(2, 3));		
		assertTrue(board.getTile(2, 3).getContents() instanceof Water);
		assertEquals(board.getTile(2, 3), board.getTile(2, 3).getContents().getRootTile());
		assertFalse(board.getTile(2, 3).isDirty());
	}
	
}

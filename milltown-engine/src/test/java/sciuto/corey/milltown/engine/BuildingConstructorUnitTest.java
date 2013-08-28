package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.buildings.*;
import sciuto.corey.milltown.test.DummyBoardGenerator;

public class BuildingConstructorUnitTest {

	private Game game;
	private GameBoard board;
	private BuildingConstructor bc;

	@Before
	public void init() {
		game = new Game(DummyBoardGenerator.createDummyBoard1());
		bc = new BuildingConstructor(game);
		board = game.getBoard();
	}

	@Test
	public void testCanBuildHere() {

		assertTrue(bc.canBuild(board.getTile(0, 0), new Canal()));
		assertFalse(bc.canBuild(board.getTile(1, 2), new Canal()));
		assertTrue(bc.canBuild(board.getTile(0, 0), new Mill()));
		assertFalse(bc.canBuild(board.getTile(0, 1), new Mill()));
		assertFalse(bc.canBuild(board.getTile(4, 1), new Mill()));
		assertTrue(bc.canBuild(board.getTile(4, 1), new Road()));
	}

	@Test
	public void testBuild() {

		Mill mill = new Mill();
		bc.build(board.getTile(3, 1), mill);

		AbstractBuilding building = board.getTile(4, 2).getContents();
		assertEquals(mill, building);
		assertTrue(board.getTile(4, 2).isDirty());

		assertTrue(board.getTile(3, 0).getContents() instanceof Land);
		assertFalse(board.getTile(3, 0).isDirty());
		assertTrue(board.getTile(2, 3).getContents() instanceof Water);
		assertFalse(board.getTile(2, 3).isDirty());
	}

	@Test
	public void testBuildOnWater() {

		Road road = new Road();
		bc.build(board.getTile(3, 1), road);

		AbstractBuilding building = board.getTile(3, 1).getContents();
		assertEquals(road, building);

		Road road2 = new Road();
		bc.build(board.getTile(3, 4), road2);

		AbstractBuilding building2 = board.getTile(3, 4).getContents();
		assertTrue(building2 instanceof RoadBridge);

		bc.demolish(board.getTile(3, 4));
		assertTrue(board.getTile(3, 4).getContents() instanceof Water);
	}

	@Test
	public void testBuildAndDemolish() {

		Mill mill = new Mill();
		bc.build(board.getTile(3, 1), mill);

		AbstractBuilding building = board.getTile(4, 2).getContents();
		assertEquals(mill, building);
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

package sciuto.corey.milltown.engine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.buildings.Canal;
import sciuto.corey.milltown.test.DummyBoardGenerator;

public class CanalSystemManagerUnitTest {

	private Game game;
	private GameBoard board;
	private BuildingConstructor bc;

	@Before
	public void init() {
		game = new Game(DummyBoardGenerator.createDummyBoard2());
		bc = new BuildingConstructor(game);
		board = game.getBoard();
	}
	
	@Test
	public void testCanals(){
		
		/* Build two canal squares off of the water
		 * 
		 * LLLLL
		 * LLLLL
		 * LCLLL
		 * LCLLL
		 * WWWWW
		 * 
		 */
		Canal canalRoot = new Canal();
		bc.build(board.getTile(1, 3), canalRoot);

		Canal canal2 = new Canal();
		bc.build(board.getTile(1, 2), canal2);
		
		game.simulate();
		
		assertTrue(game.getCanalSystemManager().canalRoots.contains(canalRoot));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(canalRoot));
		
		assertFalse(game.getCanalSystemManager().canalRoots.contains(canal2));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(canal2));
		
		assertTrue(canalRoot.hasWater());
		assertTrue(canal2.hasWater());

		/* Demolish the connection, make sure the water is gone
		 * 		  
		 * LLLLL
		 * LLLLL
		 * LCLLL
		 * LLLLL
		 * WWWWW
		 * 
		 */
		bc.demolish(canalRoot.getRootTile());
		
		game.simulate();
		
		assertFalse(game.getCanalSystemManager().canalRoots.contains(canalRoot));
		assertFalse(game.getCanalSystemManager().canalSquares.contains(canalRoot));
		assertFalse(canal2.hasWater());
		
		/* Add a new square disconnected from the water but connected to the empty canal. Should be empty
		 * 
		 * LLLLL
		 * LCLLL
		 * LCLLL
		 * LLLLL
		 * WWWWW
		 * 
		 */
		Canal canal3 = new Canal();
		bc.build(board.getTile(1, 1), canal3);
		
		game.simulate();
		
		assertFalse(canal2.hasWater());
		assertFalse(canal3.hasWater());
		
		/* Reconnect the whole thing and make sure it works.
		 * 
		 * LLLLL
		 * LCLLL
		 * LCLLL
		 * LCLLL
		 * WWWWW
		 * 
		 */
		canalRoot = new Canal();
		bc.build(board.getTile(1, 3), canalRoot);
		
		game.simulate();
		
		assertTrue(canalRoot.hasWater());
		assertTrue(canal2.hasWater());
		assertTrue(canal3.hasWater());
		
		/* Now, build a loop and a branch
		 * 
		 * LLCLL
		 * LCCCL
		 * LCLCL
		 * LCLCL
		 * WWWWW
		 * 
		 */
		
		Canal branchPiece = new Canal();
		Canal secondRoot = new Canal();
		
		bc.build(board.getTile(2, 0), branchPiece);
		bc.build(board.getTile(3, 3), secondRoot);

		bc.build(board.getTile(2, 1), new Canal());
		bc.build(board.getTile(3, 1), new Canal());
		bc.build(board.getTile(3, 2), new Canal());
		
		game.simulate();
		
		assertTrue(game.getCanalSystemManager().canalRoots.contains(canalRoot));
		assertTrue(game.getCanalSystemManager().canalRoots.contains(secondRoot));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(canalRoot));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(secondRoot));
		
		assertTrue(canalRoot.hasWater());
		assertTrue(canal2.hasWater());
		assertTrue(canal3.hasWater());
		assertTrue(branchPiece.hasWater());
		assertTrue(secondRoot.hasWater());
	
		/* Finally, break the connection on one side
		 * 
		 * LLCLL
		 * LCCCL
		 * LLLCL
		 * LCLCL
		 * WWWWW
		 * 
		 */
		bc.demolish(canal2.getRootTile());
		
		game.simulate();

		assertTrue(game.getCanalSystemManager().canalRoots.contains(canalRoot));
		assertTrue(game.getCanalSystemManager().canalRoots.contains(secondRoot));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(canalRoot));
		assertTrue(game.getCanalSystemManager().canalSquares.contains(secondRoot));
		assertFalse(game.getCanalSystemManager().canalSquares.contains(canal2));
		
		assertTrue(canalRoot.hasWater());
		assertTrue(canal3.hasWater());
		assertTrue(branchPiece.hasWater());
		assertTrue(secondRoot.hasWater());
		
	}
	
}

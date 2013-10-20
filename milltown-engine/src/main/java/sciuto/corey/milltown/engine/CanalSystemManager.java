package sciuto.corey.milltown.engine;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.Canal;
import sciuto.corey.milltown.model.buildings.Water;


/**
 * Owns and operates the canal system.
 * @author Corey
 *
 */
public class CanalSystemManager implements Serializable {

	private static final long serialVersionUID = -4256775891274539695L;

	private final GameBoard board;
	
	private boolean doRecalc = false;

	/**
	 * All canal squares
	 */
	protected Set<Canal> canalSquares = new HashSet<Canal>();
	
	/**
	 * The squares from hence the water flows.
	 */
	protected Set<Canal> canalRoots = new HashSet<Canal>();
	
	public CanalSystemManager(GameBoard board){
		this.board = board;
	}
	
	/**
	 * Tells us to run on the next call of Manage
	 */
	public void primeRecalc() {
		doRecalc = true;
	}
	
	/**
	 * Set up a new canal square in the manager
	 * @param c
	 */
	public void newCanal(Canal c){

		Tile t = c.getRootTile();
		
		if ((board.getTileEast(t) != null && board.getTileEast(t).getContents() instanceof Water)
				|| (board.getTileWest(t) != null && board.getTileWest(t).getContents() instanceof Water)
				|| (board.getTileNorth(t) != null && board.getTileNorth(t).getContents() instanceof Water)
				|| (board.getTileSouth(t) != null && board.getTileSouth(t).getContents() instanceof Water)) {
			// If the tile is a new canal stub, fill it with water.
			canalRoots.add(c);
			c.setHasWater(true);
		}
		canalSquares.add(c);
		
		primeRecalc();
	}
	
	/**
	 * Remove a new canal square from the manager
	 * @param c
	 */
	public void removeCanal(Canal c){
		canalRoots.remove(c);
		canalSquares.remove(c);
		primeRecalc();
	}
	
	/**
	 * Makes sure the right canal tiles do and do not have water.
	 */
	public void manage(){
		if (!doRecalc){
			return;
		}
		doRecalc = false;
		
		/* This algorithm is crap but for now just get it working, right?
		 * 
		 * First, empty all the water. We're going to recalc the whole system.
		 */
		for (Canal c : canalSquares){
			c.setHasWater(false);
		}
		
		// Now, recursively, refill it.
		for (Canal c : canalRoots){
			c.setHasWater(true);
			
			Tile current = c.getRootTile();
			pathfindAndAddWater(board.getTileEast(current));
			pathfindAndAddWater(board.getTileWest(current));
			pathfindAndAddWater(board.getTileNorth(current));
			pathfindAndAddWater(board.getTileSouth(current));
		}
		
	}
	
	/**
	 * Regular old recursive depth-first traversal.
	 * @param t
	 */
	private void pathfindAndAddWater(Tile current){
		
		// If we're off the board, not on a canal, or have already filled this one up, bail.
		if (current == null || !(current.getContents() instanceof Canal) || ((Canal)current.getContents()).hasWater() ){
			return;
		}
		
		((Canal)current.getContents()).setHasWater(true);
		
		pathfindAndAddWater(board.getTileEast(current));
		pathfindAndAddWater(board.getTileWest(current));
		pathfindAndAddWater(board.getTileNorth(current));
		pathfindAndAddWater(board.getTileSouth(current));
		
	}
	
}

package sciuto.corey.milltown.engine;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.buildings.Canal;


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
	 * The squares from hence the water flows.
	 */
	private Set<Canal> canalRoots = new HashSet<Canal>();
	
	public CanalSystemManager(GameBoard board){
		this.board = board;
	}
	
	/**
	 * Adds a canal square as a root square
	 * @param canal
	 */
	public void addRoot(Canal canal){
		canalRoots.add(canal);
	}
	
	/**
	 * Retrieves all root squares
	 * @return
	 */
	public Set<Canal>getRoots(){
		return Collections.unmodifiableSet(canalRoots);
	}

	/**
	 * Tells us to run on the next call of Manage
	 */
	public void primeRecalc() {
		doRecalc = true;
	}
	
	/**
	 * Makes sure the right canal tiles do and do not have water.
	 */
	public void manage(){
		if (!doRecalc){
			return;
		}
		doRecalc = false;
		
		for (Canal c : canalRoots){
			// Well, this is crappy...we don't know where we are! I knew that was here for a reason...
			// Tile west = 
		}
		
	}
	
}

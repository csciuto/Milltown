package sciuto.corey.milltown.engine;

import java.io.Serializable;
import java.util.Collections;
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
	
	/**
	 * The squares from hence the water flows.
	 */
	private Set<Canal> canalRoots;
	
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
	
}

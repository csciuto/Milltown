package sciuto.corey.milltown.engine;

import java.io.Serializable;

/**
 * The main game state class
 * 
 * @author Corey
 * 
 */
public class Game implements Serializable {

	private static final long serialVersionUID = -6707842129706632605L;

	private GameBoard board;

	private GameDate gameDate = new GameDate();

	protected Game() {
		board = new GameBoard();
		gameDate = new GameDate();
	}

	/**
	 * Runs the engine.
	 */
	public void simulate() {
		gameDate.tick();
	}

	public GameBoard getBoard() {
		return board;
	}

	protected void setBoard(GameBoard b) {
		this.board = b;
	}

	public GameDate getGameDate() {
		return gameDate;
	}
}

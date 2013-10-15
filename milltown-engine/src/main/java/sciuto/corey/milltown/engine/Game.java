package sciuto.corey.milltown.engine;

import java.io.Serializable;

import org.apache.commons.lang3.mutable.MutableLong;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import sciuto.corey.milltown.model.DollarAmount;
import sciuto.corey.milltown.model.FormattedNumber;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.GameDate;

/**
 * The main game state class
 * 
 * @author Corey
 * 
 */
public class Game implements Serializable {

	private static final long serialVersionUID = -6707842129706632605L;

	private static final Logger LOGGER = Logger.getLogger(Game.class);
	
	private final GameBoard board;
	private final CanalSystemManager canalSystemManager;

	private GameDate gameDate = new GameDate();
	private FormattedNumber population = new FormattedNumber(0);
	private DollarAmount money = new DollarAmount(2000000L);
	private DollarAmount economy = new DollarAmount(0);
	
	private boolean isDebug = Boolean.parseBoolean(PropertiesReader.read("milltown.properties").getProperty("milltown.debug"));
	
	/**
	 * Create through the SaveGameManager
	 * 
	 * @param b The game board to init with. Used for testing
	 */
	protected Game(GameBoard b) {
		
		if (isDebug){
			Logger.getRootLogger().setLevel(Level.DEBUG);
			LOGGER.debug("Debug mode ON");
		}
		
		if (b == null){
			board = new GameBoard();
		} else {
			board = b;
		}
		
		canalSystemManager = new CanalSystemManager(board);
	}

	/**
	 * Runs the engine.
	 */
	public void simulate() {
		gameDate.addWeek();
		canalSystemManager.manage();
	}

	public GameBoard getBoard() {
		return board;
	}

	public GameDate getGameDate() {
		return gameDate;
	}
	
	public MutableLong getPopulation() {
		return population;
	}
	
	public MutableLong getMoney() {
		return money;
	}
	
	public MutableLong getEconomy() {
		return economy;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public CanalSystemManager getCanalSystemManager() {
		return canalSystemManager;
	}
}

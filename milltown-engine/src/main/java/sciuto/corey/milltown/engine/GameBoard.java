package sciuto.corey.milltown.engine;

import java.io.Serializable;

import sciuto.corey.milltown.model.board.Tile;

/**
 * The map of tiles.
 * @author Corey
 *
 */
public class GameBoard implements Serializable{
	
	private static final long serialVersionUID = -3708198581963691226L;

	private final int boardSize;

	private Tile[][] tiles;
	
	/**
	 * Returns the requested tile, or null if that's off the board
	 * @param tile
	 * @return
	 */
	public Tile getTile(int x, int y){
		return getTileHelper(x,y);
	}

	/**
	 * Returns the tile to the North, or null if that's off the board
	 * @param tile
	 * @return
	 */
	public Tile getTileNorth(Tile tile) {
		return getTileHelper(tile.getXLoc()-1,tile.getYLoc());
	}

	/**
	 * Returns the tile to the South, or null if that's off the board
	 * @param tile
	 * @return
	 */
	public Tile getTileSouth(Tile tile) {
		return getTileHelper(tile.getXLoc()+1,tile.getYLoc());
	}

	/**
	 * Returns the tile to the East, or null if that's off the board
	 * @param tile
	 * @return
	 */
	public Tile getTileEast(Tile tile) {
		return getTileHelper(tile.getXLoc(),tile.getYLoc()+1);
	}

	/**
	 * Returns the tile to the West, or null if that's off the board
	 * @param tile
	 * @return
	 */
	public Tile getTileWest(Tile tile) {
		return getTileHelper(tile.getXLoc(),tile.getYLoc()-1);
	}
	
	/**
	 * Returns the number of squares on each dimension of the board
	 * @return
	 */
	public int getBoardSize() {
		return boardSize;
	}

	/**
	 * Creates a 25x25 blank board
	 */
	public GameBoard(){
		this(25);
	}

	/**
	 * Creates a blank board
	 * @param boardSize How big to make it on each side in number of squares
	 */
	public GameBoard(int boardSize){
		this.boardSize = boardSize;
		tiles = new Tile[boardSize][boardSize];
		for (int x=0;x<boardSize;x++){
			for (int y=0;y<boardSize;y++){
				Tile tile = new Tile(x,y); 
				tiles[x][y] = tile;
			}
		}
	}

	/**
	 * Attempts to get the tile requested. NULL if it is off the board.
	 * @param x
	 * @param y
	 * @return
	 */
	private Tile getTileHelper(int x, int y){
		try{
			return tiles[x][y];
		}
		catch (ArrayIndexOutOfBoundsException e){
			return null;
		}
	}
	
}

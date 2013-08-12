package sciuto.corey.milltown.engine;

import java.io.Serializable;

import sciuto.corey.milltown.engine.elements.Land;

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
	 * Creates a 25x25 blank board
	 */
	protected GameBoard(){
		this(25);
	}
	
	/**
	 * Creates a blank board
	 * @param boardSize How big to make it on each side in number of squares
	 */
	protected GameBoard(int boardSize){
		this.boardSize = boardSize;
		tiles = new Tile[boardSize][boardSize];
		for (int x=0;x<boardSize;x++){
			for (int y=0;y<boardSize;y++){
				Tile tile = new Tile(x,y,new Land()); 
				tiles[x][y] = tile;
			}
		}
	}
	
	public Tile getTile(int x, int y){
		Tile tile = getTileHelper(x,y);
		if (tile == null){
			throw new IllegalArgumentException(String.format("(%d,%d) is not a valid tile!",x, y));
		}
		return tile;
	}

	public Tile getTileNorth(Tile tile) {
		return getTileHelper(tile.getX()-1,tile.getY());
	}

	public Tile getTileSouth(Tile tile) {
		return getTileHelper(tile.getX()+1,tile.getY());
	}

	public Tile getTileEast(Tile tile) {
		return getTileHelper(tile.getX(),tile.getY()+1);
	}

	public Tile getTileWest(Tile tile) {
		return getTileHelper(tile.getX(),tile.getY()-1);
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

	/**
	 * Returns the number of squares on each dimension of the board
	 * @return
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
}

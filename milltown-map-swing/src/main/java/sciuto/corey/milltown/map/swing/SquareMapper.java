package sciuto.corey.milltown.map.swing;

import java.awt.event.MouseEvent;

import sciuto.corey.milltown.map.swing.components.GameMap;
import sciuto.corey.milltown.model.board.GameBoard;
import sciuto.corey.milltown.model.board.Tile;

/**
 * Maps tiles to the UI
 * 
 * @author Corey
 * 
 */
public class SquareMapper {

	private final GameBoard board;
	private final GameMap map;
	
	private int squareSize;
	
	/**
	 * Creates a SquareMapper
	 * @param board The board to select a tile from
	 * @param mapSize The map to map from
	 */
	public SquareMapper(GameBoard board, GameMap map){
		this.board = board;
		this.map = map;
		
		int smallSide =  map.getWidth() < map.getHeight() ? map.getWidth() : map.getHeight();
		this.squareSize = smallSide / board.getBoardSize();
	}
	
	/**
	 * Maps the tile on board to the click position on the Mouse event.
	 * @param e The click event
	 * @return
	 */
	public Tile mapSquare(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			
			int xModSquareSize = mappingHelper(x);
			int yModSquareSize = mappingHelper(y);
			
			return board.getTile(xModSquareSize, yModSquareSize);
	}
	
	/**
	 * Updates the size of the map and square. Attach to an event listener.
	 */
	public void update(){
		int smallSide =  map.getWidth() < map.getHeight() ? map.getWidth() : map.getHeight();
		this.squareSize = smallSide / board.getBoardSize();
	}
	
	/**
	 * To make unit testing simpler.
	 * @param pixel
	 */
	protected int mappingHelper(int pixel){
		
		int tile = pixel /squareSize;
		if (tile >= board.getBoardSize()){
			// To prevent the last pixel from causing an error:
			// e.g. 625px / 25 = 25. The last index is 24...
			tile -= 1;
		}
		return tile;
	}

}

package sciuto.corey.milltown.map.swing;

import java.awt.event.MouseEvent;

import sciuto.corey.milltown.engine.GameBoard;
import sciuto.corey.milltown.model.board.Tile;

/**
 * Maps tiles to the UI
 * 
 * @author Corey
 * 
 */
public class SquareMapper {

	private final GameBoard board;
	private final int squareSize;
	
	/**
	 * Creates a SquareMapper
	 * @param board The board to select a tile from
	 * @param mapSize The size of the map in pixels
	 */
	public SquareMapper(GameBoard board, int mapSize){
		this.board = board;
		this.squareSize = mapSize / board.getBoardSize();
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
	 * To make unit testing simpler.
	 * @param pixel
	 */
	protected int mappingHelper(int pixel){
		int tile = pixel / squareSize;
		if (tile == board.getBoardSize()){
			// To prevent the last pixel from causing an error:
			// 625px / 25 = 25. The last index is 24...
			tile -= 1;
		}
		return tile;
	}

}

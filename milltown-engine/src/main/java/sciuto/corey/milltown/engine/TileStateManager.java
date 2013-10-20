package sciuto.corey.milltown.engine;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import sciuto.corey.milltown.model.board.Tile;

/**
 * Maintains state information about tiles, like if a UI should redraw them or not.
 * @author Corey
 *
 */
public class TileStateManager implements Serializable {

	private static final long serialVersionUID = -6606393727066183055L;
	
	
	private Set<Tile> dirtyTiles = new HashSet<Tile>();
	
	public boolean setDirtyTile(Tile t){
		return dirtyTiles.add(t);
	}
	
	public boolean isTileDirty(Tile t){
		return dirtyTiles.contains(t);
	}
	
	/**
	 * Returns a view into the dirty tiles
	 * @return
	 */
	public Set<Tile> getDirtyTiles(){
		return Collections.unmodifiableSet(dirtyTiles);
	}
	
	public void clearDirtyTiles(){
		dirtyTiles = new HashSet<Tile>();
	}
	
}

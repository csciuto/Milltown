package sciuto.corey.milltown.model.board;

import java.io.Serializable;

import org.apache.commons.lang3.tuple.Pair;


/**
 * A game entity
 * 
 * @author Corey
 * 
 */
public abstract class AbstractBuilding implements Serializable {

	private static final long serialVersionUID = -6372187461985835699L;

	protected static final Pair<Integer, Integer> SMALL_SQUARE = Pair.of(1, 1);
	protected static final Pair<Integer, Integer> MEDIUM_SQUARE = Pair.of(2, 2);
	protected static final Pair<Integer, Integer> LARGE_SQUARE = Pair.of(3, 3);

	protected final Pair<Integer, Integer> size;

	private Tile rootTile = null;

	/**
	 * Instantiates an unbuilt building.
	 * 
	 * @param size
	 */
	public AbstractBuilding(Pair<Integer, Integer> size) {
		this.size = size;
	}

	public Pair<Integer, Integer> getSize() {
		return size;
	}

	public Tile getRootTile() {
		return rootTile;
	}

	/**
	 * Unit tests only! Otherwise, use the BuidingConstructor.
	 * @param t
	 */
	public void setRootTile(Tile t) {
		rootTile = t;
	}
	
	@Override
	public String toString(){
		return this.getClass().getSimpleName();
	}
}

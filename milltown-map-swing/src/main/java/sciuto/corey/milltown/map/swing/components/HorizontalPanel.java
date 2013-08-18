package sciuto.corey.milltown.map.swing.components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Goes to the side of the map.
 * 
 * @author Corey
 * 
 */
public class HorizontalPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5378554478433884941L;
	protected final static int BAR_WIDTH = 1000;

	/**
	 * Creates a new InfoBar
	 * @param name The name of the box
	 * @param height The height it has
	 */
	public HorizontalPanel(String name, int height) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setName(name);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(BAR_WIDTH,height));
	}

}

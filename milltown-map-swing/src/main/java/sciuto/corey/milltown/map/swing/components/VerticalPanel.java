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
public class VerticalPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4808845618130644861L;
	protected final static int BOX_HEIGHT = 700;

	/**
	 * Creates a new ToolBox
	 * @param name The name of the box
	 * @param width The width it has
	 */
	public VerticalPanel(String name, int width) {
		super();
		setName(name);
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(width, BOX_HEIGHT));
	}

}

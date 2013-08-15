package sciuto.corey.milltown.map.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Goes to the side of the map.
 * 
 * @author Corey
 * 
 */
public class ToolBox extends JPanel {

	protected final static int BOX_HEIGHT = 700;

	/**
	 * Creates a new ToolBox
	 * @param name The name of the box
	 * @param width The width it has
	 */
	public ToolBox(String name, int width) {
		super();
		setName(name);
		setBackground(Color.WHITE);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(width, BOX_HEIGHT));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked in infobox");
			}
		});
	}

}

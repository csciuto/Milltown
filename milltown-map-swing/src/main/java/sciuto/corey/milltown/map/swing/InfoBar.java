package sciuto.corey.milltown.map.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Goes to the side of the map.
 * 
 * @author Corey
 * 
 */
public class InfoBar extends JPanel {

	protected final static int BAR_WIDTH = 1000;

	/**
	 * Creates a new InfoBar
	 * @param name The name of the box
	 * @param height The height it has
	 */
	public InfoBar(String name, int height) {
		super();
		setName(name);
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setPreferredSize(new Dimension(BAR_WIDTH,height));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Clicked in infobar");
			}
		});
	}

}

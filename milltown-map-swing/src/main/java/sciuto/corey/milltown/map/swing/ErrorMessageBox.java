package sciuto.corey.milltown.map.swing;

import javax.swing.JOptionPane;

/**
 * Pops up an error message box.
 * 
 * @author Corey
 * 
 */
public class ErrorMessageBox {

	public static void show(String message) {
		MainScreen screen = MainScreen.instance();
		JOptionPane.showMessageDialog(screen, message, "Milltown", JOptionPane.ERROR_MESSAGE);
	}

}

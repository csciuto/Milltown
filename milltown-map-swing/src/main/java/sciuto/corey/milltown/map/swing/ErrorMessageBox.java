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
		JOptionPane.showMessageDialog(null, message, "Milltown", JOptionPane.ERROR_MESSAGE);
	}

}

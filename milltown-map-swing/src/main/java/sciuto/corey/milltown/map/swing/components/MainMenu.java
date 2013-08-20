package sciuto.corey.milltown.map.swing.components;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.engine.Game;
import sciuto.corey.milltown.engine.SaveGameManager;
import sciuto.corey.milltown.engine.exception.LoadGameException;
import sciuto.corey.milltown.engine.exception.SaveGameException;
import sciuto.corey.milltown.map.swing.ErrorMessageBox;
import sciuto.corey.milltown.map.swing.MainScreen;

public class MainMenu extends JMenuBar implements ActionListener {

	private static final Logger LOGGER = Logger.getLogger(MainMenu.class);

	private static final long serialVersionUID = 1121772552134399148L;

	private static final String SAVE = "Save";
	private static final String LOAD = "Load";
	private static final String EXIT = "Exit";

	private static final String HELP = "Help";
	private static final String ABOUT = "About";

	private final JFileChooser fc;

	public MainMenu() {

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		this.add(file);
		JMenuItem save = new JMenuItem(SAVE);
		save.setMnemonic(KeyEvent.VK_S);
		save.addActionListener(this);
		save.setName(SAVE);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		file.add(save);
		JMenuItem load = new JMenuItem(LOAD);
		load.setMnemonic(KeyEvent.VK_L);
		load.addActionListener(this);
		load.setName(LOAD);
		load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		file.add(load);
		file.addSeparator();
		JMenuItem exit = new JMenuItem(EXIT);
		exit.setMnemonic(KeyEvent.VK_X);
		exit.addActionListener(this);
		exit.setName(EXIT);
		file.add(exit);

		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		this.add(help);
		JMenuItem help1 = new JMenuItem(HELP);
		help1.setMnemonic(KeyEvent.VK_H);
		help1.addActionListener(this);
		help1.setName(HELP);
		help.add(help1);
		help.addSeparator();
		JMenuItem about = new JMenuItem(ABOUT);
		about.setMnemonic(KeyEvent.VK_A);
		about.addActionListener(this);
		about.setName(ABOUT);
		help.add(about);

		fc = new JFileChooser();
		FileFilter filter = new FileNameExtensionFilter("Milltown Save (*.mtown)", "mtown");
		fc.addChoosableFileFilter(filter);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MainScreen.instance().disableKeyEvents();
			String name = ((JMenuItem) e.getSource()).getName();
			if (name.equals(SAVE)) {
				doSaveDialog();
			} else if (name.equals(LOAD)) {
				doLoadDialog();
			} else if (name.equals(EXIT)) {
				// Custom button text
				Object[] options = { "Yes", "No", "Cancel" };
				int n = JOptionPane.showOptionDialog(MainScreen.instance(), "Save first?", "Milltown!",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
				if (n == 0) {
					doSaveDialog();
					System.exit(1);
				} else if (n == 1) {
					System.exit(1);
				}
			}
		} finally {
			MainScreen.instance().enableKeyEvents();
		}
	}

	private final void doSaveDialog() {
		if (fc.showSaveDialog(MainScreen.instance()) == JFileChooser.APPROVE_OPTION) {
			try {
				SaveGameManager.saveGame(fc.getSelectedFile(), MainScreen.instance().getGame());
			} catch (SaveGameException e) {
				LOGGER.error("Error saving game", e);
				ErrorMessageBox.show("Could not save game to file " + fc.getSelectedFile().getAbsolutePath());
			}
		}
	}

	private final void doLoadDialog() {
		if (fc.showOpenDialog(MainScreen.instance()) == JFileChooser.APPROVE_OPTION) {
			try {
				Game g = SaveGameManager.loadGame(fc.getSelectedFile());
				MainScreen.instance().loadMainScreen(g);
			} catch (LoadGameException e) {
				LOGGER.error("Error loading game", e);
				ErrorMessageBox.show("Could not load game from file " + fc.getSelectedFile().getAbsolutePath());
			}
		}
	}

}

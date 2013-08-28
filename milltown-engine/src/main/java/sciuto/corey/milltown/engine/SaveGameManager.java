package sciuto.corey.milltown.engine;

import java.io.*;
import java.net.URL;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.engine.exception.LoadGameException;
import sciuto.corey.milltown.engine.exception.SaveGameException;

/**
 * Creates, saves, and loads Games.
 * 
 * @author Corey
 * 
 */
public class SaveGameManager {

	private static final Logger LOGGER = Logger.getLogger(SaveGameManager.class);

	public static Game newBlankGame() {
		return new Game(null);
	}
	
	public static Game newGame() {
		InputStream in = null;
		ObjectInputStream s = null;
		Game g = null;
		try {
			/*
			 * TODO: This is an issue because the serializaiton doesn't stay up to date as the engine changes...
			 * URL file = SaveGameManager.class.getClassLoader().getResource("default.mtown");
			 * in = file.openStream();
			 * s = new ObjectInputStream(in);
			 * g = (Game) s.readObject();
			 * s.close();*/
			g = new Game(null);
			
			LOGGER.debug(String.format("Loaded default game"));
		} catch (Exception e) {
			LOGGER.fatal(String.format("Unable to load default game"),e);
			System.exit(-1);
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return g;
	}

	public static Game loadGame(File file) throws LoadGameException {

		FileInputStream in = null;
		ObjectInputStream s = null;
		Game g = null;
		try {
			in = new FileInputStream(file);
			s = new ObjectInputStream(in);
			g = (Game) s.readObject();

			LOGGER.info(String.format("Loaded Game %s", file.getAbsoluteFile()));
			s.close();
		} catch (Exception e) {
			LOGGER.error(String.format("Unable to load game %s",file.getAbsoluteFile()),e);
			throw new LoadGameException(file, e);
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return g;
	}

	public static void saveGame(File file, Game game) throws SaveGameException {

		FileOutputStream f = null;
		ObjectOutput s = null;
		try {
			f = new FileOutputStream(file);
			s = new ObjectOutputStream(f);
			s.writeObject(game);
			s.flush();

			LOGGER.info(String.format("Saved Game %s", file.getAbsoluteFile()));
			s.close();
		} catch (Exception e) {
			LOGGER.error(String.format("Unable to save game %s",file.getAbsoluteFile()),e);
			throw new SaveGameException(file, e);
		} finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}

}

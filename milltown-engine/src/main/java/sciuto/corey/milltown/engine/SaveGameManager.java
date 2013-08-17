package sciuto.corey.milltown.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Creates, saves, and loads Games.
 * 
 * @author Corey
 * 
 */
public class SaveGameManager {

	public static Game newGame() {
		return new Game();
	}

	public static Game loadGame(File file) {
		
	    FileInputStream in = null;
	    ObjectInputStream s = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    try {
			s = new ObjectInputStream(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Game g = null;
	    try {
			g = (Game) s.readObject();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return g;
	    
	}

	public static void saveGame(File file, Game game) {

		FileOutputStream f = null;
		ObjectOutput s = null;
		try {
			f = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			s = new ObjectOutputStream(f);
			s.writeObject(game);
			s.flush();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return;
	}

}

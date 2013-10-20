package sciuto.corey.milltown.map.swing;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.model.board.AbstractBuilding;
import sciuto.corey.milltown.model.board.Tile;
import sciuto.corey.milltown.model.buildings.*;

/**
 * This static class contains methods related to the UI components of buildings.
 * 
 * @author Corey
 * 
 */
public class BuildingGraphicsRetriever {

	private static final Logger LOGGER = Logger.getLogger(BuildingGraphicsRetriever.class);

	private final GraphicsCache cache = new GraphicsCache();

	/**
	 * Returns a graphical representation of the passed-in building
	 * 
	 * @param tile
	 * @return
	 */
	public BufferedImage retrieveImage(Tile tile) {
		String fileName = tile.getContents().getFileName();

		BufferedImage img = null;
		if (fileName != null) {
			return cache.getGraphics(fileName);
		}
		return img;
	}

	/**
	 * Returns the icon matching the building class.
	 * 
	 * @param b
	 * @return
	 */
	public Icon retrieveIcon(Class<? extends AbstractBuilding> buildingClass) {

		String fileName = getFileName(buildingClass);

		URL url = this.getClass().getResource(String.format("/map_images/%s_ico.png", fileName));
		if (url == null) {
			String msg = String.format("Cannot find image map_images/%s_ico.png", fileName);
			// All we can do is crash to the desktop. Popping up a dialog
			// causes an infinite loop.
			LOGGER.fatal(msg, new Exception().fillInStackTrace());
			System.exit(-1);
		}
		return new ImageIcon(url);
	}

	/**
	 * Retrieves the file name by class
	 * @param buildingClass
	 * @return
	 */
	public String getFileName(Class<? extends AbstractBuilding> buildingClass) {
		if (buildingClass.equals(Land.class)) {
			return "land";
		} else if (buildingClass.equals(Water.class)) {
			return "water";
		} else if (buildingClass.equals(House.class)) {
			return "house_1";
		} else if (buildingClass.equals(Tenement.class)) {
			return "tenement_1";
		} else if (buildingClass.equals(Office.class)) {
			return "office_1";
		} else if (buildingClass.equals(Mill.class)) {
			return "mill";
		} else if (buildingClass.equals(Warehouse.class)) {
			return "warehouse";
		} else if (buildingClass.equals(Road.class)) {
			return "road";
		} else if (buildingClass.equals(Canal.class)) {
			return "canal";
		} else {
			String msg = String.format("Cannot find image for %s", buildingClass.toString());
			// All we can do is crash to the desktop. Popping up a dialog
			// causes an infinite loop.
			LOGGER.fatal(msg, new Exception().fillInStackTrace());
			System.exit(-1);
		}

		return null;
	}
}

package sciuto.corey.milltown.map.swing;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import sciuto.corey.milltown.model.board.AbstractBuilding;
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
	 * @param buildingClass
	 * @return
	 */
	public BufferedImage retrieveImage(Class<? extends AbstractBuilding> buildingClass) {
		String fileName = getFileName(buildingClass);

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

		URL url = this.getClass().getResource(
				String.format("/map_images/%s_ico.png", fileName));
		if (url == null) {
			String msg = String.format("Cannot find image map_images/%s_ico.png", fileName);
			// All we can do is crash to the desktop. Popping up a dialog
			// causes an infinite loop.
			LOGGER.fatal(msg, null);
			System.exit(-1);
		}
		return new ImageIcon(url);
	}

	/**
	 * Retrieves the actual class to build if there are variants used by the UI
	 * 
	 * @param classToBuild
	 * @return
	 */
	public Class<? extends AbstractBuilding> getVariantSelector(Class<? extends AbstractBuilding> classToBuild) {

		if (classToBuild.equals(House1.class)) {
			double d = Math.random();

			if (d <= 0.3) {
				return House1.class;
			} else if (d <= 0.6) {
				return House2.class;
			} else {
				return House3.class;
			}
		}

		// No variants
		return classToBuild;
	}

	public String getFileName(Class<? extends AbstractBuilding> buildingClass) {
		if (buildingClass.equals(Land.class)){
			return "land";
		}else if (buildingClass.equals(Water.class)) {
			return "water";
		} else if (buildingClass.equals(House1.class)) {
			return "house_1";
		} else if (buildingClass.equals(House2.class)) {
			return "house_2";
		} else if (buildingClass.equals(House3.class)) {
			return "house_3";
		} else if (buildingClass.equals(Mill.class)) {
			return "mill";
		} else if (buildingClass.equals(Road.class)) {
			return "road";
		} else if (buildingClass.equals(Canal.class)) {
			return "canal";
		} else {
			String msg = String.format("Cannot find image for %s", buildingClass.toString());
			// All we can do is crash to the desktop. Popping up a dialog
			// causes an infinite loop.
			LOGGER.fatal(msg, null);
			System.exit(-1);
		}

		return null;
	}
}

package sciuto.corey.milltown.map.swing;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class GraphicsCache {

	private static final Logger LOGGER = Logger.getLogger(GraphicsCache.class);
	
	private Map<String, BufferedImage> cache = new HashMap<String, BufferedImage>();
	
	public BufferedImage getGraphics(String fileName){
		
		BufferedImage img = cache.get(fileName);
		
		if (img == null){
			URL url = this.getClass().getResource(
					String.format("/map_images/%s.png", fileName));
			if (url == null) {
				String msg = String.format("Cannot find image map_images/%s.png", fileName);
				// All we can do is crash to the desktop. Popping up a dialog
				// causes an infinite loop.
				LOGGER.fatal(msg, new Exception().fillInStackTrace());
				System.exit(-1);
			}
			try {
				img = ImageIO.read(url);
			} catch (IOException e) {
				String msg = String.format("Cannot render image: %s", url);
				// All we can do is crash to the desktop. Popping up a dialog
				// causes an infinite loop.
				LOGGER.fatal(msg, new Exception().fillInStackTrace());
				System.exit(-1);
			}
		}
		
		cache.put(fileName, img);
		
		return img;
		
	}
	
	
}

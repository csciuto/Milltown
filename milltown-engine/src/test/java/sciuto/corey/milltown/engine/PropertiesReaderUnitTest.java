package sciuto.corey.milltown.engine;

import java.util.Properties;

import org.junit.Test;
import static org.junit.Assert.*;

public class PropertiesReaderUnitTest {

	@Test
	public void testPropertiesFile() {
		Properties props = PropertiesReader.read("test.properties");
		assertNull(props.getProperty("milltown.version"));
		assertNotNull(props.getProperty("milltown.debug"));
		assertEquals("true",props.getProperty("milltown.debug"));

		Properties nullProps = PropertiesReader.read("invalid.milltown.properties");
		assertNull(nullProps.getProperty("doesntExist.version"));
	}
}

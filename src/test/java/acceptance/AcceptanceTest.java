package acceptance;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AcceptanceTest {

    @Test
    public void canGetValuesFromPropertiesWithoutSpringBootTest() throws IOException {
        Properties properties = getSpringProfileProperties();
        assertEquals("9001", properties.getProperty("server.port"));
    }

    private Properties getSpringProfileProperties() throws IOException {
        String springProfile = System.getProperty("spring.profiles.active");
        if (null == springProfile) {
            springProfile = "";
        } else {
            springProfile = "-" + springProfile;
        }
        InputStream resource = getClass().getClassLoader().getResourceAsStream("application" + springProfile + ".properties");
        Properties properties = new Properties();
        properties.load(resource);
        return properties;
    }
}

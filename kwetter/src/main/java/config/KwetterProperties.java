package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class KwetterProperties {
    private Properties properties;

    public KwetterProperties(String name) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream stream = classLoader.getResourceAsStream(name);
            properties = new Properties();
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getValue(String property) {
        return properties.getProperty(property);
    }
}

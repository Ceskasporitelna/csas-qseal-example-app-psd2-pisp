package cz.csas.psd2.qsealsample.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class ConfigurationProperties {

    private static Properties prop;

    public static String getProperty(String key) {
        return getPropValues().getProperty(key);
    }

    private static Properties getPropValues() {
        if (prop == null) {
            String propFileName = "/configuration.properties";

            try (InputStream inputStream = ClassLoader.class.getResourceAsStream(propFileName)) {

                if (inputStream != null) {
                    prop = new Properties();
                    prop.load(inputStream);
                } else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
                }

            } catch (IOException e) {
                throw new UncheckedIOException(e);

            }
        }
        return prop;
    }
}

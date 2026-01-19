package dunk.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private static Properties properties;

	static {
		try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
			properties = new Properties();
			properties.load(is);
		} catch (Exception e) {
			throw new RuntimeException("Could not load config.properties");
		}
	}

	public static String getLocalUrl() {
		return properties.getProperty("local.url");
	}
}
package org.elrudaille.courses.util;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

	public static String getBaseDirectory() {
		return getValueFromCourseProperty("baseOutputDirectory");
	}

	public static String getLogFilePath() {
		return getValueFromLog4JProperty("log4j.appender.file.File");
	}

	public static String getValueFromLyndaProperty(String property) {
		return getValueFromPropertyOrNull(property, "lynda", "course.properties");
	}

	public static String getValueFromUdemyProperty(String property) {
		return getValueFromPropertyOrNull(property, "udemy", "course.properties");
	}

	public static String getValueFromCourseProperty(String property) {
		return getValueFromPropertyOrNull(property, null, "course.properties");
	}

	private static String getValueFromLog4JProperty(String property) {
		return getValueFromPropertyOrNull(property, null, "log4j.properties");
	}

	private static String getValueFromPropertyOrNull(String property, String prefix, String fileName) {
		Properties prop = new Properties();
		try {
			prop.load(ConfigUtil.class.getResourceAsStream("/" + fileName));
		} catch (IOException e) {
			return null;
		}
		if (prefix != null)
			return prop.getProperty(prefix + "." + property);
		return prop.getProperty(property);
	}
}

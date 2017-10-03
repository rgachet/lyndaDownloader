package org.elrudaille.courses.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.elrudaille.courses.model.Course;

import com.google.gson.Gson;
import org.elrudaille.courses.model.LearningPath;

public class GsonUtil {
	private static Logger logger = Logger.getLogger(GsonUtil.class.getPackage().getName());
	private static Gson gson = new Gson();

	public static boolean save(Course course, String baseOutputDir) {
		logger.info(String.format("JSON course '%s'", course.getTitre()));
		return GsonUtil.save(course, baseOutputDir + "\\savedState.json", true);
	}

	public static boolean save(LearningPath path, String baseOutputDir, String name) {
		return GsonUtil.save(path, String.format("%s/LearningPaths/%s.json", baseOutputDir, name), true);
	}

	public static boolean save(Object o, String jsonFilePath, boolean overwrite) {
		String json = gson.toJson(o);
		try {
			File jsonFile = new File(jsonFilePath);
			File dir = new File(jsonFile.getParent());
			if (!dir.exists() || !dir.isDirectory())
				dir.mkdirs();
			if (!jsonFile.exists() || jsonFile.getTotalSpace() == 0 || overwrite) {
				FileWriter writer = new FileWriter(jsonFile);
				writer.write(json);
				logger.info(String.format("JSON saved to '%s'", jsonFilePath));
				writer.close();
			} else {
				logger.info(String.format("JSON already exists and will not be overwritten '%s'", jsonFilePath));
			}

		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static Course load(File jsonFile) {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(jsonFile));
		} catch (FileNotFoundException e) {
			return null;
		}
		Course course = gson.fromJson(br, Course.class);
		return course;
	}
}

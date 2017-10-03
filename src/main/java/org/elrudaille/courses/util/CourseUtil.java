package org.elrudaille.courses.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.elrudaille.courses.model.Lesson;
import org.elrudaille.courses.model.Chapter;
import org.elrudaille.courses.model.Course;

public class CourseUtil {

	protected static Logger logger = Logger.getLogger(CourseUtil.class.getPackage().getName());

	public static void checkAndCorrect(Course cours) {
		boolean corrupted = false;
		for (Chapter chapter : cours.getChapitres()) {
			for (Lesson lesson : chapter.getLessons()) {
				boolean isVideoReallyDownloaded = getVideoPath(cours, chapter, lesson).toFile().exists();
				if ((lesson.isVideoDownloaded() && !isVideoReallyDownloaded)
						|| (!lesson.isVideoDownloaded() && isVideoReallyDownloaded)) {
					lesson.setVideoDownloaded(isVideoReallyDownloaded);
					corrupted = true;
				}
			}
		}
		if (corrupted) {
			logger.debug("Correcting course " + cours.getTitre());
			GsonUtil.save(cours, ConfigUtil.getBaseDirectory());
		}
	}

	private static Path getVideoPath(Course cours, Chapter chapter, Lesson lesson) {
		Path path = Paths.get(ConfigUtil.getBaseDirectory(), cours.getOutputDir(), String.valueOf(chapter.getNumero()),
				lesson.getVideoId() + ".mp4");
		return path;
	}

	public static boolean isDownloaded(Course cours) {
		boolean isDownloaded = true;
		for (Chapter chapter : cours.getChapitres()) {
			for (Lesson lesson : chapter.getLessons()) {
				if (!lesson.isVideoDownloaded()) {
					isDownloaded = false;
					break;
				}
				if (!isDownloaded)
					break;
			}
			if (!isDownloaded)
				break;
		}
		return isDownloaded;
	}

}

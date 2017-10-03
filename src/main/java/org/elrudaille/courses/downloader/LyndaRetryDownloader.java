package org.elrudaille.courses.downloader;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.elrudaille.courses.LyndaSignIn;
import org.elrudaille.courses.model.Chapter;
import org.elrudaille.courses.model.Course;
import org.elrudaille.courses.model.Lesson;
import org.elrudaille.courses.model.Video;
import org.elrudaille.courses.parser.LyndaCourseParser;
import org.elrudaille.courses.util.ConfigUtil;
import org.elrudaille.courses.util.GsonUtil;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class LyndaRetryDownloader {

	private static Logger logger = Logger.getLogger(LyndaRetryDownloader.class.getName());

	public static void main(String[] args) throws IOException {
        String baseOutputDirectory = ConfigUtil.getBaseDirectory();
        File[] courseDirectories = new File(baseOutputDirectory).listFiles(File::isDirectory);
        Set<Integer> coursesToRetry = new HashSet<>();
        for(File courseDirectory : courseDirectories){
            if (!courseDirectory.getName().startsWith("L"))
                coursesToRetry.add(Integer.valueOf(courseDirectory.getName()));
        }
        if(!coursesToRetry.isEmpty()) {
            LyndaSignIn signIn = new LyndaSignIn();
            try {
                signIn.signIn();
                for (Integer id : coursesToRetry) {
                    LyndaDownloader.downloadCourse(ConfigUtil.getBaseDirectory(), id);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            } finally {
                signIn.signOut();
            }
        }
	}

}

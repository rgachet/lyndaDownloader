package org.elrudaille.courses.parser;

import java.io.*;
import java.util.*;

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
import org.elrudaille.courses.util.ConfigUtil;
import org.elrudaille.courses.util.GsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;

public class LyndaCourseParser {

	private static Logger logger = Logger.getLogger(LyndaCourseParser.class.getName());

    public static void extractCourse(String dossierGeneration, Integer courseId) throws IOException, UnirestException {
        String urlCourse = String.format("https://www.lynda.com/ajax/player?courseId=%s&type=course",courseId);
        GetRequest getRequest = Unirest.get(urlCourse);
        File json = new File(String.format("%s/raw.json", dossierGeneration));
        new File(json.getParent()).mkdirs();
        logger.info("Saving "+ json);
        IOUtils.copy(getRequest.asString().getRawBody(), new FileOutputStream(json));
        Course course = LyndaCourseParser.toCourse(json);
        GsonUtil.save(course, dossierGeneration);
    }

    public static Course toCourse(File rawJson) {
        Course course = new Course(Course.LYNDA);
        JSONObject json = null;
        try {
            json = new JSONObject(IOUtils.toString(new FileInputStream(rawJson)));
        } catch (Exception e) {
            return null;
        }
        course.setId(json.getInt("ID"));
        course.setTitre(json.getString("Title"));
        JSONArray chapters = json.getJSONArray("Chapters");
        for(Object object : chapters.toList()){
            HashMap chapter = (HashMap) object;
            Chapter chapitre = new Chapter();
            chapitre.setTitre((String)chapter.get("Title"));
            chapitre.setNumero((Integer)chapter.get("ChapterIndex"));
            course.addChapitre(chapitre);
            logger.debug("Chapitre: " + chapitre.getTitre());
            for(HashMap video : (List<HashMap>) chapter.get("Videos")){
                Lesson lesson = new Lesson();
                chapitre.addLesson(lesson);
                lesson.setTitle((String)video.get("Title"));
                lesson.setNumero((Integer)video.get("VideoIndex"));
                lesson.setDuration((Integer)video.get("DurationInSeconds"));
                lesson.setVideoId((Integer)video.get("ID"));
                logger.debug("Lesson: " + lesson.getTitle());
                for(HashMap format : (List<HashMap>) video.get("Formats")){
                    Video videoFormat = new Video();
                    videoFormat.setExtension((String)format.get("Extension"));
                    videoFormat.setResolution((Integer)format.get("Resolution"));
                    videoFormat.setUrl((String)format.get("Url"));
                    lesson.addVideo(videoFormat);
                }
            }
        }
        return course;
    }
}

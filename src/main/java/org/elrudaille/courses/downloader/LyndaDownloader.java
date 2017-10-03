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
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class LyndaDownloader {

	private static Logger logger = Logger.getLogger(LyndaDownloader.class.getName());

	public static void main(String[] args) throws IOException {
        LyndaSignIn signIn = new LyndaSignIn();
        try {
            signIn.signIn();
            String baseOutputDir = ConfigUtil.getBaseDirectory();
            String[] ids = args[0].split(",");
            for(String idStr : ids) {
                downloadCourse(baseOutputDir, Integer.valueOf(idStr));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally{
            signIn.signOut();
        }

	}

    public static void downloadCourse(String dossierGeneration, Integer id) {
        try {
            dossierGeneration = String.format("%s/%s", dossierGeneration, id);
            LyndaCourseParser.extractCourse(dossierGeneration, id);
            new LyndaDownloader().download(String.format("%s/savedState.json", dossierGeneration), dossierGeneration);
        }
        catch(Exception e){
            logger.error(String.format("Cours %s - %s", id, e.getMessage()));
        }
    }

    private void download(String jsonFile, String dossierGeneration) {
        Course course = GsonUtil.load(new File(jsonFile));
        for(Chapter chapitre : course.getChapitres()){
            for(Lesson lesson : chapitre.getLessons())
            {
                logger.debug(String.format("[%s] %s", chapitre.getTitre(), lesson.getTitle()));
                File output = new File(String.format("%s/%s.mp4",dossierGeneration, lesson.getVideoId()));
                for(Video video : lesson.getVideos()){
                    if(video.getResolution() == 720 && "mp4".equals(video.getExtension())) {
                        if(video.getUrl() != null) {
                            try {
                                copy("LESSON", video.getUrl(), output, lesson.getVideoId(), "");
                                if (!output.exists() || output.length()<2000) {
                                    logger.error("Error downloading from URL, trying with another JSON call");
                                    getVideoFromJson(output, course, lesson, true);
                                }
                            } catch (IOException |UnirestException e) {
                                try {
                                    getVideoFromJson(output, course, lesson);
                                } catch (IOException e1) {
                                    logger.error(e1.getMessage());
                                }
                            }
                        }
                        else{
                            try {
                                getVideoFromJson(output, course, lesson);
                            } catch (IOException e) {
                                logger.error(e.getMessage());
                            }
                        }
                    }
                }
            }
        }
    }

    private void getVideoFromJson(File outputVideo, Course course, Lesson lesson) throws IOException {
        getVideoFromJson(outputVideo, course, lesson, false);
    }

    private void getVideoFromJson(File outputVideo, Course course, Lesson lesson, Boolean overwrite) throws IOException {
        String urlCourse = String.format("https://www.lynda.com/ajax/course/%s/%s/play",course.getId(), lesson.getVideoId());
        GetRequest getRequest = Unirest.get(urlCourse);
        try {
            String body = String.format("{ \"videos\" : %s}", getRequest.asString().getBody());
            JSONObject object = new JSONObject(body);
            List<Object> videos = object.getJSONArray("videos").toList();
            if(videos.size()>0) {
                for (Object typeO : videos) {
                    HashMap type = (HashMap) typeO;
                    if (type.get("name").equals("EDGECAST")) {
                        String videoUrl = (String) ((HashMap) type.get("urls")).get("720");
                        copy("EDGECAST", videoUrl, outputVideo, lesson.getVideoId(), urlCourse, overwrite);
                    }
                }
            }
            else{
                logger.error("No videos found at " + urlCourse);
            }
        } catch (UnirestException e) {
            logger.error(e.getMessage());
        }
    }
    private void copy(String prefix, String videoUrl, File output, Integer lessonId, String urlJson) throws IOException, UnirestException {
        copy(prefix, videoUrl, output, lessonId, urlJson, false);
    }
    private void copy(String prefix, String videoUrl, File output, Integer lessonId, String urlJson, Boolean overwrite) throws IOException, UnirestException {
        if(output.exists() && !overwrite)
            logger.debug("Already downloaded");
        else {
            logger.info(String.format("\t[%s] [%s] [%s] Trying to get %s", prefix, lessonId, urlJson, videoUrl));
            InputStream inputStream = Unirest.get(videoUrl).asBinary().getBody();
            OutputStream outputStream = new FileOutputStream(output);
            IOUtils.copy(inputStream, outputStream);
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
            logger.debug("\tDownload successful");
        }
    }
}

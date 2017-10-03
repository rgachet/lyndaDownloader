package org.elrudaille.courses.downloader;

import org.apache.log4j.Logger;
import org.elrudaille.courses.LyndaSignIn;
import org.elrudaille.courses.model.LearningPath;
import org.elrudaille.courses.parser.LyndaCourseParser;
import org.elrudaille.courses.util.ConfigUtil;
import org.elrudaille.courses.util.GsonUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rgachet on 27/02/2017.
 */
public class LearningPathDownloader {

    private static Logger logger = Logger.getLogger(LearningPathDownloader.class.getName());

    private static final Pattern TITLE_PATTERN = Pattern.compile("<h2>(.*)<cite.*");

    public static void main(String[] args) throws IOException {
        LyndaSignIn signIn = new LyndaSignIn();
        try {
            signIn.signIn();
            String dossierGeneration = ConfigUtil.getBaseDirectory();
            String url = args[1];
            String name = args[0];
            Document d = Jsoup.connect(url).get();
            LearningPath path = new LearningPath();
            String pathTitle =d.getElementsByTag("h1").first().toString().replace("<h1>", "").replace("</h1>", "");
            path.setTitre(pathTitle);
            path.setUrl(url);
            for(Element course : d.getElementsByClass("path-item")){
                Matcher titleMatcher = TITLE_PATTERN.matcher(course.getElementsByTag("h2").first().toString());
                titleMatcher.matches();
                String title = titleMatcher.group(1);
                Integer courseId = Integer.valueOf(course.getElementsByAttribute("data-course-id").first().attr("data-course-id"));
                logger.info(String.format("Course '%s' - id: %s", title, courseId));
                path.addCourse(courseId);
            }
            GsonUtil.save(path, dossierGeneration, name);
            for(Integer courseId : path.getCourses()){
                LyndaDownloader.downloadCourse(dossierGeneration, courseId);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        finally{
            signIn.signOut();
        }

    }
}

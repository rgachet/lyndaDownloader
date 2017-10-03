package org.elrudaille.courses;

import org.apache.commons.io.IOUtils;
import org.elrudaille.courses.downloader.LyndaDownloader;
import org.elrudaille.courses.model.Course;
import org.elrudaille.courses.parser.LyndaCourseParser;
import org.elrudaille.courses.util.ConfigUtil;
import org.elrudaille.courses.util.GsonUtil;
import org.elrudaille.courses.util.TemplateUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * Created by rgachet on 27/02/2017.
 */
@RestController
public class CourseController {

    @RequestMapping("/")
    @ResponseBody
    public String index() throws IOException {
        String baseOutputDirectory = ConfigUtil.getBaseDirectory();
        File[] courseDirectories = new File(baseOutputDirectory).listFiles(File::isDirectory);
        List<Course> courses = new ArrayList<>();
        for(File courseDirectory : courseDirectories){
            String jsonFile = String.format("%s/savedState.json", courseDirectory);
            Course course = GsonUtil.load(new File(jsonFile));
            if(course != null)
                courses.add(course);
        }
        Collections.sort(courses, (Course c1, Course c2) -> c1.getTitre().compareTo(c2.getTitre()));
        return TemplateUtil.generateWithTemplate("index.html", courses);
    }

    @RequestMapping(value="/video", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getVideo(@RequestParam(value = "courseId") String courseId, @RequestParam(value = "videoId") String videoId,HttpServletResponse response) throws IOException {
        String baseOutputDirectory = ConfigUtil.getBaseDirectory();
        String videoPath = String.format("%s/%s/%s.mp4", baseOutputDirectory, courseId, videoId);
        InputStream inputStream = new FileInputStream(videoPath);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @RequestMapping("/course")
    @ResponseBody
    public String getCourse(@RequestParam(value = "courseId") Integer courseId) throws IOException {
        String baseOutputDirectory = ConfigUtil.getBaseDirectory();
        String jsonFile = String.format("%s/%s/savedState.json", baseOutputDirectory, courseId);
        Course course = GsonUtil.load(new File(jsonFile));
        if(course != null){
            return TemplateUtil.generateWithTemplate("course.html", course);
        }
        else
            return "Error reading json description for course " + courseId;
    }
}

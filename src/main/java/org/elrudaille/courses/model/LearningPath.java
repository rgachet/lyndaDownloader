package org.elrudaille.courses.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rgachet on 27/02/2017.
 */
public class LearningPath {

    private String titre;
    private List<Integer> courses = new ArrayList<>();
    private String url;

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public List<Integer> getCourses() {
        return courses;
    }

    public void addCourse(Integer cours) {
        this.courses.add(cours);
    }


    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

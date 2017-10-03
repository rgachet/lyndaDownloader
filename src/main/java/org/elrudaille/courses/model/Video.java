package org.elrudaille.courses.model;

/**
 * Created by rgachet on 24/02/2017.
 */
public class Video {

    private String extension;


    private String url;
    private Integer resolution;

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public String getExtension() {
        return extension;
    }
    public String getUrl() {
        return url;
    }

    public Integer getResolution() {
        return resolution;
    }


    public void setResolution(Integer resolution) {
        this.resolution = resolution;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

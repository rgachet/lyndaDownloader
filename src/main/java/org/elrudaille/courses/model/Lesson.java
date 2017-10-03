package org.elrudaille.courses.model;

import java.util.ArrayList;
import java.util.List;

public class Lesson implements Comparable<Lesson> {

	private String title;
	private String url;
	private Integer duration;
	private Integer videoId;
	private Integer numero;
	private String originalWebpage;
	private String reformattedWebpage;
	private boolean videoDownloaded;
	private boolean webPageDownloaded;
	private boolean webPageReformatted;

    private List<Video> videos;

	public Lesson() {
		videoDownloaded = false;
		webPageDownloaded = false;
		webPageReformatted = false;
		videos = new ArrayList<Video>();
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setURL(String attr) {
		this.url = attr;
	}

	public String getURL() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public boolean isVideoDownloaded() {
		return videoDownloaded;
	}

	public void setVideoDownloaded(boolean videoDownloaded) {
		this.videoDownloaded = videoDownloaded;
	}

	public boolean isWebPageDownloaded() {
		return webPageDownloaded;
	}

	public void setWebPageDownloaded(boolean webPageDownloaded) {
		this.webPageDownloaded = webPageDownloaded;
	}

	public String getReformattedWebpage() {
		return reformattedWebpage;
	}

	public void setReformattedWebpage(String reformattedWebpage) {
		this.reformattedWebpage = reformattedWebpage;
	}

	public String getOriginalWebpage() {
		return originalWebpage;
	}

	public void setOriginalWebpage(String originalWebpage) {
		this.originalWebpage = originalWebpage;
	}

	public boolean isWebPageReformatted() {
		return webPageReformatted;
	}

	public void setWebPageReformatted(boolean webPageReformatted) {
		this.webPageReformatted = webPageReformatted;
	}

	public int compareTo(Lesson r) {
		return this.getNumero().compareTo(r.getNumero());
	}

    public boolean addVideo(Video video) {
        return videos.add(video);
    }

    public List<Video> getVideos() {
        return videos;
    }
}

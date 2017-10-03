package org.elrudaille.courses.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class Course {

	public static final String LYNDA = "lynda";
	public static final String UDEMY = "udemy";
	private List<Chapter> chapitres;
	private Collection<String> keywords;
	private String titre;
	private String outputDir;



	private Integer id;
	private String source;
	private boolean isDownloaded;
	private String url;

	public Course(String source) {
		chapitres = new ArrayList<Chapter>();
		keywords = new HashSet<>();
		this.source = source;
	}

	public boolean addChapitre(Chapter chapitre) {
		return chapitres.add(chapitre);
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTitre() {
		return titre;
	}

	public List<Chapter> getChapitres() {
		return chapitres;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDownloaded(boolean downloaded) {
		this.isDownloaded = downloaded;
	}

	public boolean isDownloaded() {
		return isDownloaded;
	}

	public void addKeyWord(String keyword) {
		keywords.add(keyword);
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Course course = (Course) o;
		return getId().equals(course.getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}

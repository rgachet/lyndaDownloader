package org.elrudaille.courses.model;

import java.util.ArrayList;
import java.util.List;

public class Chapter implements Comparable<Chapter> {

	private List<Lesson> lessons;

	private String titre;

	private Integer numero;

	private String outputDir;

	private Integer id;

	private boolean isDownloaded;

	public Chapter() {
		lessons = new ArrayList<Lesson>();
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String setTitre) {
		this.titre = setTitre;
	}

	public boolean addLesson(Lesson lesson) {
		return lessons.add(lesson);
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
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

	@Override
	public int compareTo(Chapter r) {
		return this.getNumero().compareTo(r.getNumero());
	}

}

package domain;

import java.util.List;

public class Actor {

	private int id;
	private String name;
	private String Surname;
	private List<String> movieLinks;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return Surname;
	}

	public void setSurname(String surname) {
		Surname = surname;
	}

	public List<String> getMovieLinks() {
		return movieLinks;
	}

	public void setMovieLinks(List<String> movieLinks) {
		this.movieLinks = movieLinks;
	}

}

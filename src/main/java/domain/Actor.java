package domain;

import java.util.List;

import javax.ws.rs.core.Link;

public class Actor {

	private int id;
	private String name;
	private String surname;
	private List<Link> movieLinks;

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
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Link> getMovieLinks() {
		return movieLinks;
	}

	public void setMovieLinks(List<Link> movieLinks) {
		this.movieLinks = movieLinks;
	}

}

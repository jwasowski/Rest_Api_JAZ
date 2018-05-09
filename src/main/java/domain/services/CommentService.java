package domain.services;

import java.util.List;

import domain.Comment;
import domain.Movie;

public class CommentService {
	private Movie movie;
	private static int currentId = -1;

	public List<Comment> getAll() {
		return movie.getComments();
	}

	public Comment get(int id) {
		for (Comment c : movie.getComments()) {
			if (c.getId() == id)
				return c;
		}
		return null;
	}

	public void add(Comment c) {
		c.setId(++currentId);
		movie.getComments().add(c);
	}

	public void update(Comment comment) {
		for (Comment c : movie.getComments()) {
			if (c.getId() == comment.getId()) {
				c.setName(comment.getName());
				c.setDate(comment.getDate());
				c.setContents(comment.getContents());
			}
		}
	}

	public void delete(Comment c) {
		movie.getComments().remove(c);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}

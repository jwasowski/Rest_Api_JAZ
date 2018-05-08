package domain.services;

import java.util.ArrayList;
import java.util.List;

import domain.Comment;

public class CommentService {
	private static List<Comment> db = new ArrayList<Comment>();
	private static int currentId = 1;

	public List<Comment> getAll(){
		return db;
	}
	
	public Comment get(int id) {
		for (Comment c : db) {
			if (c.getId() == id)
				return c;
		}
		return null;
	}

	public void add(Comment c) {
		c.setId(++currentId);
		db.add(c);
	}

	public void update(Comment comment) {
		for (Comment c : db) {
			if (c.getId() == comment.getId()) {
				c.setName(comment.getName());
				c.setDate(comment.getDate());
				c.setContents(comment.getContents());
			}
		}
	}

	public void delete(Comment c) {
		db.remove(c);
	}
}

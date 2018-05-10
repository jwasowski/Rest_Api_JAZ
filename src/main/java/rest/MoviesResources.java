package rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Comment;
import domain.Movie;
import domain.Rating;
import domain.services.CommentService;
import domain.services.MovieService;
import domain.services.RateService;

@Path("/movies")
public class MoviesResources {
	private MovieService dbService = new MovieService();
	private CommentService cService = new CommentService();
	private RateService rSerivce = new RateService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Movie> getAll() {
		return dbService.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Movie movie) {
		dbService.add(movie);
		return Response.ok(movie.getId()).build();
	}

	@GET
	@Path("/{movieId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("movieId") int movieId) {
		Movie result = dbService.get(movieId);
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}

	@PUT
	@Path("/{movieId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("movieId") int movieId, Movie m) {
		Movie result = dbService.get(movieId);
		if (result == null)
			return Response.status(404).build();
		m.setId(movieId);
		dbService.update(m);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{movieId}")
	public Response deleteMovie(@PathParam("movieId") int movieId) {
		Movie result = dbService.get(movieId);
		if (result == null)
			return Response.status(404).build();
		dbService.delete(result);
		return Response.ok().build();
	}

	@GET
	@Path("/{movieId}/comments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comment> getComments(@PathParam("movieId") int movieId) {
		Movie result = dbService.get(movieId);
		if (result == null)
			return null;
		if (result.getComments() == null)
			result.setComments(new ArrayList<Comment>());
		return result.getComments();
	}

	@POST
	@Path("/{movieId}/comments")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addComment(@PathParam("movieId") int movieId, Comment comment) {
		Movie result = dbService.get(movieId);
		cService.setMovie(result);
		if (result == null)
			return Response.status(404).build();
		if (result.getComments() == null)
			result.setComments(new ArrayList<Comment>());
		cService.add(comment);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{movieId}/comments/{commentId}")
	public Response deleteComment(@PathParam("movieId") int movieId, @PathParam("commentId") int commentId) {
		Movie result = dbService.get(movieId);
		cService.setMovie(result);
		if (result == null || result.getComments().get(commentId) == null)
			return Response.status(404).build();
		cService.delete(cService.get(commentId));
		return Response.ok().build();
	}
	
	@POST
	@Path("/{movieId}/rate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response rateMovie(@PathParam("movieId") int movieId,Rating rating){
		Movie result = dbService.get(movieId);
		rSerivce.setMovie(result);
		if (result == null)
			return Response.status(404).build();
		if(result.getRatingList() == null)
			result.setRatingList(new ArrayList<Rating>());
		rSerivce.add(rating);
		rSerivce.setAverageRating();
		dbService.update(result);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{movieId}/actors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getActors(@PathParam("movieId") int movieId) {
		Movie result = dbService.get(movieId);
		if (result == null)
			return null;
		if (result.getActorLinks() == null)
			result.setActorLinks(new ArrayList<String>());
		return result.getActorLinks();
	}
}

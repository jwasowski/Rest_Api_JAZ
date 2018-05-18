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
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import domain.Actor;
import domain.Movie;
import domain.services.ActorService;
import domain.services.MovieService;

@Path("/actors")
public class ActorResources {
	private ActorService dbService = new ActorService();
	private MovieService dbMService = new MovieService();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Actor> getAll() {
		return dbService.getAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Actor actor) {
		dbService.add(actor);
		return Response.ok(actor.getId()).build();
	}

	@GET
	@Path("/{actorId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("actorId") int actorId) {
		Actor result = dbService.get(actorId);
		if (result == null) {
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}

	@PUT
	@Path("/{actorId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("actorId") int actorId, Actor a) {
		Actor result = dbService.get(actorId);
		if (result == null)
			return Response.status(404).build();
		a.setId(actorId);
		dbService.update(a);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{actorId}")
	public Response deleteActor(@PathParam("actorId") int actorId) {
		Actor result = dbService.get(actorId);
		if (result == null)
			return Response.status(404).build();
		dbService.delete(result);
		return Response.ok().build();
	}

	@POST
	@Path("/{actorId}/movies/{movieId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response AddMovie(@PathParam("actorId") int actorId, @PathParam("movieId") int movieId) {
		Actor result = dbService.get(actorId);
		Movie mResult = dbMService.get(movieId);
		String relation = "";
		relation += actorId + "w" + movieId;
		Link linkMovie = Link.fromUri("http://localhost:8080/restapp/rest/movies/{movieId}").rel(relation)
				.build(movieId);
		Link linkActor = Link.fromUri("http://localhost:8080/restapp/rest/actors/{actorId}").rel(relation)
				.build(actorId);
		if (result.getMovieLinks() == null)
			result.setMovieLinks(new ArrayList<Link>());
		if (mResult.getActorLinks() == null)
			mResult.setActorLinks(new ArrayList<Link>());
		result.getMovieLinks().add(linkMovie);
		mResult.getActorLinks().add(linkActor);
		return Response.ok(result.getMovieLinks().toString() + "" + mResult.getActorLinks().toString()).build();
	}

}

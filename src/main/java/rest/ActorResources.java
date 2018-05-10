package rest;

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

import domain.Actor;
import domain.services.ActorService;

@Path("/actors")
public class ActorResources {
private ActorService dbService = new ActorService();

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



}

package net.dotmarks.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.dotmarks.api.om.Dotmark;
import net.dotmarks.api.services.DotmarksService;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Session;

@Path( "/dotmarks" )
@Produces(MediaType.APPLICATION_JSON)
public class DotmarksResource {
	
	//private final Cluster cassandra;
	private final Session session;
	

	public DotmarksResource(Session session) {
		super();
		this.session = session;
	}

	@GET
	@Timed
	public List<Dotmark> listAll() {
		DotmarksService service = new DotmarksService(session);
		return service.findAll();
	    
	}

	@GET
	@Timed
	@Path("/{userId}")
	public List<Dotmark> listAllByUser(@PathParam("userId") String id) {
		DotmarksService service = new DotmarksService(session);
		return service.findAllByUser(id);
	    
	}
	
	
	@POST
    @Timed 
    public Response create(Dotmark dotmark){
		DotmarksService service = new DotmarksService(session);
		Dotmark dtm = service.create(dotmark);
		return Response.status(Response.Status.CREATED).entity(dtm).build();		
	}
}

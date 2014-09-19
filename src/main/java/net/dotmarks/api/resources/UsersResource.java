package net.dotmarks.api.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.dotmarks.api.om.User;
import net.dotmarks.api.services.UsersService;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Cluster;

@Path( "/users" )
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
	
	private final Cluster cassandra;

	public UsersResource(Cluster cassandra) {
		super();
		this.cassandra = cassandra;
	}

	@GET
	@Timed
	public List<User> listAll() {
		UsersService service = new UsersService(cassandra);
		return service.findAll();
	    
	}
}

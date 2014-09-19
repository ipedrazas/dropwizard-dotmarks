package net.dotmarks.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
	@GET
	@Timed
	public String pong() {
		return "{\"answer\": \"pong\"}";
	}
}
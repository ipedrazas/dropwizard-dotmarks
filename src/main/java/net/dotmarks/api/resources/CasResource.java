package net.dotmarks.api.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.dotmarks.api.om.C1;

import com.codahale.metrics.annotation.Timed;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.google.common.base.Optional;


@Path("/c1")
@Produces(MediaType.APPLICATION_JSON)
public class CasResource {

	private Cluster cassandra;
	
	public CasResource(Cluster cassandra) {
        this.cassandra = cassandra;
        
    }


    @GET
    @Timed    
    public C1 sayCassandra(@QueryParam("name") Optional<String> name) {        
        Session session = cassandra.connect();
        Statement statement = QueryBuilder.select()
                .all()
                .from("dotmarks", "test_table");
        ResultSet results = session.execute(statement);
        List<String> entries = new ArrayList<String>();        
        for ( Row row : results ) {
        	entries.add(row.getString("test_value"));            
        }
        return new C1("My little message from Cassandra", entries);
    }
}

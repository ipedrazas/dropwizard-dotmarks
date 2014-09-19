package net.dotmarks.api.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class DotmarkHealthCheck extends HealthCheck {

private static final Logger LOG = LoggerFactory.getLogger(DotmarkHealthCheck.class);
	
	private  Cluster cassandra;

	public DotmarkHealthCheck(Cluster cassandra) {
		super();
		this.cassandra = cassandra;
	}

	
	@Override
	protected Result check() throws Exception {

		String query = "SELECT columnfamily_name FROM System.schema_columnfamilies WHERE keyspace_name='dotmarks'";
		Session session = cassandra.connect();
		ResultSet rs = session.execute(query);		
		for(Row r : rs.all()){
			String column = r.getString("columnfamily_name");
			if("dotmarks".equals(column)){
				return Result.healthy();
			}			
		}
		String error = "Dotmarks table could not be found";
		LOG.error(error);
		return Result.unhealthy(error);
	}

}

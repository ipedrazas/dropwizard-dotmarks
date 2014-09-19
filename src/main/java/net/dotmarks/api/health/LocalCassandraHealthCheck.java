package net.dotmarks.api.health;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.health.HealthCheck;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;

public class LocalCassandraHealthCheck extends HealthCheck {
	
	private static final Logger LOG = LoggerFactory.getLogger(LocalCassandraHealthCheck.class);
	
	private  Cluster cassandra;

	public LocalCassandraHealthCheck(Cluster cassandra) {
		super();
		this.cassandra = cassandra;
	}

	@Override
	protected Result check() throws Exception {
		Metadata metadata = cassandra.getMetadata();
		if(metadata.getClusterName() != null){
			String msg = metadata.getClusterName() + " - ";			
			Set<Host> hosts = metadata.getAllHosts();
			
			for (Host host : hosts){
			    msg += host.getSocketAddress() + " " + host.getDatacenter() + " " + host.getAddress() + "\t";
			}
			return Result.healthy(msg);
			
		}
		LOG.error("Unable to connect to Cassandra cluster");
		return Result.unhealthy("Cassandra looks unreachable");
	}

}

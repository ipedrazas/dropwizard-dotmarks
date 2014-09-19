package net.dotmarks.api.services;

import static org.junit.Assert.assertTrue;

import java.util.List;

import net.dotmarks.api.om.User;

import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Cluster;

public class UserServiceTest {

	Cluster cluster;
	UsersService s;
	
	@Before
	public void setUp() throws Exception {
		String cassandraNode = System.getenv("C1_PORT_22_TCP_ADDR");
		if(cassandraNode == null){
			cassandraNode = System.getProperty("C1_PORT_22_TCP_ADDR");
		}
		
		System.out.println("cassandraNode: " + cassandraNode);
	    
		cluster = Cluster.builder()
	            .addContactPoint(cassandraNode).build();
		s = new UsersService(cluster);
	}
	
	@Test
	public void testFindAll() {
		List<User> users = s.findAll();
		assertTrue(users.size() > 0);
	}
}

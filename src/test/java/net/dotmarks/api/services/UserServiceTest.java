package net.dotmarks.api.services;

import static org.junit.Assert.assertTrue;

import java.io.FileReader;
import java.util.List;
import java.util.Map;

import net.dotmarks.api.om.User;

import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Cluster;
import com.esotericsoftware.yamlbeans.YamlReader;

public class UserServiceTest {

	Cluster cluster;
	UsersService s;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setUp() throws Exception {
		String cassandraNode = System.getProperty("C1");
		
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

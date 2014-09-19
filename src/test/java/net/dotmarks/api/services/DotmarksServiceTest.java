package net.dotmarks.api.services;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.UUID;

import net.dotmarks.api.om.Dotmark;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.datastax.driver.core.Cluster;

public class DotmarksServiceTest {

	Cluster cluster;
	DotmarksService s;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Before
	public void setUp() throws Exception {
		String cassandraNode = System.getenv("C1");
		if(cassandraNode == null){
			cassandraNode = System.getProperty("C1");
		}
	    
		cluster = Cluster.builder()
	            .addContactPoint(cassandraNode).build();
		s = new DotmarksService(cluster);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAll() {
		List<Dotmark> dtms = s.findAll();
		assertTrue(dtms.size() > 0);
	}

	@Test
	public void testFindAllByUser() {		
		List<Dotmark> dtms = s.findAllByUser(UUID.randomUUID().toString());
		assertTrue(dtms.size() == 0);
	}
	
	@Test
	public void testCreate() {
		
	}

}

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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
	public void setUp() throws Exception {
		YamlReader reader = new YamlReader(new FileReader("config.yml"));
		
		Map map = (Map) reader.read();
		Map cs = (Map) map.get("cassandra");
		List<String> node = (List<String>) cs.get("contactPoints");
		String cassandraNode = node.get(0);
		
		if(cassandraNode == null){
			cassandraNode = System.getenv("C1_PORT_22_TCP_ADDR");
			if(cassandraNode == null){
				cassandraNode = System.getProperty("C1_PORT_22_TCP_ADDR");
			}
		}
			    
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

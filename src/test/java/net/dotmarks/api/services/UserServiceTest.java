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
		YamlReader reader = new YamlReader(new FileReader("config.yml"));

		Map map = (Map) reader.read();
	    Map cs = (Map) map.get("cassandra");
	    List<String> node = (List<String>) cs.get("contactPoints");
	    
		cluster = Cluster.builder()
	            .addContactPoint(node.get(0)).build();
		s = new UsersService(cluster);
	}
	
	@Test
	public void testFindAll() {
		List<User> users = s.findAll();
		assertTrue(users.size() > 0);
	}
}

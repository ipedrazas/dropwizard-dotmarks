package net.dotmarks.api.services;

import java.util.ArrayList;
import java.util.List;

import net.dotmarks.api.om.User;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

public class UsersService {

	private final Cluster cassandra;

	public UsersService(Cluster cassandra) {
		super();
		this.cassandra = cassandra;
	}
	
	public List<User> findAll(){
		List<User> users = new ArrayList<User>();
		Session s = this.cassandra.connect();
		Statement statement = QueryBuilder.select()
		        .all()
		        .from("dotmarks", "users");
		ResultSet results = s.execute(statement);
		for ( Row row : results ) {
		    users.add(new User(row));
		}
		return users;
		
	}
}

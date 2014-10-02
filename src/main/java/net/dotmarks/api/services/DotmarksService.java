package net.dotmarks.api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.dotmarks.api.om.Dotmark;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Clause;
import com.datastax.driver.core.querybuilder.QueryBuilder;



public class DotmarksService {
	
	private final Session session;
	
	public List<Dotmark> findAll(){
		List<Dotmark> dots = new ArrayList<Dotmark>();
		
		Statement statement = QueryBuilder.select()
		        .all()
		        .from("dotmarks", "dotmarks");
		ResultSet results = session.execute(statement);
		for ( Row row : results ) {
		    dots.add(new Dotmark(row));
		}
		return dots;
	}
	 
	public DotmarksService(Session session) {
		super();
		this.session = session;
	}

	public Dotmark create(Dotmark dtm) {
		
		long timestamp = System.currentTimeMillis();
		dtm.setCreated(new Date(timestamp));
		dtm.setUpdated(new Date(timestamp));
		dtm.setStar(false);		
		Statement statement = QueryBuilder.insertInto("dotmarks", "dotmarks")
		        .value("url", dtm.getUrl())
		        .value("title", dtm.getTitle())
		        .value("tags", dtm.getTags())
		        .value("star", false)
		        .value("created", timestamp)
		        .value("updated", timestamp)
		        .value("user", dtm.getUser())
		        .value("source", dtm.getSource());
		session.execute(statement);		
		return dtm;
	}

	public List<Dotmark> findAllByUser(String id) {
		List<Dotmark> dots = new ArrayList<Dotmark>();
		Clause id_equals_user = QueryBuilder.eq("user", UUID.fromString(id));
		Statement statement = QueryBuilder.select()
		        .all()
		        .from("dotmarks", "dotmarksByUser").where(id_equals_user);
		ResultSet results = session.execute(statement);
		for ( Row row : results ) {
		    dots.add(new Dotmark(row));
		}
		return dots;
	}
	
	

}


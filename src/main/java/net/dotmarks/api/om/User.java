package net.dotmarks.api.om;

import java.util.Date;
import java.util.UUID;

import com.datastax.driver.core.Row;

public class User {
	
	private String username;
	private UUID id;
	private String password;
	private boolean active;
	private Date created;
	private Date updated;
	private String email;
	
	
	public User(Row row) {
		this.created = row.getDate("created");
		this.updated = row.getDate("updated");		
		this.id = row.getUUID("user_id");
		this.active = row.getBool("active");
		this.email = row.getString("email");
		this.username = row.getString("username");
		this.password = row.getString("password");
	}

	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

}

package net.dotmarks.api.om;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.Row;

public class Dotmark {

	private String url;
	private String title;
	private Date created;
	private Date updated;
	private UUID user;
	private List<String> tags;
	private boolean star;
	private String source;
	
	
	public Dotmark() {
		super();
	}

	public Dotmark(String url, String title, Date created, Date updated,
			String user, List<String> tags, boolean star, String source) {
		super();
		this.url = url;
		this.title = title;
		this.created = created;
		this.updated = updated;
		this.user = UUID.fromString(user);
		this.tags = tags;
		this.star = star;
		this.source = source;
	}

	public Dotmark(String link, String title) {
		super();
		this.url = link;
		this.title = title;
		Date now = new Date();
		this.created = now;
		this.updated = now;
	}
	
	public Dotmark(Row row) {
		this.created = row.getDate("created");
		this.updated = row.getDate("updated");
		this.url = row.getString("url");
		this.user = row.getUUID("user");
		this.star = row.getBool("star");
		this.title = row.getString("title");
		this.user = row.getUUID("user");
		this.tags = row.getList("tags", String.class);
		this.source = row.getString("source");
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String link) {
		this.url = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

	public UUID getUser() {
		return user;
	}

	public void setUser(UUID user) {
		this.user = user;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}
	
}

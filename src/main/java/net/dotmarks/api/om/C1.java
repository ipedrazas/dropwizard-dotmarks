package net.dotmarks.api.om;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class C1 {

	private String msg;
	private List<String> entries;

	public C1() {
		super();
	}

	public C1(String msg, List<String> entries) {
		super();
		this.msg = msg;
		this.entries = entries;
	}

	@JsonProperty
	public String getMsg() {
		return msg;
	}
	
	public List<String> getEntries(){
		return entries;
	} 

	
}

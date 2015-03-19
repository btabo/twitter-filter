package com.bta.twitter.component.bo;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class TweetBO {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private Long id;

	@Persistent
	private String user;
	
	@Persistent
	private String text;
	
	@Persistent
	private Date date;
	
	@Persistent
	private List<String> hashtags;
	
	@Persistent
	private String rawJson;
	
	
	public TweetBO(Long id, String user, String text, Date date, List<String> hashtags, String rawJson){
		this.id = id;
		this.user = user;
		this.text = text;		
		this.date = date;
		this.hashtags = hashtags;
		this.rawJson = rawJson;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRawJson() {
		return rawJson;
	}

	public void setRawJson(String rawJson) {
		this.rawJson = rawJson;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}
	
}

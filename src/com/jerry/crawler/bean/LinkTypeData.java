package com.jerry.crawler.bean;

public class LinkTypeData {
	private int id;
	/**
	 * link
	 */
	private String linkHref;
	/**
	 * title of the link
	 */
	private String linkText;
	/**
	 * link summary
	 */
	private String summary;
	/**
	 * content of the link
	 */
	private String content;
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getLinkHref(){
		return linkHref;
	}
	
	public void setLinkHref(String linkHref){
		this.linkHref = linkHref;
	}
	
	public String getLinkText(){
		return linkText;
	}
	
	public void setLinkText(String linkText){
		this.linkText = linkText;
	}
	
	public String getSummary(){
		return summary;
	}
	
	public void setSummary(String summary){
		this.summary = summary;
	}
	
	public String getContent(){
		return content;
	}
	
	public void setContent(String content){
		this.content = content;
	}

}

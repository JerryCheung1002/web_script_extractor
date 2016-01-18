package com.jerry.crawler.rule;

/**
 * define the rules and basic parameters
 * @author jerry
 */
public class Rule {
	/**
	 * urls
	 */
	private String url;
	
	/**
	 * add a request data parameter. Request parameters are sent in the request query string for GETs, and in the 
	 * request body for POSTs. A request may have multiple values of the same name.
	 */
	private String[] params;
	
	/**
	 * values of the data parameters above.
	 */
	private String[] values;
	
	/*
	 * http request header name
	 */
	private String[] headerName;
	
	/*
	 * http request header value
	 */
	private String[] headerValue;
	
	/**
	 * name of the html tag.
	 */
	private String resultTagName;
	
	/**
	 * CLASS/ ID / SELECTION
	 * set the type of resultTagName. Default value is ID
	 */
	private int type = ID;
	
	/**
	 * GET/POST
	 * request type. default GET
	 */
	private int requestMode = GET;
	
	public final static int GET = 0;
	public final static int POST = 1;
	
	public final static int CLASS = 0;
	public final static int ID = 1;
	public final static int SELECTION = 2;
	
	public Rule(){
	}
	
	public Rule(String url, String[] params, String[] values, String[] headerName, 
			String[] headerValue, String resultTagName, int type, int requestMode){
		super();
		this.url = url;
		this.params = params;
		this.values = values;
		this.headerName = headerName;
		this.headerValue = headerValue;
		this.resultTagName = resultTagName;
		this.type = type;
		this.requestMode = requestMode;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public String[] getParams(){
		return params;
	}
	
	public void setParams(String[] params){
		this.params = params;
	}
	
	public String[] getValues(){
		return values;
	}
	
	public void setValues(String[] values){
		this.values = values;
	}
	
	public String[] getHeaderName(){
		return headerName;
	}
	
	public void setHeaderName(String[] headerName){
		this.headerName = headerName;
	}
	
	public String[] getHeaderValue(){
		return headerValue;
	}
	
	public void setHeaderValue(String[] headerValue){
		this.headerValue = headerValue;
	}
	
	public String getResultTagName(){
		return resultTagName;
	}
	
	public void setResuleTagName(String resultTagName){
		this.resultTagName = resultTagName;
	}
	
	public int getType(){
		return type;
	}
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getRequestMode(){
		return requestMode;
	}
	
	public void setRequestMode(int requestMode){
		this.requestMode = requestMode;
	}
}

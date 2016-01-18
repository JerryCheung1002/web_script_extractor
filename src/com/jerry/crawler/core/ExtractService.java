package com.jerry.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;  
import org.jsoup.select.Elements;

import com.jerry.crawler.bean.LinkTypeData;
import com.jerry.crawler.rule.Rule;
import com.jerry.crawler.rule.RuleException;
import com.jerry.crawler.util.TextUtil;

/**
 * 
 * @author JerryCheung
 *
 */
public class ExtractService {
	/**
	 *
	 */
	public static List<LinkTypeData> extract(Rule rule){
		
		//first check the rule
		validateRule(rule);
		
		List<LinkTypeData> datas = new ArrayList<LinkTypeData>();
		LinkTypeData data = null;
		try{
			//parse the rule
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String[] headerName = rule.getHeaderName();
			String[] headerValue = rule.getHeaderValue();
			String resultTagName = rule.getResultTagName();
			int type = rule.getType();
			int requestMode = rule.getRequestMode();
			
			Connection conn = Jsoup.connect(url);
			//set query params
			if(params != null){
				for(int i = 0; i < params.length; i++){
					conn.data(params[i], values[i]);          //add a number of request data params
				}
			}
			
			//set http request headers
			if(headerName != null){
				for(int i = 0; i < headerName.length; i++){
					conn.header(headerName[i], headerValue[i]);
				}
			}
			
			Document doc = null;
			switch(requestMode){
			case Rule.GET:
				doc = conn.timeout(100000).get();
				break;
			case Rule.POST:
				doc = conn.timeout(100000).post();
				break;
			}
			//handle the responds
//			System.out.println(doc);
			Elements results = new Elements();
			switch (type){                                    //type corresponds to the tag in html
			case Rule.CLASS:
				results = doc.getElementsByClass(resultTagName);
				break;
			case Rule.ID:
				Element result = doc.getElementById(resultTagName);
				results.add(result);
				break;
			case Rule.SELECTION:
				results = doc.select(resultTagName);
				break;
			default:
				//when the resultTagName is null, get the 'body' tag by default
				if(TextUtil.isEmpty(resultTagName)){
					results = doc.getElementsByTag("body");
				}
			}
			
//			System.out.println("Size of the results is " + results.size());    
			
			for(Element result : results){
				Elements links = result.getElementsByTag("a");
				
				for(Element link : links){
					String linkHref = link.attr("href");
					String linkText = link.text();
					
					data = new LinkTypeData();
					data.setLinkHref(linkHref);
					data.setLinkText(linkText);
					
					datas.add(data);
				}
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		
		return datas;
	}
	
	/**
	 * Extract the URL of the Next Page
	 * This is a badly-implemented method because I am not familiar with JavaScript. After getting familiar with JS,
	 * This method should be deleted. 
	 */
	
	public static Element extractNextPageUrl(Rule rule){
		validateRule(rule);
		Element result = null;
		try{
			String url = rule.getUrl();
			String[] params = rule.getParams();
			String[] values = rule.getValues();
			String[] headerName = rule.getHeaderName();
			String[] headerValue = rule.getHeaderValue();
			String resultTagName = rule.getResultTagName();
//			int type = rule.getType();
//			int requestMode = rule.getRequestMode();			
			Connection conn = Jsoup.connect(url);
			if(params != null){
				for(int i = 0; i < params.length; i++){
					conn.data(params[i], values[i]);          //add a number of request data params
				}
			}
			//set http request headers
			if(headerName != null){
				for(int i = 0; i < headerName.length; i++){
					conn.header(headerName[i], headerValue[i]);
				}
			}
			Document doc = null;
			doc = conn.timeout(100000).get();
			result = doc.getElementById(resultTagName);
		}catch (IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * validate the input params
	 */
	private static void validateRule(Rule rule){
		String url = rule.getUrl();
		if(TextUtil.isEmpty(url)){
			throw new RuleException("Error: url cannot be empty");
		}
		if(!url.startsWith("http://")){
			throw new RuleException("Error: rul badly formatted!");
		}
		if(rule.getParams() != null && rule.getValues() != null){
			if(rule.getParams().length != rule.getValues().length){
				throw new RuleException("Error: numbers of parameter and value should be identical!");
			}
		}
	}
	
}

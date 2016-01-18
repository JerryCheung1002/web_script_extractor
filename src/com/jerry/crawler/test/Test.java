package com.jerry.crawler.test;

import java.util.List;

import org.jsoup.nodes.Element;

import com.jerry.crawler.bean.LinkTypeData;
import com.jerry.crawler.core.ExtractService;
import com.jerry.crawler.rule.Rule;

public class Test {
	public static void main(String[] args){
		
		String initUrl = "http://www.1point3acres.com/bbs/forum-198-1.html";
		String nextPageUrl = null;
//		int totalPage = 0;
//		int curPage = 0;
		int count = 1;
		Rule ruleNextPage = null;
		Element nextPageEle = null;
		
		//following "rule" requests data as a fake browser
		Rule ruleSeed = new Rule(initUrl, new String[]{}, 
				new String[]{}, new String[]{"User-Agent"}, 
				new String[]{"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36"}, 
				"xst", Rule.CLASS, Rule.GET);  
		
//		String initUrl = "http://www.1point3acres.com/bbs/forum-198-1.html";
//		String nextPageUrl = null;
		
		//without the header parameters for the http request, sever treat the client as a mobile device.
		//I will use this to extract the data, because the html code is more concise than the browser version
/*		Rule ruleSeed = new Rule(initUrl, new String[]{}, 
				new String[]{}, new String[]{}, 
				new String[]{}, 
				"threadlist", Rule.CLASS, Rule.GET);  //without params, the website responds with the html code for the mobile device
				*/
		List<LinkTypeData> extracts = ExtractService.extract(ruleSeed);
		printf(extracts, 1);
		
		nextPageUrl = initUrl;
		
		for(;;){
			Rule ruleGetNext = new Rule(nextPageUrl, new String[]{}, 
					new String[]{}, new String[]{"User-Agent"}, 
					new String[]{"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36"}, 
					"autopbn", Rule.ID, Rule.GET);
			nextPageEle = ExtractService.extractNextPageUrl(ruleGetNext);
			
			count++;
			nextPageUrl = "http://www.1point3acres.com/bbs/" + nextPageEle.attr("rel");
			ruleNextPage = new Rule(nextPageUrl, new String[]{}, new String[]{}, new String[]{"User-Agent"},
					new String[]{"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36"}, 
					"xst", Rule.CLASS, Rule.GET);
			extracts = ExtractService.extract(ruleNextPage);
			printf(extracts, count);
//			System.out.println("curpage is: " + nextPageEle.attr("curpage"));
//			System.out.println("totalpage is " + nextPageEle.attr("totalpage"));
			if((Integer.parseInt(nextPageEle.attr("curpage")) + 1) == Integer.parseInt(nextPageEle.attr("totalpage"))) break;
		}
	}
	
	public static void printf(List<LinkTypeData> datas, int pageNum){
		System.out.println("Page Number: " + pageNum);
//		System.out.println("Item number is "+ datas.size());
		for(LinkTypeData data : datas){
			System.out.println(data.getLinkText());
			System.out.println(data.getLinkHref());
			System.out.println("-----------");
		}
		System.out.println("End of the page: " + pageNum);
	}
}

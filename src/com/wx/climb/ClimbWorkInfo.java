package com.wx.climb;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.sae.fetchurl.SaeFetchurl;

public class ClimbWorkInfo {
	private String address = "http://jyzx.swpu.edu.cn/";
	private HashMap<String, String> workInfo;
	public ClimbWorkInfo(){
		workInfo = new HashMap<String, String>();
		get();
	}
	
	
	public HashMap<String, String> getWorkInfo(){
		return workInfo;
	}
	
	
	private void get(){
		String html = "";
		SaeFetchurl fetchurl = null;
		System.out.println("创建连接");
		try{
			fetchurl = //new SaeFetchurl(); 
					new SaeFetchurl("o33kyll02o",
					"001yz44i0j0jixyijzjw32ym1w53xj4j21klxl55");
			fetchurl.setMethod("get");
			System.out.println("抓取网页");
			html = fetchurl.fetch("http://jyzx.swpu.edu.cn/Article/JS/newscode_xy.asp?lm=61&n=60&x=1&y=11&icon=/images/icon/ico_1.gif&lmname=0&more=2&tj=0&hit=0&open=1&t=0&week=0&line=12&new=1&bg=FFFFFF");
			//System.out.println(html);
			getWork(html);
		}
		catch(Exception e){}
	}
	
	
	private void getWork(String html){
		Document doc = Jsoup.parse(html);
		Elements els = doc.getElementsByTag("a");
		for(Element e : els){
			String title = e.attr("title");
			String href = e.attr("href");
			if(!title.equals(""))
				workInfo.put(title, address+href);
		}
		
	}
	
//	public static void main(String[] args) {
//		new ClimbWorkInfo();
//	}
//	
	
}
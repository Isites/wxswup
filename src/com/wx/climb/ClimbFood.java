package com.wx.climb;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.sae.fetchurl.SaeFetchurl;

public class ClimbFood {
	
	private String address = "http://www.dianping.com";
	private HashMap<String, MUrl> foodInfo;
	public ClimbFood(){
		foodInfo = new HashMap<String, MUrl>();
		get();
	}
	
	
	public HashMap<String, MUrl> getFoodInfo(){
		return foodInfo;
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
			html = fetchurl.fetch("http://www.dianping.com/mylist/1364546");
			//System.out.println(html);
			getFood(html);
		}
		catch(Exception e){}
	}
	
	
	private void getFood(String html){
		Document doc = Jsoup.parse(html);
		Elements els = doc.getElementsByClass("odd");
		for(Element e : els){
			MUrl mUrl = new MUrl();
			String title = null;
			Elements els0 = e.getElementsByTag("a");
			for(Element e0 : els0){
				//System.out.println(e0);
				//只取第一个连接的值就好了
				mUrl.setUrl(address+e0.attr("href"));
				break;
			}
			Elements els1 = e.getElementsByTag("img");
			for(Element e1 : els1){
				//这个网页拥有可怕的编码方式
				//System.out.println(e1);
				String imgurl = e1.attr("src");
				if(imgurl.equals(""))
					imgurl = e1.attr("data-src");
				mUrl.setImgurl(imgurl);
				title = e1.attr("title");
			}
			foodInfo.put(title, mUrl);
//			System.out.println(title);
//			System.out.println(mUrl.getUrl());
//			System.out.println(mUrl.getImgurl());
		}
		
		
	}
	
	
	public static void main(String[] args) {
		new ClimbFood();
	}
	
	
}

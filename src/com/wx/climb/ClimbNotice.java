package com.wx.climb;

import java.util.HashMap;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.sae.fetchurl.SaeFetchurl;

public class ClimbNotice {

	// sae不支持httpurlconnection来抓取网页（操）

	private String address = "http://jwc.swpu.edu.cn/";

	private HashMap<String, String> mnotice;

	public ClimbNotice() {
		mnotice = new HashMap<String, String>();
		get();
	}

	public HashMap<String, String> getNotice() {
		return this.mnotice;
	}

	private void get() {
		String html = "";
		SaeFetchurl fetchurl = null;
		System.out.println("创建连接");
		try {
			fetchurl = new SaeFetchurl(); 
					//new SaeFetchurl("o33kyll02o",
					//"001yz44i0j0jixyijzjw32ym1w53xj4j21klxl55");
			fetchurl.setMethod("get");
			System.out.println("抓取网页");
			html = fetchurl.fetch(address);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("无法获取网页"+e);
		}
		finally{
			System.out.println(fetchurl.getErrno()+":"+fetchurl.getErrmsg());
		}

		getnotice(html);

	}

	private void getnotice(String html) {
		Document doc = Jsoup.parse(html);
		Elements notice = doc.getElementsByClass("tab_2col");

		Iterator<Element> it = notice.iterator();

		System.out.println("爬去公告数量：" + notice.size());

		while (it.hasNext()) {
			Element content = it.next();
			Elements links = content.getElementsByTag("a");

			for (Element link : links) {
				String linkHref = link.attr("href");
				String linkText = new String(link.text());
				mnotice.put(linkText, address + linkHref);
			}
		}
	}

	public static void main(String[] args) {
		new ClimbNotice();
	}

}

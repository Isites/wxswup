package com.wx.msgimpl;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


public class ImageArticleMessage extends WXMessage {
	//图文消息的数量必须和articles的size一样
	private String ArticleCount;
	private List<Item> Articles;
	public ImageArticleMessage(){
		Articles = new ArrayList<Item>();
	}
	
	
	@Override
	public String toString(){
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("xml");
		//doc.setRootElement(root);
		
		Element touser = root.addElement("ToUserName");
		touser.setText(getToUserName());
		
		Element fromuser = root.addElement("FromUserName");
		fromuser.setText(getFromUserName());
		
		Element msgtype = root.addElement("MsgType");
		msgtype.setText(getMsgType());
		Element time = root.addElement("CreateTime");
		time.setText(getCreateTime());
		Element artclecount = root.addElement("ArticleCount");
		artclecount.setText(ArticleCount);
		
		Element articles = root.addElement("Articles");
		
		//StringBuffer buf = new StringBuffer();
		//注意这里不能大于9因为对第一条加了一个西南石油大学的主页
		for(int i = 0; i < Integer.parseInt(ArticleCount); i++){
			//articles
			createItem(articles, Articles.get(i));
			//buf.append(Articles.get(i).toString());
		}
		
		//articles.setText(buf.toString());
		
		return doc.asXML();
	}
	
	
	private void createItem(Element element,Item aitem){
		Element item = element.addElement("item");
		Element title = item.addElement("Title");
		title.setText(aitem.getTitle());
		Element des = item.addElement("Description");
		des.setText(aitem.getDescription());
		Element purl = item.addElement("PicUrl");
		purl.setText(aitem.getPicUrl());
		Element url = item.addElement("Url");
		url.setText(aitem.getUrl());
		
	}
	
	
	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

//	public List<Item> getArticles() {
//		return Articles;
//	}

	public void addArticles(Item article) {
		Articles.add(article);
	}
}




package com.wx.interce;

import java.util.HashMap;
import java.util.Iterator;

import com.wx.climb.ClimbWorkInfo;
import com.wx.msgimpl.ImageArticleMessage;
import com.wx.msgimpl.Item;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptEventWork implements InterceptEventHandle {

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		System.out.println("开始爬取招聘信息");
		HashMap<String, String> info = new ClimbWorkInfo().getWorkInfo();
		ImageArticleMessage iam = new ImageArticleMessage();
		iam.setCreateTime(recv.getCreateTime());
		iam.setFromUserName(recv.getToUserName());
		iam.setToUserName(recv.getFromUserName());
		iam.setMsgType(Constan.MessageType.IMAGE_ARTICLE_MESSAGE);
		iam.setArticleCount((Math.min(9, info.size())+1)+"");
		
		Iterator<String> it =  info.keySet().iterator();
		//http://pan.baidu.com/s/1mguw5Os
		Item item0 = new Item();
		item0.setTitle("西南石油就业指导中心");
		item0.setUrl("http://jyzx.swpu.edu.cn/");
		item0.setDescription("西南石油大学成立于....");
		item0.setPicUrl("http://wxswup-img.stor.sinaapp.com/res/jwc.png");
		//imgarticl.getArticles().add(item1);
		iam.addArticles(item0);
		
		while(it.hasNext()){
			String key = it.next();
			String url = info.get(key);
			Item item = new Item();
			item.setTitle(key);
			item.setUrl(url);
			item.setDescription("");
			item.setPicUrl("http://wxswup-img.stor.sinaapp.com/res/tb.png");
			//imgarticl.getArticles().add(item);
			iam.addArticles(item);
		}
		return iam.toString();
	}

	@Override
	public int getMenuItem() {
		// TODO Auto-generated method stub
		return Constan.WorkInfo.WORK_SUB;
	}

}

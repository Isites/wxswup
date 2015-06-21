package com.wx.interce;

import com.wx.msgimpl.ImageArticleMessage;
import com.wx.msgimpl.Item;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptEventBus implements InterceptEventHandle {

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		ImageArticleMessage iam = new ImageArticleMessage();
		iam.setCreateTime(recv.getCreateTime());
		iam.setFromUserName(recv.getToUserName());
		iam.setToUserName(recv.getFromUserName());
		iam.setMsgType(Constan.MessageType.IMAGE_ARTICLE_MESSAGE);
		iam.setArticleCount(1+"");
		
		Item item = new Item();
		item.setTitle("西南石油大学校车时刻表");
		item.setUrl("http://1.wxswup.sinaapp.com/bus.html");
		item.setDescription("");
		item.setPicUrl("http://wxswup-img.stor.sinaapp.com/res/jwc.png");
		iam.addArticles(item);
		
		return iam.toString();
	}

	@Override
	public int getMenuItem() {
		// TODO Auto-generated method stub
		return Constan.BusRoute.BUS_SUB;
	}

}

package com.wx.interce;

import java.util.HashMap;
import java.util.Iterator;

import com.wx.climb.ClimbFood;
import com.wx.climb.MUrl;
import com.wx.msgimpl.ImageArticleMessage;
import com.wx.msgimpl.Item;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptEventFood implements InterceptEventHandle {

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		System.out.println("开始爬取美食信息");
		HashMap<String, MUrl> info = new ClimbFood().getFoodInfo();
		ImageArticleMessage iam = new ImageArticleMessage();
		iam.setCreateTime(recv.getCreateTime());
		iam.setFromUserName(recv.getToUserName());
		iam.setToUserName(recv.getFromUserName());
		iam.setMsgType(Constan.MessageType.IMAGE_ARTICLE_MESSAGE);
		iam.setArticleCount((Math.min(10, info.size()))+"");
		
		Iterator<String> it =  info.keySet().iterator();
		int i = 0;
		while(it.hasNext()){
			String key = it.next();
			MUrl murl = info.get(key);
			Item item = new Item();
			item.setTitle(key);
			item.setUrl(murl.getUrl());
			if(i == 0)
				item.setDescription("地点：成都新都区正因南街西3巷27号\n"
						+ "人均：22￥\n"
						+ "推荐理由：是一家小馆子，虽然环境不是很好，但味道份量都是很不错的，非常实惠。小中大份分别30,40,50,菜品可混至最多三种，是学生常去处，生意很火。");
			else item.setDescription("");
			item.setPicUrl(murl.getImgurl());
//			//imgarticl.getArticles().add(item);
			iam.addArticles(item);
			i++;
		}
		return iam.toString();
	}

	@Override
	public int getMenuItem() {
		// TODO Auto-generated method stub
		return Constan.FoodInfo.FOOD_SUB;
	}

}

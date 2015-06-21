package com.wx.control;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;

import com.wx.climb.ClimbNotice;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.ImageArticleMessage;
import com.wx.msgimpl.Item;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class QueryNotice implements MsgHandle {

	private RecvTextMessage recv;
	private ImageArticleMessage imgarticl;
	private ReplyTextMessage reply;
	private MySQLManager sqlm;
	private int currstatus;
	
	
	public QueryNotice(RecvTextMessage recv){
		this.recv = recv;
		sqlm = MySQLHelper.getManager();
		initCurrStatus();
		schedule();
		
	}
	
	
	private void schedule() {
		// TODO Auto-generated method stub
		switch (currstatus) {
		case Constan.QueryNotice.NOTICE_SUB:
			initImgArticle();
			break;
		case Constan.INITIAL_STATUS:
			initReplyText();
			break;
		}
		
	}


	private void initCurrStatus() {
		// TODO Auto-generated method stub
		Connection rcon = sqlm.getRConnection();
		UserInfoDao uidao = new UserInfoDao(rcon);
		int status = uidao.getStatus(recv.getFromUserName());
		if(status == Constan.INITIAL_STATUS)
			currstatus = Constan.QueryNotice.NOTICE_SUB;
		else if(status == Constan.QueryNotice.NOTICE_SUB)
			currstatus = Constan.INITIAL_STATUS;
		sqlm.close(rcon);
		
	}
	
	private void initReplyText(){
		System.out.println("进入主菜单");
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uid = new UserInfoDao(wcon);
		reply = new ReplyTextMessage();
		reply.setFromUserName(recv.getToUserName());
		reply.setToUserName(recv.getFromUserName());
		reply.setMsgType(Constan.MessageType.TEXT_MESSAGE);
		reply.setContent("请选择【】中的数字，选择您想要的服务。\n"
				+"【1】成绩查询\n"
				+"【2】课表查询\n"
				+"【3】公告查询\n"
				+"【4】自习室查询");
		uid.updateStatus(Constan.INITIAL_STATUS, recv.getFromUserName());
		
	}
	
	private void initImgArticle(){
		System.out.println("公告查询");
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uid = new UserInfoDao(wcon);
		imgarticl = new ImageArticleMessage();
		System.out.println("开始爬取公告");
		ClimbNotice cn = new ClimbNotice();
		HashMap<String, String> notice;
		notice = cn.getNotice();
		
		imgarticl.setFromUserName(recv.getToUserName());
		imgarticl.setToUserName(recv.getFromUserName());
		imgarticl.setCreateTime(recv.getCreateTime());
		imgarticl.setMsgType(Constan.MessageType.IMAGE_ARTICLE_MESSAGE);
		imgarticl.setArticleCount((Math.min(9, notice.size())+1)+"");
		
		
		Iterator<String> it =  notice.keySet().iterator();
		//http://pan.baidu.com/s/1mguw5Os
		Item item1 = new Item();
		item1.setTitle("西南石油大学教务处");
		item1.setUrl("http://jwc.swpu.edu.cn/");
		item1.setDescription("西南石油大学成立于....");
		item1.setPicUrl("http://wxswup-img.stor.sinaapp.com/res/jwc.png");
		//imgarticl.getArticles().add(item1);
		imgarticl.addArticles(item1);
		
		while(it.hasNext()){
			String key = it.next();
			String url = notice.get(key);
			Item item = new Item();
			item.setTitle(key);
			item.setUrl(url);
			item.setDescription("");
			item.setPicUrl("http://wxswup-img.stor.sinaapp.com/res/tb.png");
			//imgarticl.getArticles().add(item);
			imgarticl.addArticles(item);
		}		
		
		System.out.println(imgarticl.toString());
		uid.updateStatus(Constan.QueryNotice.NOTICE_SUB, recv.getFromUserName());
	}

	@Override
	public String getReply() {
		// TODO Auto-generated method stub
		switch (currstatus) {
		case Constan.INITIAL_STATUS:
			return XmlUtil.obj2xml(reply);
		case Constan.QueryNotice.NOTICE_SUB:
			return imgarticl.toString();

		}
		return null;
	}

}

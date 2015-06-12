package com.wx.control;

import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;

import com.wx.msgimpl.EventMsg;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.msgimpl.ScanEventMsg;
import com.wx.msgimpl.WXMessage;
import com.wx.util.XmlUtil;

public class SubscribeEvent implements MsgHandle {
	
	private Document entry;
	
	private String welcome = "您好，欢迎关注SWPU教务系统！我们竭诚为您服务。\n\n"
	+"请选择【】中的数字，选择您想要的服务。\n"
	+"【1】成绩查询\n"
	+"【2】课表查询\n"
	+"【3】公告查询\n"
	+"【4】自习室查询\n"
	+"后续功能持续更新中，敬请期待！";
	
	
	
	public SubscribeEvent(Document entry,HttpServletResponse response){
		this.entry = entry;
	}
	
	private EventMsg getEventMsg(){
		EventMsg evmsg = null;
		try {
			evmsg = (EventMsg) XmlUtil.xml2obj(entry, EventMsg.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return evmsg;
	}
	
	//对于扫码关注的用户
	private ScanEventMsg getScanEvMsg(){
		ScanEventMsg evmsg = null;
		try {
			evmsg = (ScanEventMsg) XmlUtil.xml2obj(entry, ScanEventMsg.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return evmsg;
	}
	
	//判断是普通关注还是扫码关注1,表示普通关注，2扫码关注
	private  int  subscribeType() {
		
		if(entry.selectNodes("xml/Eventkey") != null)
			return 2;
		return 1;
		
	}
	
	
	@Override
	public String getReply(){
		
		ReplyTextMessage reply = new ReplyTextMessage();
		WXMessage evmsg;
		if(subscribeType() == 1)
			evmsg = getEventMsg();
		else evmsg = getScanEvMsg();
		
		
		reply.setFromUserName(evmsg.getToUserName());
		reply.setToUserName(evmsg.getFromUserName());
		reply.setCreateTime(evmsg.getCreateTime());
		reply.setMsgType("text");
		reply.setContent(welcome);
		
		return XmlUtil.obj2xml(reply);
		

	}
	
	

}

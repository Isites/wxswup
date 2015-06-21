package com.wx.control;

import org.dom4j.Document;

import com.wx.interce.EventMsgInterceptor;
import com.wx.interce.InterceptEventBind;
import com.wx.interce.InterceptHandle;
import com.wx.msgimpl.MenuEventMsg;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class EventMessageHandle implements MsgHandle {
	
	
	private Document entry;
	public EventMessageHandle(Document entry) {
		// TODO Auto-generated constructor stub
		this.entry = entry;
	}
	
	

	@Override
	public String getReply() {
		// TODO Auto-generated method stub
		//暂时只有用户的菜单事件
		MenuEventMsg recv = null;
		String replyMsg = null;
		//用户选择的状态
		int sub = -1;
		
		try {
			recv = (MenuEventMsg) XmlUtil.xml2obj(entry, MenuEventMsg.class);
			sub = Integer.parseInt(recv.getEventKey());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
		
		InterceptHandle interceptHandle = new InterceptEventBind("");
		replyMsg = interceptHandle.intercept(recv);
		//用户如果哦没有绑定公众号的话，且用户点击的不是绑定事件
		if(!replyMsg.equals("你已经绑定了公众号。")
				&& Constan.INITIAL_STATUS != sub) return replyMsg;
		replyMsg = null;
		System.out.println("菜单的绑定事件");
		
		//对菜单事件进行处理
		//这里在创建菜单时除了点问题，本应该使用状态1，结果使用了0，所以过来的消息是0才对
		
		EventMsgInterceptor mh = new EventMsgInterceptor(recv);
		mh.setMenuItem(sub);
		replyMsg = mh.getReply();
		//当取得了处理的结果后还要做进一步的处理
		if(replyMsg.equals("你已经绑定了公众号。")){
			replyMsg = null;
			ReplyTextMessage r1 = new ReplyTextMessage();
			r1.setContent("你已经绑定了公众号");
			r1.setCreateTime(recv.getCreateTime());
			r1.setFromUserName(recv.getToUserName());
			r1.setToUserName(recv.getFromUserName());
			r1.setMsgType(Constan.MessageType.TEXT_MESSAGE);
			replyMsg = XmlUtil.obj2xml(r1);
		}
		return replyMsg;
	}	

}

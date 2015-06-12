package com.wx.control;

import org.dom4j.Document;

import com.wx.interce.TextMsgInterceptor;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;


public class TextMessageHandle implements MsgHandle{

	private Document entry;
	
	public TextMessageHandle(Document entry){
		this.entry = entry;
	}
	@Override
	public String getReply(){

			
			RecvTextMessage recv = null;
			String replyMsg = null;
			
			
			try {
				recv = (RecvTextMessage) XmlUtil.xml2obj(entry, RecvTextMessage.class);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			}
			//在这里截取消息并进行相应的处理
			MsgHandle hand =  new TextMsgInterceptor(recv);
			replyMsg = hand.getReply();
			if(replyMsg != null) return replyMsg;

			//当出现无法处理的消息时
			//到这里时，表表示错误的输入需要提示用户
			ReplyTextMessage reply = new ReplyTextMessage();
			reply = new ReplyTextMessage();
			reply.setFromUserName(recv.getToUserName());
			reply.setToUserName(recv.getFromUserName());
			reply.setMsgType(Constan.MessageType.TEXT_MESSAGE);
			reply.setContent("你的输入有误！请输入【】中的值");
			replyMsg = XmlUtil.obj2xml(reply);
			return replyMsg;
		
	}	
}

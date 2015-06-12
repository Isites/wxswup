package com.wx.msgimpl;

public class MessageFactory {
	
	public static RecvTextMessage getRecvTextMessage(){
		return new RecvTextMessage();
	}
	public static ReplyTextMessage getReplyTextMessage(){
		return new ReplyTextMessage();
	}

}

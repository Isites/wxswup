package com.wx.msgimpl;

public abstract class RecvMessg extends WXMessage {
	
	
	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

}

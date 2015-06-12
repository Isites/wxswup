package com.wx.msgimpl;

public class EventMsg extends WXMessage {
	
	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	private String Event;
}

package com.wx.msgimpl;

public class ScanEventMsg extends EventMsg {
	
	private String EventKey;
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	private String Ticket;

}

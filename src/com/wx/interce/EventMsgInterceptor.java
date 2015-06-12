package com.wx.interce;

import java.util.ArrayList;
import java.util.List;

import com.wx.control.MsgHandle;
import com.wx.msgimpl.WXMessage;

public class EventMsgInterceptor implements MsgHandle,InterceptEventHandle {

	private int menuItem = -1;
	private List<InterceptEventHandle> group;
	private WXMessage recv;
	
	public EventMsgInterceptor(WXMessage recv) {
		// TODO Auto-generated constructor stub		
		group = new ArrayList<InterceptEventHandle>();
		this.recv = recv;
	}
	
	
	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		addInterceptor();
		String reply = null;
		for(InterceptEventHandle ieh : group){
			if(menuItem == ieh.getMenuItem())
				reply = ieh.intercept(recv);
		}
		return reply;
	}

	@Override
	public int getMenuItem() {
		// TODO Auto-generated method stub
		return menuItem;
	}

	@Override
	public String getReply() {
		// TODO Auto-generated method stub
		return intercept(recv);
	}
	
	public void setMenuItem(int item){
		menuItem = item;
	}
	
	//向group中增加事件的拦截者
	private void addInterceptor(){
		group.add(new InterceptEventBind("y"));
		group.add(new InterceptEventGrade());
		group.add(new InterceptEventNotice());
		group.add(new InterceptEventRoom());
		group.add(new InterceptEventTable());
		
		group.add(new InterceptEventExamPlan());
		group.add(new InterceptTable_GX());
		
	}
	
	
	
	
	
}

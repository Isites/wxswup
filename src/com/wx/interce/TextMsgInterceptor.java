package com.wx.interce;

import java.util.ArrayList;
import java.util.List;

import com.wx.control.MsgHandle;
import com.wx.msgimpl.WXMessage;

public class TextMsgInterceptor implements MsgHandle,InterceptHandle {
	
	private WXMessage recv;
	private List<InterceptHandle> group;
	public TextMsgInterceptor(WXMessage recv) {
		// TODO Auto-generated constructor stub
		this.recv = recv;
		group = new ArrayList<InterceptHandle>();
	}

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		initContent();
		String reply = null;
		for(InterceptHandle inter : group){
			reply = inter.intercept(recv);
			if(reply != null) return reply;
		}

		return null;
	}

	@Override
	public String getReply() {
		// TODO Auto-generated method stub
		return intercept(recv);
	}
	
	//增加内容到group中
	private void initContent(){
		group.add(new InterceptBind());
		group.add(new InterceptGrade());
		group.add(new InterceptNotice());
		group.add(new InterceptRoom());
		group.add(new InterceptTable());
	}
	

}

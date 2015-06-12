package com.wx.interce;

import java.util.Calendar;

import com.wx.msgimpl.ReplyTextMessage;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class InterceptEventExamPlan implements InterceptEventHandle {

	// 当前星期几=
	private int curr;
	private String reply;
	//private String[] weekDays = { "日", "一", "二", "三", "四", "五", "六" };
	
	//设定周2和周4有考试，其余时候没有
	private String exam[] = {
		"软件工程   明德B305\n"
		+ "19:00-21:00"	,
		"网络编程  明理A109\n"
		+ "19:00-21:00"	
	};
	
	
	

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		curr = cal.get(Calendar.DAY_OF_WEEK)-1 < 0 ?  0 
				: cal.get(Calendar.DAY_OF_WEEK)-1;
		
		if(curr % 6 == 0){
			reply =  "今天是周末，出去活动活动放松一下吧！^_^";
		}
		else {
			if((curr+1)%2 == 0){
				reply += "你今天的考试有：\n";
				reply = exam[curr/2];
			}
			else{
				reply = "你今天没有考试啦，好好休息吧！^_^";
			}
		}
		return changeReply(recv);
	}

	@Override
	public int getMenuItem() {
		// TODO Auto-generated method stub
		return Constan.QueryExamPlan.EXAM_SUB;
	}
	
	private String changeReply(WXMessage msg){
		ReplyTextMessage r = new ReplyTextMessage();
		r.setContent(reply);
		r.setCreateTime(msg.getCreateTime());
		r.setFromUserName(msg.getToUserName());
		r.setToUserName(msg.getFromUserName());
		r.setMsgType(Constan.MessageType.TEXT_MESSAGE);
		return XmlUtil.obj2xml(r);
	}

}

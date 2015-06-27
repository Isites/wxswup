package com.wx.control;

import java.sql.Connection;

import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.StudentDao;
import com.wx.dao.TimeTableDao;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class QueryTimeTable  implements MsgHandle{
	
	private RecvTextMessage recv;
	private MySQLManager sqlm;
	private int currstatus;
	private ReplyTextMessage reply;
	private String result;
	
	
	public QueryTimeTable(RecvTextMessage recv) {
		// TODO Auto-generated constructor stub
		this.recv = recv;
		sqlm = MySQLHelper.getManager();
		initCurrStatus();
		schedule();
	}
	
	
	

	private void schedule() {
		// TODO Auto-generated method stub
		switch (currstatus) {
		case Constan.INITIAL_STATUS:
			enterInitMenu();
			break;
		case Constan.QueryTimeTable.TABLE_SUB:
			enterSub();
			break;
		case Constan.QueryTimeTable.TABLE_ERROR:
			enterError();
			break;
		}
	}




	private void initCurrStatus() {
		// TODO Auto-generated method stub
		
		Connection rcon = sqlm.getRConnection();
		UserInfoDao uidao = new UserInfoDao(rcon);
		int status = uidao.getStatus(recv.getFromUserName());
		if(status == Constan.INITIAL_STATUS)
			currstatus = Constan.QueryTimeTable.TABLE_SUB;
		else if(status == Constan.QueryTimeTable.TABLE_SUB 
				&& recv.getContent().equals("m"))
			currstatus = Constan.INITIAL_STATUS;
		else currstatus = Constan.QueryTimeTable.TABLE_ERROR;
		sqlm.close(rcon);
	}




	@Override
	public String getReply() {
		// TODO Auto-generated method stub
		reply = new ReplyTextMessage();
		reply.setFromUserName(recv.getToUserName());
		reply.setToUserName(recv.getFromUserName());
		reply.setMsgType("text");
		reply.setContent(result);
		return XmlUtil.obj2xml(reply);
	}
	
	
	
	//进入住菜单
	private void enterInitMenu(){
		System.out.println("从课表进入主菜单");
		
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uidao = new UserInfoDao(wcon); 
		result = "请选择【】中的数字，选择您想要的服务。\n"
				+"【1】成绩查询\n"
				+"【2】课表查询\n"
				+"【3】公告查询\n"
				+"【4】自习室查询";
		uidao.updateStatus(Constan.INITIAL_STATUS,
				recv.getFromUserName());
				
	}
	
	
	private void enterSub(){
		System.out.println("进入了查询课表的子菜单");
		Connection rcon = sqlm.getRConnection();
		TimeTableDao qtb = new TimeTableDao(rcon);
		StudentDao sd = new StudentDao(rcon);
		result = qtb.getTimeTable(
				sd.getAcademy(recv.getFromUserName()));
		result += "返回主菜单，请回复【m】";
		sqlm.close(rcon);
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uidao = new UserInfoDao(wcon); 
		uidao.updateStatus(Constan.QueryTimeTable.TABLE_SUB,
				recv.getFromUserName());
	}
	
	//如果进入错误则不更新状态
	private void enterError(){
		result = "你的输入有误!\n返回主菜单，请回复【m】";
	}
	

}

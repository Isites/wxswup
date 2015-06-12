package com.wx.interce;

import java.sql.Connection;

import com.wx.control.MsgHandle;
import com.wx.control.QueryGrade;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptGrade implements InterceptHandle {

	@Override
	public String intercept(WXMessage args) {
		// TODO Auto-generated method stub
		MySQLManager mm = MySQLHelper.getManager();
		Connection rcon = mm.getRConnection();
		UserInfoDao uidao = new UserInfoDao(rcon);
		RecvTextMessage recv = (RecvTextMessage) args;
		int currstatus = uidao.getStatus(recv.getFromUserName());
		if(currstatus >= Constan.QueryGradeStatus.GRADE_SUB &&
				currstatus <= Constan.QueryGradeStatus.GRADE_MAX ||
				(currstatus == Constan.INITIAL_STATUS && 
				recv.getContent().equals("1"))){
			mm.close(rcon);
			MsgHandle msgH = new QueryGrade(recv);
			return msgH.getReply();
		}
		else{
			mm.close(rcon);
			return null;
		}
	}

}

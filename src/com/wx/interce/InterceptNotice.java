package com.wx.interce;

import java.sql.Connection;

import com.wx.control.MsgHandle;
import com.wx.control.QueryNotice;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptNotice implements InterceptHandle {

	@Override
	public String intercept(WXMessage arg) {
		// TODO Auto-generated method stub
		MySQLManager mm = MySQLHelper.getManager();
		Connection rcon = mm.getRConnection();
		UserInfoDao uidao = new UserInfoDao(rcon);
		//强转
		RecvTextMessage recv = (RecvTextMessage)arg;
		int currstatus = uidao.getStatus(recv.getFromUserName());
		if(currstatus >= Constan.QueryNotice.NOTICE_SUB &&
				currstatus <= Constan.QueryNotice.NOTICE_MAX ||
				(currstatus == Constan.INITIAL_STATUS && 
				recv.getContent().equals("3"))){
			mm.close(rcon);
			MsgHandle msgH = new QueryNotice(recv);
			return msgH.getReply();
		}
		else{
			mm.close(rcon);
			return null;
		}
	}

}

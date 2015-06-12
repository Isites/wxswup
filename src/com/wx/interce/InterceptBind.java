package com.wx.interce;

import java.sql.Connection;

import com.wx.control.BindUser;
import com.wx.control.MsgHandle;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.WXMessage;

public class InterceptBind implements InterceptHandle {
	
	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		MySQLManager mm = MySQLHelper.getManager();
		Connection rcon = mm.getRConnection();
		UserInfoDao uinfod = new UserInfoDao(rcon);
		
		if(!uinfod.isExistUser(recv.getFromUserName())){
			mm.close(rcon);
			//交给绑定处理
			MsgHandle msgh = new BindUser((RecvTextMessage) recv);
			return msgh.getReply();
		}
		else{
			mm.close(rcon);
			return null;
		}		
	}

}

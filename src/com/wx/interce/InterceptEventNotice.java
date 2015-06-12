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

public class InterceptEventNotice implements InterceptEventHandle {

	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		System.out.println("EventNotice");
		MySQLManager mm = MySQLHelper.getManager();
		Connection wcon = mm.getWConnection();
		UserInfoDao uidao = new UserInfoDao(wcon);
		uidao.updateStatus(Constan.INITIAL_STATUS, recv.getFromUserName());
		RecvTextMessage tmp = new RecvTextMessage();
		tmp.setContent("3");
		tmp.setCreateTime(recv.getCreateTime());
		tmp.setFromUserName(recv.getFromUserName());
		tmp.setMsgId("");
		tmp.setMsgType(Constan.MessageType.TEXT_MESSAGE);
		tmp.setToUserName(recv.getToUserName());
		MsgHandle msg = new QueryNotice(tmp);
		return msg.getReply();
	}

	// 用于判定所属菜单的类型
	public int getMenuItem() {
		return Constan.QueryNotice.NOTICE_SUB;
	}

}

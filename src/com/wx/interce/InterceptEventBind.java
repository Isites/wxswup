package com.wx.interce;

import java.sql.Connection;

import com.wx.control.BindUser;
import com.wx.control.MsgHandle;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.WXMessage;
import com.wx.util.Constan;

public class InterceptEventBind implements InterceptEventHandle {

	//用来控制开始绑定和准备绑定
	private String contrl;
	
	public InterceptEventBind(String control) {
		// TODO Auto-generated constructor stub
		this.contrl = control;
	}
	//用于判定所属菜单的类型
	@Override
	public int getMenuItem(){
		return Constan.INITIAL_STATUS;
	}
	
	
	@Override
	public String intercept(WXMessage recv) {
		// TODO Auto-generated method stub
		System.out.println("EventBind");

		MySQLManager mm = MySQLHelper.getManager();
		Connection rcon = mm.getRConnection();
		UserInfoDao uinfod = new UserInfoDao(rcon);

		if (!uinfod.isExistUser(recv.getFromUserName())) {
			mm.close(rcon);
			RecvTextMessage tmp = new RecvTextMessage();
			tmp.setContent(contrl);
			tmp.setCreateTime(recv.getCreateTime());
			tmp.setFromUserName(recv.getFromUserName());
			tmp.setMsgId("");
			tmp.setMsgType(Constan.MessageType.TEXT_MESSAGE);
			tmp.setToUserName(recv.getToUserName());

			// 交给绑定处理
			MsgHandle msgh = new BindUser(tmp);
			return msgh.getReply();
		} else {
			mm.close(rcon);
			return "你已经绑定了公众号。";
		}
	}

}

package com.wx.control;


import java.sql.Connection;

import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.StudentDao;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class BindUser implements MsgHandle {
	private RecvTextMessage recv;
	private MySQLManager sqlm;
	private int currstatus;
	private ReplyTextMessage reply;
	private String result;
	
	public BindUser(RecvTextMessage recv){
		this.recv = recv;
		sqlm = MySQLHelper.getManager();
		initCurrStatus();
		schedule();
	}
	private void schedule(){
		switch (currstatus) {
		case Constan.BindUserStatus.BIND_PRPAREING:
			preparBind();
			break;
		case Constan.BindUserStatus.BIND_STARTING:
			startBind();
			break;
		case Constan.BindUserStatus.BIND_RESULT:
			bindResult();
			break;
		case Constan.BindUserStatus.BIND_CANCLE:
			bindCancle();
			break;
		}
	}
	
	//得到处理后的消息，用来返回给客户端
	@Override
	public String getReply(){
		
		reply = new ReplyTextMessage();
		reply.setFromUserName(recv.getToUserName());
		reply.setToUserName(recv.getFromUserName());
		reply.setMsgType("text");
		reply.setContent(result);
		
		String res = XmlUtil.obj2xml(reply);
		return res;

	}
	
	//对currstatus做处理
	private void initCurrStatus(){
		if(recv.getContent().equals("y"))
			currstatus = Constan.BindUserStatus.BIND_STARTING;
		else if(recv.getContent().equals("n")){
			currstatus = Constan.BindUserStatus.BIND_CANCLE;
		}
		else if(recv.getContent().contains("+")) 
			currstatus = Constan.BindUserStatus.BIND_RESULT;
		else currstatus = Constan.BindUserStatus.BIND_PRPAREING;
	}
	
	//用户绑定进入准备阶段
	private void preparBind(){
		System.out.println("preparBind");
		result = "您还未绑定公众账号,不能享受我们提供的服务。"
				+ "回复【】中的字符选择是否绑定公众号\n"
				+ "【y】绑定\n"
				+ "【n】不绑定";	
	}
	private void startBind(){
		System.out.println("startBind");
		result = "绑定微信公众账号请回复下面信息\n\"姓名+学号+密码\"";
	}
	private void bindCancle(){
		System.out.println("bindCancle");
		result = "谢谢您的关注，不过不绑定公众账号是无法享受我们的服务的。回复任意消息继续";
	}
	
	private void bindResult(){
		System.out.println("bindresult");
		if(isRightFormat()){
			System.out.println("绑定格式正确");
			String str[] = recv.getContent().split("[+]");
			Connection rcon = sqlm.getRConnection();
			StudentDao sd = new StudentDao(rcon);
			result = sd.isBindError(str[1], str[0], str[2]);
			sqlm.close(rcon);
			if(result == null){
				Connection wcon = sqlm.getWConnection();
				UserInfoDao uidao = new UserInfoDao(wcon);
				
				
				
				int item = uidao.insertItem(recv.getFromUserName(),
						str[1],
						Constan.INITIAL_STATUS);
				sqlm.close(wcon);
				if(item > 0){
					result = "恭喜您已经成功绑定了我们的微信公众平台\n"
				            +"请选择【】中的数字，选择您想要的服务。\n"
							+"【1】成绩查询\n"
							+"【2】课表查询\n"
							+"【3】公告查询\n"
							+"【4】自习室查询";
				}
				else{
					System.out.println("插入数据出错");
					result = "服务器发生未知错误,请稍后再试";
				}
			}
		}
		else {
			result = "输入格式不正确，请输入如下的格式\n"
					+ "小明+123456789+1234";
		}
		
	}
	
	
	//先判断格式是否正确
	private boolean isRightFormat(){
		String str[] = recv.getContent().split("[+]");
		if(str.length != 3 ) return false;
		//这里就不先检测学号是否全为数字了
		return true;
	}
	
}

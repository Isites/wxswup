package com.wx.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.wx.dao.GradeDao;
import com.wx.dao.MySQLHelper;
import com.wx.dao.MySQLManager;
import com.wx.dao.UserInfoDao;
import com.wx.msgimpl.RecvTextMessage;
import com.wx.msgimpl.ReplyTextMessage;
import com.wx.util.Constan;
import com.wx.util.XmlUtil;

public class QueryGrade implements MsgHandle{
	
	private RecvTextMessage recv;
	private MySQLManager sqlm;
	private int currstatus;
	private ReplyTextMessage reply;
	private String result;
	private ArrayList<String> yearTerm;
	
	public QueryGrade(RecvTextMessage recv){
		this.recv = recv;
		sqlm = MySQLHelper.getManager();
		initCurrStatus();
		schedule();
		
	}
	
	private void schedule(){
		
		switch (currstatus) {
		case Constan.INITIAL_STATUS:
			enterInitMenu();
			break;
		case Constan.QueryGradeStatus.GRADE_SUB:
			enterSub();
			break;
		case Constan.QueryGradeStatus.GRADE_DETAIL:
			enterDetail();
			break;
		case Constan.QueryGradeStatus.GRADE_ERROR:
			//System.out.println("出错");
			enterError();
			break;

		}
		
	}
	
	//回到了主菜单
	private void enterInitMenu(){
		System.out.println("回到主菜单");
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
	//进入了查询成绩的子菜单
	private void enterSub(){
		System.out.println(result);
		System.out.println("进入了查询成绩的子菜单");
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uidao = new UserInfoDao(wcon); 
		result = "请回复【】中的数字，选择学年学期\n";
		for(int i = 0; i < yearTerm.size(); i++){
			result += "【"+i+"】"+yearTerm.get(i)+"\n";
		}
		result += "返回主菜单，请回复【m】";
		
		uidao.updateStatus(Constan.QueryGradeStatus.GRADE_SUB,
				recv.getFromUserName());
	}
	//将成绩查询结果返回
	private void enterDetail(){
		System.out.println("查询成绩结果");
		String yt = yearTerm.get(Integer.parseInt(recv.getContent()));
		Connection rcon = sqlm.getRConnection();
		UserInfoDao uidao = new UserInfoDao(rcon); 
		
		String sql = "select CourseName, Score, Credit from Course, Grade"
				+" where YearTerm = '"+yt+"' AND Grade.StudentId = '"
				+uidao.getStudentId(recv.getFromUserName())
				+"' AND Course.CourseNum = Grade.CourseNum";
		try {
			System.out.println(result);
			Statement stat = (Statement) rcon.createStatement();
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			int count = 1;
			//这里有点奇怪
			result = "";
			while(rs.next()){
				result += "【"+count+"】"+rs.getString("CourseName")
						+"  "+rs.getString("Score")+"分  "
						+rs.getString("Credit")+"学分\n";
				count++;
			}
			result += "\n选择学年学期，请回复【99】\n"
					+ "返回主菜单，请回复【m】";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sqlm.close(rcon);
		//最后要更新用户的当前状态
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uidao1 = new UserInfoDao(wcon);
		uidao1.updateStatus(Constan.QueryGradeStatus.GRADE_DETAIL,
				recv.getFromUserName());
		sqlm.close(wcon);
		
	}
	//用户输入错误时
	private void enterError(){
		result = "你的输入有误请回复【】中的值！";
		Connection wcon = sqlm.getWConnection();
		UserInfoDao uidao = new UserInfoDao(wcon);
		uidao.updateStatus(Constan.QueryGradeStatus.GRADE_ERROR,
				recv.getFromUserName());
	}
	//对currstatus作初始化处理
	private void  initCurrStatus(){
		System.out.println("得到现在的状态");
		Connection rcon = sqlm.getRConnection();
		GradeDao gd = new GradeDao(rcon);
		UserInfoDao uidao = new UserInfoDao(rcon);
		int status = uidao.getStatus(recv.getFromUserName());
		String stuid = uidao.getStudentId(recv.getFromUserName());
		yearTerm = gd.getYearTerm(stuid);
		//回复m表示返回主菜单
		if(recv.getContent().equals("m") && status != Constan.INITIAL_STATUS){ 
			status = Constan.INITIAL_STATUS;
			return;
		}
		int content = -1;
		try {
			content = Integer.parseInt(recv.getContent());
		} catch (Exception e) {
			// TODO: handle exception
			currstatus = Constan.QueryGradeStatus.GRADE_ERROR;
			return;
		}
		if(status == Constan.INITIAL_STATUS) 
			currstatus = Constan.QueryGradeStatus.GRADE_SUB;
		else if(status == Constan.QueryGradeStatus.GRADE_SUB
				&& content < yearTerm.size()
				&& content >= 0)
			currstatus = Constan.QueryGradeStatus.GRADE_DETAIL;
		else if(status == Constan.QueryGradeStatus.GRADE_ERROR)
			currstatus = Constan.QueryGradeStatus.GRADE_SUB;
		else if(recv.getContent().equals("99"))
			currstatus = Constan.QueryGradeStatus.GRADE_SUB;
		else currstatus = Constan.QueryGradeStatus.GRADE_ERROR;
		
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
	
}

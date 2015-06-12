package com.wx.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class StudentDao {

	private Connection con;
	private String table = "Student";

	public StudentDao(Connection con) {
		this.con = con;
	}
	
	//返回学生的学院
	public String getAcademy(String wxid){
		
		String sql = "select Student.Academy from Student"
				+ " where Student.StudentId = (select UserInfo.StudentId from"
				+ " UserInfo where UserInfo.WXId = '"+wxid+"')";
		Statement stat;
		try {
			stat = (Statement) con.createStatement();
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			if(rs.next()) return rs.getString("Academy");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	// 通过数据库中的信息，判断学生的绑定信息是否一致，如果不一致返回相应的错误信息
	// 如果一致返回null
	
	public String isBindError(String stuid,
			String name,
			String pwd){
		String nm;
		String pd;
		
		try {
			Statement stat = (Statement) con.createStatement();
			String sql = "select * from "+table+ " where StudentId = '"+stuid+"'";
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			if(!rs.next()) return "您的学号输入有误，或者我们暂时还没有更新您的信息，我们会及时更新您的信息";
			else{
				nm = rs.getString("Name");
				pd = rs.getString("Password");
				if(!nm.equals(name)) return "您的姓名输入有误，请重新输入";
				if(!pd.equals(pwd)) return "您的密码输入错误，请重新输入";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public String getTableName(){
		return table;
	}
	
//	public static void main(String[] args) {
//	MySQLManager manager = MySQLHelper.getManager();
//	System.out.println(new StudentDao(
//			manager.getRConnection())
//	.getAcademy("o6WwbuNcRkrYHMT-4n3GveYtFqlM"));
//}

}

package com.wx.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class UserInfoDao {
	
	private Connection con;
	private String table = "UserInfo";
	public UserInfoDao(Connection con){
		this.con = con;
	}
	
	//首先判断数据库中是否存在该用户
	public boolean isExistUser(String wxid){
		boolean isExist = false;
		
		try {
			Statement stat = (Statement) con.createStatement();
			String sql = "select * from "+table+ " where WXId = '"+wxid+"'";
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			if(rs.next()) isExist = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isExist;
	}
	
	//得到当前用户的访问状态
	public int getStatus(String wxid){
		int status = 0;
		try {
			Statement stat = (Statement) con.createStatement();
			String sql = "select Status from "+table+ " where WXId = '"+wxid+"'";
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			if(rs.next())
				status = rs.getInt("Status");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	//获得用户的学号
	public String getStudentId(String wxid){
		String stuid = "";
		try {
			Statement stat = (Statement) con.createStatement();
			String sql = "select StudentId from "+table+ " where WXId = '"+wxid+"'";
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			if(rs.next())
				stuid = rs.getString("StudentId");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stuid;
	}
	
	//把用户的信息插入到数据库中
	public int insertItem(String wxid,
			String stuid,
			int staus){
		int items = 0;
		try {
			String sql = "insert into " +table+" values('"+wxid+"','"+stuid+"','"+staus+"');";
			Statement stat = (Statement) con.createStatement();
			items = stat.executeUpdate(sql);
			//ResultSet rs = (ResultSet) stat.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	//更新用户的状态
	public int updateStatus(int status,String wxid){
		int items = 0;
		String sql = "update "+table+" set Status = "+status+" where WXId = '"+wxid+"'";
		try {
			Statement stat = (Statement) con.createStatement();
			items = stat.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	public String getTableName(){
		return table;
	}

}

package com.wx.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class GradeDao {
	private Connection con;
	private String table = "Grade";
	public GradeDao(Connection con){
		this.con = con;
	}
	
	
	//取得某学生的学年学期
	public ArrayList<String> getYearTerm(String stuid){
		ArrayList<String> yt = new ArrayList<String>();
		try {
			Statement stat = (Statement) con.createStatement();
			String sql = "select YearTerm from "+table+" where StudentId = "+stuid
					+ " UNION select YearTerm from "+table+" where StudentId = "+stuid;
			ResultSet rs = (ResultSet) stat.executeQuery(sql);
			while(rs.next()){
				yt.add(rs.getString("YearTerm"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(yt);
		return yt;
	}
	
	public String getTableName(){
		return table;
	}
	
	
}

package com.wx.dao;

import java.sql.Connection;

public class CourseDao {

	private Connection con;
	private String table = "Course";
	
	public CourseDao(Connection con){
		this.con = con;
	}
	
	public String getTableName(){
		return table;
	}
	
}

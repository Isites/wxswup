package com.wx.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

public class TimeTableDao {
	
	private Connection con;
	private String table = "TimeTable";
	//当前星期几= 
	private int curr;
	
	
	private String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
	
	public TimeTableDao(Connection con){
		this.con = con;
		Calendar cal = Calendar.getInstance();
		curr = cal.get(Calendar.DAY_OF_WEEK)-1 < 0 ?  0 
				: cal.get(Calendar.DAY_OF_WEEK)-1;
		
	}
	
	//得到今日的课程和明天的课程，如果是周六周日则返回休息
	
	public String getTimeTable(String academy){
		if(curr % 6 == 0){
			return "今天是周末，出去活动活动放松一下吧！^_^";
		}
		else {
			try {
				String sql = "";
				String result = "";
				Calendar cal = Calendar.getInstance();
				if(cal.get(Calendar.HOUR_OF_DAY) > 18){
					sql = "select * from "+table+ " where Academy = '"+academy+"' "
							+ "AND Week = '"+weekDays[curr+1]+"'";
					result = "你今天的课上完啦,明天的课程有：\n";
				}
				else {
					sql = "select * from "+table+ " where Academy = '"+academy+"' "
							+ "AND Week = '"+weekDays[curr]+"'";
					result = "你今日的课程有：\n";
				}
				Statement stat = (Statement) con.createStatement();
				ResultSet rs = (ResultSet) stat.executeQuery(sql);
				while(rs.next()){
					result += rs.getString("CourseName")+" "+rs.getString("Addr")+"\n";
					result += rs.getString("Teacher")+" 第"+rs.getString("Time")+"节\n\n";
					//result += rs.getInt("StartWeek")+"-"+rs.getInt("EndWeek")+"\n";
				}
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}	
	
	
//	public static void main(String[] args) {
//		MySQLManager manager = MySQLHelper.getManager();
//		System.out.println(new TimeTableDao(
//				manager.getRConnection())
//		.getTimeTable("计算机科学学院"));
//	}

}

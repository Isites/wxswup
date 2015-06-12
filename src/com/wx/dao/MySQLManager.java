package com.wx.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLManager extends MySQLHelper {
	
	private Connection rcon;
	private Connection wcon;
	
	public MySQLManager(){
		super();
	}
	
	
	
	
	//得到主数据库的连接
	public synchronized Connection getWConnection(){
		try {
			if(wcon == null || wcon.isClosed()){
				try {
					wcon = DriverManager.getConnection(getWUrl(),
							getUser(),
							getPwd());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wcon;
	}
	//得到从数据库的连接
	public synchronized Connection getRConnection(){
		//System.out.println(rcon);
		try {
			if(rcon == null || rcon.isClosed()){
				try {
					rcon = DriverManager.getConnection(getRUrl(),
							getUser(),
							getPwd());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rcon;
	}
	
	public synchronized void close(Connection con){
		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//当应用被系统回收时，要关闭掉数据库连接
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(wcon != null) wcon.close();
		if(rcon != null) rcon.close();
	}
	
	

}

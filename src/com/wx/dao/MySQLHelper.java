package com.wx.dao;


public abstract class MySQLHelper {
	//主库域名
	private String rurl =  "jdbc:mysql://r.rdc.sae.sina.com.cn:3307/app_wxswup";
	//"jdbc:mysql://localhost:3306/swpu";
	//从库域名
	private String wurl = "jdbc:mysql://w.rdc.sae.sina.com.cn:3307/app_wxswup";
			//"jdbc:mysql://localhost:3306/swpu"; 
			
	private String driver = "com.mysql.jdbc.Driver";
	
	private String user = 
			//"root"; 
			"o33kyll02o";
	private String pwd = 
			//"";
	"001yz44i0j0jixyijzjw32ym1w53xj4j21klxl55";
	
	public MySQLHelper(){
		//加载驱动器
		try {
			Class.forName(driver).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getWUrl(){
		return wurl;
	}
	public String getRUrl(){
		return rurl;
	}
	
	public String getUser(){
		return user;
	}
	public String getPwd(){
		return pwd;
	}
	
	
	public static MySQLManager getManager(){
		return new MySQLManager();
	}
	
	
	

}

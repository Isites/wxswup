package com.wx.interce;


//接口可以继承过个接口，这一点一定要注意
public interface InterceptEventHandle extends InterceptHandle {
	
	//用于判定所属菜单的类型
	public int getMenuItem();
}

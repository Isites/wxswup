package com.wx.interce;

import com.wx.msgimpl.WXMessage;

public interface InterceptHandle {
	public String intercept(WXMessage recv);
}

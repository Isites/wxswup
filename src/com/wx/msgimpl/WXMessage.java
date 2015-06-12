package com.wx.msgimpl;

public abstract class WXMessage {

	/**为了方便一定要和微信xml文件中的标签名一样*/
	
	//微信接受方账号
	private String ToUserName;
	//微信发送方账号
	private String FromUserName;
	//消息创建时间
	private String CreateTime;
	//消息类型
	private String MsgType;
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		this.MsgType = msgType;
	}	
}

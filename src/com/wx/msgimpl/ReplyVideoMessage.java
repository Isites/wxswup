package com.wx.msgimpl;

import java.util.HashMap;

public class ReplyVideoMessage extends WXMessage {
	
	//用hashmap来描述xml文件中的嵌套结构
	private HashMap<String, String> Video;
	
	public ReplyVideoMessage(){
		Video = new HashMap<String, String>();
		//表示Video标签中还包含了MediaId标签
		Video.put("MediaId", "");
		Video.put("Title", "");
		Video.put("Description", "");
	}
	
	
	public HashMap<String, String> getVideo(){
		return Video;
	}
	
	public String getMediaId(){
		return Video.get("MediaId");
	}
	public String getTitle(){
		return Video.get("Title");
	}
	public String getDescription(){
		return Video.get("Description");
	}
	
	public void setMediaId(String mediaid){
		Video.put("MediaId",mediaid);
	}
	public void setTitle(String title){
		Video.put("Title",title);
	}
	public void setDescription(String description){
		Video.put("Description",description);
	}

}
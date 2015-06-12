package com.wx.swup;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.wx.control.EventMessageHandle;
import com.wx.control.MsgHandle;
import com.wx.control.SubscribeEvent;
import com.wx.control.TextMessageHandle;
import com.wx.util.Constan;
import com.wx.util.EncAndDecUtil;
import com.wx.util.VerifyEntry;

//将微信发来的消息交给不同的处理逻辑
public class TaskDispatch {

	private HttpServletResponse response;
	private HttpServletRequest request;
	private String replymsg;
	// 微信加密签名
	private String signature;
	// 时间戳
	String timestamp;
	// 随机数
	private String nonce;
	//微信签名
	private String msgSignature;

	public TaskDispatch(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;

		// 微信加密签名
		signature = request.getParameter("signature");
		timestamp = request.getParameter("timestamp");
		// 随机数
		nonce = request.getParameter("nonce");
		msgSignature = request.getParameter("msg_signature");

	}

	// 首先判断消息是否来自微信服务器
	private boolean isFromWX() {
		return VerifyEntry.verifyResult(signature, timestamp, nonce);
	}

	// 取得来自微信的请求实体
	private Document getEntry() throws Exception {

		InputStream in = request.getInputStream();
		SAXReader reader = new SAXReader();
		Document entry = reader.read(in);
		return entry;
	}

	// 获得请求的类型
	private String getPostType(Document entry) {
		Element ele = (Element) entry.selectObject("xml/MsgType");
		return ele.getText();
	}

	public void dispatch() {
		if (!isFromWX())
			return;
		try {
			Document entry = getEntry();
			entry = EncAndDecUtil.decryptMsg(msgSignature, timestamp, nonce, entry);
			String type = getPostType(entry);

			if (type.equals(Constan.EventPrope.EVENT)) {
				dispatcEve(entry);
			} else {
				dispatchMsg(entry, type);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 对事件的派发
	private void dispatcEve(Document entry) {
		String type = getEveType(entry);
		if (type.equals(Constan.EventPrope.SUBSCRIBE_EVENT)) {
			MsgHandle msgh = new SubscribeEvent(entry, response);
			replymsg = msgh.getReply();
			msgh = null;
		}
		else{
			//暂时只有用户的菜单事件
			MsgHandle msgh = new EventMessageHandle(entry);
			replymsg = msgh.getReply();
			msgh = null;
		}

	}

	// 获取事件的类型

	private String getEveType(Document entry) {
		Element element = (Element) entry.selectObject("xml/Event");
		return element.getText();
	}

	// 对普通消息的派发
	private void dispatchMsg(Document entry, String type) {
		if (type.equals(Constan.MessageType.TEXT_MESSAGE)) {
			MsgHandle msh = new TextMessageHandle(entry);
			replymsg = msh.getReply();
			msh = null;
		}
	}
	
	
	//对微信处理后的消息进行回复
	public void reply(){
		PrintWriter out = null;
		try {
			//对要返回给微信的消息体进行加密处理
			replymsg = EncAndDecUtil.encryptMsg(replymsg, nonce, timestamp);
			
			response.setContentType("text/html; charset=utf-8");
			System.out.println("回复给用户的消息："+replymsg);
			out = response.getWriter();
			out.write(replymsg);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			out.close();
		}
	}
	
	
}

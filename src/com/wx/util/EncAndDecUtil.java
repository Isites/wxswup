package com.wx.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

public class EncAndDecUtil {
	
	
	
	//对微信传来的消息进行解密
	public static Document decryptMsg(String msgSignature, String timestamp,
			String nonce, Document entry){
		String result = null;
		try {
			WXBizMsgCrypt hand = new WXBizMsgCrypt(Constan.TOKEN,
					Constan.AES_KEY,
					Constan.APP_ID);
			
			Element ele = (Element) entry.selectObject("xml/Encrypt");
			String encrypt = ele.getText();
			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			
			String fromXML = String.format(format, encrypt);
			// 第三方收到公众号平台发送的消息
			result = hand.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			hand = null;
			
			
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Document document = null;
		try {
			document = DocumentHelper.parseText(result);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	//对要传给微信的信息进行加密
	
	
	public static String encryptMsg(String replymsg,String nonce,String timestamp){
		
		String result = null;
		
		try {
			WXBizMsgCrypt hand = new WXBizMsgCrypt(Constan.TOKEN,
					Constan.AES_KEY,
					Constan.APP_ID);
			
			result = hand.encryptMsg(replymsg, timestamp, nonce);
			
			
			hand = null;
		} catch (AesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	

}

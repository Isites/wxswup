package com.wx.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class VerifyEntry {

	/**
	 * 验证是否是微信登陆 同时按照微信文档要求先对三个参数进行字典排序 最后再进行sha-1加密
	 */

	public static boolean verifyResult(String signature,
			String timestamp,
			String nonce
			) 
	{

		if (signature == null || timestamp == null || nonce == null)
			return false;
		String strArr[] = { Constan.TOKEN, timestamp, nonce };
		// 字典排序
		Arrays.sort(strArr);
		StringBuilder content = new StringBuilder();
		for (String str : strArr) {
			content.append(str);
		}
		String result = null;
		// 实现sha-1加密方式
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			sha1.update(content.toString().getBytes());
			// result = new BigInteger(1, sha1.digest()).toString(16);
			result = bytes2str(sha1.digest());

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content = null;
		// 返回判断结果
		return result != null ? result.equals(signature) : false;
	}

	// 将字节数组转换成16进制字符串
	private static String bytes2str(byte bt[]) {
		StringBuilder strBuilder = new StringBuilder();

		for (byte b : bt) {
			strBuilder.append(byte2HEX(b));
		}

		return strBuilder.toString();
	}

	// 将单个字节转换为16进制
	private static String byte2HEX(byte bt) {

		String hex = "0123456789abcdef";
		// 一个字节转换为两个量位的16进制
		char h[] = new char[2];
		// 去字节的高4位转换为16进制
		h[0] = hex.charAt((bt >>> 4) & 0x0F);
		h[1] = hex.charAt(bt & 0x0F);
		// 返回该字节的16进制表示
		return new String(h);
	}

}

package com.wx.swup;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.util.VerifyEntry;



/**
 * 初步计划
 * com.wx.swup
 * 微信url的入口，所以，这里是一切请求和微信服务器发来的消息的入口
 * com.wx.model包
 * 中主要建立和与数据库表相关的类
 * com.wx.dao
 * 主要封装各种对数据库的各种操作
 * com.wx.util
 * 主要实现比较实用的功能
 * 如xml的处理，json的处理，access_token接口，加密算法等
 * com.wx.control
 * 主要实现任务的分配和响应等
 * com.wx.msgimpl
 * 实现各种文本消息等的接口
 */
public class WXEnter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WXEnter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//验证消息是否来自微信客户端
			checkFromWX(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		TaskDispatch scheduled = new TaskDispatch(request, response);
		scheduled.dispatch();
		scheduled.reply();
		scheduled = null;
		

	}
	
	//检查请求是否来自微信服务器，如果不是予以提示
	private void checkFromWX(HttpServletRequest request,HttpServletResponse response) {
		//微信加密签名
		String signature = request.getParameter("signature");
		//时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        
        PrintWriter out = null;
        try {
        	response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			
			if(VerifyEntry.verifyResult(signature, timestamp, nonce)){
				out.print(echostr);
			}
			else{
				out.print(new String("请使用微信客户端登陆！"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
        	out.close();
        	out = null;
        }

	}
	

}

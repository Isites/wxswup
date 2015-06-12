package com.wx.util;

public class Constan {
	
	//微信认证token
	public final static String TOKEN = "xnswupquery";
	
	//消息加解密密钥
	public final static String AES_KEY = "t0ggWfnmMCcoeYQaNsKy66MgckxlsweWd9nydu0RIE9";
	
	//appid
	public final static String APP_ID = "wxca15a3aeadade365";
	
	//绑定成功后未进入其他状态的出事状态,此时的状态要更新到数据库中，表初始状态,及主菜单状态
	public final static int INITIAL_STATUS = 0;
	
	
	
	
	//微信的事件推送
	public class EventPrope{
		
		public final static String EVENT = "event";
		
		//具体的事件类型
		//用户的订阅事件
		public final static String SUBSCRIBE_EVENT = "subscribe";
		//用户取消订阅的事件
		public final static String UNSUBSCRIBE_EVENT = "unsubscribe";
		//用户点击事件
		public final static String CLICK_EVENT = "CLICK";
		//用户点击跳转链接事件
		public final static String VIEW_EVENT = "VIEW";
		
	}
	
	//微信的普通用户消息
	public class MessageType{
		public final static String TEXT_MESSAGE = "text";
		public final static String IMAGE_ARTICLE_MESSAGE ="news";
	}
	
	
	//绑定公众号的进度
	public class BindUserStatus{
		//进入绑定公众好的准备阶段，询问用户是否要绑定公众号
		public final static int BIND_PRPAREING = 0;
		//进入绑定公众好的第二个阶段当用户确认绑定，让用户发送姓名，学号和密码
		public final static int BIND_STARTING = 1;
		//进入绑定公众号的最后阶段绑定成功，向用户发送绑定结果
		public final static int BIND_RESULT = 2;
		//用户选择不绑定时，的状态
		public final static int BIND_CANCLE = 3;

	}
	
	//查询成绩的进度
	public class QueryGradeStatus{
		//用户从主菜单进入查询成绩的子菜单，想用户发送想要查询那一学期的成绩
		public final static int GRADE_SUB = 10;
		//用户选择学年学期后，向用户发送具体成绩的状态
		public final static int GRADE_DETAIL = 11;
		//输入不能识别
		public final static int GRADE_ERROR = 12;
		//查询成绩的最大状态值
		public final static int GRADE_MAX = 19;
	}
	
	
	//查询课表
	public class QueryTimeTable{
		//用户从主菜单进入课表查询的子菜单，向用户发送今日的课表
		public final static int TABLE_SUB = 20;
		
		
		//当回复不能识别的东西时
		public final static int TABLE_ERROR = 21;
		
		
		//查询课表所能达到的最大状态值
		public final static int TABLE_MAX = 29;
		
	}
	
	//学校公告查询进度,暂时只处理学校的公告信息
	public class QueryNotice{
		//用户从主菜单进入公告查询的子菜单，向用户发送那个图文消息
		public final static int NOTICE_SUB = 30;
		
		
		//查询公告所能达到的最大状态值
		public final static int NOTICE_MAX = 39;
	}
		
	public class QueryRoom{
		//用户从主菜单进入自习室查询子菜单，向用户发送自习室查询
		public final static int ROOM_SUB = 40;
		//处理用户的错误输入
		public final static int ROOM_ERROR = 41;
		
		//查询自习室所能达到的最大状态
		public final static int ROOM_MAX = 49;
		
	}

	//考试安排50
	public class QueryExamPlan{
		//用户从主菜单进入考试安排查询的子菜单，向用户发送考试安排查询结果
		public final static int EXAM_SUB = 50;
		
		
		//考试安排所能达到的最大状态
		public final static int EXAM_MAX = 59;
	}
	
	
	
	//招就信息60
	//公共选课70
	public class QueryGX{
		public final static int GX_SUB = 70;
		public final static int GX_MAX = 79;
	}
	
	
	//公交路线80
	//周边美食90
	//留言表白和人工服务待定，用连接好了
	
	

}

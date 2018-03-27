package com.ktds.actionhistory.vo;

public interface ActionHistory {

	static public interface ReqType{
		public static final String VIEW = "view";
		public static final String MEMBER = "mbr";
		public static final String COMMUNITY = "com";
		public static final String UPLOAD = "upl";
		public static final String DOWNLOAD = "dwn";
	}
	
	static public interface Log{
		public static final String VIEW = "View : %s, Method : %s";
		public static final String LOGIN = "LOGIN : %s";
		public static final String LOGOUT = "LOGOUT : %s";
		public static final String REGIST = "REGIST : Email(%s), Nickname(%s), Result(%s)";
		public static final String WRITE = "Write : Subject(%s), Body(%s)";
		public static final String UPDATE = "Update : Subject(%s), Body(%s)";
		public static final String DELETE = "Delete : Id(%d), Subject(%s), Body(%s)";
		public static final String READ = "Read : ID(%s), Subject(%s), Body(%s)";
		public static final String RECOMMEND = "Recommend : ID(%s)";
		public static final String WRITE_REPLY = "Write Reply : CommunityID(%d), Body(%s)";
		public static final String DOWNLOAD = "Download : CommunityID(%d), Filename(%s)";
		public static final String UPLOAD = "Upload : Filename(%s)";
	}
}

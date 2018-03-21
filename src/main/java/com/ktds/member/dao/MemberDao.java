package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDao {
	
	public int selectCountMemberEmail(String email);
	
	public int selectCountMemberNickname(String nickname);
	
	public int insertMember(MemberVO memberVO);
	
	public MemberVO selectMember(MemberVO memberVO);
	
	//로그인을 해야만 email을 알 수 있기 때문에 id 대신 email을 parameter로 함.
	public String selectSalt(String email);
	
	public int deleteMember(int id);

}

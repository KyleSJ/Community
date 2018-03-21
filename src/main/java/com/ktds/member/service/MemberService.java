package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	
	public boolean createMember(MemberVO memberVO);
	
	public MemberVO readMember(MemberVO memberVO);
	
	public boolean deleteMember(int id, String deleteFlag);

}

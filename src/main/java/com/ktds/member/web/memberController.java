package com.ktds.member.web;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.community.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class memberController {
	
	private MemberService memberService;
	private CommunityService communityService;
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	//ajax
	@RequestMapping("/api/exists/email")
	@ResponseBody //JSON 형태로 보내지게된다 , 쓸려면 의존라이브러리 추가해야됨!
	public Map<String, Boolean> apiIsExistsEmail(@RequestParam String email){
		
		boolean isExists = memberService.readCountMemberEmail(email);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("response", isExists);
		
		return response;
	}
	
	@RequestMapping("/api/exists/nickname")
	@ResponseBody
	public Map<String, Boolean> apiIsExistsNickname(@RequestParam String nickname){
		
		boolean isExists = memberService.readCountMemberNickname(nickname);
		
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("response", isExists);
		
		return response;
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {
		
		if(session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		
		return "member/login";
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public String doLoginActioin(MemberVO memberVO, HttpServletRequest request
						, @RequestAttribute ActionHistoryVO actionHistory) {
		HttpSession session = request.getSession();
		
		//로그인 시도 log 저장
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);
		String log = String.format(ActionHistory.Log.LOGIN, memberVO.getEmail());
		actionHistory.setLog(log);
		
		MemberVO member = memberService.readMember(memberVO);
		
		// FIXME DB에 계정이 존재하지 않을 경우로 변경
		if(member != null) {
			
			session.setAttribute(Member.USER, member);
			
			return "redirect:/";
		}else {
		
		session.setAttribute("status", "fail");
		
		return "redirect:/login";
		}
	}

	@RequestMapping("/logout")
	public String doLogOut(HttpSession session, @RequestAttribute ActionHistoryVO actionHistory) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		
		actionHistory.setReqType(ActionHistory.ReqType.MEMBER);
		String log = String.format(ActionHistory.Log.LOGOUT, member.getEmail());
		actionHistory.setLog(log);
		
		session.invalidate();
		
		return "redirect:/";
	}
	
	@RequestMapping("/loginReject")
	public String loginReject() {
		return "error/loginReject";
	}
	
	@RequestMapping(value="/regist", method = RequestMethod.GET)
	public String viewRegistpage() {
		return "member/regist";
	}
	
	@RequestMapping(value="/regist", method = RequestMethod.POST)
	public String doRegistAction(@ModelAttribute("registForm") @Valid MemberVO memberVO, Errors errors, HttpServletRequest request, Model model) {
		
		System.out.println("regist call");
		if(errors.hasErrors()) {
			return "member/regist";
		}
		
		ActionHistoryVO history = (ActionHistoryVO) request.getAttribute("actionHistory");
		history.setReqType(ActionHistory.ReqType.MEMBER);
		history.setIp(request.getRemoteAddr());
		
		
		if(memberService.createMember(memberVO)) {
			String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), true);
			history.setLog(log);
			model.addAttribute("actionHistory", history);
			return "redirect:/login";
		}
		
		String log = String.format(ActionHistory.Log.REGIST, memberVO.getEmail(), memberVO.getNickname(), false);
		history.setLog(log);
		model.addAttribute("actionHistory", history);
		return "redirect:/regist";
	}
	
	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {
		return "member/delete/process1";
	}
	
	@RequestMapping("/delete/process2")
	public ModelAndView viewedeleteMyCommunitiesPage(
			@RequestParam(required = false, defaultValue="") String password, HttpSession session) {
		
		if( password.length() == 0 ) {
			return new ModelAndView("error/404");
		}
		
		//session의 정보와 받아온 비밀번호 parameter를 비교하지 않는 이유는?
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);
		
		MemberVO verifyMember = memberService.readMember(member);
		if( verifyMember == null ) {
			return new ModelAndView("redirect:/delete/process1");
		}
		
		//TODO 내가 작성한 게시글의 개수 가져오기
		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getId());
		
		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);
		
		String uuid =  UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);
		
		return view;
	}
	
	@RequestMapping("/delete/{deleteFlag}")
	public String doMemberLeave(HttpSession session,
			@RequestParam(required=false, defaultValue="") String token,		
			@PathVariable String deleteFlag) {
		
		String sessionToken = (String) session.getAttribute("__TOKEN__");
		if( sessionToken == null || sessionToken.equals(token)) {
			return "error/404";
		}
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		if(member == null) {
			return "redirect:/login";
		}
		int id = member.getId();
		
		if( memberService.deleteMember(id, deleteFlag) ) {
			session.invalidate();
		}
		return "member/delete/delete";
	}
	
}

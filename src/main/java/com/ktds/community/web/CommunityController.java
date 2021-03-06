package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.actionhistory.vo.ActionHistory;
import com.ktds.actionhistory.vo.ActionHistoryVO;
import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

import io.github.seccoding.web.pager.explorer.PageExplorer;

@Controller
public class CommunityController {

	private final Logger logger = Logger.getLogger(CommunityController.class);
	private CommunityService communityService;

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/reset")
	public String viewInListPage(HttpSession session) {
			session.removeAttribute("__SEARCH__");
			return "redirect:/";
	}
 
	@RequestMapping("/")
	public ModelAndView viewListPage(CommunitySearchVO communitySearchVO, HttpSession session) {
		
		//Data가 안 넘어 왔을 경우
		// 1. 리스트페이지에 처음 접근 했을 때
		// 2. 글 내용 보기에서 목록보기로 왔을 때
		if( communitySearchVO.getPageNo() < 0 ) {
			//session에 저장된 communitySearchVO를 가져옴.
			communitySearchVO = (CommunitySearchVO) session.getAttribute("__SEARCH__");
			//session에 저장된 CommunitySearchVO가 없을 경우 pageNo를 0으로 초기화.
			if( communitySearchVO == null) {
				communitySearchVO = new CommunitySearchVO();
				communitySearchVO.setPageNo(0);
			}
		}
		
		session.setAttribute("__SEARCH__", communitySearchVO);

		ModelAndView view = new ModelAndView();
		view.setViewName("community/list");

		PageExplorer pageExplorer = communityService.getAll(communitySearchVO);
		view.addObject("pageExplorer", pageExplorer);
		view.addObject("search", communitySearchVO);

		return view;
	}

	// @GetMapping("/write")
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(HttpSession session) {
		
		return "community/write";
	}

	// spring 4.3부터 가능
	// @PostMapping("/write")
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView doWrite(@ModelAttribute("writeForm") @Valid CommunityVO communityVO, Errors errors,
			HttpServletRequest request) {
		/*
		 * if(communityVO.getTitle() == null || communityVO.getTitle().length() == 0) {
		 * session.setAttribute("status", "emptyTitle"); return "redirect:/write"; }
		 * 
		 * if(communityVO.getBody() == null || communityVO.getBody().length() == 0) {
		 * session.setAttribute("status", "emptyBody"); return "redirect:/write"; }
		 * 
		 * if(communityVO.getWriteDate() == null || communityVO.getWriteDate().length()
		 * == 0) { session.setAttribute("status", "emptyWriteDate"); return
		 * "redirect:/write"; }
		 */
		

		if (errors.hasErrors()) {
			logger.debug("errors.hassErrorS()실행");
			ModelAndView model = new ModelAndView();
			model.setViewName("community/write");
			model.addObject("communityVO", communityVO);
			return model;
		}
		
		
		
		String requestorIp = request.getRemoteAddr();
		communityVO.setRequestIp(requestorIp);
		
		communityVO.save();
		boolean isSuccess = communityService.createCommunity(communityVO);
		if (isSuccess) {
			return new ModelAndView("redirect:/reset");
		} else {
			return new ModelAndView("redirect:/write");
		}
	}

	@RequestMapping("/detail/{id}")
	public ModelAndView viewDetailList(HttpSession session, @PathVariable int id) {

		ModelAndView view = new ModelAndView();
		view.setViewName("community/detail");

		CommunityVO community = communityService.getOne(id);
		boolean isLast = communityService.isLastId(id);
		view.addObject("isLast", isLast);
		if (community != null) {
			view.addObject("community", community);
		}
		return view;
	}

	@RequestMapping("/read/{id}")
	public String readPage(HttpSession session, @PathVariable int id) {

		if (communityService.increaseViewCount(id)) {
			return "redirect:/detail/" + id;
		}
		return "redirect:/";

	}

	@RequestMapping("/remove/{id}")
	public String viewRemovelList(HttpSession session, @PathVariable int id) {
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);
		
		boolean isMyCommunity = (community.getUserId() == member.getId());
			
		
		if (isMyCommunity && communityService.removeCommunity(id)) {
			return "redirect:/";
		}
		return "WEB-INF/view/error/404";
	}

	@RequestMapping("/recommend/{id}")
	public String doRecommend(HttpSession session, @PathVariable int id, @RequestAttribute ActionHistoryVO actionHistory) {

		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		String log = String.format(ActionHistory.Log.RECOMMEND, member.getEmail() );
		actionHistory.setLog(log);
		if (communityService.increaseRecommendCount(id)) {
			return "redirect:/detail/" + id;
		}
		return "redirect:/detail/" + id;
	}

	
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		DownloadUtil download = new DownloadUtil("C:/uploadFiles/" + filename);
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);
		
		int userId = member.getId();
		if(userId != community.getUserId()) {
			return new ModelAndView("error/404");
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("community", community);
		view.addObject("mode", "modify");
		
		return view;
		
	}
	
	@RequestMapping(value="/modify/{id}", method=RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session,
								HttpServletRequest request,
								@ModelAttribute("writeForm") @Valid CommunityVO communityVO,  Errors errors,
								@RequestAttribute ActionHistoryVO actionHistory) {
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO originalVO = communityService.getOne(id);
		String asIs = actionHistory.getAsIs();
		String toBe = actionHistory.getToBe();
		
		if( member.getId() == originalVO.getId() ) {
			return "error/404";
		}
		
		if( errors.hasErrors() ) {
			return "redirect:/modify/" + id;
		}
		
		CommunityVO newCommunity = new CommunityVO();
		newCommunity.setId( originalVO.getId() );
		newCommunity.setUserId( member.getId() );
		
		boolean isModify = false;
		
		// 1. IP 변경 확인
		String ip = request.getRemoteAddr();
		if( !ip.equals(originalVO.getRequestIp()) ){
			newCommunity.setRequestIp(ip);
			isModify = true;
			asIs += "IP : " + originalVO.getRequestIp();
			toBe += "IP : " + ip;
		}
		// 2. 제목 변경 확인
		if( !originalVO.getTitle().equals( communityVO.getTitle() ) ) {
			newCommunity.setTitle( communityVO.getTitle() );
			isModify = true;
			asIs += "Title : " + originalVO.getTitle() + "<br/>";
			toBe += "Title : " + communityVO.getTitle() + "<br/>";
		}
		// 3. 내용 변경 확인
		if( !originalVO.getBody().equals( communityVO.getBody()) ) {
			newCommunity.setBody( communityVO.getBody() );
			isModify = true;
			asIs += "Body : " + originalVO.getBody() + "<br/>";
			toBe += "Body : " + communityVO.getBody() + "<br/>";
		}
		// 4. 파일 변경 확인
		if( communityVO.getDisplayFilename().length() > 0 ) {
			File file = new File("/Users/kangseongjae/Desktop/study/ktdsCommunityFile/"+ communityVO.getDisplayFilename());
			file.delete();
			communityVO.setDisplayFilename("");
		}
		else {
			communityVO.setDisplayFilename(originalVO.getDisplayFilename());
		}
		
		communityVO.save();
		if( !originalVO.getDisplayFilename().equals( communityVO.getDisplayFilename() ) ) {
			newCommunity.setDisplayFilename( communityVO.getDisplayFilename() );
			isModify = true;
			asIs += "File : " + originalVO.getDisplayFilename() + "<br/>";
			toBe += "File : " + communityVO.getDisplayFilename() + "<br/>";
		}
		
		actionHistory.setReqType(ActionHistory.ReqType.COMMUNITY);
		String log = String.format(ActionHistory.Log.UPDATE, originalVO.getTitle(), originalVO.getBody());
		actionHistory.setLog(log);
		
		if( isModify ) {
			communityService.updateCommunity( newCommunity );
		}
		
		return "redirect:/detail/"+id;
		
	}
	
}

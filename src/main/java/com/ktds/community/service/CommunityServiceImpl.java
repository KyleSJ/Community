package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDao;
import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

import io.github.seccoding.web.pager.Pager;
import io.github.seccoding.web.pager.PagerFactory;
import io.github.seccoding.web.pager.explorer.ClassicPageExplorer;
import io.github.seccoding.web.pager.explorer.PageExplorer;

public class CommunityServiceImpl implements CommunityService {

	private CommunityDao communityDao;

	public void setCommunityDao(CommunityDao communityDao) {
		this.communityDao = communityDao;
	}

	@Override
	public PageExplorer getAll(CommunitySearchVO communitySearchVO) {
		
		Pager pager = PagerFactory.getPager(Pager.ORACLE, communitySearchVO.getPageNo() + "", communityDao.selectCountAll(communitySearchVO));
		
		/*
		 * List Page Explorer
		 * Classic Page Explorer
		 * 조회해야하는 게시글 시작번호, 끝 번호 등을 넘겨준다.
		*/
		PageExplorer pageExplorer = pager.makePageExplorer(ClassicPageExplorer.class, communitySearchVO);
		pageExplorer.setList(communityDao.selectAll(communitySearchVO));
		
		return pageExplorer;
	}

	@Override
	public boolean createCommunity(CommunityVO communityVO) {
		
		String body = communityVO.getBody();
		// \n --> <br>
		body = body.replaceAll("\n", "<br>");
		communityVO.setBody(body);
		
		
		int insertCount = communityDao.insertCommunity(communityVO);
		return insertCount > 0;
	}

	@Override
	public boolean removeCommunity(int id) {
		if(communityDao.deleteOne(id) > 0 ) {
			return true;
		}
		return false;
	}
	
	

	@Override
	public CommunityVO getOne(int id) {
		CommunityVO communityVO = communityDao.selectOne(id);
		if(communityVO != null) {
			return communityVO;
		}
		return null;
	}

	@Override
	public boolean isLastId(int id) {
		return communityDao.isLast(id);
	}

	
	@Override
	public boolean increaseRecommendCount(int id) {
		if(communityDao.incrementRecoomendCount(id)>0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean increaseViewCount(int id) {
		if(communityDao.incrementViewCount(id)>0) {
			return true;
		}
		return false;
	}
	
	public boolean filter(String str) {
		
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("씨");
		blackList.add("발");
		blackList.add("1식");
		blackList.add("2식");
		blackList.add("3식");
		blackList.add("종간나세끼");
		
		//str --> 남편은 2식이에요.(예시)
		String[] splitString = str.split(" ");
		for (String word : splitString) {
			for (String blackString : blackList) {
				if(word.contains(blackString)) {
					return true;
				}
			}
		}
		
		return true;
	}

	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0;
	}

	@Override
	public int readMyCommunitiesCount(int userId) {
		return communityDao.selectMyCommunitiesCount(userId);
	}

	@Override
	public List<CommunityVO> readMyCommunities(int userId) {
		return communityDao.selectMyCommunities(userId);
	}

	@Override
	public boolean deleteCommunities(List<Integer> ids, int userId) {
		return communityDao.deleteCommunities(ids, userId) > 0;
	}

}

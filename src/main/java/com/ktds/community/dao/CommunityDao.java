package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunitySearchVO;
import com.ktds.community.vo.CommunityVO;

public interface CommunityDao {
	
	public int selectCountAll(CommunitySearchVO communitySearchVO);
	
	public List<CommunityVO> selectAll(CommunitySearchVO communitySearchVO);
	
	public int insertCommunity(CommunityVO communityVO);
	
	public int selectMyCommunitiesCount(int userId);
	
	public List<CommunityVO> selectMyCommunities(int userId);
	
	public int deleteOne(int id);
	
	public int deleteMyCommunities(int id);
	
	public int deleteCommunities(List<Integer> ids, int userId);
	
	public CommunityVO selectOne(int id);
	
	public boolean isLast(int id);
	
	public int incrementViewCount(int id);
	
	public int incrementRecoomendCount(int id);
	
	public int updateCommunity(CommunityVO communityVO);
	
}

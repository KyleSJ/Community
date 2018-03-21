package com.ktds.community.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.community.vo.CommunityVO;

public class CommunityDaoImplForOracle extends SqlSessionDaoSupport implements CommunityDao{

	@Override
	public List<CommunityVO> selectAll() {
		return getSqlSession().selectList("CommunityDao.selectAll");
	}

	@Override
	public int insertCommunity(CommunityVO communityVO) {
		return getSqlSession().insert("CommunityDao.insertCommunity", communityVO);
	}

	@Override
	public int deleteOne(int id) {
		return getSqlSession().delete("CommunityDao.deleteOne", id);
	}

	@Override
	public CommunityVO selectOne(int id) {
		return getSqlSession().selectOne("CommunityDao.selectOne", id);
	}

	@Override
	public boolean isLast(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int incrementViewCount(int id) {
		return getSqlSession().update("CommunityDao.incrementViewCount", id);
	}

	@Override
	public int incrementRecoomendCount(int id) {
		return getSqlSession().update("CommunityDao.incrementRecoomendCount", id);
	}

	@Override
	public int deleteMyCommunities(int id) {
		return getSqlSession().delete("CommunityDao.deleteMemberCommunity", id);
	}

	@Override
	public int updateCommunity(CommunityVO communityVO) {
		return getSqlSession().update("CommunityDao.updateCommunity", communityVO);
	}

	@Override
	public int selectMyCommunitiesCount(int userId) {
		return getSqlSession().selectOne("CommunityDao.selectMyCommunitiesCount", userId);
	}

	@Override
	public List<CommunityVO> selectMyCommunities(int userId) {
		return getSqlSession().selectList("CommunityDao.selectMyCommunities", userId);
	}

	@Override
	public int deleteCommunities(List<Integer> ids, int userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids);
		params.put("userId", userId);
		return getSqlSession().delete("CommunityDao.deleteCommunities", params);
	}

}

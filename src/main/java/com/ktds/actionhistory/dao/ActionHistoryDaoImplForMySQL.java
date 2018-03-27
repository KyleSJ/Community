package com.ktds.actionhistory.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.actionhistory.vo.ActionHistoryVO;

public class ActionHistoryDaoImplForMySQL extends SqlSessionDaoSupport implements ActionHistoryDao {

	@Override
	public int insertAvctionHistory(ActionHistoryVO actionHistoryVO) {
		return getSqlSession().insert("actionHistoryDao.insertAvctionHistory", actionHistoryVO);
	}

 
}

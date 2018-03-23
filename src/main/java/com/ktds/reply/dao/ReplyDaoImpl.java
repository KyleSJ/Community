package com.ktds.reply.dao;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.ktds.reply.vo.ReplyVO;

public class ReplyDaoImpl extends SqlSessionDaoSupport implements ReplyDao {

   @Override
   public List<ReplyVO> selectAllReplies(int communityId) {
      return getSqlSession().selectList("ReplyDao.selectAllReplies", communityId);
   }

   @Override
   public ReplyVO selectOneReply(int replyId) {
      return getSqlSession().selectOne("ReplyDao.selectOneReply", replyId);
   }
   
   @Override
   public int insertReply(ReplyVO replyVO) {
      int sequence = getSqlSession().selectOne("ReplyDao.nextValue");
      replyVO.setId(sequence);
      return getSqlSession().insert("ReplyDao.insertReply", replyVO);
   }


}

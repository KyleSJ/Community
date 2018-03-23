package com.ktds.reply.dao;

import java.util.List;

import com.ktds.reply.vo.ReplyVO;

public interface ReplyDao {
   
   public List<ReplyVO> selectAllReplies(int communityId);
   
   public ReplyVO selectOneReply(int replyId);
   
   public int insertReply(ReplyVO replyVO);
   
}
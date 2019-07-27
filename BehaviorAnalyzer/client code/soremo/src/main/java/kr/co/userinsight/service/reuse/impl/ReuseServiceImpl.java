package kr.co.userinsight.service.reuse.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.model.keyword.UserHistoryKeyword;
import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.quality.RDQualityFactor;
import kr.co.userinsight.model.reuse.ReuseComment;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.impl.EntityServiceImpl;
import kr.co.userinsight.service.reuse.ReuseService;

@Service
@Transactional
public class ReuseServiceImpl extends EntityServiceImpl implements ReuseService{

	private static final long serialVersionUID = 13L;

	@Override
	public List<ReuseComment> getReuseCommentList(String rdId, String rdVersion){

		BaseRestrictions<ReuseComment> restrictions = new BaseRestrictions<>(ReuseComment.class);
		restrictions.addCriterion("reuseId", Integer.parseInt(rdId));
		restrictions.addCriterion("reuseIdVersion", Double.parseDouble(rdVersion));
        return list(restrictions) ;
	}

	@Override
	public void setReuseComment(String rdId, String rdVersion, String comment, String userId){

		ReuseComment reuseComment = new ReuseComment();
		System.out.println("rdId: " + reuseComment.getReuseId() + " comment: " + reuseComment.getComment() + " userId: " + reuseComment.getUserId());
		reuseComment.setReuseId(Integer.parseInt(rdId));
		reuseComment.setReuseIdVersion(Double.parseDouble(rdVersion));
		reuseComment.setComment(comment);
    	reuseComment.setUserId(userId);
    	saveOrUpdateReuseComment(reuseComment);
	}

	@Override
	public ReuseComment saveOrUpdateReuseComment(ReuseComment reuseComment){
		saveOrUpdate(reuseComment);
		return reuseComment;
	}
}

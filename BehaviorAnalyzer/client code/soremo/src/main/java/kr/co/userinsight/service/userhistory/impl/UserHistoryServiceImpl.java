package kr.co.userinsight.service.userhistory.impl;

import kr.ac.yonsei.RDMLModel;
import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.model.keyword.UserHistoryKeyword;
import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.reuse.ReuseHistory;
import kr.co.userinsight.model.reuse.ReuseUserHistory;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.BaseEntityService;
import kr.co.userinsight.service.EntityService;
import kr.co.userinsight.service.impl.BaseEntityServiceImpl;
import kr.co.userinsight.service.impl.EntityServiceImpl;
import kr.co.userinsight.service.keyword.KeywordService;
import kr.co.userinsight.service.userhistory.UserHistoryService;
import kr.co.userinsight.tools.vo.DateUtils;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserHistoryServiceImpl extends EntityServiceImpl implements UserHistoryService{


    @Autowired
    private KeywordService keywordService;


    @Override
    public UserHistory saveOrUpdateUserHistory(UserHistory userHistory) {
        saveOrUpdate(userHistory);
        return userHistory;

    }

    @Override
    public Boolean saveKeyWord(String base_keyword) {

        UserHistory userHistory =  getByField(UserHistory.class, "userId", 1L);

            if( userHistory == null ){
                userHistory = new UserHistory();
                userHistory.setUserId(1L);
                saveOrUpdate(userHistory);
            }


         Keyword rd_id = getByField(Keyword.class, "desc", base_keyword);
                if(rd_id == null){
                    Keyword keyword = new Keyword();
                    keyword.setDescEn(base_keyword);
                    keyword.setDesc(base_keyword);
                    keywordService.saveOrUpdateKeyword(keyword);

                    UserHistoryKeyword userHistoryKeyword = new UserHistoryKeyword();
                    userHistoryKeyword.setUserHistory(userHistory);
                    userHistoryKeyword.setKeyword(keyword);

                    userHistoryKeyword.setHeaderUsedCount(0L);
                    userHistoryKeyword.setImageUsedCount(0L);
                    userHistoryKeyword.setSourceUsedCount(0L);
                    userHistoryKeyword.setTextUsedCount(0L);

                    saveOrUpdate(userHistoryKeyword);
                }

        return false;
    }

    @Override
    public void updateScroing(String base, String based_keyword) {

        BaseRestrictions<UserHistoryKeyword> restrictions = new BaseRestrictions<>(UserHistoryKeyword.class);
        UserHistory userHistory =  getByField(UserHistory.class, "userId", 1L);
        restrictions.addCriterion("userHistory.id", userHistory.getId());
        //restrictions.addAssociation("userHistoryKeywords", "tbluh", JoinType.INNER_JOIN);
        Keyword keyword = getByField(Keyword.class, "desc", based_keyword);
        restrictions.addCriterion("keyword.id", keyword.getId());

        UserHistoryKeyword userHistoryKeyword = list(restrictions).get(0);


        if(base.equalsIgnoreCase(VisualizationType.HEADER.getCode())){
            userHistoryKeyword.setHeaderUsedCount(userHistoryKeyword.getHeaderUsedCount() + 1 );
        }else if(base.equalsIgnoreCase(VisualizationType.CODE.getCode())){
            userHistoryKeyword.setSourceUsedCount(userHistoryKeyword.getSourceUsedCount() + 1 );
        }else if(base.equalsIgnoreCase(VisualizationType.IMAGE.getCode())){
            userHistoryKeyword.setImageUsedCount(userHistoryKeyword.getImageUsedCount() + 1 );
        }else if(base.equalsIgnoreCase(VisualizationType.TEXT.getCode())){
            userHistoryKeyword.setTextUsedCount(userHistoryKeyword.getTextUsedCount() + 1 );
        }

        saveOrUpdate(userHistoryKeyword);

    }

    @Override
    public ReuseHistory saveReuserHistory(String rd_type, String rd_id) {

        UserHistory userHistory =  getByField(UserHistory.class, "userId", 1L);

        if( userHistory == null ){
            userHistory = new UserHistory();
            userHistory.setUserId(1L);
            saveOrUpdate(userHistory);
        }

        ReuseHistory reuseHistory = getByField(ReuseHistory.class, "rd_id", rd_id);


        if(reuseHistory == null ){
            reuseHistory = new ReuseHistory();
            reuseHistory.setNumberOfReuse(1L);
            reuseHistory.setRd_id(rd_id);
            reuseHistory.setReuseDate(DateUtils.today());

            saveOrUpdate(reuseHistory);

            ReuseUserHistory reuseUserHistory = new ReuseUserHistory();
            reuseUserHistory.setUserHistory(userHistory);
            reuseUserHistory.setReuseHistory(reuseHistory);

            reuseUserHistory.setHeaderUsedCount(0L);
            reuseUserHistory.setImageUsedCount(0L);
            reuseUserHistory.setSourceUsedCount(0L);
            reuseUserHistory.setTextUsedCount(0L);

            updateReuseScore(rd_type, reuseUserHistory);

            saveOrUpdate(reuseUserHistory);
        }else {
            //update scoring
            BaseRestrictions<ReuseUserHistory> restrictions = new BaseRestrictions<>(ReuseUserHistory.class);
            restrictions.addCriterion("userHistory.id", userHistory.getId());
            restrictions.addCriterion("reuseHistory.id", reuseHistory.getId());

            ReuseUserHistory reuseUserHistory = list(restrictions).get(0);
            updateReuseScore(rd_type, reuseUserHistory);

            // update reuse count
            reuseHistory.setNumberOfReuse(reuseHistory.getNumberOfReuse() + 1L );

            saveOrUpdate(reuseUserHistory);
        }

        return reuseHistory;
    }


    private void updateReuseScore (String rd_type, ReuseUserHistory reuseUserHistory){
        if(rd_type.equalsIgnoreCase(VisualizationType.HEADER.getCode())){
            reuseUserHistory.setHeaderUsedCount(reuseUserHistory.getHeaderUsedCount() + 1 );
        }else if(rd_type.equalsIgnoreCase(VisualizationType.CODE.getCode())){
            reuseUserHistory.setSourceUsedCount(reuseUserHistory.getSourceUsedCount() + 1 );
        }else if(rd_type.equalsIgnoreCase(VisualizationType.IMAGE.getCode())){
            reuseUserHistory.setImageUsedCount(reuseUserHistory.getImageUsedCount() + 1 );
        }else if(rd_type.equalsIgnoreCase(VisualizationType.TEXT.getCode())){
            reuseUserHistory.setTextUsedCount(reuseUserHistory.getTextUsedCount() + 1 );
        }
    }

}

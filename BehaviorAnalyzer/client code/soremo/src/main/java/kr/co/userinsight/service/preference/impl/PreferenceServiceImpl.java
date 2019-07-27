package kr.co.userinsight.service.preference.impl;

import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.model.keyword.UserHistoryKeyword;
import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.reuse.ReuseHistory;
import kr.co.userinsight.model.reuse.ReuseUserHistory;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.scoring.WeightModel;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.EntityService;
import kr.co.userinsight.service.impl.EntityServiceImpl;
import kr.co.userinsight.service.preference.PreferenceService;
import kr.co.userinsight.tools.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("preferenceService")
@Transactional
public class PreferenceServiceImpl extends EntityServiceImpl implements PreferenceService{



    @Override
    public List<WeightModel> getVisualizationType(Long userId, String basedKeyword) {

        List<WeightModel> basedKeywordPreference = getBasedKeywordPreference(userId, basedKeyword);
        List<WeightModel> reusePreference = getReusePreference(userId);

        List<WeightModel> weightModels = new ArrayList<>();
        weightModels.addAll(basedKeywordPreference);
        weightModels.addAll(reusePreference);

        Long headerCount = 0L;
        Long imageCount = 0L ;
        Long sourceCount = 0L ;
        Long textCount = 0L ;

        if(weightModels !=null && !weightModels.isEmpty()){
            for (WeightModel weightModel :
                    weightModels) {
                if (weightModel.getVisualizationType().equals(VisualizationType.HEADER)){
                    headerCount += weightModel.getScore();
                }else if(weightModel.getVisualizationType().equals(VisualizationType.IMAGE)){
                    imageCount += weightModel.getScore();
                }else if(weightModel.getVisualizationType().equals(VisualizationType.CODE)){
                    sourceCount += weightModel.getScore();
                }else if(weightModel.getVisualizationType().equals(VisualizationType.TEXT)){
                    textCount += weightModel.getScore();
                }
            }

        }
        weightModels.clear();
        WeightModel header = new WeightModel();
        header.setScore(headerCount);
        header.setVisualizationType(VisualizationType.HEADER);

        WeightModel image = new WeightModel();
        image.setScore(imageCount);
        image.setVisualizationType(VisualizationType.IMAGE);

        WeightModel source = new WeightModel();
        source.setScore(sourceCount);
        source.setVisualizationType(VisualizationType.CODE);

        WeightModel text = new WeightModel();
        text.setScore(textCount);
        text.setVisualizationType(VisualizationType.TEXT);

        weightModels.add(header);
        weightModels.add(image);
        weightModels.add(source);
        weightModels.add(text);
      return weightModels;
    }


    private  List<WeightModel> getBasedKeywordPreference(Long userId,String based_keyword){
        List<WeightModel> weightModels = new ArrayList<>();
        BaseRestrictions<UserHistoryKeyword> restrictions = new BaseRestrictions<>(UserHistoryKeyword.class);
        UserHistory userHistory =  getByField(UserHistory.class, "userId", userId);
        restrictions.addCriterion("userHistory.id", userHistory.getId());
        Keyword keyword = getByField(Keyword.class, "desc", based_keyword);
        restrictions.addCriterion("keyword.id", keyword.getId());

        UserHistoryKeyword userHistoryKeyword = list(restrictions).get(0);

        WeightModel header = new WeightModel();
        header.setScore(userHistoryKeyword.getHeaderUsedCount() * Weight.KEYWORD.getValue());
        header.setVisualizationType(VisualizationType.HEADER);

        WeightModel image = new WeightModel();
        image.setScore(userHistoryKeyword.getImageUsedCount() * Weight.KEYWORD.getValue() );
        image.setVisualizationType(VisualizationType.IMAGE);

        WeightModel source = new WeightModel();
        source.setScore(userHistoryKeyword.getSourceUsedCount() * Weight.KEYWORD.getValue());
        source.setVisualizationType(VisualizationType.CODE);

        WeightModel text = new WeightModel();
        text.setScore(userHistoryKeyword.getTextUsedCount() * Weight.KEYWORD.getValue());
        text.setVisualizationType(VisualizationType.TEXT);

        weightModels.add(header);
        weightModels.add(image);
        weightModels.add(source);
        weightModels.add(text);
        return weightModels;
    }


    private  List<WeightModel> getReusePreference(Long userId){

        List<WeightModel> weightModels = new ArrayList<>();

        BaseRestrictions<ReuseUserHistory> restrictions = new BaseRestrictions<>(ReuseUserHistory.class);
        UserHistory userHistory =  getByField(UserHistory.class, "userId", userId);
        restrictions.addCriterion("userHistory.id", userHistory.getId());

        List<ReuseUserHistory> reuseUserHistories = list(restrictions);

        Long headerCount = 0L;
        Long imageCount = 0L ;
        Long sourceCount = 0L ;
        Long textCount = 0L ;

        if( reuseUserHistories != null && !reuseUserHistories.isEmpty()){
            for (ReuseUserHistory reuseUserHistory :
                    reuseUserHistories) {
                headerCount += reuseUserHistory.getHeaderUsedCount();
                imageCount += reuseUserHistory.getImageUsedCount();
                sourceCount += reuseUserHistory.getSourceUsedCount();
                textCount += reuseUserHistory.getTextUsedCount();
            }

        }

        WeightModel header = new WeightModel();
        header.setScore(headerCount * Weight.REUSE_COUNT.getValue() );
        header.setVisualizationType(VisualizationType.HEADER);

        WeightModel image = new WeightModel();
        image.setScore(imageCount * Weight.REUSE_COUNT.getValue());
        image.setVisualizationType(VisualizationType.IMAGE);

        WeightModel source = new WeightModel();
        source.setScore(sourceCount * Weight.REUSE_COUNT.getValue());
        source.setVisualizationType(VisualizationType.CODE);

        WeightModel text = new WeightModel();
        text.setScore(textCount * Weight.REUSE_COUNT.getValue());
        text.setVisualizationType(VisualizationType.TEXT);

        weightModels.add(header);
        weightModels.add(image);
        weightModels.add(source);
        weightModels.add(text);
        return weightModels;
    }

    @Override
    public List<WeightModel> getVisualizationScoring(Long userId, String based_keyword){
        List<WeightModel> weightModels = new ArrayList<>();

        BaseRestrictions<ReuseUserHistory> restrictions = new BaseRestrictions<>(ReuseUserHistory.class);
        UserHistory userHistory =  getByField(UserHistory.class, "userId", userId);
        restrictions.addCriterion("userHistory.id", userHistory.getId());

        List<ReuseUserHistory> reuseUserHistories = list(restrictions);

        Long headerCount = 0L;
        Long imageCount = 0L ;
        Long sourceCount = 0L ;
        Long textCount = 0L ;

        if( reuseUserHistories != null && !reuseUserHistories.isEmpty()){
            for (ReuseUserHistory reuseUserHistory :
                    reuseUserHistories) {
                headerCount += reuseUserHistory.getHeaderUsedCount();
                imageCount += reuseUserHistory.getImageUsedCount();
                sourceCount += reuseUserHistory.getSourceUsedCount();
                textCount += reuseUserHistory.getTextUsedCount();
            }

        }

        BaseRestrictions<UserHistoryKeyword> restrictions1 = new BaseRestrictions<>(UserHistoryKeyword.class);
        restrictions.addCriterion("userHistory.id", userHistory.getId());
        Keyword keyword = getByField(Keyword.class, "desc", based_keyword);
        restrictions.addCriterion("keyword.id", keyword.getId());

        UserHistoryKeyword userHistoryKeyword = list(restrictions1).get(0);

        headerCount += userHistoryKeyword.getHeaderUsedCount();
        imageCount += userHistoryKeyword.getImageUsedCount();
        sourceCount += userHistoryKeyword.getSourceUsedCount();
        textCount += userHistoryKeyword.getTextUsedCount();

        WeightModel header = new WeightModel();
        header.setScore(headerCount);
        header.setVisualizationType(VisualizationType.HEADER);

        WeightModel image = new WeightModel();
        image.setScore(imageCount);
        image.setVisualizationType(VisualizationType.IMAGE);

        WeightModel source = new WeightModel();
        source.setScore(sourceCount);
        source.setVisualizationType(VisualizationType.CODE);

        WeightModel text = new WeightModel();
        text.setScore(textCount);
        text.setVisualizationType(VisualizationType.TEXT);

        weightModels.add(header);
        weightModels.add(image);
        weightModels.add(source);
        weightModels.add(text);
        return weightModels;
    }


//    private WeightModel calculateScore(List<Scoring> scorings){
//
//        long weightedScoreHeader =0 ;
//        long weightedScoreText =0 ;
//        long weightedScoreImage =0 ;
//        long weightedScoreSource =0 ;
//        WeightModel weightModel= new WeightModel();
//
//
//        Scoring scoring = scorings.get(0);
//        weightedScoreHeader = Weight.IMPORT_COUNT.getValue() * scoring.getHeaderUsedCount();
//        weightedScoreText = Weight.IMPORT_COUNT.getValue() * scoring.getTextUsedCount();
//        weightedScoreImage = Weight.IMPORT_COUNT.getValue() * scoring.getImageUsedCount();
//        weightedScoreSource = Weight.IMPORT_COUNT.getValue() * scoring.getSourceUsedCount();
//
//        scoring = scorings.get(1);
//        weightedScoreHeader += Weight.KEYWORD.getValue() * scoring.getHeaderUsedCount();
//        weightedScoreText += Weight.KEYWORD.getValue() * scoring.getTextUsedCount();
//        weightedScoreImage += Weight.KEYWORD.getValue() * scoring.getImageUsedCount();
//        weightedScoreSource += Weight.KEYWORD.getValue() * scoring.getSourceUsedCount();
//
//
//        scoring = scorings.get(2);
//        weightedScoreHeader += Weight.REUSE_COUNT.getValue() * scoring.getHeaderUsedCount();
//        weightedScoreText += Weight.REUSE_COUNT.getValue() * scoring.getTextUsedCount();
//        weightedScoreImage += Weight.REUSE_COUNT.getValue() * scoring.getImageUsedCount();
//        weightedScoreSource += Weight.REUSE_COUNT.getValue() * scoring.getSourceUsedCount();
//
//
//        scoring = scorings.get(3);
//        weightedScoreHeader += Weight.REUSE_DATE.getValue() * scoring.getHeaderUsedCount();
//        weightedScoreText += Weight.REUSE_DATE.getValue() * scoring.getTextUsedCount();
//        weightedScoreImage += Weight.REUSE_DATE.getValue() * scoring.getImageUsedCount();
//        weightedScoreSource += Weight.REUSE_DATE.getValue() * scoring.getSourceUsedCount();
//
//        scoring = scorings.get(4);
//        weightedScoreHeader += Weight.REUSE_ID.getValue() * scoring.getHeaderUsedCount();
//        weightedScoreText += Weight.REUSE_ID.getValue() * scoring.getTextUsedCount();
//        weightedScoreImage += Weight.REUSE_ID.getValue() * scoring.getImageUsedCount();
//        weightedScoreSource += Weight.REUSE_ID.getValue() * scoring.getSourceUsedCount();
//
//
//        long highestWeight = weightedScoreHeader;
//        weightModel.setScore(weightedScoreHeader);
//        weightModel.setVisualizationType(VisualizationType.HEADER);
//
//        if(weightedScoreImage > highestWeight){
//            highestWeight = weightedScoreImage;
//            weightModel.setScore(weightedScoreImage);
//            weightModel.setVisualizationType(VisualizationType.IMAGE);
//        }
//
//        if(weightedScoreSource > highestWeight){
//            highestWeight = weightedScoreSource;
//            weightModel.setScore(weightedScoreSource);
//            weightModel.setVisualizationType(VisualizationType.CODE);
//        }
//
//        if ( weightedScoreText > highestWeight){
//            highestWeight = weightedScoreText;
//            weightModel.setScore(weightedScoreText);
//            weightModel.setVisualizationType(VisualizationType.TEXT);
//        }
//
//
//        return weightModel;
//    }


    @Override
    public WeightModel max(WeightModel ...n){
        int i = 0;
        WeightModel max = n[i];

        while (++i < n.length)
            if (n[i].getScore() > max.getScore())
                max = n[i];

        return max;
    }

}

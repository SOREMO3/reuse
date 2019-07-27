package kr.co.userinsight.service.userhistory;

import kr.ac.yonsei.RDMLModel;
import kr.co.userinsight.model.reuse.ReuseHistory;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.EntityService;

import java.util.List;

public interface UserHistoryService  extends EntityService{

    UserHistory saveOrUpdateUserHistory(UserHistory userHistory);
    Boolean saveKeyWord(String base_keyword);
    void updateScroing (String base,String based_keyword);

    ReuseHistory saveReuserHistory(String rd_type, String rd_id);

}

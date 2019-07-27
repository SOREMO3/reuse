package kr.co.userinsight.service.preference;

import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.scoring.WeightModel;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.EntityService;

import java.util.List;

public interface PreferenceService extends EntityService{

    List<WeightModel> getVisualizationType(Long userId, String baseKeyword);
    public List<WeightModel> getVisualizationScoring(Long userId, String based_keyword);
    public WeightModel max(WeightModel ...n);

}

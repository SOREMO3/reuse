package kr.co.userinsight.model.scoring;

import kr.co.userinsight.model.preference.VisualizationType;

public class WeightModel {

    private Long score;
    private VisualizationType visualizationType;

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public VisualizationType getVisualizationType() {
        return visualizationType;
    }

    public void setVisualizationType(VisualizationType visualizationType) {
        this.visualizationType = visualizationType;
    }
}

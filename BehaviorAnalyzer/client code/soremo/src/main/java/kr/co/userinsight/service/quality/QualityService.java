package kr.co.userinsight.service.quality;

import java.util.ArrayList;

import kr.co.userinsight.model.quality.RDQualityFactor;
import kr.co.userinsight.service.EntityService;

public interface QualityService extends EntityService{

	RDQualityFactor saveOrUpdateRDQualityFactor(RDQualityFactor rdQualityFactor);
	Integer getRDQualityFactors(String rdId, String rdVersion); 
	void setReuseCounter(String _rdId, String _rdVersion);
}

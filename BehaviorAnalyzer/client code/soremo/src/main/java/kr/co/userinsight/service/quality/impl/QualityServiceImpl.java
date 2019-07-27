package kr.co.userinsight.service.quality.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.userinsight.dao.hql.BaseRestrictions;
import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.model.quality.RDQualityFactor;
import kr.co.userinsight.model.reuse.ReuseComment;
import kr.co.userinsight.service.EntityService;
import kr.co.userinsight.service.impl.EntityServiceImpl;
import kr.co.userinsight.service.quality.QualityService;

@Service
@Transactional
public class QualityServiceImpl extends EntityServiceImpl implements QualityService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public RDQualityFactor saveOrUpdateRDQualityFactor(RDQualityFactor rdQualityFactor) {

		saveOrUpdate(rdQualityFactor);

		return rdQualityFactor;
	}
	@Override
	public void setReuseCounter(String _rdId, String _rdVersion){
		
		BaseRestrictions<RDQualityFactor> restrictions = new BaseRestrictions<>(RDQualityFactor.class);
		restrictions.addCriterion("rdId", Integer.parseInt(_rdId));
		restrictions.addCriterion("rdVersion",Double.parseDouble(_rdVersion));
		
		RDQualityFactor rdQualityFactor = !(list(restrictions).isEmpty())?list(restrictions).get(0):null; 
		
		if(rdQualityFactor == null){
			rdQualityFactor = new RDQualityFactor();
			rdQualityFactor.setRdId(Integer.parseInt(_rdId));
			rdQualityFactor.setRdVersion(Double.parseDouble(_rdVersion));
			rdQualityFactor.setRdReuseCount(1);
			saveOrUpdate(rdQualityFactor);
		}
		else{
			Integer reuseCount = (Integer)(rdQualityFactor.getRdReuseCount()+1);
			rdQualityFactor.setRdReuseCount(reuseCount);
			System.out.println("rd reuse count: " +rdQualityFactor.getRdReuseCount());
			saveOrUpdateRDQualityFactor(rdQualityFactor);
		}
		
	}
	@Override
	public Integer getRDQualityFactors(String rdId, String rdVersion) { // reuse count¸¸ °¡Á®¿È

		BaseRestrictions<RDQualityFactor> restrictions = new BaseRestrictions<>(RDQualityFactor.class);
		restrictions.addCriterion("rdId", Integer.parseInt(rdId));
		restrictions.addCriterion("rdVersion",Double.parseDouble(rdVersion));
		
		RDQualityFactor rdQualityFactor = !(list(restrictions).isEmpty())?list(restrictions).get(0):null;
		
		return rdQualityFactor.getRdReuseCount();
	}

}

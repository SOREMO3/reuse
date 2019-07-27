package kr.co.userinsight;

import kr.ac.yonsei.RDMLModel;
import kr.co.userinsight.model.preference.Preference;
import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.preference.PreferenceService;
import kr.co.userinsight.service.quality.QualityService;
import kr.co.userinsight.service.reuse.ReuseService;
import kr.co.userinsight.service.userhistory.UserHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:application-main-context.xml")
public class TestService {

    @Autowired
    private UserHistoryService entityService;

    @Autowired
    private PreferenceService preferenceService;
    
    @Autowired
    private QualityService qualityService;
    @Autowired
    private ReuseService reuseService;
    
    @Test
    public void testQualityService(){
    	
		qualityService.setReuseCounter("2203", "5.0");
    }
    
    @Test
    public void testReuseService(){
    	reuseService.setReuseComment("2203", "5.0", "asdf", "ssdfas");
    }
    
    @Test
    public void testInsert(){
        Preference preference = new Preference();

        preference.setUserId(2L);
        preference.setVisualizationType(VisualizationType.HEADER);
        //preference.setRd_id(2L);
        entityService.saveOrUpdate(preference);

    }

    @Test
    public void testUpdate(){
        Preference preference = entityService.getById(Preference.class, 1L);

        preference.setUserId(1L);
        preference.setVisualizationType(VisualizationType.IMAGE);
       // preference.setRd_id(1L);
        entityService.saveOrUpdate(preference);
    }


    @Test
    public void testDelete(){
        List<RDMLModel> tmpList = new ArrayList<RDMLModel>();
        RDMLModel rdmlModel = new RDMLModel();
        rdmlModel.setRDID("01");
        rdmlModel.setTitle("Software Testing");

        tmpList.add(rdmlModel);
    }


    @Test
    public void testUpdateScoring(){


        entityService.updateScroing("source", "01");
    }


    @Test
    public void testGetPreferenceUser(){
        preferenceService.getVisualizationType(1L, "RDMeta");

    }

    @Test
    public void testGetPreferenceScoringUser(){
        preferenceService.getVisualizationScoring(1L, "test");

    }

    @Test
    public void testSaveOrUpdateReuseHistory(){
        entityService.saveReuserHistory("source", "3");
    }


}

package kr.co.userinsight.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.userinsight.model.TestingActivity;
import kr.co.userinsight.model.TestingTask;

/**
 * 
 * @author yungu
 * @category 2세부
 */
@Controller
public class TestingController {

	@RequestMapping(value = "/testing/plan/customization", method = RequestMethod.GET)
	public String testingPlanCustomization(Model model) {
		return "team2/test-plan/testing-customization";
	}
	
	@RequestMapping(value = "/testing/get/task/details/{idx}", method = RequestMethod.GET)
	public @ResponseBody List<TestingActivity> testingGetTaskDetails(Model model, @PathVariable("idx") int idx) {
		List<TestingActivity> resultSet = new ArrayList<TestingActivity>();
		
		TestingActivity testingActivity = new TestingActivity();
		testingActivity.setActivityName("소프트웨어테스트");
		
		List<TestingTask> taskList = new ArrayList<TestingTask>();
		taskList.add(new TestingTask("Identify Feature Set", "blabla"));
		taskList.add(new TestingTask("Derive Test Coverage", "blabla"));
		
		testingActivity.setTaskList(taskList);
		
		resultSet.add(testingActivity);
		resultSet.add(testingActivity);
		resultSet.add(testingActivity);
		
		
		return resultSet;
	}
	
	@RequestMapping(value = "/testing/plan/range", method = RequestMethod.GET)
	public String testingPlanRange(Model model) {
		
		
		return "team2/test-plan/testing-range";
	}
	
	
	/**
	 * 문서 산출물 테스트: 메인 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/rd/", method = RequestMethod.GET)
	public String testingRD(Model model) {
		
		
		return "team2/rd-testing/testing-rd-main";
	}
	
	
	/**
	 * 문서 산출물 테스트: 품질 평가 범위 선택
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/rd/qa/range", method = RequestMethod.GET)
	public String testingRDQaRange(Model model) {
		
		return "team2/rd-testing/testing-qa-range";
	}
	
	/**
	 * 문서 산출물 테스트: SLTS 확인
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/rd/slts", method = RequestMethod.GET)
	public String testingRDSlts(Model model) {
		
		return "team2/rd-testing/testing-slts-view";
	}
	
	/**
	 * 문서 산출물 테스트: 결과 확인
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/rd/result", method = RequestMethod.GET)
	public String testingRDResult(Model model) {
		
		return "team2/rd-testing/testing-rd-result";
	}
	
	/**
	 * SW 테스트: 메인 화면
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/sw")
	public String testingSW(Model model) {
		return "team2/sw-testing/testing-sw-main";
	}
	
	/**
	 * SW 테스트: SLTS 확인 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/sw/slts")
	public String testingSWSlts(Model model) {
		return "team2/sw-testing/testing-slts-view";
	}
	
	/**
	 * SW 테스트: 결과 확인
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/sw/result")
	public String testingSWResult(Model model) {
		return "team2/sw-testing/testing-sw-result";
	}
	
	/**
	 * SW 테스트: 결과 입력
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/testing/sw/result/edit")
	public String testingSWResultEdit(Model model) {
		return "team2/sw-testing/testing-sw-result-edit";
	}
}

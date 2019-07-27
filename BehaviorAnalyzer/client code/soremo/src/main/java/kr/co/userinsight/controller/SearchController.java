package kr.co.userinsight.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import kr.co.userinsight.model.keyword.Keyword;
import kr.co.userinsight.model.keyword.UserHistoryKeyword;
import kr.co.userinsight.model.preference.VisualizationType;
import kr.co.userinsight.model.scoring.Scoring;
import kr.co.userinsight.model.scoring.WeightModel;
import kr.co.userinsight.model.userhistory.UserHistory;
import kr.co.userinsight.service.keyword.KeywordService;
import kr.co.userinsight.service.preference.PreferenceService;
import kr.co.userinsight.service.userhistory.UserHistoryService;

import org.apache.bcel.generic.GOTO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.yonsei.JARVIS;
import kr.ac.yonsei.RDMLModel;
import kr.co.userinsight.controller.ontology.ontologyConnector;
import kr.co.userinsight.model.RDStructureModel;
import kr.co.userinsight.model.SearchDataModel;
import kr.co.userinsight.model.SimilarityModel;
import kr.co.userinsight.model.reuse.ReuseComment;
import kr.co.userinsight.service.quality.QualityService;
import kr.co.userinsight.service.reuse.ReuseService;

/**
 * @category 3세부
 */
@Controller
public class SearchController {

	@Autowired
	private UserHistoryService userHistoryService;

	@Autowired
	private PreferenceService preferenceService;

	private boolean isFirstTouch = true;

	@Autowired
	private QualityService qualityService;

	@Autowired
	private ReuseService reuseService;

	List<SimilarityModel> relatumList;
	private List<RDMLModel> passList;

	private List<WeightModel> weightModels;

	private XMLParser xmlParser;
	private List<RDMLModel> resultList; // inter-document에 사용하는 검색 결과 rd들

	private int interIndex; // 검색 내 inter-document를 하기 위한 인덱스
	private Deque<String> predecessorStack; // predecessor할 때 rd 이력이 쌓이는 스택
	private Deque<String> successorStack; // successor할 때 rd 이력이 쌓이는 스택
	private String searchBaseRDVersion;
	private String searchBaseRD; // inter-document 탐색의 원점으로 돌아오기 위한 RD 변수
	private static String searchState; // 탐색의 상태 All or retrieved document
	private String parentFlag; //
	private String searchOp2; // searchOp을 받기 위한 변수
	private String RDVersionVariable = null; // All -> All할때 predecessor에서 사용하는 변수

	private List<String> rdParentList = new ArrayList<String>();
	private List<String> rdVersionList = new ArrayList<String>();

	private int versionIndex = -1;
	private String backUpRDVersionVariable = null; // 창을 닫고 다시 열 때 본래의 정보를 대입하기 위한 변수

	public SearchController() {
		relatumList = new ArrayList<SimilarityModel>();
		passList = new ArrayList<RDMLModel>();
		xmlParser = new XMLParser();

	}

	/**
	 * RD 寃��깋 View
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	@RequestMapping(value = "/rd/search", method = RequestMethod.GET)
	public String rdSearch(Model model,
			@RequestParam(required = false, defaultValue = "", value = "keyword") String keyword) {
		SearchDataModel searchDataModel = new SearchDataModel();
		searchDataModel.setBase_keyword(keyword);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(System.currentTimeMillis());

		searchDataModel.setExt_rd_start_date(sdf.format(date));
		searchDataModel.setExt_rd_end_date(sdf.format(date));
		model.addAttribute("searchDataModel", searchDataModel);
		
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, -1);
		Date before1Day = cal.getTime();
		
		cal.add(Calendar.DATE, -6);
		Date before1Week = cal.getTime();
		
		cal.add(Calendar.DATE, -23);
		Date before1Month = cal.getTime();
		
		cal.add(Calendar.DATE, -52);
		Date before6Month = cal.getTime();

		cal.add(Calendar.DATE, -282);
		Date before1Year = cal.getTime();
		
		model.addAttribute("before1Day", sdf.format(before1Day));
		model.addAttribute("before1Week", sdf.format(before1Week));
		model.addAttribute("before1Month", sdf.format(before1Month));
		model.addAttribute("before6Month", sdf.format(before6Month));
		model.addAttribute("before1Year", sdf.format(before1Year));

		List<String> basicRDTypeList = new ArrayList<String>();
		basicRDTypeList.add("Header");
		basicRDTypeList.add("Text");
		basicRDTypeList.add("Table");
		basicRDTypeList.add("Figure");
		basicRDTypeList.add("Object");
		basicRDTypeList.add("Creator");
		basicRDTypeList.add("Modifier");
		basicRDTypeList.add("Project");
		model.addAttribute("basicRDTypeList", basicRDTypeList);

		List<String> basicSDTypeList = new ArrayList<String>();
		basicSDTypeList.add("Project");
		basicSDTypeList.add("File");
		basicSDTypeList.add("Class");
		basicSDTypeList.add("Method");
		basicSDTypeList.add("Feature");
		basicSDTypeList.add("Pattern");
		basicSDTypeList.add("Code clone");
		model.addAttribute("basicSDTypeList", basicSDTypeList);

		model.addAttribute("searchDataModel", searchDataModel);

		// cbnu- history manager
		isFirstTouch = true;

		return "team3/rd-search";
	}

	// cbnu - 2017/12 --->
	/**
	 * Base Keyword�� User ID瑜� �넻�빐 寃��깋�맂 User's Preference瑜� �뙆�븙
	 *
	 * @param base_keyword
	 * @return
	 */
	@RequestMapping(value = "/rd/search/visualization", method = RequestMethod.POST)
	public @ResponseBody String setVisualizationType(String base_keyword) {

		userHistoryService.saveKeyWord(base_keyword);

		String rdBase = "none";
		weightModels = preferenceService.getVisualizationType(1L, base_keyword);
		VisualizationType visualizationType = preferenceService
				.max(weightModels.get(0), weightModels.get(1), weightModels.get(2), weightModels.get(3))
				.getVisualizationType();

		switch (visualizationType) {
		case HEADER:
			rdBase = "header";
			break;
		case IMAGE:
			rdBase = "image";
			break;
		case TEXT:
			rdBase = "text";
			break;
		case CODE:
			rdBase = "source";
			break;
		}

		return rdBase;
	}
	// <---- cbnu - 2017/12

	/**
	 * Base Keyword를 통해 검색된 Extended Keyword를 JSON형으로 반환
	 * 
	 * @param base_keyword
	 * @return
	 */
	@RequestMapping(value = "/rd/search/extended", method = RequestMethod.POST)
	public @ResponseBody List<SimilarityModel> rdSearchExtendedTest(String base_keyword) {
		// List<String> list = new ArrayList<String>();
		relatumList = new ArrayList<SimilarityModel>();
		// ontologyConnector ont = new ontologyConnector();

		relatumList.addAll(ontologyConnector.getInstance().getRelatums(base_keyword));

		// list.add("Future tech");
		// list.add("Future technology");
		// list.add("誘몃옒 湲곗닠");

		// list.add(base_keyword + "1");
		// list.add(base_keyword + "2");
		// list.add(base_keyword + "3");
		// list.add(base_keyword + "4");
		return relatumList;
	}

	/**
	 * RD 寃��깋 寃곌낵 由ъ뒪�듃 View
	 * 
	 * @param model
	 * @param base  寃��깋 諛⑸쾿(�삁: header, image ...)
	 * @param page 議고쉶�븷 �럹�씠吏    踰덊샇 
	 * @param searchDataModel 
	 * @return 
	 * @throws
	 */

	@RequestMapping(value = "/rd/search/result/{base}/{page}")
	public String rdSearchResult(Model model, @PathVariable("base") String base, @PathVariable("page") int page,
			@ModelAttribute("searchDataModel") SearchDataModel searchDataModel) {
		/*
		 * rd/search/extended/' �뿉�꽌 �꽑�깮�맂 �뜲�씠�꽣�뒗 searchDataModel 媛앹껜�뿉 �떞寃⑥엳�쓬, �씠瑜�
		 * �씠�슜�빐 寃��깋寃곌낵 �몴�떆
		 */

		JARVIS.debug("keyword=" + searchDataModel.getBase_keyword() + ", base=" + base);

		model.addAttribute("page", page);
		model.addAttribute("base", base);

		Long headerCount = 0L;
		Long imageCount = 0L;
		Long sourceCount = 0L;
		Long textCount = 0L;

		if (weightModels != null && !weightModels.isEmpty()) {
			for (WeightModel weightModel : weightModels) {
				if (weightModel.getVisualizationType().equals(VisualizationType.HEADER)) {
					headerCount = weightModel.getScore();
				} else if (weightModel.getVisualizationType().equals(VisualizationType.IMAGE)) {
					imageCount = weightModel.getScore();
				} else if (weightModel.getVisualizationType().equals(VisualizationType.CODE)) {
					sourceCount = weightModel.getScore();
				} else if (weightModel.getVisualizationType().equals(VisualizationType.TEXT)) {
					textCount = weightModel.getScore();
				}
			}

		}

		// modified - history manager
		model.addAttribute("headerCount", headerCount);
		model.addAttribute("sourceCount", sourceCount);
		model.addAttribute("imageCount", imageCount);
		model.addAttribute("textCount", textCount);

		// -------------------------------------
		// Temporary code by Yonsei
		// -------------------------------------
		StringBuilder queryResult = new StringBuilder();

		// -------------------------
		// 1. JSON request
		// -------------------------
		try {

			String keywords = searchDataModel.getBase_keyword();

			for (int keyCnt = 0; keyCnt < relatumList.size(); keyCnt++) {
				JARVIS.debug(relatumList.get(keyCnt));
				keywords += ",";
				keywords += relatumList.get(keyCnt);
			}

			String urlParameters = "q=" + keywords + "&searchField=";

			// Modified - 2017-04-20 - Yonsei
			if (!JARVIS.isNullOrEmpty(base) && base.equals("source")) {
				urlParameters += "source";
			} else {
				List<String> typeList = searchDataModel.getExt_rd_type();
				if (typeList != null && typeList.size() > 0) {
					for (int i = 0; i < typeList.size(); i++) {
						urlParameters += typeList.get(i) + ",";
					}
				}
			}

			// TODO section title
			// TODO creator / modifier

			urlParameters += "&startDate=";
			if (searchDataModel.getExt_rd_start_date() != null) {
				urlParameters += searchDataModel.getExt_rd_start_date().replaceAll("[+-:]", "");
			}
			urlParameters += "&endDate=";

			if (searchDataModel.getExt_rd_end_date() != null) {
				urlParameters += searchDataModel.getExt_rd_end_date().replaceAll("[+-:]", "");
			}

			JARVIS.debug("urlParams: " + urlParameters);

			URL url = new URL("http://165.132.221.42/soremo/rd/yonsei/query/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.getOutputStream().write(urlParameters.getBytes("UTF-8"));

			BufferedReader rd = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
			String line;

			while ((line = rd.readLine()) != null) {
				queryResult.append(line);
			}

			// cbnu - history manager

			if (!isFirstTouch) {
				userHistoryService.updateScroing(base, keywords);
			}
			isFirstTouch = false;
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -------------------------
		// 2. JSON parse
		// -------------------------
		TmpObject tmpObj = new TmpObject();
		List<RDMLModel> tmpList = new ArrayList<RDMLModel>();

		// JARVIS.debug("JSON parse");
		
		JARVIS.debug("query result : "+queryResult.toString());

		try {
			JSONParser jsonParser = new JSONParser();
			// System.out.println(queryResult.toString());
			JSONObject jsonObject = (JSONObject) jsonParser.parse(queryResult.toString());
			JSONArray resultArr = (JSONArray) jsonObject.get("result");

			int cutSize = resultArr.size();
			JARVIS.debug("cutSize : " + cutSize);

			if (base.equals("source")) {
				cutSize = cutSize > 3 ? 3 : cutSize;
			}

			for (int i = 0; i < cutSize; i++) {
				JSONObject resultObj = (JSONObject) resultArr.get(i);

				// cbnu - JSONparsing
				// Modified - 2017-04-20 - Yonsei
				if (!JARVIS.isNullOrEmpty(base) && !base.equals("source")) {
					tmpList.add(new JSONParserForResultData().parseResult(resultObj));
				}

				// Yonsei
				/*
				 * tmpObj.addContents(resultObj.get("contents") + "");
				 * tmpObj.addContentsLimited(resultObj.get("contentsLimited") + "");
				 * tmpObj.addTitle(resultObj.get("title") + "");
				 * tmpObj.addAuthor(resultObj.get("author") + "");
				 * tmpObj.addRdID(resultObj.get("rdID") + "");
				 * tmpObj.addRdType(resultObj.get("rdType") + "");
				 * tmpObj.addProjectName(resultObj.get("projectName") + "");
				 * tmpObj.addImageUrl(resultObj.get("imageUrl") + "");
				 * tmpObj.addDate(resultObj.get("date") + "");
				 */
				tmpObj.addOverview(resultObj.get("contents") + "");
				tmpObj.addOverviewSimple(resultObj.get("contentsLimited") + "");
				tmpObj.addName(resultObj.get("title") + "");
				tmpObj.addSDCodeInfo(resultObj.get("sdCodeInfo") + "");
				tmpObj.addSDLimitedCodeInfo(resultObj.get("sdLimitedCodeInfo") + "");
				tmpObj.addSDFileInfo(resultObj.get("file") + "");
				tmpObj.addSDPackageInfo(resultObj.get("sdPackageInfo") + "");
				tmpObj.addSDProjectInfo(resultObj.get("sdProjectInfo") + "");
				// Yonsei //
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (!base.equals("source")) {
			resultList = new ArrayList<RDMLModel>();
			for (RDMLModel tmp : tmpList) {
				JARVIS.debug("rd title : " + tmp.getTitle() + " / rd id : " + tmp.getRDID());
				resultList.add(tmp);
			}

			ArrayList<String> rdParentListTmp = new ArrayList<String>();
			// System.out.println("tar는? " + tarRD);
			for (RDMLModel result : resultList) {
				// yonsei added -> parseTextRDList size check
				ArrayList<RDStructureModel> tempList = xmlParser.parseTextRDList("http://13.124.180.8/rdml/parent/" + result.getRDID());
			//	ArrayList<RDStructureModel> tempList = new ArrayList<>();
				// yonsei added -> parseTextRDList size check
				JARVIS.debug("relate rd size : " + tempList.size());
				if(tempList.size() > 0) {
//					System.out.println(tempList.get(0).getId());
					rdParentListTmp.add(tempList.get(0).getId()); // 검색 결과로 나온 각 rd들의 parent를 구함
				}
			}

			rdParentList = rdParentListTmp.stream().distinct().collect(Collectors.toList());
			predecessorStack = new ArrayDeque<String>();
			successorStack = new ArrayDeque<String>();
			searchBaseRD = null;
			parentFlag = null;
			// interIndex = -1;
			versionIndex = -1;
			searchState = "Internal";
			searchOp2 = "Internal";

		}

		for (int k = 0; k < rdParentList.size(); k++) {
			System.out.println(k + "번째: " + rdParentList.get(k).toString());
		}

		// -------------------------------------
		// Temporary code by Yonsei //
		// -------------------------------------

		// cbnu - paging
		List<RDMLModel> resultList = new ArrayList<RDMLModel>();
		passList = new ArrayList<RDMLModel>();
		// making new array list for transition of data to jsp

		int numPage = 0; // declaration and initialization of the number of page

		numPage = (tmpList.size() / 10); // 10 items a page
		if (tmpList.size() % 10 != 0) {
			numPage += 1; // not exactly divided by 10
		}

		// setting a list of items on the specific page
		for (int i = (page - 1) * 10; i < page * 10; i++) {
			if (i == tmpList.size()) {
				break;
			}
			resultList.add(tmpList.get(i)); // insert items into the variable
			// for jsp
		}
		// cbnu - paging

		// JARVIS.debug("Base: " + base);
 
		// Yonsei
		if (base.equals("source")) {

			// JARVIS.debug(" -> Source!");
			searchDataModel.setOverview(tmpObj.getOverview());
			searchDataModel.setOverviewSimple(tmpObj.getOverviewSimple());
			searchDataModel.setName(tmpObj.getName());
			searchDataModel.setSDCodeInfo(tmpObj.getSDCodeInfo());
			searchDataModel.setSDLimitedCodeInfo(tmpObj.getSDLimitedCodeInfo());
			searchDataModel.setSDFileInfo(tmpObj.getSDFileInfo());
			searchDataModel.setSDPackageInfo(tmpObj.getSDPackageInfo());
			searchDataModel.setSDProjectInfo(tmpObj.getSDProjectInfo());
		} else {
			// variables to jsp - cbnu
			model.addAttribute("ResultList", resultList);
			model.addAttribute("NumPage", numPage);
			model.addAttribute("baseKeyword", searchDataModel.getBase_keyword());
		}

		passList.addAll(tmpList); // to transfer data to detailed and importer

		return "team3/rd-search-result";
	}

	// cbnu - 2017/12 --->

	/**
	 * 검색 결과 리스트 선택한 경우 상세 페이지 View
	 * 
	 * @param
	 * @param
	 *
	 * @return
	 */

	// NAM - START//

	private RDStructureModel getRDInformation(String tarRD, String version) {
		RDStructureModel rdStructureModel = xmlParser
				.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD + "/" + version);
		try {
			rdStructureModel.setRdReuseCount(qualityService.getRDQualityFactors(tarRD, version));
		} catch (Exception e) {
			rdStructureModel.setRdReuseCount(0);
		} finally {
		}

		try {
			List<ReuseComment> reuseCommentList = reuseService.getReuseCommentList(tarRD, version);
			rdStructureModel.setRdCommentList((ArrayList<ReuseComment>) reuseCommentList);
		} catch (Exception e) {

		} finally {
		}
		return rdStructureModel;
	}

	private RDStructureModel getRDInformation(String tarRD) {

		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);
		try {

			rdStructureModel.setRdReuseCount(qualityService.getRDQualityFactors(tarRD, rdStructureModel.getVersion()));
		} catch (Exception e) {
			rdStructureModel.setRdReuseCount(0);
		} finally {
		}

		try {
			List<ReuseComment> reuseCommentList = reuseService.getReuseCommentList(tarRD,
					rdStructureModel.getVersion());
			rdStructureModel.setRdCommentList((ArrayList<ReuseComment>) reuseCommentList);
		} catch (Exception e) {
		} finally {
		}
		return rdStructureModel;
	}

	@RequestMapping(value = "/rd/search/result/tohistory/", method = RequestMethod.POST)
	public @ResponseBody boolean ToHistoryTest(@RequestParam ArrayList<String> id,
			@RequestParam ArrayList<String> rdComment) {

		ArrayList<String> idList = new ArrayList<String>();
		ArrayList<String> versionList = new ArrayList<String>();

		for (String idTmp : id) {
			idList.add(idTmp.substring(0, idTmp.lastIndexOf("_")));
			versionList.add(idTmp.substring(idTmp.lastIndexOf("_") + 1));
		}

		for (int s = 0; s < id.size(); s++) {
			try {
				qualityService.setReuseCounter(idList.get(s), versionList.get(s));
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rdComment.isEmpty()) {

				} else if (rdComment.get(s) == "") {

				} else if (rdComment.get(s).isEmpty()) {

				} else {

					reuseService.setReuseComment(idList.get(s), versionList.get(s), rdComment.get(s), "SE-Lab");
				}

				// TODO: 1세부에게 rd id 리스트를 보내는 기능 만들 것 2019-02-19
			}
		}
		return true;
	}

	@RequestMapping(value = "/rd/search/result/importer/", method = RequestMethod.POST)
	public @ResponseBody RDStructureModel ImporterTest(String tarRD) {
		searchState = "Internal";
		searchOp2 = "Internal";
		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);
		List<String> rdVersionList = xmlParser.parseRDVersionList(tarRD);
		int index = 0;
		int count = 0;
		for (String tmp : rdVersionList) {
			if (rdStructureModel.getVersion().equals(tmp)) {
				index = count;
				break;
			}
			count++;
		}
		for (int i = 0; i < rdParentList.size(); i++) {
			if (rdParentList.get(i).equals(tarRD)) {
				interIndex = i;
			}
		}
		versionIndex = index;
		RDVersionVariable = backUpRDVersionVariable;
		return getRDInformation(tarRD);
	}

	@RequestMapping(value = "/rd/search/result/intra_previous/", method = RequestMethod.POST)
	public @ResponseBody RDStructureModel IntraPrevious(String tarRD) {
		ArrayList<RDStructureModel> rdStructureModelList = xmlParser
				.parseTextRDList("http://13.124.180.8/rdml/parent/" + tarRD);
		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);
		if (rdStructureModel.getId().equals(rdStructureModelList.get(0).getId()))
			return null;
		int index = 0;
		for (int i = 0; i < rdStructureModelList.size(); i++) {
			if (rdStructureModelList.get(i).getId().equals(rdStructureModel.getId())) {
				index = i - 1;
				break;
			}
		}
		return getRDInformation(rdStructureModelList.get(index).getId());
	}

	@RequestMapping(value = "/rd/search/result/whole_list/", method = RequestMethod.POST)
	public @ResponseBody ArrayList<RDStructureModel> WholeList(String tarRD) {
		ArrayList<RDStructureModel> rdStructureModelList = xmlParser
				.parseTextRDList("http://13.124.180.8/rdml/parent/" + tarRD);
		return rdStructureModelList;
	}

	@RequestMapping(value = "/rd/search/result/intra_next/", method = RequestMethod.POST)
	public @ResponseBody RDStructureModel IntraNext(String tarRD) {
		ArrayList<RDStructureModel> rdStructureModelList = xmlParser
				.parseTextRDList("http://13.124.180.8/rdml/parent/" + tarRD);
		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);
		if (rdStructureModel.getId().equals(rdStructureModelList.get(rdStructureModelList.size() - 1).getId()))
			return null;
		int index = 0;
		for (int i = 0; i < rdStructureModelList.size(); i++) {
			if (rdStructureModelList.get(i).getId().equals(rdStructureModel.getId())) {
				index = i + 1;
				break;
			}
		}
		return getRDInformation(rdStructureModelList.get(index).getId());
	}

	@RequestMapping(value = "/rd/search/result/inter_predecessor/", method = RequestMethod.POST)
	public @ResponseBody RDStructureModel InterPredecessor(String tarRD, String searchOp) {
		if (!searchOp.equals("") && searchOp != null) {
			searchOp2 = searchOp;
		}

		ArrayList<RDStructureModel> rdStructureModelList = xmlParser
				.parseTextRDList("http://13.124.180.8/rdml/parent/" + tarRD);
		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);
		rdVersionList = xmlParser.parseRDVersionList(tarRD);

		int index = 0;
		int count = 0;
		for (String tmp : rdVersionList) {
			if (rdStructureModel.getVersion().equals(tmp)) {
				index = count;
				break;
			}
			count++;
		}

		if (versionIndex != -1) {
			index = versionIndex;
		}

		if (rdStructureModel.getId().equals(rdStructureModelList.get(0).getId())) // root일때
		{
			if (searchOp2.equals("Internal")) { // searchOp이 internal 일때
				if (searchState.equals("Internal")) { // 탐색 전에 search상태가 Internal일 때 * internal -> internal
					for (int i = 0; i < rdParentList.size(); i++) { // 검색 결과안에서 문서 인덱스

						if (index != 0) { // 문서의 첫 버전이 아닐때 -> 이전 문서 탐색
							System.out.println("들어왔다에 들어가기 전");
							versionIndex = index - 1;
							return getRDInformation(tarRD, rdVersionList.get(versionIndex));
						}
						if (i == 0 && rdParentList.get(i).equals(tarRD)) { // 검색 결과에서 첫 문서
							return null;
						}
						if (i != 0 && rdParentList.get(i).equals(tarRD)) { // 검색 결과에서 첫 문서가 아님

							interIndex = i - 1; // 여기서 문제발생
							versionIndex = -1;
							return getRDInformation(rdParentList.get(interIndex));
						}
					}
				} else { // 탐색 전에 search상태가 All일 때 * all -> internal
					searchState = "Internal"; // search상태를 Internal로 바꿈

					if (!predecessorStack.isEmpty())
						searchBaseRD = predecessorStack.pollFirst();
					else if (!successorStack.isEmpty())
						searchBaseRD = successorStack.pollFirst();

					String RDTmp = null;
					int versionIndex2 = 0;

					for (int i = 0; i < rdParentList.size(); i++) {
						if (rdParentList.get(i).equals(searchBaseRD)) {
							RDTmp = rdParentList.get(i);
							interIndex = i;
						}
					}

					if (index != 0) { // 해당 문서의 버전이 여럿인 경우
						List<String> tmpRDVersionList = new ArrayList<String>();
						tmpRDVersionList = xmlParser.parseRDVersionList(RDTmp);

						for (int j = 0; j < tmpRDVersionList.size(); j++) {
							if (tmpRDVersionList.get(j).equals(searchBaseRDVersion)) // searchBaseRDVersion은
																						// inter-document할 때, All을 체크하면
																						// 넣음
								versionIndex2 = j;
						}

						if (versionIndex2 == 0) { // TODO: 확실한 확인 필요
							return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2));

						}

						return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2 - 1));

					}
					searchBaseRDVersion = null;
					searchBaseRD = null;
					RDVersionVariable = null;
					predecessorStack = new ArrayDeque<String>();
					successorStack = new ArrayDeque<String>();

					if (interIndex < 0) {

						return getRDInformation(rdParentList.get(0));
					}

				}

				return getRDInformation(rdParentList.get(interIndex));
			} else {// searchOp이 All 일때 * internal -> all : 이것은 predecessor 스택을 이용하겠다는 것.
				if (searchState.equals("Internal")) { // 탐색 전에 search상태가 Internal일 때
					searchState = "All";

					predecessorStack.push(tarRD); // 체크포인트1
					searchBaseRD = tarRD; // 체크포인트2
					searchBaseRDVersion = rdVersionList.get(index); // 체크포인트3
					RDVersionVariable = searchBaseRDVersion;

					// String RDTmp = null;
					int versionIndex2 = 0;

					if (index != 0) { // 문서의 첫 버전이 아닐때 -> 이전 버전 문서 탐색

						List<String> tmpRDVersionList = new ArrayList<String>();
						tmpRDVersionList = xmlParser.parseRDVersionList(searchBaseRD);

						for (int j = 0; j < tmpRDVersionList.size(); j++) {
							if (tmpRDVersionList.get(j).equals(searchBaseRDVersion)) // searchBaseRDVersion은
																						// inter-document할 때, All을 체크하면
																						// 넣음
								versionIndex2 = j;
						}

						if (versionIndex2 == 0) {
							return null;

						}

						if (versionIndex <= 0) {
							versionIndex = index - 1;

						} else {
							versionIndex -= 1;
						}

						backUpRDVersionVariable = tmpRDVersionList.get(versionIndex);
						return getRDInformation(rdParentList.get(interIndex), backUpRDVersionVariable);
					} else { // 문서의 첫 버전일 때, RD id상 앞에 있는 것을 탐색
						RDVersionVariable = null;

						if (!xmlParser.isRDNull("1")) { // TODO: tarRD가 DB에 저장되어 있는 가장 첫번째 RD인지 확인하는 기능 구현하기 2019-02-19
							parentFlag = xmlParser.checkParent(tarRD);
							Integer tmptarRD = Integer.parseInt(tarRD);
							String tmpParent = null;

							for (int i = Integer.parseInt(tarRD); i >= 1; i--) { // parent가 다른걸 찾음
								tmptarRD -= 1;
								if (!xmlParser.isRDNull(tmptarRD.toString())) {
									tmpParent = xmlParser.checkParent(tmptarRD.toString());
									if (!parentFlag.equals(tmpParent))
										return getRDInformation(tmpParent);
								}
							}
						}
						return null;
					}
				} else { // 탐색 전에 search상태가 All일 때 * all -> all

					String baseTmp = null;
					// successorStack에 값이 들어 있는 것 고려

					if ((predecessorStack.isEmpty() && successorStack.isEmpty())
							|| (successorStack.isEmpty() && !predecessorStack.isEmpty())) { // predecessorStack이 비어있지
																							// 않으면 --> predecessor를
																							// successor보다 먼저 했었음
						baseTmp = tarRD;
						predecessorStack.push(baseTmp);

					} else if (!successorStack.isEmpty()) { // sucessorStack이 비어있지 않으면 --> successor를 predecessor보다 먼저
															// 했었음
						baseTmp = successorStack.pop();

						List<String> rdVersionList2 = xmlParser.parseRDVersionList(baseTmp);
						RDStructureModel rdStructureModel2 = xmlParser
								.parseOneTextRD("http://13.124.180.8/rdml/" + baseTmp);
						int index2 = 0;
						int count2 = 0;
						for (String tmp : rdVersionList2) {
							if (rdStructureModel2.getVersion().equals(tmp)) {
								index2 = count2;
							}
							count2++;
						}
						if (index2 != 0) {
							List<String> rdVersionListTmp = xmlParser.parseRDVersionList(baseTmp);
							if (RDVersionVariable == null) {
								RDVersionVariable = rdVersionListTmp.get(rdVersionListTmp.size() - 1);
							}
							for (int j = rdVersionListTmp.size() - 1; j >= 0; j--) {
								if (rdVersionListTmp.get(j).equals(RDVersionVariable)) {
									if (j > 0) {
										RDVersionVariable = rdVersionListTmp.get(j - 1);
										backUpRDVersionVariable = RDVersionVariable;
										return getRDInformation(baseTmp, RDVersionVariable);
									} else {
										return null;
									}
								}
							}
							return getRDInformation(baseTmp, RDVersionVariable);
						} else { // 문서의 첫 버전일 때 -> 다른 문서 탐색

							return getRDInformation(baseTmp);
						}
					}

					if (index != 0) { // 문서의 첫 버전이 아닐때 -> 이전 버전 문서 탐색
						List<String> rdVersionListTmp = xmlParser.parseRDVersionList(baseTmp);
						if (RDVersionVariable == null) {
							RDVersionVariable = rdVersionListTmp.get(rdVersionListTmp.size() - 1);
						}

						String tmpString = null;
						if (backUpRDVersionVariable == null) //
							tmpString = RDVersionVariable;
						else
							tmpString = backUpRDVersionVariable;

						for (int j = rdVersionListTmp.size() - 1; j >= 0; j--) {

							if (rdVersionListTmp.get(j).equals(tmpString)) {

								if (j > 0) {
									RDVersionVariable = rdVersionListTmp.get(j - 1);
									backUpRDVersionVariable = RDVersionVariable;
									return getRDInformation(baseTmp, RDVersionVariable);
								} else {
									RDVersionVariable = null;
									backUpRDVersionVariable = RDVersionVariable;
									if (!xmlParser.isRDNull("1")) { // TODO: tarRD가 DB에 저장되어 있는 가장 첫번째 RD인지 확인하는 기능 구현하기
																	// 2019-02-19
										parentFlag = xmlParser.checkParent(baseTmp);
										Integer tmptarRD = Integer.parseInt(baseTmp);
										String tmpParent = null;

										for (int i = Integer.parseInt(baseTmp); i >= 1; i--) { // parent가 다른걸 찾음
											tmptarRD -= 1;
											if (!xmlParser.isRDNull(tmptarRD.toString())) {
												tmpParent = xmlParser.checkParent(tmptarRD.toString());
												if (!parentFlag.equals(tmpParent)) {
													List<String> rdVersionList2 = xmlParser
															.parseRDVersionList(tmpParent);
													return getRDInformation(tmpParent,
															rdVersionList2.get(rdVersionList2.size() - 1));
												}
											}
										}
									}
									return null;
								}
							}
						}
						return getRDInformation(baseTmp, RDVersionVariable);
					} else { // 문서의 첫 버전일 때 -> 다른 문서 탐색
						RDVersionVariable = null;
						backUpRDVersionVariable = RDVersionVariable;
						if (!xmlParser.isRDNull("1")) { // TODO: tarRD가 DB에 저장되어 있는 가장 첫번째 RD인지 확인하는 기능 구현하기 2019-02-19
							parentFlag = xmlParser.checkParent(baseTmp);
							Integer tmptarRD = Integer.parseInt(baseTmp);
							String tmpParent = null;

							for (int i = Integer.parseInt(baseTmp); i >= 1; i--) { // parent가 다른걸 찾음
								tmptarRD -= 1;
								if (!xmlParser.isRDNull(tmptarRD.toString())) {
									tmpParent = xmlParser.checkParent(tmptarRD.toString());
									if (!parentFlag.equals(tmpParent)) {
										List<String> rdVersionList2 = xmlParser.parseRDVersionList(tmpParent);
										return getRDInformation(tmpParent,
												rdVersionList2.get(rdVersionList2.size() - 1));
									}
								}
							}
						}
						return null;
					}
				}
			}

		} else { // root가 아닐 때

			if (index != 0) { // RD의 첫 버전이 아닐 때 -> 이전 버전의 RD 탐색
				return getRDInformation(tarRD, rdVersionList.get(index - 1));
			} else { // RD의 첫 버전일 때 -> 다른 문서 RD 탐색

				return null;
			}
		}
	}

	@RequestMapping(value = "/rd/search/result/inter_successor/", method = RequestMethod.POST)
	public @ResponseBody RDStructureModel InterSucessor(String tarRD, String searchOp) {
		if (!searchOp.equals("") && searchOp != null) {
			searchOp2 = searchOp;
		}
		ArrayList<RDStructureModel> rdStructureModelList = xmlParser
				.parseTextRDList("http://13.124.180.8/rdml/parent/" + tarRD);
		RDStructureModel rdStructureModel = xmlParser.parseOneTextRD("http://13.124.180.8/rdml/" + tarRD);

		rdVersionList = xmlParser.parseRDVersionList(tarRD);
		int index = 0;
		int count = 0;
		for (String tmp : rdVersionList) {
			if (rdStructureModel.getVersion().equals(tmp)) {
				index = count;
				break;
			}
			count++;
		}
		if (versionIndex != -1) {
			index = versionIndex;
		}

		// root일때
		if (rdStructureModel.getId().equals(rdStructureModelList.get(0).getId())) {
			if (searchOp2.equals("Internal")) {// searchOp이 internal 일때
				if (searchState.equals("Internal")) { // 탐색 전에 search 상태가 internal 일 때 internal -> internal
					for (int i = 0; i < rdParentList.size(); i++) { // 검색 결과안에서 문서 인덱스
						if (index != rdVersionList.size() - 1) { // 문서의 마지막 버전이 아닐 때 -> 다음 문서 탐색

							versionIndex = index + 1;
							return getRDInformation(tarRD, rdVersionList.get(versionIndex));

						}
						if (i == rdParentList.size() - 1 && rdParentList.get(i).equals(tarRD)) // 검색 결과에서 마지막 문서
							return null;
						if (i != rdParentList.size() - 1 && rdParentList.get(i).equals(tarRD)) { // 검색 결과에서 마지막 문서가 아님

							interIndex = i + 1;
							versionIndex = -1;
							return getRDInformation(rdParentList.get(interIndex));
						}
					}
				} else { // 탐색 전에 search 상태가 All 일때 all -> internal
					searchState = "Internal";

					if (!successorStack.isEmpty())
						searchBaseRD = successorStack.pollFirst();
					else if (!predecessorStack.isEmpty())
						searchBaseRD = predecessorStack.pollFirst();

					String RDTmp = null;
					int versionIndex2 = 0;

					for (int i = 0; i < rdParentList.size(); i++) {
						if (rdParentList.get(i).equals(searchBaseRD)) {
							RDTmp = rdParentList.get(i);
							interIndex = i;
						}
					}
					if (index != 0) { // 해당 문서의 버전이 여럿인 경우
						List<String> tmpRDVersionList = new ArrayList<String>();
						tmpRDVersionList = xmlParser.parseRDVersionList(RDTmp);
						for (int j = 0; j < tmpRDVersionList.size(); j++) {
							if (tmpRDVersionList.get(j).equals(searchBaseRDVersion))
								versionIndex2 = j;
						}
						if (versionIndex2 == tmpRDVersionList.size() - 1) { // TODO: 버그를 발생시키는지 확인
							return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2));

						}

						if (tmpRDVersionList.size() == 0) {
							return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2));
						}
						return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2 + 1));
					}
					searchBaseRDVersion = null;
					searchBaseRD = null;
					RDVersionVariable = null;
					predecessorStack = new ArrayDeque<String>();
					successorStack = new ArrayDeque<String>();

					if (interIndex > rdParentList.size() - 1) {
						return getRDInformation(rdParentList.get(rdParentList.size() - 1));
					}
				}
				return getRDInformation(rdParentList.get(interIndex));
			} else { // searchOp이 All 일때, *internal -> all : 이것은 successor 스택을 이용하겠다는 것.
				if (searchState.equals("Internal")) { // 탐색 전에 search 상태가 Internal일 때
					searchState = "All";

					successorStack.push(tarRD);
					searchBaseRD = tarRD;
					searchBaseRDVersion = rdVersionList.get(index);
					RDVersionVariable = searchBaseRDVersion;

					int versionIndex2 = 0;

					if (index != rdVersionList.size() - 1) { // 문서의 마지막 버전이 아닐 때 -> 다음 버전 문서 탐색
						List<String> tmpRDVersionList = new ArrayList<String>();
						tmpRDVersionList = xmlParser.parseRDVersionList(searchBaseRD);

						for (int j = 0; j < tmpRDVersionList.size(); j++) {
							if (tmpRDVersionList.get(j).equals(searchBaseRDVersion)) // searchBaseRDVersion은
																						// inter-document 할 때, All을 체크하면
																						// 넣음
								versionIndex2 = j;
						}
						if (versionIndex2 == tmpRDVersionList.size() - 1) {
							return getRDInformation(rdParentList.get(interIndex), tmpRDVersionList.get(versionIndex2));
						}

						if (versionIndex >= rdVersionList.size() - 1) {
							versionIndex = 0;
						} else {
							versionIndex += 1;
						}

						backUpRDVersionVariable = tmpRDVersionList.get(versionIndex);
						return getRDInformation(rdParentList.get(interIndex), backUpRDVersionVariable);
					} else { // 문서의 마지막 버전일 때, RD id상 뒤에 있는 것을 탐색

						RDVersionVariable = null;
						if (!xmlParser.isRDNull("2400")) { // TODO: tarRD가 DB에 저장되어 있는 가장 마지막 RD인지 확인하는 기능 구현하기
															// 2019-02-19
							parentFlag = xmlParser.checkParent(tarRD);
							Integer tmptarRD = Integer.parseInt(tarRD);
							String tmpParent = null;

							for (int i = Integer.parseInt(tarRD); i < 2400; i++) { // parent가 다른 것을 찾음
								tmptarRD += 1;
								if (!xmlParser.isRDNull(tmptarRD.toString())) {
									tmpParent = xmlParser.checkParent(tmptarRD.toString());

									if (!parentFlag.equals(tmpParent)) {
										List<String> rdVersionList2 = xmlParser.parseRDVersionList(tmpParent);
										return getRDInformation(tmpParent, rdVersionList2.get(0));
									}

								}
							}

						}
						return null;
					}
				} else { // 탐색 전에 search상태가 All일 때. * all -> all
					String baseTmp = null;
					if ((predecessorStack.isEmpty() && !successorStack.isEmpty())
							|| (successorStack.isEmpty() && predecessorStack.isEmpty())) {
						baseTmp = tarRD;
						successorStack.push(baseTmp);

					} else if (!predecessorStack.isEmpty()) {
						baseTmp = predecessorStack.pop();

						List<String> rdVersionList2 = xmlParser.parseRDVersionList(baseTmp);
						RDStructureModel rdStructureModel2 = xmlParser
								.parseOneTextRD("http://13.124.180.8/rdml/" + baseTmp);
						int index2 = 0;
						int count2 = 0;
						for (String tmp : rdVersionList2) {
							if (rdStructureModel2.getVersion().equals(tmp)) {
								index2 = count2;
							}
							count2++;
						}
						if (index2 != rdVersionList2.size() - 1) {
							List<String> rdVersionListTmp = xmlParser.parseRDVersionList(baseTmp);
							if (RDVersionVariable == null) {
								RDVersionVariable = rdVersionListTmp.get(0);
							}
							for (int j = 0; j < rdVersionListTmp.size(); j++) {
								if (rdVersionListTmp.get(j).equals(RDVersionVariable)) {
									if (j < rdVersionListTmp.size() - 1) {
										RDVersionVariable = rdVersionListTmp.get(j + 1);
										backUpRDVersionVariable = RDVersionVariable;
										return getRDInformation(baseTmp, RDVersionVariable);
									} else {

										return null;
									}
								}
							}
							return getRDInformation(baseTmp, RDVersionVariable);
						} else { // 문서의 마지막 버전일 때 -> 다른 문서 탐색

							return getRDInformation(baseTmp);
						}

					}

					if (index != rdVersionList.size() - 1) { // 문서의 마지막 버전이 아닐 때 -> 마지막 버전 문서 탐색

						List<String> rdVersionListTmp = xmlParser.parseRDVersionList(baseTmp);
						if (RDVersionVariable == null) {
							RDVersionVariable = rdVersionListTmp.get(0);
						}

						String tmpString = null;
						if (backUpRDVersionVariable == null)
							tmpString = RDVersionVariable;
						else
							tmpString = backUpRDVersionVariable;

						for (int j = 0; j < rdVersionListTmp.size(); j++) {
							if (rdVersionListTmp.get(j).equals(tmpString)) {
								if (j < rdVersionListTmp.size() - 1) {
									RDVersionVariable = rdVersionListTmp.get(j + 1);
									backUpRDVersionVariable = RDVersionVariable;
									return getRDInformation(baseTmp, RDVersionVariable);
								} else {

									RDVersionVariable = null;
									backUpRDVersionVariable = RDVersionVariable;
									if (!xmlParser.isRDNull("2400")) { // TODO: tarRD가 DB에 저장되어 있는 가장 마지막 RD인지 확인하는 기능
																		// 구현하기 2019-02-19
										parentFlag = xmlParser.checkParent(baseTmp);
										Integer tmptarRD = Integer.parseInt(baseTmp);
										String tmpParent = null;

										for (int i = Integer.parseInt(baseTmp); i < 2400; i++) { // parent가 다른걸 찾음
											tmptarRD += 1;
											if (!xmlParser.isRDNull(tmptarRD.toString())) {
												tmpParent = xmlParser.checkParent(tmptarRD.toString());
												if (!parentFlag.equals(tmpParent)) {
													List<String> rdVersionList2 = xmlParser
															.parseRDVersionList(tmpParent);
													return getRDInformation(tmpParent, rdVersionList2.get(0));
												}
											}
										}
									}
									return null;
								}
							}
						}
						return getRDInformation(baseTmp, RDVersionVariable);
					} else { // 문서의 마지막 버전일 때 -> 다른 문서 탐색
						RDVersionVariable = null;
						backUpRDVersionVariable = RDVersionVariable;
						if (!xmlParser.isRDNull("2400")) { // TODO: tarRD가 DB에 저장되어 있는 가장 마지막 RD인지 확인하는 기능 구현하기
															// 2019-02-19
							parentFlag = xmlParser.checkParent(baseTmp);
							Integer tmptarRD = Integer.parseInt(baseTmp);
							String tmpParent = null;

							for (int i = Integer.parseInt(baseTmp); i < 2400; i++) { // parent가 다른걸 찾음
								tmptarRD += 1;
								if (!xmlParser.isRDNull(tmptarRD.toString())) {
									tmpParent = xmlParser.checkParent(tmptarRD.toString());
									if (!parentFlag.equals(tmpParent)) {
										List<String> rdVersionList2 = xmlParser.parseRDVersionList(tmpParent);
										return getRDInformation(tmpParent, rdVersionList2.get(0));
									}
								}
							}
						}

						return null;
					}
				}
			}
		} else { // root가 아닐 때
			if (index != rdVersionList.size() - 1) {
				return getRDInformation(tarRD, rdVersionList.get(index + 1));
			} else {
				return null;
			}
		}
	}

	// NAM - END //

	/*
	 * ********************** Coded by Yonsei **********************
	 */

	/**
	 * 검색 텝플릿 페이지
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rd/yonsei/template")
	public String rdYonseiTemplate(Model model) {
		return "team3/rd-yonsei-template";
	}

	/**
	 * 검색 질의 처리 페이지
	 * 
	 * @param model
	 * @param q           RD query
	 * @param limitedView LimitedView
	 * @param resultSim   User defined similarity
	 * @return
	 */
	@RequestMapping(value = "/rd/yonsei/query", method = { RequestMethod.GET, RequestMethod.POST })
	public String rdYonseiQuery(Model model, @RequestParam(required = false, defaultValue = "", value = "q") String q,
			@RequestParam(required = false, defaultValue = "", value = "limitedView") String limitedView,
			@RequestParam(required = false, defaultValue = "", value = "resultSim") String resultSim) {
		return "team3/rd-yonsei-query";
	}

	/*
	 * ********************** Coded by Yonsei // **********************
	 */
	@RequestMapping(value = "/rd/search/page")
	public String SearchPage(Model model) {
		return "team3/rd-search-page";
	}

	@RequestMapping(value = "/rd/search/page", method = RequestMethod.POST)
	public String test(@RequestParam("rdtitle") String[] rdtitle) {
		for (String s : rdtitle) {
			System.out.print(s);
		}
		return "team3/rd-search-page";
	}
	
	@RequestMapping(value = "/rd/statistics", method = RequestMethod.GET)
	public String Statistics() {
		return "team3/rd-statistics";
	}
}

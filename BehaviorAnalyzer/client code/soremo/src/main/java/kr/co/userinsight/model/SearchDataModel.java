package kr.co.userinsight.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * 검색에 필요한 데이터를 담는 모델
 * 
 * @author yungu
 * @category 3세부
 */
@Data
public class SearchDataModel {
	private String base_keyword;
	private List<String> ext_rd_relation_list;
	private List<String> ext_rd_type;
	private String ext_rd_start_date;
	private String ext_rd_end_date;
	private boolean contextual_information = false;

	// <-- RD data
	private List<String> name;
	private List<String> author;
	private List<String> ISBN;
	private List<String> keyword;
	private List<String> overview;
	private List<String> overviewSimple;
	private List<String> imageUrl;
	// RD data -->

	// --------------------------------------
	// Added - Yonsei.
	// --------------------------------------
	private ArrayList<String> sdFileInfo;
	private ArrayList<String> sdPackageInfo;
	private ArrayList<String> sdProjectInfo;
	private ArrayList<String> sdCodeInfo;
	private ArrayList<String> sdLimitedCodeInfo;
	private ArrayList<String> sdReuseCount;

	public void setContextInfoFlag(boolean flag) {
		this.contextual_information = flag;
	}

	public boolean getContextInfoFlag() {
		if (contextual_information) {
			return contextual_information;
		}
		return false;
	}

	public ArrayList<String> getSDFileInfo() {
		return this.sdFileInfo;
	}

	public void setSDFileInfo(ArrayList<String> sdFileInfo) {
		this.sdFileInfo = sdFileInfo;
	}

	public ArrayList<String> getSDPackageInfo() {
		return this.sdPackageInfo;
	}

	public void setSDPackageInfo(ArrayList<String> sdPackageInfo) {
		this.sdPackageInfo = sdPackageInfo;
	}

	public ArrayList<String> getSDProjectInfo() {
		return sdProjectInfo;
	}

	public void setSDProjectInfo(ArrayList<String> sdProjectInfo) {
		this.sdProjectInfo = sdProjectInfo;
	}

	public ArrayList<String> getSDCodeInfo() {
		return this.sdCodeInfo;
	}

	public void setSDCodeInfo(ArrayList<String> sdCodeInfo) {
		this.sdCodeInfo = sdCodeInfo;
	}

	public ArrayList<String> getSDLimitedCodeInfo() {
		return this.sdLimitedCodeInfo;
	}

	public void setSDLimitedCodeInfo(ArrayList<String> sdLimitedCodeInfo) {
		this.sdLimitedCodeInfo = sdLimitedCodeInfo;
	}

	public ArrayList<String> getSDReuseCount() {
		return sdReuseCount;
	}

	public void setSDReuseCount(ArrayList<String> sdReuseCount) {
		this.sdReuseCount = sdReuseCount;
	}

	// --------------------------------------
	// Added - Yonsei. //
	// --------------------------------------

	// public SearchDataModel() {
	// ontologyConnector c = new ontologyConnector();
	// }

	public void setBase_keyword(String base_keyword) {
		this.base_keyword = base_keyword;
		/*
		 * try { this.base_keyword = new String(base_keyword.getBytes("8859_1"),
		 * "UTF-8"); System.out.println("keyword = " + this.base_keyword); }
		 * catch (UnsupportedEncodingException e) { e.printStackTrace();
		 * this.base_keyword = base_keyword; }
		 */
	}

	public String getBase_keyword() {
		return this.base_keyword;
	}

	public void setExt_rd_relation_list(List<String> ext_rd_relation_list) {
		this.ext_rd_relation_list = ext_rd_relation_list;
	}

	public List<String> getExt_rd_relation_list() {
		return ext_rd_relation_list;
	}

	public void setExt_rd_type(List<String> ext_rd_type) {
		this.ext_rd_type = ext_rd_type;
	}

	public List<String> getExt_rd_type() {
		return ext_rd_type;
	}

	public void setExt_rd_start_date(String ext_rd_start_date) {
		this.ext_rd_start_date = ext_rd_start_date;
	}

	public String getExt_rd_start_date() {
		return this.ext_rd_start_date;
	}

	public void setExt_rd_end_date(String ext_rd_end_date) {
		this.ext_rd_end_date = ext_rd_end_date;
	}

	public String getExt_rd_end_date() {
		return this.ext_rd_end_date;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<String> getName() {
		return this.name;
	}

	public void setAuthor(List<String> author) {
		this.author = author;
	}

	public List<String> getAuthor() {
		return this.author;
	}

	public void setOverview(List<String> overview) {
		this.overview = overview;
	}

	public List<String> getOverview() {
		return this.overview;
	}

	public void setOverviewSimple(List<String> overviewSimple) {
		this.overviewSimple = overviewSimple;
	}

	public List<String> getOverviewSimple() {
		return this.overviewSimple;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getImageUrl() {
		return this.imageUrl;
	}
}

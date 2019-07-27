package kr.co.userinsight.model;

import java.util.ArrayList;

public class QueryModel {
	private static QueryModel instance;

	private String base_keyword;
	private ArrayList<String> eKeyword;
	private ArrayList<String> rdTypeList;
	private String dStart;
	private String dEnd;
	private Boolean contextInfo;

	public QueryModel() {
		base_keyword = new String();
		eKeyword = new ArrayList<String>();
		rdTypeList = new ArrayList<String>();
		dStart = new String();
		dEnd = new String();
		contextInfo = false;
	}

	public static synchronized QueryModel getInstance() {
		if (instance == null) {
			instance = new QueryModel();
		}

		return instance;
	}

	public String getBase_keyword() {
		return base_keyword;
	}

	public void setBase_keyword(String base_keyword) {
		this.base_keyword = base_keyword;
	}

	public ArrayList<String> geteKeyword() {
		return eKeyword;
	}

	public void seteKeyword(String str) {
		// str.trim();
		String[] strArr = str.split(",");
		for (int i = 0; i < strArr.length; i++) {
			eKeyword.add(strArr[i]);
		}
	}

	public void seteKeyword(ArrayList<String> eKeyword) {
		this.eKeyword = eKeyword;
	}

	public ArrayList<String> getRdTypeList() {
		return rdTypeList;
	}

	public void setRdTypeList(ArrayList<String> rdTypeList) {
		this.rdTypeList = rdTypeList;
	}

	public void setRdTypeList(String strList[]) {
		for (int i = 0; i < strList.length; i++) {
			this.rdTypeList.add(strList[i]);
		}

	}

	public String getdStart() {
		return dStart;
	}

	public void setdStart(String dStart) {
		this.dStart = dStart;
	}

	public String getdEnd() {
		return dEnd;
	}

	public void setdEnd(String dEnd) {
		this.dEnd = dEnd;
	}

	public Boolean getContextInfo() {
		return contextInfo;
	}

	public void setContextInfo(Boolean contextInfo) {
		this.contextInfo = contextInfo;
	}


}

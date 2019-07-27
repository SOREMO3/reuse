package kr.co.userinsight.controller;

import org.json.simple.JSONObject;

import kr.ac.yonsei.RDMLModel;

public class JSONParserForResultData {

	private static final String empty = "";

	public JSONParserForResultData() {

	}

	public RDMLModel parseResult(JSONObject resultObj) {

		// String tmpContents = resultObj.get("contentsPlain") + empty;
		String tmpImageUrl = resultObj.get("imageUrl") + empty;

		RDMLModel tmpModel = new RDMLModel();

		tmpModel.setCreator(resultObj.get("creator") + empty);
		tmpModel.setModifier(resultObj.get("modifier") + empty);
		tmpModel.setTitle(resultObj.get("title") + empty);
		tmpModel.setVersion(resultObj.get("version") + empty);
		tmpModel.setCreatedDate(resultObj.get("createdDate") + empty);
		tmpModel.getCreatedDate().trim();
		String tmpStr = tmpModel.getCreatedDate().substring(0, 4) + "-" + tmpModel.getCreatedDate().substring(4, 6)
				+ "-" + tmpModel.getCreatedDate().substring(6, 8);

		tmpModel.setCreatedDate(tmpStr);

		tmpModel.setContents(resultObj.get("contents") + empty);

		if (!tmpImageUrl.equals(empty)) {
			tmpImageUrl = tmpImageUrl.substring(tmpImageUrl.indexOf("\"", tmpImageUrl.indexOf("src")) + 1,
					tmpImageUrl.indexOf("\"", tmpImageUrl.indexOf("\"", tmpImageUrl.indexOf("src")) + 1));
		}

		tmpModel.setImageUrl(tmpImageUrl);
		tmpModel.setModifiedDate(resultObj.get("modifiedDate") + empty);
		tmpModel.getModifiedDate().trim();
		tmpStr = tmpModel.getModifiedDate().substring(0, 4) + "-" + tmpModel.getModifiedDate().substring(4, 6) + "-"
				+ tmpModel.getModifiedDate().substring(6, 8);

		tmpModel.setCreatedDate(tmpStr);
		tmpModel.setRDID(resultObj.get("id") + empty);

		tmpModel.setContentsPlain(resultObj.get("contentsPlain") + empty);

		return tmpModel;
	}

	// public RDStructureModel parseResult2(JSONObject resultObj) {
	// RDStructureModel rd = new RDStructureModel();
	//
	// rd.setId(resultObj.get("id") + "");
	// rd.setTitle(resultObj.get("title") + "");
	// rd.setVersion(resultObj.get("version") + "");
	// rd.setCreator(resultObj.get("creator") + "");
	// rd.setCreatedDate(resultObj.get("createdDate") + "");
	// rd.setModifier(resultObj.get("modifier") + "");
	// rd.setModifiedDate(resultObj.get("modifiedDate") + "");
	// rd.setApplication(resultObj.get("application") + "");
	// rd.setDepth(resultObj.get("depth") + "");
	//
	// return rd;
	//
	// }

}

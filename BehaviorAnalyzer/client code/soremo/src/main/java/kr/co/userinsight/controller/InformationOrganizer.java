package kr.co.userinsight.controller;

import kr.co.userinsight.model.RDStructureModel;

public class InformationOrganizer {
	private static final String preFix = "http://dev.userinsight.co.kr:8888/rdml/";

	public InformationOrganizer() {
	}

	public RDStructureModel getRDModelbyID(String id, String ver) {
		String addr = new String();
		if (ver.contains("null") || ver == null) {
			addr = preFix + id + "/" + "null.null";
		} else {
			addr = preFix + id + "/" + ver;
		}

		XMLParser pp = new XMLParser();
		RDStructureModel m = pp.parseTextRD(addr);

		return m;
	}

	public RDStructureModel getContextualInformation() {
		RDStructureModel tmp = new RDStructureModel();
		
		return tmp;
	}

	// public RDStructureModel getRDModelbyID(String id, String ver, File
	// XMLOutput, File JSONOutput,
	// JSONObject resultObj) {
	// String addr = preFix + id + "/" + ver;
	// File file = getFile(addr);
	// if (file == XMLOutput)
	// return new XMLParser().parse(file);
	// else
	// return new JSONParserForResultData().parseResult2(resultObj);
	//
	// }
	//
	// private File getFile(String addr) {
	// File file = null;
	// try {
	// file = new File(new URI(addr));
	// // TODO how will you get the file?!!!!!!
	// } catch (URISyntaxException e) {
	// e.printStackTrace();
	// }
	//
	// if (file != null) {
	// return file;
	// }
	//
	// return null;
	// }
}

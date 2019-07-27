<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="kr.ac.yonsei.*" %>
<%@ page language="java" import="java.util.*" %>
<%@ page language="java" import="javax.json.*" %>
<%@ page language="java" import="org.apache.commons.lang3.StringUtils" %>
 
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
//------------------------
//request values
//------------------------
String searchQuery = request.getParameter("q");
String limitedView = request.getParameter("limitedView");
String resultSim = request.getParameter("resultSim");

//Added. 2017-04-13
String searchField = request.getParameter("searchField");
String version = request.getParameter("version");

//limitedView = "y";
resultSim = JARVIS.chkNull( resultSim ).length() > 0 ? resultSim : "70";

// Added. 2017-02-06
String extendedKeyword = request.getParameter("extendedKeyword");
String rdType = request.getParameter("rdType");

if(!JARVIS.isNullOrEmpty(searchField) && searchField.equals(RDMLModel.RD_SOURCE)) {
	rdType = RDMLModel.RD_SOURCE;
}

if(!JARVIS.isNullOrEmpty(rdType) && rdType.equals(RDMLModel.RD_SOURCE)) {
	searchField = RDMLModel.RD_SOURCE;
}

String startDate = request.getParameter("startDate");
String endDate = request.getParameter("endDate");

//------------------------
//request values //
//------------------------

ArrayList<RDMLModel> queryRDResult = null;

JARVIS.debug("rdType: " + rdType);

if( !JARVIS.isNullOrEmpty( searchQuery ) ) {
	if(JARVIS.isNullOrEmpty(searchField)) {
		searchField = StringUtils.join(SearchRDProto.baseFieldList, ",");
	}

	if(searchField.equals(RDMLModel.RD_SOURCE)) {
		SearchSDProto protoSD = new SearchSDProto();
		protoSD.search( searchQuery );
		queryRDResult = protoSD.getResult();
	} else {
		SearchRDProto protoRD = new SearchRDProto();
		protoRD.search( searchQuery, searchField, version, startDate, endDate );
		queryRDResult = protoRD.getResult();
	}
	
	Map<String, String> m = new HashMap<String, String>();

	JsonBuilderFactory factory = Json.createBuilderFactory(m);
	JsonObjectBuilder jsonResult = factory.createObjectBuilder();
	JsonArrayBuilder jsonArrayBuilder = factory.createArrayBuilder();

	jsonResult.add( "query", searchQuery );

	for( int resultPos = 0; resultPos < queryRDResult.size(); resultPos++ ) {
		RDMLModel resultModel = queryRDResult.get( resultPos );	
		
		String title = resultModel.getTitle();
		String contents = resultModel.getContents();
		
		//JARVIS.debug("Query -> Title: " + title);

		JsonObjectBuilder jsonObj;
		
		//if(rdType != null && rdType.equals(RDMLModel.RD_SOURCE)) {
		if(searchField.equals(RDMLModel.RD_SOURCE)) {
		    //JARVIS.debug("SimpleFileName: " + JARVIS.getSimpleFileName( resultModel.getPath() ) );
			//JARVIS.debug("sdLimitedCodeInfo: " + resultModel.getSDLimitedCodeInfo() );
			
			jsonObj = factory.createObjectBuilder()
					//.add( "file", JARVIS.getSimpleFileName( resultModel.getPath() ) )
					.add( "title", resultModel.getTitle() )
					.add( "searchType", resultPos % 3 )
					.add( "contentsLimited", resultModel.getContents() )
					.add( "contents", resultModel.getSDCodeInfo() )
					.add( "sdProjectInfo", resultModel.getSDProjectInfo() )
					.add( "sdPackageInfo", resultModel.getSDPackageInfo() )
					.add( "sdCodeInfo", resultModel.getSDCodeInfo() )
					.add( "sdLimitedCodeInfo", resultModel.getSDLimitedCodeInfo() )
					.add( "id", resultModel.getRDID() )
					.add( "version", resultModel.getVersion() )
					.add( "rdType", resultModel.getRDType() )
					.add( "projectName", resultModel.getProjectName() )
					.add( "creator", resultModel.getCreator() )
					.add( "createdDate", resultModel.getCreatedDate() )
					.add( "imageUrl", resultModel.getImageUrl() )
					;
		} else {
			//String[] htContents = JARVIS.emphasis( contents, searchQuery, limitedView );
			String contentsPlain = JARVIS.getPlainText(contents);

			String[] htContents = {
					JARVIS.emphasis( contents, searchQuery ),
					//JARVIS.emphasis( StringUtils.abbreviate(contents, 300) + "...", searchQuery )
					JARVIS.emphasis( StringUtils.abbreviate(contentsPlain, 300) + "...", searchQuery )
			};

			jsonObj = factory.createObjectBuilder()
					//.add( "file", JARVIS.getSimpleFileName( resultModel.getPath() ) )
					.add( "title", resultModel.getTitle() )
					.add( "searchType", resultPos % 3 )
					.add( "contentsLimited", htContents[0] )
					.add( "contents", htContents[1])
					.add( "contentsPlain", contentsPlain)
					.add( "id", resultModel.getRDID() )
					.add( "version", resultModel.getVersion() )
					.add( "rdType", resultModel.getRDType() )
					.add( "projectName", resultModel.getProjectName() )
					.add( "creator", resultModel.getCreator() )
					.add( "createdDate", resultModel.getCreatedDate() )
					.add( "modifier", resultModel.getModifier() )
					.add( "modifiedDate", resultModel.getModifiedDate() )
					.add( "imageUrl", resultModel.getImageUrl() )
					;
		}

		jsonArrayBuilder.add( jsonObj );

		//JARVIS.createJSON( model );
	}

	String result = jsonResult.add( "result", jsonArrayBuilder.build() ).build().toString();

	out.println( result );
}
%>
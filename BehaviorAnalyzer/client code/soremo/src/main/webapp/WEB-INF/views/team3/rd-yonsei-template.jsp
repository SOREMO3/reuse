<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page language="java" import="kr.ac.yonsei.*" %>
<%@ page language="java" import="java.util.*" %>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>RD Search Engine</title>
		<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.servletContext.contextPath}/resources/assets-minified/style-yonsei.css">
<%!
String[] emphasis(String src, String emWord, String limitedView) {
	int wordPadding = 30;
	String[] result = { "", "" };

	int loop = 0;
	int startIdx = src.indexOf( emWord );
	int beforeIdx = 0;

	while( startIdx > -1 && loop++ < 4 ) {
		if( startIdx > wordPadding ) {
			result[0] += "... " + src.substring( startIdx - wordPadding, startIdx + emWord.length() );
		} else if( startIdx <= wordPadding ) {
			result[0] += src.substring( beforeIdx, startIdx + emWord.length() );
		}

		beforeIdx = startIdx + emWord.length();
		startIdx = src.indexOf( emWord, startIdx + emWord.length() );
		wordPadding += startIdx;

		//System.out.println( result );
	}

	if( loop == 0 ) {
		result[0] += src.substring( 0, wordPadding * 4 );
	} else if( loop < 4 ) {
		int lastIdx = src.lastIndexOf( emWord );
		result[0] += src.substring( lastIdx + emWord.length(), (lastIdx + wordPadding) > src.length() ? src.length() - 1 : (lastIdx + wordPadding) );
	}

	result[0] += " ...";

	System.out.println( "[" + result[0] + "]");
	
	if( limitedView != null ) {
		result[0] = result[0].replaceAll( emWord, "<span class='r-matched-terms'>" + emWord + "</span>" );
	} else {
		result[0] = src.replaceAll( emWord, "<span class='r-matched-terms'>" + emWord + "</span>" );
	}
	
	result[1] = src.replaceAll( emWord, "<span class='r-matched-terms'>" + emWord + "</span>" );

	return result;
}

String getSimpleFileName( String path ) {
	return path.substring( path.lastIndexOf( "\\" ) + 1 );
}

String chkNull( String str ) {
	return str == null ? "" : str.trim();
}
%>

<%
request.setCharacterEncoding("UTF-8");
final String[] RDList = { "Text-RD", "Object-RD", "Source-RD", "Figure-RD" };
final String[] simList = { "완전일치함(100%)", "매우정확함(90%)", "다소정확함(80%)", "다소유사함(70%)", "관련성있음(60%)" };

String searchQuery = request.getParameter("searchQuery");
String limitedView = request.getParameter("limitedView");
String resultSim = request.getParameter("resultSim");
resultSim = resultSim == null ? "3" : resultSim;

int resultCount = 0;
ArrayList<RDMLModel> queryResult = null;

if( searchQuery != null && !searchQuery.equals("") ) {
	SearchRDProto proto = new SearchRDProto();
	proto.search( searchQuery );

	queryResult = proto.getResult();
	resultCount = queryResult.size();

	//out.println("결과수: " + resultCount + "<br>" );
}
%>

		<script>
/*
$(function() {
	$("#r-sim-id").click( function() {
		$("#r-sim-combo").removeClass("hidden");
		$("#r-sim-combo").addClass("show");
	});
});
*/
		</script>
	</head>
	<body>

	<!-- RD Search Results -->
		<table cellpadding=1 cellspacing=0 class="table_border2" width=95% align=center>
			<tr>
				<td class="td_search_result_header1 td_bottom_line2">

					<!-- RD Search Results header -->
					<table class="table_no_border1" width=100%>
						<tr>
							<td width=100% class="td_result_header1">

								<form name="searchForm" method="post" action="">
								<table class="table_no_border1" width=100% border=0 align=right>
									<tr>
										<td width=150>
											<b>Base keyword:</b>
										</td>
										<td>
											<input type=text class="input_search2" name="searchQuery" value="<%=chkNull( searchQuery )%>" placeholder="검색어를 입력하세요.">
										</td>
										<td style="padding: 0 5 0 5; width: 130px; text-align: right;">
											<button class="button" style="vertical-align:middle"><span>Search </span></button>
										</td>
									</tr>
								</table>

								<br>
								
								<div>
									<span class="r-label">RD 검색결과</span>:
									<span class="r-num"><%=resultCount%></span>
									<span class="sep">|</span>
									<span class="r-label">연관키워드:</span>
									<span class="r-keyword">
										<%
										if( resultCount > 0 ) {
										%>
										<u>소프트웨어</u>, <u>품질</u>, <u>생산성</u>, <u>향상</u>
										<%
										} else {
										%>-<%
										}
										%>
									</span>
									<span class="sep">|</span>
									<span class="r-sim-label">기본유사도:</span>
									<span class="r-sim-num">
										<span id="r-sim-num-id"><%=simList[ Integer.parseInt( resultSim ) ]%></span>
										<span class="r-sim-num-span">
											<select size=1 class="r-sim-num-combo" onchange="document.getElementById('r-sim-num-id').innerHTML=this.options[this.selectedIndex].text;" name="resultSim">
												<%
												for( int pos = 0; pos < simList.length; pos++ ) {
													String selected = "";

													if( pos == Integer.parseInt( resultSim ) ) {
														selected = " selected ";
													}
												%>
													<option value='<%=pos%>' <%=selected%>><%=simList[pos]%></option>
												<%
												}
												%>
											</select>
										</span>
									</span>
									<span class="sep">|</span>
									<input type=checkbox name="limitedView" id="limitedView_id" value="y" style="vertical-align: middle;" <%=(limitedView != null ? "checked" : "")%> onclick="this.form.submit();"><label for="limitedView_id"><span class="r-label">제한된보기</span></label>
								</div>
								</form>
							</td>
							<!--
							<td align=right width=30% class="td_result_header1 td_result1">
								Elapsed: <b>1.72s</b>
							</td>
							-->
						</tr>
					</table>
					<!-- RD Search Results header // -->

				</td>
			</tr>
			<tr>
				<td class="td_bottom_line2" style="padding: 7 7 7 7;">
					
					<!-- 검색결과 전체 -->
					<table class="table_no_border1" width=100%>
						<tr>
							<td colspan=2>

								<!-- 검색결과 전체 - 배경 -->
								<table class="table_code_result1 table_no_border1"> 
									<tr>
										<td>
<%
	for( int resultPos = 0; resultPos < resultCount; resultPos++ ) {
		RDMLModel model = queryResult.get( resultPos );

		String title = model.getTitle();
		String contents = model.getContents();
		String[] htContents = emphasis( contents, searchQuery, limitedView );

		//out.println( "<div>File: " + getSimpleFileName( model.getPath() ) + "</div>" );
		//out.println( "<div>Title: " + model.getTitle() + "</div>" );
		//out.println( "<div>Contents: " + htContents + "</div>" );
%>
											
											<!-- 각 검색 결과 -->
											<div class="tooltip">
											<table class="y-table-result1 overmouse">
												<tr>
													<td class="td_project_line1" rowspan=2 width=40>
														<input type="checkbox" name="relatedRD[]" id="relatedRD_<%=resultPos%>" class="chk-result1">
													</td>
													<td class="td_project_line1">
														<%=(resultPos + 1)%>.
														ID: 1
														<span class="r-title">(<%=model.getTitle()%>)</span>
														<span class="rcorners-def rcorners<%=(resultPos % 4 + 1)%>"><%=RDList[resultPos % 4]%></span>
													</td>
													<td class="td_project_line1" rowspan=2 width=60>
														20/01/<br>2017
													</td>
												</tr>
												<tr>
													<td class="td_project_line2" width=2000>
														<%=htContents[0]%>
													</td>
												</tr>
											</table>
											<span class="tooltiptext">
												<%=htContents[1]%>
											</span>
											</div>

											<hr>
<%
	}
%>
											<!-- 각 검색 결과 // -->
											

										</td>
									</tr>
								</table>
								<!-- 검색결과 전체 - 배경 // -->

							</td>
						</tr>
					</table>
					<!-- 검색결과 전체 // -->
					<table class="table_no_border1" width=100%>
						<tr>
							<td width=100% align=center>
								<button class="button"><span>연관 RD 검색</span></button>
							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
		<!-- RD Search Results // -->

		<!--
		<input type=button value="100 More ..." class="btn_search1">
		or
		<input type=button value="연관 검색" class="btn_search1">
		-->

	</body>
</html>


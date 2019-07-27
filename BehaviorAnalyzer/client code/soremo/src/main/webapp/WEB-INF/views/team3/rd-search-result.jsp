<%@page import="kr.co.userinsight.model.QueryModel"%>
<%@page import="kr.co.userinsight.model.SearchDataModel"%>
<%@page import="org.json.simple.parser.ParseException"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="java.net.URL"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URLConnection"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.nio.charset.Charset"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="../commons/meta.jsp"%>

<!-- core css, resource 정의 -->
<%@ include file="../commons/core-css.jsp"%>

<!-- user css 정의 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.servletContext.contextPath}/resources/css/custom_dialog.css">

<!-- core javascript 정의 -->
<%@ include file="../commons/core-js.jsp"%>

<!-- user javascript 정의 -->
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/js/event_rd_result.js"></script>

<%
	String base_keyword = request.getParameter("base_keyword");
	StringBuilder queryResult = new StringBuilder();

	QueryModel.getInstance().setBase_keyword(request.getParameter("base_keyword"));
	QueryModel.getInstance().seteKeyword(request.getParameter("ext_rd_relation_list"));
	if (request.getParameter("ext_rd_type") != null) {
		QueryModel.getInstance().setRdTypeList(request.getParameterValues("ext_rd_type"));
	}
	QueryModel.getInstance().setdStart(request.getParameter("ext_rd_start_date"));
	QueryModel.getInstance().setdEnd(request.getParameter("ext_rd_end_date"));

	//JSONObject obj = model.getJSON();
%>

<script>
$(function() {
	harvested = 0;
	
	$(".y-code-tooltiptext").draggable({handle: "div.y-code-tooltiptext-handle"});

	$("#show-adv-menu").click(function() {
		$("#y-adv-search").slideToggle(500);
		
		doCounting();
	});	
	
	$("#add-all").click(function() {
		if($(this).is(":checked")) {
			var searchKeyword = $("#input_search1_keyword").val();

			$("#input_search1_pattern").val(searchKeyword);
			$("#input_search1_clone").val(searchKeyword);
			$("#input_search1_feature").val(searchKeyword);
			$("#input_search1_project").val(searchKeyword);
		}
	});
	
	$("#search-btn").click(function() {
		$("#y-area-pattern").fadeToggle(300);
		$("#y-area-clone").fadeToggle(300);
	});

	codeToggleCnt = 0;
	
	$("#y-code-result-display").click(function() {
		$(".y-code-result-data").slideToggle(300);
		
		if(++codeToggleCnt % 2 == 1) {
			$("#y-code-result-display").html("+Maximize+");
			$("#y-code-result-display").css("color", "#1060C0");
		} else {
			$("#y-code-result-display").html("+Minimize+");
			$("#y-code-result-display").css("color", "#C06010");
		}
	});

	patternToggleCnt = 0;
	
	$("#y-pattern-result-display").click(function() {
		$(".y-pattern-result-data").slideToggle(300);
		
		if(++patternToggleCnt % 2 == 1) {
			$("#y-pattern-result-display").html("+Maximize+");
			$("#y-pattern-result-display").css("color", "#1060C0");
		} else {
			$("#y-pattern-result-display").html("+Minimize+");
			$("#y-pattern-result-display").css("color", "#C06010");
		}
	});

	cloneToggleCnt = 0;
	
	$("#y-clone-result-display").click(function() {
		$(".y-clone-result-data").slideToggle(300);
		
		if(++cloneToggleCnt % 2 == 1) {
			$("#y-clone-result-display").html("+Maximize+");
			$("#y-clone-result-display").css("color", "#1060C0");
		} else {
			$("#y-clone-result-display").html("+Minimize+");
			$("#y-clone-result-display").css("color", "#C06010");
		}
	});
	
	$(".y-ext-contextmenu-display").click(function(e) {
		//alert(event.pageX);
		$("#y-ext-contextmenu").fadeToggle(300);
		$("#y-ext-contextmenu").css("left", e.pageX);
		$("#y-ext-contextmenu").css("top", e.pageY);
	});
	
	$("#y-ext-menu-p").click(function() {
		$("#y-area-pattern").fadeToggle(300);
	});

	$("#y-ext-menu-c").click(function() {
		$("#y-area-clone").fadeToggle(300);
	});

	$("#y-ext-contextmenu").mouseleave(function() {
		$("#y-ext-contextmenu").fadeToggle(300);
	});
	
	$("#y-btn-sd-harvest").click(function(e) {
		/*
		e.preventDefault();
		$('.y-modal-harvest-area').toggle();
		*/
		window.open("http://165.132.221.45:8080/Pattern/analyzeProject.jsp", "", "width=900,height=900");
	});
	
	$("#y-close-harvest").click(function(e) {
		$('.y-modal-harvest-area').toggle();
		
		if(harvested) {
			$("#y-item-1").html("1310");
			$("#y-item-2").html("1722121");
			$("#y-item-3").html("2019");
			$("#y-item-4").html("01");
			$("#y-item-5").html("03");
			$("#y-item-6").html("15");
			$("#y-item-7").html("55");
			
			doCounting();
		}
	});
	
	$(".y-modal-harvest-area").draggable({handle: ".y-modal-harvest-handle"});
	
	$("#y-do-harvest").click(function(){
		var step = ".y-modal-harvest-step";
		var stepCnt = $(step).length;

		doHarvest(0, stepCnt);
		
		harvested = 1;
	});
});

function doHarvest(idx, total) {
	var step = ".y-modal-harvest-step-" + idx;
	var stepResult = step + "-result";
	
	if(idx < total) {
		$(step).fadeIn(500).delay(1000, function() {
			setTimeout(function() { },500);

			$(stepResult).html("Done!");
			
			doHarvest(++idx, total);
		});	
	}
}

function doCounting() {
	$('.y-item-count').each(function () {
		$(this).prop('Counter',0).animate({
			Counter: $(this).text()
		}, {
			duration: 1500,
			easing: 'swing',
			step: function (now) {
				$(this).text(Math.ceil(now));
			},
			done: function () {
				var txt = $(this).text();
			}
		});
	});
}

</script>

<style type="text/css">
.rd-image {
	/*width: 160px;*/
	width: 160px; /* 50px */
}
[data-tooltip-text]:hover {
	position: relative;
}
[data-tooltip-text]:hover:after {
	content: attr(data-tooltip-text);

    position: absolute;
	top: 100%;
	left: 0;
    
    background-color: rgba(0, 0, 0, 0.8);
	color: #FFFFFF;
	font-size: 12px;

	z-index: 9999;
}
</style>
</head>
<body>
	<div id="sb-site">
		
		<div id="page-wrapper">
		
			<div id="page-content-wrapper">
				<div id="page-content">
					<div class="container pad10A">
						<div class="content-box">
							<div class="pad10A">
								<ul class="pager" style="margin:0">
									<li class="previous">
										<a id="back-btn" href="${pageContext.servletContext.contextPath}/rd/search/?keyword=${searchDataModel.base_keyword}">←Back</a>
									<!-- 
										<c:url
											value="${servletContext.contextPath}/rd/search/"
											var="searchPage">
											<c:param name="keyword"
												value="${searchDataModel.base_keyword}" />
										</c:url> <a href="${searchPage}">←Back</a>
									 -->
									</li>
								</ul>
							</div>
							<form:form id="resultModel" action=""
								modelAttribute="searchDataModel">
								<form:hidden path="base_keyword" />
								<form:hidden path="ext_rd_relation_list" />
								<form:hidden path="ext_rd_type" />
								<form:hidden path="ext_rd_start_date" />
								<form:hidden path="ext_rd_end_date" />
							</form:form>
							<ul class="nav-responsive nav nav-tabs" id="ulMainTab">
								<!-- 기존에 다음과 같았는데, href="#tab-header" data-toggle="tab">Header Base, 아래와 같이 수정 -->
								<li class="${base == 'header' ? 'active' : ''}"
									value=" ${headerCount} "><a
									href="javascript:redirectToResult('header', 1);">Header
										Base</a></li>
								<li class="${base == 'image' ? 'active' : ''}"
									value="  ${imageCount}  "><a
									href="javascript:redirectToResult('image', 1);">Image Base</a></li>
								<li class="${base == 'text' ? 'active' : ''}"
									value="  ${textCount}  "><a
									href="javascript:redirectToResult('text', 1);">Text Base</a></li>
								<li class="${base == 'source' ? 'active' : ''}"
									value=" ${sourceCount} "><a
									href="javascript:redirectToResult('source', 1);">Source
										Base</a></li>
							</ul>
							<div class="tab-content">
								<div id="tab-header"
									class="tab-pane ${base == 'header' ? 'active' : ''}">
									
									<div class="pad10T pad10B">
										<p>
											<strong>입력 검색어: ${searchDataModel.base_keyword}</strong>
										</p>
									</div>
									
									<table id="search2" class="table table-hover text-center">
										<tbody>
											<tr>
											<!-- 
												<th style="font-weight: bold; text-align: center;"
													width="20px"></th>
											-->
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="15px">Num.</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="170px">Title</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;">Contents</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="50px">Author</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width=100>Date</th>
											</tr>
											<c:forEach items="${ResultList}" var="head" varStatus="status">
												<tr>
												<!-- 
													<td style="text-align: center;"><input type="checkbox"
														id="mail-checkbox-1" class="custom-checkbox"></td>
												-->
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														${(page-1)*10+status.index + 1}</td>
													<td style="border-left: 5px solid transparent;">
														<h5 class="font-black">
															<c:choose>
																<c:when
																	test="${ResultList.get(status.index).getContentsPlain().length()!=''}">
																	<a href="#"
																		onclick="reuse_popup(${ResultList.get(status.index).getRDID()})">
																		${ResultList.get(status.index).getTitle()} </a>
																</c:when>
																<c:otherwise>
																${ResultList.get(status.index).getTitle()}
															</c:otherwise>
															</c:choose>
														</h5>
													</td>
													<td style="border-left: 5px solid transparent;"><c:choose>
															<c:when
																test="${ResultList.get(status.index).getContentsPlain().length()>200}">
														${ResultList.get(status.index).getContentsPlain().substring(0,199)}...
														</c:when>
															<c:when
																test="${ResultList.get(status.index).getContentsPlain().length()==''}">
														(No Contents)
														</c:when>
															<c:otherwise>
														${ResultList.get(status.index).getContentsPlain()}
														</c:otherwise>
														</c:choose></td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														<small>${ResultList.get(status.index).getCreator()}</small>
													</td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														${ResultList.get(status.index).getCreatedDate()}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div id="tab-image"
									class="tab-pane ${base == 'image' ? 'active' : ''}">
									
									<div class="pad10T pad10B">
										<p>
											<strong>입력 검색어: ${searchDataModel.base_keyword}</strong>
										</p>
									</div>
									
									<table id="search2" class="table table-hover text-center">
										<tbody>
											<tr>
											<!-- 
												<th style="font-weight: bold; text-align: center;"
													width="20px"></th>
											-->
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="15px">Num.</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="170px">Image</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;">Title</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="50px">Author</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width=100>Date</th>
											</tr>
											<c:forEach items="${ResultList}" var="image"
												varStatus="status">
												<tr>
												<!-- 
													<td><input type="checkbox" id="text-checkbox-1"
														class="custom-checkbox"></td>
												-->
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														${(page-1)*10+status.index + 1}</td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														<a href="#"
														onclick="reuse_popup(${ResultList.get(status.index).getRDID()})">
															<img class="img-responsive rd-image"
															src="${ResultList.get(status.index).getImageUrl()}"
															alt="(No Images on this RD)" />
													</a>
													</td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														<h5 class="font-black">${ResultList.get(status.index).getTitle()}</h5>
													</td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														<small>${ResultList.get(status.index).getCreator()}</small>
													</td>
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														${ResultList.get(status.index).getCreatedDate()}</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div id="tab-text"
									class="tab-pane ${base == 'text' ? 'active' : ''}">
									
									<div class="pad10T pad10B">
										<p>
											<strong>입력 검색어: ${searchDataModel.base_keyword}</strong>
										</p>
									</div>
									
									<table id="search2" class="table table-hover text-center">
										<tbody>
											<tr>
											<!-- 
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="20"></th>
											-->
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;"
													width="15">Num.</th>
												<th
													style="font-weight: bold; text-align: center; border-left: 5px solid transparent;">Contents</th>
											</tr>
											<c:forEach items="${ResultList}" var="text"
												varStatus="status">
												<tr>
													<!-- 
													<td><input type="checkbox" id="text-checkbox-1"
														class="custom-checkbox"></td>
												 	-->
													<td
														style="text-align: center; border-left: 5px solid transparent;">
														${(page-1)*10+status.index + 1}</td>
													<td style="border-left: 5px solid transparent;">
														<div class="font-size-13">
															<c:choose>
																<c:when
																	test="${ResultList.get(status.index).getContentsPlain().length()>200}">
																	<a href="#"
																		onclick="reuse_popup(${ResultList.get(status.index).getRDID()})">
																		${ResultList.get(status.index).getContentsPlain().substring(0,199)}...
																	</a>
																</c:when>
																<c:when
																	test="${ResultList.get(status.index).getContentsPlain().length()==''}">
																	<a href="#"
																		onclick="reuse_popup(${ResultList.get(status.index).getRDID()})">
																		(No Contents) </a>
																</c:when>
																<c:otherwise>
																	<a href="#"
																		onclick="reuse_popup(${ResultList.get(status.index).getRDID()})">
																		${ResultList.get(status.index).getContentsPlain()} </a>
																</c:otherwise>
															</c:choose>
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>

								<!-- --------------- -->
								<!-- Coded by Yonsei -->
								<!-- --------------- -->
								<div id="tab-source"
									class="tab-pane ${base == 'source' ? 'active' : ''}">
									
									<!--  Code result count -->
									<table class="y-result-table">
										<tr>
											<td width=60% class="y-result-header3">
												Results
												<span class="y-result-count">1-20</span>
												of about
												<span class="y-result-count">22,122,012</span>
												results found for
												'<span class="y-result-word">${searchDataModel.base_keyword}</span>'
												in
												<span class="y-result-estimation">3.212</span>
												seconds.
											</td>
											<td align=right class="y-result-header1">
												<span id="show-adv-menu">+advanced search</span>
											</td>
										</tr>
									</table>
									<!--  Code result count // -->
									
									<!--  Advanced search area -->
									<div id="y-adv-search">
									
									
		<table class="y-adv-table" valign=top>
			<tr>
				<td style="padding-left: 5px !important;" class="y-adv-header td_bottom_line1" colspan=3>
					Advanced Search Options
				</td>
			</tr>
			<tr>
				<td class="td_bottom_line1" colspan=3 align=right>
					<div class="y-item-area">
						<span class="y-item-title">Projects:</span>
						<span class="y-item-count">10221</span>
						<span class="y-item-title">Source files:</span>
						<span class="y-item-count">12254231</span>
						<span class="y-item-title">Patterns:</span>
						<span class="y-item-count">1546931</span>
						<span class="y-item-title">Last update:</span>
						<span class="y-item-date">
						<span class="y-item-count">2019</span>-<span class="y-item-count">01</span>-<span class="y-item-count">01</span> <span class="y-item-count">23</span>:<span class="y-item-count">30</span></span>
					</div>
				</td>
			</tr>
			<tr>
				<td class="td_bottom_line1">

					<!-- ------------------ -->
					<!-- Keyword            -->
					<!-- ------------------ -->
					<table class="y-adv-inner-table h60">
						<tr>
							<td class=y-adv-item-name>
								Keyword
							</td>
						</tr>
						<tr>
							<td class=td_search_area1>
								<input type=text id="input_search1_keyword" value="${searchDataModel.base_keyword}">
								<input type=checkbox id="add-all"><label for="add-all">전체적용</label>
							</td>
						</tr>
					</table>

				</td>
				<td class="td_bottom_line1" rowspan=3>

					<!-- ------------------ -->
					<!-- Pattern            -->
					<!-- ------------------ -->
					<table class="y-adv-inner-table h180">
						<tr>
							<td class=y-adv-item-name>
								Pattern
							</td>
						</tr>
						<tr>
							<td class=td_search_area1>

								<table border=0 cellpadding=0 cellspacing=0 width=100% align=right>
									<tr>
										<td align=right colspan=2>
											<textarea id="input_search1_pattern" class="input_search1 textarea_search1"></textarea>
										</td>
									</tr>
									<tr>
										<td align=right colspan=2>
											<input type=file class="input_file1">
										</td>
									</tr>
								</table>

							</td>
						</tr>
					</table>

				</td>
				<td class="td_bottom_line1" rowspan=3>

					<!-- ------------------ -->
					<!-- Clone Code         -->
					<!-- ------------------ -->
					<table class="y-adv-inner-table h180">
						<tr>
							<td class=y-adv-item-name>
								Code Clone
							</td>
						</tr>
						<tr>
							<td class="td_search_area1">
								<table border=0 cellpadding=2 cellspacing=0 width=100%>
									<tr>
										<td align=right>
											<textarea id="input_search1_clone" class="input_search1 textarea_search1"></textarea>
										</td>
									</tr>
									<tr>
										<td align=right>
											<input type=file class="input_file1">
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td class="td_bottom_line1">

					<!-- ------------------ -->
					<!-- Feature            -->
					<!-- ------------------ -->
					<table class="y-adv-inner-table h60">
						<tr>
							<td class=y-adv-item-name>
								Feature
							</td>
						</tr>
						<tr>
							<td class=td_search_area1>
								<input type=text id="input_search1_feature" class="input_search1">
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td class="td_bottom_line1">

					<!-- ------------------ -->
					<!-- Project            -->
					<!-- ------------------ -->
					<table class="y-adv-inner-table h60">
						<tr>
							<td class=y-adv-item-name>
								Project
							</td>
						</tr>
						<tr>
							<td class=td_search_area1>
								<input type=text id="input_search1_project" class="input_search1">
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td colspan=3 class="y-adv-search-btn-area">
					<button class="search-btn search-btn-default" id="search-btn">
						<span id="search-btn-label">Adv Search</span>
					</button>
					<button class="harvest-btn harvest-btn-default" id="y-btn-sd-harvest">
						<span id="harvest-btn-label">Harvest</span>
					</button>
				</td>
			</tr>
		</table>
									
									</div>
									<!--  Advanced search area // -->

							<!-- Code result area -->
							<div class="y-code-result-area">
								<div class="y-code-result-title">
									<b>Code</b> (<span class="y-result-count">1,032</span>)						
									<span id="y-code-result-display">+Minimize+</span>
								</div>				
										
								<div class="y-code-result-data">
									<!--  <table id="search2" class="table table-hover text-center"> -->
									<table class="y-table-no-border">

										<!-- JSON Parsing part -->
										<c:forEach items="${searchDataModel.getSDLimitedCodeInfo()}" var="resultOverviewSimple" varStatus="status">
										<tr>
											<td class="y-code-info-td">
												<div class="y-code-info">
													<span class="y-code-num">${status.index + 1}</span>
													<span class="y-code-info-title y-lp20">File:</span><span
														class="y-code-info-data">${searchDataModel.getSDFileInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">Package:</span><span
														class="y-code-info-data">${searchDataModel.getSDPackageInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">Project:</span><span
														class="y-code-info-data">${searchDataModel.getSDProjectInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">재사용:</span><span
														class="y-code-info-data">-</a>
												</div>

												<!-- result code iteration -->
												<div class="y-code-tooltip">
													<table class="y-result-table-ani overmouse">
														<tr>
															<td class="y-result-code y-NOPAD">
															
																<!-- code line by line -->
																<table class="y-code-table">
																	<tbody class="y-code-tbody">
																		<c:set var="limitedCode">${searchDataModel.getSDLimitedCodeInfo().get(status.index)}</c:set>
																		<%
																		// -------------------------------------------------------------
																		// Important:
																		//   - 1. JSTL에는 배열 생성 불가능
																		//   - 2. JSTL split은 multi character seperating이 불가능
																		//   ==> JSP로 처리해야함. 
																		//
																		//       Don't do the 삽질 with JSTL!
																		//   - 3. 원본 코드 자체의 오류 처리 필요 -> 이상한 코드들이 많음
																		// -------------------------------------------------------------
																	
																		String limitedCode = pageContext.getAttribute("limitedCode") + "";
																		String limitedCodeArr[] = limitedCode.split("_NEWLINE_");
																		
																		if( limitedCodeArr.length < 5 ) {
																			%>
																			<tr class="y-code-tr">
																				<td class="y-code-line y-NOPAD">N/A</td>
																				<td class="y-code-data y-NOPAD"><span class="y-err-code">Source code has some problem.</span></td>
																			</tr>
																			<%
																			for(int i=0; i < 5; i++) {
																				%>
																				<tr class="y-code-tr">
																					<td class="y-code-line y-NOPAD" colspan=2></td>
																				</tr>
																				<%
																				}
																		} else {
																			for(int i=0; i < limitedCodeArr.length; i++ ) {
																				String eachLimitedCode = limitedCodeArr[i];
																				int sepPos = eachLimitedCode.indexOf(',');

																				String eachLineNo = eachLimitedCode.substring(0, sepPos);
																				String eachLimitedCodeFrag = eachLimitedCode.substring(sepPos+1, eachLimitedCode.length());
																				//String eachLimitedCodeFrag = eachLimitedCode.substring(sepPos+1);
																				
																				// TODO: temporary code
																				eachLimitedCodeFrag = eachLimitedCodeFrag.replaceAll("Connection", "<span class='y-ext-contextmenu-display'>Connection</span>");
																				
																				pageContext.setAttribute("eachLineNo", eachLineNo);
																				pageContext.setAttribute("eachLimitedCodeFrag", eachLimitedCodeFrag);
																				%>
																				<tr class="y-code-tr">
																					<td class="y-code-line y-NOPAD">${eachLineNo}</td>
																					<td class="y-code-data y-NOPAD">${eachLimitedCodeFrag}</td>
																				</tr>
																				<%
																			}
																		}
																		%>
																	</tbody>
																</table>
																<!-- code line by line // -->
																
															</td>
														</tr>
													</table>
													
													<!-- will be moved. -->
													<!-- will be moved. // -->
													<div class="y-code-tooltiptext">
														<div class="y-code-tooltiptext-handle">
															&nbsp;
														</div>
														<div class="y-code-tooltiptext-inner">
															<table class="y-code-overview">
															<c:set var="overview">${searchDataModel.getOverview().get(status.index)}</c:set>
															<%
															String overview = pageContext.getAttribute("overview") + "";
															String overviewArr[] = overview.split("<br>");
															
															if( overviewArr.length < 1 ) {
																%>
																<tr>
																	<td>
																		Overview code has some problem.
																	</td>
																</tr>
																<%
															} else {
																for(int i=0; i < overviewArr.length; i++ ) {
																	String eachOverviewCode = overviewArr[i];
																	int sepPos = eachOverviewCode.indexOf(',');
																	String eachLineNo = eachOverviewCode.substring(0, sepPos);
																	String eachOverviewCodeFrag = eachOverviewCode.substring(sepPos+1, eachOverviewCode.length());
																	//String eachOverviewCodeFrag = eachOverviewCode.substring(sepPos+1);
																	
																	pageContext.setAttribute("eachLineNo", eachLineNo);
																	pageContext.setAttribute("eachOverviewCodeFrag", eachOverviewCodeFrag);
																	%>
																	<tr class="y-code-tr">
																		<td class="y-code-line y-NOPAD">${eachLineNo}</td>
																		<td class="y-code-data y-NOPAD">${eachOverviewCodeFrag}</td>
																	</tr>
																	<%
																}
															}
															/*
															pageContext.setAttribute("eachOverview", overview.trim());
															${searchDataModel.overview[status.index]}
															*/
															%>
															</table>
														</div>
													</div>
												</div>
												<!-- result code iteration // -->
											</td>
											
											<!-- 
											
											<td class="y-rd-info-td">
												<div class="y-code-info">
													<span class="y-code-info-title y-lp20">Related RD List</span>
													(<span class="y-code-info-data">0</span>)
												</div>
	
												<div class="y-rd-tooltip">
													<table class="y-result-table-ani overmouse">
														<tr>
															<td class="y-result-rd y-NOPAD">
																														
																<table class="y-rd-table">
																	<tbody class="y-rd-tbody">
																	<tr class="y-rd-tr">
																		<td class="y-rd-line y-NOPAD">1</td>
																		<td class="y-rd-data y-NOPAD">
																			연결된 RD 정보 없음
																		</td>
																	</tr>
																	<tr class="y-rd-tr">
																		<td class="y-rd-line y-NOPAD" colspan=2></td>
																	</tr>
																	<tr class="y-rd-tr">
																		<td class="y-rd-line y-NOPAD" colspan=2></td>
																	</tr>
																	<tr class="y-rd-tr">
																		<td class="y-rd-line y-NOPAD" colspan=2></td>
																	</tr>
																	<tr class="y-rd-tr">
																		<td class="y-rd-line y-NOPAD" colspan=2></td>
																	</tr>
																	</tbody>
																</table>												
															
															</td>
														</tr>
													</table>			
																					
													<span class="y-rd-tooltiptext">
														Some codes...
													</span>
												</div>
											</td>
											 -->
											
										</tr>
										</c:forEach>
										<!-- JSON Parsing part // -->
											
									</table>
								</div>
								<div class="y-result-more">
									more codes... (1,029)
								</div>
									
							</div>
							<!-- Code result area // -->
							
							<!-- Pattern result area -->							
							<div class="y-code-result-area" id="y-area-pattern">
								<div class="y-code-result-title">
									<b>Pattern</b> (<span class="y-result-count">253</span>)						
									<span id="y-pattern-result-display">+Minimize+</span>
								</div>				
										
								<div class="y-pattern-result-data">
									<!--  <table id="search2" class="table table-hover text-center"> -->
									<table class="y-table-no-border">
									
										<!-- JSON Parsing part -->
										<tr>
											<td class="y-code-info-td">
												<div class="y-code-info">
													<span class="y-code-num">${status.index + 1}</span>
													<span class="y-code-info-title y-lp20">File:</span><span
														class="y-code-info-data">${searchDataModel.getSDFileInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">Package:</span><span
														class="y-code-info-data">${searchDataModel.getSDPackageInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">Project:</span><span
														class="y-code-info-data">${searchDataModel.getSDProjectInfo().get(status.index)}</span><span
														class="y-code-info-title y-lp20">재사용:</span><span
														class="y-code-info-data">-</a>
												</div>

												<!-- result code iteration -->
												<div class="y-code-tooltip">
													<table class="y-result-table-ani overmouse">
														<tr>
															<td class="y-result-code y-NOPAD">
															
															<!-- code line by line -->
																<table class="y-code-table">
																	<tbody class="y-code-tbody">
																		
																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">27</td>
																			<td class="y-code-data y-NOPAD">&nbsp;protected Connection Connect(){</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">28</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;Connection instanceCon = null;</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">29</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;try{</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">30</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(this.conMode == Mode.Oracle){</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">31</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class.forName("oracle.jdbc.driver.OracleDriver");</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">32</td>
																			<td class="y-code-data y-NOPAD">&nbsp;</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">33</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;instanceCon = DriverManager.getConnection("jdbc:oracle:thin:@localhost");</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">34</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} else {</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">35</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class.forName("com.mysql.jdbc.Driver");</td>
																		</tr>

																	</tbody>
																</table>
																<!-- code line by line // -->	
															</td>
														</tr>
													</table>
													
													<!-- will be moved. -->
													<!-- will be moved. // -->

												</div>
												<!-- result code iteration // -->
											</td>
										</tr>
										<tr>
											<td class="y-code-info-td">
												<div class="y-code-info">
													<span class="y-code-num">${status.index + 2}</span>
													<span class="y-code-info-title y-lp20">File:</span><span
														class="y-code-info-data">${searchDataModel.getSDFileInfo().get(status.index + 1)}</span><span
														class="y-code-info-title y-lp20">Package:</span><span
														class="y-code-info-data">${searchDataModel.getSDPackageInfo().get(status.index + 1)}</span><span
														class="y-code-info-title y-lp20">Project:</span><span
														class="y-code-info-data">${searchDataModel.getSDProjectInfo().get(status.index + 1)}</span><span
														class="y-code-info-title y-lp20">재사용:</span><span
														class="y-code-info-data">-</a>
												</div>

												<!-- result code iteration -->
												<div class="y-code-tooltip">
													<table class="y-result-table-ani overmouse">
														<tr>
															<td class="y-result-code y-NOPAD">
															
															<!-- code line by line -->
																<table class="y-code-table">
																	<tbody class="y-code-tbody">
																		
																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">27</td>
																			<td class="y-code-data y-NOPAD">&nbsp;protected Connection Connect(){</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">28</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;Connection instanceCon = null;</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">29</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;try{</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">30</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;if(this.conMode == Mode.Oracle){</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">31</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class.forName("oracle.jdbc.driver.OracleDriver");</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">32</td>
																			<td class="y-code-data y-NOPAD">&nbsp;</td>
																		</tr>

																		<tr class="y-code-tr y-pattern-line">
																			<td class="y-code-line y-NOPAD">33</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;instanceCon = DriverManager.getConnection("jdbc:oracle:thin:@localhost");</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">34</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;} else {</td>
																		</tr>

																		<tr class="y-code-tr">
																			<td class="y-code-line y-NOPAD">35</td>
																			<td class="y-code-data y-NOPAD">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Class.forName("com.mysql.jdbc.Driver");</td>
																		</tr>

																	</tbody>
																</table>
																<!-- code line by line // -->	
															</td>
														</tr>
													</table>
													
													<!-- will be moved. -->
													<!-- will be moved. // -->

												</div>
												<!-- result code iteration // -->
											</td>
										</tr>
										<!-- JSON Parsing part // -->
									
									</table>
								</div>
								
								<div class="y-result-more">
									more patterns... (250)
								</div>
								
							</div>
							<!-- Pattern result area // -->
							
							<!-- Clone result area -->							
							<div class="y-code-result-area" id="y-area-clone">
								<div class="y-code-result-title">
									<b>Clone</b> (<span class="y-result-count">591</span>)						
									<span id="y-clone-result-display">+Minimize+</span>
								</div>				
										
								<div class="y-clone-result-data">
									<!--  <table id="search2" class="table table-hover text-center"> -->
									<table class="y-table-no-border">
									
										<!-- JSON Parsing part -->
										<tr>
											<td class="y-code-info-td">

												<!-- result code iteration -->
												<div style="width:100%;">
													<table class="y-result-table-ani">
														<tr>
															<td class="y-result-clone">
																<div class="y-code-info">
																	<span class="y-code-info-title">Method:</span>
																	<span class="y-code-info-data">org.apache.storm.ma...OutBufferThread.run()</span>
																</div>
																<textarea class="y-result-clone-ta">@Override
public void run() {
	try {
		String line = reader.readLine();
		while(line != null) {
			output.add(line);
			line = reader.readLine();
		}
	} catch(IOException ex) {
		throw new RuntimeException("make falied with error code" + ex.toString());
	}
}
																</textarea>
															</td>
															<td class="y-result-clone">
																<div class="y-code-info">
																	<span class="y-code-info-title">Method:</span>
																	<span class="y-code-info-data">org.apache.storm.ma...StreamRedirect.run()</span>
																</div>
																<textarea class="y-result-clone-ta">@Override
public void run() {
	try {
		int i = -1;
		while((i = this.in.read()) != -1) {
			out.write(i);
		}
		this.in.close();
		this.out.close();
	} catch(Exception e) {
		e.printStackTrace();
	}
}
																</textarea>
															</td>
														</tr>
													</table>
													
													<!-- will be moved. -->
													<!-- will be moved. // -->

												</div>
												<!-- result code iteration // -->
											</td>
										</tr>
									</table>
								</div>
								
								<div class="y-result-more">
									more clones... (127)
								</div>
								
							</div>
							<!-- Clone result area // -->
							
							<!-- ---------------------------- -->
							<!-- Coded by Yonsei - 2019-01-02 -->
							<!-- ---------------------------- -->
							
							<div class="">
							<!-- extra -->
							</div>							
							
							<!-- ------------------------------- -->
							<!-- Coded by Yonsei - 2019-01-02 // -->
							<!-- ------------------------------- -->
							
								<!-- ------------------ -->
								<!-- Coded by Yonsei // -->
								<!-- ------------------ -->

							</div>
							<div class="button-pane mrg20T">
								<ul class="pagination">

									<c:forEach var="i" begin="1" end="${NumPage }">
										<li class="${page == i ? 'active' : ''} rd-list-page-number"><a
											href="javascript:redirectToResult('${base}', '${i }');">${i }<span
												class="sr-only">(current)</span></a></li>
									</c:forEach>

								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

		<!--  importer pop-up ui implementation start 
			by.Nam
		-->
		<input type="hidden" id="rdID" value=""> <input type="hidden"
			id="rdName" value=""> <input type="hidden" id="rdContents"
			value=""> <input type="hidden" id="rdHome" value="">
			

		<div class="hide" id="reuse_importer" title="RD reuse importer">
			<div class="container reuse-importer-container">
				<div class="row">
					<div class="col-sm-6 pad5R pad0L">
						<div class="border-container">
							<div class="contents-header">
								<div class="quality-table-header col-sm-12 mar0B">
									<span>RD Info</span>
								</div>
							</div>
							<table id="selected-rd-table"
								class="table table-hover reuse-table mar0B">
								<tr class="none-border-top none-hover">
									<th class="wid15 pad10T pad10B">Title</th>
									<td class="wid50 pad10T pad10B pad10R" bgcolor="#f9fafe">
										<span id="rd_title"></span>
									</td>
									<th class="wid15 pad10T pad10B">Version</th>
									<td class="wid20 pad10T pad10B pad10R" bgcolor="#f9fafe">
										<span id="rd_version"></span>
									</td>
								</tr>
							</table>
							<table class="table table-hover reuse-table mar5B">
								<tr class="none-hover">
									<th class="wid15 pad10T pad10B">Author</th>
									<td class="wid50 pad10T pad10B pad10R" bgcolor="#f9fafe">
										<span id="rd_creator"></span>
									</td>
									<th class="wid15 pad10T pad10B">Date</th>
									<td class="wid20 pad10T pad10B pad10R" bgcolor="#f9fafe">
										<span id="rd_created_date"></span>
									</td>
								</tr>
							</table>
						</div>
						<div id="rd-quality-factors-container" class="border-container">
							<div id="rd-quality-factors-table-header"
								class="quality-table-header mar0B">
								<div class="col-sm-9 pad0L">
									<span>RD Quality Factors</span>
								</div>
								<div class="col-sm-3 right pad0R">
									<span id="rd-quality-change-btn"
										class="glyphicon glyphicon-random pad10R changeBtn"
										onClick="qualityChangeBtnClick(this.id);"></span>
								</div>
							</div>
							<div id="rd-quality-factors-table-container">
								<table class="table table-hover reuse-table mar0B">
									<tr class="none-border-top none-hover">
										<th class="wid20">Words</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_words" readonly>
										</td>
										<th class="wid20">Reuse</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_reuse_count" readonly>
										</td>
									</tr>
								</table>
								<table class="table table-hover reuse-table mar0B">
									<tr class="none-border-top none-hover">
										<th class="wid20">Figure</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_figure" readonly>
										</td>
										<th class="wid20">Table</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_table" readonly>
										</td>
									</tr>
								</table>
							</div>
						</div>
						<div id="sd-quality-factors-container"
							class="border-container hide">
							<div id="sd-quality-factors-table-header"
								class="quality-table-header mar0B">
								<div class="col-sm-9 pad0L">
									<span>SD Quality Factors</span>
								</div>
								<div class="col-sm-3 right pad0R">
									<span id="sd-quality-change-btn"
										class="glyphicon glyphicon-random pad10R changeBtn"
										onClick="qualityChangeBtnClick(this.id);"></span>
								</div>
							</div>
							<div id="sd-quality-factors-table-container">
								<table class="table table-hover reuse-table mar0B">
									<tr class="none-border-top none-hover">
										<th class="wid20">LOC</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_loc" readonly>
										</td>
										<th class="wid20">Reuse</th>
										<td class="wid30 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_reuse_count" readonly>
										</td>
									</tr>
								</table>
								<table class="table table-hover reuse-table mar0B">
									<tr class="none-hover">
										<th class="wid35">Halstead value</th>
										<td class="wid65 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_halstead" readonly>
										</td>
									</tr>
								</table>
								<table class="table table-hover reuse-table mar0B">
									<tr class="none-hover">
										<th class="wid35">Cyclomatic complexity</th>
										<td class="wid65 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_cyclomatic" readonly>
										</td>
									</tr>
								</table>
								<table class="table table-hover reuse-table mar5B">
									<tr class="none-hover">
										<th class="wid35">Fan-in/Fan-out</th>
										<td class="wid65 pad10R" bgcolor="#f9fafe"><input
											type="text" class="form-control" id="rd_fanin_fanout"
											readonly></td>
									</tr>
								</table>
							</div>
						</div>
						<div class="border-container mar0B">
							<div id="cotents-header" class="quality-table-header mar0B">
								<div class="col-sm-12 pad0L">
									<span>RD's Comment List</span>
								</div>
							</div>
							<table class="table table-hover mar0B">
								<thead>
									<tr>
										<th style="width: 16%; text-align: center;">User Name</th>
										<th style="width: 40%; text-align: center;">Comment</th>
										<th style="width: 31%; text-align: center;">Date</th>
										<th style="width: 3%;"></th>
									</tr>
								</thead>
							</table>
							<div class="comment-list-container">
								<table id="rd-comments-table"
									class="table table-hover table-bottom-line">
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>

					<div class="col-sm-6 pad0R pad5L">
						<div class="border-container">
							<div id="cotents-header" class="quality-table-header mar0B">
								<div class="col-sm-12 pad0L">
									<span>Contents</span>
								</div>
							</div>
							<div style="width: 100%; height: 200px; overflow-y: auto"
								class="pad5R pad5L rd-contents-container">
								<table id="rd-contents-table" class="table table-hover">
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
						<div class="border-container">
							<div id="contents-header" class="quality-table-header mar0B">
								<div class="col-sm-12 pad0L">
									<span>Extension Navigator</span> <span style="float: right;">
										<input type="radio" name="searchOp" value="Internal"
										checked="checked"><font size="2">&nbspRetrieved
											documents&nbsp&nbsp&nbsp</font> <input type="radio" name="searchOp"
										value="All"><font size="2">&nbspAll documents</font>
									</span>
								</div>
							</div>
							<div class="importer-button-panel">
								<button id="home-btn" class="btn btn-primary mar0R"
									type="button">Home</button>
								<button id="previous-btn" class="btn btn-primary mar0R"
									type="button">Previous RD</button>
								<button id="next-btn" class="btn btn-primary mar0R"
									type="button">Next RD</button>
								<button id="rd-select-btn" class="btn btn-primary mar0R"
									type="button">RD Selection</button>
								<button id="predecessor-btn" class="btn btn-primary mar0R"
									type="button">Predecessor</button>
								<button id="successor-btn" class="btn btn-primary" type="button">Successor</button>
							</div>
						</div>
						<div class="border-container mar0B">
							<div id="cotents-header" class="quality-table-header mar0B">
								<div class="col-sm-12 pad0L">
									<span>Selected RD List</span>
								</div>
							</div>
							<table class="table table-hover mar0B">
								<thead>
									<tr>
										<th style="width: 0.1%;"><input type=checkbox
											id="checkAll" onClick="toggle(this)" /></th>
										<th style="width: 15%; text-align: left;">ID(Version)</th>
										<th style="width: 50%; text-align: left;">Title</th>
									</tr>
								</thead>
							</table>
							<div class="selected-rd-list-container">
								<table id="rd_list_table"
									class="table table-hover table-bottom-line pad5L">
									<tbody>
									</tbody>
								</table>
							</div>
							<div class="divider"></div>
							<div class="button-panel">
								<button class="btn btn-info" id="remove_id_btn" type="button">Remove
									from List</button>
								<button class="btn btn-info" id="import_all_btn" type="button">Import
									RD List</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="hide" id="comment_importer" title="Final Import Stage">
			<div class="border-container">
				<table id="rd_comment_table_header" class="table table-hover mar0B"
					style="">
					<thead>
						<tr>
							<th style="text-align: left; width: 25%">RD ID<br />
								(version)
							</th>
							<th style="text-align: left; width: 40%">RD TITLE</th>
							<th style="text-align: center; width: 35%">COMMENT</th>
						</tr>
					</thead>
				</table>
				<table id="rd_comment_table"
					class="table table-hover comment-add-list-container"
					style="overflow-y: scroll">
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="button-panel pad0B">
				<button class="btn btn-info" id="final_import_btn" type="button">Final Confirm</button>
			</div>
		</div>



		<!--  importer pop-up ui implementation end -->

		<!-- nam start-->
		<script type="text/javascript">
		var newCheckList="";	
		var newreuseList="";
		var tmp_basket_id = [];	
		var basket_id = [];
		var basket_title = [];
		var basket_version = [];
		var basket_contents = [];
		var basket_comment = [];
		var check_basket = [];
		var checkTmp="";
		var titleColor = [];
		var home="";

		function toggle(source){
			for(var i=0, n=check_basket.length; i<n; i++){
				checkTmp = document.getElementById(check_basket[i]);
				checkTmp.checked = source.checked;
			}
			
		}
		
		$("#checkAll").click(function(){
			$("input:checkbox").prop('checked', this.checked);
		});
		
		function add_reuseList(z,y){
			var x = z.replace(".","-");
			var tmp = z.replace("_","(");
			var tmp2 = tmp + ")";
			newreuseList = "<tr><th id="+x+" style=text-align: left; width: 10%>"+tmp2+"</th><th id="+y+" style=text-align: center; width: 50%>"+y+"</th><th class=text-right><textarea style= overflow-y:scroll cols=25 rows=1 colspan=10 align=right class=comment_"+x+"></textarea></th></tr>";			
			$('#rd_comment_table').append(newreuseList);
		}
		
		$('#final_import_btn').on('click',function(){
			
			var id_arr = [];
			var comment_arr = [];
			var commentListId = $("#rd_comment_table tr");
			var temp = null;
			var tmpReplace = null;

			for(var j=0; j<commentListId.length; j++){
				tmpReplace = basket_id[j].replace(".","-");
				temp = $(".comment_"+tmpReplace).val();
				if(temp==""){
					basket_comment.push(temp);
				}
				else if(temp!=""){
					basket_comment.push(temp);
				}
				else{
				}
			}
			for(var i=0; i<basket_id.length; i++){
				id_arr.push(basket_id[i]);	
				comment_arr.push(basket_comment[i]);	
				
			}

			$.ajax(
					{
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/tohistory/',
						data : "id="+id_arr+"&rdComment="+comment_arr,
						success : function(data){
							
								if(data == true)
									alert("complete");
									
								$('#comment_importer').dialog('close');
						},
						error : function(e){
							console.log('ajax error');
						}
					});
		});
		
		$('#remove_id_btn').on('click', function(){
			var tableId = $('#rd_list_table');
			var tbody = tableId.find('tbody');
			var tableTR = tbody.find('td');
			var tableLength = $('#rd_list_table div').length;
			var flag = 0;
			var temp;
			
			for(var j=0; j<tableLength;j++){
				
				if(tableId.children().eq(j).find('input').prop("checked") == true)
				{
					flag = 1;	
				}
			}
			
			if(flag ==0){
				alert("하나 이상 체크하셔야 합니다.");
				return;
			}
			
			if(tableLength> 0){

				for(var t=0; t<tableLength;t++){
					if(tableId.children().eq(t).find('input').prop("checked") == true)
					{
						
						basket_id = jQuery.grep(basket_id, function(value){
							return value != (temp = tableId.children().eq(t).find('input').attr('id'));
						});
						tmp_basket_id = jQuery.grep(tmp_basket_id, function(value){
							return value != temp;
						});
						basket_title = jQuery.grep(basket_title, function(value){
							return value != tableId.children().eq(t).find('input').attr('name');
						});
						tableId.children().eq(t).remove();
						
						if(t!=tableLength-1){
							if(tableId.children().eq(t).find('input').prop("checked") == true){
							basket_id = jQuery.grep(basket_id, function(value){
								return value != (temp = tableId.children().eq(t).find('input').attr('id'));
							});
							tmp_basket_id = jQuery.grep(tmp_basket_id, function(value){
								return value != temp;
							});
							basket_title = jQuery.grep(basket_title, function(value){
								return value != tableId.children().eq(t).find('input').attr('name');
							});
							tableId.children().eq(t).remove();	
							}
						}
					}
					else
					{	
					}	
				}
				tableLength = $('#rd_list_table div').length;
				if(tableLength ==0){
					basket_id = [];
					basket_version = [];
					basket_title = [];
					basket_contents = [];
					tmp_basket_id = [];
				}
			}
			else{
				alert("리스트가 비었습니다.");
			}
		});
		
		$('#rd-select-btn').on('click', function(){
			var tableId = $('#rd_list_table');
			var tbody = tableId.find('tbody');
			var tableTR = tbody.find('td');
			var tableLength = $('#rd_list_table div').length;
			var idWithVersion = $('#rdID').val() +'(' + $('#rd_version').html() + ')';
			var idWithVersion2 = $('#rdID').val() +'_' + $('#rd_version').html();
			if(tableLength> 0){
				for(var t=0; t<=tableLength;t++){
					
					if(tableId.children().eq(t).find('input').attr('id') == idWithVersion)
					{
						alert("이미 리스트에 추가하였습니다.");
						return;
					}
				}
			}
			basket_id.push(idWithVersion2);
			basket_title.push($('#rdName').val());
			basket_contents.push($('#rdContents').val());
			basket_version.push($('#rd_version').html());
			add_checkList(idWithVersion, $('#rdName').val(), $('#rdContents').val(), $('#rd_version').html());
			tableId.append(newCheckList);
			
		});
		
		$('#import_all_btn').on('click',function(){
				
				if($('#rd_list_table div').length ==0)
				{
					alert("임포트할 대상이 없습니다.");
					return;
				}
				$('#reuse_importer').dialog('close');
				
				$('#comment_importer').dialog({
					modal : true,
					minWidth : 550,
					minHeight : 400,
					dialogClass : "",
					position: {
						my:"left top",
						at:"center top",
						of:'#tab-header'
					},
					show : "fadeIn"
						
				});
				
				for(var i=0; i<basket_id.length; i++){
					add_reuseList(basket_id[i],basket_title[i]);
				}
				
				$('.ui-dialog-content').removeClass('hide');
				
		});

		function add_checkList(x,y,z,q){
			var str = x.split('(');
			var p = str[0];
			newCheckList = '<div>'+'&nbsp&nbsp'+'<input type=checkbox id=' + x + '><span data-tooltip-text="'+z+'">'+'&nbsp&nbsp&nbsp&nbsp;' + x + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp;'+ y +'</span></div>';
			check_basket.push(x);				
		}
	
		function reuse_popup(x){
			check_basket=[];
			tmp_basket_id=[];
			basket_id=[];
			basket_version=[];
			basket_title=[];
			basket_comment=[];
			basket_contents=[];
			titleColor = [];
			home="";
			$("#rd_title").css('color', 'black');
			$('#rd_comment_table tbody').remove();
			var rd_contents_table = $('#rd-contents-table');
			var tableId = $('#rd_list_table');
			rd_contents_table.find('tbody').empty();
			tableId.empty();
			
			getRDStructureModel(x);
			
			$(function(){
				$('#reuse_importer').dialog({
					modal : true,
					resizable: false,
					minWidth: 1200,
					minHeight: 700,
					position: {
						my:"left top",
						at:"center bottom",
						of:'#back-btn'
					},
					dialogClass : "",
					show : "fadeIn"
				});
				$('.ui-dialog-content').removeClass('hide');
				$('#rd_comment_table tbody').empty();
			});
		};
		
		$('#dc-select-btn').on('click',function(){
			var rd_contents_table = $('#rd-contents-table');
			var tarRD_input = $('#rdID').val();
			var flag = 0;
	
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/whole_list/',
						data : "tarRD="+tarRD_input,
						success : function(data){
				
								var i;
								var flag_id = 0;
								var flag_title = 0;
								var flag_contents = 0;
								var flag_version = 0;
								var tableId = $('#rd_list_table');
								var tbody = tableId.find('tbody');
								var tableTR = tbody.find('td');
								var tableLength = $('#rd_list_table tr').length;
								
								for(i=0; i<data.length; i++){
									flag_id = 0;
									flag_title = 0;
									flag_version = 0;
									flag_contents = 0;
									for(var l=0; l<basket_id.length; l++){
									
										if(basket_id[l] == data[i].id)
											flag_id = 1;
										if(basket_title[l] == data[i].title)
											flag_title = 1;
										if(basket_contents[l] == data[i].contents)
											flag_contents = 1;
										if(basket_version[l] == data[i].version)
											flag_version = 1;
									}
									if(flag_id == 0){
										basket_id.push(
											data[i].id
										);
									}
									if(flag_title == 0){
										basket_title.push(
											data[i].title
										);
									}
									if(flag_contents == 0){
										basket_contents.push(
											data[i].contents
										);
									}
									if(flag_version == 0){
										basket_version.push(
											data[i].version
										);											
									}
								
										
								}
								
								if(tableLength> 0){

								for(var t=0; t<=tableLength;t++){
									for(var k=0; k<basket_id.length; k++){
											if(tableId.children().eq(t).find('input').attr('id') == basket_id[k])
											{
												tmp_basket_id.push(basket_id[k]);
												
											}
									}
									}
								}
								
								for(var t=0; t<basket_id.length; t++){
									flag = 0;
									
									for(var k=0; k<tmp_basket_id.length; k++){
										if(basket_id[t] == tmp_basket_id[k]){
											flag =1;
										}
									}
									
									if(flag != 1){
										add_checkList(basket_id[t], basket_title[t], basket_contents[t], basket_version[t]);
										tableId.append(newCheckList);
									}
								}
								
								alert("중복을 제외한 문서 전체를 임포트 하였습니다.");	
						},
						error : function(e){
							console.log('ajax error');
						}
					});
		});
		
		$('#home-btn').on('click',function(){
			var base_id = $('#rdHome').val();
			getRDStructureModel(base_id);
			
		});
		
		$('#next-btn').on('click',function(){
			var rd_contents_table = $('#rd-contents-table');
			var tarRD_input = $('#rdID').val();
			var rd_comments_table = $('#rd-comments-table');
			var check_id_dupl;
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/intra_next/',
						data : "tarRD="+tarRD_input,
						success : function(data){
								if(!$.trim(data)){
									alert("문서의 끝 입니다.");
								}
								else{
									rd_comments_table.find('tbody').empty();
							rd_contents_table.find('tbody').empty();
							$('#rd_version').html(data.version);
							$('#rd_title').html(data.title);
							$('#rd_creator').html(data.creator);
							$('#rd_created_date').html(data.createdDate);
							$('#rdID').val(data.id);
							$('#rdName').val(data.title);
							$('#rdContents').val(data.contents);
							rd_contents_table.find('tbody').append(data.contents);
							$('#rd_words').val(data.annotation.numOfWords);
							$('#rd_figure').val(data.annotation.numOfImages);
							$('#rd_table').val(data.annotation.numOfTables);
							$('#rd_reuse_count').val(data.rdReuseCount);
							rd_comments_table.find('tbody').empty();
							for(var i=0; i<data.rdCommentList.length; i++){
								rd_comments_table.find('tbody').append('<tr><td style="width: 15%; text-align:center ">'+data.rdCommentList[i].userId+'</td><td style="word-break:break-all; width: 40%; text-align:center">'+data.rdCommentList[i].comment+ 
								'</td><td style="width: 33%; text-align:center">'+formatDate(new Date(data.rdCommentList[i].updateDate))+'</td></tr>');
							}
							$("#rd_title").css('color', 'black');
							check_id_dupl = $('#rdID').val() + '_' + $('#rd_version').html();
							for(var i=0; i<basket_id.length; i++){
					
								if(basket_id[i] == check_id_dupl){
									$("#rd_title").css('color', 'blue');
								}
							}
							
								}
						},
						error : function(e){
							console.log('ajax error');
						}
					});
				
				
		});
		
		var searchOp_input = null;
		$('input[name=searchOp]').click(function(){
			searchOp_input = this.value;
		});
		$('#previous-btn').on('click',function(){
			
			var rd_contents_table = $('#rd-contents-table');
			var tarRD_input = $('#rdID').val();
			var rd_comments_table = $('#rd-comments-table');
			var check_id_dupl;
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/intra_previous/',
						data : "tarRD="+tarRD_input,
						success : function(data){
								if(!$.trim(data)){
									alert("문서의 처음 입니다.");
								}
								else{
									rd_comments_table.find('tbody').empty();
							rd_contents_table.find('tbody').empty();
							$('#rd_version').html(data.version);
							$('#rd_title').html(data.title);
							$('#rd_creator').html(data.creator);
							$('#rd_created_date').html(data.createdDate);
							$('#rdID').val(data.id);
							$('#rdName').val(data.title);
							$('#rdContents').val(data.contents);
							rd_contents_table.find('tbody').append(data.contents);
							$('#rd_words').val(data.annotation.numOfWords);
							$('#rd_figure').val(data.annotation.numOfImages);
							$('#rd_table').val(data.annotation.numOfTables);
							$('#rd_reuse_count').val(data.rdReuseCount);
							rd_comments_table.find('tbody').empty();
							for(var i=0; i<data.rdCommentList.length; i++){
								rd_comments_table.find('tbody').append('<tr><td style="width: 15%; text-align:center ">'+data.rdCommentList[i].userId+'</td><td style="word-break:break-all; width: 40%; text-align:center">'+data.rdCommentList[i].comment+ 
								'</td><td style="width: 33%; text-align:center">'+formatDate(new Date(data.rdCommentList[i].updateDate))+'</td></tr>');
							}
							$("#rd_title").css('color', 'black');
							check_id_dupl = $('#rdID').val() + '_' + $('#rd_version').html();
							for(var i=0; i<basket_id.length; i++){
					
								if(basket_id[i] == check_id_dupl){
									$("#rd_title").css('color', 'blue');
								}
							}
							
								}
						},
						error : function(e){
							console.log('ajax error');
						}
					});
				
		});
		
		$('#predecessor-btn').on('click',function(){
			
			var rd_contents_table = $('#rd-contents-table');
			var tarRD_input = $('#rdID').val();
			var rd_comments_table = $('#rd-comments-table');
			var check_id_dupl;
			
			
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/inter_predecessor/',
						data : {tarRD : tarRD_input , searchOp :searchOp_input},
						success : function(data){
								if(!$.trim(data)){
									alert("RD의 첫 번째 버전 혹은 탐색 가능한 문서의 처음입니다.");
								}
								else{
									rd_comments_table.find('tbody').empty();
							rd_contents_table.find('tbody').empty();
							$('#rd_version').html(data.version);
							
							$('#rd_title').html(data.title);
							$('#rd_creator').html(data.creator);
							$('#rd_created_date').html(data.createdDate);
							$('#rdID').val(data.id);
							$('#rdName').val(data.title);
							$('#rdContents').val(data.contents);
							rd_contents_table.find('tbody').append(data.contents);
							$('#rd_words').val(data.annotation.numOfWords);
							$('#rd_figure').val(data.annotation.numOfImages);
							$('#rd_table').val(data.annotation.numOfTables);
							$('#rd_reuse_count').val(data.rdReuseCount);
							rd_comments_table.find('tbody').empty();
							for(var i=0; i<data.rdCommentList.length; i++){
								rd_comments_table.find('tbody').append('<tr><td style="width: 15%; text-align:center ">'+data.rdCommentList[i].userId+'</td><td style="word-break:break-all; width: 40%; text-align:center">'+data.rdCommentList[i].comment+ 
								'</td><td style="width: 33%; text-align:center">'+formatDate(new Date(data.rdCommentList[i].updateDate))+'</td></tr>');
							}
							
							$("#rd_title").css('color', 'black');
							check_id_dupl = $('#rdID').val() + '_' + $('#rd_version').html();
							for(var i=0; i<basket_id.length; i++){
					
								if(basket_id[i] == check_id_dupl){
									$("#rd_title").css('color', 'blue');
								}
							}
				
							
							}
						},
						error : function(e){
							console.log('ajax error');
						}
					});
				
		});
		
		$('#successor-btn').on('click',function(){
			
			var rd_contents_table = $('#rd-contents-table');
			var tarRD_input = $('#rdID').val();
			var rd_comments_table = $('#rd-comments-table');
			var check_id_dupl;
			
				
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/inter_successor/',
						data : {tarRD : tarRD_input , searchOp :searchOp_input},
						success : function(data){
								if(!$.trim(data)){
									alert("RD의 마지막 버전 혹은 탐색 가능한 문서의 마지막입니다.");
								}
								else{
									rd_comments_table.find('tbody').empty();
							rd_contents_table.find('tbody').empty();
							$('#rd_version').html(data.version);
							$('#rd_title').html(data.title);
							
							$('#rd_creator').html(data.creator);
							$('#rd_created_date').html(data.createdDate);
							$('#rdID').val(data.id);
							$('#rdName').val(data.title);
							$('#rdContents').val(data.contents);
							rd_contents_table.find('tbody').append(data.contents);
							$('#rd_words').val(data.annotation.numOfWords);
							$('#rd_figure').val(data.annotation.numOfImages);
							$('#rd_table').val(data.annotation.numOfTables);
							$('#rd_reuse_count').val(data.rdReuseCount);
							rd_comments_table.find('tbody').empty();
							for(var i=0; i<data.rdCommentList.length; i++){
								rd_comments_table.find('tbody').append('<tr><td style="width: 15%; text-align:center ">'+data.rdCommentList[i].userId+'</td><td style="word-break:break-all; width: 40%; text-align:center">'+data.rdCommentList[i].comment+ 
								'</td><td style="width: 33%; text-align:center">'+formatDate(new Date(data.rdCommentList[i].updateDate))+'</td></tr>');
							}
							$("#rd_title").css('color', 'black');
							
								check_id_dupl = $('#rdID').val() + '_' + $('#rd_version').html();
								for(var i=0; i<basket_id.length; i++){
									if(basket_id[i] == check_id_dupl){
										
										$("#rd_title").css('color', 'blue');
									}
								}
							}
						},
						error : function(e){
							console.log('ajax error');
						}
					});
				
		});
		
		function getRDStructureModel(x) {
			var tarRD_input = x;
			var count = 0;
			var rd_contents_table = $('#rd-contents-table');
			var tableId = $('#rd_list_table');
			var rd_comments_table = $('#rd-comments-table');
			$('input:radio[name="searchOp"][value="Internal"]').prop("checked",true);
			
			var rd_id_tmp;
			
			
			$.ajax(
					{
					
						type : "POST",
						url : '${pageContext.servletContext.contextPath}/rd/search/result/importer/',
						data : "tarRD="+tarRD_input,
						success : function(data){
							console.log(data);
							rd_comments_table.find('tbody').empty();
							rd_contents_table.find('tbody').empty();
							$('#rdHome').val(data.id);
							$('#rd_version').html(data.version);
							rd_id_tmp = $('#rd_title').html(data.title);
							$('#rd_creator').html(data.creator);
							$('#rd_created_date').html(data.createdDate);
							$('#rdID').val(data.id);
							$('#rdName').val(data.title);
							$('#rdContents').val(data.contents);
							rd_contents_table.find('tbody').append(data.contents);
							$('#rd_words').val(data.annotation.numOfWords);
							$('#rd_figure').val(data.annotation.numOfImages);
							$('#rd_table').val(data.annotation.numOfTables);
							$('#rd_reuse_count').val(data.rdReuseCount);
							rd_comments_table.find('tbody').empty();
							for(var i=0; i<data.rdCommentList.length; i++){
								rd_comments_table.find('tbody').append('<tr><td style="width: 15%; text-align:center ">'+data.rdCommentList[i].userId+'</td><td style="word-break:break-all; width: 40%; text-align:center">'+data.rdCommentList[i].comment+ 
								'</td><td style="width: 33%; text-align:center">'+formatDate(new Date(data.rdCommentList[i].updateDate))+'</td></tr>');
							}
							$("#rd_title").css('color', 'black');
							check_id_dupl = $('#rdID').val() + '_' + $('#rd_version').html();
							for(var i=0; i<basket_id.length; i++){
					
								if(basket_id[i] == check_id_dupl){
									$("#rd_title").css('color', 'blue');
								}
							}
							
						},
						error : function(e){
							console.log('ajax error');
						}
					});
				
		}
		
		function formatDate(date) {
  			var monthNames = [
   			 "January", "February", "March",
   			 "April", "May", "June", "July",
    			"August", "September", "October",
    			"November", "December"
  			];

  			var day = date.getDate();
  			var monthIndex = date.getMonth();
  			var year = date.getFullYear();
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			
  			return '[Date: ' + day + ' ' + monthNames[monthIndex] + ' ' + year + '  ' + hour + ':' + minute+ ':'+ second+']';
		}
		</script>
		<!-- nam end -->

		<script type="text/javascript"
			?
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/uniform/uniform.js"></script>
		<script type="text/javascript">
			$(function() {
				"use strict";
				$('input[type="checkbox"].custom-checkbox').uniform();
			});
		</script>


		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tabs-ui/tabs.js"></script>
		<script type="text/javascript">
			$(function() {
				"use strict";
				$(".nav-tabs").tabs();
			});

			$(function() {
				"use strict";
				$(".tabs-hover").tabs({
					event : "mouseover"
				});
			});
		</script>



		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets-minified/admin-all-demo.js"></script>

		<script>
			function redirectToResult(base, page) {
				$('#resultModel').attr(
						'action',
						'${pageContext.servletContext.contextPath}/rd/search/result/'
								+ base + '/' + page).submit();
			}

            $('#ulMainTab').html(
                $('#ulMainTab').children('li').sort(function (a, b) {
                return $(b).val()  -  $(a).val();
            }) // End Sort
            ); // End HTML

		</script>
	</div>
	
	<div id="y-ext-contextmenu">
		<table height=100%>
			<tr>
				<td class="y-ext-menu">
					<span id="y-ext-menu-p">Search pattern examples</span>
				</td>
			</tr>
			<tr>
				<td class="y-ext-menu">
					<span id="y-ext-menu-c">Code clone detection</span>
				</td>
			</tr>
		</table>
	</div>

<!-- ---------------------------- -->
<!-- Coded by Yonsei - 2019-01-02 -->
<!-- ---------------------------- -->
<!-- Modal -->
<div class="y-modal-harvest-area">
	<table class="y-modal-harvest-container">
		<tr>
			<td class="y-modal-harvest-handle">
				SD Harvest
			</td>
		</tr>
		<%
		Object[][] stepList = {
				{ "Connecting to repositories:", "connecting.." },
				{ "Download project list:", "downloading.." },
				{ "Download project archives:", "downloading.." },
				{ "Unzip project archives:", "unzipping.." },
				{ "Collect java files:", "collecting.." },
				{ "Analyze features:", "analyzing.." },
				{ "Analyze patterns:", "analyzing.." },
				{ "Create indexes for SD:", "creating.." },
				{ "Update database:", "updating.." },
				{ "Finish:", "finishing.." },
		};
		
		for(int idx = 0; idx < stepList.length; idx++) {
		%>
			<tr class="y-modal-harvest-step-<%=idx%> y-modal-harvest-step">
				<td class="y-modal-harvest-data">
					<span class="y-modal-harvest-title">
					<%=stepList[idx][0]%>
					</span>
					<span class="y-modal-harvest-step-<%=idx%>-result">
					<%=stepList[idx][1]%>
					</span>
				</td>
			</tr>
		<%
		}
		%>
		<tr>
			<td class="y-modal-harvest-bottom">
				<button type="button" class="btn btn-info" id="y-do-harvest">Do harvest!</button>
				<button type="button" class="btn btn-info" id="y-close-harvest">Close</button>
			</td>
		</tr>
	</table>
</div>
<!-- Modal // -->
<!-- ------------------------------- -->
<!-- Coded by Yonsei - 2019-01-02 // -->
<!-- ------------------------------- -->
	

</body>

</html>

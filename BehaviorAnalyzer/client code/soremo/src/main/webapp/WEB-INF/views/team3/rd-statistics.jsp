<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%@ include file="../commons/meta.jsp"%>

<!-- core css, resource 정의 -->
<%@ include file="../commons/core-css.jsp"%>

<!-- user css 정의 -->
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/resources/css/custom_dialog.css">

<!-- core javascript 정의 -->
<%@ include file="../commons/core-js.jsp"%>
	
<!-- user javascript 정의 -->

<style type="text/css">

</style>

<script>

var typeArr = ['project', 'source', 'pattern'];

function showList(type) {
	console.log(type);
	for(var i=0; i<3; i++) {
		$("#"+typeArr[i]+"-list").css("display", "none");
		$("#"+typeArr[i]+"-list-title").css("display", "none");
	}
	
	$("#"+type+"-list").css("display", "table");
	$("#"+type+"-list-title").css("display", "block");
}

</script>

</head>

<body>
	<div id="sb-site">
	
		<div id="page-wrapper">
	
			<div id="page-content-wrapper">
				<div id="page-content">
					<div class="container pad10A">
						<div class="content-box">
							
							<ul class="pager" style="margin:10px 0px 0px 10px;">
								<li class="previous">
									<a href="${pageContext.servletContext.contextPath}/rd/search/?keyword=${searchDataModel.base_keyword}">←Back</a>
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
							
							<div class="y-fs20 y-fw700 y-font-lato mar15L mar10T">
								RD/SD Patterns
							</div>
							
							<div class="pad10T">
								<div class="y-statistics-item-area y-statistics-item-area-border">
									<span class="y-statistics-item-box" onclick="showList('project')">
										<span class="y-statistics-item-title"># of projects:</span>
										<span class="y-statistics-item-count" id="y-item-1">10,353</span>
									</span>
									<span class="y-statistics-item-box" onclick="showList('source')">
										<span class="y-statistics-item-title"># of sources:</span>
										<span class="y-statistics-item-count" id="y-item-2">1,243,631</span>
									</span>
									<span class="y-statistics-item-box" onclick="showList('pattern')">
										<span class="y-statistics-item-title"># of patterns:</span>
										<span class="y-statistics-item-count" id="y-item-3">8,534,742</span>
									</span>
								</div>
							</div>
							
							<!--  project list table -->							
							<div class="y-fs18 y-fw700 y-font-lato mar15L mar15T mar5B" id="project-list-title">
								Project List
							</div>
							<table id="project-list" class="table table-hover text-center">
								<tbody>
									<tr>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="60px">Num.</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="150px">Project Name</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="150px">File Name</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="100px">File Size</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" >Description</th>
									</tr>
									
									<tr>
										<td style="text-align:center">
											1
										</td>
										<td style="text-align: center; border-left: 5px solid transparent; cursor:pointer" onclick="showList('source')">
											<h5 class="font-black">
												JxBrowser
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxBrowser.jar
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											10,234 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											webview
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											2
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												jedit
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jedit.jar
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											1,253 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											editor
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											3
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												jcodeclone
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jclone.jar
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											5,243 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											clone detector
										</td>
									</tr>
								</tbody>
							</table>
							
							<!--  source list table -->
							<div class="y-fs18 y-fw700 y-font-lato mar15L mar15T mar5B" id="source-list-title" style="display:none;">
								Source List
							</div>
							<table id="source-list" class="table table-hover text-center" style="display:none;">
								<tbody>
									<tr>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="60px">Num.</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" >Source File Name</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="100px">File Size</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="150px">Project Name</th>
									</tr>
									
									<tr>
										<td style="text-align:center">
											1
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												jxbrowser.java
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											14,234 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxbrowser
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											2
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												jxmain.java
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											1,634 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxbrowser
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											3
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												jxcontents.java
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											4,536 bytes
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxbrowser
										</td>
									</tr>
								</tbody>
							</table>
							
							<!--  pattern list table -->
							<div class="y-fs18 y-fw700 y-font-lato mar15L mar15T mar5B" id="pattern-list-title" style="display:none;">
								Pattern List
							</div>
							<table id="pattern-list" class="table table-hover text-center" style="display:none;">
								<tbody>
									<tr>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="60px">Num.</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" >Pattern Info</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="200px">Class Name</th>
										<th style="font-weight: bold; text-align: center; border-left: 5px solid transparent;" width="200px">Source File</th>
									</tr>
									
									<tr>
										<td style="text-align:center">
											1
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												118, MCE, ..., ..., 60, ..., 1
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											java.lang.String
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxbrowser.java
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											2
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												119, MCE, ..., ..., 57, ..., 5
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											DBDataObject
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxmain.java
										</td>
									</tr>
									
									<tr>
										<td style="text-align:center">
											3
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											<h5 class="font-black">
												120, VDE, ..., ..., 61, ..., 16
											</h5>
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											Accesscode
										</td>
										<td style="text-align: center; border-left: 5px solid transparent;">
											jxcontents.java
										</td>
									</tr>
								</tbody>
							</table>
							
							<div class="button-pane mrg20T">
								<ul class="pagination">
									<li class="active">
										<a href="">1<span class="sr-only">(current)</span></a>
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/resizable.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/draggable.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/sortable.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/interactions-ui/selectable.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/dialog/dialog.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/multi-select/multiselect.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/datepicker/datepicker.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tabs-ui/tabs.js"></script>
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets-minified/admin-all-demo.js"></script>
			
		<!-- user define javascript functions -->
		<script type="text/javascript">
		
		
		
		</script>
	</div>
</body>

</html>

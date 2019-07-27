<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="../commons/meta.jsp"%>
<%@ include file="../commons/core-css.jsp"%>
<%@ include file="../commons/core-js.jsp"%>

<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tree/jstree.min.js"></script>
<style type="text/css">
.rd-image {
	width: 160px;
}
</style>
</head>
<body>
	<div id="sb-site">
		<%@ include file="../commons/ui/dashboard-compact.jsp"%>
		<%@ include file="../commons/ui/loader.jsp"%>
		<div id="page-wrapper">
			<div id="page-content-wrapper">
				<div id="page-content">
					<div class="container pad10A">
						<div class="content-box">
							<div class="pad10A">
								<ul class="pager">
									<li class="previous"><a href="javascript:history.back();">←Back</a></li>
								</ul>
								<h4 class="text-center">Detail Information</h4>
							</div>
							<div class="panel no-border">
								<div class="panel-body">
									<div class="example-box-wrapper">
										<form class="form-horizontal bordered-row" method="post" action="${pageContext.servletContext.contextPath}/rd/search/result/importer/${tarRD.getRDID()}">
											<div class="form-group">
												<label class="col-sm-2 control-label">Description</label>
												<div class="col-sm-8">
													<textarea name="description" rows="4"
														class="form-control textarea-no-resize"></textarea>
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">Project</label>
												<div class="col-sm-3">
													<input type="text" name="project" class="form-control"
														value="${tarRD.getTitle() }">
												</div>
												<label class="col-sm-2 control-label">Domain</label>
												<div class="col-sm-3">
													<input type="text" name="domain" class="form-control"
														value="">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">Author</label>
												<div class="col-sm-3">
													<input type="text" name="author" class="form-control"
														value="${tarRD.getCreator() }">
												</div>
												<label class="col-sm-2 control-label">Author Role</label>
												<div class="col-sm-3">
													<input type="text" name="email" class="form-control"
														value="">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">Contents</label>
												<div class="col-sm-8">
													<textarea name="contents" rows="14"	class="form-control textarea-no-resize">${tarRD.getContentsPlain()}</textarea>
												</div>
											</div>
											<!--YS code -->
											<div class="form-group">
												<label class="col-sm-2 control-label">Reuser Comments</label>
												<div class="col-sm-8">
													<textarea name="contents" rows="8"	class="form-control textarea-no-resize">${reuserComments}</textarea>
												</div>
											</div>

											<div class="form-group">
												<label class="col-sm-2 control-label">Number of
													Pages</label>
												<div class="col-sm-3">
													<input type="text" class="form-control" name="pages">
												</div>
												<label class="col-sm-2 control-label">Page
													Complexity</label>
												<div class="col-sm-3">
													<input type="text" name="pagecomplexity"
														class="form-control">
												</div>
											</div>
											<div class="form-group">
												<label class="col-sm-2 control-label">Contents
													Diversity</label>
												<div class="col-sm-3">
													<input type="text" name="contentsdiversity"
														class="form-control">
												</div>
												<label class="col-sm-2 control-label">Template
													Complience</label>
												<div class="col-sm-3">
													<input type="text" name="templatecomplience"
														class="form-control">
												</div>
											</div>

											<div class="button-pane mrg20T">
												<button class="btn btn-info" type="submit">Reuse</button>
											</div>
										</form>
									</div>
								</div>
							</div>
						</div>


					</div>
				</div>
			</div>
		</div>		

		<script type="text/javascript"
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
			function redirectToSearch() {
				$('#resultModel')
						.attr('action',
								'${pageContext.servletContext.contextPath}/rd/search/extended/')
						.submit();
			}
		</script>
	</div>

</body>

</html>

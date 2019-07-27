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
</head>
<body>
	<div id="sb-site">
		<%@ include file="../commons/ui/dashboard-compact.jsp"%>
		<%@ include file="../commons/ui/loader.jsp"%>
		<c:import url="http://dev.userinsight.co.kr:8888/view/header"></c:import>
		<c:import url="http://dev.userinsight.co.kr:8888/view/sidebar"></c:import>
	</div>
</body>

</html>

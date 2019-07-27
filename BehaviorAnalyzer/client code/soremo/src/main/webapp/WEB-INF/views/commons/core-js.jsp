<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- core javascript 정의 -->

<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/assets-minified/js-core.js"></script>
<script type="text/javascript" 
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript" 	
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" 
	src="${pageContext.servletContext.contextPath}/resources/js/MooTools-Core-1.6.0.js"></script>
<script type="text/javascript" 
	src="${pageContext.servletContext.contextPath}/resources/js/jquery-ui.js"></script>
<script type="text/javascript" 
	src="${pageContext.servletContext.contextPath}/resources/js/jquery.multi-select.js"></script>
<script type="text/javascript" 
	src="${pageContext.servletContext.contextPath}/resources/js/bootstrap-datepicker.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tree/jstree.min.js"></script>
<script type="text/javascript"
	src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tree/jstree.min.js"></script>
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

<script type="text/javascript">
	$(window).load(function() {
		setTimeout(function() {
			$('#loading').fadeOut(400, "linear");
		}, 300);
	});
</script>
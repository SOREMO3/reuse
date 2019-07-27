<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="../commons/meta.jsp" %>
	<%@ include file="../commons/core-css.jsp" %>
	<%@ include file="../commons/core-js.jsp" %>
</head>

<body>
    <div id="sb-site">
        <%@ include file="../commons/ui/dashboard-compact.jsp" %>
		<%@ include file="../commons/ui/loader.jsp" %>

        <div id="page-wrapper">
        	<%@ include file="../commons/ui/header-ui.jsp" %>
			<%@ include file="../commons/ui/sidemenu-ui.jsp" %>
            
            <div id="page-content-wrapper">
                <div id="page-content">
                    <div class="container pad10A">
                        <div id="page-title">
                            <h2>Page title</h2>
                        </div>
                        
                    </div>
                    
                </div>
            </div>
        </div>

 
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets-minified/admin-all-demo.js"></script>
    </div>
</body>

</html>

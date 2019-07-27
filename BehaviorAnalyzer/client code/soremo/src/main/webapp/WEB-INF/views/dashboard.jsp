<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@ include file="commons/meta.jsp" %>
	<%@ include file="commons/core-css.jsp" %>
	<%@ include file="commons/core-js.jsp" %>
</head>

<body>
    <div id="sb-site">
        <%@ include file="commons/ui/dashboard-compact.jsp" %>
		<%@ include file="commons/ui/loader.jsp" %>

        <div id="page-wrapper">
        	<%@ include file="commons/ui/header-ui.jsp" %>
			<%@ include file="commons/ui/sidemenu-ui.jsp" %>
            
            <div id="page-content-wrapper">
                <div id="page-content">
                    <div class="container pad10A">
                        <div id="page-title">
                            <h2>내 프로젝트</h2>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="content-box  mrg15B" style="height:300px;">
                                    <h3 class="content-box-header clearfix">
                                        Project Alpha
                                        <small>2016-08-01 ~ 2016.10.30</small>
                                        <div class="font-size-11 float-right">
                                            <a href="#" title="">
                                                <i class="glyph-icon opacity-hover icon-cog"></i>
                                            </a>
                                        </div>
                                    </h3>
                                    <div class="content-box-wrapper text-center clearfix">
                                        <div style="height:148px;overflow-y:scroll">
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="PM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial1.jpg" alt="">
                                                <div class="small-badge bg-red"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="RM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial2.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="RM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial3.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="TA.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial4.jpg" alt="">
                                                <div class="small-badge bg-green"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="QA.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial5.jpg" alt="">
                                                <div class="small-badge bg-blue"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-content pad15A bg-black">
                                        <div class="center-vertical">

                                            <ul class="center-content list-group list-group-separator row mrg0A">
                                                <li class="col-md-12">
                                                    <a href="edit.html" title="">
                                                        <i class="glyph-icon opacity-60 icon-paper-plane font-size-28"></i>
                                                        <p class="mrg5T">진행하기</p>
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="content-box mrg15B" style="height:300px;">
                                    <h3 class="content-box-header clearfix">
                                        Project Beta
                                        <small>2016-07-01 ~ 2017-01-01</small>
                                        <div class="font-size-11 float-right">
                                            <a href="#" title="">
                                                <i class="glyph-icon opacity-hover icon-cog"></i>
                                            </a>
                                        </div>
                                    </h3>
                                    <div class="content-box-wrapper text-center clearfix">
                                        <div style="height:148px;overflow-y:scroll">
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial1.jpg" alt="">
                                                <div class="small-badge bg-red"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial2.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial3.jpg" alt="">
                                                <div class="small-badge bg-red"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial4.jpg" alt="">
                                                <div class="small-badge bg-green"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial5.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial6.jpg" alt="">
                                                <div class="small-badge bg-red"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial7.jpg" alt="">
                                                <div class="small-badge bg-green"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial8.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial8.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial8.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-content pad15A bg-black">
                                        <div class="center-vertical">
                                            <ul class="center-content list-group list-group-separator row mrg0A">
                                                <li class="col-md-12">
                                                    <a href="edit.html" title="">
                                                        <i class="glyph-icon opacity-60 icon-paper-plane font-size-28"></i>
                                                        <p class="mrg5T">진행하기</p>
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="container pad10A">
                        <div id="page-title">
                            <h2>내 연구노트</h2>
                        </div>
                        <div class="row">
                            <div class="col-md-4">
                                <div class="content-box  mrg15B" style="height:300px;">
                                    <h3 class="content-box-header clearfix">
                                        Project 2016-08-01 ~ 2016.10.30</small>
                                        <div class="font-size-11 float-right">
                                            <a href="#" title="">
                                                <i class="glyph-icon opacity-hover icon-cog"></i>
                                            </a>
                                        </div>
                                    </h3>
                                    <div class="content-box-wrapper text-center clearfix">
                                        <div style="height:148px;overflow-y:scroll">
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="PM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial1.jpg" alt="">
                                                <div class="small-badge bg-red"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="RM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial2.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="RM.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial3.jpg" alt="">
                                                <div class="small-badge bg-orange"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="TA.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial4.jpg" alt="">
                                                <div class="small-badge bg-green"></div>
                                            </div>
                                            <div class="status-badge mrg10A" data-toggle="tooltip" data-placement="top" data-original-title="QA.íê¸¸ë">
                                                <img class="img-circle" width="40" src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/people/testimonial5.jpg" alt="">
                                                <div class="small-badge bg-blue"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel-content pad15A bg-black">
                                        <div class="center-vertical">

                                            <ul class="center-content list-group list-group-separator row mrg0A">
                                                <li class="col-md-12">
                                                    <a href="edit.html" title="">
                                                        <i class="glyph-icon opacity-60 icon-paper-plane font-size-28"></i>
                                                        <p class="mrg5T">진행하기</p>
                                                    </a>
                                                </li>
                                            </ul>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>


		<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets/widgets/tooltip/tooltip.js"></script>
		<script type="text/javascript">
			$('[data-toggle="tooltip"]').tooltip();
		</script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets/widgets/chosen/chosen.js"></script>
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets/widgets/chosen/chosen-demo.js"></script>


        <!-- JS Demo -->
        <script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets-minified/admin-all-demo.js"></script>


    </div>
</body>

</html>

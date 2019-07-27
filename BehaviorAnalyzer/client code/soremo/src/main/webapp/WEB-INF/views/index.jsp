<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
<style>
html, body {
    height: 100%;
}

body {
    background: #fff;
    overflow: hidden;
}
/* Loading Spinner */
.spinner {
	margin: 0;
	width: 70px;
	height: 18px;
	margin: -35px 0 0 -9px;
	position: absolute;
	top: 50%;
	left: 50%;
	text-align: center
}

.spinner>div {
	width: 18px;
	height: 18px;
	background-color: #333;
	border-radius: 100%;
	display: inline-block;
	-webkit-animation: bouncedelay 1.4s infinite ease-in-out;
	animation: bouncedelay 1.4s infinite ease-in-out;
	-webkit-animation-fill-mode: both;
	animation-fill-mode: both
}

.spinner .bounce1 {
	-webkit-animation-delay: -.32s;
	animation-delay: -.32s
}

.spinner .bounce2 {
	-webkit-animation-delay: -.16s;
	animation-delay: -.16s
}

@ -webkit-keyframes bouncedelay { 0%, 80%, 100% {
	-webkit-transform: scale(0.0)
}

40%
{ -webkit-transform: scale (1 .0)     } }
@ keyframes bouncedelay { 0%, 80%, 100% {
	transform: scale(0.0);
	-webkit-transform: scale(0.0)
}
40%
{ transform : scale (1.0);        -webkit-transform : scale (1.0)        } }
</style>
<%@ include file="commons/meta.jsp"%>
<%@ include file="commons/core-css.jsp"%>
<%@ include file="commons/core-js.jsp"%>
</head>

<body>
	<%@ include file="commons/ui/dashboard-compact.jsp" %>
	<%@ include file="commons/ui/loader.jsp" %>

	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets/widgets/wow/wow.js"></script>
	<script type="text/javascript">
		wow = new WOW({
			animateClass : 'animated',
			offset : 100
		});
		wow.init();
	</script>

	<img src="${pageContext.servletContext.contextPath}/resources/assets/image-resources/blurred-bg/blurred-bg-9.jpg"
		class="login-img wow fadeIn" alt="">

	<div class="center-vertical">
		<div class="center-content row">
			<div class="col-md-4 col-sm-5 col-xs-11 col-lg-3 center-margin ">
				<div
					class="content-box wow bounceInDown modal-content pad20A clearfix row">
					<div class="col-md-2"></div>
					<div class="col-md-10">
						<div class="meta-box text-left">
							<h3 class="meta-heading font-size-16">Login</h3>
							<h4 class="meta-subheading font-size-13 font-gray">Sofrware
								Real Monitoring System</h4>
							<div class="divider"></div>
							<form action="${pageContext.servletContext.contextPath}/dashboard" class="form-inline pad10T"
								method="">
								<div class="form-group" style="margin-bottom: 10px">
									<div class="input-group">
										<span class="input-group-addon"> <i
											class="glyph-icon icon-user"></i></span> <input type="text"
											placeholder="Username" class="form-control">
									</div>
								</div>
								<div class="form-group">
									<div class="input-group">
										<span class="input-group-addon"> <i
											class="glyph-icon icon-lock"></i></span> <input type="text"
											placeholder="Password" class="form-control">
									</div>
								</div>
								<div class="button-pane mrg20T">
									<button class="btn btn-info">로그인</button>
									<button class="btn btn-link font-gray-dark">회원가입</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- WIDGETS -->
	<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/assets/bootstrap/js/bootstrap.js"></script>
</body>
</html>

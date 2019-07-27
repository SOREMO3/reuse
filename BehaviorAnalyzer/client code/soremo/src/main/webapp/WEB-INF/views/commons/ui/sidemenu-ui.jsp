<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="page-sidebar">
	<div class="scroll-sidebar"
		style="border-color: #dfe8f1; border-right-style: solid; border-right-width: 1px">
		<ul id="sidebar-menu">
			<li class="header"><span>Research Descriptor</span></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/rd/search/"> <i
					class="glyph-icon icon-search"></i> <span>RD 검색</span>
			</a></li>
			<li><a href="${pageContext.servletContext.contextPath}/rd/edit">
					<i class="glyph-icon icon-edit"></i> <span>RD 저작</span>
			</a></li>
			<li><a href="validation.html"> <i
					class="glyph-icon icon-share-alt"></i> <span>Relation 관리</span>
			</a></li>

			<!-- add new menu item called search history -->
			<li><a href="${pageContext.servletContext.contextPath}/rd/search_history">
				<i class="glyphicon-list-alt"></i> <span>RD Search History</span>
			</a></li>
			<!-- end search history menu item -->

			<li class="divider"></li>
			<li class="header"><span>RD 테스트
					<li><a href="#"> <i class="glyph-icon icon-calendar"></i>
							<span>테스트 계획</span>
					</a>
						<div class="sidebar-submenu" style="display: none">
							<ul>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/plan/customization"
									class="sfActive"><span>맞춤화</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/plan/range"><span>테스트
											범위</span></a></li>
								<li><a href="${pageContext.servletContext.contextPath}/"><span>테스트
											전략</span></a></li>
								<li><a href="${pageContext.servletContext.contextPath}/"><span>팀원
											및 일정</span></a></li>
							</ul>
						</div></li> <!-- RD Test -->
					<li><a href="#"> <i class="glyph-icon icon-bullseye"></i><span>문서
								산출물 테스트</span>
					</a>
						<div class="sidebar-submenu" style="display: none">
							<ul>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/rd/"><span>메인
											화면</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/rd/rlim/edit"><span>RLIM
											편집</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/rd/qa/range"><span>품질
											평가 범위 선택</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/rd/slts"><span>SLTS
											확인 및 테스트 수행</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/rd/result"><span>문서
											산출물 테스트 결과 확인</span></a></li>
							</ul>
						</div></li> <!-- SW Test -->
					<li><a href="#"> <i class="glyph-icon icon-bug"></i><span>SW
								테스트</span>
					</a>
						<div class="sidebar-submenu" style="display: none">
							<ul>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/sw/"><span>메인
											화면</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/sw/slts"><span>SLTS
											확인 및 테스트 수행</span></a></li>
								<li><a
									href="${pageContext.servletContext.contextPath}/testing/sw/result"><span>SW
											테스트 결과 확인</span></a></li>
							</ul>
						</div></li>
					<li><a href="#"> <i class="glyph-icon icon-bar-chart"></i> <span>테스트
								결과 확인</span>
					</a></li>
					<li class="divider"></li>
					<li class="header"><span>프로젝트 모니터링</span></li>
					<li><a href="project-settings.html"> <i
							class="glyph-icon icon-gears"></i> <span>프로젝트 기본정보</span>
					</a></li>
					<li><a href="#"> <i class="glyph-icon icon-dashboard"></i>
							<span>진척도 모니터링</span>
					</a></li>
					<li><a href="#"> <i class="glyph-icon icon-magic"></i> <span>주요산출물
								테스트 진행</span></a></li>
			<li class="divider"></li>
			<li class="header"><span>Statistics</span></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/rd/statistics/"> <i
					class="glyph-icon icon-bar-chart"></i> <span>Features/Patterns</span>
			</a></li>
		</ul>
	</div>
</div>
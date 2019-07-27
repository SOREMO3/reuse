<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<%@ include file="../commons/meta.jsp"%>

<!-- core javascript 정의 -->
<%@ include file="../commons/core-js.jsp"%>

<!-- user javascript 정의 -->
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/resources/js/event_rd_search.js"></script>

<!-- core css, resource 정의 -->
<%@ include file="../commons/core-css.jsp"%>

<!-- user css 정의 -->
<link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/resources/css/custom_dialog.css">


<style type="text/css">
#external-keyword-table td, #external-keyword-table th {
	text-align: center
} 
</style>

<!-- ---------------------------- -->
<!-- Coded by Yonsei - 2019-01-02 -->
<!-- ---------------------------- -->
<script>
$(function() {
	doCounting();
	
	harvested = 0;

	$("#y-btn-rd-harvest").click(function(e) {
		/*
		e.preventDefault();
		$('.y-modal-harvest-area').toggle();
		*/
		window.open("http://165.132.221.45:8080/Pattern/harvest.jsp", "", "width=900,height=850");
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
<!-- ------------------------------- -->
<!-- Coded by Yonsei - 2019-01-02 // -->
<!-- ------------------------------- -->


</head>

<body>
	<div id="sb-site">
		
		<div id="page-wrapper">
	
			<div id="page-content-wrapper">
				<div id="page-content">
					<div class="container pad10A">
						<div class="content-box">
							<!-- 
	                            TODO: Base Keyword 와 Extended Keyword 통해 호출할 API (action 속성 ) 
	                        -->
							<form:form id="searchForm"
								class="pad15L pad15R"
								modelAttribute="searchDataModel" method="post"
								action="${pageContext.servletContext.contextPath}/rd/search/result/header/1">
								
								<table class="" style="width:100%;">
									<tr>
										<td width=140>
											<label class="control-label y-font-lato y-fs14 mar0B">Base Keyword:</label>
										</td>
										<td>
											<form:input type="text" id="base-keyword" class="form-control" path="base_keyword" placeholder="Base Keyword"></form:input>
										</td>
										<td width=120>
											<button id="external-keyword-btn" class="btn mar10L" type="button">Relatum Search</button>
										</td>
									</tr>
									<tr>
										<td>
											<label class="control-label y-font-lato y-fs14 mar0B">Extended Keyword:</label>
										</td>
										<td colspan=2>
											<input name="ext_rd_relation_list" class="form-control" />
										</td>
									</tr>
								</table>
								<div class="text-center mar10B" id="accordion">
									<a id="searchExtended" data-toggle="collapse"
										class="y-font-lato y-fs14"
										data-target="#collapseMore" href="#" aria-expanded="true">
										More Options <i class="glyph-icon icon-sort-down"></i>
									</a>
								</div>
								
								
								<div id="collapseMore" class="panel-collapse collapse" aria-expanded="true">

	<!--  Advanced search options area -->
	<div class="">
		<table class="y-adv-table" valign=top>
			<tr>
				<td style="padding-left: 5px !important;" class="y-adv-header td_bottom_line1 y-font-lato" colspan=3>
					Advanced Search Options
				</td>
			</tr>
			<tr>
				<td style="padding: 10px !important;">

					<!-- RD search options -->
					<table class="y-adv-opt-table">
						<tr>
							<td class="y-adv-opt-table-header" colspan=5>
								RD search options
							</td>
						</tr>
						<tr>
							<td style="padding-left: 10px !important;" class="h40" colspan=5>
								<c:forEach items="${basicRDTypeList}" var="item">
									<label class="y-label-opt pad20R">
										<input type=checkbox checked>
										${item}
									</label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td class="y-adv-opt-table-header" colspan=5>
								검색일자
							</td>
						</tr>
						<tr>
							<td>

								<table class="y-adv-inner-table">
									<tr>
										<td class="date-type-title">
											시작일
										</td>
										<td style="padding: 7px 0px 7px 0px !important;" class="input-group">
											<span class="add-on input-group-addon"> <i
												class="glyph-icon icon-calendar"></i>
											</span>
											<input type="text" name="ext_rd_start_date"
												value="${searchDataModel.ext_rd_start_date}"
												class="bootstrap-datepicker form-control date-input"
												data-date-format="yyyy-mm-dd"  />
										</td>
										<td class="date-type-title">
											종료일
										</td>
										<td style="padding: 7px 5px 7px 5px !important;" class="input-group">
											<span class="add-on input-group-addon"> <i
												class="glyph-icon icon-calendar"></i>
											</span>
											<input type="text" name="ext_rd_end_date"
												value="${searchDataModel.ext_rd_end_date}"
												class="bootstrap-datepicker form-control date-input"
												data-date-format="yyyy-mm-dd" />
										</td>
										<td class="date-period-group">
											<span class="y-search-term" onclick="beforeTermRD('${before1Day}');">1일</span>
											<span class="y-search-term" onclick="beforeTermRD('${before1Week}');">1주일</span>
											<span class="y-search-term" onclick="beforeTermRD('${before1Month}');">1개월</span>
											<span class="y-search-term" onclick="beforeTermRD('${before6Month}');">6개월</span>
											<span class="y-search-term" onclick="beforeTermRD('${before1Year}');">1년</span>
										</td>
										<td class="date-type-radio-group">
											<label class="y-label-opt">
												<input type="checkbox" checked> 생성일
											</label>
											<label class="y-label-opt">
												<input type="checkbox" checked> 갱신일
											</label>
										</td>
									</tr>
								</table>
							
							</td>
						</tr>
					</table>
					<!-- RD search options // -->

				</td>
			</tr>
			
			<tr>
				<td style="padding: 10px !important;">

					<!-- SD search options -->
					<table class="y-adv-opt-table">
						<tr>
							<td class="y-adv-opt-table-header" colspan=5>
								SD search options
							</td>
						</tr>
						<tr>
							<td style="padding-left: 10px !important;" class="h40" colspan=5>
								<c:forEach items="${basicSDTypeList}" var="item">
									<label class="y-label-opt pad20R">
										<input type=checkbox checked>
										${item}
									</label>
								</c:forEach>
							</td>
						</tr>
						<tr>
							<td class="y-adv-opt-table-header" colspan=5>
								검색일자
							</td>
						</tr>
						<tr>
							<td>

								<table class="y-adv-inner-table">
									<tr>
										<td class="date-type-title">
											시작일
										</td>
										<td style="padding: 7px 0px 7px 0px !important;" class="input-group">
											<span class="add-on input-group-addon"> <i
												class="glyph-icon icon-calendar"></i>
											</span>
											<input type="text" name="ext_sd_start_date"
												value="${searchDataModel.ext_rd_start_date}"
												class="bootstrap-datepicker form-control date-input"
												data-date-format="yyyy-mm-dd"  />
										</td>
										<td class="date-type-title">
											종료일
										</td>
										<td style="padding: 7px 5px 7px 5px !important;" class="input-group">
											<span class="add-on input-group-addon"> <i
												class="glyph-icon icon-calendar"></i>
											</span>
											<input type="text" name="ext_sd_end_date"
												value="${searchDataModel.ext_rd_end_date}"
												class="bootstrap-datepicker form-control date-input"
												data-date-format="yyyy-mm-dd" />
										</td>
										<td class="date-period-group">
											<span class="y-search-term" onclick="beforeTermSD('${before1Day}');">1일</span>
											<span class="y-search-term" onclick="beforeTermSD('${before1Week}');">1주일</span>
											<span class="y-search-term" onclick="beforeTermSD('${before1Month}');">1개월</span>
											<span class="y-search-term" onclick="beforeTermSD('${before6Month}');">6개월</span>
											<span class="y-search-term" onclick="beforeTermSD('${before1Year}');">1년</span>
										</td>
										<script>
											function beforeTermRD(d) {
												$("input[name=ext_rd_start_date]").val(d);
											}

											function beforeTermSD(d) {
												$("input[name=ext_sd_start_date]").val(d);
											}
										</script>
										<td class="date-type-radio-group">
											<label class="y-label-opt">
												<input type="checkbox" checked> 생성일
											</label>
											<label class="y-label-opt">
												<input type="checkbox" checked> 갱신일
											</label>
										</td>
									</tr>
								</table>
							
							</td>
						</tr>
					</table>
					<!-- SD search options // -->

				</td>
			</tr>			
						
		</table>
	</div>
	<!--  Advanced search options area -->

								</div>
								
								<!-- ---------------------------- -->
								<!-- Coded by Yonsei - 2019-01-02 -->
								<!-- ---------------------------- -->
								
				<div class="pad20T">
					<table class="y-item-area y-item-area-border">
						<tr>
							<td style="width:100px;padding:2px!important;padding-left:6px!important;">
								<a href="${pageContext.servletContext.contextPath}/rd/statistics/">
									<span class="btn y-fs13">View Statistics</span>
								</a>
							</td>
							<td style="padding-right:8px!important">						
								<span class="y-item-title">On going projects:</span>
								<span class="y-item-count" id="y-item-1">1284</span>
								<span class="y-item-title">Total RD:</span>
								<span class="y-item-count" id="y-item-2">1553234</span>
								<span class="y-item-title">Last update:</span>
								<span class="y-item-date">
								<span class="y-item-count" id="y-item-3">2018</span>-<span class="y-item-count" id="y-item-4">12</span>-<span class="y-item-count" id="y-item-5">23</span> <span class="y-item-count" id="y-item-6">23</span>:<span class="y-item-count" id="y-item-7">30</span></span>
							</td>
						</tr>
					</table>
				</div>

								<!-- ------------------------------- -->
								<!-- Coded by Yonsei - 2019-01-02 // -->
								<!-- ------------------------------- -->

								<div class="button-pane mrg20T">
									<button id="search-button" class="btn btn-info w120 y-fs16 y-font-lato" type="submit">검색</button>
									<button class="btn btn-info w120 y-fs16 y-font-lato" type="button" id="y-btn-rd-harvest">Harvest</button>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="hide" id="extended-search-dialog" title="Relatum Select">
			<table class="" style="width:100%;">
				<tr>
					<td width=100>
						<label class="control-label mar10T pad5L">Base Keyword:</label>
					</td>
					<td>
						<input type="text" class="form-control" name="base_keyword_popup"
							placeholder="Base Keyword"/> 
					</td>
					<td width=80>
						<button id="external-keyword-pop-btn" class="btn btn-primary mar10L" style="font-size: 14px" type="button">Search</button>
					</td>
				</tr>
			</table>
		
			
			<div class="container search-dialog-container">
				<div class="form-group pad5T pad10B center">
					<label id="relatum-dialog-content-title" class="col-sm-9 control-label pad60L">Relatum List</label>
					<div class="col-sm-3 right">
						<span id="listBtn" class="glyphicon glyphicon-list pad10R changeBtn" onClick="changeBtnClick(this.id)"></span>
						<span id="cardBtn" class="glyphicon glyphicon-th-large pad10R changeBtn" onClick="changeBtnClick(this.id)"></span>
					</div>
				</div>
				<div class="divider"></div>
				<table id="extended-keyword-table-header" class="table table-hover mar0B">
					<thead>
						<tr>
							<th style="width: 10%;"></th>
							<th style="width: 68%;">Relatum</th>
							<th style="width: 19%;">Similarity</th>
							<th style="width: 3%;"></th>
						</tr>
					</thead>
				</table>
				<div id="extended-keyword-table-container" class="relatum-keyword-container">
					<table id="external-keyword-table" class="table table-hover">
						<tbody>
						</tbody>
					</table>
				</div>
				<div id="extended-keyword-card-container" class="relatum-card-container container" style="display:none;">
				</div>
				<div class="divider"></div>
				<div id="extended-keyword-result" style="height: 100px;overflow-y:auto;">
				</div>
				
			</div>
						
			<div class="button-panel mrg20T pad0B">
				<button class="btn btn-info" id="external-keyword-submit-btn" type="submit">확인</button>
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
		<script type="text/javascript">
			
			function goToStatistics() {
				$.ajax(
	                {
	                    type : "POST",
	                    url : '${pageContext.servletContext.contextPath}/rd/search/statistics',
	                    success : function(data) {
	                    },
	                    error : function(e) {
	                        console.log('ajax error');
	                    }
	                });
			}
		
			var base_keyword_input = $('input[name="base_keyword"]');
			var base_keyword_input_popup = $('[name="base_keyword_popup"]');

            // cbnu - 2017/12  --->
            // communication to the method to choose the RD type preference with mouse click
            $('#search-result-btn').on('click',
                function() {
            		console.log('${pageContext.servletContext.contextPath}/rd/search/visualization');
                    $.ajax(
                        {
                            type : "POST",
                            url : '${pageContext.servletContext.contextPath}/rd/search/visualization',
                            data : {
                                base_keyword : base_keyword_input.val()
                            },
                            success : function(data) {
                                redirectToResult(data, 1);
                                console.log("search success")
                            },
                            error : function(e) {
                                console.log('ajax error');
                            }
                        });
                });

            // communication to the method to choose the RD type preference with enter-key press
            $('#base-keyword').keypress(
                function(e){
                    if(e.which == 13 || e.KeyCode == 13){
                        $.ajax(
                            {
                                type : "POST",
                                url : '${pageContext.servletContext.contextPath}/rd/search/visualization',
                                data : {
                                    base_keyword : base_keyword_input.val()
                                },
                                success : function(data) {
                                    redirectToResult(data, 1);
                                },
                                error : function(e) {
                                    console.log('ajax error');
                                }
                            });
                    }
                });

            // move to search-result page
            function redirectToResult(base, page) {
                $('#searchForm').attr(
                    'action',
                    '${pageContext.servletContext.contextPath}/rd/search/result/'
                    + base + '/' + page).submit();
            }
            // <--- cbnu - 2017/12


			$('#external-keyword-btn').on(
					'click',
					function() {
						searchExtendedKeyword(base_keyword_input.val());
						$('[name="base_keyword_popup"]').val(
								$('[name="base_keyword"]').val());

						$(function() {
							$("#extended-search-dialog").dialog({
								modal : true,
								minWidth : 500,
								minHeight : 340,
								position: {
									my:"top",
									at:"center",
									of:'[name="ext_rd_relation_list"]'
								},
								dialogClass : "",
								show : "fadeIn"
							});

							$('.ui-dialog-content').removeClass('hide');
						});
					});

			/* Dialog의 search button */
			$('#external-keyword-pop-btn').on('click', function() {
				searchExtendedKeyword(base_keyword_input_popup.val());
			});

			/* Dialog의 search input */
			$('[name="base_keyword_popup"]').on('keydown', function(e) {
				if (e.keyCode == 13)
					searchExtendedKeyword(base_keyword_input_popup.val());
			});

			/* 체크된 Relatum submit */
			$('#external-keyword-submit-btn').on(
					'click',
					function() {
						var list = '';

						$('[name="extended-keyword-selected"]:checked').each(
								function(i) {
									list += $(this).val() + ",";
								});

						$('[name="ext_rd_relation_list"]').val(list);
						$('#extended-search-dialog').dialog('close');
					});

			$('[name="base_keyword_popup"]').change(function() {
				$('[name="base_keyword"]').val($(this).val());
			});

			/* extended keyword를 비동기 검색하여 table에 출력 */
			function searchExtendedKeyword(keyword) {
				var extended_keyword_table = $('#external-keyword-table');
				keywordMap.clear();
				$.ajax({
					type : "POST",
					url : '${pageContext.servletContext.contextPath}/rd/search/extended',
					data : {
						base_keyword : keyword
					},
					success : function(data) { /////////<-------------'list' variable: data//////////
						extended_keyword_table.find('tbody').empty();

						for (var i = 0; i < data.length; i++) {
							var tmpRelatum = data[i].word;
							var tmpSimilarity = data[i].similarity;
							if (parseInt(tmpSimilarity) == -200) {
								tmpSimilarity = "N/A";
							}
							extended_keyword_table
								.find('tbody')
								.append(
										'<tr>'
										+'<td style="width: 10%;">'
										+'<input type="checkbox" class="y-extended-checkbox" name="extended-keyword-selected" value="'+tmpRelatum+'" onclick="selectExtendedKeyword(\''+tmpRelatum+'\', '+i+')" />'
										+'</td>'
										+'<td style="width: 71%;">'
										+ data[i].word
										+ '</td>'
										+'<td style="width: 19%;">'
										+ tmpSimilarity
										+ '</td>'
										+'</tr>');
						}
					},
					error : function(e) {
						console.log('ajax error');
					}
				});
			}
			
			
			var keywordMap = new Map();
			
			function selectExtendedKeyword(keyword, index) {
				var extended_keyword_result = document.getElementById('extended-keyword-result');
				
				if(keywordMap.has(index)) {
					keywordMap.delete(index);
				} else {
					keywordMap.set(index,keyword);
				}
				
				extended_keyword_result.innerHTML = "";
				for(var value of keywordMap.values()) {
					extended_keyword_result.innerHTML += "<div class=\"selected-keyword\" val=\""+value+"\">"+value+"</div>"
				}
			}
			
		</script>
		
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/multi-select/multiselect.js"></script>
		<script type="text/javascript">
			$(function() {
				"use strict";
				$(".multi-select").multiSelect();
				$(".ms-container").append(
						'<i class="glyph-icon icon-exchange"></i>');
			});
			$(document).ready(function() {
				$(".multi-select").multiSelect('deselect_all');
			});
		</script>
		
		<script type="text/javascript"
			src="${pageContext.servletContext.contextPath}/resources/assets/widgets/datepicker/datepicker.js"></script>
		<script type="text/javascript">
			$(function() {
				"use strict";
				$('.bootstrap-datepicker').bsdatepicker({
					format : 'yyyy-mm-dd'
				});
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

		<script type="text/javascript">
			$('#collapseMore').on('hidden.bs.collapse', function() {
				$(this).find('select, input').attr('disabled', true);
				$(".multi-select").multiSelect('refresh');
			});

			$('#collapseMore').on('show.bs.collapse', function() {
				$(this).find('select, input').attr('disabled', false);
				$(".multi-select").multiSelect('refresh');
			});
		</script>
	</div>
</body>

</html>

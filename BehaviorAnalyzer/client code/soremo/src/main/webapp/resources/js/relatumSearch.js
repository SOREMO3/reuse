	$(window).load(function() {
		setTimeout(function() {
			$('#loading').fadeOut(400, "linear");
		}, 300);
	});
	
	var base_keyword_input = $('input[name="base_keyword"]');
	var base_keyword_input_popup = $('[name="base_keyword_popup"]');

	// cbnu - 2017/12  --->
	// communication to the method to choose the RD type preference with mouse click
	$('#search-result-btn').on('click',
		function() {
			$.ajax(
				{
					type : "POST",
					url : './rd/search/visualization',
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
		});

	// communication to the method to choose the RD type preference with enter-key press
	$('#base-keyword').keypress(
		function(e){
			if(e.which == 13 || e.KeyCode == 13){
				$.ajax(
					{
						type : "POST",
						url : './rd/search/visualization',
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
			'./rd/search/result/'
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

	$.ajax({
			type : "POST",
			url : './rd/search/extended',
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
									'<tr><td style="text-algin:center;"><input type="checkbox" name="extended-keyword-selected" class="checkbox" value="'+tmpRelatum+'" /></td><td style="text-algin:center;">'
											+ data[i].word
											+ '</td><td style="text-algin:center;">'
											+ tmpSimilarity
											+ '</td><td style="text-algin:center;">&nbsp;&nbsp;</td></tr>');
				}
			},
			error : function(e) {
				console.log('ajax error');
			}
		});
	}
	
	$(function() {
		"use strict";
		$(".multi-select").multiSelect();
		$(".ms-container").append(
				'<i class="glyph-icon icon-exchange"></i>');
	});
	$(document).ready(function() {
		$(".multi-select").multiSelect('deselect_all');
	});
	
	$(function() {
		"use strict";
		$('.bootstrap-datepicker').bsdatepicker({
			format : 'yyyy-mm-dd'
		});
	});
	
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
	
	$('#collapseMore').on('hidden.bs.collapse', function() {
		$(this).find('select, input').attr('disabled', true);
		$(".multi-select").multiSelect('refresh');
	});

	$('#collapseMore').on('show.bs.collapse', function() {
		$(this).find('select, input').attr('disabled', false);
		$(".multi-select").multiSelect('refresh');
	});
var keywordSelectedArray = [];

window.addEvent('domready', function() {
	var pageStartTime = "";
	
	$(function() {
		pageStartTime = new Date();
	});
	
	var isFirstKey = 0;
	var ctrlDown = false;
	var isEdit = true;
	
	// search-input -> base-keyword
	$('#base-keyword').keyup(function(e) {
		isEdit = true;
		if(e.key == "Control") {
			ctrlDown = false;
		}
		var dateNow = new Date();
		if(isFirstKey == 0) {
			pageStartTime = dateNow;
			isFirstKey = 1;
		}
		var timeText = getTimeText(dateNow);
		var spanText = "Key Value : " + e.key + " / Key Code : " + e.keyCode + " / time : " + timeText;
		writeEvent('searchInputKeyDown', 'selab', e.key + " / " + e.keyCode, 'search', dateNow);
		
		//addEventLogToSearchDIV(spanText);
	});
	
	$('#base-keyword').keydown(function(e) {
		isEdit = true;
		if(e.key == "Control") {
			ctrlDown = true;
		}
		var dateNow = new Date();
		var timeText = getTimeText(dateNow);
		var spanText = "";
		if(ctrlDown == true) {
			if(e.keyCode == 67) {
				spanText = "text copy occured / at search page /  time : " + timeText;
				writeEvent('searchInputCopy', 'selab', 'text copy', 'search', dateNow);
				//addEventLogToSearchDIV(spanText);
			} else if(e.keyCode == 86) {
				spanText = "text paste occured / at search page / time : " + timeText;
				writeEvent('searchInputPaste', 'selab', 'text paste', 'search', dateNow);
				//addEventLogToSearchDIV(spanText);
			}
		}
	});
	
	// rd-input-event-submit-button -> search-result-btn -> search-button
//	$('#rd-input-event-submit-button').click(function() {
	$('#search-button').click(function() {
		var dateNow = new Date();
		var diff = new Date(dateNow - pageStartTime);
		var timeText = getMilliTimeText(diff);
		var keyword = $('#base-keyword').val();
//		$('#base-keyword').val(""); -> make error at search result
//		console.log(keword + " submit")
		writeEvent('searchInputSubmit', 'selab', keyword, 'search', dateNow);
		addWeight(keyword, "submit");
		//addClickCount(keyword);
		//addEventLogToSearchDIV("Input keyword : " + keyword + " / Total elapsed time : " + timeText);
		isFirstKey = 0;
		$("#searchForm").submit();
	});
	
	$('#rd-input-event-clear-button').click(function() {
		var dateNow = new Date();
		$('#base-keyword').val("");
		var search_event_writer_div = document.getElementById('rd-input-event-writer');
		search_event_writer_div.innerHTML = "";
		isFirstKey = 0;
		writeEvent('searchInputClear', 'selab', 'clear', 'search', dateNow);
	});
	
	$('#rd-list-event-clear-button').click(function() {
		var dateNow = new Date();
		var rdList_event_writer_div = document.getElementById('rd-list-event-writer');
		rdList_event_writer_div.innerHTML = "";
		writeEvent('RDListClear', 'selab', 'clear', "RD list", dateNow);
	});
	
	$('#base-keyword').autocomplete({
		source: function(request, response) {
			$.ajax( {
				url: "http://165.132.221.45:8080/BehaviorAnalyzer/event/getKeyword.jsp",
				dataType: "jsonp",
				data: {
					term: request.term
				},
				success: function(data) {
					//console.log(data);
					var check = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
					var strLen = 0;
					var korStrLen = 0;
					var nonKorStrLen = 0;
					$.each(data, function(key , item) {
						if(item.value.length > strLen) {
							strLen = item.value.length;
							var nonKorStr = item.value.replace(check, '');
							nonKorStrLen = nonKorStr.length;
							korStrLen = strLen - nonKorStrLen;
//							console.log(item.value + ' / ' + strLen);
//							console.log(nonKorStr + ' / ' + nonKorStrLen);
						}
						
					});
					var inputWidth = $('#base-keyword').width() + 26;
					var autoWidth = nonKorStrLen*7 + korStrLen*14;
					var minWidth = 300;
					var paddingLeft = 10;
					var fontSize = 14;
					//console.log(inputWidth + ' / ' + autoWidth);
					autoWidth = (autoWidth > inputWidth ? inputWidth : autoWidth);
					$('.ui-autocomplete').css('max-width', autoWidth);
					$('.ui-autocomplete').css('width', autoWidth);
					$('.ui-autocomplete').css('min-width', minWidth);
					$('.ui-autocomplete').css('padding-left', paddingLeft);
					//console.log($('.ui-autocomplete').width());
					
					response(data.slice(0,50));
				}
			});
		},
		delay: 500,
		minLength: 1,
		select: function(event, ui) {
			isEdit = true;
			var keyword = $('#base-keyword').val();
			var dateNow = new Date();
			var timeText = getTimeText(dateNow);
			writeEvent('searchInputSelect', 'selab', keyword, 'search', dateNow);
			//addEventLogToSearchDIV("Input: " + keyword + " / Selected: " + ui.item.value + " / Select time : " + timeText + " / Index: " + ui.item.index);
			addClickCount(ui.item.value);
			addWeight(ui.item.value, "keyword click");
		},
		focus: function(event, ui) {
			var keyword = $('#base-keyword').val();
			var dateNow = new Date();
			var timeText =getTimeText(dateNow);
			if(isEdit == true) {
				writeEvent('searchInputEdit', 'selab', keyword, 'search', dateNow);
				//addEventLogToSearchDIV("Edit Keyword : " + keyword + " / at search page / time : " +  dateNow);
				isEdit = false;
			}
			//writeEvent('searchInputHover', 'selab', keyword, 'search', dateNow);
			//addEventLogToSearchDIV("Input: " + keyword + " / Mouse Hover: " + ui.item.value + " / Hover time : " + timeText + " / Index: " + ui.item.index);
			addWeight(ui.item.value, "keyword hover");
		}
	}).autocomplete("instance")._renderItem = function(ul, item) {
		return $("<table width='100%' style='border:none'>")
			.append("<tr style='border:none' width='100%'>")
			.append("<td style='border:none' width='90%'>" + item.label + "</td>")
			// click count
//			.append("<td style='border:none' width='10px' class='item-center center'>" + item.click + "</td>")
			.append("</tr>")
			.append("</table>")
			.appendTo(ul);
	};	
});


//write event to DB
function writeEvent(eventType, eventUser, eventVal, eventPage, eventDate) {
	var dateTime = eventDate.getFullYear() + "/" + (eventDate.getMonth()+1) + "/" + eventDate.getDate() 
					+ " " + eventDate.getHours() + ":" + eventDate.getMinutes() + ":" + eventDate.getSeconds() + "." + eventDate.getMilliseconds();
	$.ajax( {
		type: "post",
		url: "http://165.132.221.45:8080/BehaviorAnalyzer/event/writeEvent.jsp",
		data: {
			type: eventType,
			user: eventUser,
			value: eventVal,
			page: eventPage,
			date: dateTime
		},
		dataType: "jsonp",
		success: function(msg) {
			//console.log(msg);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function addWeight(keyword, type) {
	$.ajax( {
		type: "post",
		url: "http://165.132.221.45:8080/BehaviorAnalyzer/event/addWeight.jsp",
		data: {
			term: keyword,
			type: type
		},
		dataType: "jsonp",
		success: function(msg) {
			//console.log(msg);
			//console.log(type);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function addClickCount(keyword) {
	$.ajax( {
		type: "post",
		url: "http://165.132.221.45:8080/BehaviorAnalyzer/event/addClickCount.jsp",
		data: {
			term: keyword
		},
		dataType: "jsonp",
		success: function(msg) {
			//console.log(msg);
		},
		error:function(request,status,error){
			console.log("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
		}
	});
}

function addEventLogToSearchDIV(spanText) {
	var search_event_writer_div = document.getElementById('rd-input-event-writer');
	var span = document.createElement('span');
	var br = document.createElement('br');
	$(span).text(spanText);
	search_event_writer_div.appendChild(span);
	search_event_writer_div.appendChild(br);
}

function addEventLogToListDIV(spanText) {
	var rdList_event_writer_div = document.getElementById('rd-list-event-writer');
	var span = document.createElement('span');
	var br = document.createElement('br');
	$(span).text(spanText);
	rdList_event_writer_div.appendChild(span);
	rdList_event_writer_div.appendChild(br);
}

function getTimeText(date) {
	return date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + date.getMilliseconds();
}

function getMilliTimeText(date) {
	return date.getMinutes() + ":" + date.getSeconds() + "." + date.getMilliseconds();
}


window.addEvent('domready', function() {
	var hoverStartTime = "";
	var hoverEndTime = "";
	
	$('.rd-list-title').on('mouseenter', function() {
		hoverStartTime = new Date();
		var timeText = getTimeText(hoverStartTime);
		writeEvent('RDListTitleHoverStart', 'selab', $(this).text(), 'RD list', hoverStartTime);
		//addEventLogToListDIV($(this).text() + " hover start at " + timeText);
	});
	
	$('.rd-list-title').on('mouseleave', function() {
		hoverEndTime = new Date();
		var timeText = getTimeText(hoverEndTime);
		writeEvent('RDListTitleHoverEnd', 'selab', $(this).text(), 'RD list', hoverEndTime);
		//addEventLogToListDIV($(this).text() + " hover end at "+timeText);
		var diff = new Date(hoverEndTime - hoverStartTime);
		timeText = getMilliTimeText(diff);
		writeEvent('RDListTitleHoverTime', 'selab', timeText, 'RD list', hoverEndTime);
		//addEventLogToListDIV("hover time : "+timeText);
	});
	
	$('.rd-list-title').on('mousedown', function(e) {
		var dateNow = new Date();
		var timeText = getTimeText(dateNow);
		if(e.button == 2) {
			writeEvent('RDListTitleRightClick', 'selab', $(this).text(), 'RD list', dateNow);
			//addEventLogToListDIV($(this).text() +" / right click occurred at " + timeText);
		}
	});
	
	$('.rd-list-title').click(function(e) {
		var dateNow = new Date();
		var timeText = getTimeText(dateNow);
		writeEvent('RDListTitleLeftClick', 'selab', $(this).text(), 'RD list', dateNow);
		//addEventLogToListDIV($(this).text() + " is clicked at " + timeText);
	});
	
	var isFirstScroll = 0;
	var scrollStartTime = "";
	var scrollEndTime = "";
	
	$('.rd-list-container').on('scrollstart', function() {
		scrollStartTime = new Date();
		if(isFirstScroll == 1) {
			var diff = new Date(scrollStartTime - scrollEndTime);
			var timeText = getMilliTimeText(diff);
			writeEvent('RDListScrollTime', 'selab', timeText, 'RD list', scrollStartTime);
			//addEventLogToListDIV("scroll stop time : " + timeText);
		}
		var timeText = getTimeText(scrollStartTime);
		writeEvent('RDListScrollStart', 'selab', 'scroll start', 'RD list', scrollStartTime);
		//addEventLogToListDIV("scroll start at " + timeText);
	});
	
	$('.rd-list-container').on('scrollstop', function() {
		isFirstScroll = 1;
		scrollEndTime = new Date();
		var timeText = getTimeText(scrollEndTime);
		writeEvent('RDListScrollStop', 'selab', 'scroll stop at ' + $(this).scrollTop(), 'RD list', scrollEndTime);
		//addEventLogToListDIV("scroll stop at " + timeText + " / scroll position : " + $(this).scrollTop());
	});
	
	$('.rd-content').on('mousedown', function(e) {
		var dateNow = new Date();
		var timeText = getTimeText(dateNow);
		if(e.button == 0) {
			writeEvent('RDListContentLeftClick', 'selab', $(this).text(), 'RD list', dateNow);
			//addEventLogToListDIV($(this).text() +" is selected at " + timeText);
		} else if(e.button == 2) {
			writeEvent('RDListContentRightClick', 'selab', $(this).text(), 'RD list', dateNow);
			//addEventLogToListDIV($(this).text() +" / right click occurred at " + timeText);
		}
	});
	
	$('.rd-list-page-number').click(function() {
		var dateNow = new Date();
		var timeText = getTimeText(dateNow);
		var event_log = "";
		if($(this).text() == "<-")  {
			event_log = "previous page is clicked at " + timeText;
			writeEvent('RDListPrevPageClick', 'selab', 'previous page click', 'RD list', dateNow);
		} else if($(this).text() == "->") {
			event_log = "next page is clicked at " + timeText;
			writeEvent('RDListNextPageClick', 'selab', 'next page click', 'RD list', dateNow);
		} else {
			event_log = "page " + $(this).text() + " is clicked at " + timeText;
			writeEvent('RDListPageSelect', 'selab', 'page select', 'RD list', dateNow);
		}
		console.log(event_log);
		//addEventLogToListDIV(event_log);
	});	
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


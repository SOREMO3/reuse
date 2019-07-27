$(".black-dialog").click(function() {
	$("#basic-dialog").dialog({
		modal : !0,
		minWidth : 800,
		minHeight : 200,
		dialogClass : "",
		resizable:0,
		show : "fadeIn",
		buttons:{
			OK:function(){$(this).dialog("close")}
		}
	}), $(".ui-widget-overlay").addClass("bg-black opacity-60")
})
$(function() {
	$("#username").editable({
		type : "text",
		pk : 1,
		name : "username",
		title : "Enter username"
	}), $("#-rd").editable({
		validate : function(a) {
			return "" == $.trim(a) ? "This field is required" : void 0
		}
	}), $("#target-rd").editable(
			{
				prepend : "not selected",
				source : [ {
					value : 1,
					text : "1 type"
				}, {
					value : 2,
					text : "2 type"
				} ],
				display : function(a, b) {
					var c = {
						"" : "gray",
						1 : "green",
						2 : "blue"
					}, d = $.grep(b, function(b) {
						return b.value == a
					});
					d.length ? $(this).text(d[0].text).css("color", c[a]) : $(
							this).empty()
				}
			}), $("#status").editable(), $("#group").editable({
		showbuttons : !1
	}), $("#dob").editable(), $("#comments").editable({
		showbuttons : "bottom"
	}), $("#rd-type").editable(
			{
				prepend : "text",
				source : [ {
					value : 1,
					text : "text"
				}, {
					value : 2,
					text : "image"
				} ],
				display : function(a, b) {
					var c = {
						"" : "gray",
						1 : "green",
						2 : "blue"
					}, d = $.grep(b, function(b) {
						return b.value == a
					});
					d.length ? $(this).text(d[0].text).css("color", c[a]) : $(
							this).empty()
				}
			}), $("#status").editable(), $("#group").editable({
		showbuttons : !1
	}), $("#dob").editable(), $("#comments").editable({
		showbuttons : "bottom"
	}), $("#inline-username").editable({
		type : "text",
		pk : 1,
		name : "username",
		title : "Enter username",
		mode : "inline"
	}), $("#inline-firstname").editable({
		validate : function(a) {
			return "" == $.trim(a) ? "This field is required" : void 0
		},
		mode : "inline"
	}), $("#rd-process").editable({
        prepend : "not selected",
        source : [ {
            value : 1,
            text : "Imagination"
        }, {
            value : 2,
            text : "Conceptualization"
        } ],
        display : function(a, b) {
            var c = {
                "" : "gray",
                1 : "green",
                2 : "blue"
            }, d = $.grep(b, function(b) {
                return b.value == a
            });
            d.length ? $(this).text(d[0].text).css("color", c[a]) : $(
                    this).empty()
        }
    }), $("#rd-relation").editable({
        prepend : "not selected",
        source : [ {
            value : 1,
            text : "Evolve"
        }, {
            value : 2,
            text : "Paraphrasing"
        }, {
            value : 3,
            text : "Refer"
        }, {
            value : 3,
            text : "Equal"
        } ],
        display : function(a, b) {
            var c = {
                "" : "gray",
                1 : "green",
                2 : "blue"
            }, d = $.grep(b, function(b) {
                return b.value == a
            });
            d.length ? $(this).text(d[0].text).css("color", c[a]) : $(
                    this).empty()
        }
    }), $("#inline-status").editable({
		mode : "inline"
	}), $("#inline-group").editable({
		showbuttons : !1,
		mode : "inline"
	}), $("#inline-dob").editable({
		mode : "inline"
	}), $("#inline-comments").editable({
		showbuttons : "bottom",
		mode : "inline"
	})
});
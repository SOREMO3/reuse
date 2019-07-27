$(document).ready(
		function() {
			var a = function(a) {
				var b = a.length ? a : $(a.target), c = b.data("output");
				window.JSON ? c.val(window.JSON.stringify(b
						.nestable("serialize"))) : c
						.val("JSON browser support required for this demo.")
			};
			a($("#nestable3").data("output", $("#nestable2-output")))
		});
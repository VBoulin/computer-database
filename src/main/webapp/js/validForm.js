$(document).ready(function() {
		$("#form").validate({
			success: function(label) {
			    label.addClass("valid").text("Ok!")
			},
			messages: {
                name: "Please specify a computer name",
                introduced: "Valid format is yyyy-MM-dd or leave it field emtpy",
                discontinued: "Valid format is yyyy-MM-dd or leave it field emtpy"
            }
		});
});
function checkName() {
	if ($("#computerName").val().length > 0 && $("#computerName").val().length < 256) {
		$("#computerName").parent().removeClass("has-error ");
		$("#computerName").parent().addClass("has-success");
		$('#submit').prop('disabled', false);
	} else {
		$("#computerName").parent().removeClass("has-success");
		$("#computerName").parent().addClass("has-error");
		$('#submit').prop('disabled', true);
	}
}

function checkDate(date, regex) {
	var reg = new RegExp(regex);
	console.log($("#" + date).val().length);
	if ($("#" + date).val().length == 0)
	{
		$("#" + date).parent().removeClass("has-error");
		$("#" + date).parent().removeClass("has-success");
		$('#submit').prop('disabled', true);
	} else {
		if (reg.test($("#" + date).val())) {
			$("#" + date).parent().removeClass("has-error");
			$("#" + date).parent().addClass("has-success");
			$('#submit').prop('disabled', false);
		} else {
			$("#" + date).parent().removeClass("has-success");
			$("#" + date).parent().addClass("has-error");
			$('#submit').prop('disabled', true);
		}
	}
}
$(document).ready(function() {
	function  getCookie(name){
	     if(document.cookie.length == 0)
	       return null;

	     var regSepCookie = new RegExp('(; )', 'g');
	     var cookies = document.cookie.split(regSepCookie);

	     for(var i = 0; i < cookies.length; i++){
	       var regInfo = new RegExp('=', 'g');
	       var infos = cookies[i].split(regInfo);
	       if(infos[0] == name){
	         return unescape(infos[1]);
	       }
	     }
	     return null;
	}

	var cookie = getCookie("org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE");
	
	if(cookie==("fr")){
		$("#form").validate({
			success: function(label) {
			    label.addClass("valid").text("C'est bon!")
			},
			messages: {
	            name: "Veuillez entrer un nom.",
	            introduced: "Format de date invalide",
	            discontinued: "Format de date invalide"
	        }
		});
	}else{
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
	}
});
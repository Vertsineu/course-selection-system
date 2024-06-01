function gotoStudentLogin(){
	if(localStorage.getItem("key")==null)
		window.location.href="studentLogin.html";
	else
		window.location.href="studentMainpage.html";
}
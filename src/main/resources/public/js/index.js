function gotoStudentLogin(){
	if(localStorage.getItem("key")==null)
		window.location.href="login/studentLogin.html";
	else
		window.location.href="studentMainpage.html?token="+localStorage.getItem("key");
}

function gotoStudentLogin(){
	if(document.cookie.trim()==='')
		window.location.href="login/studentLogin.html";
	else
		window.location.href="studentMainpage.html";
}
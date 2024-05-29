function login(){
	var id=document.getElementById("studentID").value;
	var pw=document.getElementById("studentPassword").value;
	var studentinfo={
		studentID:id,
		studentPassword:pw
	}
	var url="/api/account/login";
	fetch(url,{
		method:"POST",
		headers:{
			"Content-Type":"application/json",
		},
		mode:"cors",
		body:JSON.stringify(studentinfo)
	}).then(function(response){
		return response.json()
	}).then(function(data){
		if(data.result==0){
			localStorage.setItem("key",data.key);
			window.location.href="studentMainpage.html";
		}
		else if(data.result==1){
			alert("用户不存在！");
		}
		else if(data.result==2){
			aleat("密码错误！");
		}
	}).catch(function(error){
		console.log(error);
	});
}
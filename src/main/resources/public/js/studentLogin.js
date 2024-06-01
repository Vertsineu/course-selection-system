function login(){
	var id=document.getElementById("studentID").value;
	var pw=document.getElementById("studentPassword").value;
	var studentinfo={
		username:id,
		password:pw
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
		return response.json();
	}).then(function(result){
		if(result.code==200){
			localStorage.setItem("key",result.data.token);
			window.location.href="../studentMainpage.html?token="+localStorage.getItem("key");
		}
		else{
			alert(result.msg);
		}
	}).catch(function(error){
		console.log(error);
	});
}
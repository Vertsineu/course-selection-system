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
			var date=new Date();
			date.setTime(date.getTime()+7*24*60*60*1000);
			var expires="expires="+date.toUTCString;
			document.cookie="token"+"="+result.data.token+";"+expires+";path=/";
			window.location.href="../studentMainpage.html";
		}
		else{
			alert(result.msg);
		}
	}).catch(function(error){
		console.log(error);
	});
}

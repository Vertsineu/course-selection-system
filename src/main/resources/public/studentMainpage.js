function show(contentId){
    var contents=document.querySelectorAll('.content');
    contents.forEach(function(content){
      content.classList.remove('active');
    });
    document.getElementById(contentId).classList.add('active');
}
function quit(){
	localStorage.clear();
	window.location.href="welcome.html";
}
function query(){
	var semester=document.getElementById("semester").value;
	var college=document.getElementById("college").value;
	var courseID=document.getElementById("courseID").value;
	var teacher=document.getElementById("teacher").value;
	if(courseID=="")
			courseID=null;
	if(teacher=="")
			teacher=null;
	var courseinfo={
		semester:semester,
		college:college,
		courseID:courseID,
		teacher:teacher
	}
	var url="/api/course/query";
	fetch(url,{
		method:"POST",
		headers:{
			"Content-Type":"application/json",
		},
		mode:"cors",
		body:JSON.stringify(courseinfo)
	}).then(function(response){
		return response.json()
	}).then(function(data){
		
	}).catch(function(error){
		console.log(error);
	});
}
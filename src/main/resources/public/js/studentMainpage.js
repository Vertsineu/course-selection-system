function show(contentId){
    var contents=document.querySelectorAll('.content');
    contents.forEach(function(content){
      content.classList.remove('active');
    });
    document.getElementById(contentId).classList.add('active');
}
function quit(){
	var date=new Date();
    date.setTime(date.getTime()-1);
    var expires="expires="+date.toUTCString();
	document.cookie="token"+"=;"+expires;
	window.location.href="index.html";
}
function query(){
	var oldTable=document.getElementById("showCourse");
	while(oldTable.hasChildNodes()>0){
		oldTable.deleteRow(0);
	}
	var classList=new Array(10005);
	var classNum=0;
	var semester=document.getElementById("semester").value;
	var college=document.getElementById("college").value;
	var courseName=document.getElementById("courseName").value;
	var courseID=document.getElementById("courseID").value;
	var teacher=document.getElementById("teacher").value;
	if(courseID=="")
		courseID=null;
	if(teacher=="")
		teacher=null;
	var courseinfo={
		semester:semester,
		departmentCode:college,
		courseName:courseName,
		courseCode:courseID,
		teacherName:teacher
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
	}).then(function(result){
		for(let course in result.data){
			for(let classes in course.classes){
				var thisClass={
					name:course.name,
					gradation:course.gradation,
					courseType:course.type,
					credits:course.credits,
					education:course.education,
					period:course.periodTotal,
					code:classes.code,
					classType:classes.type,
					examMode:classes.examMode,
					limitCount:classes.limitCount,
					teachLang:classes.teachLang,
					departmentCode:classes.department.code,
					departmentName:classes.department.name,
					teacher:classes.teacher,
					tpc:classes.tpc
				}
				classList[classNum]=thisClass;
				classNum++;
			}
		}
	}).catch(function(error){
		console.log(error);
	});
	var table=document.createElement("table");
	var thead=document.createElement("thead");
	var headerRow=document.createElement("tr");
	var th1=document.createElement("th");
	var th2=document.createElement("th");
	var th3=document.createElement("th");
	var th4=document.createElement("th");
	var th5=document.createElement("th");
	th1.textContent="课程";
	headerRow.appendChild(th1);
	th2.textContent="开课单位";
	headerRow.appendChild(th2);
	th3.textContent="授课教师";
	headerRow.appendChild(th3);
	th4.textContent="时间地点";
	headerRow.appendChild(th4);
	th5.textContent="课堂容量";
	headerRow.appendChild(th5);
	thead.appendChild(headerRow);
	table.appendChild(thead);
	var tbody=document.createElement("tbody");
	for(var i=0;i<classNum;i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		cell1.textContent=classList[i].code+"\n"+classList[i].name+"\n"+classList[i].credits+"学分 "+classList[i].period+"学时 "+classList[i].education+" "+classList[i].courseType+" "+classList[i].classType+" "+classList[i].teachLang+" "+classList[i].examMode+" "+classList[i].gradation;
		row.appentChild(cell1);
		cell2.textContent=classList[i].departmentCode+"\n"+classList[i].departmentName;
		row.appentChild(cell2);
		cell3.textContent=classList[i].teacher;
		row.appentChild(cell3);
		cell4.textContent=classList[i].tpc;
		row.appentChild(cell4);
		cell5.textContent=classList[i].limitCount;
		row.appentChild(cell5);
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
}
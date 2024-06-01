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
	oldTable.innerHTML="";
	var table=document.createElement("table");
	var thead=document.createElement("thead");
	var headerRow=document.createElement("tr");
	var th1=document.createElement("th");
	var th2=document.createElement("th");
	var th3=document.createElement("th");
	var th4=document.createElement("th");
	var th5=document.createElement("th");
	var courseNum=0;
	th1.textContent="课程";
	headerRow.appendChild(th1);
	th1.style.width="30%";
	th2.style.width="10%";
	th3.style.width="10%";
	th4.style.width="40%";
	th5.style.width="10%";
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
	var semester=document.getElementById("semester").value;
	var college=document.getElementById("college").value;
	var courseName=document.getElementById("courseName").value;
	var courseID=document.getElementById("courseID").value;
	var teacher=document.getElementById("teacher").value;
	if(semester=="null")
		semester=null;
	if(college=="null")
		college=null;
	if(courseName=="")
		courseName=null;
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
	var url="/api/query/forClass";
	fetch(url,{
		method:"POST",
		headers:{
			"Content-Type":"application/json",
		},
		mode:"cors",
		body:JSON.stringify(courseinfo)
	}).then(function(response){
		return response.json();
	}).then(function(result){
		for(let course of result.data.courseList){
			for(let classes of course.classes){
				courseNum++;
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
					teacher:classes.teachers,
					tpc:classes.tpc
				}
				var row=document.createElement("tr");
				var cell1=document.createElement("td");
				var cell2=document.createElement("td");
				var cell3=document.createElement("td");
				var cell4=document.createElement("td");
				var cell5=document.createElement("td");
				cell1.textContent=thisClass.code+"\n"+thisClass.name+"\n"+thisClass.credits+"学分 "+thisClass.period+"学时 "+thisClass.education+" "+thisClass.courseType+" "+thisClass.classType+" "+thisClass.teachLang+" "+thisClass.examMode+" "+thisClass.gradation;
				row.appendChild(cell1);
				cell2.textContent=thisClass.departmentCode+"\n"+thisClass.departmentName;
				row.appendChild(cell2);
				cell3.textContent=thisClass.teacher;
				row.appendChild(cell3);
				cell4.textContent=thisClass.tpc;
				row.appendChild(cell4);
				cell5.textContent=thisClass.limitCount;
				row.appendChild(cell5);
				tbody.appendChild(row);
			}
		}
	}).catch(function(error){
		console.log(error);
	});
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
}
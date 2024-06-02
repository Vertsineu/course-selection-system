var courseArray
var classArray=[];
var pageTotal,pageNow;
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
function selectQuery(){
	classArray=[];
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
	var courseInfo={
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
		body:JSON.stringify(courseInfo)
	}).then(function(response){
		return response.json();
	}).then(function(result){
		for(let course of result.data.courseList){
			for(let classes of course.classes){
				var thisClass={
					name:course.name,
					gradation:course.gradation,
					courseType:course.type,
					credits:course.credits,
					education:course.education,
					period:course.periodTotal,
					id:classes.id,
					code:classes.code,
					classType:classes.type,
					examMode:classes.examMode,
					limitCount:classes.limitCount,
					selectedCount:classes.selectedCount,
					teachLang:classes.teachLang,
					departmentCode:classes.department.code,
					departmentName:classes.department.name,
					teacher:classes.teachers,
					tpc:classes.tpc,
					selected:classes.selected
				}
				classArray.push(thisClass);
			}
		}
	}).then(function(){
		pageTotal=Math.ceil(classArray.length/25);
		pageNow=1;
		//显示课程列表
		var oldTable=document.getElementById("showSelect");
		oldTable.innerHTML="";
		var table=document.createElement("table");
		var thead=document.createElement("thead");
		var headerRow=document.createElement("tr");
		var th1=document.createElement("th");
		var th2=document.createElement("th");
		var th3=document.createElement("th");
		var th4=document.createElement("th");
		var th5=document.createElement("th");
		var th6=document.createElement("th");
		th1.style.width="30%";
		th2.style.width="10%";
		th3.style.width="10%";
		th4.style.width="30%";
		th5.style.width="10%";
		th6.style.width="10%";
		th1.textContent="课程";
		th2.textContent="开课单位";
		th3.textContent="授课教师";
		th4.textContent="时间地点";
		th5.textContent="已选人数";
		th6.textContent="";
		headerRow.appendChild(th1);
		headerRow.appendChild(th2);
		headerRow.appendChild(th3);
		headerRow.appendChild(th4);
		headerRow.appendChild(th5);
		headerRow.appendChild(th6);
		thead.appendChild(headerRow);
		table.appendChild(thead);
		var tbody=document.createElement("tbody");
		for(let i=0;i<Math.min(classArray.length,25);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			var cell6=document.createElement("td");
			cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
			cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
			cell3.textContent=classArray[i].teacher;
			cell4.textContent=classArray[i].tpc;
			cell5.textContent=classArray[i].selectedCount+"/"+classArray[i].limitCount;
			if(classArray[i].selected==false){
				var selectButton=document.createElement("button");
				selectButton.innerHTML="选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/select";
					fetch(url,{
						method:"POST",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("选课成功！");
							classArray[i].selected=true;
							classArray[i].selectedCount++;
							selectButton.innerHTML="取消选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			else{
				var selectButton=document.createElement("button");
				selectButton.innerHTML="取消选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/delete";
					fetch(url,{
						method:"DELETE",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("取消成功！");
							classArray[i].selected=false;
							classArray[i].selectedCount--;
							selectButton.innerHTML="选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			row.appendChild(cell4);
			row.appendChild(cell5);
			row.appendChild(cell6);
			tbody.appendChild(row);
		}
		table.appendChild(tbody);
		document.getElementById("showSelect").appendChild(table);
		var pageShow=document.getElementById("pageNumForSelect");
		pageShow.textContent=pageNow+'/'+pageTotal;
	}).catch(function(error){
		console.log(error);
	});
}
function selectLastPage(){
	if(pageNow===1){
		alert("已经是第一页！");
		return;
	}
	pageNow--;
	var oldTable=document.getElementById("showSelect");
		oldTable.innerHTML="";
		var table=document.createElement("table");
		var thead=document.createElement("thead");
		var headerRow=document.createElement("tr");
		var th1=document.createElement("th");
		var th2=document.createElement("th");
		var th3=document.createElement("th");
		var th4=document.createElement("th");
		var th5=document.createElement("th");
		var th6=document.createElement("th");
		th1.style.width="30%";
		th2.style.width="10%";
		th3.style.width="10%";
		th4.style.width="30%";
		th5.style.width="10%";
		th6.style.width="10%";
		th1.textContent="课程";
		th2.textContent="开课单位";
		th3.textContent="授课教师";
		th4.textContent="时间地点";
		th5.textContent="已选人数";
		th6.textContent="";
		headerRow.appendChild(th1);
		headerRow.appendChild(th2);
		headerRow.appendChild(th3);
		headerRow.appendChild(th4);
		headerRow.appendChild(th5);
		headerRow.appendChild(th6);
		thead.appendChild(headerRow);
		table.appendChild(thead);
		var tbody=document.createElement("tbody");
		for(let i=25*(pageNow-1);i<Math.min(classArray.length,25*pageNow);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			var cell6=document.createElement("td");
			cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
			cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
			cell3.textContent=classArray[i].teacher;
			cell4.textContent=classArray[i].tpc;
			cell5.textContent=classArray[i].selectedCount+"/"+classArray[i].limitCount;
			if(classArray[i].selected==false){
				var selectButton=document.createElement("button");
				selectButton.innerHTML="选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/select";
					fetch(url,{
						method:"POST",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("选课成功！");
							classArray[i].selected=true;
							classArray[i].selectedCount++;
							selectButton.innerHTML="取消选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			else{
				var selectButton=document.createElement("button");
				selectButton.innerHTML="取消选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/delete";
					fetch(url,{
						method:"DELETE",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("取消成功！");
							classArray[i].selected=false;
							classArray[i].selectedCount--;
							selectButton.innerHTML="选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			row.appendChild(cell4);
			row.appendChild(cell5);
			row.appendChild(cell6);
			tbody.appendChild(row);
		}
		table.appendChild(tbody);
		document.getElementById("showSelect").appendChild(table);
		var pageShow=document.getElementById("pageNumForSelect");
		pageShow.textContent=pageNow+'/'+pageTotal;
}
function selectNextPage(){
	if(pageNow>=pageTotal){
		alert("已经是最后一页！");
		return;
	}
	pageNow++;
	var oldTable=document.getElementById("showSelect");
		oldTable.innerHTML="";
		var table=document.createElement("table");
		var thead=document.createElement("thead");
		var headerRow=document.createElement("tr");
		var th1=document.createElement("th");
		var th2=document.createElement("th");
		var th3=document.createElement("th");
		var th4=document.createElement("th");
		var th5=document.createElement("th");
		var th6=document.createElement("th");
		th1.style.width="30%";
		th2.style.width="10%";
		th3.style.width="10%";
		th4.style.width="30%";
		th5.style.width="10%";
		th6.style.width="10%";
		th1.textContent="课程";
		th2.textContent="开课单位";
		th3.textContent="授课教师";
		th4.textContent="时间地点";
		th5.textContent="已选人数";
		th6.textContent="";
		headerRow.appendChild(th1);
		headerRow.appendChild(th2);
		headerRow.appendChild(th3);
		headerRow.appendChild(th4);
		headerRow.appendChild(th5);
		headerRow.appendChild(th6);
		thead.appendChild(headerRow);
		table.appendChild(thead);
		var tbody=document.createElement("tbody");
		for(let i=25*(pageNow-1);i<Math.min(classArray.length,25*pageNow);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			var cell6=document.createElement("td");
			cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
			cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
			cell3.textContent=classArray[i].teacher;
			cell4.textContent=classArray[i].tpc;
			cell5.textContent=classArray[i].selectedCount+"/"+classArray[i].limitCount;
			if(classArray[i].selected==false){
				var selectButton=document.createElement("button");
				selectButton.innerHTML="选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/select";
					fetch(url,{
						method:"POST",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("选课成功！");
							classArray[i].selected=true;
							classArray[i].selectedCount++;
							selectButton.innerHTML="取消选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			else{
				var selectButton=document.createElement("button");
				selectButton.innerHTML="取消选课";
				selectButton.onclick=function(){
					var selectInfo={
						id:classArray[i].id
					}
					var url="/api/course/delete";
					fetch(url,{
						method:"DELETE",
						headers:{
							"Content-Type":"application/json",
						},
						mode:"cors",
						body:JSON.stringify(selectInfo)
					}).then(function(response){
						return response.json();
					}).then(function(result){
						if(result.code==200){
							alert("取消成功！");
							classArray[i].selected=false;
							classArray[i].selectedCount--;
							selectButton.innerHTML="选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				};
				cell6.appendChild(selectButton);
			}
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			row.appendChild(cell4);
			row.appendChild(cell5);
			row.appendChild(cell6);
			tbody.appendChild(row);
		}
		table.appendChild(tbody);
		document.getElementById("showSelect").appendChild(table);
		var pageShow=document.getElementById("pageNumForSelect");
		pageShow.textContent=pageNow+'/'+pageTotal;
}
function query(){
	classArray=[];
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
	var courseInfo={
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
		body:JSON.stringify(courseInfo)
	}).then(function(response){
		return response.json();
	}).then(function(result){
		for(let course of result.data.courseList){
			for(let classes of course.classes){
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
				classArray.push(thisClass);
			}
		}
	}).then(function(){
		pageTotal=Math.ceil(classArray.length/25);
		pageNow=1;
		//显示课程列表
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
		th1.style.width="30%";
		th2.style.width="10%";
		th3.style.width="10%";
		th4.style.width="40%";
		th5.style.width="10%";
		th1.textContent="课程";
		th2.textContent="开课单位";
		th3.textContent="授课教师";
		th4.textContent="时间地点";
		th5.textContent="课堂容量";
		headerRow.appendChild(th1);
		headerRow.appendChild(th2);
		headerRow.appendChild(th3);
		headerRow.appendChild(th4);
		headerRow.appendChild(th5);
		thead.appendChild(headerRow);
		table.appendChild(thead);
		var tbody=document.createElement("tbody");
		for(let i=0;i<Math.min(classArray.length,25);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
			cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
			cell3.textContent=classArray[i].teacher;
			cell4.textContent=classArray[i].tpc;
			cell5.textContent=classArray[i].limitCount;
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			row.appendChild(cell4);
			row.appendChild(cell5);
			tbody.appendChild(row);
		}
		table.appendChild(tbody);
		document.getElementById("showCourse").appendChild(table);
		var pageShow=document.getElementById("pageNumForQuery");
		pageShow.textContent=pageNow+'/'+pageTotal;
	}).catch(function(error){
		console.log(error);
	});
}
function queryLastPage(){
	if(pageNow===1){
		alert("已经是第一页！");
		return;
	}
	pageNow--;
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
	th1.style.width="30%";
	th2.style.width="10%";
	th3.style.width="10%";
	th4.style.width="40%";
	th5.style.width="10%";
	th1.textContent="课程";
	th2.textContent="开课单位";
	th3.textContent="授课教师";
	th4.textContent="时间地点";
	th5.textContent="课堂容量";
	headerRow.appendChild(th1);
	headerRow.appendChild(th2);
	headerRow.appendChild(th3);
	headerRow.appendChild(th4);
	headerRow.appendChild(th5);
	thead.appendChild(headerRow);
	table.appendChild(thead);
	var tbody=document.createElement("tbody");
	for(let i=25*(pageNow-1);i<Math.min(classArray.length,25*pageNow);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
		cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
		cell3.textContent=classArray[i].teacher;
		cell4.textContent=classArray[i].tpc;
		cell5.textContent=classArray[i].limitCount;
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell4);
		row.appendChild(cell5);
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
	var pageShow=document.getElementById("pageNumForQuery");
	pageShow.textContent=pageNow+'/'+pageTotal;
}
function queryNextPage(){
	if(pageNow>=pageTotal){
		alert("已经是最后一页！");
		return;
	}
	pageNow++;
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
	th1.style.width="30%";
	th2.style.width="10%";
	th3.style.width="10%";
	th4.style.width="40%";
	th5.style.width="10%";
	th1.textContent="课程";
	th2.textContent="开课单位";
	th3.textContent="授课教师";
	th4.textContent="时间地点";
	th5.textContent="课堂容量";
	headerRow.appendChild(th1);
	headerRow.appendChild(th2);
	headerRow.appendChild(th3);
	headerRow.appendChild(th4);
	headerRow.appendChild(th5);
	thead.appendChild(headerRow);
	table.appendChild(thead);
	var tbody=document.createElement("tbody");
	for(let i=25*(pageNow-1);i<Math.min(classArray.length,25*pageNow);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		cell1.textContent=classArray[i].code+"\n"+classArray[i].name+"\n"+classArray[i].credits+"学分 "+classArray[i].period+"学时 "+classArray[i].education+" "+classArray[i].courseType+" "+classArray[i].classType+" "+classArray[i].teachLang+" "+classArray[i].examMode+" "+classArray[i].gradation;
		cell2.textContent=classArray[i].departmentCode+"\n"+classArray[i].departmentName;
		cell3.textContent=classArray[i].teacher;
		cell4.textContent=classArray[i].tpc;
		cell5.textContent=classArray[i].limitCount;
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell4);
		row.appendChild(cell5);
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
	var pageShow=document.getElementById("pageNumForQuery");
	pageShow.textContent=pageNow+'/'+pageTotal;
}

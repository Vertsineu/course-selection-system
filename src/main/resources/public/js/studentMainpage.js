var classArrayForSelect=[],classArrayForQuery=[],classTable=[];
var pageTotalForSelect,pageNowForSelect,pageTotalForQuery,pageNowForQuery,weekNow;
window.onload=function(){
	weekNow=1;
	fetch("/api/query/forStudentInfo").then(function(response){
		return response.json();
	}).then(function(result){
		document.getElementById("studentName").innerHTML=result.data.name;
		document.getElementById("studentID").innerHTML=result.data.number;
		document.getElementById("studentDepartment").innerHTML=result.data.department.name;
	}).catch(function(error){
		console.log(error);
	});
	fetch("/api/course/timeSet").then(function(response){
		return response.json();
	}).then(function(result){
		for(let course of result.data.timeCourseList){
			var thisClass={
				name:course.name,
				id:course.classes[0].id,
				tpc:course.classes[0].tpc
			}
			classTable[i].push(thisClass);
		}
	}).then(function(){
		weekNow=1;
		for(let day=1;day<=7;day++){
			for(let period=1;period<=13;period++){
				var thisClass=classTable[(weekNow-1)*7*13+(day-1)*13+(period-1)];
				document.getElementById("showClassTable").querySelector("table").rows[period].cells[day].innerHTML=thisClass.id+"\n"+thisClass.name+"\n"+thisClass.tpc;
			}
		}
		document.getElementById("weekNum").textContent="第"+weekNow+"周";
	}).catch(function(error){
		console.log(error);
	});
}
function lastWeek(){
	if(weekNow===1){
		alert("已经是第一周！");
		return;
	}
	weekNow--;
	for(let day=1;day<=7;day++){
		for(let period=1;period<=13;period++){
			var thisClass=classTable[(weekNow-1)*7*13+(day-1)*13+(period-1)];
			document.getElementById("showClassTable").querySelector("table").rows[period].cells[day].innerHTML=thisClass.id+"\n"+thisClass.name+"\n"+thisClass.tpc;
		}
	}
	document.getElementById("weekNum").textContent="第"+weekNow+"周";
}
function nextWeek(){
	if(weekNow===20){
		alert("已经是最后一周！");
		return;
	}
	weekNow++;
	for(let day=1;day<=7;day++){
		for(let period=1;period<=13;period++){
			var thisClass=classTable[(weekNow-1)*7*13+(day-1)*13+(period-1)];
			document.getElementById("showClassTable").querySelector("table").rows[period].cells[day].innerHTML=thisClass.id+"\n"+thisClass.name+"\n"+thisClass.tpc;
		}
	}
	document.getElementById("weekNum").textContent="第"+weekNow+"周";
}
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
	classArrayForSelect=[];
	var semester=document.getElementById("semesterForSelect").value;
	var college=document.getElementById("collegeForSelect").value;
	var courseName=document.getElementById("courseNameForSelect").value;
	var courseID=document.getElementById("courseIDForSelect").value;
	var teacher=document.getElementById("teacherForSelect").value;
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
				classArrayForSelect.push(thisClass);
			}
		}
	}).then(function(){
		pageTotalForSelect=Math.ceil(classArrayForSelect.length/25);
		pageNowForSelect=1;
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
		for(let i=0;i<Math.min(classArrayForSelect.length,25);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			var cell6=document.createElement("td");
			cell1.textContent=classArrayForSelect[i].code+"\n"+classArrayForSelect[i].name+"\n"+classArrayForSelect[i].credits+"学分 "+classArrayForSelect[i].period+"学时 "+classArrayForSelect[i].education+" "+classArrayForSelect[i].courseType+" "+classArrayForSelect[i].classType+" "+classArrayForSelect[i].teachLang+" "+classArrayForSelect[i].examMode+" "+classArrayForSelect[i].gradation;
			cell2.textContent=classArrayForSelect[i].departmentCode+"\n"+classArrayForSelect[i].departmentName;
			cell3.textContent=classArrayForSelect[i].teacher;
			cell4.textContent=classArrayForSelect[i].tpc;
			cell5.textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
			var selectButton=document.createElement("button");
			if(classArrayForSelect[i].selected==false)
				selectButton.innerHTML="选课";
			else
				selectButton.innerHTML="取消选课";
			selectButton.addEventListener('click',function(){
				var selectInfo={
					id:classArrayForSelect[i].id
				}
				if(classArrayForSelect[i].selected==false){
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
							classArrayForSelect[i].selected=true;
							classArrayForSelect[i].selectedCount++;
							document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
							document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="取消选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				}
				else{
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
							classArrayForSelect[i].selected=false;
							classArrayForSelect[i].selectedCount--;
							document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
							document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="选课";
						}
						else
							alert(result.msg);
					}).catch(function(error){
						console.log(error);
					});
				}
			});
			cell6.appendChild(selectButton);
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
		document.getElementById("pageNumForSelect").textContent=pageNowForSelect+'/'+pageTotalForSelect;
	}).catch(function(error){
		console.log(error);
	});
}
function lastPageForSelect(){
	if(typeof pageNowForSelect==='undefined'){
		alert("请先进行查询！");
		return;
	}
	if(pageNowForSelect===1){
		alert("已经是第一页！");
		return;
	}
	pageNowForSelect--;
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
	for(let i=25*(pageNowForSelect-1);i<Math.min(classArrayForSelect.length,25*pageNowForSelect);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		var cell6=document.createElement("td");
		cell1.textContent=classArrayForSelect[i].code+"\n"+classArrayForSelect[i].name+"\n"+classArrayForSelect[i].credits+"学分 "+classArrayForSelect[i].period+"学时 "+classArrayForSelect[i].education+" "+classArrayForSelect[i].courseType+" "+classArrayForSelect[i].classType+" "+classArrayForSelect[i].teachLang+" "+classArrayForSelect[i].examMode+" "+classArrayForSelect[i].gradation;
		cell2.textContent=classArrayForSelect[i].departmentCode+"\n"+classArrayForSelect[i].departmentName;
		cell3.textContent=classArrayForSelect[i].teacher;
		cell4.textContent=classArrayForSelect[i].tpc;
		cell5.textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
		var selectButton=document.createElement("button");
		if(classArrayForSelect[i].selected==false)
			selectButton.innerHTML="选课";
		else
			selectButton.innerHTML="取消选课";
		selectButton.addEventListener('click',function(){
			var selectInfo={
				id:classArrayForSelect[i].id
			}
			if(classArrayForSelect[i].selected==false){
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
						classArrayForSelect[i].selected=true;
						classArrayForSelect[i].selectedCount++;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="取消选课";
					}
					else
						alert(result.msg);
				}).catch(function(error){
					console.log(error);
				});
			}
			else{
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
						classArrayForSelect[i].selected=false;
						classArrayForSelect[i].selectedCount--;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="选课";
					}
					else
						alert(result.msg);
				}).catch(function(error){
					console.log(error);
				});
			}
		});
		cell6.appendChild(selectButton);
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
	document.getElementById("pageNumForSelect").textContent=pageNowForSelect+'/'+pageTotalForSelect;
}
function nextPageForSelect(){
	if(typeof pageNowForSelect==='undefined'){
		alert("请先进行查询！");
		return;
	}
	if(pageNowForSelect>=pageTotalForSelect){
		alert("已经是最后一页！");
		return;
	}
	pageNowForSelect++;
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
	for(let i=25*(pageNowForSelect-1);i<Math.min(classArrayForSelect.length,25*pageNowForSelect);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		var cell6=document.createElement("td");
		cell1.textContent=classArrayForSelect[i].code+"\n"+classArrayForSelect[i].name+"\n"+classArrayForSelect[i].credits+"学分 "+classArrayForSelect[i].period+"学时 "+classArrayForSelect[i].education+" "+classArrayForSelect[i].courseType+" "+classArrayForSelect[i].classType+" "+classArrayForSelect[i].teachLang+" "+classArrayForSelect[i].examMode+" "+classArrayForSelect[i].gradation;
		cell2.textContent=classArrayForSelect[i].departmentCode+"\n"+classArrayForSelect[i].departmentName;
		cell3.textContent=classArrayForSelect[i].teacher;
		cell4.textContent=classArrayForSelect[i].tpc;
		cell5.textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
		var selectButton=document.createElement("button");
		if(classArrayForSelect[i].selected==false)
			selectButton.innerHTML="选课";
		else
			selectButton.innerHTML="取消选课";
		selectButton.addEventListener('click',function(){
			var selectInfo={
				id:classArrayForSelect[i].id
			}
			if(classArrayForSelect[i].selected==false){
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
						classArrayForSelect[i].selected=true;
						classArrayForSelect[i].selectedCount++;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="取消选课";
					}
					else
						alert(result.msg);
				}).catch(function(error){
					console.log(error);
				});
			}
			else{
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
						classArrayForSelect[i].selected=false;
						classArrayForSelect[i].selectedCount--;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[4].textContent=classArrayForSelect[i].selectedCount+"/"+classArrayForSelect[i].limitCount;
						document.getElementById("showSelect").querySelector("table").rows[i-25*(pageNowForSelect-1)+1].cells[5].querySelector('button').innerHTML="选课";
					}
					else
						alert(result.msg);
				}).catch(function(error){
					console.log(error);
				});
			}
		});
		cell6.appendChild(selectButton);
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
	document.getElementById("pageNumForSelect").textContent=pageNowForSelect+'/'+pageTotalForSelect;
}
function query(){
	classArrayForQuery=[];
	var semester=document.getElementById("semesterForQuery").value;
	var college=document.getElementById("collegeForQuery").value;
	var courseName=document.getElementById("courseNameForQuery").value;
	var courseID=document.getElementById("courseIDForQuery").value;
	var teacher=document.getElementById("teacherForQuery").value;
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
				classArrayForQuery.push(thisClass);
			}
		}
	}).then(function(){
		pageTotalForQuery=Math.ceil(classArrayForQuery.length/25);
		pageNowForQuery=1;
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
		for(let i=0;i<Math.min(classArrayForQuery.length,25);i++){
			var row=document.createElement("tr");
			var cell1=document.createElement("td");
			var cell2=document.createElement("td");
			var cell3=document.createElement("td");
			var cell4=document.createElement("td");
			var cell5=document.createElement("td");
			cell1.textContent=classArrayForQuery[i].code+"\n"+classArrayForQuery[i].name+"\n"+classArrayForQuery[i].credits+"学分 "+classArrayForQuery[i].period+"学时 "+classArrayForQuery[i].education+" "+classArrayForQuery[i].courseType+" "+classArrayForQuery[i].classType+" "+classArrayForQuery[i].teachLang+" "+classArrayForQuery[i].examMode+" "+classArrayForQuery[i].gradation;
			cell2.textContent=classArrayForQuery[i].departmentCode+"\n"+classArrayForQuery[i].departmentName;
			cell3.textContent=classArrayForQuery[i].teacher;
			cell4.textContent=classArrayForQuery[i].tpc;
			cell5.textContent=classArrayForQuery[i].limitCount;
			row.appendChild(cell1);
			row.appendChild(cell2);
			row.appendChild(cell3);
			row.appendChild(cell4);
			row.appendChild(cell5);
			tbody.appendChild(row);
		}
		table.appendChild(tbody);
		document.getElementById("showCourse").appendChild(table);
		document.getElementById("pageNumForQuery").textContent=pageNowForQuery+'/'+pageTotalForQuery;
	}).catch(function(error){
		console.log(error);
	});
}
function lastPageForQuery(){
	if(typeof pageNowForQuery==='undefined'){
		alert("请先进行查询！");
		return;
	}
	if(pageNowForQuery===1){
		alert("已经是第一页！");
		return;
	}
	pageNowForQuery--;
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
	for(let i=25*(pageNowForQuery-1);i<Math.min(classArrayForQuery.length,25*pageNowForQuery);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		cell1.textContent=classArrayForQuery[i].code+"\n"+classArrayForQuery[i].name+"\n"+classArrayForQuery[i].credits+"学分 "+classArrayForQuery[i].period+"学时 "+classArrayForQuery[i].education+" "+classArrayForQuery[i].courseType+" "+classArrayForQuery[i].classType+" "+classArrayForQuery[i].teachLang+" "+classArrayForQuery[i].examMode+" "+classArrayForQuery[i].gradation;
		cell2.textContent=classArrayForQuery[i].departmentCode+"\n"+classArrayForQuery[i].departmentName;
		cell3.textContent=classArrayForQuery[i].teacher;
		cell4.textContent=classArrayForQuery[i].tpc;
		cell5.textContent=classArrayForQuery[i].limitCount;
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell4);
		row.appendChild(cell5);
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
	document.getElementById("pageNumForQuery").textContent=pageNowForQuery+'/'+pageTotalForQuery;
}
function nextPageForQuery(){
	if(typeof pageNowForQuery==='undefined'){
		alert("请先进行查询！");
		return;
	}
	if(pageNowForQuery>=pageTotalForQuery){
		alert("已经是最后一页！");
		return;
	}
	pageNowForQuery++;
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
	for(let i=25*(pageNowForQuery-1);i<Math.min(classArrayForQuery.length,25*pageNowForQuery);i++){
		var row=document.createElement("tr");
		var cell1=document.createElement("td");
		var cell2=document.createElement("td");
		var cell3=document.createElement("td");
		var cell4=document.createElement("td");
		var cell5=document.createElement("td");
		cell1.textContent=classArrayForQuery[i].code+"\n"+classArrayForQuery[i].name+"\n"+classArrayForQuery[i].credits+"学分 "+classArrayForQuery[i].period+"学时 "+classArrayForQuery[i].education+" "+classArrayForQuery[i].courseType+" "+classArrayForQuery[i].classType+" "+classArrayForQuery[i].teachLang+" "+classArrayForQuery[i].examMode+" "+classArrayForQuery[i].gradation;
		cell2.textContent=classArrayForQuery[i].departmentCode+"\n"+classArrayForQuery[i].departmentName;
		cell3.textContent=classArrayForQuery[i].teacher;
		cell4.textContent=classArrayForQuery[i].tpc;
		cell5.textContent=classArrayForQuery[i].limitCount;
		row.appendChild(cell1);
		row.appendChild(cell2);
		row.appendChild(cell3);
		row.appendChild(cell4);
		row.appendChild(cell5);
		tbody.appendChild(row);
	}
	table.appendChild(tbody);
	document.getElementById("showCourse").appendChild(table);
	document.getElementById("pageNumForQuery").textContent=pageNowForQuery+'/'+pageTotalForQuery;
}

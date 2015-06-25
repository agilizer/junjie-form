var userEditUrl="/adminUser/edit/";
var addRoleUrl="/adminUser/addRole";
var removeRoleUrl="/adminUser/removeRole";
var userEnableddUrl="/adminUser/enabled"

function userEdit(aObj){
	var userId = $(aObj).attr("data-id");
	jQuery.ajax({
		type : 'POST',
		url : userEditUrl+userId,
		success : function(data, textStatus) {
			if(data.success){
				$("#userId").val(userId);
				$("#usernamdSamp").html($("#username"+userId).html());
				var roleLength=data.roleList.length
				//$('input[name="enabled"]').removeAttr("checked"); 
				var radio_oj = document.getElementsByName("enabled")
				for(var i=0;i<radio_oj.length;i++) {//循环
		            radio_oj[i].checked=false; //修改选中状态
			   }
			   for(var i=0;i<roleLength;i++){
					console.log("roleId-->"+data.roleList[i].id)
					document.getElementById("role"+data.roleList[i].id).checked=true;
				}
				console.log($("#userEnabled"+userId).attr("data-enabled"));
				if($("#userEnabled"+userId).attr("data-enabled")=="false"){
					document.getElementById("userEnabledCheckbox").checked=true;
				}else{
					document.getElementById("userEnabledCheckbox").checked=false;
				}
				$('#userEditModal').modal('show')
			}else{
				alert("保存数据出错！请联系客服！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}


function changeRole(radioObj){
	var requestUrl = "";
	if(radioObj.checked==true){
		requestUrl = addRoleUrl;
	}else{
		requestUrl = removeRoleUrl;
	}
	var userId = $("#userId").val();
	var roleId = $(radioObj).attr("data-id");
	jQuery.ajax({
		type : 'POST',
		url : requestUrl,
		data:{userId:userId,roleId:roleId},
		success : function(data, textStatus) {
			if(data=="success"){
			}else{
				alert("保存数据出错！请联系客服！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}



function userEnabled(checkedObj){
	var userId = $("#userId").val();
	var enabled= document.getElementById("userEnabledCheckbox").checked;
	//enabled的值和checkbox相反
	enabled=!enabled;
	jQuery.ajax({
		type : 'POST',
		url : userEnableddUrl,
		data:{userId:userId,enabled:enabled},
		success : function(data, textStatus) {
			if(data=="success"){
				var enabledJq = $("#userEnabled"+userId)
				enabledJq.attr("data-enabled",enabled)
				if(enabled==true){
					enabledJq.html("正常")
				}else{
					enabledJq.html("禁用")
				}
			}else{
				alert("保存数据出错！请联系客服！");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}
var userInfoSaveUrl = "/userCenter/userInfoSave";

function userInfoSubmit(){
	$.ajax({
		url : userInfoSaveUrl,
		type : "POST",
		data : $("#userInfoForm").serialize(),
		success : function(data) {
			if (data) {				
				alert("更新成功 ！");
			} else {
				alert("没有找到相关数据！");
			}
		}
	});
}

function changePasswordSubmit(){
	$.ajax({
		url : changePasswordUrl,
		type : "POST",
		data : $("#changePasswordForm").serialize(),
		success : function(data) {
			if (data) {				
				alert("更新成功 ！");
			} else {
				alert("没有找到相关数据！");
			}
		}
	});
}

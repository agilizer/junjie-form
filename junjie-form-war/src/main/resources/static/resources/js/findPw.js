
$(function(){

	// 回车事件
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$('#register_btn').trigger('click');
		}
	}
	// 用户注册验证
	$('#findPw_btn').on('click',function(){
		var fail = $('.fail');
		var errorTx = fail.find('.text');
		if ($('#mobile').val() == '' || $('#mobile').val() == $('#mobile').attr('placeholder')) {
			fail.removeClass('dp-none');
			errorTx.text('请输入您的手机');
			$('#mobile').focus();
		}
		else if (!$('#mobile').val().match(/^1\d{10}$/)) {
			fail.removeClass('dp-none');
			errorTx.text('请输入正确的手机');
			$('#user_mobile').focus();
		}
        else if (!$('#code_num').val().match(/^\d{6}$/) || $('#code_num').val() == $('#code_num').attr('placeholder')) {
			fail.removeClass('dp-none');
			errorTx.text('请输入正确的验证码');
			$('#code_num').focus();
        }
        else if (!$('#password').val().match(/^.{6,}$/)) {
        	fail.removeClass('dp-none');
        	errorTx.text('请输入您的密码, 至少6位');
        	$('#password').focus();
        }
        else if (!$('#password1').val().match(/^.{6,}$/)) {
        	fail.removeClass('dp-none');        	
        	errorTx.text('请确认您的密码, 至少6位');
        	$('#password1').focus();
        }
        else if ($('#password1').val() !== $('#password').val()) {
        	fail.removeClass('dp-none'); 
        	errorTx.text('两次密码输入不符,请重新输入');
        	$('#password1').focus();
        }
        else{     	
        	$.ajax({
        		url : resetPasswordUrl,
        		type : "POST",
        		data : $("#findPwForm").serialize(),
        		success : function(data) {
        			if (data.success) {
        				alert("恭喜你,找回密码成功 ！");
        				location.href="/auth/loginPage";
        			} else {
        				fail.removeClass('dp-none'); 
        	        	errorTx.text(data.errorMsg);
        			}
        		}
        	});
       	
        }
	});	
});
//忘记密码部分
//验证码
$(document).on("click", "#sendResetSmsCodeBtn", function() {
	var resetUsername = $("#mobile").val();
	if (resetUsername =='' || !resetUsername.match("^1[0-9]{10}")) {
		var fail = $('.fail');
		fail.removeClass('dp-none'); 
		var errorTx = fail.find('.text');
		errorTx.text('请输入正确的手机');
	}else{
		$.ajax({
			url : sendFindPwSmsCodeUrl + resetUsername,
			type : "POST",
			success : function(data) {
				var fail = $('.fail');
				var errorTx = fail.find('.text');					
				if (data.success) {
					$("#sendResetSmsCodeBtn").attr("disabled","true");
					timeout();					
				} else {
					fail.removeClass('dp-none'); 
					errorTx.text(usernameError);
					$('#mobile').focus();
				}	
			}
		});
	}
});

$(document).on("focus","#resetUsername",function(){
	$("#sendResetSmsCodeBtn").removeAttr("disabled");
	$("#sendResetSmsCodeBtn").html(getSmsCode);
});

//改变验证码获取状态
function timeout(){  
	$('.code').html('  <lable id="timeber"> 60 </lable>秒后重新获取 ');  
		timer = self.setInterval(calculate,1000);  
	$("#sendRegSmsCodeBtn").removeAttr("disabled");
} 
// 验证码倒计时
function calculate(){  
	var t = $('#timeber').html();  
	if(t > 0){  
		$('#timeber').html(t-1);  
	}else{  
		window.clearInterval(timer);  
		$('.code').html("获取短信验证码");  
	}    
}  
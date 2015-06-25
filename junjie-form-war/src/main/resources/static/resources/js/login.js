$(function(){

	// cookie  读取写入
	if ($.cookie("rmbUser") == "true") {
		$("#login_auto").attr("checked", true);
		$("#mobile").val($.cookie("username"));
		$("#password").val($.cookie("password"));
	}

	// enter 回车键
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$('#login_btn').trigger('click');
		}
	}

	// 用户登录
	$('#login_btn').on('click',function(){
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
        else if (!$('#password').val().match(/^.{6,}$/)) {
        	fail.removeClass('dp-none');
        	errorTx.text('请输入您的密码, 至少6位');
        	$('#password').focus();
        }
        else{
			//save();
        	fail.addClass('dp-none');
        	$("#loginForm").submit();
        }
	});

	// 合作账户登录
	$('.sel_box_login').on('click',function(){
		var t= $(this);
		if(!t.hasClass('active')){
			$('.other_list_men').addClass('border').animate({height:"170px"},'300',function(){
				t.addClass('active');
				$(this).find('li').each(function(){
					$(this).on('click',function(){
						t.find('.login_path').text($(this).text());
					});
				});
			});			
		}else{
			$('.other_list_men').animate({height:"0px"},'300',function(){
				$(this).removeClass('border');
				t.removeClass('active');
			});	
		}
	});
});

	// cookie  存入
    function save() {
        if ($("#login_auto").attr("checked")) {
            var username = $("#mobile").val();
            var password = $("#password").val();
            $.cookie("rmbUser", "true", { expires: 7 });
            $.cookie("username", username, { expires: 7 });
            $.cookie("password", password, { expires: 7 });
        }
        else {
            $.cookie("rmbUser", "false", { expire: -1 });
            $.cookie("username", "", { expires: -1 });
            $.cookie("password", "", { expires: -1 });
        }
    };


$(function(){
	// header  下拉
	$('.user_order').hover(function(){
		$(this).addClass('li_sel_active');
		$('.nav_select').removeClass('dp-none');
	},function(){
		$(this).removeClass('li_sel_active');
		$('.nav_select').addClass('dp-none');
	});


	// 头部导航切换
	$('.h_menu_ul li a').on('click',function(){
		$('.h_menu_ul li a').removeClass('active');
		$(this).addClass('active');
	});

	// 表格操作---数量加减
	// 加
	$('.num > .add').on('click',function(){
		var mVal = $(this).attr('data-max');
		var cut  = $(this).closest('.num').find('.cut');
        cut.removeAttr("disabled"); 
		var t = $(this).prev('.text').find('.num_text');
		t.val(parseInt(t.val())+1);
        if (parseInt(t.val())< (mVal) && parseInt(t.val())>=0){
        	cut.removeClass('no');
        }else{
        	$(this).attr('disabled','true').addClass('no');
        	cut.removeClass('no');

        }
	});

	// 减
	$('.num > .cut').on('click',function(){
		var t = $(this).next('.text').find('.num_text');
		var add  = $(this).closest('.num').find('.add');
        t.val(parseInt(t.val())-1);
        if (parseInt(t.val()) <= 0){
           $(this).attr('disabled','true').addClass('no');
        }else if(parseInt(t.val()) > 0 && parseInt(t.val()) <1200){
        	add.removeClass('no').removeAttr('disabled');
        }
	});   


	// 商城nav、资源单table点击效果
	function tab(obj){
		obj.each(function(i,item){
			$(this).off('click').on('click',function(event){
				event.stopPropagation();
				obj.removeClass('active');
				$(this).addClass('active');
			})
		}); 			
	}

	// 商城nav 
	tab($('.c_subnav_ul li a'));

	// 资源单nav
	tab($('.city_tab_ul li'));
	 
	tab($('.result_ul_header li.money a'));
	// 复选框操作
	$("#all").click(function(){   
		if(this.checked){   
			$("input[name='checkbox']").each(function(){this.checked=true;});
		}
		else{   
			$("input[name='checkbox']").each(function(){this.checked=false;});   
		}   
	});




	// 求购报价刷新计时
	var timeIndex = 0;//初始化
	var times;

	setTime();
	times = setInterval(setTime,1000);

	// 点击刷新
	$('.click_path').on('click',function(event){
		event.stopPropagation();
		clearInterval(times); 
		timeIndex = 0;
		setTime();
		times = setInterval(setTime,1000);
	});

	// 计时
	function setTime(){
		var hour 	 =	parseInt(timeIndex / 3600); //计算时
		var minutes  =	parseInt((timeIndex % 3600)/60); //计算分
		var seconds  =	parseInt(timeIndex % 60); //计算秒
		hour = hour < 10 ? "0" + hour : hour;
		minutes = minutes < 10 ? "0" + minutes : minutes;
		seconds = seconds < 10 ? "0" + seconds : seconds;
		$(".date .times").text(hour + ":" + minutes + ":" + seconds);
		timeIndex++;
	}

	// 求购报价采购单hover
	$('.quo_ul').hover(function(){
		var topSider = $(this).find('.sider');
		$('.sider').css('display','none');
		topSider.css('display','block');
		$('.quo_ul').removeClass('hover');
		$(this).addClass('hover');
	},function(){
		var topSider = $(this).find('.sider');
		topSider.css('display','none');
		$(this).removeClass('hover'); 
	});

	// 求购报价弹层

	$('.b-btn').on('click',function(){
		closeMaskModel('block');
	});

	$('.close_icon').on('click',function(){
		closeMaskModel();
	});

	// 提交
	$('.commint_money').on('click',function(){
		// $ajax({
		// 	url:'',
		// 	....
		// 	success:function(data){
		// 		closeMaskModel();
		// 	}
		// });
	});

	// 关闭弹层弹窗
	function closeMaskModel(news){
		var mask = $('.mask');
		var models = $('.models');
		if(typeof(news) !== 'string'){			
			mask.addClass('dp-none');
			models.addClass('dp-none');				
		}
		else{
			mask.removeClass('dp-none');
			models.removeClass('dp-none');				
		}
	}

	// 物流tab
	$('#logistics_tab a').on('click',function(){
		 var _tab =  $(this).index();
		$(this).addClass('active').siblings().removeClass('active');
		$('.tab_box').eq(_tab).css('display','block').siblings().css('display','none');
		$('#logistics_tab').show()
	});




    $('.carousel').hover(function(){
        $(this).find('.btn').fadeIn();
    },function(){
        $(this).find('.btn').fadeOut();
    })

    $('.down_menu_ul .item').hover(function(){
        $('.down_menu_ul .item').removeClass('active');
        $(this).addClass('active');
        $(this).children('.item_div').show();
    },function(){
        $(this).removeClass('active');
        $(this).children('.item_div').hide();
    })

   	$('.more_menu').hover(function(){
		$('.down_menu').removeClass('dp-none');
	},function(){
		$('.down_menu').addClass('dp-none');
	});


   	// 资源单tab点击选中
   	$('.breed_ul li').on('click',function(){
   		$(this).addClass('active').siblings().removeClass('active');
   	});
   	$('.resource_tab_box li').on('click',function(){
   		$(this).addClass('active').siblings().removeClass('active');
   	});

   	$('#upload_resource').on('click',function(){
   		var path = $(this).attr('data-path');
   		if(path !== "" && path !== undefined){
   			location.href=path;
   		}else{
   			return;
   		}
   	});


    // 我的竞拍
   	$('#user_auction_tab a').on('click',function(){
   		$(this).addClass('active').siblings().removeClass('active');
   	});

   	// 行情
   	$('#ATopTabNewGroup a').on('click',function(){
   		$(this).addClass('active').siblings().removeClass('active');
   	});


   	// 交易指南
   	$('.accord .accord-tl').on('click',function(){
   		$(this).closest('.accord').toggleClass('active').find('.accord-text').toggleClass('dp-none');
   	});

   	// 商城展开

   	$('.btn_more').on('click',function(){
   		$(this).toggleClass('open');

   		var m =  $('.btn_more').attr('data-ul');
   		var result_ul = $(this).closest('.item_ul_div').find('.result_ul_son');
   		var dataUl =  result_ul.attr('data-ul');
   		if(dataUl != undefined && dataUl != "" && dataUl === m){
   			result_ul.toggleClass('dp-none');
   		}
   	});
});
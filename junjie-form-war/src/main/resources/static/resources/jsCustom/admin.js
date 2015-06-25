function productCheck(productId,value){
	if(confirm("请确认操作，如果通过则可以在商城中显示！")){
		$.ajax({
			url : productCheckUrl+productId,
			type : "POST",
			data : {result:value},
			success : function(data) {
				if (data) {				
					alert("提交成功 ！");
				} else {
					alert("没有找到相关数据");
				}
				history.go(0) 
			}
		});
	}
	return false;
}
function productChangePriceCheck(productChangePriceId,value){
	if(confirm("请确认操作，如果通过则可以在商城中显示！")){
		$.ajax({
			url : productChangePriceCheckUrl+productChangePriceId,
			type : "POST",
			data : {result:value},
			success : function(data) {
				if (data) {				
					alert("提交成功 ！");
				} else {
					alert("没有找到相关数据");
				}
				history.go(0) 
			}
		});
	}
	return false;
}

function orderCheck(orderId,value){
	if(confirm("请确认操作，如果通过请联系物流和客服提贷！")){
		$.ajax({
			url : orderCheckUrl+orderId,
			type : "POST",
			data : {result:value},
			success : function(data) {
				if (data) {				
					alert("提交成功 ！");
				} else {
					alert("没有找到相关数据");
				}
				history.go(0) 
			}
		});
	}
	return false;
}
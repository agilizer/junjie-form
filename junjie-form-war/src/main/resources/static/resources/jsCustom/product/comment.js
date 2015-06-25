function orderCreate(productId){
	$("#productId").val(productId);
	var orderNumber = parseInt($("#productOrderNumber"+productId).val())
	var maxNumber = parseInt($("#productOrderNumber"+productId).attr("max"))
	if(orderNumber<=0||orderNumber>maxNumber){
		alert("当前只能购买1--"+maxNumber+"吨!");
		return false;
	}
	$("#orderNumber").val(orderNumber);
	$("#orderForm").submit();
}
function submitComment(btn) {
	jQuery.ajax({
		type : 'POST',
		url : '/comment/product',
		data : $("#commentForm").serialize(),
		success : function(data, textStatus) {
			if(data.success){
				alert("感谢您的评价!");
				var html='<div class="row col-sm-offset-1 commentShow">'+
					'<span class="name col-sm-2">您自己</span>'+
					'<span class="name col-sm-4">'+$('input[name="star"]:checked').val()+'</span>'+  
					'<span class="date col-sm-2">刚刚</span>'+
					'<div class="content col-sm-8">'+$("#commentContent").html()+'</div></div>';
				$("#commentFormDiv").after(html)
			}else{
				alert(data.errorMsg)
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		},
	});
	return false;

}
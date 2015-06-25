var wuluTypeListUrl = "/wulu/typeList";
var wuluSaveUrl = "/seller/sendGood"
$(document).ready(function() {
	jQuery.ajax({
		type : 'POST',
		url : wuluTypeListUrl,
		success : function(data, textStatus) {
			var length=data.length;
			console.log(length)
			var selectObj = $("#wuluTypeSelect")
			for(var i=0;i<length;i++){
				selectObj.append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
	$("#sendGoodForm").validate({
		ignore : "",
		submitHandler : function(form) {
			jQuery.ajax({
				type : 'POST',
				url : wuluSaveUrl,
				data : $("#sendGoodForm").serialize(),
				success : function(data, textStatus) {
					if(data.success){
						var wuluName=$("#wuluTypeSelect").find("option:selected").text()
						$("#paymentFinishDiv").html(wuluName+":"+$("#wuluId").val());
						$("#wuluModal").modal("hide");
					}else{
						alert("保存数据出错！请联系客服！");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	});
	
});

function sendGood(obj){
	var jObj = $(obj);
	$("#orderId").val(jObj.attr("data-id"));
	$("#wuluModal").modal("show");
}
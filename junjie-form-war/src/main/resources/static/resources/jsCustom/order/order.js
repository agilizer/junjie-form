/**
 * 
 */
var orderAddressSaveUrl="/orderAddress/save";
var invoiceSaveUrl = "/invoice/save";
var areaGetUrl="/area/list"
	 	
function changeOrderAddress(checkBoxObj){
	$("#orderAddressId").val($(checkBoxObj).val())
}
function changeInvoice(checkBoxObj){
	$("#invoiceId").val($(checkBoxObj).val())
}
$(document).ready(function() {
	var addressRadios = $('input[name="addressRadio"]');
	if(addressRadios.length>0){
		var addressRadio = $('input[name="addressRadio"]').first()
		addressRadio.attr("checked","checked");
		$("#orderAddressId").val(addressRadio.val())
	}
	var invoiceRadios = $('input[name="invoiceRadio"]');
	if(invoiceRadios.length>0){
		var invoiceRadio = $('input[name="invoiceRadio"]').first()
		invoiceRadio.attr("checked","checked");
		$("#invoiceId").val(invoiceRadio.val())
	}
	
	$("#orderAddressForm").validate({
		ignore : "",
		submitHandler : function(form) {
			var addressPrefix = $("#areaSelectOne").find("option:selected").text()+
			$("#areaSelectTwo").find("option:selected").text()+$("#areaSelectThree").find("option:selected").text();
			if(addressPrefix.indexOf('请选择:')==-1){
				$("#orderAddressFormAddress").val(addressPrefix+$("#orderAddressFormAddress").val());
			}else{
				alert("请选择正确的区域！");
				return false;
			}
			jQuery.ajax({
				type : 'POST',
				url : orderAddressSaveUrl,
				data : $("#orderAddressForm").serialize(),
				success : function(data, textStatus) {
					if(data.success){
						var text=$("#orderAddressFormName").val() +"   " +$("#orderAddressFormTel").val()+"   "+ $("#orderAddressFormAddress").val()  
						var html = '<div class="radio"><label> <input name="addressRadio" onclick="changeOrderAddress(this)" checked="" type="radio" value="'+data.data+'" /> '+
						text+'</label></div>';
						$("#orderAddressUl").append(html);
						$("#orderAddressId").val(data.data)
						$('#addAddress').modal('hide')
					}else{
						alert("保存数据出错！请联系客服！");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	});
	
	$("#invoiceForm").validate({
		ignore : "",
		submitHandler : function(form) {
			jQuery.ajax({
				type : 'POST',
				url : invoiceSaveUrl,
				data : $("#invoiceForm").serialize(),
				success : function(data, textStatus) {
					if(data.success){
						var text="增值："+$("#companyName").val()
						var html = '<div class="radio"><label> <input name="invoiceRadio" onclick="changeInvoice(this)" checked="" type="radio" value="'+data.data+'" /> '+
						text+'</label></div>';
						$("#invoiceUl").append(html);
						$("#invoiceId").val(data.data)
						$('#addInvoice').modal('hide')
					}else{
						alert("保存数据出错！请联系客服！");
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	});
	
	
	$("#invoiceNormalForm").validate({
		ignore : "",
		submitHandler : function(form) {
			jQuery.ajax({
				type : 'POST',
				url : invoiceSaveUrl,
				data : $("#invoiceNormalForm").serialize(),
				success : function(data, textStatus) {
					if(data.success){
						var text="普通："+$("#invoiceNormalCompanyName").val() +"  发票内容 "+$("#invoiceNormalContent").val()
						var html = '<div class="radio"><label> <input name="invoiceRadio" onclick="changeInvoice(this)" checked="" type="radio" value="'+data.data+'" /> '+
						text+'</label></div>';
						$("#invoiceUl").append(html);
						$("#invoiceId").val(data.data)
						$('#addInvoiceNormal').modal('hide')
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

function changeArea(selectObj,targetId){
	selectObj = $(selectObj);
	jQuery.ajax({
		type : 'POST',
		url : areaGetUrl,
		data : {parentId:selectObj.val()},
		success : function(data, textStatus) {
			var length=data.length
			if(length>0){
				var html = "<option value='-1' selected='selected'>请选择:</option>"  ;
				for(var i=0;i<length;i++){
					html=html+"<option value='"+data[i].id+"'>"+data[i].name+"</option>"
				}
				$("#"+targetId).html(html);
				if(targetId=="areaSelectTwo"){
					$("#areaSelectThree").html("<option value='-1' selected='selected'>请选择:</option>");
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	
}
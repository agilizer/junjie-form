function setMainPhoto(btn){
	var obj = $(btn)
	$("#mainPhotoId").val(obj.attr('data-id'))
	obj.html("设置首图成功")
}
var catalogDataUrl = "/product/listCatalog/";
var deleteFileUrl = '/product/deleteFile/';
var addToCarouselUrl = "/product/addToCarousel/";
var removeFromCarouselUrl = "/product/removeFromCarousel/"
function changeSelect(id, targetSelectId, genNext) {
	$
			.ajax({
				url : catalogDataUrl + $('#' + id).val(),
				type : "POST",
				success : function(data) {
					// alert(data.length)
					if (data.length > 0) {
						if (targetSelectId == 'catalogLevelThree') {
							$("#" + targetSelectId + "Div")
									.html(
											'<select class="form-control" name="catalog.id" style="" id="catalogLevelThree"></select>');
							$("#catalogLevelTwo").attr("name",
									"catalogLevelTwo");
						}
						jQuery("#" + targetSelectId).empty();
						var temp;
						for (var i = 0; i < data.length; i++) {
							temp = data[i]
							jQuery("#" + targetSelectId).append(
									"<option value='" + temp.id + "'>"
											+ temp.text + "</option>");
						}
						$("#" + targetSelectId).show()
						if (genNext == true) {
							changeSelect("catalogLevelTwo", "catalogLevelThree")
						}
					} else {
						jQuery("#" + targetSelectId).empty();
						if (targetSelectId == 'catalogLevelThree') {
							$("#" + targetSelectId + "Div")
									.html(
											'<input type="text"  placeholder="自定义分类 可以为空" id="catalogLevelThree"  class="form-control" name="customCatalogName">');
							$("#catalogLevelTwo").attr("name", "catalog.id");
						}
					}
					var catalogJoinName = jQuery("#catalogLevelOne").find(
							"option:selected").text();
					catalogJoinName = catalogJoinName
							+ "->"
							+ jQuery("#catalogLevelTwo")
									.find("option:selected").text();
					$("#catalogJoinName").val(catalogJoinName);
				}
			});
}
$(document).ready(function() {
	// $("#catalogLevelOne").select2()
	/**
	 * $('#catalogLevelOne').change(function() { changeSelect("catalogLevelOne",
	 * "catalogLevelTwo") }) $('#catalogLevelTwo').change(function() {
	 * changeSelect("catalogLevelTwo", "catalogLevelThree") })
	 * changeSelect("catalogLevelOne", "catalogLevelTwo", true)
	 */
});

/**
 * Created by asdtiang on 15-3-2.
 */
(function($, window, undefined) {
	$.danidemo = $
			.extend(
					{},
					{

						addLog : function(id, status, str) {
							var d = new Date();
							var li = $('<li />', {
								'class' : 'demo-' + status
							});

							var message = '[' + d.getHours() + ':'
									+ d.getMinutes() + ':' + d.getSeconds()
									+ '] ';

							message += str;

							li.html(message);

							$(id).prepend(li);
						},

						addFile : function(id, i, file) {
							var template = '<div id="demo-file'
									+ i
									+ '">'
									+ '<img  class="demo-image-preview" id="img'
									+ i
									+ '"/>'
									+ '<span class="demo-file-id">#'
									+ i
									+ '</span> - '
									+ file.name
									+ ' <span class="demo-file-size" id="fileSize'
									+ i
									+ '">('
									+ $.danidemo.humanizeSize(file.size)
									+ ')</span><br />状态: <span class="demo-file-status">等待上传</span>'
									+ '<div class="progress progress-striped active">'
									+ '<div class="progress-bar" role="progressbar" style="width: 0%;">'
									+ '<span class="sr-only">0% 完成</span>'
									+ '</div>' + '</div>' + '</div>';
							var i = $(id).attr('file-counter');
							if (!i) {
								$(id).empty();
								i = 0;
							}

							i++;

							$(id).attr('file-counter', i);

							$(id).prepend(template);
						},

						updateFileStatus : function(i, status, message) {
							$('#demo-file' + i).find('span.demo-file-status')
									.html(message).addClass(
											'demo-file-status-' + status);
						},

						updateFileProgress : function(i, percent) {
							$('#demo-file' + i).find('div.progress-bar').width(
									percent);

							$('#demo-file' + i).find('span.sr-only').html(
									percent + ' Complete');
						},

						humanizeSize : function(size) {
							var i = Math.floor(Math.log(size) / Math.log(1024));
							return (size / Math.pow(1024, i)).toFixed(2) * 1
									+ ' ' + [ 'B', 'kB', 'MB', 'GB', 'TB' ][i];
						}

					}, $.danidemo);
})(jQuery, this);

$(function() {
	var extraData = {
		productId : $("#productId").val()
	}
	$('#drag-and-drop-zone')
			.dmUploader(
					{
						url : fileUploadUrl,
						dataType : 'json',
						allowedTypes : 'image/*',
						extFilter : 'png,jpeg,jpg,gif',
						extraData : extraData,
						// extraData:extraData,
						onInit : function() {
						},
						onBeforeUpload : function(id) {

						},
						onNewFile : function(id, file) {
							$.danidemo.addFile('#demo-files', id, file);
							/** * Begins Image preview loader ** */
							if (typeof FileReader !== "undefined") {

								var reader = new FileReader();

								// Last image added
								var img = $('#demo-files').find(
										'.demo-image-preview').eq(0);

								reader.onload = function(e) {
									img.attr('src', e.target.result);
								}

								reader.readAsDataURL(file);

							} else {
								// Hide/Remove all Images if FileReader isn't
								// supported
								$('#demo-files').find('.demo-image-preview')
										.remove();
							}
							/** * Ends Image preview loader ** */

						},
						onComplete : function() {
							// /$.danidemo.addLog('#demo-debug', 'default', 'All
							// pending tranfers completed');
						},
						onUploadProgress : function(id, percent) {
							var percentStr = percent + '%';

							$.danidemo.updateFileProgress(id, percentStr);
						},
						onUploadSuccess: function (id, data) {
					        	/**
					        	 * upload success
					        	 */		
					            $.danidemo.updateFileStatus(id, 'success', '上传完成');
					            $.danidemo.updateFileProgress(id, '100%');
					            var html='<input type="hidden" name="images.id" value="'+data.fileId+'" id="imageId'+data.fileId+'"/>'
					            $("#catalogJoinName").after(html)
					            html='<button class="btn btn-primary btn-xs" type="button" onclick="setMainPhoto(this)" data-id="'
					            	+data.fileId+'" >设置为首图</button>'+
					            	'&nbsp;<button class="btn btn-primary btn-xs" type="button" onclick="addToCarousel(this)" data-id="'
					            	+data.fileId+'">添加到轮播图片</button>'
					            	+'&nbsp;<button class="btn btn-primary btn-xs" type="button" onclick="insertPhoto(this)" data-url="'
					            	+data.path+'">插入产品详情</button>'
					            $("#img"+id).after(html)
					            var fileSize = $('#demo-files'+id).find('.demo-file-size');
					            html = '<button class="btn btn-danger btn-xs" style="float:right" type="button" data-index-id="'+
					            id+'" onclick="delPhoto(this)" data-id="'+data.fileId+'" >'
					        	+'删除图片</button>'
					        	//console.log(fileSize.html())
					        	$("#fileSize"+id).after(html)
					            //console.log($("#img"+id).attr('id'));
				        },
						onUploadError : function(id, message) {
							$.danidemo.updateFileStatus(id, 'error', message);
						},
						onFileTypeError : function(file) {
							alert(file.name + " 文件类型不正确!!");
						},
						onFileSizeError : function(file) {
							alert(file.name + " 文件大小不正确!!");
						},
						onFallbackMode : function(message) {
							alert("浏览器不支持,文件上传请用chrome firfox,或者ie10,ie11"
									+ message);
						},
						onFileExtError : function(file) {
							alert(file.name + " 文件类型不正确!!支持jpg,jpeg,gif,png");
						}
					});
	/**
	 * 提交付款凭据审核
	 */
	$(document).on("click", "#submitCheckButton", function() {
		if (confirm("注意:提交后不能更改付款凭据")) {
			var orderId = $("#orderId").val();
			$.ajax({
				url : submitCheckUrl,
				data : {
					orderId : orderId
				},
				type : "POST",
				success : function(data) {
					if (data.success) {
						$("#submitCheckButton").html("正在进行付款审核");
						$(".demo-columns").hide();
						$('#submitCheckButton').unbind();
					} else {
						alert(data.errorMsg)
					}
				}
			});
		}
	});
	var maniPhotoId = $("#mainPhotoId").val()
	$("#imageBtnSet" + maniPhotoId).attr("disabled", "");
	$("#imageBtnSet" + maniPhotoId).html("当前首图");
	$("#imageBtnCarousel" + maniPhotoId).attr("disabled", "");
	$("#imageBtnDel" + maniPhotoId).attr("disabled", "");
})

function delPhoto(btn) {
	var obj = $(btn)
	var id = obj.attr('data-id')
	if ($("#mainPhotoId").val() == id) {
		alert("首图不能删除，如果要删除，请设置其它图片为首图！");
		return false;
	}
	if (confirm("确定删除?")) {
		var url = window.location.href
		var data = {
			fileId : id,
			mainPhoto : 'false'
		}
		data = {
			fileId : id,
			productId : $("#productId").val(),
			mainPhoto : 'false'
		}
		if ($("#mainPhotoId").val() == id) {
			$("#mainPhotoId").val("")
		}
		jQuery.ajax({
			type : 'POST',
			url : deleteFileUrl,
			data : data,
			success : function(data, textStatus) {
				if (data.success) {
					var index = obj.attr("data-index-id");
					$("#demo-file" + index).hide();
					$("#imageId" + id).remove()
				} else {
					alert(data.errorMsg);
				}

			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			},
			complete : function(XMLHttpRequest, textStatus) {

			}
		});
		return false;
	}
}
function setMainPhoto(btn) {
	var obj = $(btn)
	var fileInfoId = obj.attr('data-id')
	var oldManiPhotoId = $("#mainPhotoId").val()
	$("#mainPhotoId").val(fileInfoId)
	$("#imageBtnSet" + oldManiPhotoId).removeAttr("disabled");
	$("#imageBtnSet" + oldManiPhotoId).html("设置为首图");
	$("#imageBtnDel" + oldManiPhotoId).removeAttr("disabled");
	$("#imageBtnCarousel" + oldManiPhotoId).removeAttr("disabled");

	$("#imageBtnSet" + fileInfoId).attr("disabled", "");
	$("#imageBtnCarousel" + fileInfoId).attr("disabled", "");
	$("#imageBtnDel" + fileInfoId).attr("disabled", "");
	obj.html("设置首图成功,保存后更新！")
}

function addToCarousel(btn) {
	var obj = $(btn)
	var fileId = obj.attr('data-id')
	$.ajax({
		url : addToCarouselUrl,
		data : {
			fileId : fileId,
			productId : $("#productId").val()
		},
		type : "POST",
		success : function(data) {
			if (data.success) {
				obj.html("轮播图片中移除");
				obj.attr("onclick", "removeFromCarousel(this)");
			} else {
				alert(data.errorMsg)
			}
		}
	});
}

function removeFromCarousel(btn) {
	var obj = $(btn)
	var fileId = obj.attr('data-id')
	$.ajax({
		url : removeFromCarouselUrl,
		data : {
			fileId : fileId,
			productId : $("#productId").val()
		},
		type : "POST",
		success : function(data) {
			if (data.success) {
				obj.html("添加到轮播图片");
				obj.attr("onclick", "addToCarousel(this)");
			} else {
				alert(data.errorMsg)
			}
		}
	});
}

function insertPhoto(btn) {
	var obj = $(btn)
	var path = obj.attr('data-url')
	// Get the editor instance that we want to interact with.
	var oEditor = CKEDITOR.instances.content;
	var value = '<img src="/upload/' + path + '"/>';
	// Check the active editing mode.
	if (oEditor.mode == 'wysiwyg') {
		// Insert HTML code.
		oEditor.insertHtml(value);
	} else
		alert('You must be in WYSIWYG mode!');
}

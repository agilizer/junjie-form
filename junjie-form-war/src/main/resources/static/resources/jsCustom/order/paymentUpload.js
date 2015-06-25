/**
 * Created by asdtiang on 15-3-2.
 */
(function( $, window, undefined ) {
    $.danidemo = $.extend( {}, {

        addLog: function(id, status, str){
            var d = new Date();
            var li = $('<li />', {'class': 'demo-' + status});

            var message = '[' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds() + '] ';

            message += str;

            li.html(message);

            $(id).prepend(li);
        },

        addFile: function(id, i, file){
            var template = '<div id="demo-file' + i + '">' +
                '<span class="demo-file-id">#' + i + '</span> - ' + file.name
                + ' <span class="demo-file-size">(' + $.danidemo.humanizeSize(file.size) +
                ')</span><br />状态: <span class="demo-file-status">等待上传</span>'+
                '<div class="progress progress-striped active">'+
                '<div class="progress-bar" role="progressbar" style="width: 0%;">'+
                '<span class="sr-only">0% 完成</span>'+
                '</div>'+
                '</div>'+
                '</div>';
            var i = $(id).attr('file-counter');
            if (!i){
                $(id).empty();

                i = 0;
            }

            i++;

            $(id).attr('file-counter', i);

            $(id).prepend(template);
        },

        updateFileStatus: function(i, status, message){
            $('#demo-file' + i).find('span.demo-file-status').html(message).addClass('demo-file-status-' + status);
        },

        updateFileProgress: function(i, percent){
            $('#demo-file' + i).find('div.progress-bar').width(percent);

            $('#demo-file' + i).find('span.sr-only').html(percent + ' Complete');
        },

        humanizeSize: function(size) {
            var i = Math.floor( Math.log(size) / Math.log(1024) );
            return ( size / Math.pow(1024, i) ).toFixed(2) * 1 + ' ' + ['B', 'kB', 'MB', 'GB', 'TB'][i];
        }

    }, $.danidemo);
})(jQuery, this);



$(function() {
    $('#drag-and-drop-zone').dmUploader({
        url: fileUploadUrl,
        dataType: 'json',
        allowedTypes: '*',
        extFilter:'png,jpeg,jpg,gif',
        extraData:extraData,
        onInit: function () {
        },
        onBeforeUpload: function (id) {

        },
        onNewFile: function (id, file) {
            $.danidemo.addFile('#demo-files', id, file);
            /*** Begins Image preview loader ***/
            if (typeof FileReader !== "undefined") {

                var reader = new FileReader();

                // Last image added
                var img = $('#demo-files').find('.demo-image-preview').eq(0);

                reader.onload = function (e) {
                    img.attr('src', e.target.result);
                }

                reader.readAsDataURL(file);

            } else {
                // Hide/Remove all Images if FileReader isn't supported
                $('#demo-files').find('.demo-image-preview').remove();
            }
            /*** Ends Image preview loader ***/

        },
        onComplete: function () {
            ///$.danidemo.addLog('#demo-debug', 'default', 'All pending tranfers completed');
        },
        onUploadProgress: function (id, percent) {
            var percentStr = percent + '%';

            $.danidemo.updateFileProgress(id, percentStr);
        },
        onUploadSuccess: function (id, data) {

            $.danidemo.updateFileStatus(id, 'success', '上传完成');
            $.danidemo.updateFileProgress(id, '100%');
            if(data.success == false){
                alert(data.msg)
            }else{
            	$("#paymentImg").attr("src","/upload/"+data.path)
            }
        },
        onUploadError: function (id, message) {
            $.danidemo.updateFileStatus(id, 'error', message);
        },
        onFileTypeError: function (file) {
            alert(file.name+" 文件类型不正确!!");
        },
        onFileSizeError: function (file) {
            alert(file.name+" 文件大小不正确!!");
        },
        onFallbackMode: function (message) {
            alert("浏览器不支持,文件上传请用chrome firfox,或者ie10,ie11"+ message);
        },
        onFileExtError:function(file){
            alert(file.name+" 文件类型不正确!!支持jpg,jpeg,gif,png");
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
				data:{orderId:orderId},
				type : "POST",
				success : function(data) {
					if (data.success) {
						$( "#submitCheckButton").html("正在进行付款审核");
						$(".demo-columns").hide();
						$('#submitCheckButton').unbind(); 
					} else {
						alert(data.errorMsg)
					}
				}
			});
		}
	});

})

function delFile(fileId){
    if(confirm("确定删除文件?")){
        jQuery.ajax({type:'POST',
                url:'/exhibitor/deleteFile/'+fileId,
                success:function(data,textStatus){

                },
                error:function(XMLHttpRequest,textStatus,errorThrown){},
                complete:function(XMLHttpRequest,textStatus){
                    document.getElementById('demo-file-upload-exhibitor-'+fileId).innerHTML=''}
            }
        );
        return false;
    }

}

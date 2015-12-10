var HTML_FORM_ID_KEY="storeHtmlFormId";
$(document).ready($(function() {
	if(store.get(HTML_FORM_ID_KEY) !=undefined){
		initFormBuilder(store.get(HTML_FORM_ID_KEY))
	}
	$("#createForm").on("submit", function() {
		$.ajax({
			url : "/api/v1/form",
			type : "POST",
			data : $("#createForm").serialize(),
			success : function(data) {
				if (data.success) {
					initFormBuilder(data.data.id)
					store.set(HTML_FORM_ID_KEY,data.data.id)
				} else {
					alert(data.errorMsg);
				}
			}
		});
		return false;
	});
}));

function initFormBuilder(htmlFormId) {
	if (htmlFormId != "") {
		var jsonContent;
		var bootstrapData = [];
		var data = {
			accessKey : $("#accessKey").val(),
			saasId : $("#saasId").val(),
			htmlFormId : htmlFormId
		}
		$.ajax({
			url : "/api/v1/form",
			data : data,
			type : "GET",
			async : false,
			error : function(request) {
				alert("登录超时或者服务器不可达！");
			},
			success : function(data) {
				if (data.success) {
					jsonContent = eval('(' + data.data.jsonContent + ')');
					console.log(jsonContent);
					if (null != jsonContent) {
						bootstrapData = jsonContent.fields;
					}
				}
			}
		})
		fb = new Formbuilder({
			selector : '.fb-main',
			bootstrapData : bootstrapData
		});

		fb.on('save', function(jsonContent) {
			console.log(jsonContent);
			updateForm(htmlFormId, jsonContent)
		})
		
		$("#createDiv").show();
		$("#answerFormBtn").on('click', function() {
			
		})
	}
}

function updateForm(htmlFormId, jsonContent) {
	$("#accessKeyUpdate").val($("#accessKey").val());
	$("#saasIdUpdate").val($("#saasId").val());
	$("#htmlFormIdUpdate").val(htmlFormId);
	$("#jsonContent").html(jsonContent);
	$.ajax({
		url : "/api/v1/form/updateByJsonContent",
		data : $("#formBuilderUpdate").serialize(),
		type : "POST",
		error : function(request) {
			alert("登录超时或者服务器不可达！");
		},
		success : function(data) {
			if (!data.success) {
				alert("当前保存数据出错！");
			}
		}
	});
}


var answerId = '';
function answerForm(htmlFormId){
	if(answerId===''){
		answerId = store.get(htmlFormId+"-answerId");
		console.log("answerId form store:"+answerId+":");
		if(answerId== undefined){
			answerId = UUID.generate()
			store.set(htmlFormId+"-answerId", answerId);
		}
		$("#answerId").val(answerId);
	}
	FormRenderer.BUTTON_CLASS = 'button button-primary btn btn-primary'
		// Initialize form
		var fr = new FormRenderer($.extend(
		  Fixtures.FormRendererOptions[$('#fixture').val()](),
		  {
		    screendoorBase: 'http://screendoor.dobt.dev',
		    onReady: function(){
		      console.log('Form is ready!');
		    }
		  }
		));
		fr.save = function(){
		  this.state.set({
		    hasChanges: false
		  });
		  console.log(this.getValue());
		};
}
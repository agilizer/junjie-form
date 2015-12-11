var HTML_FORM_ID_KEY = "storeHtmlFormId";
var DATA_TABLE ;
$(document).ready($(function() {
	if (store.get(HTML_FORM_ID_KEY) != undefined) {
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
					store.set(HTML_FORM_ID_KEY, data.data.id)
				} else {
					alert(data.errorMsg);
				}
			}
		});
		return false;
	});
	
	
	$("#answerListForm").on("submit", function() {
		$.ajax({
			url : "/api/v1/statistics/data",
			type : "POST",
			data : $("#answerListForm").serialize(),
			success : function(data) {
				if (data.success) {
					var dataSet = data.data.valueList;
					var titleList = data.data.titleList;
					var titleShowList = new Array();
					for(var i=0;i<titleList.length;i++){
						titleShowList.push({title:titleList[i]})
					}
					if(DATA_TABLE!=null){
						DATA_TABLE.destroy();
					}
					DATA_TABLE =  $('#dataTable').DataTable( {
					        data: dataSet,
					        dom: 'Bfrtip',
					        buttons: [
					           'csv'
					        ],
					        columns:titleShowList,
					        "language": {
					        	    "decimal":        "",
					        	    "emptyTable":     "没有数据展示",
					        	    "info":           "查看 _START_ to _END_ of _TOTAL_ 数据",
					        	    "infoEmpty":      "查看 0 到 0 共 0 数据",
					        	    "infoFiltered":   "(共  _MAX_  条)",
					        	    "infoPostFix":    "",
					        	    "thousands":      ",",
					        	    "lengthMenu":     "每页显示 _MENU_ 条",
					        	    "loadingRecords": "加载...",
					        	    "processing":     "处理...",
					        	    "search":         "查找:",
					        	    "zeroRecords":    "没有查找到数据",
					        	    "paginate": {
					        	        "first":      "首页",
					        	        "last":       "末页",
					        	        "next":       "下一页",
					        	        "previous":   "上一页"
					        	    },
					          }
					    } );
					console.log(dataTable)
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
		$("#showHtmlFormId").html(htmlFormId);
		$("#htmlFormIdListAnswer").val(htmlFormId);
		$("#answerListDiv").show();
		$("#accessKeyListAnswer").val($("#accessKey").val());
		$("#saasIdListAnswer").val($("#saasId").val());
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
			answerForm(htmlFormId);
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
function answerForm(htmlFormId) {
	answerId = UUID.generate()
	$("#answerId").val(answerId);
	FormRenderer.BUTTON_CLASS = 'button button-primary btn btn-primary'
	var jsonContent;
	var bootstrapData = [];
	$("#accessKeyAnswer").val($("#accessKey").val());
	$("#saasIdAnswer").val($("#saasId").val());
	$("#htmlFormIdAnswer").val(htmlFormId);
	// init data
	$.ajax({
		url :"/api/v1/answer-form",
		data : $("#answerForm").serialize(),
		type : "POST",
		async : false,
		error : function(request) {
			alert("服务器出错或登录超时！");
		},
		success : function(data) {
			if (data.success) {
				jsonContent = eval('(' + data.data.formRenderCache + ')');
				console.log(jsonContent);
				bootstrapData = jsonContent.fields;
				console.log(JSON.stringify(bootstrapData))
			}
		}
	})
	// Initialize form
	fr = new FormRenderer({
		project_id : 1,
		response_fields : bootstrapData,
		response : {
			id : 'xxx',
			responses : {}
		}
	});
	fr.save = function() {
		this.state.set({
			hasChanges : false
		});
		console.log(JSON.stringify(this.getValue()));
		var jsonAnswerValue = JSON.stringify(this.getValue())
		$("#jsonAnswer").val(jsonAnswerValue);
		$.ajax({
			url : "/api/v1/inputValue/answerForm",
			data : $("#answerForm").serialize(),
			type : "POST",
			async : false,
			success : function(data) {
				if (data.success) {
				}
				console.log(data);
			}
		})
		return true;
	};
	
	fr.state.on('change:submitting', function(model, val){
		alert("本次回答已经提交");
	}
	);
	
	fr.afterSubmit=function() {
		alert("本次回答已经提交");
	};
}

/*
Version: core-1.0
The MIT License: Copyright (c) 2012 LiosK.
*/
function UUID(){}UUID.generate=function(){var a=UUID._gri,b=UUID._ha;return b(a(32),8)+"-"+b(a(16),4)+"-"+b(16384|a(12),4)+"-"+b(32768|a(14),4)+"-"+b(a(48),12)};UUID._gri=function(a){return 0>a?NaN:30>=a?0|Math.random()*(1<<a):53>=a?(0|1073741824*Math.random())+1073741824*(0|Math.random()*(1<<a-30)):NaN};UUID._ha=function(a,b){for(var c=a.toString(16),d=b-c.length,e="0";0<d;d>>>=1,e+=e)d&1&&(c=e+c);return c};

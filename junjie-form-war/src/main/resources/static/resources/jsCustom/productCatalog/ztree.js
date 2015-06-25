var updateUrl = "/productCatalog/update";
var createUrl = "/productCatalog/create";
var createMutilUrl = "/productCatalog/createMutil";
var deleteUrl = "/productCatalog/delete";
var catalogListUrl="/productCatalog/list";

function showIconForTree(treeId, treeNode) {
	return !treeNode.isParent;
};

function beforeDrag(treeId, treeNodes) {
	return false;
}
function genNodeIds(treeNodes, treeNodeIds) {
	if (typeof (treeNodes) != "undefined") {
		for (var i = 0; i < treeNodes.length; i++) {
			treeNodeIds = treeNodeIds + "," + treeNodes[i].dbId
			treeNodeIds = genNodeIds(treeNodes[i].children, treeNodeIds);
		}
		return treeNodeIds;
	} else {
		return treeNodeIds;
	}
}
function genLastNode(targetNode) {
	var children = targetNode.children
	if (typeof (children) != "undefined") {
		return genLastNode(children[children.length - 1]);
	} else {
		return targetNode;
	}
}
function zTreeOnDrop(event, treeId, treeNodes, targetNode, moveType) {
	if (moveType == null) {
		return;
	}
	var treeNodeIds = "";
	treeNodeIds = genNodeIds(treeNodes, treeNodeIds)
	// alert(treeNodeIds)
	if (moveType != "inner") {
		var targetLastNode = genLastNode(targetNode)
		if (targetLastNode.id != targetNode.id) {
			// alert("targetLastNode:"+targetLastNode.id+"
			// targetNode:"+targetNode.id);
			targetNode = targetLastNode;
			moveType = "lastTargetNode"
		}
	}
	jQuery.ajax({
		type : 'POST',
		async : false,
		data : {
			targetNodeId : targetNode.dbId,
			treeNodeIds : treeNodeIds,
			moveType : moveType
		},
		url : dragUrl,
		success : function(data, textStatus) {
			if (data == false) {
				alert(data.msg);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	// alert(treeNodes.length + "," + (targetNode ? (targetNode.tId + ", " +
	// targetNode.name) : "isRoot" )+" moveType:"+moveType);
};
function beforeRemove(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("productCatalogTree");
	zTree.selectNode(treeNode);
	var removeResult = false;
	if (confirm("确认删除 节点 -- " + treeNode.name + " 吗？")) {
		jQuery.ajax({
			type : 'POST',
			async : false,
			url : deleteUrl + "/" + treeNode.dbId,
			success : function(data, textStatus) {
				if (!data.success) {
					alert(data.errorMsg);
				}
				removeResult = data.success;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}
	return removeResult;
}
function onRemove(e, treeId, treeNode) {
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("productCatalogTree");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	if (!isCancel) {
		if (treeNode.name == "") {
			alert("名称不能为空");
			return false;
		}
		jQuery.ajax({
			type : 'POST',
			url : updateUrl,
			data : {
				id : treeNode.dbId,
				name : treeNode.name
			},
			success : function(data, textStatus) {
				if (!data.success) {
					alert(data.errorMsg);
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}

}
function showRemoveBtn(treeId, treeNode) {
	return !treeNode.isParent;
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	// alert("#addBtn_"+treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("productCatalogTree");
			jQuery.ajax({
				type : 'POST',
				url : createUrl,
				async : false,
				data : {
					parentId : treeNode.dbId
				},
				success : function(data, textStatus) {
					if (!data.success) {
						alert(data.errorMsg);
					} else {
						treeNode = zTree.addNodes(treeNode, data.data);
						if (treeNode) {
							zTree.editName(treeNode[0]);
						}
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
			return false;
		});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("productCatalogTree");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}

function addDiyDom(treeId, treeNode) {
	/**
	 * // if(typeof(treeNode.parentNode) != "undefined"){ var aObj = $("#" +
	 * treeNode.tId + "_a"); var editStr = "<span class='"+treeNode.dbId+"'
	 * id='progressSpan"+treeNode.dbId+"'>"+treeNode.progress +"%</span>" // +'<input
	 * type="text" id="amount'+treeNode.tId+'" style="border:0; color:#f6931f;
	 * font-weight:bold;">'; aObj.append(editStr); var btn =
	 * $("#progressSpan"+treeNode.dbId); var sliderDiv='<div
	 * id="slider'+treeNode.tId+'"></div>'; if (btn) btn.bind("click",
	 * function(){ $showProgressModal.modal();
	 * $showProgressModal.load(showProgressUrl+"?id="+treeNode.dbId, '',
	 * function(){ $showProgressModal.modal(); });
	 * 
	 * });
	 */
}

$(document).ready(function() {
	var setting = {
		view : {
			addHoverDom : addHoverDom,
			addDiyDom : addDiyDom,
			removeHoverDom : removeHoverDom,
			selectedMulti : true,
			showIcon : true
		},
		edit : {
			enable : true,
			editNameSelectAll : true,
			showRemoveBtn : true,
			showRenameBtn : true,
			removeTitle : "删除类别",
			renameTitle : "编辑类别名称",

		},
		data : {
			simpleData : {
				enable : true,
				rootPId : null
			}
		},
		callback : {
			beforeDrag : true,
			beforeEditName : true,
			beforeRemove : beforeRemove,
			beforeRename : true,
			onRemove : onRemove,
			onRename : onRename,
			onDrop : zTreeOnDrop
		}
	};
	jQuery.ajax({
		type : 'POST',
		url : catalogListUrl,
		success : function(data, textStatus) {
			zNodes = data;
			$.fn.zTree.init($("#productCatalogTree"), setting, zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
	$("#createMutilForm").validate({
		ignore : "",
		submitHandler : function(form) {
			jQuery.ajax({
				type : 'POST',
				url : createMutilUrl,
				data : $("#createMutilForm").serialize(),
				success : function(data, textStatus) {
					alert("提交成功"+data.errorMsg);
					location.reload();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
				}
			});
		}
	});
});

var getCarBrandUrl="/carBrandAndSeries/listBrand";
var getCarSeriesUrl="/carBrandAndSeries/listSeries";
var getCarModelUrl="/carBrandAndSeries/listModel";
var getCarMaintainUrl="/carBrandAndSeries/listMaintain";
$(document).ready(function() {
	var searchBrand = $('#searchCarBrand');
	if(searchBrand.length>0){
		jQuery.ajax({
			type : 'POST',
			url : getCarBrandUrl,
			success : function(data, textStatus) {
				var temp;
				for (var i = 0; i < data.length; i++) {
					temp = data[i]
					jQuery("#searchCarBrand").append(
							"<option value='" + temp.brandId + "'>" + temp.brandName
									+ "</option>");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
			}
		});
	}	
});

function searchCarBrandChange(select){
	var selectJquery=$(select)
	var brandId = selectJquery.val();
	jQuery.ajax({
		type : 'POST',
		url : getCarSeriesUrl,
		data:{brandId:brandId},
		success : function(data, textStatus) {
			var temp;
			jQuery("#searchCarSeries").html("")
			jQuery("#searchCarSeries").append(
						"<option value='0'>请选择车系</option>");
			for (var i = 0; i < data.length; i++) {
				temp = data[i]
				jQuery("#searchCarSeries").append(
						"<option value='" + temp.id + "'>" + temp.carSeries
								+ "</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}


function searchCarSeriesChange(select){
	var selectJquery=$(select)
	var seriesId = selectJquery.val();
	jQuery.ajax({
		type : 'POST',
		url : getCarModelUrl,
		data:{seriesId:seriesId},
		success : function(data, textStatus) {
			var temp;
			jQuery("#searchCarModel").html("")
			jQuery("#searchCarModel").append(
						"<option value='0'>请选择车型</option>");
			for (var i = 0; i < data.length; i++) {
				temp = data[i]
				jQuery("#searchCarModel").append(
						"<option value='" + temp.id + "'>" + temp.years+' '+temp.transmission+" "+temp.exhaustVolume
								+ "</option>");
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}


function searchCarModelChange(select){
	var selectJquery=$(select)
	var modelId = selectJquery.val();
	var oldSearchHtml = $("#searchDiv").html();
	jQuery.ajax({
		type : 'POST',
		url : getCarMaintainUrl,
		data:{modelId:modelId},
		success : function(data, textStatus) {
			data = $(data).find("#mainContentDiv").html();
			$("#mainContentDiv").html(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
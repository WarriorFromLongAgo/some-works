//form序列化为json
$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

//获取url后的参数值
function getUrlParam(key) {
	var href = window.location.href;
	var url = href.split("?");
	if(url.length <= 1){
		return "";
	}
	var params = url[1].split("&");
	
	for(var i=0; i<params.length; i++){
		var param = params[i].split("=");
		if(key == param[0]){
			return param[1];
		}
	}
}

// 检查登录状态
function loginInfo(){
	var user = "";
    $.ajax({
        type : 'get',
        url : '/enforce/sys/login',
        async: false,
        success : function(data){
            if(data != null && data != ""){
                user = data;
            }
        },
        error: function(xhr,textStatus,errorThrown){
            var msg = xhr.responseText;
            var response = JSON.parse(msg);
            $("#info").html(response.message);
        }
    });
    
    return user;
}

//初始化字典选择列表
function getDictList(typeValue,selectId){
	$.ajax({
		type : 'get',
		url : '/base/dict/list/'+typeValue,
		contentType: "application/json; charset=utf-8", 
		async: false,
		success : function(data) {
			if ( data.code == 0 ) {
				var dicts= '';
				for( var i = 0 ; i < data.data.length ; i ++ ) {
					dicts += '<option value = "' + data.data[i].enumValue + '">' + data.data[i].enumDesc + '</option>';
				}
				$('#'+selectId).append(dicts);
			}	
		}
	});
}
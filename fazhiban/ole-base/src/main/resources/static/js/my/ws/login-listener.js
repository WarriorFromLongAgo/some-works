var websocket = null;
var pro = window.location.protocol;
var host = window.location.host;
var domain = pro + "//" + host;
/*
//判断当前浏览器是否支持WebSocket  
if ('WebSocket' in window) {
	websocket = new WebSocket("ws://" + host + "/ws/users/login");
} else {
	websocket = new SockJS("http://" + host + "/sockjs/ws/users/login");
}

//接收到消息的回调方法  
websocket.onmessage = function(event) {
    var d = event.data;
    var data = JSON.parse(d);
 	processData(data);
}
*/
//处理推送消息
function processData(data){
	var type = data.type;
	if(type  == "LOGIN_USER") {
		showLoginInfo(data.user);
	}
	
	if(type  == "NEW_NOTICE"){
		showNotices(data.articles);
	}
}

// 登录信息
function showLoginInfo(user){
	$(".admin-header-user span").text(user.nickname);
	var sex = user.sex;
	var url = user.headImgUrl;
	if(url == null || url == ""){
		if(sex == 1){
			url = "/img/avatars/sunny.png";
		} else {
			url = "/img/avatars/1.png";
		}
	} else {
		url = "/files" + url;
	}
	var img = $(".admin-header-user img");
	img.attr("src", domain + url);
}


function showNotices(articles){
	layui.use(['layer'], function(){
	    layer = layui.layer;
	    layer.open({
	        type: 1,
	        title: articles.title,
	        area: ['800px', '400px'],
	        shade: 0.7,
	        moveType: 0,
	        content: '<div style="padding: 50px;">' + articles.content + '</div>'
	        
	      });
	});
}


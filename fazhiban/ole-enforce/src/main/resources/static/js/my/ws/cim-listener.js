var websocket = null;
var contextPath = "/enforce";
var host = window.location.host + contextPath;
//判断当前浏览器是否支持WebSocket  
if ('WebSocket' in window) {
websocket = new WebSocket("ws://" + host + "/ws/cim/message");
} else {
	websocket = new SockJS("http://" + host + "/sockjs/ws/cim/message");
}
websocket.onopen = function(){
		$.ajax({
			type : 'get',
		url : '/enforce/users/current',
		data : $("#form").serialize(),
		success : function(data) {
			// 离线消息
			$.ajax({
				type : 'get',
				url : '/enforce/message/offlinePush?username=' + data.username,
					success : function(data) {
					}
				});
			}
		});
   }
//接收到消息的回调方法  
websocket.onmessage = function(event) {
    var d = event.data;
    var data = JSON.parse(d);
    tips(data.msg, data.caseNum, data.username);
}
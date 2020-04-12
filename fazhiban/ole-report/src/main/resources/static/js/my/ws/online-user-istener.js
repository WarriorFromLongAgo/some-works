var websocket = null;
var contextPath = "/report";
var host = window.location.host + contextPath;
//判断当前浏览器是否支持WebSocket  
if ('WebSocket' in window) {
	websocket = new WebSocket("ws://" + host + "/ws/users/online");
} else {
	websocket = new SockJS("http://" + host + "/sockjs/ws/users/online");
}

//接收到消息的回调方法  
websocket.onmessage = function(event) {
    var d = event.data;
//    console.log(d);
    reloadList();
}

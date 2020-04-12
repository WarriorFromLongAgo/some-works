initMenu();
function initMenu(){
	 $.ajax({  
	     url:"/report/ownpermissions/current",
	     type:"get",  
	     async:false,
	     success:function(data){
	    	 if(!$.isArray(data)){
	    		 location.href='/report/login.html';
	    		 return;
	    	 }
	    	 
	         var length = data.length;
	         var menu = $("#menuList");
	         
	         for(var i=0; i<length; i++){
	        	 var p = data[i];
	        	 var li = $("<li class='pid' ></li>");
	        	 li.attr("pid",p['id']);
	             var a = $("<a class='accordion-toggle collapsed' data-toggle='collapse' >");
	             
	             var img = $("<img src='' alt=''>");
	             var imgSrcStr = "images/index/main_icons"+(i+1)+".png";
	             img.attr("src",imgSrcStr);
	             var span = $("<span>"+p['name']+"</span>")
	             a.append(img);
	             a.append(span);
	             li.append(a);
	             
	             var child = p['child'];
	             if(child != null && child.length > 0){
	                 var c_ul = $("<ul id='nav"+(i+1)+"' class='collapse '></ul>");
	                 
	                 //写手机兼容
	                 /*var href = "#nav"+(i+1);
		             if(href != null && href != ""){
		                a.attr("href", href);
		             }*/
		             
	                 var childSize = 0;
	                 for(var t=0; t<child.length; t++){
	                     var c = child[t];
	                     var ctype = c['type'];
	                     if(ctype == 1){ // 排除掉按钮
	                         var c_li = $("<li></li>");
	                         
	                         var c_a = $("<a class='jump'  href='javascript:;'></a>");
	                         c_a.attr("cid",c['parentId']);
	                         c_a.attr("jumphref",c['href']);
	                         
	                         var css = c['css'];
	                         if(css!=null && css!=""){
	                        	 var c_i = $("<i aria-hidden='true' class='fa " + css +"'></i>");
	                        	 c_a.append(c_i);
	                         }
	                         
	                         c_a.append(c['name']);
	                         
	                         c_li.append(c_a);
	                         
                             childSize++;
                             if(childSize > 0){
                                 c_ul.append(c_li);
                             }
	                     }
	                  }
	                     
                    
                     li.append(c_ul);
                     
	               }
	               menu.before(li);
	             }
	         }
	  
	     });
	}

//获取url中的参数
function getUrlParam(name) {
  var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
  var r = window.location.search.substr(1).match(reg); //匹配目标参数
  if (r != null) return unescape(r[2]); return null; //返回参数值
}

var jumpurl = getUrlParam('jumpurl');
if(jumpurl!=null){
	$("iframe").attr("src",jumpurl);
}else{
	var url = $("li[pid]").find("a[class='jump']").attr("jumphref");
	$("iframe").attr("src",url);
}
var active_pid = getUrlParam('pid');
$(".pid").each(function(index,item){
	var pid =$(this).attr("pid");
	if(pid==active_pid){
		$(this).addClass("active");
	}else{
		$(this).removeClass("active");
	}
})
//初始设置默认页面（第一个选项卡的第一项）
$("li[pid]").first().addClass("active");
var url = $("li[pid]").find("a[class='jump']").attr("jumphref");
$("iframe").attr("src",url);

//panel页面的跳转
$(document).on("click", ".jump", function() {
	var jumpUrl = $(this).attr("jumphref");
	var activeId = $(this).attr("cid");
	$(".pid").each(function(index,item){
		var pid =$(this).attr("pid");
		if(pid==activeId){
			$(this).addClass("active");
		}else{
			$(this).removeClass("active");
		}
		
	})
	$("iframe").attr("src",jumpUrl);
    //alert($(this).attr("href"));
})
/*
var active;

layui.use(['layer', 'element'], function() {
	var $ = layui.jquery,
	layer = layui.layer;
	var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    element.on('nav(demo)', function(elem){
      //layer.msg(elem.text());
    });
	
	  //触发事件  
	   active = {  
	       tabAdd: function(obj){
	    	 var lay_id = $(this).attr("lay-id");
	    	 var title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
	         //新增一个Tab项  
	         element.tabAdd('admin-tab', {  
	           title: title,
	           content: '<iframe src="' + $(this).attr('data-url') + '"></iframe>',
	           id: lay_id
	         });  
	         element.tabChange("admin-tab", lay_id);    
	       }, 
	       tabDelete: function(lay_id){
    	      element.tabDelete("admin-tab", lay_id);
    	   },
	       tabChange: function(lay_id){
	         element.tabChange('admin-tab', lay_id);
	       }  
	   };  
	   //添加tab  
	   $(document).on('click','a',function(){  
	       if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
	    	   return;  
	       }
	       var tabs = $(".layui-tab-title").children();
	       var lay_id = $(this).attr("lay-id");

	       for(var i = 0; i < tabs.length; i++) {
	           if($(tabs).eq(i).attr("lay-id") == lay_id) { 
	        	   active.tabChange(lay_id);
	               return;  
	           }    
	       }  
	       active["tabAdd"].call(this);  
	       resize();  
	   });  
	     
	   //iframe自适应  
	   function resize(){  
	       var $content = $('.admin-nav-card .layui-tab-content');  
	       $content.height($(this).height() - 122);  
	       $content.find('iframe').each(function() {  
	           $(this).height($content.height());  
	       });  
	   }  
	   $(window).on('resize', function() {  
	       var $content = $('.admin-nav-card .layui-tab-content');  
	       $content.height($(this).height() - 122);  
	       $content.find('iframe').each(function() {  
	           $(this).height($content.height());  
	       });  
	   }).resize();  
	   
	   //toggle左侧菜单  
	   $('.admin-side-toggle').on('click', function() {
	       var sideWidth = $('#admin-side').width();  
	       if(sideWidth === 200) {  
	           $('#admin-body').animate({  
	               left: '0'  
	           });
	           $('#admin-footer').animate({  
	               left: '0'  
	           });  
	           $('#admin-side').animate({  
	               width: '0'  
	           });  
	       } else {  
	           $('#admin-body').animate({  
	               left: '200px'  
	           });  
	           $('#admin-footer').animate({  
	               left: '200px'  
	           });  
	           $('#admin-side').animate({  
	               width: '200px'  
	               });  
	           }  
	       });
});*/
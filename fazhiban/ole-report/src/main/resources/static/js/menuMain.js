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
	         var menu = $("#menuMain");
	         for(var i=0; i<length; i++){
	        	 	 var p = data[i];
		        	 var li = $("<li></li>");
		             var a = $("<a class='accordion-toggle collapsed' href='javascript:;'></a>");
		             var href = p['child'][0].href;
		             if(p['id'] != null && p['id'] != ""){
			             var pid = p['id'];
		             }
		             if(href != null && href != ""){
		                a.attr("href", "panel.html?jumpurl="+href+"&pid="+pid);
		             }
		             var img = $("<img src='' alt=''>");
		             var imgSrcStr = "images/index/main_icons"+(i+1)+".png";
		             img.attr("src",imgSrcStr);
		             var span = $("<span>"+p['name']+"</span>")
		             a.append(img);
		             a.append(span);
		             li.append(a);
		             
		             menu.append(li);
	             }
	         }
	  
	     });
	}

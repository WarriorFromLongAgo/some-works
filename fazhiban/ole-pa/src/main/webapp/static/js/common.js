/* 3E支部 - 登录系统 */

		/* 顶部下拉菜单 */ 
		$("#topNavTitle").click(function(){
			if( $(this).hasClass('open') ){
				$(this).removeClass('open');
				$("#navDownList").slideUp();
			}else{
				$(this).addClass('open');
				$("#navDownList").slideDown();
			}					 
		});
		
		/* 点击登录选项 显示对应的登录框*/
		function showLoginBox(clickElem, loginBoxElem){
			$(clickElem).click(function(){
				$("#login_left").addClass("move");
				$(".loginBox").hide();
				$(loginBoxElem).show();
				
				//收起顶部下拉菜单 
				$("#navDownList").hide(); 
				$("#topNavTitle").removeClass("open");
			});	
		}
		
		showLoginBox("#toELogin", "#loginBox-3E");	//登录 支部中心
		showLoginBox("#toHome", "#loginBox-home");	//登录 党员主页
		
		showLoginBox("#toZeren", "#loginBox-zeren");	//登录 两个责任
		showLoginBox("#toJiandu", "#loginBox-jiandu");	//登录 日常监督
		showLoginBox("#toJubao", "#loginBox-jubao");	//登录 监督举报
		showLoginBox("#toQingfeng", "#loginBox-qingfeng");	//登录 清风园地
		showLoginBox("#toGongche", "#loginBox-gongche");	//公车监管




		
		
		/* 纪检监察登录选项 - 点击 显示登录框 */
		$("#a").click(function(){
			$("#login_left").addClass("move");
			$("#loginBox-home").show();
			$("#loginBox-3E").hide();
			
			//收起顶部下拉菜单 
			$("#navDownList").hide(); 
			$("#topNavTitle").removeClass("open");
		});


/* 通用TAB菜单 */
$(".tabMenu li").click(function(){
	var index = $(this).index();
	$(this).addClass("current").siblings().removeClass("current");
	
	var tabContent = $(this).parent(".tabMenu").siblings(".tabContent");
	tabContent.eq(index).addClass("current").siblings().removeClass("current");
});

/* 复选框自定义样式 */
$(".checkbox").click(function(){
							  console.log(0);
	if( $(this).hasClass("selected") ){
		$(this).removeClass("selected");
	}else{
		$(this).addClass("selected");
	}
});
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>绩效考核系统</title>
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        
        <!-- #CSS Links -->
        <!-- Basic Styles -->
        <link rel="stylesheet" type="text/css" media="screen" href="${ctxStatic}/bootstrap/smartadmin-production.min.css">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/bootstrap.css"/>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/login.css"/>

        <!-- RTL Support -->

        <!-- iOS web-app metas : hides Safari UI Components and Changes Status Bar Appearance -->
        <meta name="apple-mobile-web-app-capable" content="yes">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        
    </head>
    
    <body class="animated fadeInDown">

        <div class="container-fluid"  style="padding-left: 0px;padding-right: 0px;">
			<div class="row">
				<div class="boxs col-md-12 flex-row flex-center">
					<div class="inside">
						<div class="logo">
							<img src="${ctxStatic}/img/login_logo.png"/>
						</div>
						<div class="inputs">
							 <form id="loginForm"  method="post"  action="${ctx}/login" >
								<div class="left">
										<div class="in-input">
											<input placeholder="用户名" type="text" name="username" id="username"  value="${username}"  />
											<input placeholder="密码"  type="password" name="password" id="password" />
										</div>
									<div class="in-pssword"  style="margin-top:15px;">
										 <span>记住密码</span> 
										 <input type="radio">
										 <span id="info" style="color: red;margin-left:53px;"></span>
									</div>
									<div class="button"  style="margin-top:10px;">
									   <button type="submit" class="btn btn-primary">登录</button>
									</div>
								</div>
							</form>
							<div class="right">
								<img src="${ctxStatic}/img/login_rimg.png"/>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
        <script src="${ctxStatic}/js/jquery-1.12.4.min.js"></script>
        <script src="${ctxStatic}/js/common.js"></script>
    </body>
</html>

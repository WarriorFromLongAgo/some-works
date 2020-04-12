<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>方案指标分配</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treeview.jsp"%>
<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}
</style>
</head>
<body>
	<sys:message content="${message}" />
	<div id="content" class="row-fluid">
		<div id="office" class="accordion-group span3 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle">部门选择<i
					class="icon-refresh pull-right" onclick="refreshOfficeTree();"></i></a>
			</div>
			<div id="officeTree" class="ztree scoller-down"></div>
		</div>
		<div id="user" class="accordion-group span2 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle">监督人选择</a>
			</div>
			<div id="userTree" class="ztree scoller-down"></div>
		</div>
		<div id="unselected" class="accordion-group span3 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle">未分配指标</a>
			</div>
			<div id="unselectedTree" class="ztree scoller-down"></div>
		</div>
		<div id="btnSpan" class="span1 height-full btn-left-right">
			<a href="#" class="btn btn-primary btn-left-right" onclick="return confirmx('确认要将指标分配至审核人吗？', addItem)">
				<i class="icon-chevron-right"></i>
			</a>
			<a href="#" class="btn btn-primary btn-left-right" onclick="return confirmx('确认要将指标从审核人中移除吗？', removeItem)">
				<i class="icon-chevron-left"></i>
			</a>
		</div>
		<div id="selected" class="accordion-group span3 height-full">
			<div class="accordion-heading">
				<a class="accordion-toggle">已分配指标</a>
			</div>
			<div id="selectedTree" class="ztree scoller-down"></div>
		</div>
	</div>
	<script type="text/javascript">
		var selectedUserId = "";
		var selectedOfficeId = "";
		var settingOfficeTree = {
				data:{
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:'0'
						}
		},
				callback:{
					onClick:function(event, treeId, treeNode){
							var id = treeNode.id == '0' ? '' :treeNode.id;
							selectedUserId = "";
							selectedOfficeId = id;
							refreshUserTree(id);
							refreshUnselectedTree();
							refreshSelectedTree();
						}
				}
		};
		var settingUserTree = {
				data:{
					simpleData:{
					enable:true,
					idKey:"id",
					pIdKey:"pId",
					rootPId:'0'}
		},
			callback:{onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					selectedUserId = id;
					refreshUnselectedTree();
					refreshSelectedTree(id);
				}
			}
		};
		var settingUnselectedTree = {
				data:{
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						schemeItemId:'schemeItemId',
						rootPId:'0'}
		},
			check:{
				enable: true,
				nocheckInherit: false,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "" }
		}, 
			callback:{
				onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
					
				}
			}
		};
		var settingSelectedTree = {
				data:{
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						schemeItemId:'schemeItemId',
						rootPId:'0'}
		},
			check:{
				enable: true,
				nocheckInherit: false,
				chkStyle: "checkbox",
				chkboxType: { "Y": "s", "N": "" }
		}, 
			callback:{
				onClick:function(event, treeId, treeNode){
					var id = treeNode.id == '0' ? '' :treeNode.id;
				
				}
			}
		};
	
		//zTree初始化： 
		function refreshOfficeTree(){
			$.getJSON("${ctx}/sys/office/treeData?type=3",
					function(data){
				$.fn.zTree.init($("#officeTree"), settingOfficeTree, data).expandAll(true);
			});
		}
		refreshOfficeTree();
		function refreshUserTree(id){
			$.getJSON("${ctx}/sys/user/treeDataAssign?officeId="+id,
					function(data){
				$.fn.zTree.init($("#userTree"), settingUserTree, data).expandAll(true)	
			});
		}
		function refreshUnselectedTree(){
			$.getJSON("${ctx}/opa/opaSchemeItem/TreeDataForAssign?schemeId=${schemeId}&status=${fns:getDictValue('待分配', 'opa_schemeItem_status', '')}",
					function(data){
				console.log(data);
				$.fn.zTree.init($("#unselectedTree"), settingUnselectedTree, data).expandAll(true);
			});
		}
		refreshUnselectedTree();
		function refreshSelectedTree(auditorId){
			$.getJSON("${ctx}/opa/opaSchemeItem/TreeDataForAssign?schemeId=${schemeId}&auditorId="+auditorId+"&status=${fns:getDictValue('已分配', 'opa_schemeItem_status', '')}",
					function(data){
				$.fn.zTree.init($("#selectedTree"), settingSelectedTree, data).expandAll(true);
			});
		} 
		
		function removeItem(){
			if(selectedUserId == ""){
				showTip("请先选择负责人再进行指标分配！");
				return;
			}
			loading();
			var treeObj = $.fn.zTree.getZTreeObj("selectedTree");
			var checkedNodes = treeObj.getCheckedNodes(true);
			var selectedNodes = new Array();
			for(var i=0; i<checkedNodes.length; i++){
				var id = checkedNodes[i].schemeItemId;
				selectedNodes = (selectedNodes + id) + (((i + 1)== checkedNodes.length) ? '':',');
			}
			$.ajax({ 
			    type: "post", 
			    url: "${ctx}/opa/opaSchemeItem/assign/remove?ids="+selectedNodes, 
			    cache:false, 
			    async:false, 
			    dataType:'text', 
			    success: function(data){ 
			    	if(data=="ok"){
			    		showTip("指标移除成功！");
			    	}
			    	refreshUnselectedTree();
					refreshSelectedTree(selectedUserId);
			    	closeLoading();
			    } 
			});
		}
		function addItem(){
			if(selectedUserId == ""){
				showTip("请先选择负责人再进行指标分配！");
				return;
			}
			loading();
			var treeObj = $.fn.zTree.getZTreeObj("unselectedTree");
			var checkedNodes = treeObj.getCheckedNodes(true);
			var selectedNodes = new Array();
			for(var i=0; i<checkedNodes.length; i++){
				var id = checkedNodes[i].schemeItemId;
				selectedNodes = (selectedNodes + id) + (((i + 1)== checkedNodes.length) ? '':',');
			}
			$.ajax({ 
			    type: "post", 
			    url: "${ctx}/opa/opaSchemeItem/assign/add?ids="+selectedNodes+"&auditorId="+selectedUserId+"&officeId="+selectedOfficeId, 
			    cache:false, 
			    async:false, 
			    dataType:'text', 
			    success: function(data){ 
			    	if(data=="ok"){
			    		showTip("指标分配成功！");
			    	}
			    	refreshUnselectedTree();
					refreshSelectedTree(selectedUserId);
			    	closeLoading();
			    } 
			});
		}
	</script>
</body>
</html>
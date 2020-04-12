<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>考核打分</title>
<meta name="decorator" content="default" />
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId+data[i].office.id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+data[i].office.id+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].itemParentId+data[i].office.id+',') == -1){
						rootIds.push(data[i].itemParentId+data[i].office.id);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			
			$("#treeTable").treeTable({expandLevel : 10}); 
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.itemParentId')})+(${fns:jsGetVal('row.office.id')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict:{
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_planTask_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid), 
							row: row,
							btnCol: btnRender(row),
						
					}));
					addRow(list, tpl, data, row.itemId+row.office.id);
				}
			}
		}
		function auditPass(a){
			return confirmx("确认通过该任务吗",a);
		}
		function auditReturn(a){
			return confirmx("确认不通过该任务吗",a);
		}
		function inputScore(value, thisValue, a){
			if(thisValue==null||thisValue=="undefined"){
				thisValue=0;
			}
			inputPromptx("请输入考评分值", "分值", value, thisValue, a, true);
			return false;
		}
		//提示输入对话框
		function inputPromptx(title, lable, value, thisValue, href, closed){
			top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable
					+ "：<input type='text' id='txt' name='txt'/> /"+value+"分&nbsp;<span class='help-inline'>自评:"+thisValue+"分</span></div>"+
					"<div class='form-search' style='padding:20px;text-align:center;'>打分：<div><textarea type='textarea' htmlEscape='false' rows='5' cols='20' maxlength='50' id='reason' name='reason'/></div></div>", {
					title: title, submit: function (v, h, f){
			    if (f.txt == '') {
			        top.$.jBox.tip("请输入" + lable + "。", "error");
			        return false;
			    }
			    if (f.txt*1 >value*1) {
			        top.$.jBox.tip('填写分值不能大于标准分值');
			        return false;
			    }
				if (typeof href == 'function') {
					href();
				}else{
					resetTip(); //loading();
					location = href + encodeURIComponent(f.txt) + "&reason=" + encodeURIComponent(f.reason);
				}
			},closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			resetTip();
			return false;
		}
		function btnRender(row){
			var str="";
			if((row.status == "${fns:getDictValue('待考核', 'opa_planTask_status', '')}")){
					//上报
					//str+="<shiro:hasPermission name='pa:opaPlanTask:excute:audit:edit'><a href='${ctx}/opa/opaPlanTask/excute/inputScore/audit?id="+row.id 
					//	+"&planId="+row.planId+"' onclick='return inputPromptx(this.href)'>通过</a>&nbsp;</shiro:hasPermission>";
					str+="<shiro:hasPermission name='pa:opaPlanTask:excute:audit:edit'><a href='${ctx}/opa/opaPlanTask/excute/audit/inputScore?id="+row.id 
						+"&planId="+row.planId+"&inputScore=' onclick='return inputScore(\""+row.value+"\",\""+row.score+"\",this.href)'>打分</a>&nbsp;</shiro:hasPermission>";
					
			}
			
			if((row.attachedPath != null && row.attachedPath != undefined && row.attachedPath != '')){ 
					str+="<shiro:hasPermission name='pa:opaPlanTask:excute:audit:edit'><a href='${ctx}/opa/opaPlanTask/download?id="+row.id 
						+"&planId="+row.planId+"' >下载</a>&nbsp;</shiro:hasPermission>";
			}
			return str;
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/opa/opaPlanTask/excute/audit/index">考核打分</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaPlanTask"
		action="${ctx}/opa/opaPlanTask/excute/audit/index" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>计划名称：</label> <form:select path="planId"
					class="input-xlarge">
					<form:options items="${planList}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li><label>被考核部门</label> <sys:treeselect id="office"
					name="office.id" value="${opaPlanTask.office.id}"
					labelName="office.name" labelValue="${opaPlanTask.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="input-small"
					allowClear="true" notAllowSelectParent="true" /></li>
			<li><label>名称：</label> <form:input path="name"
					htmlEscape="false" maxlength="200" class="input-medium" /></li>
			<li><label>指标类型：</label> <form:select path="type"
					class="input-mini">
					<form:option value="" label="" />
					<form:options items="${fns:getDictBlankList('opa_item_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<%-- <li><label>数值类型：</label> <form:select path="ifNum"
					class="input-mini">
					<form:option value="" label="" />
					<form:options items="${fns:getDictBlankList('opa_item_Num_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li> --%>
			<%-- <li><label>审核者：</label>
				<sys:treeselect id="auditorId" name="auditorId" value="${opaPlanTask.auditorId}" labelName="auditorName" labelValue="${opaPlanTask.auditorName}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li> --%>
			<li><label>状态：</label> <form:select path="status"
					class="input-mini">
					<form:option value="" label="" />
					<form:options
						items="${fns:getDictBlankList('opa_planTask_status')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="treeTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>指标类型</th>
				<th>考核方式</th>
				<!-- <th>数值类型</th> -->
				<th>考评标准</th>
				<th>得分</th>
				<!-- <th>数值</th>
				<th>完成</th> -->
				<th>考核对象</th>
				<th>考核部门</th>
				<th>考核人员</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}{{row.office.id}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaPlanTask/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{dict.type}}
				</td>
				<td>
					{{dict.method}}
				</td>
				<!--<td>
					{{dict.ifNum}}
				</td>-->
				<td>
					{{row.value}}
				</td>
				<td>
					{{row.score}}
				</td>
				<!--<td>
					{{row.count}}
				</td>
				<td>
					{{row.result}}
				</td>-->
				<td>
					{{row.objectType}}
				</td>
				<td>
					{{row.office.name}}
				</td>
				<td>
					{{row.peopleId}}
				</td>
				<td>
					{{dict.status}}
				</td>
				<td>
					{{{btnCol}}}
				</td>	
		</tr>
		
	</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>方案指标填报</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].itemId);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].itemParentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].itemParentId+',') == -1){
						rootIds.push(data[i].itemParentId);
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
				if ((${fns:jsGetVal('row.itemParentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict:{
							type: getDictLabel(${fns:toJson(fns:getDictList('opa_item_type'))}, row.type),
							ifNum: getDictLabel(${fns:toJson(fns:getDictList('opa_item_Num_type'))}, row.ifNum),
							method: getDictLabel(${fns:toJson(fns:getDictList('opa_item_method'))}, row.method),
							status: getDictLabel(${fns:toJson(fns:getDictList('opa_schemeItem_status'))}, row.status), blank123:0}, 
							pid: (root?0:pid), 
							row: row,
							btnCol: btnRender(row),
					}));
					addRow(list, tpl, data, row.itemId);
				}
			}
		}
		function btnRender(row){
			var str="";
			if(row.status == "${fns:getDictValue('已填报', 'opa_schemeItem_status', '')}"){
					str+="<shiro:hasPermission name='pa:opaSchemeItem:apply:apply'><a href='${ctx}/opa/opaSchemeItem/apply/apply?id="+row.id 
					+"&schemeId="+row.schemeId+"' onclick='return apply(this.href)'>提交</a>&nbsp;</shiro:hasPermission>";
			}
			if(row.status == "${fns:getDictValue('填报已退回', 'opa_schemeItem_status', '')}"){
				str+="<shiro:hasPermission name='pa:opaSchemeItem:apply:view'><a href='${ctx}/opa/opaSchemeItem/apply/view?id="+row.id 
				+"&schemeId="+row.schemeId+"' onclick='return applyView(this.href)'>修改</a>&nbsp;</shiro:hasPermission>";
		}
			return str;
		}
		function apply(a){
			return confirmx("确认要提交该指标吗",a);
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/opa/opaSchemeItem/apply/index">方案指标填报</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="opaSchemeItem" action="${ctx}/opa/opaSchemeItem/apply/index" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>方案名称：</label>
				<form:select path="schemeId" class="input-xlarge">
					<form:options items="${schemeAuditedList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>指标名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_schemeItem_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>指标类型：</label>
				<form:select path="type" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			
			<li><label>考核方式：</label>
				<form:select path="method" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_method')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>数值类型：</label>
				<form:select path="ifNum" class="input-mini">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictBlankList('opa_item_Num_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li><label>起始日期：</label>
				<input name="dateFrom" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li><label>截止日期：</label>
				<input name="dateTo" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${opaSchemeItem.dateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="btns"><input id="btnSetItemValue" class="btn btn-primary" type="button" value="指标批量填报"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<script>
		//打开指标分配窗口
		$("#btnSetItemValue").click(function(){
			top.$.jBox.open("iframe:${ctx}/opa/opaSchemeItem/apply/batch/index?schemeId="+$("#schemeId").find("option:selected").val(), "指标批量填报",800,$(top.document).height()-100,{
				buttons:{"关闭":true}, closed:function(){location.reload();},bottomText:"点选左侧指标后，即可即可进行指标填报。",submit:function(v, h, f){
				}, loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				}
			});
		});
	</script>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>内容描述</th>
				<th>指标类型</th>
				<th>考核方式</th>
				<!-- <th>数值类型</th> -->
				<th>考评标准</th>
				<!-- <th>数值标准</th> -->
				<!-- <th>审核者</th> -->
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.itemId}}" pId="{{pid}}">
				<td><a href="${ctx}/opa/opaSchemeItem/apply/view?id={{row.id}}">
					{{row.name}}
				</a></td>
				<td>
					{{row.content}}
				</td>
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
				<!--<td>
					{{row.count}}
				</td>
				 <td>
					{{row.auditorName}}
				</td>-->
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
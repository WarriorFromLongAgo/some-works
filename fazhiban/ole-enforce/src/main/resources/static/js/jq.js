$.ajaxSetup({
	cache : false,
	error : function(xhr, textStatus, errorThrown) {
		var msg = xhr.responseText;
		var response = JSON.parse(msg);
		var code = response.code;
		var message = response.msg;
		if (code == 400) {
			layer.msg(message);
		} else if (code == 401) {
			layer.msg('未登录');
		} else if (code == 403) {
			layer.msg('未授权');
		} else if (code == 500) {
			if(message=="null"||message==''){
				layer.msg('对不起，操作失败或您无权进行操作！' );
			}else{
				layer.msg('对不起，系统错误：' +message)	;
			}
		}else{
			layer.msg('操作失败！');
		}
	}
});

function buttonDel(data, permission, pers) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-danger' title='删除' onclick='del(\""
			+ data + "\")'><i class='layui-icon'>&#xe640;</i></button>");
	return btn.prop("outerHTML");
}

function buttonEdit(href, permission, pers) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini' title='编辑' onclick='window.location=\""
			+ href + "\"'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}

function btnUpdateCase(href, permission, pers, functionName, rowId, userKeyId,
		caseStatus) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}
	if (rowId != userKeyId || !(caseStatus in {
		"案件暂存" : "",
		"检查暂存" : ""
	})) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-normal' title='编辑' onclick='"
			+ functionName
			+ "(\""
			+ href
			+ "\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}
/**
 * 日常检查暂存时编辑
 * 
 * @param href
 * @param permission
 * @param pers
 * @param functionName
 * @param rowId
 * @param userKeyId
 * @param status
 * @returns
 */
function btnUpdateCheckDaily(href, permission, pers, functionName, rowId,
		userKeyId, status) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}
	if (rowId != userKeyId || status != "暂存") {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-normal' title='编辑' onclick='"
			+ functionName
			+ "(\""
			+ href
			+ "\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}
function btnUpdateCheck(href, permission, pers, functionName, createBy, userN,
		caseStatus) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}
	if (createBy != userN || !(caseStatus in {
		"案件暂存" : "",
		"检查暂存" : ""
	})) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-normal' title='编辑' onclick='"
			+ functionName
			+ "(\""
			+ href
			+ "\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}

function btnUpdateModal(href, permission, pers, functionName) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-normal' title='编辑' onclick='"
			+ functionName
			+ "(\""
			+ href
			+ "\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}

/**
 * 返回附件列表删除按钮 permission 权限 pers 权限列表 id 要删除记录的ID值
 */
function btnattachFileDel(permission, pers, id) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-danger' title='删除' onclick='deleteFile(\""
			+ id + "\")'><i class='layui-icon'>&#xe640;</i></button>");
	return btn.prop("outerHTML");
}

/**
 * 返回附件列表预览按钮 permission 权限 pers 权限列表 id 要预览记录的ID值
 */
function btnattachFileShow(permission, pers, id, fileType) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var type = [ 'txt', 'doc', 'docx', 'xls', 'xlsx', 'jpg', 'jpeg', 'png',
			'bmp', 'gif', 'mp4', 'mp3','pdf' ];
	if (!(type.toString().indexOf(fileType.toLowerCase()) > -1)) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini layui-btn-normal' title='预览' onclick='showFile(\""
			+ id
			+ "\",\""
			+ fileType.toLowerCase()
			+ "\")'><i class='layui-icon'>&#xe615;</i></button>");
	return btn.prop("outerHTML");
}

/**
 * 返回附件列表下载按钮 permission 权限 pers 权限列表 id 要下载记录的ID值
 */
function btnattachFileDownload(permission, pers, id) {
	if ($.inArray(permission, pers) < 0) {
		return "";
	}

	var btn = $("<button class='layui-btn layui-btn-mini' title='下载' onclick='downloadFile(\""
			+ id + "\")'><i class='layui-icon'>&#xe601;</i></button>");
	return btn.prop("outerHTML");
}

/**
 * 返回查看流程图按钮
 * 
 * @param taskId
 *            步骤ID
 * @returns
 */
function btnCaseTask(caseNum,status) {
	var btn = $("<button class='layui-btn layui-btn-mini' title='查看流程图' onclick='viewFlowImage(\""
			+ caseNum + "\",\""
			+ status + "\")'><i class='layui-icon'>&#xe615;</i></button>");
	return btn.prop("outerHTML");
}

function deleteCurrentTab() {
	var lay_id = $(parent.document).find("ul.layui-tab-title").children(
			"li.layui-this").attr("lay-id");
	parent.active.tabDelete(lay_id);
}

// datatables分页控件初始化 提示信息
var lang = {
	"sProcessing" : "正在加载数据...",
	"sZeroRecords" : "没有匹配结果",
	"sInfo" : "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
	"sInfoEmpty" : "当前显示第 0 至 0 项，共 0 项",
	"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
	"sEmptyTable" : "表中数据为空",
	"sLoadingRecords" : "载入中...",
	"oPaginate" : {
		"sFirst" : "首页",
		"sPrevious" : "上页",
		"sNext" : "下页",
		"sLast" : "末页",
		"sJump" : "跳转"
	},
	"oAria" : {
		"sSortAscending" : ": 以升序排列此列",
		"sSortDescending" : ": 以降序排列此列"
	}
};

// 模版代码：模版ID
var templateCode = null;
$.ajax({
	type : 'get',
	url : '/enforce/docTemplate/getTemplateCode',
	success : function(data) {
		templateCode = data;
	}
});

// 各个流程节点增加附件管理功能
function addFileManageBtn(flowNode) {
	if ($("#docflow")) {
		var template = "<button class=\"layui-btn layui-btn-small\" onclick=\"attachFileManage("
				+ flowNode + ")\">";
		template += "<i class=\"layui-icon\">&#xe608;</i>附件管理</button>";
		$("#docflow").prepend(template);
	}
}

// 打开附件管理页面
function attachFileManage(flowNode) {
	if ($(".checkchild:checked").length == 0) {
		layer.msg("请先勾选需要录入的记录.", {
			anim : 6
		});
		return;
	}
	var caseId = $(".checkchild:checked").val();
	if (caseId) {
		var caseNum = caseId.split(",")[0];
		var caseId = caseId.split(",")[1];
		parent.layer.open({
			type : 2,
			title : '附件管理',
			shade : [ 0 ],
			area : [ '80%', '80%' ],
			shift : 2,
			content : 'pages/attachFile/fileList.html?caseStatus=' + flowNode
					+ '&caseId=' + caseId + "&caseNum=" + caseNum
		});
	}
}

// 动态获取文书
function getDocFlow(flowNode, flowType) {
	$.ajax({
		type : 'get',
		url : '/enforce/docFlow/docFlows?flowNode=' + flowNode
				+ '&flowType=' + flowType,
		dataType : 'json',
		success : function(docflows) {
			$("#moreTemplate").attr("doclength", docflows.data.length)
			if (docflows.data.length < 2) {
				var template = "";
				for (var i = 0; i < docflows.data.length; i++) {
					template += "<button class=\"layui-btn layui-btn-small\" onclick=\"addDocContent('"
							+ docflows.data[i].id
							+ "','"
							+ docflows.data[i].name + "')\">";
					template += "<input class=\"templateId\" type=\"hidden\" value=\""
							+ docflows.data[i].id + "\"/>";
					template += "<input class=\"needAdd\" type=\"hidden\" value=\""
							+ docflows.data[i].needAdd + "\"/>";
					template += "<i class=\"layui-icon \">&#xe608;</i>"
							+ docflows.data[i].name;
					template += "</button>";
				}
				$("#docflow").prepend(template);
			} else {
				var template = "<button class=\"layui-btn layui-btn-small\" onclick=\"docManage()\">";
				template += "<i class=\"layui-icon\">&#xe608;</i>文书管理";
				template += "</button>";
				$("#docflow").prepend(template);
				var temp = "<div id=\"jzrydetailid\" class=\"panel\"  style=\"z-index:3;position: absolute; top: 0px; right: 0px; bottom: 5px; width: 0px; background: #ffffff; display: none;\">";
				temp += "<div class=\"container\" style=\"width: 265px;\">";
				temp += "<div class=\"row clearfix\">";
				temp += "<div class=\"col-md-12 column\" id=\"doctemplate\">";
				temp += "<div class='list-group-item' style=\"height:40px;padding-left:10px;background-color:#C6C6C6;cursor:pointer;\" onclick=\"docListHide()\">";
				temp += "<button class=\"layui-btn layui-btn-small\"  style=\"margin-top:-5px;background-color:#C6C6C6;\">";
				temp += "<i class=\"layui-icon\">&#xe65b;文书列表</i>";
				temp += "</button>";
				temp += "</div>";
				for (var i = 0; i < docflows.data.length; i++) {
					var buttonName=docflows.data[i].name;
					var allName=docflows.data[i].name;
				    if(allName.length>15){
				    	buttonName=buttonName.substr(0,15);
					}
					temp += "<div class='list-group-item' id=\"moredoc\">";
					 if(allName.length>15){
						temp += "<button  title=\""+allName+"\" class=\"layui-btn layui-btn-small\" onclick=\"addDocContent('"
								+ docflows.data[i].id
								+ "','"
								+ docflows.data[i].name + "')\">";
					 }else{
						 temp += "<button title=\""+allName+"\" class=\"layui-btn layui-btn-small\" onclick=\"addDocContent('"
							+ docflows.data[i].id
							+ "','"
							+ docflows.data[i].name + "')\">";
					 }
					temp += "<input class=\"templateId\" type=\"hidden\" value=\""
							+ docflows.data[i].id + "\"/>";
					temp += "<input class=\"needAdd\" type=\"hidden\" value=\""
							+ docflows.data[i].needAdd + "\"/>";
					temp += "<i class=\"layui-icon \">&#xe608;</i>"
							+ buttonName;
					temp += "</button>";
					temp += "</div>";
				}
				temp += "</div>";
				temp += "</div>";
				temp += "</div>";
				temp += "</div>";
				$("#moreTemplate").append(temp);
			}
		}
	});
}

// 文书右边框隐藏
function docListHide() {
	$('#jzrydetailid').hide(1000);
}
// 文书右边框滑出
function docManage() {
	$('#jzrydetailid').show().animate({
		width : 270
	});
}

// 打开添加文书页面
function addDocContent(templateId, templateName) {
	if ($(".checkchild:checked").length == 0) {
		layer.msg("请先勾选需要录入的记录.", {
			anim : 6
		});
		return;
	}
	if ($(".checkchild:checked").length > 1) {
		var temp = "一次只能录入一条" + templateName + ".";
		layer.msg(temp, {
			anim : 6
		});
		return;
	}
	var caseId = $(".checkchild:checked").val();
	var caseId = caseId.split(",")[1];
	// var templateId = '849b8d5d-a112-42b6-8e6a-cdde385c3c05';
	layer.open({
		type : 2,
		title : templateName,
		shade : [ 0 ],
		area : [ '88%', '95%' ],
		shift : 2,
		content : '/enforce/pages/docContent/addDocContent.html?templateId='
				+ templateId + '&caseId=' + caseId
	});
}

// 文书是否必填判断
function docNeedAdd() {
	var caseId = $(".checkchild:checked").val();
	caseId = caseId.split(",")[1];
	var doclength = $("#moreTemplate").attr("doclength");
	var needadd = "";
	if (doclength < 2) {

		$("#docflow button").each(
				function(i) {
					if (needadd == -1) {
						return;
					}
					var templateId = $(this).find(".templateId").val();
					var docname = $(this).text().substring(1,
							$(this).text().length);
					if ($(this).find(".needAdd").val() == 1) {
						$.ajax({
							type : 'get',
							url : '/enforce/docFlow/needAdd?templateId='
									+ templateId + '&caseId=' + caseId,
							dataType : 'json',
							async : false,
							success : function(data) {
								if (data.code == -1) {
									needadd = data.code;
									layer.msg(docname + "必须填写.", {
										anim : 6
									});
									return;
								}
							}
						})
					}
				})
		if (needadd == -1) {
			return -1;
		}
	} else {

		$("#doctemplate #moredoc").each(
				function(i) {
					if (needadd == -1) {
						return;
					}
					var templateId = $(this).children().find(".templateId")
							.val();
					var docname = $(this).children().attr("title");
					if ($(this).find(".needAdd").val() == 1) {
						$.ajax({
							type : 'get',
							url : '/enforce/docFlow/needAdd?templateId='
									+ templateId + '&caseId=' + caseId,
									
							dataType : 'json',
							async : false,
							success : function(data) {
								if (data.code == -1) {
									needadd = data.code;
									layer.msg(docname + "必须填写.", {
										anim : 6
									});
									return;
								}
							}
						})
					}
				})
		if (needadd == -1) {
			return -1;
		}
	}
}

// 获取url中的参数
function getQuery(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}

/**
 * 数据有效性校验
 * 如果元素被隐藏则不校验
 */
function dataCheck() {
	// 假设数据校验成功
	var checkFlag = true;
	
	
	// 数据为空校验
	$(".not-null").each(function() {
		if(!$(this).is(':hidden')) {
			if (this.value == null || this.value == "") {
				layer.msg($(this).attr("iname") + "不能为空", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});

	if (!toNext()) {
		return checkFlag;
	}

	// 身份证号码简单校验
	$(".id-num").each(function() {
		if(!$(this).is(':hidden') && $(this).val()!='') {
			if (!/^([0-9]){17}([0-9|x|X]){1}$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "格式错误", {anim:6});
				
				checkFlag = false;
				return checkFlag;
			}
		}
	});

	if (!toNext()) {
		return checkFlag;
	}

	// 邮政编码简单校验
	$(".zip-code").each(function() {
		if(!$(this).is(':hidden') && $(this).val()!='') {
			if (!/^([0-9]){6}$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "格式错误<br>邮政编码必须为6位纯数字组成", {anim:6});
				
				checkFlag = false;
				return checkFlag;
			}
		}
	});

	if (!toNext()) {
		return checkFlag;
	}

	// 电话号码简单校验
	$(".tel").each(function() {
		if(!$(this).is(':hidden') && $(this).val()!='') {
			if (!/(^\d{3,4}\-\d{7,8}$)|(^\d{7,8}\-\d{1,4}$)|(^\d{3,4}\-\d{7,8}\-\d{1,4}$)|(^\d{7,8}$)|(^1[345789]\d{9}$)/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "格式错误：<br>电话号码必须为11位手机号或者固话号码<br>固话号码格式为：[区号]<固话号>[分机号]<br>如：13104710471<br>或：0471-8888888-1234、0471-8888888、8888888-1234、8888888", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});

	if (!toNext()) {
		return checkFlag;
	}
	
	// 全中文字段校验
	$(".cn").each(function() {
		if(!$(this).is(':hidden') && $(this).val()!='') {
			if (!/^[\u4e00-\u9fa5]+$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "必须全为中文", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	if (!toNext()) {
		return checkFlag;
	}
	// 纯数字字段校验
	$(".num").each(function() {
		if(!$(this).is(':hidden') && $(this).val()!='') {
			if (!/^[0-9]*$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "包含非法字符", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	
	
	function toNext() {
		return checkFlag;
	}
	
	return checkFlag;
}

//验证当前登录人是否是主执法人员
function judgePerson(){
	var checkId = $(".checkchild:checked").val();
	var caseNum=checkId.split(",")[0];
	var caseId=checkId.split(",")[1];
	var code='';
	$("#docflow button").each(
	function(i) {
		$.ajax({
			type : 'get',
			url : '/enforce/case/judgeZperson?&caseId=' 
					+ caseId+'&caseNum='+caseNum,
			dataType : 'json',
			async : false,
			success : function(data) {
				code=data.code;
				if (data.code==-1) {
					layer.msg("该案件协办人无权提交.", {anim:6});
				}
				return;           
				} 
			}
		)
	})
	if (code == -1) {
		return -1;
	}
    
}

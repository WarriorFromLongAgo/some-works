
$.ajaxSetup({
	cache : false,
	error : function(xhr, textStatus, errorThrown) {
		var msg = xhr.responseText;
		var response = JSON.parse(msg);
		var code = response.code;
		var message = response.message;
		if (code == 400) {
			layer.msg(message);
		} else if (code == 401) {
			layer.msg('未登录');
		} else if (code == 403) {
			console.log("未授权:" + message);
			layer.msg('未授权');
		} else if (code == 500) {
			layer.msg('系统错误：' + message);
		}
	}
});

function buttonDel(data, permission, pers){
	if ($.inArray(permission, pers) < 0) {
		return "";
	}
	var btn = $("<button class='layui-btn layui-btn-danger layui-btn-mini' title='删除' onclick='del(\"" + data +"\")'><i class='layui-icon'>&#xe640;</i></button>");
	return btn.prop("outerHTML");
}

function buttonEdit(id, permission, pers){
	if ($.inArray(permission, pers) < 0) {
		return "";
	}
	var btn = $("<button class='layui-btn layui-btn-mini' title='编辑' onclick='update(\"" + id +"\")'><i class='layui-icon'>&#xe642;</i></button>");
	return btn.prop("outerHTML");
}

function buttonconfig(id, permission, pers){
	var btn = $("<button class='layui-btn layui-btn-normal layui-btn-mini' title='关联' onclick='addCon(\"" + id +"\")'><i class='layui-icon'>&#xe644;</i></button>");
	return btn.prop("outerHTML");
}

function buttonPotenceconfig(id,name, permission, pers){
	var btn = $("<button class='layui-btn layui-btn-normal layui-btn-mini' title='关联' onclick='addCon(\"" + id +"\",\""+name+"\")'><i class='layui-icon'>&#xe644;</i></button>");
	return btn.prop("outerHTML");
}

function deleteCurrentTab(){
	var lay_id = $(parent.document).find("ul.layui-tab-title").children("li.layui-this").attr("lay-id");
	parent.active.tabDelete(lay_id);
}

//datatables分页控件初始化 提示信息
var lang = {
    "sProcessing": "正在加载数据...",
    "sZeroRecords": "没有匹配结果",
    "sInfo": "当前显示第 _START_ 至 _END_ 项，共 _TOTAL_ 项。",
    "sInfoEmpty": "当前显示第 0 至 0 项，共 0 项",
    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
    "sEmptyTable": "表中数据为空",
    "sLoadingRecords": "载入中...",
    "oPaginate": {
        "sFirst": "首页",
        "sPrevious": "上页",
        "sNext": "下页",
        "sLast": "末页",
        "sJump": "跳转"
    },
    "oAria": {
        "sSortAscending": ": 以升序排列此列",
        "sSortDescending": ": 以降序排列此列"
    }
};

var lang_mg = {
    "sProcessing": "ᠲᠣᠭ᠎ᠠ ᠪᠠᠷᠢᠮᠲᠠ ᠶᠢ ᠠᠴᠢᠶᠠᠯᠠᠵᠤ ᠪᠤᠢ...",
    "sZeroRecords": " ᠬᠠᠷᠠᠭᠠᠯᠵᠠᠭᠰᠠᠨ ᠦᠷ᠎ᠡ ᠳ᠋ᠦᠩ ᠦᠭᠡᠢ",
    "sInfo": "ᠤᠳᠤ ᠢᠯᠠᠷᠠᠵᠦ ᠪᠠᠶᠢᠭ᠎ᠠ _START_ ᠳ᠋ᠤᠭᠠᠷ ᠨᠢᠭᠤᠷ  _END_ ᠲᠥᠷᠥᠯ，ᠨᠡᠶᠢᠳᠡ _TOTAL_ ᠲᠦᠷᠦᠯ ",
    "sInfoEmpty": "ᠣᠳᠣ ᠢᠯᠡᠷᠡᠵᠦ ᠪᠠᠶᠢᠭ᠎ 0 ᠡᠴᠡ  0 ᠲᠥᠷᠥᠯ，ᠨᠠᠶᠢᠳᠠ  0 ᠲᠥᠷᠥᠯ",
    "sInfoFiltered": "(_MAX_ᠲᠦᠷᠦᠯ ᠢᠶᠡᠷ ᠰᠢᠭᠰᠢᠬᠦ )",
    "sEmptyTable": "ᠬᠦᠰᠦᠨᠦᠭᠲᠦ ᠳ᠋ᠡᠬᠢ ᠲᠣᠭ᠎ᠠ ᠪᠠᠷᠢᠮᠲᠠ ᠬᠤᠭᠤᠰᠤᠨ",
    "sLoadingRecords": "ᠠᠴᠢᠶᠠᠯᠠᠵᠤ ᠪᠤᠢ ...",
    "oPaginate": {
        "sFirst": "ᠲᠡᠷᠢᠭᠦᠨ ᠨᠢᠭᠤᠷ",
        "sPrevious": "ᠲᠦᠷᠦᠭᠦᠦ ᠨᠢᠭᠤᠷ",
        "sNext": "ᠳᠠᠷᠠᠭ᠎ᠠ ᠨᠢᠭᠤᠷ",
        "sLast": "ᠡᠴᠤᠰ ᠦᠨ ᠨᠢᠭᠤᠷ",
        "sJump": "ᠴᠢᠬᠤ"
    },
    "oAria": {
        "sSortAscending": ": ᠲᠤᠰ ᠮᠦᠷ ᠢ ᠳᠡᠪᠰᠢᠬᠦ ᠳᠠᠷᠠᠭᠠᠯᠠᠯ ᠢᠶᠠᠷ ᠵᠢᠭᠰᠠᠭᠠᠬᠤ",
        "sSortDescending": ": ᠲᠤᠰ ᠮᠦᠷ ᠢ ᠪᠠᠭᠤᠷᠠᠬᠤ ᠳᠠᠷᠠᠭᠠᠯᠠᠯ ᠢᠶᠠᠷ ᠵᠢᠭᠰᠠᠭᠠᠬᠤ"
    }
};


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
		if(!$(this).is(':hidden')) {
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
		if(!$(this).is(':hidden')) {
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
		if(!$(this).is(':hidden')) {
			if (!/(^\d{3,4}\-\d{7,8}$)|(^\d{7,8}\-\d{1,4}$)|(^\d{3,4}\-\d{7,8}\-\d{1,4}$)|(^\d{7,8}$)|(^1[345789]\d{9}$)/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "格式错误：<br>电话号码必须为11位手机号或者固话号码<br>固话号码格式为：[区号]<固话号>[分机号]<br>如：13104710471<br>或：0471-8888888-1234、0471-8888888、8888888-1234、8888888", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	
	// 全中文字段校验
	$(".cn").each(function() {
		if(!$(this).is(':hidden')) {
			if (!/^[\u4e00-\u9fa5]+$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "必须全为中文", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	
	// 纯数字字段校验
	$(".num").each(function() {
		if(!$(this).is(':hidden')) {
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


/**
 * 蒙文数据有效性校验
 */
function dataCheck_mn() {
	// 假设数据校验成功
	var checkFlag = true;

	// 数据为空校验
	$(".not-null").each(function() {
		if(!$(this).is(':hidden')) {
			if (this.value == null || this.value == "") {
				layer.msg($(this).attr("iname") + "ᠬᠣᠭᠤᠰᠤᠨ ᠪᠠᠶᠢᠵᠤ ᠪᠣᠯᠬᠤ ᠦᠭᠡᠢ ", {anim:6});
	
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
		if(!$(this).is(':hidden')) {
			if (!/^([0-9]){17}([0-9|x|X]){1}$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "ᠪᠤᠷᠤᠭᠤ", {anim:6});
				
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
		if(!$(this).is(':hidden')) {
			if (!/^([0-9]){6}$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "ᠪᠤᠷᠤᠭᠤ <br>ᠰᠢᠤᠳᠠᠨ ᠳ᠋ᠤᠭᠠᠷ ᠨᠢ ᠵᠠᠪᠠᠯ 6ᠣᠷᠤᠨᠲᠤ ᠲᠣᠭ᠎ᠠ  ᠪᠠᠷ ᠪᠥᠷᠢᠯᠳᠦᠵᠡᠢ ", {anim:6});
				
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
		if(!$(this).is(':hidden')) {
			if (!/(^\d{3,4}\-\d{7,8}$)|(^\d{7,8}\-\d{1,4}$)|(^\d{3,4}\-\d{7,8}\-\d{1,4}$)|(^\d{7,8}$)|(^1[345789]\d{9}$)/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "ᠪᠤᠷᠤᠭᠤ <br>ᠤᠲᠠᠰᠤᠨ ᠳ᠋ᠤᠭᠠᠷ ᠨᠢ ᠵᠠᠪᠠᠯ 11ᠣᠷᠤᠨᠲᠤ ᠭᠠᠷ ᠤᠲᠠᠰᠤᠨ ᠳ᠋ᠤᠭᠠᠷ ᠪᠤᠶᠤ ᠳᠣᠭᠲᠠᠮᠠᠯ ᠤᠲᠠᠰᠤᠨ ᠳ᠋ᠤᠭᠠᠷ <br>ᠳᠣᠭᠲᠠᠮᠠᠯ ᠤᠲᠠᠰᠤᠨ ᠳ᠋ᠤᠭᠠᠷ ᠦᠨ ᠬᠡᠯᠪᠡᠷᠢ ᠨᠢ [ᠣᠷᠤᠨ ᠦ ᠳ᠋ᠤᠭᠠᠷ]<ᠳᠣᠭᠲᠠᠮᠠᠯ ᠳ᠋ᠤᠭᠠᠷ>[ᠰᠠᠯᠪᠤᠷᠢ ᠤᠲᠠᠰᠤᠨ ᠳ᠋ᠤᠭᠠᠷ]<br>ᠵᠢᠱᠢᠶᠡᠯᠡᠪᠡᠯ ：13104710471<br>ᠪᠤᠶᠤ 0471-8888888-1234、0471-8888888、8888888-1234、8888888", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	
	// 全中文字段校验
	$(".cn").each(function() {
		if(!$(this).is(':hidden')) {
			if (!/^[\u4e00-\u9fa5]+$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "ᠵᠠᠪᠠᠯ ᠬᠢᠲᠠᠳ ᠬᠡᠯᠡ ᠪᠠᠶᠢᠬᠤ ", {anim:6});
	
				checkFlag = false;
				return checkFlag;
			}
		}
	});
	
	// 纯数字字段校验
	$(".num").each(function() {
		if(!$(this).is(':hidden')) {
			if (!/^[0-9]*$/.test($(this).val())) {
				layer.msg($(this).attr("iname") + "ᠶᠣᠰᠣ ᠪᠣᠰᠣ ᠲᠡᠮᠳᠡᠭ ᠠᠭᠤᠯᠤᠭᠳᠠᠬᠤ", {anim:6});
	
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
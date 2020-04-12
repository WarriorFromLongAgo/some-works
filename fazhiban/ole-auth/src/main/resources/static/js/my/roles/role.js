function initRoles() {
	
	$("#roles").combotree({
		required: false,
		multiple:true,
		cascadeCheck:false,
		onLoadSuccess:function(){
			
		}
	});
	
	$.ajax({
		type : 'get',
		url : '/auth/roles/all',
		async : false,
		success : function(data) {
			var treeData=[];
			for (var i = 0; i < data.length; i++) {
				var d = data[i];
				var id = d['id'];
				var name = d['name'];
				treeData[i]={id:id,text:name};
			}
			$("#roles").combotree('loadData',treeData);
		}
	});
}

function getCheckedRoleIds() {
	var ids = $("#roles").combotree('getValues');
	return ids;
}

function initRoleDatas(userId) {
	$.ajax({
		type : 'get',
		url : '/auth/roles?userId=' + userId,
		success : function(data) {
			$("#roles").combotree('setValues',data);
		}
	});
}
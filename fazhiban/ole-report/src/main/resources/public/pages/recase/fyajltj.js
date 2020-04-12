$(function() {
	//获取从2014年至今,当前部门及下级部门复议案件数量
	var yearArr = [];
	for(var i=2014;i<=new Date().getFullYear();i++){
		yearArr.push(i);
	}
	var CaseHandBarChart = echarts.init(document.getElementById('CaseHandBarChart'));
	CaseHandBarChart.showLoading({
		text: 'loading',
		color: '#c23531',
		textColor: '#fff',
		maskColor: 'rgba(0, 18, 99, 0.7)',
		zlevel: 0
	});
	
	$.get('/report/recase/yearCount').done(function (data) {
		var mydata={
			data:data,
			getNameList : function(){
				var res = [];
				this.data.forEach(function(value, index, array) {
					res.push(value.name);
				});
				return res;
			},
			getSeries : function(){
				var res = [];
				this.data.forEach(function(value, index, array) {
					res.push({
						name: value.name,
						type: 'bar',
						data: value.value
					});
					
				});
				return res;
			}
		};
		
		CaseHandBarChart.setOption({
			title: {
			text: '年复议案件数统计',
			textStyle : {
				//演示
				//color : '#7B8CA5'
				color : '#fff'
			}},
			tooltip: {
				trigger: 'axis',
				axisPointer: {
					type: 'shadow'
				}
			},
			legend: {
				x: 'left', // 'center' | 'left' | {number},
		        y: 'top', // 'center' | 'bottom' | {number}
		        width:100,
		        top:50,
				textStyle:{color : '#7B8CA5'},
				data: mydata.getNameList()
			},
			toolbox: {
				show: true,
				orient: 'vertical',
				left: 'right',
				top: 'center',
				feature: {
					mark: {show: true},
					dataView: {show: true, readOnly: false},
					magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
					restore: {show: true},
					saveAsImage: {show: true}
				}
			},
			calculable: true,
			xAxis: [{
				axisLine: {
					lineStyle: {
						color: '#7B8CA5'
					}
				},
			type: 'category',
			axisTick: {show: false},
			data: yearArr
			}],
			yAxis: [{
				axisLine: {
					lineStyle: {
						color: '#7B8CA5'
					}
				},
				type: 'value'
			}],
			series: mydata.getSeries()
		});
		CaseHandBarChart.hideLoading();
		
	});
})
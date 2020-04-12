/**
 * 各部门复议案件数量统计 田俊文
 */
var bmfy = echarts.init(document.getElementById('bmfy'), 'echartstheme');
$(function() {

	bmfy.showLoading({
		text : 'loading',
		color : '#0066FF',
		textColor : '#fff',
		maskColor : 'rgba(0, 18, 99, 0.7)',
		zlevel : 0
	});
	$.get('/report/recase/bmfy').done(function(data) {
		bmfy.hideLoading();
		// console.log(data)
		var deptArr = [];
		var seriesArr = [];
		$.each(data, function(index, item) {
			deptArr[index] = item.name;
			seriesArr[index] = item.value;
		})
		option = {
			title : {
				text : '各部门复议案件数量统计'
			},
			tooltip : {
				trigger : 'axis',
				axisPointer : {
					type : 'shadow',
					label : {
						show : true
					}
				}
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			legend : {
				data : [ '复议案件数' ],
				itemGap : 5
			},
			grid : {
				top : '12%',
				left : '4%',
				right : '7%',
				containLabel : true
			},
			xAxis : [ {
				type : 'category',
				data : deptArr,
				axisLabel : {
					interval : 0,// 横轴信息全部显示
					rotate : 25,// 25度角倾斜显示
				}
			},

			],
			yAxis : [ {
				type : 'value',
				axisLabel : {
					formatter : function(a) {
						a = +a;
						return isFinite(a) ? echarts.format.addCommas(+a) : '';
					}
				}
			} ],
			dataZoom : [ {
				show : true,
				start : 0,
				end : 10
			}, {
				type : 'inside',
				start : 94,
				end : 100
			}, {
				show : true,
				yAxisIndex : 0,
				filterMode : 'empty',
				width : 30,
				height : '80%',
				showDataShadow : false,
				left : '97%'
			} ],
			series : [ {
				name : '复议案件数',
				type : 'bar',
				data : seriesArr
			} ]
		};

		bmfy.setOption(option);

	});

});
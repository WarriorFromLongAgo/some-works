/**
 * 案件评查结果统计
 */
var ReviewChart = echarts.init(document.getElementById('ReviewChart'));
$(function() {

	ReviewChart.showLoading({
		text : 'loading',
		color : '#c23531',
		textColor : '#fff',
		maskColor : 'rgba(0, 18, 99, 0.7)',
		zlevel : 0
	});

	ReviewChart.setOption({
		title : {
			text : '案件评查结果统计',
			textStyle : {
				color : '#fff'
			}
		},
		color : [ '#074dff', '#009aff', '#00d8ff' ],
		tooltip : {
			trigger : 'axis',
			axisPointer : {
				type : 'shadow'
			}
		},
		legend : {
			textStyle : {
				color : '#7B8CA5'
			},
			data : [],
			top : '5%',
			right : '1%'
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
				dataView : {
					show : true,
					readOnly : false
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
		xAxis : [ {
			type : 'category',
			axisTick : {
				show : false
			},
			data : [],
			axisLine : {
				lineStyle : {
					color : '#7B8CA5'
				}
			},
		} ],
		yAxis : [ {
			type : 'value',
			axisLine : {
				lineStyle : {
					color : '#7B8CA5'
				}
			},
			splitLine : {
				show : true,
				lineStyle : {
					color : '#7B8CA5'
				}
			}
		} ],
		series : [ {
			type : 'bar',
			barGap : 0,
			data : [],
		}, {
			type : 'bar',
			data : [],
		}, {
			type : 'bar',
			data : [],
		},

		],
		label : {
			normal : {
				show : true,// 是否展示
				position : 'top',
				textStyle : {
					fontWeight : 'bolder',
					fontSize : '12',
					fontFamily : '微软雅黑',
				}
			}
		}
	})

	// 异步柱状图数据
	$.get('/report/reportreview/bar').done(function(data) {
		var lengedArr = [];
		var yearArr = [];
		var value1Arr = [];
		var value2Arr = [];
		var value3Arr = [];
		lengedArr = data.lenged;
		$.each(data.BarData, function(index, item) {
			yearArr[index] = item.name;
			value1Arr[index] = item.value1;
			value2Arr[index] = item.value2;
			value3Arr[index] = item.value3;
		})

		ReviewChart.hideLoading();

		ReviewChart.setOption({
			legend : {
				data : lengedArr,
				top : '5%',
				right : '1%'
			},
			xAxis : [ {
				data : yearArr
			} ],
			series : [ {
				name : lengedArr[0],
				type : 'bar',
				data : value1Arr
			}, {
				name : lengedArr[1],
				type : 'bar',
				data : value2Arr
			}, {
				name : lengedArr[2],
				type : 'bar',
				data : value3Arr
			} ]
		})
	});
});
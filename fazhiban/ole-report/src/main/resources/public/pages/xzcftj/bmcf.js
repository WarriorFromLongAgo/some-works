/**
 * 部门行政处罚案件数
 */
var bmcfChart = echarts.init(document.getElementById('bmcfChart'),'echartstheme');
var nsjzChart = echarts.init(document.getElementById('nsjzChart'),'echartstheme');
$(function() {

	bmcfChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	var yearArr = [];

	//初始化时间轴数据
	$.ajax({
		type: "GET",
		url: "/report/areamap/areaMapInit",
		async: false,
		success: function(data) {
			yearArr = data.year;
		},
		error: function(data) {
			layer.msg(data);
		}
	});


	//年份时间轴数据
	var nsjzoption = {
		timeline: {
			data: yearArr,
			axisType: 'category',
			playInterval: '3000',
			autoPlay: true,
			//currentIndex : 4,//默认年份下标
			//orient : 'vertical',
			//left : '10%',
			label: {
				formatter: function(s) {
					return s.slice(0, 4);
				},
			},
		},
	};
	//设置年时间轴
	nsjzChart.setOption(nsjzoption);
	
	//自动播放或点击年份时间轴查询图表数据
	nsjzChart.on("timelinechanged", function(param) {
		//console.log(yearArr[param.currentIndex]);
		var year = yearArr[param.currentIndex];
		//var categoryIndex = getCategoryName(flsjzchart,categoryArr);
		//getAreaDailyFun(categoryIndex,year);
		AreaAdminDept(year);
	});
	
	
	AreaAdminDept();

	function AreaAdminDept(year) {
		$.ajax({
			type: "GET",
			url: "/report/AdminSanction/AdminPunDept?year=" + year,
			success: function(data) {
				var name = [];
				var value = [];
				$.each(data, function(index, item) {
					name[index] = item.name;
					value[index] = item.value;
				})
				bmcfChart.hideLoading();
				doption = {
					title: {
						text: '各部门行政处罚案件数量',
					},
					tooltip: {
						trigger: 'axis',
						axisPointer: {
							type: 'shadow',
							label: {
								show: true
							}
						}
					},
					toolbox: {
						show: true,
						feature: {
							mark: {
								show: true
							},
							magicType: {
								show: true,
								type: ['line', 'bar']
							},
							restore: {
								show: true
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					legend: {
						data: ['处罚案件数量'],
						itemGap: 5
					},
					grid: {
						top: '12%',
						left: '4%',
						right: '7%',
						containLabel: true
					},
					xAxis: [{
						type: 'category',
						data: name,
						axisLabel: {
							interval: 0,
							//横轴信息全部显示  
							rotate: 25,
							//25度角倾斜显示 
						}
					},

					],
					yAxis: [{
						type: 'value',
						axisLabel: {
							formatter: function(a) {
								a = +a;
								return isFinite(a) ? echarts.format.addCommas(+a) : '';
							}
						}
					}],
					dataZoom: [
			            {
			                show: true,
			                start: 0,
			                end: 10
			            },
			            {
			                type: 'inside',
			                start: 94,
			                end: 100
			            },
			            {
			                show: true,
			                yAxisIndex: 0,
			                filterMode: 'empty',
			                width: 30,
			                height: '80%',
			                showDataShadow: false,
			                left: '97%'
			            }
			        ],
					series: [{
						name: '处罚案件数量',
						type: 'bar',
						data: value,
						//顶部数字展示pzr  
	                    itemStyle: {  
	                        normal: {  
	                            label: {  
	                                show: true,//是否展示  
	                                position: 'top',
	                                textStyle: {  
	                                    fontWeight:'bolder',  
	                                    fontSize : '12',  
	                                    fontFamily : '微软雅黑',  
	                                }  
	                            }  
	                        }
	                    }
					}],
				}
				bmcfChart.setOption(doption);
			}
		})
	}
});
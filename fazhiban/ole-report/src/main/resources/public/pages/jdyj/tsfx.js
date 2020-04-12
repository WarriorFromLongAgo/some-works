$(function() {
	//获取近五年每年的投诉总量
	var yearArr = [];
	for(var i=new Date().getFullYear()-4;i<=new Date().getFullYear();i++){
		yearArr.push(i);
	}
	//年投诉量图
	var PostBar = echarts.init(document.getElementById('PostBar'));
	//月投诉量图
	var CaseHandBarChart = echarts.init(document.getElementById('CaseHandBarChart'));
	PostBar.showLoading({
		text: 'loading',
		color: '#c23531',
		textColor: '#fff',
		maskColor: 'rgba(0, 18, 99, 0.7)',
		zlevel: 0
	});
	CaseHandBarChart.showLoading({
		text: 'loading',
		color: '#c23531',
		textColor: '#fff',
		maskColor: 'rgba(0, 18, 99, 0.7)',
		zlevel: 0
	});
	
	PostBar.on('click',function(params){
		getMonth(yearArr[params.dataIndex])
	});
	
	$.get('/report/complaint/recentYears').done(function (data) {
		PostBar.setOption({
			title : {
                text: '近5年投诉量统计',
                x:'center',
                textStyle : {
                	//演示
    				//color : '#7B8CA5'
                	color:'#fff'
			}},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    toolbox: {
				show: true,
				orient: 'vertical',
				left: 'right',
				top: 'center',
				feature: {
					mark: {show: true},
					dataView: {show: true, readOnly: false},
					magicType: {show: true, type: ['line', 'bar']},
					restore: {show: true},
					saveAsImage: {show: true}
				}
			},
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    xAxis : [
		        {
		        	axisLine: {
						lineStyle: {
							color: '#7B8CA5'
						}
					},
		            type : 'category',
		            data : yearArr,
		            axisTick: {
		                alignWithLabel: true
		            }
		        }
		    ],
		    yAxis : [
		        {
		        	axisLine: {
						lineStyle: {
							color: '#7B8CA5'
						}
					},
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'投诉量',
		            type:'bar',
		            barWidth: '60%',
		            data:data
		        }
		    ]
		});
		PostBar.hideLoading();
		
	});
	getMonth(new Date().getFullYear());
	function getMonth(year){
		$.get('/report/complaint/monthly?year='+year).done(function (data) {
			CaseHandBarChart.setOption({
				title : {
	                text: year+'年月投诉量统计',
	                x:'center',
	                textStyle : {
	                	//演示
	    				//color : '#7B8CA5'
	                	color:'#fff'
				}},
			    color: ['#3398DB'],
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    toolbox: {
					show: true,
					orient: 'vertical',
					left: 'right',
					top: 'center',
					feature: {
						mark: {show: true},
						dataView: {show: true, readOnly: false},
						magicType: {show: true, type: ['line', 'bar']},
						restore: {show: true},
						saveAsImage: {show: true}
					}
				},
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			        	axisLine: {
							lineStyle: {
								color: '#7B8CA5'
							}
						},
			            type : 'category',
			            data : ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			        	axisLine: {
							lineStyle: {
								color: '#7B8CA5'
							}
						},
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'投诉量',
			            type:'bar',
			            barWidth: '60%',
			            data:data
			        }
			    ]
			});
			CaseHandBarChart.hideLoading();
			
		});
	}
	
})
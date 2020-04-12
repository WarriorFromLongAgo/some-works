/**
 * 案件处理统计图
 * 田俊文
 */
var CaseHandBarChart = echarts.init(document.getElementById('CaseHandBarChart'));
var CaseHandLineChart = echarts.init(document.getElementById('CaseHandLineChart'));
$(function() {
	var myDate = new Date();
	//获取当前年
	var year=myDate.getFullYear();
	
	CaseHandBarChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	CaseHandLineChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	
	
	//填入这折线图基础数据
	CaseHandBarChart.setOption({
		title: {
	        text: '案件数统计',
	        textStyle : {
	        	//演示
                //color : '#7B8CA5'
	        	color : '#fff'
			}
	    },
	    color : [ '#074dff','#009aff','#00d8ff'],
	    tooltip: {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow'
	        }
	    },
	    legend: {
	    	textStyle : {
	        	color : '#7B8CA5'
			},
	        data: [],
	        top : '5%',
	        right:'1%'
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            mark: {show: true},
	            magicType: {show: true, type: ['line', 'bar']},
	            dataView : {show: true, readOnly: false},
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    calculable: true,
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {show: false},
	            data: [],
	            axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
	        }
	    ],
	    yAxis: [
	        {
	        	type: 'value',
	            axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
		        splitLine: {
	        		show: true,
	        		lineStyle: {
	        			color: '#7B8CA5'
	        		}
	        	}
	        }
	    ],
	    series: [
	        {
	            type: 'bar',
	            barGap: 0,
	            data: [],
	        },
	        {
	            type: 'bar',
	            data: [],
	        },
	        {
	            type: 'bar',
	            data: [],
	        },
	       
	    ],
	    label: {  
    		normal: {  
                show: true,//是否展示
                position: 'top',
                textStyle: {  
                    fontWeight:'bolder',  
                    fontSize : '12',  
                    fontFamily : '微软雅黑',  
                }  
            }
        }
	})
	
	
	
	//异步柱状图数据
	$.get('/report/casehead/bar').done(function (data) {
		var lengedArr = [];
		var yearArr = [];
		var value1Arr = [];
		var value2Arr = [];
		var value3Arr = [];
		lengedArr = data.lenged;
		$.each(data.BarData,function(index,item){
			yearArr[index] = item.name;
			value1Arr[index] = item.value1;
			value2Arr[index] = item.value2;
			value3Arr[index] = item.value3;
		})
		
		CaseHandBarChart.hideLoading();
		
		CaseHandBarChart.setOption({
			legend: {
		        data: lengedArr,
		        top : '5%',
		        right:'1%'
		    },
		    xAxis: [
		        {
		            data: yearArr
		        }
		    ],
		    series: [
		        {
		            name: lengedArr[0],
		            type: 'bar',
		            data: value1Arr
		        },
		        {
		            name: lengedArr[1],
		            type: 'bar',
		            data: value2Arr
		        },
		        {
		            name: lengedArr[2],
		            type: 'bar',
		            data: value3Arr
		        }
		    ]
		})
	});

	CaseHandBarChart.on('click', function(params) {
		CaseHandLineChartC(params.name);
		});
	
	// 填入折线图基础数据
	CaseHandLineChart.setOption({
			title: {
		        text: '案件处理结果分析',
		        textStyle : {
		        	//演示
	                //color : '#7B8CA5'
		        	color : '#fff'
				},
		    },
		    color: ['#73e9d1', '#00d8ff', '#0069ff'],
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		    	textStyle : {
		        	color : '#7B8CA5'
				},
		        data:[],
		        top:'5%',
		        right:'1%'
		    },
		    toolbox: {
		        show: true,
		        feature: {
		            magicType: {type: ['line', 'bar']},
		            restore: {},
		            saveAsImage: {}
		        }
		    },
		    xAxis:  {
		        type: 'category',
		        boundaryGap: false,
		        data: [],
		        axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
		    },
		    yAxis: {
		        type: 'value',
		        axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
		        splitLine: {
	        		show: true,
	        		lineStyle: {
	        			color: '#7B8CA5'
	        		}
	        	}
		    },
		    series: [
		        {
		            type:'line',
		            data:[],
		        },
		        {
		            type:'line',
		            data:[],
		        },
		        {
		            type:'line',
		            data:[],
		            
		        }
		    ],
		    label: {  
	    		normal: {  
	                show: true,//是否展示
	                position: 'top',
	                textStyle: {  
	                    fontWeight:'bolder',  
	                    fontSize : '12',  
	                    fontFamily : '微软雅黑',  
	                }  
	            }
	        }
	});
	CaseHandLineChartC(year);
	function CaseHandLineChartC(year){
		var title = year+"年";
		//异步柱状图数据
		$.get('/report/casehead/line?year='+year).done(function (data) {
			
			var lengedArr = [];
			var monthArr = [];
			var value1Arr = [];
			var value2Arr = [];
			var value3Arr = [];
			lengedArr = data.lenged;
			monthArr = data.month;
			$.each(data.LineData,function(index,item){
				value1Arr[index] = item.value1;
				value2Arr[index] = item.value2;
				value3Arr[index] = item.value3;
			})
			//console.log(yearArr);
			CaseHandLineChart.hideLoading();
			CaseHandLineChart.setOption({
				title: {
					text: title+"案件处理结果"
			    },
				legend: {
			        data: lengedArr,
			        top:'5%',
			        right:'1%'
			    },
			    xAxis: [
			        {
			            data: monthArr
			        }
			    ],
			    series: [
			        {
			            name: lengedArr[0],
			            type: 'line',
			            data: value1Arr,
			            markPoint: {
			                data: [
			                    {type: 'max', name: '最大值'},
			                    {type: 'min', name: '最小值'}
			                ]
			            },
			            markLine: {
			                data: [
			                    {type: 'average', name: '平均值'}
			                ]
			            }
			        },
			        {
			            name: lengedArr[1],
			            type: 'line',
			            data: value2Arr,
			            markPoint: {
			                data: [
			                    {type: 'max', name: '最大值'},
			                    {type: 'min', name: '最小值'}
			                ]
			            },
			            markLine: {
			                data: [
			                    {type: 'average', name: '平均值'}
			                ]
			            }
			        },
			        {
			            name: lengedArr[2],
			            type: 'line',
			            data: value3Arr,
			            markPoint: {
			            	data: [
			                    {type: 'max', name: '最大值'},
			                    {type: 'min', name: '最小值'}
			                ]
			            },
			            markLine: {
			                data: [
			                    {type: 'average', name: '平均值'},
			                ]
			            }
			        }
			    ]
			})
		});
	}
});
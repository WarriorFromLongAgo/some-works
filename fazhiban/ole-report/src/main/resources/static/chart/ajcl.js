/**
 * 案件处理统计图
 * 田俊文
 */
var CaseHandBarChart = echarts.init(document.getElementById('CaseHandBarChart'));
var CaseHandLineChart = echarts.init(document.getElementById('CaseHandLineChart'));
$(function() {

	//填入这折线图基础数据
	CaseHandBarChart.setOption({
		title: {
	        text: '案件数统计',
	        textStyle : {
		        color : '#7B8CA5'
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
	        data: ["受理案件量","立案案件量","結案案件量"],
	        top : '5%',
	        right:'1%',
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            mark: {show: true},
	            dataView: {show: true, readOnly: false},
	            magicType: {show: true, type: ['line', 'bar']},
	            restore: {show: true},
	            saveAsImage: {show: true}
	        }
	    },
	    calculable: true,
	    xAxis: [
	        {
	            type: 'category',
	            axisTick: {show: false},
	            data: ['2013','2014','2015','2016','2017'],
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
	        	name:'受理案件量',
	            type: 'bar',
	            barGap: 0,
	            data: ['20','43','3453','453','5223']
	        },
	        {
	        	name:'立案案件量',
	            type: 'bar',
	            data: ['20','2343','453','3453','1523']
	        },
	        {
	        	name:'結案案件量',
	            type: 'bar',
	            data: ['2320','5443','353','343','523']
	        }
	    ]
	})
	
	
	
	//异步柱状图数据
	/*$.get('/report/casehead/bar').done(function (data) {
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
	});*/
	
	CaseHandBarChart.on('click', function(params) {
		CaseHandLineChartC(params.name);
		});
	
	// 填入折线图基础数据
	CaseHandLineChart.setOption({
			title: {
		        text: '案件处理结果分析',
		        textStyle : {
			        color : '#7B8CA5'
				},
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    legend: {
		        data:[]
		    },
		    toolbox: {
		        show: true,
		        feature: {
		            dataView: {readOnly: false},
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
		    ]
	});


	CaseHandLineChart.setOption({
				title: {
					text: "案件处理结果",
					textStyle : {
						color : '#7B8CA5'
					},
			    },
			    color: ['#73e9d1', '#00d8ff', '#0069ff'],
				legend: {
					textStyle : {
			        	color : '#7B8CA5'
					},
			        data: ["受理案件量","立案案件量","結案案件量"],
			        top:'5%',
			        right:'1%'
			    },

			    xAxis: [

			        {
			            data: ["1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"],
			        }
			    ],
			 //    toolbox: {
				//     orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
				//                                // 'horizontal' ¦ 'vertical'
				//     x: 'right',                // 水平安放位置，默认为全图右对齐，可选为：
				//                                // 'center' ¦ 'left' ¦ 'right'
				//                                // ¦ {number}（x坐标，单位px）
				//     y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
				//                                // 'top' ¦ 'bottom' ¦ 'center'
				//                                // ¦ {number}（y坐标，单位px）
				//     color : ['#1e90ff','#22bb22','#4b0082','#d2691e'],
				//     backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
				//     borderColor: '#ccc',       // 工具箱边框颜色
				//     borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
				//     padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
				//                                // 接受数组分别设定上右下左边距，同css
				//     itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，
				//                                // 横向布局时为水平间隔，纵向布局时为纵向间隔
				//     itemSize: 16,              // 工具箱图形宽度
				//     featureImageIcon : {},     // 自定义图片icon
				//     featureTitle : {
				//         mark : '辅助线开关',
				//         markUndo : '删除辅助线',
				//         markClear : '清空辅助线',
				//         dataZoom : '区域缩放',
				//         dataZoomReset : '区域缩放后退',
				//         dataView : '数据视图',
				//         lineChart : '折线图切换',
				//         barChart : '柱形图切换',
				//         restore : '还原',
				//         saveAsImage : '保存为图片'
				//     }
				// },
			    // splitLine: {           // 分隔线
			    //     show: true,        // 默认显示，属性show控制显示与否
			    //     // onGap: null,
			    //     lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
			    //         color: ['#fff'],
			    //         width: 10,
			    //         type: 'solid'
			    //     }
			    // },
			 //    grid: {
				//     x: 100,
				//     y: 60,
				//     x2: 100,
				//     y2: 60,
				//     // width: {totalWidth} - x - x2,
				//     // height: {totalHeight} - y - y2,
				//     backgroundColor: 'rgba(234,0,145,0)',
				//     borderWidth: 10,
				//     borderColor: '#000'
				// },

			 //    dataRange: {
				//     orient: 'vertical',        // 布局方式，默认为垂直布局，可选为：
				//                                // 'horizontal' ¦ 'vertical'
				//     x: 'left',                 // 水平安放位置，默认为全图左对齐，可选为：
				//                                // 'center' ¦ 'left' ¦ 'right'
				//                                // ¦ {number}（x坐标，单位px）
				//     y: 'center',               // 垂直安放位置，默认为全图底部，可选为：
				//                                // 'top' ¦ 'bottom' ¦ 'center'
				//                                // ¦ {number}（y坐标，单位px）
				//     backgroundColor: 'rgba(0,0,0,0)',
				//     borderColor: '#ccc',       // 值域边框颜色
				//     borderWidth: 0,            // 值域边框线宽，单位px，默认为0（无边框）
				//     padding: 5,                // 值域内边距，单位px，默认各方向内边距为5，
				//                                // 接受数组分别设定上右下左边距，同css
				//     itemGap: 10,               // 各个item之间的间隔，单位px，默认为10，
				//                                // 横向布局时为水平间隔，纵向布局时为纵向间隔
				//     itemWidth: 20,             // 值域图形宽度，线性渐变水平布局宽度为该值 * 10
				//     itemHeight: 14,            // 值域图形高度，线性渐变垂直布局高度为该值 * 10
				//     splitNumber: 5,            // 分割段数，默认为5，为0时为线性渐变
				//     color:['#1e90ff','#f0ffff'],//颜色 
				//     //text:['高','低'],         // 文本，默认为数值文本
				//     textStyle: {
				//         color: '#333'          // 值域文字颜色
				//     }
				// },
			    series: [			    	
			        {
			            name: ["受理案件量"],
			            type: 'line',
			            data: ["50","50","45","42","44","0","391","133","51","47","52","47",],
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
			            name: ["立案案件量"],
			            type: 'line',
			            data: ["47","52","47","50","50","391","133","51","45","42","44","0",],
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
			            name: ["結案案件量"],
			            type: 'line',
			            data: ["133","51","47","52","47","50","50","45","391","42","44","0",],
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
	/*CaseHandLineChartC("2018");
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
			
			
		});
	}*/
});
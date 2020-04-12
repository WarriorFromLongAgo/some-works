/**
 * 案件发生统计图
 * 田俊文
 */
var caseChart = echarts.init(document.getElementById('caseChart'));
$(function() {
	
	//异步柱状图数据
	//$.get('/report/casehotmap/ymhotmap').done(function (data) {
		var maxValue = [];
		//解析数据            //封装成所需要的数据 x：距右边距的像素，y：距上边距的像素，code：热度值
		var heatData= [[11, 0, "2"],
[0, 1, "63"],[1, 1, "43"],[2, 1, "66"],[3, 1, "50"],[4, 1, "63"],[5, 1, "45"],[6, 1, "52"],[7, 1, "50"],[8, 1, "46"],[9, 1, "49"],
[10, 1, "58"],[11, 1, "2"],[0, 2, "56"],[1, 2, "49"],[2, 2, "53"],[3, 2, "48"],[4, 2, "50"],[5, 2, "55"],[6, 2, "47"],[7, 2, "50"],[8, 2, "44"],
[9, 2, "48"],[10, 2, "53"],[11, 2, "2"],[0, 3, "40"],[1, 3, "40"],[2, 3, "49"],[3, 3, "51"],[4, 3, "55"],[5, 3, "58"],[6, 3, "54"],[7, 3, "48"],
[8, 3, "56"],[9, 3, "47"],[10, 3, "44"],[11, 3, "4"],[0, 4, "41"],[1, 4, "49"],[2, 4, "58"],[3, 4, "53"],[4, 4, "45"],[5, 4, "48"],[6, 4, "45"],
[7, 4, "53"],[8, 4, "51"],[9, 4, "53"],[10, 4, "51"],[11, 4, "13"],[0, 5, "391"],[1, 5, "133"],[2, 5, "51"],[3, 5, "47"],[4, 5, "52"],
[5, 5, "47"],[6, 5, "50"],[7, 5, "50"],[8, 5, "45"],[9, 5, "42"],[10, 5, "44"],];


	    
	//});
	
	option = {
		    animation: false,
		    grid: {
		        height: '50%',
		        y: '10%'
		    },
		    xAxis: {
		    	name:'月份',
		        type: 'category',
		        data: [],
		        axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
		        axisLabel: {
		        	color: '#7B8CA5'
		        },
		        splitArea: {
		            show: true
		        }
		    },
		    yAxis: {
		    	name:'年份',
		        type: 'category',
		        data: [],
		        axisLine: {
		        	lineStyle: {
		        		color: '#7B8CA5'
		        	}
		        },
		        axisLabel: {
		        	color: '#7B8CA5'
		        },
		        splitArea: {
		            show: true,
		            lineStyle: {
	        			color: '#7B8CA5'
	        		}
		        }
		    },
		    visualMap: {
		    	textStyle : {
			        color : '#7B8CA5'
				},
		    	inRange: {
					color : [ '#00d8ff','#4200ff' ]
					// color : [ 'rgba(12, 0, 255, 0.5)','rgba(28, 0, 130, 0.5)' ]
		    	},
		        min: 0,
		        max: 500,
		        calculable: true,
		        orient: 'horizontal',
		        left: 'center',
		        bottom: '15%',
		    },
		    series: [{
		        name: '案件数量',
		        type: 'heatmap',
		        data: [],
		        label: {
		            normal: {
		                show: true
		            }
		        },
		        itemStyle: {

		            emphasis: {
		                shadowBlur: 10,
		                shadowColor: 'rgba(0, 6, 255, 0.5)'
		            }
		        }
		    }]
	};
	caseChart.setOption(option);


	var year = ['2014','2015','2016','2017','2018'];
	var month = ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'];


	// 填入数据
		caseChart.setOption({
			tooltip: {
		        position: 'top',
		        formatter: function (params, ticket, callback) {
		        		return year[params.value[1]]+"年"+month[params.value[0]]+":"+params.value[2]+"件";
		         }
		    },
			title:{
				text:'案件发生热点图',
				x:'center' ,
			    textStyle : {
			        color : '#7B8CA5'
				}
			},
	        xAxis: {
	            data: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
	        },
	        yAxis:{
	        	data: ['2013','2014','2015','2016','2017','2018'],
	        },
	        visualMap: {
		        min: 0,
		        max: 300,
		    },
	        series : 
	        	[{
	        		type: 'heatmap',
		            data: heatData,
		        }]
	    });

});
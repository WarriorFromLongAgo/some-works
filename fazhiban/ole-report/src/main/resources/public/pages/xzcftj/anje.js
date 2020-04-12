/**
 * 案件发生统计图
 * 田俊文
 */
var caseChart = echarts.init(document.getElementById('caseChart'),'echartstheme');
$(function() {
	
	//异步柱状图数据
	$.get('/report/casehotmap/ymhotmap').done(function (data) {
		var maxValue = [];
		//解析数据
		var heatData=new Array();//定义数组存取后台数据
        //封装成所需要的数据 x：距右边距的像素，y：距上边距的像素，code：热度值
        for(var i = 0;i < data.series.length; i++) {
            heatData[i]=[data.series[i].y,data.series[i].x,data.series[i].code];
            maxValue[i] = data.series[i].code;
        } 
	    // 填入数据
		caseChart.setOption({
			tooltip: {
		        position: 'top',
		        formatter: function (params, ticket, callback) {
		        		return data.year[params.value[1]]+"年"+data.month[params.value[0]]+":"+params.value[2]+"件";
		         }
		    },
			title:{
				text:'案件发生热点图',
				x:'center',
				textStyle : {
					//演示
	                //color : '#7B8CA5'
				}
			},
	        xAxis: {
	            data: data.month,
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
	        yAxis:{
	        	data: data.year,
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
		        max: Math.max.apply(null, maxValue),
		    },
	        series : 
	        	[{
	        		type: 'heatmap',
		            data: heatData,
		        }]
	    });
	});
	
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
		        splitArea: {
		            show: true
		        }
		    },
		    yAxis: {
		    	name:'年份',
		        type: 'category',
		        data: [],
		        splitArea: {
		            show: true
		        }
		    },
		    visualMap: {
		        min: 0,
		        max: 500,
		        calculable: true,
		        orient: 'horizontal',
		        left: 'center',
		        bottom: '15%'
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
		                shadowColor: 'rgba(0, 0, 0, 0.5)'
		            }
		        }
		    }]
	};
	caseChart.setOption(option);
});
/**
 * 执法人员分布
 * 田俊文
 */
var BarChart = echarts.init(document.getElementById('BarChart'),'echartstheme');
var PieChart = echarts.init(document.getElementById('PieChart'),'echartstheme');
$(function() {

	BarChart.setOption({
		title : {
	        text: '全市执法人员分布',
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['执法人员','监督人员']
	    },
	    dataZoom: [
            {
                show: true,
                start: 0,
                end: 50
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
                left: '93%'
            }
        ],
	    toolbox: {
	        show : true,
	        feature : {
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            data : []
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        
	    ]
	})
	
	
	$.get('/report/LawPersonDist/bar').done(function (data) {
		var value1max = Math.max.apply(null, data.value1);
		var value1min = Math.min.apply(null, data.value1);

		BarChart.setOption({
		    xAxis: [
		        {
		            data: data.name
		        }
		    ],
		    series: [
		    	{
		            name:'执法人员',
		            type:'bar',
		            data:data.value1,
		            markPoint : {
		                data : [
		                    {type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name: '平均值'}
		                ]
		            }
		        },
		        {
		            name:'监督人员',
		            type:'bar',
		            data:data.value2,
		            markPoint : {
		                data : [
		                	{type : 'max', name: '最大值'},
		                    {type : 'min', name: '最小值'}
		                ]
		            },
		            markLine : {
		                data : [
		                    {type : 'average', name : '平均值'}
		                ]
		            }
		        }
		    ]
		});
		
		var PersonType = [];
		$.each(data.personLawType,function(index,item){
			PersonType[index] = item.name;
		})
		
		PieChart.setOption({
			legend : {
				data:PersonType,
			},
			series : [
		        {
		            name: '全市执法人员执法类型占比',
		            type: 'pie',
		            data:data.personLawType,
		        }
		    ]
		})
	});
	
	PieChart.setOption({
		title : {
	        text: '全市执法人员执法类型占比',
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: '5%',
	        top:'5%',
	        data: []
	    },
	    series : [
	        {
	            name: '全市执法人员占比',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	});
});
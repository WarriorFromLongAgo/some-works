/**
 * 
 */

	var ajaytj = echarts.init(document.getElementById('ajaytj'),'echartstheme');
	var ajqytj=echarts.init(document.getElementById('ajqytj'),'echartstheme');
$(function() {
	
	
	$.ajax({
	    type : "GET",
	    url : "/report/law/politicaloutlook",
	    success : function(result) {
	    	//console.log(result);
	    	ajaytj.setOption({
	    		yAxis : [
	    	        {
	    	            data : result.area
	    	        }
	    	    ],
	    	    series : [
	    	        {
	    	            data: result.num
	    	        }
	    	    ]
	    	})
	    }
	});
	
	
	var yoption = {
			title: {  
                text: '呼和浩特市政治面貌统计图',  
                textStyle : {
                	//演示
    				//color : '#7B8CA5'
                }                   
            },
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
	//	    	y2：-140,
		        left: '5%',
		        right: '4%',
		        bottom: '10%',
		        containLabel: true
		    },
		    
		    yAxis : [
		        {
		            type : 'category',
		            data : [],
		            axisTick: {
		                alignWithLabel: true,
		            }
		        }
		    ],
		    xAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'专项及日常检查统计',
		            type:'bar',
		            barWidth: '60%',
		            data:[]
		        }
		    ]
		 
		};
	ajaytj.setOption(yoption);
	ajaytj.on('click',function(params){
		showPie(params.name);
	});
	
	showPie("");
	
function showPie(areaName){
		$.get('/report/law/GetPolitical?areaName='+areaName).done(function (data) {
			console.log(data);
			ajqytj.setOption({
				legend: {
			        data: data.legend
			    },
		        series : 
		        	[{
		        		type: 'pie',
			            data: data.series
			        }]
		    });
		});
	};

	
	qoption = {
			title: {  
				x:'center',
                text: '区域职位比例图',  
                textStyle : {
                	//演示
    				//color : '#7B8CA5'
                }                   
            },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 orient: 'vertical',
		         left: 'left',
		        data: []
		    },
		    series : [
		        {
		            name: '执法检查统计及对比',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		};
	
	ajqytj.setOption(qoption);
	
});
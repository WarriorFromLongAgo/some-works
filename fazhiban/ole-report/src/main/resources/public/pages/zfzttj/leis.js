/**
 * 
 */

	var ajaytj = echarts.init(document.getElementById('ajaytj'),'echartstheme');
	var ajqytj=echarts.init(document.getElementById('ajqytj'),'echartstheme');
	$(function() {
	var yoption = {
		    color: ['#3398DB'],
		    title:{
		    	text : '专项及日常检查统计情况图',
		    	x:'center',
		    },
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
		    
		    xAxis : [
		        {
		            type : 'category',
		            data : [],
		            axisLabel:{  
	                    interval:0,//横轴信息全部显示  
	                    rotate:-30,//-30度角倾斜显示  
	               }, 
		            axisTick: {
		                alignWithLabel: true,
		            }
		        }
		    ],
		    yAxis : [
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
	
	
	
	function showPie(areaName){
		$.get('/report/areacheckdaily/DeptPersonCheck?areaName='+areaName).done(function (data) {
			var legend = [];
			$.each(data,function(item,index){
				legend[index] = item.deptProperty;
			})
		    // 填入数据
			ajqytj.setOption({
				title:{
					text:areaName+"检查主体类型对比图"
				},
				legend: {
			        data: legend
			    },
		        series : 
		        	[{
		        		type: 'pie',
			            data: data
			        }]
		    });
		});
	};

	$.ajax({
	    type : "GET",
	    url : "/report/areacheckdaily/AreaDailyCheck",
	    success : function(result) {
	    	showPie(result.area[0]);
	    	////console.log(result);
	    	ajaytj.setOption({
	    		xAxis : [
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
	qoption = {
			title : {
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 orient: 'vertical',
		         left: 'left',
		         top:'10%',
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
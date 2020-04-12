/**
 * 行政、授权、委托执法人员占比统计
 */

$(function() {
	//获取对象
	var zfztBar = echarts.init(document.getElementById('zfztBar'),'echartstheme');
	var zfztPie=echarts.init(document.getElementById('zfztPie'),'echartstheme');
	
	//首次加载图表友好提示
	zfztBar.showLoading({text: '正在努力的读取数据中...'});
	
	//异步柱状图数据
	$.get('/report/lawsub/getAreaAndDept').done(function (data) {
		//console.log(data);
	    // 填入数据
		zfztBar.setOption({
	        xAxis: {
	            data: data.e_area
	        },
	        series : 
	        	[{
	        		name:"区域主体数量",
		            type:'bar',
		            data: data.e_dept,
		            barWidth: '40%',
		        }]
	    });
		
		zfztBar.hideLoading();
	});


	//柱状图默认设置
	Baroption = {
			title:{
				text:"各市区执法主体数量统计图",
			},
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    xAxis : [
		        {
		        	name:"区域",
		            type : 'category',
		            data : [],
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel:{
		            	interval:0,
		            	rotate : 25,
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		        }
		    ]
		};
		zfztBar.setOption(Baroption);
		
		// 添加点击事件
		zfztBar.on('click', function(params) {
			////console.log(params);
			zfztPie.showLoading({text: '正在努力的读取数据中...'});
			showZfztPie(params.name);
		});
		
		showZfztPie("");

		
		function showZfztPie(areaName){
			////console.log(areaName);
			//异步加载数据
			$.get('/report/lawsub/getDeptAndProp?areaName='+areaName).done(function (data) {
				//console.log(data);
			    // 填入数据
				zfztPie.setOption({
					title:{
						text:data.title+"执法主体性质数量统计图"
					},
					legend: {
				        data: data.legend
				    },
			        series : 
			        	[{
			        		type: 'pie',
				            data: data.series
				        }]
			    });
				zfztPie.hideLoading();
			});
		}
		
		//饼图默认参数
		Pieoption = {
				title : {
			        x:'center'
			    },
				legend: {
			        orient: 'vertical',
			        left: 'left',
			        top:"10%",
			        data: []
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    series : [
			        {
			        	name:"执法主体数量",
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
		zfztPie.setOption(Pieoption);
		


});
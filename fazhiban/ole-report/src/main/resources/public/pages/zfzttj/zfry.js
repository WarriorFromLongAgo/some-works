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
	$.get('/report/lawperson/getLawPersonBar').done(function (data) {
		//console.log(data);
	    // 填入数据
		zfztBar.setOption({
	        xAxis: {
	            data: data.name
	        },
	        series : 
	        	[{
	        		name:"执法主体人数",
		            type:'bar',
		            data: data.count,
		            barWidth: '40%',
		        }]
	    });
		
		zfztBar.hideLoading();
	});


	//柱状图默认设置
	Baroption = {
			title: {  
				x:'center',
                text: "市区执法主体人员统计图",  
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
		    xAxis : [
		        {
		        	name:"区域",
		            type : 'category',
		            data : [],
		            axisTick: {
		                alignWithLabel: true
		            },
		            axisLabel:{
		            	interval:0,//横轴信息全部显示  
                        rotate:25,//25度角倾斜显示 
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name :'人员数量'
		        }
		    ]
		};
		zfztBar.setOption(Baroption);
		
		// 添加点击事件
		zfztBar.on('click', function(params) {
			//console.log(params);
			zfztPie.showLoading({text: '正在努力的读取数据中...'});
			showZfztPie(params.name);
		});
		
		showZfztPie("");

		
		function showZfztPie(areaName){
			//console.log(areaName);
			//异步加载数据
			$.get('/report/lawperson/getLawPersonPie?areaName='+areaName).done(function (data) {
				//console.log(data.count);
			    // 填入数据
				zfztPie.setOption({
					title:{
						text:data.title+"执法人员数量对比图"
					},
					legend: {
				        data: data.lename
				    },
			        series : 
			        	[{
			        		type: 'pie',
				            data: data.count
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
			        data: []
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    series : [
			        {
			        	name:"执法主体人员数量",
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
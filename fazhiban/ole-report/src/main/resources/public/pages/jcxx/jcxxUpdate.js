/**
 * 
 */
var PostPerson = echarts.init(document.getElementById('PostPerson'),'echartstheme');
var PostPersonBar = echarts.init(document.getElementById('PostPersonBar'),'echartstheme');
$(function() {
	
	
	//柱状图数据
	Baroption = {
			title:{
				text:"全市基础信息预警数量",
				x:"center",
			},
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    toolbox: {
	            show : true,
	            feature : {
	                mark : {show: true},
	                magicType: {show: true, type: ['line', 'bar']},
	                restore : {show: true},
	                saveAsImage : {show: true}
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
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'预警数量',
		            type:'bar',
		            barWidth: '60%',
		            data:[]
		        }
		    ]
		};
	
	$.get('/report/basewraninfo/warnType').done(function(data){
		PostPersonBar.setOption({
			//x轴
			xAxis : [
		        {
		          data : data.xAxis
		        }
		    ],
		    //y轴
		    series : [
		        {
		            type:'bar',
		            data:data.series
		        }
		    ]
		});
		
	});

	PostPersonBar.setOption(Baroption);
	//柱状图点击事件
	PostPersonBar.on('click',function(params){
		var warnType = params.name;
		getWarnType(warnType);
	});
	
	getWarnType("执法人员预警");
	
	function getWarnType(warnType){
		PostPersonBar.showLoading({text: 'loading',
			  color: '#0066FF',
			  textColor: '#fff',
			  maskColor: 'rgba(0, 18, 99, 0.7)',
			  zlevel: 0});
		$.get('/report/basewraninfo/warnTypeDetail?warnType='+warnType).done(function (data) {
	    	PostPersonBar.hideLoading();
	    	var legend = [];
	        var selectedArr = {};
	    	$.each(data.warnTypeDetail,function (index,item) {
				legend[index] = item.name;
	            selectedArr[item.name] = item.value > 0;
	        })
			PostPerson.setOption({
	            title : {
	                text: '全市各部门预警数量占比',
	                subtext: '新增/修改/删除预警占比',
	                x:'center'
	            },
	            tooltip : {
	                trigger: 'item',
	                formatter: "{a} <br/>{b} : {c} ({d}%)"
	            },
	            legend: {
	                type: 'scroll',
	                orient: 'vertical',
	                left: 10,
	                top: 20,
	                bottom: 20,
	                data: legend,
	                selected: selectedArr
	            },
	            series : [
	                {
	                    name: '预警类型',
	                    type: 'pie',
	                    radius : '50%',
	                    center: ['65%', '50%'],
	                    data: data.warnTypeDetail,
	                    itemStyle: {
	                        emphasis: {
	                            shadowBlur: 10,
	                            shadowOffsetX: 0,
	                            shadowColor: 'rgba(0, 0, 0, 0.5)'
	                        }
	                    }
	                }
	            ]
			})
		});
	}
});
/**
 * 
 */
var caseSimp = echarts.init(document.getElementById('caseSimp'),'echartstheme');
$(function() {
	
	var year = new Array();
	var timeYear;
	//获取图表基础设置的数据
	$.get('/report/lawCaseSimp/getDataCount').done(function(data){
		console.log(data);
		$.each(data,function(index,item){
			year[index] = {value:item.caseTime,symbol: 'diamond',symbolSize: 18};
			
		});
		get(year[4]);
		
		caseSimp.setOption({
			baseOption:{
				timeline:{
					data:year
				}
			},
		});
		
	});
	/**
	 * 图表基础设置
	 */
	var coption={
			baseOption: {
				timeline:{
			        data:[
//			        	{value:'2017'},
//			        	{value:'2018'}
			        	],
				    axisType:'category',
			        label :{formatter: function(s){return (new Date(s)).getFullYear();}},
			        autoPlay : true,//自动播放按钮
			        playInterval : 2000,//自动播放的时间间隔
				},
				grid: {
		            top: 80,
		            bottom: 100
		        },
				legend: {data:['简易流程','一般流程','重大案件']},
				yAxis:{type : 'value'},
				xAxis:{data:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']},
				tooltip: {
					trigger: 'axis',
		            axisPointer: {
		                type: 'shadow',
		                label: {
		                    show: true
		                }
		            }
		        },
				series: [
					// 系列一的一些其他配置
					{type: 'bar',stack:'统计',name: '简易流程'},
					// 系列二的一些其他配置
	                {type: 'bar',stack:'统计',name: '一般流程'},
	                // 系列三的一些其他配置
	                {type: 'bar',stack:'统计',name: '重大案件'}
	            ]
			},
			options:[
				{series :[{data:[]},{data:[]},{data:[]}]},
				{series :[{data:[]},{data:[]},{data:[]}]},
				{series :[{data:[]},{data:[]},{data:[]}]},
				{series :[{data:[]},{data:[]},{data:[]}]},
				{series :[{data:[]},{data:[]},{data:[]}]}
			]
		};
	
	//加载图表基础设置
	caseSimp.setOption(coption,true);
	
	/**
	 * 设置时间轴事件
	 */
	caseSimp.on("timelinechanged",function(params){
		
		//console.log(params);
		var option=caseSimp.getOption();
		timeYear = option.timeline[0].data[params.currentIndex].value;
		get(timeYear);
	});
	
	
	/**
	 * 请求后台数据以及设置图表数据
	 */
	function get(timeYear){
		//console.log(timeYear);
		var dataMap1 = new Array(0,0,0,0,0,0,0,0,0,0,0,0);
		var dataMap2 = new Array(0,0,0,0,0,0,0,0,0,0,0,0);
		var dataMap3 = new Array(0,0,0,0,0,0,0,0,0,0,0,0);
		$.get('/report/lawCaseSimp/getData?timeYear=' + timeYear).done(function(data){
			//console.log(data);
			var year1 = [];
			var yearSet = [];
			var yearSet1 = [];
			$.each(data,function(index,item){
				//console.log(item.dataValue-1);
				if(item.flowType == 1){
					dataMap1[item.dataValue-1] = item.count;
				}else if(item.flowType == 2){
					dataMap2[item.dataValue-1] = item.count;
				}else if(item.flowType == 3){
					dataMap3[item.dataValue-1] = item.count;
				};

			});
			caseSimp.setOption({
				options:[
					{
						title: {text: '案件简易程度统计分析'},
						series :
							[{type: 'bar',data:dataMap1},{type: 'bar',data:dataMap2},{type: 'bar',data:dataMap3}]
					},
					{
						title: {text: '案件简易程度统计分析'},
						series :
							[{type: 'bar',data:dataMap1},{type: 'bar',data:dataMap2},{type: 'bar',data:dataMap3}]
					},
					{
						title: {text: '案件简易程度统计分析'},
						series :
							[{type: 'bar',data:dataMap1},{type: 'bar',data:dataMap2},{type: 'bar',data:dataMap3}]
					},
					{
						title: {text:  '案件简易程度统计分析'},
						series :
							[{type: 'bar',data:dataMap1},{type: 'bar',data:dataMap2},{type: 'bar',data:dataMap3}]
					},
					{
						title: {text: '案件简易程度统计分析'},
						series :
							[{type: 'bar',data:dataMap1},{type: 'bar',data:dataMap2},{type: 'bar',data:dataMap3}]
					}
				]	
			});
		});
	}
	
});
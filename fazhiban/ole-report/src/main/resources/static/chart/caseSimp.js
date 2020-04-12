/**
 * 
 */
var caseSimp = echarts.init(document.getElementById('caseSimp'));;
$(function() {
	
	var year = new Array();
	var timeYear;

	

	/**
	 * 图表基础设置
	 */
	var coption={
			baseOption: {
				color : [ '#074dff','#009aff','#00d8ff'],
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
				legend: {
					textStyle : {
			        	color : '#7B8CA5'
					},
					data:['简易流程','一般流程','重大案件']
				},
				yAxis:{
					type : 'value',
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
				xAxis:{
					data:['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月'],
					axisLine: {
			        	lineStyle: {
			        		color: '#7B8CA5'
			        	}
			        },
				},
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
				{series :[{data:["391","133","51","47","52","47","50","50","45","42","44","0",]},{data:["52","47","50","50","45","391","133","51","47","42","44","0",]},{data:["50","50","133","51","47","42","44","52","47","45","391","0"]}]},
				{series :[{data:["391","133","51","47","52","47","50","50","45","42","44","0",]},{data:["52","47","50","50","45","391","133","51","47","42","44","0"]},{data:["50","50","133","51","47","42","44","52","47","45","391","0"]}]},
				{series :[{data:["391","133","51","47","52","47","50","50","45","42","44","0",]},{data:["52","47","50","50","45","391","133","51","47","42","44","0"]},{data:["50","50","133","51","47","42","44","52","47","45","391","0"]}]},
				{series :[{data:["391","133","51","47","52","47","50","50","45","42","44","0",]},{data:["52","47","50","50","45","391","133","51","47","42","44","0"]},{data:["50","50","133","51","47","42","44","52","47","45","391","0"]}]},
				{series :[{data:["391","133","51","47","52","47","50","50","45","42","44","0",]},{data:["52","47","50","50","45","391","133","51","47","42","44","0"]},{data:["50","50","133","51","47","42","44","52","47","45","391","0"]}]}
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
	});
	
	caseSimp.setOption({
		baseOption:{
			timeline:{
				data:['2013','2014','2015','2016','2017','2018']
			}
		},
	});
	
	
});
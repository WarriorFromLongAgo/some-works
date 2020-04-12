/**
 * 
 */
var PostPerson = echarts.init(document.getElementById('PostPerson'),'echartstheme');
var PostPersonBar = echarts.init(document.getElementById('PostPersonBar'),'echartstheme');
$(function() {
	
	
	//柱状图数据
	Baroption = {
			title:{
				text:"各区县执法人员数量",
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
		            name:'执法人员数量',
		            type:'bar',
		            barWidth: '60%',
		            data:[]
		        }
		    ]
		};
	
	$.get('/report/post/getAllAreaPostPersonnel').done(function(data){
		//console.log(data);
		PostPersonBar.setOption({
			xAxis : [
		        {
		          data : data.xAxis
		        }
		    ],
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
		//alert(params.name)
		var AreaName = params.name;
		getAreaName(AreaName);
	});
	
	getAreaName("呼和浩特市本级");
	
	function getAreaName(AreaName){
		PostPersonBar.showLoading({text: 'loading',
			  color: '#0066FF',
			  textColor: '#fff',
			  maskColor: 'rgba(0, 18, 99, 0.7)',
			  zlevel: 0});
		//console.log(AreaName)
		$.get('/report/post/getPostPersonnel?AreaName=' + AreaName).done(function(data){
			console.log(data)
			PostPersonBar.hideLoading();
			//饼图数据
			doption = {
					title:{
						text:AreaName+"执法人员性质占比",
						x:"center",
					},
				    tooltip: {
				        trigger: 'item',
				        formatter: "{a} <br/>{b}: {c} ({d}%)"
				    },
				    legend: {
				        orient: 'vertical',
				        x: 'left',
				        data:data.legend
				    },
				    series: [
				        {
				            name:'行政执法主体数',
				            type:'pie',
				            selectedMode: 'single',
				            radius: [0, '30%'],
				            label: {
				                normal: {
				                    position: 'inner'
				                }
				            },
				            labelLine: {
				                normal: {
				                    show: false
				                }
				            },
				            data:data.deptSeries
				        },
				        {
				            name:'行政执法人数',
				            type:'pie',
				            radius: ['40%', '55%'],
				            label: {
				                normal: {
				                    formatter: '{a|{a}}{abg|}\n{hr|}\n  {b|{b}：}{c}  {per|{d}%}  ',
				                    backgroundColor: '#eee',
				                    borderColor: '#aaa',
				                    borderWidth: 1,
				                    borderRadius: 4,
				                    rich: {
				                        a: {
				                            color: '#999',
				                            lineHeight: 22,
				                            align: 'center'
				                        },
				                        hr: {
				                            borderColor: '#aaa',
				                            width: '100%',
				                            borderWidth: 0.5,
				                            height: 0
				                        },
				                        b: {
				                            fontSize: 16,
				                            lineHeight: 33
				                        },
				                        per: {
				                            color: '#eee',
				                            backgroundColor: '#334455',
				                            padding: [2, 4],
				                            borderRadius: 2
				                        }
				                    }
				                }
				            },
				            data:data.series
				        }
				    ]
				};
			PostPerson.setOption(doption);
			
			
		});
	}

	
});
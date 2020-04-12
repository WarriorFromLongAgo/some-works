$(document).ready(function(){
	//初始化对象
	var zztchart = echarts.init(document.getElementById('area1'),'echartstheme'); 
    var btchart = echarts.init(document.getElementById('area2'),'echartstheme'); 
    var btchart2 = echarts.init(document.getElementById('area6'),'echartstheme'); 
    var dtchart = echarts.init(document.getElementById('area3'),'echartstheme'); 
    var nsjzchart = echarts.init(document.getElementById('area4'),'echartstheme'); 
    var flsjzchart = echarts.init(document.getElementById('area5'),'echartstheme'); 
    
    //初始化先清除本地缓存；
    localStorage.clear();
    
    //时间轴参数
    var categoryArr  = [];
    var yearArr = [];
    
    
    var dataRangeMax = 0;
    
    //var dataUrlArr = ['areaCaseList','lawPersonNum','ReconsiderationCasesNum','personLawAvgNum'];
    var dataUrlArr = ['areaCaseList'];
 /*   var model = layer.load(1, {
    	  shade: [0.8,'#fff'] //0.1透明度的白色背景
    	});*/

    //初始化时间轴数据
    $.ajax({
        type: "GET",
        url: "/report/areamap/areaMapInit",
        async : false,
        success: function(data){
        	categoryArr = data.category;
        	yearArr = data.year;
         },
         error : function(data){
        	 layer.msg(data);
         }
    });
    //默认柱状图参数
	zztoption = {
			backgroundColor:'rgba(128, 128, 128, 0.1)' ,//rgba设置透明度0.1
			title: {  
                text: '案件数量：',  
                textStyle : {
                	//演示
                    //color : '#7B8CA5'
                }                   
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {
					type : 'shadow',
					label : {
						show : true,
						backgroundColor : '#333'
					}
				}
            },
            grid: [
    	        {width:'90%'},
    	    ],
			xAxis: [
                {
	    	        type: 'category',
	    	        data: [],
	        	    axisLabel:{
		            	interval:0,
		            	rotate : 25,
	        	    },
	        	    axisLine : {
						lineStyle : {
							color : '#7B8CA5'
						}
					},
	        	    
                },
            ],
            yAxis: [
            	{
            		type: 'value',
                    boundaryGap: [0, 0.01],
                    axisLabel: {
                        textStyle: {
                            fontSize: 14,
                            color: '#7B8CA5'
                        }
                    },
                    axisLine : {
						lineStyle : {
							color : '#7B8CA5'
						}
					},
					splitLine: {
			            lineStyle: {
			                color: '#666'
			            }
			        }
                },
            ],
            series : [
            	{
                type: 'bar',
                data: [],
                label: {
                    normal: {
                        show: true,
                        position: 'inside'
                    }
                },
                itemStyle : {
					normal : {
						barBorderRadius : 5,
						color : new echarts.graphic.LinearGradient(2,
								0, 0, 1, [ {
									offset : 0,
									color : '#0099FF'
								}, {
									offset : 1,
									color : '#0033FF'
								} ])
					}
				},
            },
            ]
	}
	
	
	//柱状图默认值
	btoption = {
			backgroundColor:'rgba(128, 128, 128, 0.1)' ,//rgba设置透明度0.1
			title: {
		        text: '分析',
		        textStyle : {
		        	//演示
                    //color: '#7B8CA5'
				},
		        left: '1%'
		    },
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            lineStyle: {
		                color: '#57617B'
		            }
		        }
		    },
		    legend: {
		    	type: 'scroll',
		        orient: 'horizontal',
		        bottom:'1%',
		        icon: 'rect',
		        itemWidth: 14,
		        itemHeight: 5,
		        itemGap: 13,
		        data: [],
		        right: '1%',
		        textStyle: {
		            fontSize: 12,
		            color: '#F1F1F3'
		        },
		        pageIconColor : '#fff',
				pageIconInactiveColor : "#fff",
				pageTextStyle:{
					color : "#fff",
				},
		    },
		    grid: {
		        left: '3%',
		        right: '0%',
		        bottom: '13%',
		        containLabel: true
		    },
		    xAxis: [{
		        type: 'category',
		        boundaryGap: false,
		        axisLine: {
		            lineStyle: {
		                color: '#57617B'
		            }
		        },
		        data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
		    }],
		    yAxis: [{
		        type: 'value',
		        axisTick: {
		            show: false
		        },
		        axisLine: {
		            lineStyle: {
		                color: '#57617B'
		            }
		        },
		        axisLabel: {
		            margin: 10,
		            textStyle: {
		                fontSize: 14,
		                color : '#57617B'
		            }
		        },
		        splitLine: {
		            lineStyle: {
		               color: '#57617B'
		            }
		        }
		    },
		    ]
		};
	

			//饼图默认值
			bt2option = {
					backgroundColor:'rgba(128, 128, 128, 0.1)' ,//rgba设置透明度0.1
				    title : {
				        text: '区域案件数量占比',
				        	textStyle : {
				        		//演示
			                    //color : '#7B8CA5'
							},
				    },
				    //color : [ '#85b6b2', '#6d4f8d', '#cd5e7e', '#e38980', '#f7db88','#660066','#9900CC','#996633','#99FF33','#99FFFF' ],
				    tooltip : {
						show : true,
						trigger : 'item',
						formatter : "{b}: {c} ({d}%)"
					},
				    legend: {
				    	type: 'scroll',
				        orient: 'horizontal',
				        bottom:'1%',
				        textStyle : {
							color : '#7B8CA5'
						},
						pageIconColor : '#fff',
						pageIconInactiveColor : "#fff",
						pageTextStyle:{
							color : "#fff",
						},
				        data: []
				    },
				    series : [
				        {
				            name: '每平方公里平均案件数：',
				            type: 'pie',
				            radius : '65%',
				            center: ['50%', '50%'],
				            data:[],
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '14',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
				        }
				    ]
				};
	
	//获取地图数据参数
	$.get('/report/json/hhht.json', function (yCjson) {
        echarts.registerMap('呼和浩特市', yCjson);
        dtchart.setOption(dtoption);
	});
	dtoption = {
		animation: false,
	    backgroundColor: new echarts.graphic.RadialGradient(0.5, 0.5, 0.4, [{
	        offset: 0,
	        color: 'rgba(0,55,205,0.8)'
	    }, {
	        offset: 1,
	        color: 'rgba(0,0,55,0.1)'
	    }]),
		title: {  
            text: '呼和浩特市分布图',  
        }, 
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	return params.seriesName + '   '+ params.data.count+'<br/>' + params.data.name + '   每平方公里数量：' + params.data.value;
            }
        },
        visualMap: {
            min: 0,
            max: dataRangeMax,
            calculable: true,
            color: ['#d94e5d','#eac736','#00CC66','#50a3ba'],
            text: ['高', '低'],
            textStyle: {
                color: '#fff'
            }
        },
        series:[  
            {  
                name:'案件数量',  
                type:'map',  
                map:'呼和浩特市', 
                zoom : 1.2,
                roam:true,
                label: {
                    normal: {
                        formatter: '{b}',
                        position: 'center',
                        destance : '50',
                        show: true,
                        color : '#fff'
                    },
                    emphasis: {
                        show: true,
                        color : '#fff'
                    }
                },
                data:[  
                    {name:'玉泉区',value:Math.round(Math.random()*500)},
                    {name:'新城区',value:Math.round(Math.random()*500)},
                    {name:'赛罕区',value:Math.round(Math.random()*500)},
                    {name:'回民区',value:Math.round(Math.random()*500)},
                    {name:'武川县',value:Math.round(Math.random()*500)},
                    {name:'土默特左旗',value:Math.round(Math.random()*500)},
                    {name:'托克托县',value:Math.round(Math.random()*500)},
                    {name:'和林格尔县',value:Math.round(Math.random()*500)},
                    {name:'清水河县',value:Math.round(Math.random()*500)}
                ],
            }  
        ],	
	};
	dtchart.on("click",function(param){
    });
	
	//年份时间轴数据
	var nsjzoption={
		    timeline:{
		        data: yearArr,
		        axisType : 'category',
		        playInterval:'3000',
		        autoPlay : true,
		        //loop:false,
		        //currentIndex : 4,//默认年份下标
		        //orient : 'vertical',
		        //left : '10%',
		        label : {
		            formatter : function(s) {
		                return s.slice(0, 4);
		                },
		            normal :{
		            	color : '#fff',
		            	fontsize : 14,
		            },
		            emphasis :{
		            	color : '#fff',
		            	fontsize : 14,
			    	},
		        },
	            lineStyle:{
			    	color: '#fff',
			    	type : 'dotted',
			    },
			    itemStyle:{
			    	normal :{
			    		color : '#fff'
			    	},
			    	emphasis :{
			    		color : '#fff'
			    	},
			    	
			    },
			    checkpointStyle:{
			    	symbol : 'roundRect',
			    	color : '#0066FF',
			    	borderColor :'rgba(0,204,255,0.5)'
			    },
			    controlStyle:{
			    	normal :{
			    		color:'#fff',
			    		borderColor:'#fff',
			    	},
			    	emphasis :{
			    		color : '#fff',
			    		borderColor:'#fff',
			    	},
			    }
		    },
		    
		};
	//自动播放或点击年份时间轴查询图表数据
	nsjzchart.on("timelinechanged",function(param){
		var year = yearArr[param.currentIndex];
		var categoryIndex = getCategoryName(flsjzchart,categoryArr);
        getAreaDailyFun(categoryIndex,year);
    });  
        
	//分类时间轴数据
	var flsjzoption={
		    timeline:{
		    	show:false,
		        data:categoryArr,
		        axisType : 'category',
		        playInterval:'3000',
		        label : {
		            formatter : function(s) {
		                return s.slice(0, 4);
		                },
		            normal :{
		            	color : '#fff',
		            	fontsize : 14,
		            },
		            emphasis :{
		            	color : '#fff',
		            	fontsize : 14,
			    	},
		        },
	            lineStyle:{
			    	color: '#fff',
			    	type : 'dotted',
			    },
			    itemStyle:{
			    	normal :{
			    		color : '#fff'
			    	},
			    	emphasis :{
			    		color : '#fff'
			    	},
			    	
			    },
			    checkpointStyle:{
			    	symbol : 'roundRect',
			    	color : '#0066FF',
			    	borderColor :'rgba(0,204,255,0.5)'
			    },
			    controlStyle:{
			    	normal :{
			    		color:'#fff',
			    		borderColor:'#fff',
			    	},
			    	emphasis :{
			    		color : '#fff',
			    		borderColor:'#fff',
			    	},
			    }
		    },
		};
	
	
	//自动播放点击分类时间轴查询图表数据
	flsjzchart.on("timelinechanged",function(param){
		var year = getCategoryName(nsjzchart,yearArr);;
		var categoryIndex = param.currentIndex;
		getAreaDailyFun(categoryIndex,yearArr[year]);
    });
	
	//设置初始数据
	zztchart.setOption(zztoption);
	btchart.setOption(btoption);
	btchart2.setOption(bt2option);
	dtchart.setOption(dtoption);
	nsjzchart.setOption(nsjzoption);
    flsjzchart.setOption(flsjzoption);
    
    //获取时间轴的当前数据
    //getCategoryName(flsjzchart,categoryArr);
    //getCategoryName(nsjzchart,yearArr);
    //获取某一个时间轴的当前所在index对应的名称；
	function getCategoryName(chartObject,data){
		var chartOption = chartObject.getOption();
		var timeIndex = chartOption.timeline[0].currentIndex;
		var timeName = data[chartOption.timeline[0].currentIndex];
		//console.log(timeName);//option_fl.timeline[0].currentIndex
		return timeIndex;
	}
	
	//面积图样式
	var rgbaArr = [];
	rgbaArr = ["0,136,212","137,189,27","219,50,51","0,255,102","0,255,255","255,255,255","102,204,204","102,255,51","153,51,153","153,204,51","153,255,51","204,255,255"];

	getAreaDailyFun(0,2018);
	//封装查询方法
	function getAreaDailyFun(categoryIndex,year){
		dataRangeMax = 0;
		var data = get(dataUrlArr[categoryIndex]+year,1000*60*60);//过期时间为1小时;
		if(data !=null) {  
			//取消模态框
			//layer.close(model);
			
			//----------------------------
			//var data = JSON.parse(localStorage[dataUrlArr[categoryIndex]+year]);
			
			//var data = get(dataUrlArr[categoryIndex]+year,1000*60);//过期时间为1小时;
        	
        	//柱状图参数
            var areaNameArr = [];
        	var areaCaseArr = [];
        	//饼图参数
        	var areaAVGCaseArr = [];
        	$("#fl_box").empty();
        	
    		$.each(data.resultList,function(index,item){
    			if(item.value>dataRangeMax){
        			dataRangeMax = item.value;
    			}
        		areaNameArr[index] = item.name;
        		areaCaseArr[index] = item.count;
        		areaAVGCaseArr[index] = item.avgCase;
        		var temp = '<li><a>'+item.name+'</a><a>'+item.count+'</a></li>'
        		$("#fl_box").append(temp);
        	})
        	
        	var seriesArr = [];

        	var dataArr = data.countByAreaName;

        	if(categoryIndex==0){
        		for(var index = 0 ; index < 10; index++){
        			seriesArr.push({
        				name :areaNameArr[index],
        				type: 'line',
        				smooth: true,
        				lineStyle: {normal: {width: 1}},
        				areaStyle: {
        		            normal: {
        		                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
        		                    offset: 0,
        		                    color: 'rgba('+rgbaArr[index]+', 0.5)'
        		                }, {
        		                    offset: 0.8,
        		                    color: 'rgba('+rgbaArr[index]+', 0.1)'
        		                }], false),
        		                shadowColor: 'rgba(0, 0, 0, 0.1)',
        		                shadowBlur: 10
        		            }
        		        },
        				itemStyle: {normal: {color: 'rgb('+rgbaArr[index]+')'}},
        				data: dataArr[index]
        			});
        		}
        	}
        	//设置柱状图数据
        	zztchart.setOption({
        		title :{
        			text : year+"地区"+categoryArr[categoryIndex],
        		},
    	        xAxis: {
    	            data: areaNameArr
    	        },
    	        series : 
    	        	[{
    	        		name:"地区案件总数",
    		            type:'bar',
    		            data: areaCaseArr,
    		        }]
    	    });
        	//设置柱状图
        	btchart.setOption({
        		title :{
        			text : year+"年地区的每月案件数量",
        		},
        		legend :{
        			data : areaNameArr
        		},
        		xAxis :{
        			data : ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        		},
        		series : seriesArr
    	    });
        	//设置饼图
        	btchart2.setOption({
        		title :{
        			text : year+"年地区"+categoryArr[categoryIndex]+"占比"
        		},
    			legend: {
    		        data: areaNameArr
    		    },
    	        series : 
    	        	[{
    	        		name: '访问来源',
    	        		type: 'pie',
    		            data: data.resultList,
    		            labelLine : {
    						normal : {
    							show : false
    						}
    					},
    					label : {
    						normal : {
    							position : 'inner'
    						}
    					},
    		        }]
    	    });
        	//设置地图
        	dtchart.setOption({
        		visualMap:{
        			max : dataRangeMax,
        		},
        		series :[{
        			type:'map',  
        			map:'呼和浩特市', 
        			data : data.resultList
        		}]
        	})
    		
			//---------------------------
		}else{
			$.ajax({
		        type: "GET",
		        cache:true,
		        url: "/report/areamap/"+dataUrlArr[categoryIndex]+"?year="+year,
		        success: function(data){
		        	//layer.close(model);
		        	
		        	var obj = eval(data); //后台给返的数据  
		            var localStorgeName = dataUrlArr[categoryIndex]+year;
		            set(localStorgeName,obj);
		            //console.log(dataUrlArr[categoryIndex]+year);
		        	//console.log(localStorage[dataUrlArr[categoryIndex]+year]);
		        	
		        	//柱状图参数
		            var areaNameArr = [];
		        	var areaCaseArr = [];
		        	//饼图参数
		        	var areaAVGCaseArr = [];
		        	$("#fl_box").empty();
		        	if(data.resultList.length == 0){
		        		layer.msg("没有找到你想要的数据,请更改后重新尝试...");
		        	}else{
		        		$.each(data.resultList,function(index,item){
		        			if(item.value>dataRangeMax){
			        			dataRangeMax = item.value;
		        			}
			        		areaNameArr[index] = item.name;
			        		areaCaseArr[index] = item.count;
			        		areaAVGCaseArr[index] = item.avgCase;
			        		var temp = '<li><a>'+item.name+'</a><a>'+item.count+'</a></li>'
			        		$("#fl_box").append(temp);
			        	})
		        	}
		        	
		        	//console.log(dataRangeMax);

		        	var seriesArr = [];

		        	var dataArr = data.countByAreaName;

		        	if(categoryIndex==0){
		        		for(var index = 0 ; index < 10; index++){
		        			seriesArr.push({
		        				name :areaNameArr[index],
		        				type: 'line',
		        				smooth: true,
		        				lineStyle: {normal: {width: 1}},
		        				areaStyle: {
		        		            normal: {
		        		                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
		        		                    offset: 0,
		        		                    color: 'rgba('+rgbaArr[index]+', 0.5)'
		        		                }, {
		        		                    offset: 0.8,
		        		                    color: 'rgba('+rgbaArr[index]+', 0.1)'
		        		                }], false),
		        		                shadowColor: 'rgba(0, 0, 0, 0.1)',
		        		                shadowBlur: 10
		        		            }
		        		        },
		        				itemStyle: {normal: {color: 'rgb('+rgbaArr[index]+')'}},
		        				data: dataArr[index]
		        			});
		        		}
		        	}
		        	//设置柱状图数据
		        	zztchart.setOption({
		        		title :{
		        			text : year+"年地区"+categoryArr[categoryIndex],
		        		},
		    	        xAxis: {
		    	            data: areaNameArr
		    	        },
		    	        series : 
		    	        	[{
		    	        		name:"地区案件总数",
		    		            type:'bar',
		    		            data: areaCaseArr,
		    		        }]
		    	    });
		        	//设置柱状图
		        	btchart.setOption({
		        		title :{
		        			text : year+"年地区每月案件数量",
		        		},
		        		legend :{
		        			data : areaNameArr
		        		},
		        		xAxis :{
		        			data : ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
		        		},
		        		series : seriesArr
		    	    });
		        	//设置饼图
		        	btchart2.setOption({
		        		title :{
		        			text : year+"年地区"+categoryArr[categoryIndex]+"占比"
		        		},
		    			legend: {
		    		        data: areaNameArr
		    		    },
		    	        series : 
		    	        	[{
		    	        		name: '访问来源',
		    	        		type: 'pie',
		    		            data: data.resultList,
		    		            labelLine : {
		    						normal : {
		    							show : false
		    						}
		    					},
		    					label : {
		    						normal : {
		    							position : 'inner'
		    						}
		    					},
		    		        }]
		    	    });
		        	//设置地图
		        	dtchart.setOption({
		        		visualMap:{
		        			max : dataRangeMax,
		        		},
		        		series :[{
		        			type:'map',  
		        			map:'呼和浩特市', 
		        			data : data.resultList
		        		}]
		        	})
		    		
		        	
		         },
		         error : function (data){
		        	 layer.msg(data.code+":查询失败，请重新查询");
		         }
		    });
		}
		
	}
	
	
	
	//封装过期控制代码
    function set(key,value){
        var curTime = new Date().getTime();
        localStorage.setItem(key,JSON.stringify({data:value,time:curTime}));
    }
    function get(key,exp){
        var data = localStorage.getItem(key);
        if(data == null){
        	return null;
        }
        var dataObj = JSON.parse(data);
        if (new Date().getTime() - dataObj.time>exp) {
        	localStorage.removeItem(key);
            //console.log('信息已过期');
            return null;
            //alert("信息已过期")
        }else{
            //console.log("data="+dataObj.data);
            //console.log(JSON.parse(dataObj.data));
            var dataObjDatatoJson = dataObj.data;
            return dataObjDatatoJson;
        }
    }
});
        
        

$(document).ready(function(){
	//初始化对象
	var zztchart = echarts.init(document.getElementById('area1'),'echartstheme'); 
    var btchart = echarts.init(document.getElementById('area2'),'echartstheme'); 
    var dtchart = echarts.init(document.getElementById('area3'),'echartstheme'); 
    var nsjzchart = echarts.init(document.getElementById('area4'),'echartstheme'); 
    var flsjzchart = echarts.init(document.getElementById('area5'),'echartstheme'); 
    
    //时间轴参数
    var categoryArr  = [];
    var yearArr = [];
    
    var dataUrlArr = ['areaCaseList','lawPersonNum','ReconsiderationCasesNum','personLawAvgNum'];
    
    var index = layer.load(1, {
    	  shade: [0.1,'#000'] //0.1透明度的白色背景
    	});

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
			title: {  
                text: '案件数量：',  
                x:'center'  
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
			xAxis: [
                {
	    	        type: 'category',
	    	        data: [],
	        	    axisLabel:{
		            	interval:0,
		            	rotate : 25,
	        	    }
                },
            ],
            yAxis: [
            	{
            		type: 'value',
                    boundaryGap: [0, 0.01]
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
            },
            ]
	}
	
	
	//饼图默认值
	btoption = {
		    title : {
		        text: '每平方公里案件数量',
		        x:'center'
		    },
		    label: {
                normal: {
                    position: 'inner'
                }
            },
		    legend: {
		        orient: 'vertical',
		        left: '10%',
		        data: []
		    },
		    series : [
		        {
		            name: '每平方公里平均案件数：',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data:[],
		            itemStyle:{ 
                        normal:{ 
                            label:{ 
                               show: true, 
                               formatter: '{b} : {c} ({d}%)' 
                            }, 
                            labelLine :{show:true}
                        } 
                    } 
		        }
		    ]
		};
	
	
	//获取地图数据参数
	$.get('/report/json/hhht.json', function (yCjson) {
        echarts.registerMap('呼和浩特市', yCjson);
        dtchart.setOption(dtoption);
	});
	dtoption = {
		title: {  
            text: '城市分布图',  
            x:'center'  
        }, 
        tooltip : {
            trigger: 'item',
            formatter: function(params){
            	//console.log(params);
            	return params.seriesName + '<br/>' + params.data.name + '   数量：' + params.data.value;
            }//'{b}'
        },
        dataRange:{  
        	show : false,
            min:0,  
            max:100,  
            text:['高','低'],  
            realtime:true,  
            calculable:true,  
            color:['orangered','yellow','green']  
        }, 
        visualMap: {
            min: 0,
            max: 100,
            left: 'left',
            top: 'bottom',
            text: ['高', '低'],
            calculable: true,
            inRange: {
                color: ['#ffffff', '#E0DAFF', '#ADBFFF', '#9CB4FF', '#6A9DFF', '#3889FF']
            }
        },
        series:[  
            {  
                name:'犯罪数量',  
                type:'map',  
                map:'呼和浩特市',  
                mapLocation:{  
                    y:60  
                },  
                itemSytle:{  
                    emphasis:{label:{show:false}}  
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
                label: {
                    normal: {
                        show: true,
                    }
                },
            }  
        ],	
	};
	dtchart.on("click",function(param){
        //console.log(param.name);
    });
	
	//年份时间轴数据
	var nsjzoption={
		    timeline:{
		        data: yearArr,
		        axisType : 'category',
		        autoPlay : true,
		        currentIndex : 4,//默认年份下标
		        //orient : 'vertical',
		        //left : '10%',
		        /*label : {
		            formatter : function(s) {
		                return s.slice(0, 4);
		                }
		            },*/
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
		        data:categoryArr,
		        axisType : 'category',
		    },
		};
	
	
	//自动播放点击分类时间轴查询图表数据
	flsjzchart.on("timelinechanged",function(param){
		var year = getCategoryName(nsjzchart,yearArr);;
		var categoryIndex = param.currentIndex;
		getAreaDailyFun(categoryIndex,yearArr[year]);
		//console.log(categoryIndex);
    });
	
	//设置初始数据
	zztchart.setOption(zztoption);
	btchart.setOption(btoption);
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
		//console.log(timeIndex);
		var timeName = data[chartOption.timeline[0].currentIndex];
		//console.log(timeName);//option_fl.timeline[0].currentIndex
		return timeIndex;
	}
	

	getAreaDailyFun(0,2018);
	//封装查询方法
	function getAreaDailyFun(categoryIndex,year){
		$.ajax({
	        type: "GET",
	        url: "/report/areamap/"+dataUrlArr[categoryIndex]+"?year="+year,
	        success: function(data){
	        	layer.close(index);
	        	//console.log(data);
	        	//console.log(data);
	        	//柱状图参数
	            var areaNameArr = [];
	        	var areaCaseArr = [];
	        	//饼图参数
	        	var areaAVGCaseArr = [];
	        	if(data.resultList.length == 0){
	        		layer.msg("没有找到你想要的数据,请更改后重新尝试...");
	        	}else{
	        		$.each(data.resultList,function(index,item){
		        		areaNameArr[index] = item.name;
		        		areaCaseArr[index] = item.count;
		        		areaAVGCaseArr[index] = item.avgCase;
		        	})
	        	}
	        	        	
	        	//设置柱状图数据
	        	zztchart.setOption({
	        		title :{
	        			text : year+categoryArr[categoryIndex]
	        		},
	    	        xAxis: {
	    	            data: areaNameArr
	    	        },
	    	        series : 
	    	        	[{
	    	        		name:"区域案件总数",
	    		            type:'bar',
	    		            data: areaCaseArr,
	    		            barWidth: '40%',
	    		        }]
	    	    });
	        	//设置饼图
	        	btchart.setOption({
	        		title :{
	        			text : year+"年各地区的"+categoryArr[categoryIndex]
	        		},
					legend: {
				        data: areaNameArr
				    },
			        series : 
			        	[{
			        		name: '访问来源',
			        		type: 'pie',
				            data: data.resultList
				        }]
			    });
	        	//设置地图
	        	dtchart.setOption({
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
	
});
        
        

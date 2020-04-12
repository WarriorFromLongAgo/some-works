/**
 * 
 */
var Department = echarts.init(document.getElementById('Department'),'echartstheme');
var Monthly = echarts.init(document.getElementById('Monthly'),'echartstheme');
$(function() {
	
	
	
	Department.showLoading({text: 'loading',
		  color: '#0066FF',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	
	Monthly.showLoading({text: 'loading',
		  color: '#0066FF',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	$.get('/report/checkChart/getDayAndSpecial').done(function (data) {
		Department.hideLoading();
		//console.log(data)
	    doption = {
	    	title:{
	    		text : '各部门检查总数',
	    		x:'center',
	    	},
	        tooltip : {
	            trigger: 'axis',
	            axisPointer: {
	                type: 'shadow',
	                label: {
	                    show: true
	                }
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
	        calculable : true,
	        legend: {
	            data:['Growth', '检查数量总数'],
	            itemGap: 5
	        },
	        grid: {
	            top: '12%',
	            left: '4%',
	            right: '7%',
	            containLabel: true
	        },
	        xAxis: [
	            {
	                type : 'category',
	                data : data[0].name,
	                axisLabel:{
		            	interval:0,//横轴信息全部显示  
	                    rotate:25,//25度角倾斜显示 
		            }
	            },
	            
	        ],
	        yAxis: [
	            {
	                type : 'value',
	                axisLabel: {
	                    formatter: function (a) {
	                        a = +a;
	                        return isFinite(a)
	                            ? echarts.format.addCommas(+a)
	                            : '';
	                    }
	                }
	            }
	        ],
	        dataZoom: [
	            {
	                show: true,
	                start: 0,
	                end: 5
	            },
	            {
	                type: 'inside',
	                start: 94,
	                end: 100
	            },
	            {
	                show: true,
	                yAxisIndex: 0,
	                filterMode: 'empty',
	                width: 30,
	                height: '80%',
	                showDataShadow: false,
	                left: '93%'
	            }
	        ],
	        series : [
	            {
	                name: '日常检查数量',
	                type: 'bar',
	                stack: '检查',
	                data: data[0].DayValue
	            },
	            {
	                name: '专项检查数量',
	                type: 'bar',
	                stack: '检查',
	                data: data[0].SpecialValue
	            }
	        ]
	    };
	    Department.setOption(doption);
	    
	   

	    getDay("");
		Department.on('click',function(params){
		    var AreaName = params.name;
		    getDay(AreaName);
		});
		
	    function getDay(AreaName){
	    	$.get('/report/checkChart/getMonthly?AreaName=' + AreaName).done(function(data){
	    		Monthly.hideLoading();
	    		//console.log(data[0].series1[0]);
	    	    /**
	    		 * 右边图表基础设置
	    		 */
	    		coption = {
	    			    tooltip : {
	    			        trigger: 'axis',
	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	    			        }
	    			    },
	    			    legend: {
	    			        data:['日常检查','专项检查']
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
	    			            data : ['一月','二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月']
	    			        }
	    			    ],
	    			    yAxis : [
	    			        {
	    			            type : 'value'
	    			        }
	    			    ],
	    			    title: {text: AreaName},
	    			    series : [
	    			       
	    			        {
	    			            name:'日常检查',
	    			            type:'bar',
	    			            stack: '广告',
	    			            data:data[0].series1[0]
	    			        },
	    			        {
	    			            name:'专项检查',
	    			            type:'bar',
	    			            stack: '广告',
	    			            data:data[0].series2[0]
	    			        }
	    			    ]
	    			};
	    		
	    		//加载图表基础设置
	    		Monthly.setOption(coption,true);
	    		
	    	});//get结束
	    }
	});
	
});
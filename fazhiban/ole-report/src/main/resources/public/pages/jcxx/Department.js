/**
 * 部门权责分析
 */
var Department = echarts.init(document.getElementById('Department'),'echartstheme');
//var timeLine = echarts.init(document.getElementById('timeLine'));
$(function() {
	
	
	
	Department.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	$.get('/report/power/getDepartmentCount').done(function (data) {
		Department.hideLoading();
		//console.log(data)
	    doption = {
	    	title:{
	    		text : '各执法主体权责数量',
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
	            data:['Growth', '权责数量'],
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
	                data : data.xAxis,
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
	                end: 20
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
	                name: '权责数量',
	                type: 'bar',
	                data: data.series
	            }
	        ]
	    };

	    Department.setOption(doption);

	});

});
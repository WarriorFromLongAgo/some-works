/**
 * 异常告警统计
 * 田俊文
 */
var WranBarChart = echarts.init(document.getElementById('WranBarChart'),'echartstheme');
var WranPieChart = echarts.init(document.getElementById('WranPieChart'),'echartstheme');
$(function() {
	
	WranBarChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	WranPieChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	//异步柱状图数据
	$.get('/report/casewran/bar').done(function (data) {
		
		WranBarChart.hideLoading();
		WranPieChart.hideLoading();
		var lengedArr = [];
		var pielengedArr = [];
		var valueArr = [];
		$.each(data,function(index,item){
				lengedArr[index] = item.name;
				valueArr[index] = item.value;		    
		})
		/*var piedata = data;
		piedata.splice(piedata.length-1,1);
		var pielengedArr = lengedArr;
		pielengedArr.splice(pielengedArr.length-1,1);*/
		WranBarChart.setOption({
            title : {
                text: '异常告警统计',
            },
            color: ['#3398DB'],
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
                right: '10%',
                bottom: '10%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : lengedArr,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            dataZoom: [
                {
                    type: 'slider',
                    xAxisIndex: 0,
                    filterMode: 'empty'
                },
                {
                    type: 'slider',
                    yAxisIndex: 0,
                    filterMode: 'empty'
                },
                {
                    type: 'inside',
                    xAxisIndex: 0,
                    filterMode: 'empty'
                },
                {
                    type: 'inside',
                    yAxisIndex: 0,
                    filterMode: 'empty'
                }
            ],
            series : [
                {
                    name:'案件数量',
                    type:'bar',
                    barWidth: '60%',
                    data:valueArr
                }
            ]
		})

        WranPieChart.setOption({
            title : {
                text: '异常告警统计',
                x : 'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
            	type: 'scroll',
                orient: 'vertical',
                left: 'left',
                data: lengedArr,
                pageIconColor : '#7B8CA5',
				pageIconInactiveColor : "#7B8CA5",
				pageTextStyle:{
					color : "#7B8CA5",
				},
                top:'10%',
                bottom:'10%',
            },
            series : [
                {
                    name: '案件类型',
                    type: 'pie',
                    radius : '50%',
                    center: ['55%', '50%'],
                    data:data,
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

});
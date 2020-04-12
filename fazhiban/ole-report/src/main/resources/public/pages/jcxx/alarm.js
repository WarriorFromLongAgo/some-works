/**
 * 异常告警统计
 * jwen
 */
//本市红黄蓝综合
var cityChart = echarts.init(document.getElementById('cityChart'),'echartstheme');
//部门红黄蓝色占比
var deptChart = echarts.init(document.getElementById('deptChart'),'echartstheme');
$(function() {

    cityChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
    deptChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	$.get('/report/deptwraninfo/dept').done(function (data) {
		var name = [];
		var value = [];
		$.each(data,function (index,item) {
			name[index] = item.name;
			value[index] = item.value;
        })
        deptChart.hideLoading();
	    doption = {
	    	title :{
	    		text : '各部门预警总量'
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
	            data:['Growth', '预警总量'],
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
	                data : name,
	                axisLabel:{
		            	interval:0,//横轴信息全部显示  
	                    rotate:25,//25度角倾斜显示 
		            }
	            },
	            
	        ],
	        yAxis: [
	            {
	                type : 'value',
	                name : '数量',
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
	                end: 10
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
	                name: '预警总量',
	                type: 'bar',
	                data: value
	            }
	        ]
	    };

        deptChart.setOption(doption);

	});



    $.get('/report/deptwraninfo/area').done(function (data) {
        cityChart.hideLoading();
    	var legend = [];
        var selectedArr = {};
    	$.each(data.dept,function (index,item) {
			legend[index] = item.name;
            selectedArr[item.name] = item.value > 0;
        })
		cityChart.setOption({
            title : {
                text: '全市各部门预警数量占比',
                subtext: '红色/黄色/蓝色预警占比',
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
                    name: '部门名称',
                    type: 'pie',
                    radius : '50%',
                    center: ['65%', '50%'],
                    data: data.dept,
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


	})
});
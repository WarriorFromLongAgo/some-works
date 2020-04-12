/**
 * 案件处理统计图
 * 田俊文
 */
var PieChart = echarts.init(document.getElementById('PieChart'),'echartstheme');
$(function() {

	//填入这折线图基础数据
    PieChart.setOption({
        title : {
            text: '处罚人员类别占比分析',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: []
        },
        series : [
            {
                name: '类别占比',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:[
                ],
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
	
	
	
	//异步柱状图数据
	$.get('/report/CasePersonType/bar').done(function (data) {
		var legend = [];
		$.each(data,function (index,item) {
			legend[index] = item.name;
        })
        PieChart.setOption({
            legend:{
				data :legend
			},
            series :{
				data : data
			}
		});
	});
	

});
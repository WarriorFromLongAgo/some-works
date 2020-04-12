/**
 * 行政处罚案件数
 */
var CasePersonChart = echarts.init(document.getElementById('CasePersonChart'),'echartstheme');
$(function() {
	
	CasePersonChart.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	var myDate = new Date();
	var year = myDate.getFullYear();
	get(year);

function get(year){
	$.get('/report/deptperson/deptpersonlawcount?year='+year).done(function (data) {
		var name = [];
		var value = [];
		var legendArr  = [];
		legendArr = data.lenged;
		var ok = [];
		//console.log(data.dept);
		$.each(data.dept,function(index,item){
			name[index] = item.name;
			value[index] = item.value;			
		})
		//console.log(ok);
		CasePersonChart.hideLoading();
        doption = {
        		
        	title:{
        		text : "各执法部门人均执法量"
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
                data:['人均执法量'],
                itemGap: 5
            },
            grid: {
                top: '12%',
                left: '4%',
                right: '7%',
                bottom : '20%',
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
                    name: '人均执法量',
                    type: 'bar',
                    data: value
                }
            ]
        };
	    CasePersonChart.setOption(doption);

	});
}
});
/**
 * 
 */
var PostPerson = echarts.init(document.getElementById('PostPerson'),'echartstheme');
var PostPersonBar = echarts.init(document.getElementById('PostPersonBar'),'echartstheme');
$(function() {
			
	$.get('/report/basics/getCheckedItem').done(function (data) {
		//console.log(data);
		var legend = [];
	    var selectedArr = {};
		$.each(data,function (index,item) {
			legend[index] = item.name;
	        selectedArr[item.name] = item.value > 0;
	    })
	    //console.log(legend);
		PostPerson.setOption({
	        title : {
	            text: '各部门检查转处罚的数量',
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
	                data: data,
	                itemStyle: {
	                    emphasis: {
	                        shadowBlur: 10,
	                        shadowOffsetX: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.8)'
	                    }
	                }
	            }
	        ]
		})
	});	
	

	
	$.get('/report/basics/getChecked').done(function (data) {
//		console.log(data[0].SpecialValue);
//		console.log(data[0].DayValue);
//		console.log(data[0].count);
		
		//////////////////////////////////
		
		var mainData = [];
		mainData.push({
		    name: '日常检查',
		    value: data[0].DayValue,
		    prevalue: data[0].count,
		    hismax: data[0].count
		});
		mainData.push({
		    name: '专项检查',
		    value: data[0].SpecialValue,
		    prevalue: data[0].count,
		    hismax: data[0].count
		});


		function createSeries(mainData) {
		    var result = [];
		    var insideLabel = {
		        normal: {
		            position: 'center',
		            formatter: function(params) {
		                if (params.name == "other")
		                    return "";
		                return params.value + '\n' + params.name;
		            },
		            textStyle: {
		                fontStyle: 'normal',
		                fontWeight: 'normal',
		                fontSize: 18
		            }
		        }
		    };
		    var outsideLabel = {
		        normal: {
		            show: false
		        }
		    };
		    var itemOthers = {
		        normal: {
		            color: '#ccc'
		        }
		    };
		    for (var i = 0; i < mainData.length; i++) {
		    		var increase = mainData[i].value > mainData[i].prevalue;
		        result.push({
		        	name:'检查数量',
		            type: 'pie',
		            center: [i * 50 + 20 + '%', '45%'],
		            radius: ['20%', '35%'],
		            label: insideLabel,
		            data: [{
		                name: 'other',
		                value: mainData[i].hismax - mainData[i].value,
		                itemStyle: itemOthers
		            }, {
		                name: mainData[i].name,
		                value: mainData[i].value,
		                prevalue: mainData[i].prevalue
		            }],
		            markPoint: {
		            data: [{
		                        symbol: 'triangle',
		                        symbolSize: 15,
		                        symbolRotate: increase ? 0 : 180,
		                        itemStyle: {
		                            normal: {
		                                color: increase ? 'red' : 'green'
		                            }
		                        },
		                        name: mainData[i].name,
		                        value: ((mainData[i].value / mainData[i].prevalue) * 100).toFixed(2) + '%',
		                        x: PostPersonBar.getWidth() * (i + 0.5) /2 - 80,
		                        y: PostPersonBar.getHeight() * 0.8 + 15,
		                        label: {
		                            normal: {
		                                show: true,
		                                position: 'right',
		                                formatter: function(params) {
		                                    return params.value;
		                                },
		                                textStyle: {
		                                    color: increase ? 'red' : 'green'
		                                }
		                            }
		                        },
		                    }
		            ]
		        }
		        });
		    }
		    return result;
		}
		option = {
			
			tooltip:{formatter: "{a} <br/>{b} : {c} ({d}%)"  },
			legend: {top: 20,data:['日常检查','专项检查']},
		    title: {
		        text: '日常检查和专项检查对比',
		        x: 'left'
		    },
		    toolbox: {
		        show: true,
		        feature: {
		            restore: {
		                show: true
		            },
		            saveAsImage: {
		                show: true
		            }
		        }
		    },
		    series: createSeries(mainData)
		}
		PostPersonBar.setOption(option);
		//////////////////////////////////
		
	});
	
});
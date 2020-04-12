/**
 * 
 */
var PostPerson = echarts.init(document.getElementById('PostPerson'),'echartstheme');
$(function() {
	
	
	$.get('/report/lawperson/getItemLawCount').done(function(data){
		//console.log(data);
		//////////////
		var mainData = [];
		
		for(var i =1; i < data.length; i++){
			
			if(i < data.length){
				mainData.push({
					name: data[i].enumDesc,
				    value: data[i].itemCount,
				    prevalue: data[0].count,
				    hismax: data[0].count
				});
			}
			
		}

		function createSeries(mainData) {
		    var result = [];
		    var insideLabel = {
		        normal: {
		            position: 'center',
		            formatter: function(params) {
		                if (params.name == "其他类型总和")
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
		            type: 'pie',
		            center: [i * 20 + 10 + '%', '40%'],
		            radius: ['22%', '32%'],
		            label: insideLabel,
		            data: [{
		                name: '其他类型总和',
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
		                        x: PostPerson.getWidth() * (i + 0.4) /5 - 15,
		                        y: PostPerson.getHeight() * 0.8 + 15,
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
		    tooltip:{formatter: "{a} <br/>{b} : {c}"  },
		    title: {
		        text: '     法律法规分类统计',
		        x: 'left'
		    },
		    series: createSeries(mainData)
		}

		PostPerson.setOption(option);
		
	});

	
});
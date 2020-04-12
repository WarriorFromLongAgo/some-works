/**
 * 执法人员统计 田俊文
 */
var aeraChart = echarts.init(document.getElementById('aeraChart'),'echartstheme');
var ageChart = echarts.init(document.getElementById('ageChart'),'echartstheme');
var positionChart = echarts.init(document.getElementById('positionChart'),'echartstheme');
var eduChart = echarts.init(document.getElementById('eduChart'),'echartstheme');
var nationChart = echarts.init(document.getElementById('nationChart'),'echartstheme');
var outlookChart = echarts.init(document.getElementById('outlookChart'),'echartstheme');
$(function() {
	// 区域数据
	$.get('/report/enforceperson/areaPersonCount').done(
			function(data) {
				var legendArr = [];
				$.each(data,function(index,item){
					legendArr[index] = item.name;
				})
				aeraChart.setOption({
					title : {
						text : '各区域执法人员数量',
						textStyle : {
							//演示
							//color : '#7B8CA5'
						}
					},
					tooltip : {
						trigger : 'axis',
						axisPointer : {
							type : 'shadow',
							label : {
								show : true,
								backgroundColor : '#333'
							}
						}
					},
					
					xAxis : {
						data :legendArr,
						axisLine : {
							lineStyle : {
								color : '#7B8CA5'
							}
						}
					},
					yAxis : {
						splitLine : {
							show : false
						},
						axisLine : {
							lineStyle : {
								color : '#7B8CA5'
							}
						}
					},
					series : [ {
						name : '执法人员数量',
						type : 'bar',
						barWidth : '30%',
						itemStyle : {
							normal : {
								barBorderRadius : 5,
								color : new echarts.graphic.LinearGradient(2,
										0, 0, 1, [ {
											offset : 0,
											color : '#00e4ff'
										}, {
											offset : 1,
											color : '#222eda'
										} ])
							}
						},
						data : data,
						markPoint : {
			                data : [
			                    {type : 'max', name: '最大值'},
			                    {type : 'min', name: '最小值'}
			                ]
			            },
			            markLine: { //平均值
			                data: [{
			                    type: 'average',
			                    name: '平均值'
			                }]
			            }
					}]
				});
			});
	
	
	

	// age Data
	var dataStyle = {
		normal : {
			label : {
				show : false
			},
			labelLine : {
				show : false
			},
			shadowBlur : 20,
			shadowColor : 'rgba(40, 40, 40, 0.5)',
		}
	};
	var placeHolderStyle = {
		normal : {
			color : 'rgba(0,0,0,0)',
			label : {
				show : false
			},
			labelLine : {
				show : false
			}
		},
		emphasis : {
			color : 'rgba(0,0,0,0)'
		}
	};
	
	var legendArr = ["1<30","31-40","41-50",">50"]
	$.get('/report/enforceperson/ageList').done(function(data) {
		var legendArr = [];
		
		$.each(data,function(index,item){
			legendArr[index] = item.name;
		})
		ageChart.setOption({
			title : {
				text : '年龄分布',
				textStyle : {
					//演示
					//color : '#7B8CA5'
				}
			},
			color : [ '#00d8ff','#009aff','#0066ff','#0033cc','#330100' ],
			tooltip : {
				show : true,
				formatter : "{a} <br/>{b} : {c} ({d}%)"
			},
			toolbox : {
				show : true,
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			legend : {
				itemGap : 12,
				top : '87%',
				data : legendArr,
				textStyle : {
					color : '#ccc'
				}
			},
			series : [ {
				name : '年龄区间：',
				type : 'pie',
				clockWise : false,
				radius : [ '20%', '30%' ],
				itemStyle : dataStyle,
				hoverAnimation : false,
				legend : {
					data : legendArr[0],
				},
				data : [
					data[0], 
					{
						value : (data[0].value)/2,
						name : 'invisible',
						itemStyle : placeHolderStyle
					}]
			}, {
				name : '年龄区间：',
				type : 'pie',
				clockWise : false,
				radius : [ '30%', '40%' ],
				itemStyle : dataStyle,
				hoverAnimation : false,
				legend : {
					data : legendArr[1],
				},
				data : [ 
					data[1], 
					{
						value : (data[1].value)/3,
						name : 'invisible',
						itemStyle : placeHolderStyle
					}]
			}, {
				name : '年龄区间：',
				type : 'pie',
				clockWise : false,
				hoverAnimation : false,
				radius : [ '40%', '50%' ],
				itemStyle : dataStyle,
				legend : {
					data : legendArr[2],
				},
				data : [ 
					data[2], 
					{
						value : (data[2].value)/4,
						name : 'invisible',
						itemStyle : placeHolderStyle
					}]
			}, {
				name : '年龄区间：',
				type : 'pie',
				clockWise : false,
				hoverAnimation : false,
				radius : [ '50%', '60%' ],
				itemStyle : dataStyle,
				legend : {
					data : legendArr[3],
				},
				data : [ 
					data[3], 
					{
						value : (data[3].value)/5,
						name : 'invisible',
						itemStyle : placeHolderStyle
					}]
			}/*, {
				name : 'Line 5',
				type : 'pie',
				clockWise : false,
				hoverAnimation : false,
				radius : [ '60%', '70%' ],
				itemStyle : dataStyle,
	
				data : [ {
					value : 30,
					name : '05'
				}, {
					value : 30,
					name : 'invisible',
					itemStyle : placeHolderStyle
				} ]
			},*/
			]
		});
	});
	$.get('/report/enforceperson/dutyPersonCount').done(function(data) {
		var legendArr = [];
		var selectedArr = {};
		$.each(data,function(index,item){
			legendArr[index] = item.name;
			selectedArr[item.name] = item.value > 100;
		})	
		positionChart.setOption({
			title : {
				text : '职位统计',
				textStyle : {
					//演示
					//color : '#7B8CA5'
				}
			},
			color : [ '#00d8ff','#009aff','#074dff','#0023cc','#2200b6' ],
			backgroundColor : 'rgba(255,255,255,0)',
			tooltip : {
				show : true,
				trigger : 'item',
				formatter : "{b}: {c} ({d}%)"
			},
			toolbox : {
				show : true,
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			legend : {
				type: 'scroll',
                //orient: 'vertical',
                bottom: 20,
				data : legendArr,
				textStyle : {
					color : '#7B8CA5'
				},
				selected: selectedArr,
				pageIconColor : '#7B8CA5',
				pageIconInactiveColor : "#7B8CA5",
				pageTextStyle:{
					color : "#7B8CA5",
				}
			},
			series : [ {
				name : '职位统计',
				type : 'pie',
				selectedMode : 'single',
				radius : [ '30%', '60%' ],
				label : {
					normal : {
						position : 'inner',
						formatter : '{d}%',
	
						textStyle : {
							color : '#7B8CA5',
							fontWeight : 'bold',
							fontSize : 12
						}
					}
				},
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
				data : data,
			} ]
		})
	});

	// deu Data
	$.get('/report/enforceperson/PersonNation').done(function(data) {
		var legendArr = [];
		
		$.each(data,function(index,item){
			legendArr[index] = item.name;
		})	
		eduChart.setOption({
			title : {
				text : '民族统计',
				textStyle : {
					//演示
					//color : '#7B8CA5'
				}
			},
			toolbox : {
				show : true,
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			legend : {
				orient : 'horizontal',
				bottom : '0%',
				data : legendArr,
				type: 'scroll',
				textStyle : {
					color : '#7B8CA5'
				},
				pageTextStyle:{
					color : "#7B8CA5",
				}
			},
			series : [ {
				label : {
					normal : {
						position : 'inner'
					}
				},
	
				type : 'pie',
				radius : '65%',
				center : [ '50%', '45%' ],
				color : [ '#00d8ff', '#009aff','#074dff', '#007eff','#330100','#6600ff','#9900ff','#ff6600','#ffcc00','#ffff33','#99cc33','#99ff99' ],
				data : data,
				itemStyle : {
					normal : {
						borderWidth : 1,
						borderColor : '#000',
					},
					emphasis : {
						color : '#0006ff',
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 10, 5, 0)'
					}
				}
			} ]
		});
	});
});

$.get('/report/enforceperson/EduList').done(function(data) {
	var legendArr = [];
	
	$.each(data,function(index,item){
		legendArr[index] = item.name;
	})	
		nationChart.setOption({
			title : {
				text : '学历统计',
				textStyle : {
					//演示
					//color : '#7B8CA5'
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			toolbox : {
				show : true,
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			legend : {
				type: 'scroll',
				orient : 'horizontal',
				bottom : '0%',
				data : legendArr,
				textStyle : {
					color : '#7B8CA5'
				},
				pageTextStyle:{
					color : "#7B8CA5",
				}
			},
			series : [ {
				label : {
					normal : {
						position : 'inner'
					}
				},
	
				type : 'pie',
				radius : '65%',
				center : [ '50%', '45%' ],
				color : [ '#00d8ff', '#009aff','#074dff', '#007eff','#330100','#6600ff','#9900ff','#ff6600','#ffcc00','#ffff33','#99cc33','#99ff99' ],
				data : data,
				itemStyle : {
					normal : {
						borderWidth : 1,
						borderColor : '#000',
					},
					emphasis : {
						color : '#0006ff',
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 10, 5, 0)'
					}
				}
			} ]
		})
})
	// outlookChart Data
	$.get('/report/enforceperson/politicalList').done(function(data) {
		var legendArr = [];
		
		$.each(data,function(index,item){
			legendArr[index] = item.name;
		})	
		outlookChart.setOption({
			title : {
				text : '政治面貌统计',
				textStyle : {
					//演示
					//color : '#7B8CA5'
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			toolbox : {
				show : true,
				feature : {
					restore : {},
					saveAsImage : {}
				}
			},
			legend : {
				orient : 'horizontal',
				bottom : '0%',
				data : legendArr,
				type: 'scroll',
				textStyle : {
					color : '#7B8CA5'
				},
				pageTextStyle:{
					color : "#7B8CA5",
				}
			},
			series : [ {
				label : {
					normal : {
						position : 'inner'
					}
				},
	
				type : 'pie',
				radius : '65%',
				center : [ '50%', '45%' ],
				color :[ '#00d8ff', '#009aff','#074dff', '#007eff','#330100','#6600ff','#9900ff','#ff6600','#ffcc00','#ffff33','#99cc33','#99ff99' ],
				data : data,
				itemStyle : {
					normal : {
						borderWidth : 1,
						borderColor : '#000',
					},
					emphasis : {
						color : '#0006ff',
						shadowBlur : 10,
						shadowOffsetX : 0,
						shadowColor : 'rgba(0, 10, 5, 0)'
					}
				}
			} ]
		})
	});
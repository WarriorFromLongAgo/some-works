/**
 * 执法人员统计 田俊文
 */
var aeraChart = echarts.init(document.getElementById('aeraChart'));
var ageChart = echarts.init(document.getElementById('ageChart'));
var positionChart = echarts.init(document.getElementById('positionChart'));
var eduChart = echarts.init(document.getElementById('eduChart'));
var nationChart = echarts.init(document.getElementById('nationChart'));
var outlookChart = echarts.init(document.getElementById('outlookChart'));
$(function() {

	// 区域数据
				aeraChart.setOption({
					title : {
						text : '各区域执法人员数量',
						textStyle : {
							color : '#7B8CA5'
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
						data : ['呼和浩特市','玉泉区','赛罕区'],
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
						data : [{name:'呼和浩特市',value:"300"},
								{name:'玉泉区',value:'245'},
								{name:'赛罕区',value:'378'}],
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
	
		ageChart.setOption({
			title : {
				text : '年龄分布',
				textStyle : {
					color : '#7B8CA5'
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
					dataView : {
						readOnly : false
					},
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
					{
						value : 500,
						name : legendArr[0],
					}, 
					{
						value : (500)/2,
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
					{
						value : 1000,
						name : legendArr[1],
					}, 
					{
						value : (1000)/2,
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
					{
						value : 1500,
						name : legendArr[2],
					}, 
					{
						value : (1500)/2,
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
					{
						value : 2000,
						name : legendArr[3],
					}, 
					{
						value : (2000)/2,
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
		})

	// 
		var legendArr = ["办事员", "监察员", "大队长", "副主任科员", "主任科员", "副大队长", "副支队长", "稽查员", "副分局长", "办公室主任", "副书记", "科员", "副调研员", "政委", "副所长", "处长", "调研员", "监理员", "中队长", "副处长", "办公室副主任", "书记", "副科长", "所长", "站长", "法制员", "副站长", "厅长", "法制办主任", "检查员", "法制办副主任", "督察员", "党组成员", "正科长", "纪检组长", "台长", "局长", "副局长", "主任", "副主任", "队长"];
		var selectedArr = {
			"中队长":false,
"主任":false,
"主任科员":true,
"书记":false,
"党组成员":false,
"副主任":false,
"副主任科员":false,
"副书记":false,
"副分局长":false,
"副处长":false,
"副大队长":false,
"副局长":true,
"副所长":false,
"副支队长":false,
"副科长":true,
"副站长":false,
"副调研员":false,
"办事员":false,
"办公室主任":false,
"办公室副主任":false,
"厅长":false,
"台长":false,
"处长":false,
"大队长":false,
"局长":false,
"所长":false,
"政委":false,
"检查员":false,
"正科长":true,
"法制办主任":false,
"法制办副主任":false,
"法制员":false,
"监察员":true,
"监理员":false,
"督察员":false,
"科员":true,
"稽查员":true,
"站长":false,
"纪检组长":false,
"调研员":false,
"队长":false,
			
		};
		positionChart.setOption({
			title : {
				text : '职位统计',
				textStyle : {
					color : '#7B8CA5'
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
					dataView : {
						readOnly : false
					},
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
				radius : [ '40%', '70%' ],
                center: ['50%', '40%'],
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
				data : [{name: "办事员", value: "10"},
{name: "监察员", value: "373"},
{name: "大队长", value: "35"},
{name: "副主任科员", value: "1"},
{name: "主任科员", value: "300"},
{name: "副大队长", value: "34"},
{name: "副支队长", value: "18"},
{name: "稽查员", value: "103"},
{name: "副分局长", value: "25"},
{name: "办公室主任", value: "16"},
{name: "副书记", value: "7"},
{name: "科员", value: "1379"},
{name: "副调研员", value: "22"},
{name: "政委", value: "3"},
{name: "副所长", value: "10"},
{name: "处长", value: "8"},
{name: "调研员", value: "8"},
{name: "监理员", value: "9"},
{name: "中队长", value: "8"},
{name: "副处长", value: "22"},
{name: "办公室副主任", value: "12"},
{name: "书记", value: "21"},
{name: "副科长", value: "290"},
{name: "所长", value: "11"},
{name: "站长", value: "15"},
{name: "法制员", value: "9"},
{name: "副站长", value: "20"},
{name: "厅长", value: "1"},
{name: "法制办主任", value: "3"},
{name: "检查员", value: "3"},
{name: "法制办副主任", value: "1"},
{name: "督察员", value: "1"},
{name: "党组成员", value: "3"},
{name: "正科长", value: "390"},
{name: "纪检组长", value: "3"},
{name: "台长", value: "1"},
{name: "局长", value: "65"},
{name: "副局长", value: "160"},
{name: "主任", value: "85"},
{name: "副主任", value: "93"},
{name: "队长", value: "5"},],
			} ]
		})
	});

	// deu Data
		eduChart.setOption({
			title : {
				text : '民族统计',
				textStyle : {
					color : '#7B8CA5'
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			legend : {
				orient : 'horizontal',
				bottom : '0%',
				data : ['汉族','蒙古族'],
				textStyle : {
					color : '#7B8CA5'
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
				data : [{name:'1',value:245},{name:'2',value:245},{name:'3',value:245},],
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

	// deu Data

		nationChart.setOption({
			title : {
				text : '学历统计',
				textStyle : {
					color : '#7B8CA5'
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			legend : {
				orient : 'horizontal',
				bottom : '0%',
				data : ['1','2','3'],
				textStyle : {
					color : '#7B8CA5'
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
				data : [{name:'1',value:245},{name:'2',value:245},{name:'3',value:245},],
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
	// outlookChart Data

		outlookChart.setOption({
			title : {
				text : '政治面貌统计',
				textStyle : {
					color : '#7B8CA5'
				}
			},
			tooltip : {
				show : true,
				formatter : "{b}: {c} ({d}%)"
			},
			legend : {
				orient : 'horizontal',
				bottom : '0%',
				data : ['1','2','3'],
				textStyle : {
					color : '#7B8CA5'
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
				data : [{name:'1',value:245},{name:'2',value:245},{name:'3',value:245},],
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
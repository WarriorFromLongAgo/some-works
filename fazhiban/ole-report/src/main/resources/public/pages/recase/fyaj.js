/**
 * 复议案件分析
 * 田俊文
 */
var charts = echarts.init(document.getElementById('charts'),'echartstheme');
$(function() {
	charts.showLoading({text: 'loading',
		  color: '#0066FF',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	$.get('/report/recase/fyaj').done(function (data) {
		charts.hideLoading();
        console.log(data.acccase);
		var pie1NameArr = [];
        var pie1ValueArr = [];
		$.each(data.person,function (index,item) {
            pie1NameArr[index] = item.name;
            pie1ValueArr[index] = item.value;
        })
	    option = {
            legend: {
                data: data.leg
            },
            xAxis: {
                type: 'category',
                data: data.monthArr
            },
            series: [{
                name: data.leg[0],
                smooth: true,
                type: 'line',
                symbolSize: 8,
                symbol: 'circle',
                data: data.v1
            }, {
                name: data.leg[1],
                smooth: true,
                type: 'line',
                symbolSize: 8,
                symbol: 'circle',
                data: data.v2
            }, {
                name: data.leg[2],
                smooth: true,
                type: 'line',
                symbolSize: 8,
                symbol: 'circle',
                data: data.v3
            },
            {
                type: 'pie',
                center: ['83%', '33%'],
                radius: ['25%', '35%'],
                label: {
                    normal: {
                        position: 'center'
                    }
                },

                data: [{
                    value: pie1ValueArr[0],
                    name: pie1NameArr[0],
                    itemStyle: {
                        normal: {
                            color: '#ffd285'
                        }
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "人员类别 <br/>{b} : {c} ({d}%)"
                    },
                    label: {
                        normal: {
                            formatter: '{d} %',
                            textStyle: {
                                color: '#ffd285',
                                fontSize: 20

                            }
                        }
                    }
                }, {
                    value: pie1ValueArr[1],
                    name: pie1NameArr[1],
                    tooltip: {
                        show: false
                    },
                    itemStyle: {
                        normal: {
                            color: '#87CEFA'
                        }
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "人员类别 <br/>{b} : {c} ({d}%)"
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#ffd285',
                            },
                            formatter: '\n申请人类别占比分析'
                        }
                    }
                }]
            },


            {
                type: 'pie',
                center: ['83%', '72%'],
                radius: ['25%', '35%'],
                label: {
                    normal: {
                        position: 'center'
                    }
                },
                data: [{
                    value: data.allacccase[0].value,
                    name: '申请复议数量',
                    itemStyle: {
                        normal: {
                            color: '#ff733f'
                        }
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "申请复议数量 <br/>{b} : {c} ({d}%)"
                    },
                    label: {
                        normal: {
                            formatter: '{d} %',
                            textStyle: {
                                color: '#ff733f',
                                fontSize: 20

                            }
                        }
                    }
                }, {
                    value: data.acccase[0].value,
                    name: data.acccase[0].name,
                    tooltip: {
                        show: false
                    },
                    itemStyle: {
                        normal: {
                            color: '#87CEFA'
                        }
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "受理数量 <br/>{b} : {c} ({d}%)"
                    },
                    label: {
                        normal: {
                            textStyle: {
                                color: '#FF4500',
                            },
                            formatter: '\n复议申请/受理'
                        }
                    }
                }]
            }]
	    };

	    charts.setOption(option);

	});

	charts.setOption({
        color: ['#ffd285', '#ff733f', '#ec4863'],

        title: [{
            text: '复议案件量分析',
            left: '1%',
            top: '6%',
        }, {
            text: '复议案件分析',
            left: '83%',
            top: '6%',
            textAlign: 'center',
        }],
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            x: 300,
            top: '7%',
            textStyle: {
                color: '#ffd285',
            },
            data: []
        },
        grid: {
            left: '1%',
            right: '35%',
            top: '16%',
            bottom: '6%',
            containLabel: true
        },
        toolbox: {
            "show": false,
            feature: {
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',

            "axisTick": {
                "show": false
            },
            boundaryGap: false,
            data: []
        },
        yAxis: {
            type: 'value'
        },

	})
});
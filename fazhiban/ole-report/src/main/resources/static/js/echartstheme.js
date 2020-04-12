(function (root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function (exports, echarts) {
    var log = function (msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }
    echarts.registerTheme('echartstheme', {
        "color": [
            "#0000ff",
            "#00ccff",
            "#00ffff",
            "#00ff00",
            "#ffe600",
            "#ffa800",
            "#ccff00",
            "#ffccff",
            "#ccffff",
            "#ccccff",
            "#3399cc",
            "#9999cc",
            "#333333",
            "#eb6100",
            "#f39800",
            "#fcc800",
            "#fff100",
            "#cfdb00",
            "#8fc31f",
            "#22ac38",
            "#009944",
            "#009b6b",
            "#009e96",
            "#00a0c1",
            "#00a0e9",
            "#0086d1",
            "#0068b7",
            "#00479d",
            "#601986",
            "#920783",
            "#be0081",
            "#e4007f",
            "#e5004f"
        ],
        "backgroundColor": "rgba(0,0,0,0)",
        "textStyle": {},
        "title": {
            "textStyle": {
                "color": "#ffffff"
            },
            "subtextStyle": {
                "color": "#a3b9ea"
            }
        },
        "line": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false
        },
        "radar": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false
        },
        "bar": {
            "itemStyle": {
                "normal": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#eeeeee"
                },
                "emphasis": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#eeeeee"
                }
            }
        },
        "pie": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "scatter": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "boxplot": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "parallel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "sankey": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "funnel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "gauge": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            }
        },
        "candlestick": {
            "itemStyle": {
                "normal": {
                    "color": "#00ccff",
                    "color0": "transparent",
                    "borderColor": "#00ff00",
                    "borderColor0": "#ffe600",
                    "borderWidth": "2"
                }
            }
        },
        "graph": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#eeeeee"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "1",
                    "color": "#eeeeee"
                }
            },
            "symbolSize": "8",
            "symbol": "emptyCircle",
            "smooth": false,
            "color": [
                "#0000ff",
                "#00ccff",
                "#00ffff",
                "#00ff00",
                "#ffe600",
                "#ffa800",
                "#ccff00",
                "#ffccff",
                "#ccffff",
                "#ccccff",
                "#3399cc",
                "#9999cc",
                "#333333",
                "#eb6100",
                "#f39800",
                "#fcc800",
                "#fff100",
                "#cfdb00",
                "#8fc31f",
                "#22ac38",
                "#009944",
                "#009b6b",
                "#009e96",
                "#00a0c1",
                "#00a0e9",
                "#0086d1",
                "#0068b7",
                "#00479d",
                "#601986",
                "#920783",
                "#be0081",
                "#e4007f",
                "#e5004f"
            ],
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        },
        "map": {
            "itemStyle": {
                "normal": {
                    "areaColor": "#0000ff",
                    "borderColor": "#00ffff",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "areaColor": "#ffc700",
                    "borderColor": "#fff500",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        },
        "geo": {
            "itemStyle": {
                "normal": {
                    "areaColor": "#0000ff",
                    "borderColor": "#00ffff",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "areaColor": "#ffc700",
                    "borderColor": "#fff500",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        },
        "categoryAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#eeeeee"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#a3b9ea"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#ffffff"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "valueAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#eeeeee"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#a3b9ea"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#ffffff"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "logAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#eeeeee"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#a3b9ea"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#ffffff"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "timeAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#eeeeee"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#a3b9ea"
                }
            },
            "splitLine": {
                "show": true,
                "lineStyle": {
                    "color": [
                        "#ffffff"
                    ]
                }
            },
            "splitArea": {
                "show": false,
                "areaStyle": {
                    "color": [
                        "rgba(250,250,250,0.05)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "toolbox": {
            "iconStyle": {
                "normal": {
                    "borderColor": "#ffffff"
                },
                "emphasis": {
                    "borderColor": "#ffffff"
                }
            }
        },
        "legend": {
            "textStyle": {
                "color": "#ffffff"
            }
        },
        "tooltip": {
            "axisPointer": {
                "lineStyle": {
                    "color": "#eeeeee",
                    "width": 1
                },
                "crossStyle": {
                    "color": "#eeeeee",
                    "width": 1
                }
            }
        },
        "timeline": {
            "lineStyle": {
                "color": "#ffffff",
                "width": 1
            },
            "itemStyle": {
                "normal": {
                    "color": "#ffffff",
                    "borderWidth": 1
                },
                "emphasis": {
                    "color": "#00ffde"
                }
            },
            "controlStyle": {
                "normal": {
                    "color": "#0038ff",
                    "borderColor": "#00a3ff",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "color": "#0038ff",
                    "borderColor": "#00a3ff",
                    "borderWidth": 0.5
                }
            },
            "checkpointStyle": {
                "color": "#3fb1e3",
                "borderColor": "rgba(63,177,227,0.15)"
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        },
        "visualMap": {
            "color": [
                "#2a99c9",
                "#afe8ff"
            ]
        },
        "dataZoom": {
            "backgroundColor": "rgba(255,255,255,0)",
            "dataBackgroundColor": "rgba(0,203,247,1)",
            "fillerColor": "rgba(114,230,212,0.25)",
            "handleColor": "#ffffff",
            "handleSize": "100%",
            "textStyle": {
                "color": "#ffffff"
            }
        },
        "markPoint": {
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#ffffff"
                    }
                }
            }
        }
    });
}));

/**
 *
 */
var ajaytj = echarts.init(document.getElementById('ajaytj'),'echartstheme');
var ajqytj=echarts.init(document.getElementById('ajqytj'),'echartstheme');
var attr = new Array();
var attrs = new Array();
var zfzt = new Array();
var zfzts = new Array();
$(function() {
	ajaytj.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
	ajqytj.showLoading({text: 'loading',
		  color: '#c23531',
		  textColor: '#fff',
		  maskColor: 'rgba(0, 18, 99, 0.7)',
		  zlevel: 0});
    //执法人员
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/report/index/zfryfbqk",
        success: function (data) {
        	ajaytj.hideLoading();
            for(var i =0;i < data.length;i++){
                attr[i] = data[i].name;
                attrs[i] = data[i].cnumber;
                //alert(attr[i]);
            }
            ajaytj.setOption(yoption);
        }
    });
    //执法主体
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/report/index/zfztfbqk",
        success: function (data) {
        	ajqytj.hideLoading();
            for(var i =0;i < data.length;i++){
                zfzt[i] = data[i].name;
                zfzts[i] = data[i].cnumber;
                //alert(attr[i]);
            }
            ajqytj.setOption(qoption);
        }
    });
    yoption = {
    		title:{
    			text:"执法人员分布情况",
    		},
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '13%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : attr,
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel:{
                    interval:0,
                    rotate:45,//倾斜度 -90 至 90 默认为0
                    margin:10,
                    textStyle:{
                        fontWeight:"bolder",
                    }
                },

            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'人员数量',
                type:'bar',
                barWidth: '60%',
                data:attrs
            }
        ]
    };


    qoption = {
    		title:{
    			text:"执法主体分布情况",
    		},
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '13%',
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : zfzt,
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel:{
                    interval:0,
                    rotate:45,//倾斜度 -90 至 90 默认为0
                    margin:10,
                    textStyle:{
                        fontWeight:"bolder",
                    }
                },
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'主体数量',
                type:'bar',
                barWidth: '60%',
                data: zfzts
            }
        ]
    };

});
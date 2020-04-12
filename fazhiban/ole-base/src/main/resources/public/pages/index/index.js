/**
 * 
 */
var ajaytj = echarts.init(document.getElementById('ajaytj'));
var ajqytj=echarts.init(document.getElementById('ajqytj'));
var attr = new Array();
var attrs = new Array();
var zfzt = new Array();
var zfzts = new Array();
$(function() {
    //执法人员
    $.ajax({
        type: "post",
        dataType: "json",
        url: "/base/index/zfryfbqk",
        success: function (data) {
            for(var i =0;i < data.length;i++){
            	if(localStorage.getItem("lang") && localStorage.getItem("lang")=='mn'){
            		attr[i] = data[i].mgl_name;
            	}else{
            		attr[i] = data[i].name;
            	}
                
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
        url: "/base/index/zfztfbqk",
        success: function (data) {
            for(var i =0;i < data.length;i++){
            	if(localStorage.getItem("lang") && localStorage.getItem("lang")=='mn'){
            		zfzt[i] = data[i].mgl_name;
            	}else{
            		zfzt[i] = data[i].name;
            	}
                zfzts[i] = data[i].cnumber;
            }
            ajqytj.setOption(qoption);
        }
    });
yoption = {
    color: ['#3398DB'],
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
//                rotate:45,//倾斜度 -90 至 90 默认为0
                margin:10,
                textStyle:{
                    fontWeight:"bolder",
                    color:"#000000",
                    fontSize: localStorage.getItem("lang")=='mn'?18:10,
                    fontFamily: localStorage.getItem("lang")=='mn'?"OrhonChaganTig":""
                },
                formatter: function(value)  
				{  
					return value.split(" ").reverse().join("\n");  
				},
				rotate:localStorage.getItem("lang")=='mn'? 270 : 45,
//				textStyle: {
//					// color: '#fff',
//					fontFamily: self.i18n('Microsoft YaHei')
//				}
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
	    color: ['#3398DB'],
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
                    rotate:localStorage.getItem("lang")=='mn'? 270 : 45,//倾斜度 -90 至 90 默认为0
                    margin:10,
                    textStyle:{
                        fontWeight:"bolder",
                        color:"#000000",
                        fontSize: localStorage.getItem("lang")=='mn'?18:10,
                        fontFamily: localStorage.getItem("lang")=='mn'?"OrhonChaganTig":""
                    },
                    formatter: function(value)  
					{  
						return value.split(" ").reverse().join("\n");  
					},
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
	            data:zfzts
	        }
	    ]
	};

});
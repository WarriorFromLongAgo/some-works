/**
 * 
 */

	var ajaytj = echarts.init(document.getElementById('ajaytj'),'echartstheme');
$(function() {
	
	
	$.ajax({
	    type : "GET",
	    url : "/report/law/reportage",
	    success : function(result) {
	    	//console.log(result);
	    	ajaytj.setOption({
	    		xAxis : [
	    	        {
	    	            data : result.area
	    	        }
	    	    ],
	    	    series : [
	    	        {
	    	            data: result.num
	    	        }
	    	    ]
	    	})
	    }
	});
	
	
	var yoption = {
			backgroundColor:'rgba(128, 128, 128, 0.1)' ,//rgba设置透明度0.1
			title: {  
                text: '呼和浩特市执法人员年龄分布统计图',  
                textStyle : {
                	//演示
    				//color : '#7B8CA5'
                }                   
            },
		    color: ['#3398DB'],
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
	//	    	y2：-140,
		        left: '5%',
		        right: '4%',
		        bottom: '20%',
		        containLabel: true
		    },
		    
		    xAxis : [
		        {
		            type : 'category',
		            data : [],
//		            2.。
		            axisTick: {
		                alignWithLabel: true,
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            name:'专项及日常检查统计',
		            type:'bar',
		            barWidth: '60%',
		            data:[]
		        }
		    ]
		 
		};
	ajaytj.setOption(yoption);

});
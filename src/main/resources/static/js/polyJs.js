$(document).ready(()=>{
	console.log("Ready!");
	
	var path = window.location.pathname;
	var gid = path.slice(path.indexOf("/", 1) + 1, path.length);
	
	$.ajax({
		url: "/poly/getGraph?gid=" + gid,
		method: "get",
		success: (res)=>{
			obj = JSON.parse(res);
			
			if (obj.xVals.length == 0) {
				console.log("got here");
				return ;
			}
			
			var data = [];
			for (let i = 0; i < obj.xVals.length; i++) {
				let temp = [];
				temp.push(obj.xVals[i]);
				temp.push(obj.yVals[i]);
				data.push(temp);
			}
			
			var myChart = echarts.init(document.getElementById('main'));
			
			var myRegression = ecStat.regression('polynomial', data, 3);

			myRegression.points.sort(function(a, b) {
			    return a[0] - b[0];
			});

			option = {

			    tooltip: {
			        trigger: 'axis',
			        axisPointer: {
			            type: 'cross'
			        }
			    },
			    title: {
			        text: obj.title,
			        subtext: obj.description,
			        left: 'center',
			        top: 16
			    },
			    xAxis: {
			        type: 'value',
			        splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        },
			        splitNumber: 20
			    },
			    yAxis: {
			        type: 'value',
			        min: -40,
			        splitLine: {
			            lineStyle: {
			                type: 'dashed'
			            }
			        }
			    },
			    grid: {
			        top: 90
			    },
			    series: [{
			        name: 'scatter',
			        type: 'scatter',
			        label: {
			            emphasis: {
			                show: true,
			                position: 'right',
			                textStyle: {
			                    color: 'blue',
			                    fontSize: 16
			                }
			            }
			        },
			        data: data
			    }, {
			        name: 'line',
			        type: 'line',
			        smooth: true,
			        showSymbol: false,
			        data: myRegression.points,
			        markPoint: {
			            itemStyle: {
			                normal: {
			                    color: 'transparent'
			                }
			            },
			            label: {
			                normal: {
			                    show: true,
			                    position: 'left',
			                    formatter: myRegression.expression,
			                    textStyle: {
			                        color: '#333',
			                        fontSize: 14
			                    }
			                }
			            },
			            data: [{
			                coord: myRegression.points[myRegression.points.length - 1]
			            }]
			        }
			    }]
			};
			
			myChart.setOption(option);
		}
	});
});

//$("#button").on("click", ()=>{
//$.ajax({
//    url: "/clicked",
//    method: "get",
//    data: "data",
//    success: (res)=>{
//    	obj = JSON.parse(res);
//    	console.log(obj);
//    	
//    	$('#main').addClass('_medium')
//    	
//        var myChart = echarts.init(document.getElementById('main'));
//
//        var option = {
//                title: {
//                    text: obj.title
//                },
//                tooltip: {},
//                legend: {
//                    data:[obj.data]
//                },
//                xAxis: {
//                    data: obj.xAxis
//                },
//                yAxis: {},
//                series: [{
//                    name: obj.data,
//                    type: 'bar',
//                    data: obj.yAxis
//                }]
//            };
//
//        myChart.setOption(option);
//    }
//});
//});

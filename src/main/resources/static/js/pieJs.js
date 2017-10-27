$(document).ready(()=>{
	console.log("Ready!");
	
	var path = window.location.pathname;
	var gid = path.slice(path.indexOf("/", 1) + 1, path.length);
	
	$.ajax({
		url: "/pie/getGraph?gid=" + gid,
		method: "get",
		success: (res)=>{
			obj = JSON.parse(res);
			
			if (obj.yVals.length == 0) {
				return ;
			}
			
			var type = [];
			var finalD = [];
			for (var i = 0; i < obj.xAxis.length; i++) {
				type.push(obj.xAxis[i]);
				var temp = {};
				temp.value = obj.yVals[i];
				temp.name = obj.xAxis[i];
				finalD.push(temp);
			}
			
			var myChart = echarts.init(document.getElementById('main'));
			
			option = {
			    title : {
			        text: obj.title,
			        subtext: obj.description,
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: type
			    },
			    series : [
			        {
			            name: 'Category',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data: finalD,
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                }
			            }
			        }
			    ]
			};
			
			myChart.setOption(option);
		}
	});
});
$(document).ready(()=>{
	console.log("Ready!");
	
	var path = window.location.pathname;
	var gid = path.slice(path.indexOf("/", 1) + 1, path.length);
	
	$.ajax({
		url: "/histo/getGraph?gid=" + gid,
		method: "get",
		success: (res)=>{
			obj = JSON.parse(res);
			
			if (obj.xVals.length == 0) {
				return ;
			}
			
			var myChart = echarts.init(document.getElementById('main'));
			
			var bins = ecStat.histogram(obj.xVals);

			var interval;
			var min = Infinity;
			var max = -Infinity;

			data = echarts.util.map(bins.data, function (item, index) {
			    var x0 = bins.bins[index].x0;
			    var x1 = bins.bins[index].x1;
			    interval = x1 - x0;
			    min = Math.min(min, x0);
			    max = Math.max(max, x1);
			    return [x0, x1, item[1]];
			});

			function renderItem(params, api) {
			    var yValue = api.value(2);
			    var start = api.coord([api.value(0), yValue]);
			    var size = api.size([api.value(1) - api.value(0), yValue]);
			    var style = api.style();

			    return {
			        type: 'rect',
			        shape: {
			            x: start[0] + 1,
			            y: start[1],
			            width: size[0] - 2,
			            height: size[1]
			        },
			        style: style
			    };
			}

			option = {
			    title: {
			        text: obj.title,
			        subtext: obj.description,
			        left: 'center',
			        top: 10
			    },
			    color: ['rgb(25, 183, 207)'],
			    grid: {
			        top: 80,
			        containLabel: true
			    },
			    xAxis: [{
			        type: 'value',
			        min: min,
			        max: max,
			        interval: interval
			    }],
			    yAxis: [{
			        type: 'value',
			    }],
			    series: [{
			        name: 'height',
			        type: 'custom',
			        renderItem: renderItem,
			        label: {
			            normal: {
			                show: true,
			                position: 'insideTop'
			            }
			        },
			        encode: {
			            x: [0, 1],
			            y: 2,
			            tooltip: 2,
			            label: 2
			        },
			        data: data
			    }]
			};
			
			myChart.setOption(option);
		}
	});
});

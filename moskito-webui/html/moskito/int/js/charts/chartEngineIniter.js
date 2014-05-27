if (google) google.load("visualization", "1", {packages:["corechart"]});

var chartEngineIniter = {
    GOOGLE_CHART_API: function(params){
        var chartData = new google.visualization.DataTable();
        chartData.addColumn('string', 'Stat');

		for(var i=0; i<params.names.length; i++) {
			chartData.addColumn('number', params.names[i]);
		}

        chartData.addRows(params.data || []);
        var options = {is3D:true, title: params.title || '', chartArea:{width: '80%', left: 75}, width: params.width, height: params.height};
        var chartInfo = {
            params: '',
            container: params.container,
            type: params.type || 'LineChart',
            data: chartData,
            options: options 
        };

        document.getElementById(chartInfo.container).chartInfo = chartInfo;
        google.visualization.drawChart({
            "containerId": chartInfo.container,
            dataTable: chartInfo.data,
            "chartType": chartInfo.type,
            "options": chartInfo.options,
            "refreshInterval": 60
        });
    },
    JQPLOT: function(params){
        var types = {
            PieChart: $.jqplot.PieRenderer,
            ColumnChart: $.jqplot.BarRenderer,
            LineChart: $.jqplot.DateAxisRenderer
        };

        var data = params.data;

        var options = {
            title: params.title || '',
            seriesDefaults: {
                renderer: types[params.type] || $.jqplot.DateAxisRenderer,
                rendererOptions: {
                    showDataLabels: true
                }
            },
            axesDefaults: {
                tickOptions: {
                    show: true
                }
            },
            grid: {
                background: '#fefefe'
            },
            legend: { show:true, location: 'e', xoffset: 100 }
        };
        
        if(params.type == 'LineChart' || !params.type) {
            options.cursor = { 
                show: true,
                zoom:true, 
                showTooltip:false
            };
            options.highlighter = {
                show: true,
                sizeAdjust: 7.5
            };
            options.axes = {
                xaxis: {
                    renderer: $.jqplot.DateAxisRenderer
                }
            }
            delete options.seriesDefaults;
            options.series = [];
            params.names.forEach(function(name){
                options.series.push({label: name});
            });
            /*options.seriesDefaults = {
                show: true
            }*/

            var lineData = [];
            if (!params.data[0]) return;
            params.data[0].forEach(function(item, index){
                if(index !== 0) lineData.push([]);
            });
            params.data.forEach(function(item){
                item.forEach(function(val, index){
                    if(index !== 0){
                        lineData[index-1].push([item[0], val]);
                    }
                })
            });

            data = lineData;
        }
        else if(params.type == 'ColumnChart') {
            options.seriesDefaults.rendererOptions =  {
                barPadding: 5,
                barMargin: 0,
                barWidth: 60
            }
            options.series = [];
            var columnData = [];
            data.forEach(function(item){
                options.series.push({label: item[0]});
                columnData.push([item[1]]);
            });
            data = columnData;
            options.axes = {
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    tickOptions: {
                        show: false
                    }
                }
            }
            //delete options.legend;
        }
        else{
            data = [params.data || []];
        }
        $('#' + params.container).empty();
        var plot1 = $.jqplot(params.container, data || [], options);

        if(params.type == 'LineChart') $('#' + params.container).click(function() { plot1.resetZoom() });
        if(params.type == 'ColumnChart') $('.jqplot-table-legend').css('right', '55px');
    }
};

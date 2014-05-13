if (google) google.load("visualization", "1", {packages:["corechart"]});

var chartEngineIniter = {
    GOOGLE_CHART_API: function(params){
        var chartData = new google.visualization.DataTable();
        chartData.addColumn('string', 'Stat');

		for(var i=0; i<params.names.length; i++) {
			chartData.addColumn('number', params.names[i]);
		}

        chartData.addRows(params.data || []);
        var options = {is3D:true, title: params.title || '', chartArea:{width: '77%', left: 100}};
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
            BarChart: $.jqplot.BarRenderer,
            LineChart: $.jqplot.DateAxisRenderer
        };

        var data = params.data;

        var options = { // -------------------------------------------------------------------------- Common Options
            title: params.title || '',
            axesDefaults: {
                tickOptions: {
                    show: true,
                    autoscale: true
                }
            },
            seriesDefaults: {
                renderer: types[params.type] || $.jqplot.DateAxisRenderer,
                rendererOptions: {
                    showDataLabels: true
                }
            },
            grid: {
                drawGridLines: true,
                gridLineColor: '#666666',
                background: 'transparent',
                borderColor: 'transparent',
                borderWidth: 0.0,
                shadow: false
            },
            legend: {
                renderer: $.jqplot.EnhancedLegendRenderer,
                show: true,
                location: 'ne',
                placement: 'outsideGrid',
                rendererOptions: {
                    numberColumns: 1
                }
            }
        };

        if(params.type == 'LineChart' || !params.type) { // ---------------------------------------- Line Chart
            delete options.seriesDefaults;

            options.axes = {
                xaxis: {
                    renderer: $.jqplot.DateAxisRenderer,
                    tickOptions: {
                        show: true,
                        formatString: '%H:%M'
                    }
                }
            }
            options.cursor = {
                show: true,
                zoom: true,
                showTooltip: false
            };
            options.highlighter = {
                show: true,
                sizeAdjust: 10,
                useAxesFormatters: true,
                tooltipContentEditor: function tooltipContentEditor(str, seriesIndex, pointIndex, plot) {
                    /* displays series label with x and y values value */
                    return plot.series[seriesIndex]['label'] + ': <br><b>' + str + '</b>'; // str comes from useAxesFormatters: true option
                }
            };

            options.series = [];
            params.names.forEach(function(name){
                /* also it's labels for legend by default at the same time */
                options.series.push({label: name});
            });

            var lineData = [];
            if (!data[0]) return;
            /* memory allocation for values-per-line arrays */
            data[0].forEach(function(item, index){
                if(index !== 0) lineData.push([]);
            });
            data.forEach(function(item){
                item.forEach(function(val, index){
                    if(index !== 0){
                        lineData[index-1].push([item[0], val]);
                    }
                })
            });

            data = lineData;
        }

        else if(params.type == 'ColumnChart') { // -------------------------------------------------- Column Chart
            options.seriesDefaults.rendererOptions =  {
                barPadding: 5,
                barMargin: 10,
                barWidth: 50
            }

            options.series = [];
            params.names.forEach(function(name){
                options.series.push({label: name});
            });

            var timeTicks = [];
            var columnValues = [];

            if (!data[0]) return;
            /* group - columns with the same label (color) */
            /* memory allocation for values-per-column-group arrays */
            data[0].forEach(function(item, index){
                if(index !== 0) columnValues.push([]); // excluding time tick value
            });

            data.forEach(function(item){
                timeTicks.push(item[0]);
                item.forEach(function(value, index){
                    if(index !== 0){
                        columnValues[index - 1].push(value);
                    }
                })
            });

            options.axes = {
                xaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer,
                    ticks: timeTicks
                }
            }
            options.legend = {
                show: true,
                location: 'ne',
                placement: 'outsideGrid'
            }

            data = columnValues;

        }

        else if(params.type == 'BarChart') { // ------------------------------------------------------ Bar Chart
            options.axes = {
                yaxis: {
                    renderer: $.jqplot.CategoryAxisRenderer
                }
            }
            options.seriesDefaults.rendererOptions =  {
                barPadding: 5,
                barMargin: 0,
                barWidth: 15,
                barDirection: 'horizontal'
            }
            options.series = [];
            var barData = [];
            data.forEach(function(item){
                options.series.push({label: item[0]});
                barData.push([item[1]]);
            });
            data = barData;
            options.highlighter = {
                show:true,
                sizeAdjust: 10,
                tooltipAxes: 'both',
                useAxesFormatters: true
            }
        }

        else if(params.type == 'PieChart') { // ------------------------------------------------------- Pie Chart
            options.highlighter = {
                show:true,
                sizeAdjust: 10,
                tooltipAxes: 'both',
                useAxesFormatters: true
            }
        }

        else{
            data = [params.data || []];
        }
        $('#' + params.container).empty();
        var plot1 = $.jqplot(params.container, data || [], options);

        if(params.type == 'LineChart') $('#' + params.container).click(function() { plot1.resetZoom() });
        if(params.type == 'ColumnChart' || params.type == 'BarChart') $('.jqplot-table-legend').css('right', '55px');
    }
};

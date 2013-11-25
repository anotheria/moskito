if (google) google.load("visualization", "1", {packages:["corechart"]});

var chartEngineIniter = {
    HIGHCHART: function (params){
        var types = {
            PieChart: 'pie',
            ColumnChart: 'column',
            LineChart: 'spline'
        };

        var options = {
            title: {
                text: params.name
            },
            chart: {
                type: types[params.type] || 'spline',
            },
            xAxis: {
                labels:{
                    formatter: function(){
                        console.log(this)
                        var val;

                        if(params.type=='LineChart') val =  new Date(this.value).toLocaleTimeString();
                        else val = this.val;

                        return val;
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {enabled: false}
            },
            tooltip: {
                formatter: function() {
                    console.log(this)
                    var time = new Date(this.x).toLocaleTimeString();
                    var label;
                    if(params.type=='LineChart') label = '<b>'+ time +'</b><br/>'+ name + ': '+ this.y;
                    else label  = name + '  ' + this.y;
                    return label;
                }
            },
            series: [{
                name: params.name,
                data: params.data || []
            }]
        };

        if(params.type == 'ColumnChart'){
            var columnSeries = [];
            params.data.forEach(function(item){
                columnSeries.push({name: item[0], data: [item[1]]});
            });
            options.series = columnSeries;
        }

        $('#' + params.container).highcharts(options);
    },
    GOOGLE_CHART_API: function(params){
        var chartData = new google.visualization.DataTable();
        chartData.addColumn('string', 'Time');
        chartData.addColumn('number', params.name);
        chartData.addRows(params.data || []);
        var options = {/*width: 1200, height: 300,*/ title: params.name/*, chartArea:{left:140,width:800}*/};
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
            dataTable: chartInfo.data/*+chartInfo.params*/,
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
            title: params.name,
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
        }
        if(params.type == 'ColumnChart') {
            options.seriesDefaults.rendererOptions =  {
                barPadding: 20,
                barMargin: 0,
                barWidth: 100
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
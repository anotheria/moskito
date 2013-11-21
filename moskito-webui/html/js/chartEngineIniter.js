var chartEngineIniter = {
    HIGHCHART: function (params){
        $('#' + params.container).highcharts({
            title: {
                text: ''
            },
            chart: {
                type: 'spline'
            },
            xAxis: {
                labels:{
                    formatter: function(){ return new Date(this.value).toLocaleTimeString(); }
                }
            },
            yAxis: {
                min: 0,
                title: {enabled: false}
            },
            tooltip: {
                formatter: function() {
                    var time = new Date(this.x).toLocaleTimeString();
                    return '<b>'+ time +'</b><br/>'+ name + ': '+ this.y;
                }
            },
            series: [{
                name: params.name,
                data: params.data
            }]
        });
    },
    GOOGLE_CHART_API: function(params){
        google.load("visualization", "1", {packages:["corechart"]});
        google.setOnLoadCallback(drawLineChart);
        //combined chart
        function drawLineChart() {
                var chartData = new google.visualization.DataTable();
                chartData.addColumn('string', 'Time');
                chartData.addColumn('number', params.name);
                chartData.addRows(params.data);
                var options = {width: 1200, height: 300, title: params.name, chartArea:{left:140,width:800}};
                var chartInfo = {
                    params: '',
                    container: params.container,
                    type: 'LineChart',
                    data: chartData,
                    options: options 
                };
                drawChart(chartInfo);       
        }
        function drawChart(chartInfo) {
            document.getElementById(chartInfo.container).chartInfo = chartInfo;

            google.visualization.drawChart({
                "containerId": chartInfo.container,
                dataTable: chartInfo.data/*+chartInfo.params*/,
                "chartType": chartInfo.type,
                "options": chartInfo.options,
                "refreshInterval": 60
            });
        }
    },
    JQPLOT: function(params){
        var plot1 = $.jqplot(params.container, [params.data], { 
            title: params.name,
            axes:{
                xaxis: {
                    renderer:$.jqplot.DateAxisRenderer
                }
            },
            cursor:{ 
                show: true,
                zoom:true, 
                showTooltip:false
            },
            highlighter: {
                show: true,
                sizeAdjust: 7.5
            },
            seriesDefaults: {
                color: '#5E7CFF'
            },
            grid: {
                background: '#fefefe'
            }
        });

        $('#' + params.container).click(function() { plot1.resetZoom() });
    }
};
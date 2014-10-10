if (typeof google != 'undefined') google.load("visualization", "1", {packages:["corechart"]});

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
    },
    D3 : function(params){
        var barColor = 'steelblue';

        $('#' + params.container).empty();

        var count = 0;
        var data = params.data.map( function(d) {
            var res =  {label: d[0], color:colortabel[count], value:d[1]};
            count = (count + 4) % colortabel.length;
            return res;
        });

        D3chart("#"+params.container, data, params);
    }
};

function D3chart(id, fData, params){
    var barColor = 'steelblue';

    // function to handle histogram.
    function histoGram(fDIn){
        var hGDim = {t: 60, r: 0, b: 30, l: 0};
        hGDim.w = params.width - hGDim.l - hGDim.r,
            hGDim.h = params.height - hGDim.t - hGDim.b;

        var fD = fDIn.map(function(d){return [d.label, d.value];});

        //create svg for histogram.
        var hGsvg = d3.select(id).append("svg")
            .attr("width", hGDim.w + hGDim.l + hGDim.r)
            .attr("height", hGDim.h + hGDim.t + hGDim.b).append("g")
            .attr("transform", "translate(" + hGDim.l + "," + hGDim.t + ")");

        // create function for x-axis mapping.
        var x = d3.scale.ordinal().rangeRoundBands([0, hGDim.w], 0.1)
            .domain(fD.map(function(d) { return d[0]; }));

        // Add x-axis to the histogram svg.
        hGsvg.append("g").attr("class", "x axis")
            .attr("transform", "translate(0," + hGDim.h + ")")
            .call(d3.svg.axis().scale(x).orient("bottom"));

        // Create function for y-axis map.
        var y = d3.scale.linear().range([hGDim.h, 0])
            .domain([0, d3.max(fD, function(d) { return d[1]; })]);

        // Create bars for histogram to contain rectangles and freq labels.
        var bars = hGsvg.selectAll(".bar").data(fD).enter()
            .append("g").attr("class", "bar");

        //create the rectangles.
        bars.append("rect")
            .attr("x", function(d) { return x(d[0]); })
            .attr("y", function(d) { return y(d[1]); })
            .attr("width", x.rangeBand())
            .attr("height", function(d) { return hGDim.h - y(d[1]); })
            .attr('fill',barColor);

        //Create the frequency labels above the rectangles.
        bars.append("text").text(function(d){ return d3.format(",")(d[1])})
            .attr("x", function(d) { return x(d[0])+x.rangeBand()/2; })
            .attr("y", function(d) { return y(d[1])-5; })
            .attr("text-anchor", "middle");
    }

    // function to handle pieChart.
    function pieChart(pD){

        var pieDim ={w:params.width, h: params.height};
        var edge = 5;
        pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;
        var svg = d3.select("#"+params.container).append("svg").attr("width",(pieDim.r+edge)*2).attr("height",(pieDim.r+edge)*2);
        svg.append("g").attr("id","donut");

        Donut3D.draw("donut", pD, pieDim.r+edge, pieDim.r*0.8+edge, pieDim.r, pieDim.r*0.8, pieDim.r * 0.2, 0.4);
    }

    // function to handle legend.
    function legend(lD){

        // create table for legend.
        var legend = d3.select(id).append("table").attr('class','legend');

        // create one row per segment.
        var tr = legend.append("tbody").selectAll("tr").data(lD).enter().append("tr");

        // create the first column for each segment.
        tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
            .attr("width", '16').attr("height", '16')
            .attr("fill",function(d){ return d.color; });

        // create the second column for each segment.
        tr.append("td").text(function(d){ return d.label;});

        // create the third column for each segment.
        tr.append("td").attr("class",'legendFreq')
            .text(function(d){ return d3.format(",")(d.value);});
    }

    if(params.type == 'PieChart') {
        pieChart(fData);
        legend(fData);
    }
    else if(params.type == 'ColumnChart') {
        histoGram(fData)
    }
}

var colortabel = [
    "#FF3030",
    "#EE2C2C",
    "#CD2626",
    "#8B1A1A",
    "#FF7F24",
    "#EE7621",
    "#CD661D",
    "#8B4513",
    "#FFB90F",
    "#EEAD0E",
    "#CD950C",
    "#8B6508",
    "#CAFF70",
    "#BCEE68",
    "#A2CD5A",
    "#6E8B3D",
    "#00FF00",
    "#00EE00",
    "#00CD00",
    "#008B00",
    "#00FFFF",
    "#00EEEE",
    "#00CDCD",
    "#008B8B",
    "#1E90FF",
    "#1C86EE",
    "#1874CD",
    "#104E8B",
    "#BF3EFF",
    "#B23AEE",
    "#9A32CD",
    "#68228B"];
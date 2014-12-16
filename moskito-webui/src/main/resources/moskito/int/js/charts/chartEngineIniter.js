if (typeof google != 'undefined') google.load("visualization", "1", {packages:["corechart"]});

var chartEngineIniter = {
    GOOGLE_CHART_API: function(params){
        var chartData = new google.visualization.DataTable(),
            chartRows = params.data || [],
            chartColumnType = params.dataType || 'string';

        switch (params.type) {
            case "LineChart":
                chartRows = chartRows.map(function (d) {
                    d[0] = new Date(d[0]);
                    return d;
                });
                break;
            default:
            // do nothing
        }

        chartData.addColumn(chartColumnType, 'Stat');

        for (var i = 0; i < params.names.length; i++) {
            chartData.addColumn('number', params.names[i]);
        }

        chartData.addRows(chartRows);
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
            var res =  {label: d[0], color:colorTable[count], value:d[1]};
            count = (count + 4) % colorTable.length;
            return res;
        });

        var d3Chart = D3chart.getInstance();

        d3Chart("#"+params.container, data, params);
    }
};

var D3chart = (function () {
    var instance;

    function createD3chart() {
        var chartColors = function (names) {
            if (names.length < 10)
                return d3.scale.category10().domain(names);

            var d3Colors = d3.scale.category10().range(),
                extraColors = [
                    "#EDB458", "#D3C0CD", "#6F8483", "#937666", "#C3746E", "#DF8D9B", "#B2D3A8", "#DAC4FF", "#A5E6BA", "#9AC6C5"
                ];
            return d3.scale.ordinal().range(d3.merge([d3Colors, extraColors])).domain(names);
        };
        var lineChart = function () {
            var multiFormat = d3.time.format.multi([
                    [".%L", function (d) {
                        return d.getMilliseconds();
                    }],
                    [":%S", function (d) {
                        return d.getSeconds();
                    }],
                    ["%H:%M", function (d) {
                        return d.getMinutes() || d.getHours();
                    }],
                    ["%a %d", function (d) {
                        return d.getDay() && d.getDate() != 1;
                    }],
                    ["%b %d", function (d) {
                        return d.getDate() != 1;
                    }],
                    ["%B", function (d) {
                        return d.getMonth();
                    }],
                    ["%Y", function () {
                        return true;
                    }]
                ]),
                formatTime = d3.time.format("%H:%M"),
                margin = {top: 20, right: 80, bottom: 30, left: 150};

            var slicesManager = function (elementsPerSlice, data) {
                var perSlice = elementsPerSlice || 10,
                    currentSlice = 0,
                    totalSlices = Math.ceil(data.length / perSlice),
                    getOffset = function () {
                        return (currentSlice - 1) * perSlice;
                    },
                    getSlice = function (sliceNumber) {
                        if (sliceNumber < 1) sliceNumber = 1;
                        if (sliceNumber > totalSlices) sliceNumber = totalSlices;

                        currentSlice = sliceNumber;
                        var start = getOffset(),
                            end = start + perSlice;
                        return data.slice(start, end);
                    }, next = function () {
                        return getSlice(currentSlice + 1);
                    }, prev = function () {
                        return getSlice(currentSlice - 1)
                    }, hasNext = function () {
                        return currentSlice < totalSlices;
                    }, hasPrev = function () {
                        return currentSlice > 1;
                    },
                    getSlicesNumber = function () {
                        return totalSlices;
                    };
                return {
                    next: next,
                    prev: prev,
                    hasNext: hasNext,
                    hasPrev: hasPrev,
                    getSlice: getSlice,
                    getSlicesNumber: getSlicesNumber
                }
            };

            var showChartTooltip = function buildTooltip() {
                var tooltip = d3.select(".accumulator-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "accumulator-chart-tooltip hidden");
                }

                var template = function (html, data) {
                        return html.replace(/{(\w*)}/g, function (m, key) {
                            return data.hasOwnProperty(key) ? data[key] : "";
                        });
                    },
                    titleSectionTmpl = '<div class="tooltip-section tooltip-title"><strong>{title}</strong></div>',
                    tooltipSectionTmpl = '<div class="tooltip-section">' +
                        '<div class="tooltip-swatch" style="background-color: {color}"></div>' +
                        '<div class="tooltip-key">{name}</div>' +
                        '<div class="tooltip-value">{value}</div>' +
                        '</div>';

                return function (containerId, rect, tooltipSelector, title, data) {
                    var titleSectionHtml = template(titleSectionTmpl, {title: title}),
                        tooltipSectionsHtml = data.map(function (d) {
                            return template(tooltipSectionTmpl, d);
                        }),
                        html = [].concat.call(titleSectionHtml, tooltipSectionsHtml);

                    var d3mouse = d3.mouse(rect),
                        bodyRect = document.body.getBoundingClientRect(),
                        svgRect = rect.getBoundingClientRect(),
                        offsetTop = svgRect.top - bodyRect.top,
                        offsetLeft = svgRect.left - bodyRect.left,
                        tooltipWidth = parseInt(tooltip.style("width"));

                    var left = d3mouse[0] + offsetLeft;
                    left = d3mouse[0] > _getWidth(containerId) / 2 ? left - 20 - tooltipWidth : left + 20;

                    tooltip.html(html.join(""))
                        .style("left", (left) + "px")
                        .style("top", (d3mouse[1] + offsetTop + 10) + "px");
                }
            }();

            var _createContainer = function (containerId, names, data) {
                var chartContainer = d3.select(containerId);
                containers[containerId] = {};
                containers[containerId].container = chartContainer;
                containers[containerId].names = names;
                containers[containerId].color = function () {
                    return chartColors(names);
                };
                containers[containerId].data = data;

                var width = parseInt(chartContainer.style("width"), 10) - margin.left - margin.right,
                    height = parseInt(chartContainer.style("height"), 10) - margin.top - margin.bottom;
                _setWidth(containerId, width);
                _setHeight(containerId, height);
            };

            var _createSvg = function(containerId){
                var svg = containers[containerId].container.append("svg");
                svg.attr("class", "graph")
                    .attr("width", _getWidth(containerId) + margin.left + margin.right)
                    .attr("height", _getHeight(containerId) + margin.top + margin.bottom)
                    .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

                containers[containerId].svg = svg;
            };

            var _getColorFunc = function (containerId) {
                return containers[containerId].color();
            };

            var _setWidth = function (containerId, width) {
                containers[containerId].width = width;
            };

            var _setHeight = function (containerId, height) {
                containers[containerId].height = height;
            };

            var _getWidth = function (containerId) {
                return containers[containerId].width;
            };

            var _getHeight = function (containerId) {
                return containers[containerId].height;
            };

            var _createScales = function (containerId, timeValues) {
                var xScale = d3.time.scale().range([0, _getWidth(containerId)]);
                var yScale = d3.scale.linear().range([_getHeight(containerId), 0]);

                xScale.domain([
                    d3.min(timeValues, function (t) {
                        return d3.min(t.values, function (v) {
                            return v.time;
                        });
                    }),
                    d3.max(timeValues, function (t) {
                        return d3.max(t.values, function (v) {
                            return v.time;
                        });
                    })
                ]);

                var minValue = d3.min(timeValues, function (t) {
                    return d3.min(t.values, function (v) {
                        return v.value;
                    });
                });

                yScale.domain([
                    minValue < 0 ? minValue : 0,
                    d3.max(timeValues, function (t) {
                        return d3.max(t.values, function (v) {
                            return v.value;
                        });
                    })
                ]);

                scales[containerId] = {
                    "xScale": xScale,
                    "yScale": yScale
                };
            };

            var _createAxises = function (containerId) {
                var xScale = scales[containerId].xScale;
                var yScale = scales[containerId].yScale;
                var svg = containers[containerId].svg;

                var xAxis = d3.svg.axis()
                    .scale(xScale)
                    .orient("bottom")
                    .ticks(6)
                    .tickFormat(multiFormat);

                var yAxis = d3.svg.axis()
                    .scale(yScale)
                    .orient("left")
                    .ticks(5);

                svg.append("g")
                    .attr("class", "x axis");
                svg.append("g")
                    .attr("class", "y axis");

                axises[containerId] = {
                    "xAxis": xAxis,
                    "yAxis": yAxis
                }
            };

            var _createGrid = function (containerId) {
                var xScale = scales[containerId].xScale;
                var yScale = scales[containerId].yScale;
                var svg = containers[containerId].svg;

                var xGrid = d3.svg.axis()
                    .scale(xScale)
                    .orient("bottom")
                    .ticks(6)
                    .tickFormat("");

                var yGrid = d3.svg.axis()
                    .scale(yScale)
                    .orient("left")
                    .ticks(5)
                    .tickFormat("");

                svg.append("g")
                    .attr("class", "x grid");
                svg.append("g")
                    .attr("class", "y grid");

                grids[containerId] = {
                    "xGrid": xGrid,
                    "yGrid": yGrid
                };
            };

            var _createLine = function (containerId, timeValues) {
                var xScale = scales[containerId].xScale;
                var yScale = scales[containerId].yScale;
                var svg = containers[containerId].svg;

                var line = d3.svg.line()
                    .defined(function (d) {
                        return !isNaN(d.value);
                    })
                    .x(function (d) {
                        return xScale(d.time);
                    })
                    .y(function (d) {
                        return yScale(d.value);
                    });

                var timeValue = svg.selectAll(".timeValue")
                    .data(timeValues)
                    .enter().append("g")
                    .attr("class", "timeValue");
                timeValue.append("path")
                    .attr("class", "line");

                lines[containerId] = line;
            };

            var _renderLine = function (containerId) {
                var svg = containers[containerId].svg;
                var line = lines[containerId];
                var color = _getColorFunc(containerId);

                svg.selectAll('.line')
                    .attr("d", function (d) {
                        return line(d.values);
                    })
                    .style("stroke", function (d) {
                        return color(d.name);
                    });
            };

            var _renderGrid = function (containerId) {
                var xGrid = grids[containerId].xGrid;
                var yGrid = grids[containerId].yGrid;
                var svg = containers[containerId].svg;

                xGrid.tickSize(-_getHeight(containerId), 0, 0);
                yGrid.tickSize(-_getWidth(containerId), 0, 0);

                svg.select('.x.grid')
                    .attr("transform", "translate(0," + _getHeight(containerId) + ")")
                    .call(xGrid);

                svg.select('.y.grid')
                    .call(yGrid);
            };

            var _setScalesRange = function (containerId) {
                var xScale = scales[containerId].xScale;
                var yScale = scales[containerId].yScale;

                xScale.range([0, _getWidth(containerId)]);
                yScale.range([_getHeight(containerId), 0]);
            };

            var _renderAxises = function (containerId) {
                var xAxis = axises[containerId].xAxis;
                var yAxis = axises[containerId].yAxis;
                var svg = containers[containerId].svg;

                svg.select('.x.axis')
                    .attr("transform", "translate(0," + _getHeight(containerId) + ")")
                    .call(xAxis);

                svg.select('.y.axis')
                    .call(yAxis);
            };

            var _createAndRenderFocuses = function (containerId, dotsValues) {
                if (dotsValues.length == 0)
                    return;

                var svg = containers[containerId].svg;
                var focus = svg.append("g")
                    .style("display", "none");

                var color = _getColorFunc(containerId);

                var focusDots = containers[containerId].names.map(function (name) {
                    return focus.append("circle")
                        .attr("class", "dot")
                        .style("fill", color(name))
                        .style("stroke", color(name))
                        .attr("r", 0);
                });

                svg.append("rect")
                    .attr("class", "focusRect")
                    .style("fill", "none")
                    .style("pointer-events", "all")
                    .on("mouseover", function () {
                        focus.style("display", null);
                        d3.select(".accumulator-chart-tooltip").classed("hidden", false);
                    })
                    .on("mouseout", function () {
                        focus.style("display", "none");
                        d3.select(".accumulator-chart-tooltip").classed("hidden", true);
                    })
                    .on("mousemove", function () {
                        var xScale = scales[containerId].xScale;
                        var yScale = scales[containerId].yScale;

                        var bisectDate = d3.bisector(function (d) {
                            return d.time;
                        }).left;

                        var self = this;
                        var tooltipData = [];
                        var x0 = xScale.invert(d3.mouse(self)[0]),
                            i = bisectDate(dotsValues, x0, 1),
                            d0 = dotsValues[i - 1],
                            d1 = dotsValues[i] || d0,
                            d = x0 - d0.time > d1.time - x0 ? d1 : d0;

                        d.values.forEach(function (timeValue, ind) {
                            tooltipData.push({
                                color: color(timeValue.name),
                                name: timeValue.name,
                                value: timeValue.value
                            });

                            if (isNaN(timeValue.value)) {
                                focusDots[ind].attr("r", 0);
                                return;
                            }

                            focusDots[ind]
                                .attr("transform",
                                    "translate(" + xScale(d.time) + "," +
                                    yScale(timeValue.value) + ")")
                                .attr("r", 3.5);
                        });

                        showChartTooltip(containerId, self, ".accumulator-chart-tooltip", formatTime(d.time), tooltipData);
                    });
            };

            var _renderFocus = function (containerId) {
                containers[containerId].svg.select("rect.focusRect")
                    .attr("width", _getWidth(containerId))
                    .attr("height", _getHeight(containerId));
            };

            var _createLegend = function () {
                var setLegendText = function (legend) {
                    legend.select("text")
                        .attr("dy", ".35em")
                        .style("text-anchor", "end")
                        .text(function (d) {
                            return d;
                        });
                };

                var setLegendRect = function (containerId, legend) {
                    var color = _getColorFunc(containerId);
                    legend.select("rect")
                        .attr("width", 10)
                        .attr("height", 10)
                        .style("fill", function (d) {
                            return color(d);
                        });
                };

                var updateLegend = function (containerId, data) {
                    var svg = containers[containerId].svg;
                    var existing = svg.selectAll(".lineChartLegend")
                        .data(data);
                    setLegendText(existing);
                    setLegendRect(containerId, existing);
                    existing.exit().remove();

                    var newAdded = existing.enter().append("g")
                        .attr("class", "lineChartLegend")
                        .attr("transform", function (d, i) {
                            return "translate(0," + i * 20 + ")";
                        });
                    newAdded.append("text");
                    newAdded.append("rect");
                    setLegendText(newAdded);
                    setLegendRect(containerId, newAdded);

                    svg.selectAll(".legendButtons")
                        .attr("transform", "translate(0," + data.length * 20 + ")");
                };

                return function (containerId, legendsPerSlice) {
                    var svg = containers[containerId].svg;
                    var names = containers[containerId].names;

                    var slicer = slicesManager(legendsPerSlice, names);
                    var showLegendButtons = slicer.getSlicesNumber() > 1,
                        namesSlice = slicer.getSlice(1);

                    var legend = svg.selectAll(".lineChartLegend")
                        .data(namesSlice)
                        .enter().append("g")
                        .attr("class", "lineChartLegend")
                        .attr("transform", function (d, i) {
                            return "translate(0," + i * 20 + ")";
                        });
                    legend.append("text");
                    legend.append("rect");
                    setLegendText(legend);
                    setLegendRect(containerId, legend);

                    if (showLegendButtons) {
                        var buttons = svg
                            .append("g")
                            .attr("class", "legendButtons")
                            .attr("transform", "translate(0," + namesSlice.length * 20 + ")");

                        buttons.append("text")
                            .attr("class", "prevButton")
                            .attr("dy", "1em")
                            .style("text-anchor", "end")
                            .text('\u25C0')
                            .on("click", function () {
                                if (slicer.hasPrev()) {
                                    updateLegend(containerId, slicer.prev());
                                    _renderLegend(containerId);
                                }
                            });

                        buttons.append("text")
                            .attr("class", "nextButton")
                            .attr("dy", "1em")
                            .style("text-anchor", "end")
                            .text("\u25B6")
                            .on("click", function () {
                                if (slicer.hasNext()) {
                                    updateLegend(containerId, slicer.next());
                                    _renderLegend(containerId);
                                }
                            });
                    }
                }
            }();

            var _renderLegend = function (containerId) {
                var svg = containers[containerId].svg;
                svg.selectAll(".lineChartLegend rect")
                    .attr("x", _getWidth(containerId) - 18)
                    .attr("y", 4);
                svg.selectAll(".lineChartLegend text")
                    .attr("x", _getWidth(containerId) - 24)
                    .attr("y", 9);
                svg.selectAll(".legendButtons .prevButton")
                    .attr("x", _getWidth(containerId) - 24)
                    .attr("y", 9);
                svg.selectAll(".legendButtons .nextButton")
                    .attr("x", _getWidth(containerId) - 10)
                    .attr("y", 9);
            };

            var init = function (containerId, names, data, options) {
                _createContainer(containerId, names, data);
                _createSvg(containerId);

                var timeValues = names.map(function (name, namesIdx) {
                    return {
                        name: name,
                        values: data.map(function (d, dataIdx) {
                            return {
                                time: new Date(d[0]),
                                value: d[namesIdx + 1]
                            }
                        })
                    }
                });

                _createScales(containerId, timeValues);
                _createAxises(containerId);
                _createGrid(containerId);
                _createLine(containerId, timeValues);
                _renderAxises(containerId);

                var dotsValues = data.map(function (d, dataIdx) {
                    return {
                        time: new Date(d[0]),
                        values: names.map(function (name, nameIdx) {
                            return {
                                value: d[nameIdx + 1],
                                name: name
                            }
                        })
                    }
                });

                _createAndRenderFocuses(containerId, dotsValues);
                _createLegend(containerId, options.legendsPerSlice);
            };

            var render = function (containerId) {
                var chartContainer = containers[containerId].container;
                var svg = containers[containerId].svg;
                var width = parseInt(chartContainer.style("width"), 10) - margin.left - margin.right;
                var height = parseInt(chartContainer.style("height"), 10) - margin.top - margin.bottom;

                _setWidth(containerId, width);
                _setHeight(containerId, height);

                var transition = svg.transition().duration(750);
                transition.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

                _setScalesRange(containerId);
                _renderAxises(containerId, transition);
                _renderGrid(containerId, transition);
                _renderLine(containerId, transition);
                _renderLegend(containerId, transition);
                _renderFocus(containerId, transition);

                chartContainer.select("svg").attr('width', chartContainer.style("width"));
                chartContainer.select("svg").attr('height', chartContainer.style("height"));
            };

            var containers = {},
                scales = {},
                axises = {},
                grids = {},
                lines = {};

            return (lineChart = function (containerId, inputNames, inputData) {
                init(containerId, inputNames, inputData, {
                    legendsPerSlice: 7
                });

                d3.select(window).on('resize.' + containerId, function () {
                    var resizeTimer = -1;
                    return function () {
                        if (resizeTimer > -1)
                            clearTimeout(resizeTimer);
                        resizeTimer = setTimeout(function () {
                            render(containerId);
                        }, 100);
                    };
                }());

                if (!chartEngineIniter.d3charts) {
                    chartEngineIniter.d3charts = {};
                    var dispatch = d3.dispatch("resizeLineCharts");
                    chartEngineIniter.d3charts.dispatch = dispatch;

                    dispatch.on("resizeLineCharts", function () {
                        Object.keys(containers).forEach(function (containerId, index) {
                            setTimeout(function () {
                                render(containerId);
                            }, (index + 1) * 10);
                        });
                    });
                }

                render(containerId);
            })(arguments[0], arguments[1], arguments[2]);
        };

        // function to handle histogram.
        function histoGram(id, fDIn, params) {
            var barColor = 'steelblue';

            var hGDim = {t: 60, r: 0, b: 30, l: 0};
            hGDim.w = params.width - hGDim.l - hGDim.r;
            hGDim.h = params.height - hGDim.t - hGDim.b;

            var fD = fDIn.map(function (d) {
                return [d.label, d.value];
            });

            //create svg for histogram.
            var hGsvg = d3.select(id).append("svg")
                .attr("width", hGDim.w + hGDim.l + hGDim.r)
                .attr("height", hGDim.h + hGDim.t + hGDim.b).append("g")
                .attr("transform", "translate(" + hGDim.l + "," + hGDim.t + ")");

            // create function for x-axis mapping.
            var x = d3.scale.ordinal().rangeRoundBands([0, hGDim.w], 0.1)
                .domain(fD.map(function (d) {
                    return d[0];
                }));

            // Add x-axis to the histogram svg.
            hGsvg.append("g").attr("class", "x axis")
                .attr("transform", "translate(0," + hGDim.h + ")")
                .call(d3.svg.axis().scale(x).orient("bottom"));

            // Create function for y-axis map.
            var y = d3.scale.linear().range([hGDim.h, 0])
                .domain([0, d3.max(fD, function (d) {
                    return d[1];
                })]);

            // Create bars for histogram to contain rectangles and freq labels.
            var bars = hGsvg.selectAll(".bar").data(fD).enter()
                .append("g").attr("class", "bar");

            //create the rectangles.
            bars.append("rect")
                .attr("x", function (d) {
                    return x(d[0]);
                })
                .attr("y", function (d) {
                    return y(d[1]);
                })
                .attr("width", x.rangeBand())
                .attr("height", function (d) {
                    return hGDim.h - y(d[1]);
                })
                .attr('fill', barColor);

            //Create the frequency labels above the rectangles.
            bars.append("text").text(function (d) {
                return d3.format(",")(d[1])
            })
                .attr("x", function (d) {
                    return x(d[0]) + x.rangeBand() / 2;
                })
                .attr("y", function (d) {
                    return y(d[1]) - 5;
                })
                .attr("text-anchor", "middle");
        }

        // function to handle pieChart.
        function pieChart(pD, params) {

            var pieDim = {w: params.width, h: params.height};
            var edge = 5;
            pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;
            var svg = d3.select("#" + params.container).append("svg").attr("width", (pieDim.r + edge) * 2).attr("height", (pieDim.r + edge) * 2);
            svg.append("g").attr("id", "donut");

            Donut3D.draw("donut", pD, pieDim.r + edge, pieDim.r * 0.8 + edge, pieDim.r, pieDim.r * 0.8, pieDim.r * 0.2, 0.4);
        }

        // function to handle legend.
        function legend(id, lD) {

            // create table for legend.
            var legend = d3.select(id).append("table").attr('class', 'legend');

            // create one row per segment.
            var tr = legend.append("tbody").selectAll("tr").data(lD).enter().append("tr");

            // create the first column for each segment.
            tr.append("td").append("svg").attr("width", '16').attr("height", '16').append("rect")
                .attr("width", '16').attr("height", '16')
                .attr("fill", function (d) {
                    return d.color;
                });

            // create the second column for each segment.
            tr.append("td").text(function (d) {
                return d.label;
            });

            // create the third column for each segment.
            tr.append("td").attr("class", 'legendFreq')
                .text(function (d) {
                    return d3.format(",")(d.value);
                });
        }

        return function (id, fData, params) {
            switch (params.type){
                case "PieChart":
                    pieChart(fData, params);
                    legend(id, fData);
                    break;
                case "ColumnChart":
                    histoGram(id, fData, params);
                    break;
                case "LineChart":
                    lineChart(id, params.names, params.data);
                    break;
                default:
                    lineChart(id, params.names, params.data);
            }
        }
    }

    return {
        getInstance: function(){
            if (instance === undefined) {
                instance = createD3chart();
            }
            return instance;
        }
    };
})();

var colorTable = [
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
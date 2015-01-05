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
    D3 : function(params){
        var barColor = 'steelblue';

        $('#' + params.container).empty();

        var count = 0;
        var data = params.data.map( function(d) {
            return {
                label: d[0],
                value:d[1]
            };
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

        var template = function (html, data) {
            return html.replace(/{(\w*)}/g, function (m, key) {
                return data.hasOwnProperty(key) ? data[key] : "";
            });
        };

        var createLegend = function () {
            var setLegendText = function (legend, dy) {
                legend.select("text")
                    .attr("dy", dy)
                    .style("text-anchor", "end")
                    .text(function (d) {
                        return d.value;
                    });
            };

            var setLegendRect = function (legend, width, height) {
                legend.select("rect")
                    .attr("width", width)
                    .attr("height", height)
                    .style("fill", function (d) {
                        return d.color;
                    });
            };

            var updateLegend = function (svg, data, options) {
                var existing = svg.selectAll(".legend").data(data);
                setLegendText(existing, options.textDy);
                setLegendRect(existing, options.rectWidth, options.rectHeight);
                existing.exit().remove();

                var newAdded = existing.enter().append("g")
                    .attr("class", "legend")
                    .attr("transform", function (d, i) {
                        return "translate(0," + i * 20 + ")";
                    })
                    .on("mouseover", function(d){
                        if(options.mouseover)
                            options.mouseover(d.index);
                    })
                    .on("mouseout", function(d){
                        if(options.mouseout)
                            options.mouseout(d.index);
                    });
                newAdded.append("text");
                newAdded.append("rect");
                setLegendText(newAdded, options.textDy);
                setLegendRect(newAdded, options.rectWidth, options.rectHeight);

                svg.selectAll(".legendButtons")
                    .attr("transform", "translate(0," + data.length * 20 + ")");
            };

            return function (svg, options) {
                var legendsPerSlice = options.legendsPerSlice,
                    names = options.names;

                var slicer = slicesManager(legendsPerSlice, names);
                var showLegendButtons = slicer.getSlicesNumber() > 1,
                    namesSlice = slicer.getSlice(1);

                var legend = svg.selectAll(".legend")
                    .data(namesSlice)
                    .enter().append("g")
                    .attr("class", "legend")
                    .attr("transform", function (d, i) {
                        return "translate(0," + i * 20 + ")";
                    })
                    .on("mouseover", function(d){
                        if(options.mouseover)
                            options.mouseover(d.index);
                    })
                    .on("mouseout", function(d){
                        if(options.mouseout)
                            options.mouseout(d.index);
                    });
                legend.append("text");
                legend.append("rect");
                setLegendText(legend, options.textDy);
                setLegendRect(legend, options.rectWidth, options.rectHeight);

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
                                updateLegend(svg, slicer.prev(), options);
                                renderLegend(svg, options);
                            }
                        });

                    buttons.append("text")
                        .attr("class", "nextButton")
                        .attr("dy", "1em")
                        .style("text-anchor", "end")
                        .text("\u25B6")
                        .on("click", function () {
                            if (slicer.hasNext()) {
                                updateLegend(svg, slicer.next(), options);
                                renderLegend(svg, options);
                            }
                        });
                }
            }
        }();

        var renderLegend = function (svg, options) {
            var containerWidth = options.containerWidth;

            svg.selectAll(".legend rect")
                .attr("x", containerWidth - 18)
                .attr("y", 4);
            svg.selectAll(".legend text")
                .attr("x", containerWidth - 24)
                .attr("y", 9);
            svg.selectAll(".legendButtons .prevButton")
                .attr("x", containerWidth - 24)
                .attr("y", 9);
            svg.selectAll(".legendButtons .nextButton")
                .attr("x", containerWidth - 10)
                .attr("y", 9);
        };

        var pieChart = function () {
            var colorFunc;

            var showChartTooltip = function () {
                var tooltip = d3.select(".d3-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "d3-chart-tooltip hidden");
                }

                var tooltipSectionTmpl = '<div class="tooltip-section">' +
                    '<div class="tooltip-swatch" style="background-color: {color}"></div>' +
                    '<div class="tooltip-key">{name}</div>' +
                    '<div class="tooltip-value">{value}</div>' +
                    '</div>';

                return function (rect, data) {
                    var tooltipSectionsHtml = data.map(function (d) {
                            return template(tooltipSectionTmpl, d);
                        }),
                        html = [].concat.call(tooltipSectionsHtml);

                    var d3mouse = d3.mouse(rect),
                        bodyRect = document.body.getBoundingClientRect(),
                        svgRect = rect.getBoundingClientRect(),
                        offsetLeft = svgRect.left - bodyRect.left,
                        left = d3mouse[0] + offsetLeft+ 10;

                    tooltip.html(html.join(""))
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                }
            }();

            var pieTop = function (d, rx, ry, ir) {
                if (d.endAngle - d.startAngle == 0) return "M 0 0";
                var sx = rx * Math.cos(d.startAngle),
                    sy = ry * Math.sin(d.startAngle),
                    ex = rx * Math.cos(d.endAngle),
                    ey = ry * Math.sin(d.endAngle);

                var ret = [];
                ret.push("M", sx, sy, "A", rx, ry, "0", (d.endAngle - d.startAngle > Math.PI ? 1 : 0), "1", ex, ey, "L", ir * ex, ir * ey);
                ret.push("A", ir * rx, ir * ry, "0", (d.endAngle - d.startAngle > Math.PI ? 1 : 0), "0", ir * sx, ir * sy, "z");
                return ret.join(" ");
            };

            var pieOuter = function (d, rx, ry, h) {
                var startAngle = (d.startAngle > Math.PI ? Math.PI : d.startAngle);
                var endAngle = (d.endAngle > Math.PI ? Math.PI : d.endAngle);

                var sx = rx * Math.cos(startAngle),
                    sy = ry * Math.sin(startAngle),
                    ex = rx * Math.cos(endAngle),
                    ey = ry * Math.sin(endAngle);

                var ret = [];
                ret.push("M", sx, h + sy, "A", rx, ry, "0 0 1", ex, h + ey, "L", ex, ey, "A", rx, ry, "0 0 0", sx, sy, "z");
                return ret.join(" ");
            };

            var getPercent = function(d) {
                return (d.endAngle - d.startAngle > 0.2 ?
                    Math.round(1000 * (d.endAngle - d.startAngle) / (Math.PI * 2)) / 10 + '%' : '');
            };

            var renderPie = function (svg, data, x, y, rx, ry, ir, h) {
                var slicesContainer = svg.append("g")
                    .attr("transform", "translate(" + x + "," + y + ")")
                    .attr("class", "slices");

                slicesContainer.selectAll(".topSlice").data(data).enter().append("path").attr("class", "topSlice")
                    .style("fill", function (d) {
                        return colorFunc(d.data.name);
                    })
                    .style("stroke", function (d) {
                        return colorFunc(d.data.name);
                    })
                    .attr("d", function (d) {
                        return pieTop(d, rx, ry, ir);
                    });

                slicesContainer.selectAll(".outerSlice").data(data).enter().append("path").attr("class", "outerSlice")
                    .style("fill", function (d) {
                        return d3.hsl(colorFunc(d.data.name)).darker(0.7);
                    })
                    .attr("d", function (d) {
                        return pieOuter(d, rx - .5, ry - .5, h);
                    });

                slicesContainer.selectAll(".percent").data(data).enter().append("text").attr("class", "percent")
                    .attr("x", function (d) {
                        return 0.6 * rx * Math.cos(0.5 * (d.startAngle + d.endAngle));
                    })
                    .attr("y", function (d) {
                        return 0.6 * ry * Math.sin(0.5 * (d.startAngle + d.endAngle));
                    })
                    .text(getPercent);
            };

            var renderInteractivePart = function (svg, data, x, y, rx, ry, ir, h) {
                var slicesContainer = svg.append("g")
                    .attr("transform", "translate(" + x + "," + y + ")")
                    .attr("class", "interactivePart");

                var onMouseOver = function (d, id) {
                    d3.select(".d3-chart-tooltip").classed("hidden", false);
                    slicesContainer.selectAll("*[data-slice-number='" + id + "']").style("opacity", .4);
                };

                var onMouseOut = function (d, id) {
                    slicesContainer.selectAll("*[data-slice-number='" + id + "']").style("opacity", 0);
                    d3.select(".d3-chart-tooltip").classed("hidden", true);
                };

                var onMouseMove = function(d){
                    showChartTooltip(this, [
                            {
                                color: colorFunc(d.data.name),
                                name: d.data.name,
                                value: d.data.value
                            }
                        ]
                    );
                };

                slicesContainer.selectAll(".topSlice").data(data).enter().append("path").attr("class", "topSlice")
                    .attr("data-slice-number", function (d, i) {
                        return i;
                    })
                    .style("opacity", 0)
                    .style("fill", function (d) {
                        return d3.hsl(colorFunc(d.data.name)).brighter(1);
                    })
                    .attr("d", function (d) {
                        return pieTop(d, rx, ry, ir);
                    })
                    .on("mouseover", onMouseOver)
                    .on("mouseout", onMouseOut)
                    .on("mousemove", onMouseMove);

                slicesContainer.selectAll(".outerSlice").data(data).enter().append("path").attr("class", "outerSlice")
                    .attr("data-slice-number", function (d, i) {
                        return i;
                    })
                    .style("opacity", 0)
                    .style("fill", function (d) {
                        return d3.hsl(colorFunc(d.data.name)).brighter(1);
                    })
                    .attr("d", function (d) {
                        return pieOuter(d, rx - .5, ry - .5, h);
                    })
                    .on("mouseover", onMouseOver)
                    .on("mouseout", onMouseOut)
                    .on("mousemove", onMouseMove);
            };

            return (pieChart = function (containerId, params) {
                var width = params.width,
                    height = params.height,
                    edge = 100;

                var names = params.data.map(function (d) {
                    return d[0];
                });

                colorFunc = chartColors(names);

                var data = params.data.map(function (d) {
                    return {
                        name: d[0],
                        value: d[1]
                    };
                });

                var pieChartData = d3.layout.pie().sort(null).value(function (d) {
                    return d.value;
                })(data);

                var radius = Math.min(width, height) / 2;
                var svg = d3.select(containerId).append("svg")
                    .attr("width", width)
                    .attr("class", "pieChart")
                    .attr("height", height)
                    .append("g");

                var x = radius + edge,
                    y = radius * 0.6 + edge,
                    rx = radius,
                    ry = radius * 0.6,
                    h = radius * 0.2;

                renderPie(svg, pieChartData, x, y, rx, ry, 0, h);
                renderInteractivePart(svg, pieChartData, x, y, rx+7, ry+7, 0, h);

                var legendOptions = {
                    legendsPerSlice: 15,
                    mouseover: function(id){
                        d3.select(".interactivePart").selectAll("*[data-slice-number='" + id + "']").style("opacity", .4);
                    },
                    mouseout: function(id){
                        d3.select(".interactivePart").selectAll("*[data-slice-number='" + id + "']").style("opacity", 0);
                    },
                    containerWidth: width,
                    names: names.map(function (item, idx) {
                        return {
                            index: idx,
                            value: item,
                            color: colorFunc(item)
                        }
                    }),
                    rectWidth: 14,
                    rectHeight: 14,
                    textDy: ".50em"
                };

                createLegend(svg, legendOptions);
                renderLegend(svg, legendOptions);
            })(arguments[0], arguments[1]);
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

            var showChartTooltip = function () {
                var tooltip = d3.select(".d3-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "d3-chart-tooltip hidden");
                }

                var titleSectionTmpl = '<div class="tooltip-section tooltip-title"><strong>{title}</strong></div>',
                    tooltipSectionTmpl = '<div class="tooltip-section">' +
                        '<div class="tooltip-swatch" style="background-color: {color}"></div>' +
                        '<div class="tooltip-key">{name}</div>' +
                        '<div class="tooltip-value">{value}</div>' +
                        '</div>';

                return function (containerId, rect, title, data) {
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

            var _createSvg = function (containerId) {
                var svg = containers[containerId].container.append("svg").attr("class", "graph")
                    .attr("width", _getWidth(containerId) + margin.left + margin.right)
                    .attr("height", _getHeight(containerId) + margin.top + margin.bottom)
                    .append("g");
                svg.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

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

            var _renderLine = function (containerId, svg) {
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

            var _renderGrid = function (containerId, svg) {
                var xGrid = grids[containerId].xGrid;
                var yGrid = grids[containerId].yGrid;

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

            var _renderAxises = function (containerId, svg) {
                var xAxis = axises[containerId].xAxis;
                var yAxis = axises[containerId].yAxis;

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
                        d3.select(".d3-chart-tooltip").classed("hidden", false);
                    })
                    .on("mouseout", function () {
                        focus.style("display", "none");
                        d3.select(".d3-chart-tooltip").classed("hidden", true);
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

                        showChartTooltip(containerId, self, formatTime(d.time), tooltipData);
                    });
            };

            var _renderFocus = function (containerId, svg) {
                svg.select("rect.focusRect")
                    .attr("width", _getWidth(containerId))
                    .attr("height", _getHeight(containerId));
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
                _renderAxises(containerId, containers[containerId].svg);

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

                var legendOptions = {
                    legendsPerSlice: 7,
                    containerWidth: _getWidth(containerId),
                    names: containers[containerId].names.map(function (name, idx) {
                        return {
                            index: idx,
                            value: name,
                            color: _getColorFunc(containerId)(name)
                        }
                    }) ,
                    rectWidth: 10,
                    rectHeight: 10,
                    textDy: ".35em"
                };

                createLegend(containers[containerId].svg, legendOptions);
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
                renderLegend(transition, {containerWidth: _getWidth(containerId)});
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

        return function (id, fData, params) {
            switch (params.type){
                case "PieChart":
                    pieChart(id, params);
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

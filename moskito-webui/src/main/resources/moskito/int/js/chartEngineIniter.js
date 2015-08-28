if (typeof google != 'undefined') google.load("visualization", "1", {packages: ["corechart"]});

var chartEngineIniter = {
    GOOGLE_CHART_API: function (params) {
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
        var options = {is3D: true, title: params.title || '', chartArea: {width: '80%', left: 75}, width: params.width, height: params.height};
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
    D3: function (params) {
        $('#' + params.container).empty();

        var d3Chart = D3chart.getInstance();

        d3Chart("#" + params.container, params);
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

        var valueFormat = d3.format(",");

        var siPrefixFormat = function (num, digits) {
            if (num < 1000)
                return num;

            var prefix = d3.formatPrefix(num),
                fpNum = parseFloat(prefix.scale(num).toFixed(digits));

            return (fpNum % 1 == 0 ? fpNum.toPrecision(1) : fpNum) + prefix.symbol;
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
                    .on("mouseover", function (d) {
                        if (options.mouseover)
                            options.mouseover(d.index);
                    })
                    .on("mouseout", function (d) {
                        if (options.mouseout)
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
                    .on("mouseover", function (d) {
                        if (options.mouseover)
                            options.mouseover(d.index);
                    })
                    .on("mouseout", function (d) {
                        if (options.mouseout)
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
                        left = d3mouse[0] + offsetLeft + 10;

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

            var getPercent = function (d) {
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

                var onMouseMove = function (d) {
                    showChartTooltip(this, [
                            {
                                color: colorFunc(d.data.name),
                                name: d.data.name,
                                value: valueFormat(d.data.value)
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
                    .style("stroke", function (d) {
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
                renderInteractivePart(svg, pieChartData, x, y, rx + 7, ry + 7, 0, h);

                var legendOptions = {
                    legendsPerSlice: 15,
                    mouseover: function (id) {
                        d3.select(".interactivePart").selectAll("*[data-slice-number='" + id + "']").style("opacity", .4);
                    },
                    mouseout: function (id) {
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
                formatTime = d3.time.format("%H:%M");

            var showChartTooltip = function () {
                var tooltip = d3.select(".d3-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "d3-chart-tooltip hidden");
                }

                var titleSectionTmpl = '<div class="tooltip-section tooltip-title"><strong>{title}</strong></div>',
                    tooltipSectionTmpl = '<div class="tooltip-section">' +
                        '<div class="tooltip-swatch" style="background-color: {color}"></div>' +
                        '<div class="tooltip-key{highlight}">{name}</div>' +
                        '<div class="tooltip-value">{value}</div>' +
                        '</div>';

                return function (containerId, rect, title, data) {
                    var titleSectionHtml = template(titleSectionTmpl, {title: title}),
                        tooltipSectionsHtml = data.map(function (d) {
                            d.highlight = d.highlight ? " highlight" : "";

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

            var _createContainer = function (containerId, names, data, options) {
                var chartContainer = d3.select(containerId);
                containers[containerId] = {};
                containers[containerId].container = chartContainer;
                containers[containerId].names = names;
                containers[containerId].color = function () {
                    return chartColors(names);
                };
                containers[containerId].data = data;

                var margin = options.margin,
                    width = parseInt(chartContainer.style("width"), 10) - margin.left - margin.right,
                    height = parseInt(chartContainer.style("height"), 10) - margin.top - margin.bottom;
                _setWidth(containerId, width);
                _setHeight(containerId, height);
            };

            var _createSvg = function (containerId, options) {
                var margin = options.margin,
                    svg = containers[containerId].container.append("svg").attr("class", "graph")
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
                    .ticks(5)
                    .tickFormat(function (d) {
                        if (d < 1000)
                            return d;

                        var prefix = d3.formatPrefix(d);
                        return prefix.scale(d).toFixed() + prefix.symbol;
                    });

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
                        svg.selectAll(".line").classed("hover", false);
                    })
                    .on("mousemove", function () {
                        var xScale = scales[containerId].xScale;
                        var yScale = scales[containerId].yScale;

                        var bisectDate = d3.bisector(function (d) {
                            return d.time;
                        }).left;

                        var bisectValue = d3.bisector(function (d) {
                            return d.value;
                        }).left;

                        var self = this;
                        var tooltipData = [];
                        var x0 = xScale.invert(d3.mouse(self)[0]),
                            i = bisectDate(dotsValues, x0, 1),
                            d0 = dotsValues[i - 1],
                            d1 = dotsValues[i] || d0,
                            d = x0 - d0.time > d1.time - x0 ? d1 : d0;

                        var sortedValues = d.values.slice().sort(function (a, b) {
                            var aValue = a.value,
                                bValue = b.value;

                            if (!isFinite(aValue) && !isFinite(bValue))
                                return 0;

                            if (!isFinite(aValue))
                                return 1;

                            if (!isFinite(bValue))
                                return -1;

                            return aValue - bValue;
                        });

                        var y0 = yScale.invert(d3.mouse(self)[1]),
                            iValue = bisectValue(sortedValues, y0, 1),
                            v0 = sortedValues[iValue - 1],
                            v1 = sortedValues[iValue] || v0,
                            v = y0 - v0.value > v1.value - y0 ? v1 : v0;

                        d.values.forEach(function (timeValue, ind) {
                            tooltipData.push({
                                color: color(timeValue.name),
                                name: timeValue.name,
                                value: valueFormat(timeValue.value),
                                highlight: v.lineId == ind
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

                        svg.selectAll(".line").each(function (el) {
                            d3.select(this).classed("hover", el.lineId == v.lineId);
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
                _createContainer(containerId, names, data, options);
                _createSvg(containerId, options);

                var timeValues = names.map(function (name, namesIdx) {
                    return {
                        lineId: namesIdx,
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
                _renderAxises(containerId, containers[containerId].svg);

                var dotsValues = data.map(function (d, dataIdx) {
                    return {
                        time: new Date(d[0]),
                        values: names.map(function (name, nameIdx) {
                            return {
                                lineId: nameIdx,
                                value: d[nameIdx + 1],
                                name: name
                            }
                        })
                    }
                });

                _createLine(containerId, timeValues);
                _createAndRenderFocuses(containerId, dotsValues);

                var legendOptions = {
                    legendsPerSlice: options.legendsPerSlice,
                    containerWidth: _getWidth(containerId),
                    names: containers[containerId].names.map(function (name, idx) {
                        return {
                            index: idx,
                            value: name,
                            color: _getColorFunc(containerId)(name)
                        }
                    }),
                    rectWidth: 10,
                    rectHeight: 10,
                    textDy: ".35em"
                };

                createLegend(containers[containerId].svg, legendOptions);
            };

            var render = function (containerId, options) {
                var chartContainer = containers[containerId].container;
                var svg = containers[containerId].svg;
                var margin = options.margin;
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

            return (lineChart = function (containerId, params) {
                var inputNames = params.names,
                    inputData = params.data,
                    options = params.options;

                init(containerId, inputNames, inputData, options);

                d3.select(window).on('resize.' + containerId, function () {
                    var resizeTimer = -1;
                    return function () {
                        if (resizeTimer > -1)
                            clearTimeout(resizeTimer);
                        resizeTimer = setTimeout(function () {
                            render(containerId, options);
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
                                render(containerId, options);
                            }, (index + 1) * 10);
                        });
                    });
                }

                render(containerId, options);
            })(arguments[0], arguments[1]);
        };

        var barChart = function () {
            var MAX_INTERVAL_NAME_PX_LENGTH = 150,
                NAVIGATION_HEIGHT = 25,
                BAR_HEIGHT = 50,
                BAR_MARGIN = 10;

            var measureText = function (text, classname) {
                if (!text || text.length === 0) return {height: 0, width: 0};

                var container = d3.select('body').append('svg').attr('class', classname);
                container.append('text').attr({x: -1000, y: -1000}).text(text);

                var bbox = container.node().getBBox();
                container.remove();

                return {height: bbox.height, width: bbox.width};
            };

            var truncateText = function (text, size) {
                if (!text || text.length === 0)
                    return "";
                return text.length > size ? text.substr(0, size - 1) + "..." : text;
            };

            var showChartTooltip = function () {
                var tooltip = d3.select(".d3-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "d3-chart-tooltip hidden");
                }

                var tooltipSectionTmpl = '<div class="tooltip-section">' +
                    '<div class="tooltip-key">{name}</div>' +
                    '<div class="tooltip-value">{value}</div>' +
                    '</div>';

                return function (data) {
                    var tooltipSectionsHtml = data.map(function (d) {
                            return template(tooltipSectionTmpl, d);
                        }),
                        html = [].concat.call(tooltipSectionsHtml);

                    tooltip.html(html.join(""))
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                }
            }();

            var createSvg = function (containerId, width, height, margin) {
                var svg = d3.select(containerId).append("svg")
                    .attr("class", "d3-bar-chart")
                    .attr("width", width + margin.left + margin.right)
                    .attr("height", height + margin.top + margin.bottom)
                    .append("g")
                    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

                svg.append("g")
                    .attr("class", "x axis");

                svg.append("g")
                    .attr("class", "y axis");

                return svg;
            };

            var createBar = function (svg, x, y, barHeight, maxCharsCount, data) {
                var bar = svg.insert("g", ".y.axis")
                    .attr("class", "enter")
                    .attr("transform", "translate(0,5)")
                    .selectAll("g")
                    .data(data)
                    .enter().append("g");

                bar.append("rect")
                    .attr("width", function (d) {
                        return x(d.value);
                    })
                    .attr("height", barHeight)
                    .attr("x", 0)
                    .attr("y", function (d, i) {
                        return y(i) - barHeight / 2;
                    });

                bar.append("text")
                    .attr("x", -20)
                    .attr("y", function (d, i) {
                        return y(i) - barHeight / 2;
                    })
                    .attr("dy", "2em")
                    .style("text-anchor", "end")
                    .text(function (d) {
                        return truncateText(d.name, maxCharsCount);
                    })
                    .on("mouseover", function (d) {
                        d3.select(".d3-chart-tooltip").classed("hidden", false);
                    })
                    .on("mouseout", function (d) {
                        d3.select(".d3-chart-tooltip").classed("hidden", true);
                    })
                    .on("mousemove", function (d) {
                        showChartTooltip([
                            {
                                name: d.name,
                                value: valueFormat(d.value)
                            }
                        ]);
                    });

                bar.append("text")
                    .attr("x", function (d) {
                        if (d.valueTextSize.width + 20 > x(d.value))
                            return x(d.value) + 30;

                        return x(d.value) / 2
                    })
                    .attr("y", function (d, i) {
                        return y(i) - barHeight / 2;
                    })
                    .style("fill", function (d) {
                        if (d.valueTextSize.width + 20 > x(d.value))
                            return "steelblue";

                        return "white";
                    })
                    .attr("dx", -3)
                    .attr("dy", "2em")
                    .style("text-anchor", function (d) {
                        if (d.valueTextSize.width + 20 > x(d.value))
                            return "start";

                        return "middle";
                    })
                    .attr("class", "valueText")
                    .text(function (d) {
                        return valueFormat(d.value);
                    });

                return bar;
            };

            var drawBars = function () {
                return function (svg, x, y, xAxis, yAxis, maxCharsCount, data) {
                    var exit = svg.selectAll(".enter")
                        .attr("class", "exit");

                    var exitTransition = exit.transition()
                        .duration(750)
                        .style("opacity", 1e-6)
                        .remove();

                    x.domain([0, d3.max(data, function (d) {
                        return d.value;
                    })]).nice(5);

                    y.domain(data.map(function (d, i) {
                        return i;
                    }));

                    svg.selectAll(".x.axis").transition()
                        .duration(750)
                        .call(xAxis);

                    var enter = createBar(svg, x, y, BAR_HEIGHT, maxCharsCount, data)
                        .style("opacity", 1);

                    enter.select("text").style("fill-opacity", 1e-6);

                    var enterTransition = enter.transition()
                        .duration(750)
                        .delay(function (d, i) {
                            return i * 25;
                        });

                    enterTransition.select("rect")
                        .attr("width", function (d) {
                            return x(d.value);
                        });

                    enterTransition.select("text")
                        .style("fill-opacity", 1);
                };
            };

            var getNameSettings = function (data) {
                var maxPxLength = d3.max(data, function (d) {
                    return d.nameTextSize.width;
                });

                var dataEls = data.filter(function (el) {
                    return el.nameTextSize.width === maxPxLength;
                });

                if (dataEls.length == 0) {
                    return {
                        maxNamePxLength: MAX_INTERVAL_NAME_PX_LENGTH,
                        maxCharsCount: 10
                    }
                }

                var maxPixelLength = dataEls[0].nameTextSize.width,
                    maxCharsLength = dataEls[0].name.length;

                var maxNamePxLength = maxPixelLength > MAX_INTERVAL_NAME_PX_LENGTH ? MAX_INTERVAL_NAME_PX_LENGTH : maxPixelLength,
                    maxCharsCount = maxPixelLength > MAX_INTERVAL_NAME_PX_LENGTH ? Math.floor(MAX_INTERVAL_NAME_PX_LENGTH / maxPixelLength * maxCharsLength) : maxCharsLength;

                return {
                    maxNamePxLength: maxNamePxLength,
                    maxCharsCount: maxCharsCount
                }
            };

            return (barChart = function (containerId, params) {
                var data = params.data.map(function (d) {
                    return {
                        name: d[0],
                        value: d[1],
                        nameTextSize: measureText("" + d[0], "d3-bar-chart-text-measure"),
                        valueTextSize: measureText("" + d[1], "d3-bar-chart-text-measure")
                    };
                });

                var namesSettings = getNameSettings(data);

                var margin = {top: 30, right: 50, bottom: 30, left: namesSettings.maxNamePxLength + 50},
                    width = params.width - margin.left - margin.right,
                    height = params.height - margin.top - margin.bottom - NAVIGATION_HEIGHT;

                var perSlice = Math.floor((height) / (BAR_MARGIN * 2 + BAR_HEIGHT)),
                    showNavigation = data.length > perSlice,
                    slicer = slicesManager(perSlice, data);

                var x = d3.scale.linear().range([0, width]),
                    y = d3.scale.ordinal().rangeRoundBands([0, height], 1);

                var xAxis = d3.svg.axis()
                    .scale(x)
                    .orient("top")
                    .ticks(5)
                    .tickFormat(valueFormat);

                var yAxis = d3.svg.axis()
                    .scale(y)
                    .orient("left");

                var svg = createSvg(containerId, width, height, margin);
                var draw = drawBars();

                draw(svg, x, y, xAxis, yAxis, namesSettings.maxCharsCount, slicer.getSlice(1));

                if (showNavigation) {
                    var navigationDiv = d3.select(containerId).append("div")
                        .attr("class", "d3-bar-chart-navigation");

                    navigationDiv.append("span")
                        .attr("id", "prevSlide")
                        .attr("class", "glyphicon glyphicon-chevron-left")
                        .on("click", function () {
                            draw(svg, x, y, xAxis, yAxis, namesSettings.maxCharsCount, slicer.prev());
                        });

                    navigationDiv.append("span")
                        .attr("id", "nextSlide")
                        .attr("class", "glyphicon glyphicon-chevron-right")
                        .on("click", function () {
                            draw(svg, x, y, xAxis, yAxis, namesSettings.maxCharsCount, slicer.next());
                        });
                }
            })(arguments[0], arguments[1]);
        };

        var gaugeChart = function () {
            var zoneColours = {
                "green": "#109618",
                "yellow": "#FFFF00",
                "orange": "#FF9900",
                "red": "#DC3912"
            };

            /**
             * Represents gauge.
             * http://bl.ocks.org/tomerd/1499279.
             */
            function Gauge(placeholderSelector, configuration) {
                this.placeholderSelector = placeholderSelector;

                var self = this; // for internal d3 functions

                this.configure = function (configuration) {
                    this.config = configuration;

                    this.config.size = this.config.size * 0.9;

                    this.config.raduis = this.config.size * 0.97 / 2;
                    this.config.cx = this.config.size / 2;
                    this.config.cy = this.config.size / 2;

                    this.config.min = undefined != configuration.min ? configuration.min : 0;
                    this.config.max = undefined != configuration.max ? configuration.max : 100;
                    this.config.range = this.config.max - this.config.min;
                    this.config.current = undefined != configuration.current ? configuration.current : 0;

                    this.config.majorTicks = configuration.majorTicks || 5;
                    this.config.minorTicks = configuration.minorTicks || 2;

                    this.config.transitionDuration = configuration.transitionDuration || 500;
                };

                this.render = function () {
                    this.body = d3.select(this.placeholderSelector)
                        .append("svg:svg")
                        .attr("class", "gauge")
                        .attr("width", this.config.size)
                        .attr("height", this.config.size)
                        .on("mouseover", function () {
                            d3.select(".d3-chart-tooltip").classed("hidden", false);
                        })
                        .on("mouseout", function () {
                            d3.select(".d3-chart-tooltip").classed("hidden", true);
                        })
                        .on("mousemove", function () {
                            _showChartTooltip(self.config.caption, [
                                {
                                    name: "Min",
                                    value: valueFormat(self.config.min)
                                },
                                {
                                    name: "Current",
                                    value: valueFormat(self.config.current)
                                },
                                {
                                    name: "Max",
                                    value: valueFormat(self.config.max)
                                }
                            ]);
                        });

                    this.body.append("svg:circle")
                        .attr("cx", this.config.cx)
                        .attr("cy", this.config.cy)
                        .attr("r", this.config.raduis)
                        .style("fill", "#ccc")
                        .style("stroke", "#000")
                        .style("stroke-width", "0.5px");

                    this.body.append("svg:circle")
                        .attr("cx", this.config.cx)
                        .attr("cy", this.config.cy)
                        .attr("r", 0.9 * this.config.raduis)
                        .style("fill", "#fff")
                        .style("stroke", "#e0e0e0")
                        .style("stroke-width", "2px");

                    // draw band zones
                    this.config.bandZones.forEach(function (zone) {
                        self.drawBand(zone.from, zone.to, zone.colorCode);
                    });

                    // show label
                    if (undefined != this.config.label) {
                        var labelFontSize = Math.round(this.config.size / 9);
                        this.body.append("svg:text")
                            .attr("x", this.config.cx)
                            .attr("y", this.config.cy / 2 + labelFontSize / 2)
                            .attr("dy", labelFontSize / 2)
                            .attr("text-anchor", "middle")
                            .text(this.config.label)
                            .style("font-size", labelFontSize + "px")
                            .style("fill", "#333")
                            .style("stroke-width", "0px");
                    }

                    var majorDelta = this.config.range / (this.config.majorTicks - 1);
                    for (var major = this.config.min; major <= this.config.max; major += majorDelta) {
                        var minorDelta = majorDelta / this.config.minorTicks;
                        // show minor tick marks
                        for (var minor = major + minorDelta; minor < Math.min(major + majorDelta, this.config.max); minor += minorDelta) {
                            var minorPoint1 = this.valueToPoint(minor, 0.75);
                            var minorPoint2 = this.valueToPoint(minor, 0.85);

                            this.body.append("svg:line")
                                .attr("x1", minorPoint1.x)
                                .attr("y1", minorPoint1.y)
                                .attr("x2", minorPoint2.x)
                                .attr("y2", minorPoint2.y)
                                .style("stroke", "#666")
                                .style("stroke-width", "1px");
                        }

                        var majorPoint1 = this.valueToPoint(major, 0.7);
                        var majorPoint2 = this.valueToPoint(major, 0.85);
                        // show major tick marks
                        this.body.append("svg:line")
                            .attr("x1", majorPoint1.x)
                            .attr("y1", majorPoint1.y)
                            .attr("x2", majorPoint2.x)
                            .attr("y2", majorPoint2.y)
                            .style("stroke", "#333")
                            .style("stroke-width", "2px");

                        // show min or max tick values
                        if (major == this.config.min || major == this.config.max) {
                            var minMaxFontSize = Math.round(this.config.size / 16);
                            var point = this.valueToPoint(major, 0.63);

                            this.body.append("svg:text")
                                .attr("x", point.x)
                                .attr("y", point.y)
                                .attr("dy", minMaxFontSize / 3)
                                .attr("text-anchor", major == this.config.min ? "start" : "end")
                                .text(siPrefixFormat(major, 1))
                                .style("font-size", minMaxFontSize + "px")
                                .style("fill", "#333")
                                .style("stroke-width", "0px");
                        }
                    }

                    var pointerContainer = this.body.append("svg:g").attr("class", "pointerContainer");

                    var midValue = (this.config.min + this.config.max) / 2;

                    var pointerPath = this.buildPointerPath(midValue);

                    var pointerLine = d3.svg.line()
                        .x(function (d) {
                            return d.x
                        })
                        .y(function (d) {
                            return d.y
                        })
                        .interpolate("basis");

                    pointerContainer.selectAll("path")
                        .data([pointerPath])
                        .enter()
                        .append("svg:path")
                        .attr("d", pointerLine)
                        .style("fill", "#dc3912")
                        .style("stroke", "#c63310")
                        .style("fill-opacity", 0.7);

                    pointerContainer.append("svg:circle")
                        .attr("cx", this.config.cx)
                        .attr("cy", this.config.cy)
                        .attr("r", 0.12 * this.config.raduis)
                        .style("fill", "#4684EE")
                        .style("stroke", "#666")
                        .style("opacity", 1);

                    var fontSize = Math.round(this.config.size / 10);
                    pointerContainer.selectAll("text")
                        .data([midValue])
                        .enter()
                        .append("svg:text")
                        .attr("x", this.config.cx)
                        .attr("y", this.config.size - this.config.cy / 4 - fontSize)
                        .attr("dy", fontSize / 2)
                        .attr("text-anchor", "middle")
                        .style("font-size", fontSize + "px")
                        .style("fill", "#000")
                        .style("stroke-width", "0px");

                    this.redraw(this.config.min, 0);
                };

                this.buildPointerPath = function (value) {
                    var delta = this.config.range / 13;

                    var head = valueToPoint(value, 0.85);
                    var head1 = valueToPoint(value - delta, 0.12);
                    var head2 = valueToPoint(value + delta, 0.12);

                    var tailValue = value - (this.config.range * (1 / (270 / 360)) / 2);
                    var tail = valueToPoint(tailValue, 0.28);
                    var tail1 = valueToPoint(tailValue - delta, 0.12);
                    var tail2 = valueToPoint(tailValue + delta, 0.12);

                    return [head, head1, tail2, tail, tail1, head2, head];

                    function valueToPoint(value, factor) {
                        var point = self.valueToPoint(value, factor);
                        point.x -= self.config.cx;
                        point.y -= self.config.cy;
                        return point;
                    }
                };

                this.drawBand = function (start, end, color) {
                    if (0 >= end - start) return;

                    this.body.append("svg:path")
                        .style("fill", color)
                        .attr("d", d3.svg.arc()
                            .startAngle(this.valueToRadians(start))
                            .endAngle(this.valueToRadians(end))
                            .innerRadius(0.65 * this.config.raduis)
                            .outerRadius(0.85 * this.config.raduis))
                        .attr("transform", function () {
                            return "translate(" + self.config.cx + ", " + self.config.cy + ") rotate(270)"
                        });
                };

                this.redraw = function (value, transitionDuration) {
                    var pointerContainer = this.body.select(".pointerContainer");

                    var valueToShow = this.config.complete ? siPrefixFormat(value, 1) : "Not Ready";
                    pointerContainer.selectAll("text").text(valueToShow);

                    var pointer = pointerContainer.selectAll("path");
                    pointer.transition()
                        .duration(undefined != transitionDuration ? transitionDuration : this.config.transitionDuration)
                        .attrTween("transform", function () {
                            var pointerValue = value;
                            if (value > self.config.max) pointerValue = self.config.max + 0.02 * self.config.range;
                            else if (value < self.config.min) pointerValue = self.config.min - 0.02 * self.config.range;
                            var targetRotation = (self.valueToDegrees(pointerValue) - 90);
                            var currentRotation = self._currentRotation || targetRotation;
                            self._currentRotation = targetRotation;

                            return function (step) {
                                var rotation = currentRotation + (targetRotation - currentRotation) * step;
                                return "translate(" + self.config.cx + ", " + self.config.cy + ") rotate(" + rotation + ")";
                            }
                        });
                };

                this.valueToDegrees = function (value) {
                    return value / this.config.range * 270 - (this.config.min / this.config.range * 270 + 45);
                };

                this.valueToRadians = function (value) {
                    return this.valueToDegrees(value) * Math.PI / 180;
                };

                this.valueToPoint = function (value, factor) {
                    return {
                        x: this.config.cx - this.config.raduis * factor * Math.cos(this.valueToRadians(value)),
                        y: this.config.cy - this.config.raduis * factor * Math.sin(this.valueToRadians(value))
                    };
                };

                // initialization
                this.configure(configuration);
            }

            var _showChartTooltip = function () {
                var tooltip = d3.select(".d3-chart-tooltip");
                if (tooltip.empty()) {
                    tooltip = d3.select("body").append("div")
                        .attr("class", "d3-chart-tooltip hidden");
                }

                var titleSectionTmpl = '<div class="tooltip-section tooltip-title"><strong>{title}</strong></div>',
                    tooltipSectionTmpl = '<div class="tooltip-section">' +
                        '<div class="tooltip-key">{name}</div>' +
                        '<div class="tooltip-value">{value}</div>' +
                        '</div>';

                return function (title, data) {
                    var titleSectionHtml = template(titleSectionTmpl, {title: title}),
                        tooltipSectionsHtml = data.map(function (d) {
                            return template(tooltipSectionTmpl, d);
                        }),
                        html = [].concat.call(titleSectionHtml, tooltipSectionsHtml);

                    tooltip.html(html.join(""))
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                }
            }();

            var _createGauge = function (containerId, config) {
                var range = config.max - config.min;

                config.bandZones = [];

                config.zones.forEach(function (zone) {
                    if (!zone || !zone.enabled || !zone.color)
                        return;

                    config.bandZones.push({
                        colorCode: zone.colorCode || zoneColours[zone.color],
                        from: config.min + range * zone.from,
                        to: config.min + range * zone.to
                    })
                });

                var gauge = new Gauge(containerId, config);
                _gauges.push(gauge);

                gauge.render();
                gauge.redraw(config.current);
            };

            var _gauges = [];

            return (gaugeChart = function (container, params) {
                var d = params.data;

                var config = {
                    complete: d.complete,
                    size: 160,
                    label: d.name,
                    caption: d.caption,
                    min: undefined != d.min ? d.min : 0,
                    max: undefined != d.max ? d.max : 100,
                    minorTicks: 5,
                    current: undefined != d.current ? d.current : 0,
                    zones: d.zones || []
                };

                _createGauge(container, config);
            })(arguments[0], arguments[1]);
        };

        var heatMapChart = function () {

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

                    tooltip.html(html.join(""))
                        .style("left", (d3.event.pageX) + "px")
                        .style("top", (d3.event.pageY - 28) + "px");
                }
            }();


            function renderHeatMap(svg, data, conf) {
                var lowest = Number.POSITIVE_INFINITY;
                var highest = Number.NEGATIVE_INFINITY;
                var tmp;
                for (var i = data.length - 1; i >= 0; i--) {
                    tmp = data[i].value;
                    if (tmp < lowest) lowest = tmp;
                    if (tmp > highest) highest = tmp;
                }

                var legendHeight = 15,
                    col_number = conf.col_number,
                    row_number = conf.row_number,
                    width = conf.width,
                    height = conf.height - 2*legendHeight,
                    xCellSize = width / row_number,
                    yCellSize = height / col_number,
                    border = 1,
                    bordercolor = '#335E82',
                    hcrow = conf.hcrow,
                    hccol = conf.hccol;


                    var colorScale = d3.scale.linear()
                        .domain([lowest, highest])
                        .range(["white", "#1f77b4"]);

                    var legendScale = d3.scale.linear()
                        .domain([lowest, highest])
                        .range([10, width - 10]);

                    var onMouseMove = function (d, color) {
                        showChartTooltip(this, [
                                {
                                    color: color,
                                    name: d.statName,
                                    value: d.value
                                }
                            ]
                        );
                    };

                    var heatMapContainer = svg.append("g").attr("class", "g3");
                    var heatMap = heatMapContainer.selectAll(".cellg")
                        .data(data, function (d) {
                            return d.col + ':' + d.row;
                        })
                        .enter()
                        .append("rect")
                        .attr("x", function (d) {
                            return hccol.indexOf(d.col) * xCellSize;
                        })
                        .attr("y", function (d) {
                            return hcrow.indexOf(d.row) * yCellSize;
                        })
                        .attr("class", function (d) {
                            return "cell cell-border cr" + (d.row - 1) + " cc" + (d.col - 1);
                        })
                        .attr("width", xCellSize)
                        .attr("height", yCellSize)
                        .style("fill", function (d) {
                            return colorScale(d.value);
                        })
                        .on("mouseover", function () {
                            d3.select(".d3-chart-tooltip").classed("hidden", false);
                        })
                        .on("mousemove", function (d) {
                            onMouseMove(d, colorScale(d.value));
                            legendPointer
                                .transition()
                                .attr("transform", "translate(" + _getLegendCursorPosition(d.value) + "," + (height + 8) + ")")
                        })
                        .on("mouseout", function () {
                            d3.select(".d3-chart-tooltip").classed("hidden", true);
                        });


                    var _getLegendCursorPosition = function (value) {
                        return legendScale(value);
                    }

                    //----------------
                    //border for heatmap container
                    //----------------
                    var borderPath = heatMapContainer.append("rect")
                        .attr("x", 0)
                        .attr("y", 0)
                        .attr("height", yCellSize * col_number)
                        .attr("width", xCellSize * row_number)
                        .style("stroke", bordercolor)
                        .style("fill", "none")
                        .style("stroke-width", border);

                    //----------------
                    //Legend pointer
                    //-----------------

                    var legendPointer = svg.selectAll(".lehendPointer")
                        .data([1])
                        .enter()
                        .append("path")
                        .attr("d", d3.svg.symbol().type("triangle-down"))
                        .attr("transform", "translate(100," + (height + 8) + ")")
                        .style("fill", "grey");

            };

            function renderLegend(svg, conf) {
                //----------------
                //Gradient legend
                //----------------
                var legend = svg.selectAll(".legend")
                    .data([1])
                    .enter().append("g")
                    .attr("class", "legend");

                var gradient = legend
                    .append("linearGradient")
                    .attr("y1", "0")
                    .attr("y2", "0")
                    .attr("x1", 0)
                    .attr("x2", conf.width)
                    .attr("id", "gradient")
                    .attr("gradientUnits", "userSpaceOnUse")

                gradient
                    .append("stop")
                    .attr("offset", "0")
                    .attr("stop-color", "#ffffff")

                gradient
                    .append("stop")
                    .attr("offset", "1")
                    .attr("stop-color", "#1f77b4")

                legend
                    .append("rect")
                    .attr("x", 0)
                    .attr("y", conf.height + (conf.legendCellSize))
                    .attr("width", conf.width)
                    .attr("height", conf.legendHeight)
                    .attr("fill", "url(#gradient)");
            };

            function _getdataPoins(dataList, mapColumnSize) {
                var dataPoints = new Array(),
                    i = 0,
                    j = 0,
                    rowStep = 0;
                dataList.forEach(function(item, k, dataList) {
                    dataPoints.push({
                            value: item.value,
                            row: j,
                            col: i,
                            statName: item.name
                        }
                    );
                    rowStep++;
                    if (rowStep < mapColumnSize) i++;
                    else {
                        j++;
                        i = 0;
                        rowStep = 0;
                    }
                });

                return dataPoints;
            }

            return (heatMapChart = function (containerId, params) {
                var width = params.width,
                    height = params.height;

                var dataList = params.data.map(function (d) {
                    return {
                        name: d[0],
                        value: d[1]
                    };
                });

                var mapColumnSize = dataList.length == 1? 1:Math.round(Math.sqrt(dataList.length)) + 1,
                    mapRowSize = Math.ceil( dataList.length / mapColumnSize);

                var xCategories = Array.apply(null, {length: mapRowSize}).map(Number.call, Number),
                    yCategories = Array.apply(null, {length: mapColumnSize}).map(Number.call, Number)


                var dataPoints = _getdataPoins(dataList, mapColumnSize);

                var conf = {
                    col_number: mapRowSize,
                    row_number: mapColumnSize ,
                    hcrow: xCategories,
                    hccol: yCategories,
                    width: width,
                    height: height
                };

                var svg = d3.select(containerId).append("svg")
                    .attr("width", width)
                    .attr("class", "heatMap")
                    .attr("height", height)
                    .append("g");

                renderHeatMap(svg, dataPoints, conf);


                var legendConf = {
                    width: width,
                    height: height,
                    legendCellSize: 12,
                    legendHeight: 15
                };

                renderLegend(svg, legendConf);

            })(arguments[0], arguments[1]);
        };


        return function (id, params) {
            switch (params.type) {
                case "PieChart":
                    pieChart(id, params);
                    break;
                case "ColumnChart":
                    barChart(id, params);
                    break;
                case "LineChart":
                    lineChart(id, params);
                    break;
                case "GaugeChart":
                    gaugeChart(id, params);
                    break;
                case "HeatMapChart":
                    heatMapChart(id, params);
                    break;
                default:
                    lineChart(id, params);
            }
        }
    }

    return {
        getInstance: function () {
            if (instance === undefined) {
                instance = createD3chart();
            }
            return instance;
        }
    };
})();

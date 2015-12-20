<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <ano:equal name="showHelp" value="true">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            This Dashboard is yet empty. Add some elements to it by editing <em>moskito.json</em>.<br/>For more details on MoSKito Configuration visit: <a href="https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Configuration+Guide">https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Configuration+Guide</a>.
        </div>
    </ano:equal>

    <div class="content">

        <ano:equal name="gaugesPresent" value="true">
            <!-- gauges js -->
            <script language="JavaScript">
                var gauges = [];
                <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.api.GaugeAO" id="gauge">
                gauges.push({
                    "name": '${gauge.name}',
                    "caption": '${gauge.caption}',
                    "complete": ${gauge.complete},
                    "min": ${gauge.min.rawValue},
                    "current": ${gauge.current.rawValue},
                    "max": ${gauge.max.rawValue},
                    "zones": <ano:equal name="gauge" property="customZonesAvailable" value="false">[]</ano:equal>
                            <ano:equal name="gauge" property="customZonesAvailable" value="true">
                            [
                                <ano:iterate id="zone" name="gauge" property="zones" type="net.anotheria.moskito.webui.gauges.api.GaugeZoneAO" indexId="zoneIndex"><ano:greaterThan name="zoneIndex" value="0">, </ano:greaterThan>
                                    {
                                        "color": '${zone.color}',
                                        "colorCode": '',
                                        "enabled": true,
                                        "from": ${zone.left},
                                        "to": ${zone.right}
                                    }
                                </ano:iterate>
                            ]
                            </ano:equal>

                });
                </ano:iterate>
            </script>

            <!-- gauges -->
            <div class="dashboard-line">
                <div class="row">
                    <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.api.GaugeAO" id="gauge" indexId="index">
                        <div class="col-lg-3 col-md-4 col-sm-6">
                            <div class="box gauge-item">
                                <div class="box-title">
                                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#gauge_collapse_chart${index}"><i class="fa fa-caret-right"></i></a>

                                    <h3 class="pull-left chart-header">
                                        ${gauge.caption}
                                    </h3>
                                </div>
                                <div id="gauge_collapse_chart${index}" class="box-content accordion-body collapse in">
                                    <div class="paddner text-center">
                                        <div id="gaugeChart${index}" class="gauge-content gauge-chart"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ano:iterate>
                </div>

                <div class="dashboard-line-footer text-right">
                    <ul class="dashboard-line-nav-box list-unstyled">
                        <li>
                            <a onclick="saveGuagesSvgAsPng()" class="save_as"><i class="fa fa-download"></i> Save all Gauges</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- // end gauges -->

            <script type="text/javascript">
                var chartEngineName = '${chartEngine}' || 'GOOGLE_CHART_API';

                var gaugeContainerSelectors = $('.gauge-chart').map(function () {
                    return $(this).attr("id");
                });

                gauges.forEach(function (gaugeData, index) {
                    var chartParams = {
                        container: gaugeContainerSelectors[index],
                        data: gaugeData,
                        type: 'GaugeChart'
                    };

                    chartEngineIniter[chartEngineName](chartParams);
                });
            </script>
        </ano:equal>

        <ano:equal name="chartsPresent" value="true">
            <script type="text/javascript">
                var multipleGraphData = [];
                var multipleGraphNames = [];
                var multipleGraphColors = [];
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.api.DashboardChartAO">
                <ano:define id="singleChart" toScope="page" scope="page" name="chart" property="chart" type="net.anotheria.moskito.webui.accumulators.api.MultilineChartAO"/>
                multipleGraphData.push([
                    <ano:iterate name="singleChart" property="data" id="value" indexId="i">
                    <ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
                    </ano:iterate>
                ]);
                multipleGraphNames.push([
                    <ano:iterate name="singleChart" property="names" id="lineName" indexId="i">
                    <ano:notEqual name="i" value="0">,</ano:notEqual>'<ano:write name="lineName"/>'
                    </ano:iterate>
                ]);
                multipleGraphColors.push(
                    <ano:write name="singleChart" property="accumulatorsColorsDataJSON"/>
                );
                </ano:iterate>
            </script>

        <div class="dashboard-line">

            <div class="row">
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.api.DashboardChartAO" indexId="index">
                <div class="col-lg-6 col-md-12">
                    <div class="box">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse_chart${index}"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left chart-header">
                                ${chart.caption}
                            </h3>

                            <div class="box-right-nav">
                                <a class="tooltip-bottom save_as" title="Save" onclick="saveSvgAsPng(${index}+4)"><i class="fa fa-download"></i></a>
                            </div>
                        </div>
                        <div id="collapse_chart${index}" class="box-content accordion-body collapse in">
                            <div class="paddner"><div id="chart_div${index}" class="accumulator-chart"></div></div>
                        </div>
                    </div>
                </div>
                </ano:iterate>
            </div>

        </div>


            <script type="text/javascript">
                var chartEngineName = '${chartEngine}' || 'GOOGLE_CHART_API';

                var names = multipleGraphNames.map(function (graphNames) {
                    return graphNames;
                });

                var containerSelectors = $('.accumulator-chart').map(function () {
                    return $(this).attr("id");
                });

                multipleGraphData.forEach(function (graphData, index) {
                    var chartParams = {
                        container: containerSelectors[index],
                        names: names[index],
                        data: graphData,
                        colors: multipleGraphColors[index],
                        type: 'LineChart',
                        title: names[index],
                        dataType: 'datetime',
                        options: {
                            legendsPerSlice: 5,
                            margin: {top: 20, right: 10, bottom: 20, left: 40}
                        }
                    };

                    chartEngineIniter[chartEngineName](chartParams);
                });
            </script>
        </ano:equal>

        //Guages
        <script type="text/javascript">
             function saveGuagesSvgAsPng() {

//                 var xValBegin= 100, xStep = 200;
                 var guageWidth = 144,
                         guageHeight = 144,
                         marginLeft = 50,
                         marginRight = 50,
                         marginTop = 50,
                         marginBottom = 50,
                         indent=20;

                 var allSvgsCode = '<svg xmlns="http://www.w3.org/2000/svg" class="gauge1" width="800" height="244" style="background-color: #FFFFFF;">';


                var svgs = document.getElementsByClassName("gauge");
                 for(var i = 0; i < svgs.length;i++) {
                     var svgOrigin =svgs[i];

                     //copy svg chart
                     var svg = svgOrigin.cloneNode(true);

                     svg.setAttribute("x", marginLeft + i*(guageWidth+indent))
                     svg.setAttribute("y", marginTop)
                     svg.setAttribute("style", "background-color: #FFFFFF;");

                     var css = '.axis path,' +
                     '.axis line {' +
                        'fill: none;' +
                        'stroke: #000;' +
                        'shape-rendering: crispEdges;' +
                     '}' +
                        '.legend, .tick {' +
                        'font: 12px sans-serif;' +
                     '}' +
                      'text {' +
                         'font: 12px sans-serif;' +
                     '}' +
                     '.line {' +
                        'fill: none;' +
                        'stroke: steelblue;' +
                        'stroke-width: 1.5px;' +
                     '}' +
                     '.line.hover {' +
                        'fill: none;' +
                        'stroke: steelblue;' +
                        'stroke-width: 3.0px;' +
                     '}' +

                     '.grid .tick {' +
                        'stroke: lightgrey;' +
                        'opacity: 0.7;' +
                     '}' +
                     '.grid path {' +
                        'stroke-width: 0;' +
                     '}';

                     var style = document.createElement('style');
                     style.type = 'text/css';
                     if (style.styleSheet){
                         style.styleSheet.cssText = css;
                     } else {
                         style.appendChild(document.createTextNode(css));
                     }

                     svg.appendChild(style);

                     allSvgsCode+= new XMLSerializer().serializeToString(svg);
                 }
                 allSvgsCode+='</svg>';

                 var svgData = allSvgsCode;
                 var canvas = document.createElement("canvas");
                 canvas.width  = marginLeft + marginRight+svgs.length*guageHeight+(svgs.length-1)*indent;
                 canvas.height = guageHeight + marginBottom+marginTop;
                 var ctx = canvas.getContext("2d");
                 ctx.fillStyle="white";
                 ctx.fill();
                 var img = document.createElement("img");

                 var imagesData=btoa(svgData)

                img.setAttribute("src", "data:image/svg+xml;base64," + imagesData);

                img.onload = function () {
                    ctx.drawImage(img, 0, 0);
                    var canvasdata = canvas.toDataURL("image/png")
                    var a = document.createElement("a");
                    var file_name = getChartFileNameG();

                    a.download = file_name + ".png";
                    a.href = canvasdata;
                    document.body.appendChild(a);
                    a.click();

                };
            };

            function getChartFileNameG() {
                var t = new Date($.now());
                var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes()
                return "Guages_"+current_date;
            }
        </script>

        <script type="text/javascript">
            function saveSvgAsPng(index) {
                var chartWidth = 525,
                        chartHeight = 321,
                        margin = 30;

                var svgOrigin = document.getElementsByTagName("svg")[index];
                var svg = svgOrigin.cloneNode(true);


                svg.setAttribute("style", "background-color: #FFFFFF;");
                svg.setAttribute("x",margin);
                svg.setAttribute("y",margin);

                var css = '.axis path,'+
                '.axis line {'+
                'fill: none;'+
                'stroke: #000;'+
                'shape-rendering: crispEdges;'+
                '}'+
                '.legend, .tick {'+
                'font: 12px sans-serif;'+
                '}'+

                '.line {'+
                'fill: none;'+
                'stroke: steelblue;'+
                'stroke-width: 1.5px;'+
                '}'+

                '.line.hover {'+
                'fill: none;'+
                'stroke: steelblue;'+
                'stroke-width: 3.0px;'+
                '}'+

                '.grid .tick {'+
                'stroke: lightgrey;'+
                'opacity: 0.7;'+
                '}'+
                '.grid path {'+
                'stroke-width: 0;'+
                '}';

                var style = document.createElement('style');
                style.type = 'text/css';
                if (style.styleSheet){
                    style.styleSheet.cssText = css;
                } else {
                    style.appendChild(document.createTextNode(css));
                }

                svg.appendChild(style);

                var svgData = new XMLSerializer().serializeToString(svg);

                svgData ='<svg xmlns="http://www.w3.org/2000/svg"  style="background-color: #FFFFFF;" width="800" height="381" >' + svgData + '</svg>';

                var canvas = document.createElement("canvas");
                canvas.width  = chartWidth + 2*margin;
                canvas.height = chartHeight + 2*margin;
                var ctx = canvas.getContext("2d");
                ctx.fillStyle="white";
                ctx.fill();

                var img = document.createElement("img");
                img.setAttribute("src", "data:image/svg+xml;base64," + btoa(svgData));

                img.onload = function () {
                    ctx.drawImage(img, 0, 0);
                    var canvasdata = canvas.toDataURL("image/png")
                    var a = document.createElement("a");
                    var file_name = getChartFileName(index);

                    a.download = file_name + ".png";
                    a.href = canvasdata;
                    document.body.appendChild(a);
                    a.click();

                };
            };

            function getChartFileName(index) {
                var t = new Date($.now());
                var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes()
                var chart_header = document.getElementsByClassName("chart-header")[index];
                return $.trim(chart_header.innerText).split(' ').join('_')+'_'+current_date;
            }
        </script>

        <ano:equal name="thresholdsPresent" value="true">
        <div class="dashboard-line">
            <div class="row">
                <ano:iterate name="thresholds" type="net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO" id="threshold">
                <div class="col-lg-2 col-md-3 col-sm-4">
                    <div class="box threshold-item tooltip-bottom" title="${threshold.name} ${threshold.value}">
                        <i class="status status-${threshold.colorCode}"></i>
                        <span class="threshold-title">${threshold.name}&nbsp;${threshold.value}</span>
                    </div>
                </div>
                </ano:iterate>
            </div>
        </div>
        </ano:equal>

    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>

</body>
</html>



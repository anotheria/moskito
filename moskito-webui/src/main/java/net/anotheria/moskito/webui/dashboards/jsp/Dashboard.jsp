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

    <ano:present name="infoMessage">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            ${requestScope.infoMessage}
        </div>
    </ano:present>

    <div class="content">
        <ano:equal name="thresholdsPresent" value="true">
            <!-- Thresholds start -->
            <div class="dashboard-line">
                <div class="row">
                    <ano:iterate name="thresholds" type="net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean" id="threshold">
                        <div class="col-lg-3 col-md-3 col-sm-4">
                            <div class="box threshold-item tooltip-bottom">
                                <i class="status status-${threshold.colorCode}"></i>
                                <span class="threshold-title">${threshold.name}&nbsp;${threshold.value}</span>
                                <div class="box-right-nav dropdown threshold-body">
                                    <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                    <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                        <ano:iF test="${requestScope.selectedDashboard == null && threshold.dashboardsToAdd != ''}">
                                            <li><a onclick="addTresholds('${threshold.name}', '${threshold.dashboardsToAdd}')" >Add to Dashboard</a></li>
                                        </ano:iF>
                                        <ano:iF test="${requestScope.selectedDashboard != null}">
                                            <li><a onclick="removeTresholds('${threshold.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
                                        </ano:iF>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </ano:iterate>
                </div>
            </div>
            <!-- Thresholds end -->
        </ano:equal>

        <ano:equal name="gaugesPresent" value="true">
            <!-- gauges js -->
            <script language="JavaScript">
                var gauges = [];
                <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.bean.GaugeBean" id="gauge">
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
                                <ano:iterate id="zone" name="gauge" property="zones" type="net.anotheria.moskito.webui.gauges.api.GaugeZoneAO" indexId="zoneIndex"><ano:greaterThan name="zoneIndex" value="0">,</ano:greaterThan>
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

                })
                </ano:iterate>
            </script>

            <!-- gauges -->
            <div class="dashboard-line">
                <div class="row">
                    <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.bean.GaugeBean" id="gauge" indexId="index">
                        <div class="col-lg-3 col-md-4 col-sm-6">
                            <div class="box gauge-item">
                                <div class="box-title">
                                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#gauge_collapse_chart${index}"><i class="fa fa-caret-right"></i></a>

                                    <h3 class="pull-left chart-header">
                                        ${gauge.caption}
                                    </h3>
                                    <div class="box-right-nav dropdown">
                                        <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                            <li><a href="" onclick="saveSvgAsPng(event, ${index})">Save</a></li>
                                            <ano:iF test="${requestScope.selectedDashboard == null && gauge.dashboardsToAdd != ''}">
                                                <li><a onclick="addGauge('${gauge.caption}', '${gauge.name}', '${gauge.dashboardsToAdd}')" >Add to Dashboard</a></li>
                                            </ano:iF>
                                            <ano:iF test="${requestScope.selectedDashboard != null}">
                                                <li><a onclick="removeGauge('${gauge.caption}', '${gauge.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
                                            </ano:iF>
                                        </ul>
                                    </div>
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
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean">
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
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean" indexId="index">
                <div class="col-lg-6 col-md-12">
                    <div class="box">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse_chart${index}"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left chart-header">
                                ${chart.caption}
                            </h3>

                            <div class="box-right-nav dropdown">
                                <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                    <li><a href="" onclick="saveSvgAsPng(event, ${index}+countGauges())">Save</a></li>
                                    <ano:iF test="${requestScope.selectedDashboard == null && chart.dashboardsToAdd != ''}">
                                        <li><a onclick="addChart('${chart.caption}','${chart.dashboardsToAdd}')">Add to Dashboard</a></li>
                                    </ano:iF>
                                    <ano:iF test="${requestScope.selectedDashboard != null}">
                                        <li><a onclick="removeChart('${chart.caption}', '${requestScope.selectedDashboard}')">Remove</a></li>
                                    </ano:iF>
                                </ul>
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

        <ano:iF test="${requestScope.selectedDashboard != null}">
            <div class="dashboard-line">
                <div class="row">
                    <a href="#DeleteDashboard" data-toggle="modal" data-target="#DeleteDashboard" title="Delete Dashboard">Delete this Dashboard</a>
                </div>
            </div>
        </ano:iF>

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

        <!-- Gauges -->
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

                     svg.setAttribute("x", marginLeft + i*(guageWidth+indent));
                     svg.setAttribute("y", marginTop);
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

                 var img = document.createElement("img");
                 var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function(c) {
                     return '&#' + c.charCodeAt(0) + ';';
                 }));
                 img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);

                img.onload = function () {
                    ctx.drawImage(img, 0, 0);
                    var canvasdata = canvas.toDataURL("image/png");
                    var a = document.createElement("a");
                    var file_name = getChartFileNameG();

                    a.download = file_name + ".png";
                    a.href = canvasdata;
                    document.body.appendChild(a);
                    a.click();

                };
             }
             function getChartFileNameG() {
                var t = new Date($.now());
                var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes();
                return "Guages_"+current_date;
            }
        </script>

        <script type="text/javascript">
            function countGauges() {
                return $('.gauge').length;
            }
            function saveSvgAsPng(event, index) {
                event.preventDefault();
                event.stopPropagation();

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
                var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function(c) {
                    return '&#' + c.charCodeAt(0) + ';';
                }));
                img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);

                img.onload = function () {
                    ctx.drawImage(img, 0, 0);
                    var canvasdata = canvas.toDataURL("image/png");
                    var a = document.createElement("a");
                    var file_name = getChartFileName(index);

                    a.download = file_name + ".png";
                    a.href = canvasdata;
                    document.body.appendChild(a);
                    a.click();

                };
            }
            function getChartFileName(index) {
                var t = new Date($.now());
                var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes();
                var chart_header = document.getElementsByClassName("chart-header")[index];
                return $.trim(chart_header.innerText).split(' ').join('_')+'_'+current_date;
            }
        </script>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>

<%--------------------------------- Create/Delete dashboards -----------------------------------%>

<div class="modal fade" id="CreateDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Create Dashboard</h4>
            </div>
            <div class="modal-body">
                <label>Please type new Dashboard name</label>
                <form name="CreateDashboard" action="mskCreateDashboard" method="GET">
                    <div class="form-group">
                        <input type="text" class="form-control" name="pName" placeholder="Name">
                    </div>
                    <div class="form-group text-right">
                        <button class="btn btn-success" type="button" onclick="submit();">Create</button>
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="DeleteDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete Dashboard "${requestScope.selectedDashboard}" ? </h4>
            </div>
            <div class="modal-body">
                <form name="CreateDashboard" action="mskDeleteDashboard" method="GET">
                    <input type="hidden" class="form-control" name="pName" value="${requestScope.selectedDashboard}">
                    <div class="form-group text-right">
                        <button class="btn btn-success" type="button" onclick="submit();">Yes</button>
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%----------------------------------------------------------------------------------------------%>

<%------------------------------ Add/Delete elements to dashboard ------------------------------%>

<div class="modal fade" id="addElementToDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Add <span id="selectedElement">someElement</span> to dashboards</h4>
            </div>
            <form id="addElementToDashboardAction" action="someAction" method="GET">
                <div class="modal-body">
                    <input id="selectedElementName" type="hidden" class="form-control" name="pName" value="test">
                    <div id="dashboardsToSelect">
                    </div>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-success" onclick="submit();">Add</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="modal fade modal-danger" id="removeElementFromDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="removeElementFromDashboardTitle">test</h4>
            </div>

            <div class="modal-body">
                <form id="removeElementFromDashboardAction" action="someAction" method="GET">
                    <input type="hidden" class="form-control" name="pName" value="${requestScope.selectedDashboard}">
                    <input type="hidden" class="form-control" name="pElement" value="test" id="removeElement">
                    <div class="form-group text-right">
                        <button class="btn btn-danger" type="button" onclick="submit();">Yes</button>
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%----------------------------------------------------------------------------------------------%>

<div class="modal fade modal-danger" id="chartDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Remove chart <span id="selectedChartForRemoval">test</span> from Dashboard: '${selectedDashboard}'?</h4>
            </div>
            <div class="modal-footer text-center">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <a href="" onclick="location.href='mskDashboardRemoveChart?chart='+selectedChartForRemoval+'&dashboard=${selectedDashboard}'; return false" class="btn btn-danger">Remove</a>
            </div>
        </div>
    </div>
</div>

<script language="JavaScript">

    function removeChart(chartCaption, dashboard) {
        $("#removeElementFromDashboardTitle").html("Remove chart \"" + chartCaption + "\" from dashboard \""+dashboard+"\"?");
        $("#removeElementFromDashboardAction").attr("action", "mskDashboardRemoveChart");
        $("#removeElement").attr("value", chartCaption);
        $("#removeElementFromDashboard").modal('show');
    }

    function addChart(chartCaption, dashboardsToAdd) {

        $("#selectedElement").html("chart \"" + chartCaption + "\"");
        $("#selectedElementName").attr("value", chartCaption);
        $("#addElementToDashboardAction").attr("action", "mskAddChartToDashboard");

        var dashboards = dashboardsToAdd.split(',');

        var textToAdd = "";
        for (var i = 0; i < dashboards.length; i++) {
            textToAdd +=
                    "<div class=\"checkbox\"> " +
                    "<label>" +
                    "<input type=\"checkbox\" checked name=\"pDashboards\" value=\""+dashboards[i]+"\">" + dashboards[i] +
                    "</label>" +
                    "</div>";

        }
        $("#dashboardsToSelect").html(textToAdd);

        if (dashboards.length == 1) {
            $("#addElementToDashboardAction").submit();
        } else {
            $("#addElementToDashboard").modal('show');
        }
    }

    function removeGauge(gaugeForRemovalCaption, gaugeForRemovalName, dashboard){
        $("#removeElementFromDashboardTitle").html("Remove gauge \"" + gaugeForRemovalCaption + "\" from dashboard \""+dashboard+"\"?");
        $("#removeElementFromDashboardAction").attr("action", "mskDashboardRemoveGauge");
        $("#removeElement").attr("value", gaugeForRemovalName);
        $("#removeElementFromDashboard").modal('show');
    }

    function addGauge(gaugeCaption, gaugeName, dashboardsToAdd){

        $("#selectedElement").html("gauge \"" + gaugeCaption + "\"");
        $("#selectedElementName").attr("value", gaugeName);
        $("#addElementToDashboardAction").attr("action", "mskAddGaugeToDashboard");

        var dashboards = dashboardsToAdd.split(',');

        var textToAdd = "";
        for (var i = 0; i < dashboards.length; i++) {
            textToAdd +=
            "<div class=\"checkbox\"> " +
                "<label>" +
                    "<input type=\"checkbox\" checked name=\"pDashboards\" value=\""+dashboards[i]+"\">" + dashboards[i] +
                "</label>" +
            "</div>";

        }
        $("#dashboardsToSelect").html(textToAdd);

        if (dashboards.length == 1) {
            $("#addElementToDashboardAction").submit();
        } else {
            $("#addElementToDashboard").modal('show');
        }
    }

    function removeTresholds(thresholdName, dashboard) {
        $("#removeElementFromDashboardTitle").html("Remove threshold \"" + thresholdName + "\" from dashboard \""+dashboard+"\"?");
        $("#removeElementFromDashboardAction").attr("action", "mskDashboardRemoveThreshold");
        $("#removeElement").attr("value", thresholdName);
        $("#removeElementFromDashboard").modal('show');
    }

    function addTresholds(thresholdName, dashboardsToAdd) {
        $("#selectedElement").html("threshold \"" + thresholdName + "\"");
        $("#selectedElementName").attr("value", thresholdName);
        $("#addElementToDashboardAction").attr("action", "mskAddThresholdToDashboard");

        var dashboards = dashboardsToAdd.split(',');

        var textToAdd = "";
        for (var i = 0; i < dashboards.length; i++) {
            textToAdd +=
                    "<div class=\"checkbox\"> " +
                    "<label>" +
                    "<input type=\"checkbox\" checked name=\"pDashboards\" value=\""+dashboards[i]+"\">" + dashboards[i] +
                    "</label>" +
                    "</div>";

        }
        $("#dashboardsToSelect").html(textToAdd);

        if (dashboards.length == 1) {
            $("#addElementToDashboardAction").submit();
        } else {
            $("#addElementToDashboard").modal('show');
        }
    }
</script>

</body>
</html>



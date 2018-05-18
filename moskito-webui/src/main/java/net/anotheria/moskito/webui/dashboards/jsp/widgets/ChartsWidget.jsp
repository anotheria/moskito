<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>

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

    <ano:present name="thresholdsGraph">
        var thresholdsGraph = [];
        <ano:iterate name="thresholdsGraph" type="java.util.Map.Entry" id="thresholdGraph">
            thresholdsGraph.push({
                <ano:iterate name="thresholdGraph" property="value" id="guard" indexId="i">
                    <ano:notEqual name="i" value="0">,</ano:notEqual> <ano:write name="guard" property="status"/>:<ano:write name="guard" property="value"/>
                </ano:iterate>
            })
        </ano:iterate>

        var thresholdsGraphColors = (thresholdsGraph.length > 0) ? getThresholdsGraphColors() : {};

        function getThresholdsGraphColors() {
            var colors = {};

            <ano:present name="thresholdGraphColors">
                <ano:iterate name="thresholdGraphColors" type="net.anotheria.moskito.webui.util.ThresholdGraphColor" id="thresholdGraphColor">
                    colors["<ano:write name="thresholdGraphColor" property="status"/>"] = "<ano:write name="thresholdGraphColor" property="color"/>";
                </ano:iterate>
            </ano:present>

            colors.GREEN = colors.GREEN || "#53d769";
            colors.YELLOW = colors.YELLOW || "#ffde00";
            colors.ORANGE = colors.ORANGE || "#ff8023";
            colors.RED = colors.RED || "#fc3e39";
            colors.PURPLE = colors.PURPLE || "#b44bc4";

            return colors;
        }
    </ano:present>
</script>

<div class="dashboard-line">

    <div class="row">
        <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean" indexId="index">
            <div class="col-lg-6 col-md-12">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse_chart${index}"><i class="fa fa-caret-down"></i></a>
                        <h3 class="pull-left chart-header">
                                ${chart.caption}
                        </h3>

                        <div class="box-right-nav dropdown">
                            <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                <li><a href="" onclick="saveSvgAsPng(event, ${index}+countGauges())">Screenshot</a></li>
                                <ano:iF test="${chart.dashboardsToAdd != ''}">
                                    <li><a onclick="addChart('${chart.chartNames}','${chart.dashboardsToAdd}')">Add to Dashboard</a></li>
                                </ano:iF>
                                <li><a onclick="removeChart('${chart.chartNames}', '${requestScope.selectedDashboard}')">Remove</a></li>
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


<script type="text/javascript">
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
                legendsPerSlice: 6,
                margin: {top: 20, right: 10, bottom: 20, left: 40}
            }
        };

        if (!isEmptyObject(thresholdsGraph[index])) {
            addThresholdsToChart(thresholdsGraph[index], chartParams);
        }

        chartEngineIniter.init(chartParams);
    });

    function addThresholdsToChart(thresholds, chartParams) {
        for (var color in thresholds) {
            if (thresholds.hasOwnProperty(color)) {
                if (thresholdsGraphColors.hasOwnProperty(color)) {
                    var gauge = thresholds[color];
                    var data = chartParams.data;
                    var legendColorName = color.substring(0, 1) + color.substring(1).toLowerCase() + " Barrier";
                    chartParams.colors.push({"color": thresholdsGraphColors[color], "name": legendColorName});
                    chartParams.names.push(legendColorName);
                    for (var i = 0, length = data.length; i < length; i++) {
                        data[i].push(gauge);
                    }
                }
            }
        }
    }

    function isEmptyObject(obj) {
        for (var property in obj) {
            if (obj.hasOwnProperty(property)) {
                return false;
            }
        }
        return true;
    }
</script>
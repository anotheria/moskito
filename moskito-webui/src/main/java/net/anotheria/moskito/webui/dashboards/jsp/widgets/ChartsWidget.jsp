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
                legendsPerSlice: 5,
                margin: {top: 20, right: 10, bottom: 20, left: 40}
            }
        };

        chartEngineIniter.init(chartParams);
    });
</script>
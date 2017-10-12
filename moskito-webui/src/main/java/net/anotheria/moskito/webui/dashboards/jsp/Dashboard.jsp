<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
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
                                        <ano:iF test="${threshold.dashboardsToAdd != ''}">
                                            <li><a onclick="addTresholds('${threshold.name}', '${threshold.dashboardsToAdd}')" >Add to Dashboard</a></li>
                                        </ano:iF>
                                        <li><a onclick="removeTresholds('${threshold.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
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
                });
                </ano:iterate>
            </script>

            <!-- gauges -->
            <div class="dashboard-line">
                <div class="row">
                    <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.bean.GaugeBean" id="gauge" indexId="index">
                        <div class="col-lg-3 col-md-4 col-sm-6">
                            <div class="box gauge-item">
                                <div class="box-title">
                                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#gauge_collapse_chart${index}"><i class="fa fa-caret-down"></i></a>

                                    <h3 class="pull-left chart-header">
                                        ${gauge.caption}
                                    </h3>
                                    <div class="box-right-nav dropdown">
                                        <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                            <li><a href="" onclick="saveGaugesSvgAsPng(event, ${index}, ${index})">Save</a></li>
                                            <ano:iF test="${gauge.dashboardsToAdd != ''}">
                                                <li><a onclick="addGauge('${gauge.caption}', '${gauge.name}', '${gauge.dashboardsToAdd}')" >Add to Dashboard</a></li>
                                            </ano:iF>
                                            <li><a onclick="removeGauge('${gauge.caption}', '${gauge.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div id="gauge_collapse_chart${index}" class="box-content accordion-body collapse in">
                                    <div class="paddner text-center">
                                        <div id="gaugeChart${index}" class="gauge-content gauge-chart">Not enough data</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ano:iterate>
                </div>

                <div class="dashboard-line-footer text-right">
                    <ul class="dashboard-line-nav-box list-unstyled">
                        <li>
                            <a onclick="saveGaugesSvgAsPng(event, 0, 10000)" class="save_as"><i class="fa fa-download"></i> Save all Gauges</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- // end gauges -->

            <script type="text/javascript">
                var gaugeContainerSelectors = $('.gauge-chart').map(function () {
                    return $(this).attr("id");
                });

                gauges.forEach(function (gaugeData, index) {
                    var chartParams = {
                        container: gaugeContainerSelectors[index],
                        data: gaugeData,
                        type: 'GaugeChart'
                    };

                    chartEngineIniter.init(chartParams);
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
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse_chart${index}"><i class="fa fa-caret-down"></i></a>
                            <h3 class="pull-left chart-header">
                                ${chart.caption}
                            </h3>

                            <div class="box-right-nav dropdown">
                                <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                    <li><a href="" onclick="saveSvgAsPng(event, ${index}+countGauges())">Save</a></li>
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
        </ano:equal>

        <ano:equal name="producersPresent" value="true">
            <ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" id="decorator" name="decorators">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-down"></i></a>
                        <h3 class="pull-left">${decorator.name}</h3>
                        <div class="box-right-nav">
                            <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                        </div>
                    </div>
                    <div id="collapse${decorator.decoratorNameForCss}" class="box-content accordion-body collapse in">


                        <div class="table1fixed">
                            <table class="table table-striped tablesorter">
                                <thead>
                                <tr>
                                    <th class="headcol">Producer Id <i class="fa fa-caret-down"></i></th>
                                    <th>Category <i class="fa fa-caret-down"></i></th>
                                    <th>Subsystem <i class="fa fa-caret-down"></i></th>
                                    <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                                        <th title="${caption.shortExplanation}" class="{sorter: 'commaNumber'} table-column">
                                            <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                                            <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption}<i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                                        </th>
                                    </ano:iterate>
                                    <th>Class</th>
                                    <th class="th-actions"></th>
                                </tr>
                                </thead>
                                <tbody>
                                    <%-- writing out values --%>
                                <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
                                    <tr>
                                        <td class="headcol"><mos:deepLink  href="mskShowProducer?pProducerId=${producer.producerId}" class="tooltip-bottom" title="Show details for producer ${producer.producerId}">${producer.producerId}</mos:deepLink ></td>
                                        <td><mos:deepLink  href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</mos:deepLink ></td>
                                        <td><mos:deepLink  href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</mos:deepLink ></td>
                                        <ano:iterate name="producer" property="firstStatsValues" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                            <td class="tooltip-bottom" title="${producer.producerId}.${value.name}=${value.value}">${value.value}</td>
                                        </ano:iterate>
                                        <td>${producer.producerClassName}</td>

                                        <td class="actions-links">
                                            <a onclick="removeProducer('${producer.producerId}', '<ano:write name="selectedDashboard" />');" href="#" class="action-icon delete-icon tooltip-bottom" title="Remove ${producer.producerId} from dashboard"><i class="fa fa-close"></i></a>
                                            <ano:notEmpty name="dashboardNames">
                                                <a onclick="addProducer('${producer.producerId}', '<ano:write name="dashboardNames" />');" href="#" class="action-icon add-icon tooltip-bottom" title="Add ${producer.producerId} to dashboard"><i class="fa fa-plus"></i></a>
                                            </ano:notEmpty>
                                        </td>
                                    </tr>
                                </ano:iterate>
                                </tbody>
                            </table>
                        </div>

                    </div>
                </div>
            </ano:iterate>
        </ano:equal>

        <ano:iF test="${requestScope.selectedDashboard != null}">
            <div class="dashboard-line">
                <div class="row">
                    <a href="#DeleteDashboard" data-toggle="modal" data-target="#DeleteDashboard" title="Delete Dashboard">Delete this Dashboard</a>
                </div>
            </div>
        </ano:iF>
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
                    <input type="hidden" name="remoteConnection" value="${remoteLink}"/>
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
                    <input type="hidden" name="remoteConnection" value="${remoteLink}"/>
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

<script type="text/javascript">
    (function (dashboard, $) {
        dashboard.restApiUrl = '${dashboardRestApiUrl}';
        dashboard.refreshInterval = ${dashboardRefreshRate};

        dashboard.init = function () {
            setTimeout(function () {
                getDashboardData();
            }, dashboard.refreshInterval)
        };

        function getDashboardData() {
            var success = function (response) {
                if(response.success != true) {
                    console.info("Not successful response.");
                    return;
                }

                var dashboard = response["results"]["dashboard"];

                refreshThresholdStatuses(dashboard["thresholds"]);
                refreshGaugeCharts(dashboard["gauges"]);
                refreshLineCharts(dashboard["charts"]);
            };

            $.ajax({
                url: dashboard.restApiUrl,
                type: "GET",
                headers: {
                    "Accept": "application/json"
                },
                success: success,
                error: function (data) {
                    console.info("Unable to refresh dashboard.");
                },
                complete: setTimeout(function () {
                    getDashboardData();
                }, dashboard.refreshInterval)
            });
        }

        function refreshThresholdStatuses(thresholdStatuses) {
            _.each(thresholdStatuses, function (thresholdStatus, idx) {
                var name = thresholdStatus["name"],
                        value = thresholdStatus["value"],
                        colorCode = thresholdStatus["colorCode"];

                var $thresholdItem = $('.threshold-item').eq(idx);
                $thresholdItem.find(".threshold-title").text(name + " " + value);
                $thresholdItem.find("i.status").attr("class", "status status-" + colorCode);
            });
        }

        function refreshGaugeCharts(gauges) {
            _.each(gauges, function (gauge, idx) {
                chartEngineIniter.d3charts.dispatch.refreshGauge({
                    "containerId": "#gaugeChart" + idx,
                    "min": Number(gauge["min"]),
                    "max": Number(gauge["max"]),
                    "current": Number(gauge["current"])
                });
            });
        }

        function refreshLineCharts(dashboardCharts) {
            _.each(dashboardCharts, function (dashboardChart, idx) {
                var multiLineChart = dashboardChart["chart"];

                var names;
                if (_.isArray(multiLineChart["names"])) {
                    names = _.map(multiLineChart["names"], function (name) {
                        return name;
                    });
                } else {
                    names = [].concat(multiLineChart["names"]);
                }

                var data = _.map(multiLineChart["data"], function (accumulatedValueObj) {
                    var dataItem = [];

                    dataItem.push(_.toNumber(accumulatedValueObj["numericTimestamp"]));

                    _.each(accumulatedValueObj["values"], function (valueItem) {
                        dataItem.push(_.toNumber(valueItem));
                    });

                    return dataItem;
                });

                var containerId = "#chart_div" + idx;

                chartEngineIniter.d3charts.dispatch.refreshLineCharts({
                    "containerId": containerId,
                    "data": data,
                    "names": names
                });
            });
        }
    }(window.dashboard = window.dashboard || {}, jQuery));


    $(function () {
        dashboard.init();
    });
</script>

<script src="../moskito/int/js/dashboard.js" type="text/javascript"></script>

<jsp:include page="/net/anotheria/moskito/webui/producers/jsp/snippet/ProducerHelpModal.jsp" />
</body>
</html>



<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <ano:equal name="showHelp" value="true">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            This Dashboard is yet empty. Add some elements to it by editing <em>moskito.json</em>.<br/>For more details on MoSKito Configuration visit: <a href="https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Configuration+Guide">https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Configuration+Guide</a>.
        </div>
    </ano:equal>

    <ano:present name="infoMessage">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            ${requestScope.infoMessage}
        </div>
    </ano:present>

    <div class="content">
        <ano:iterate name="widgets" id="widget" type="net.anotheria.moskito.core.config.dashboards.DashboardWidget">
             <jsp:include page="${widget.jspPath}"/>
        </ano:iterate>

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
                    <div class="form-group text-center">
                        <button class="btn btn-success" type="button" onclick="submit();">Create</button>
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade modal-danger" id="DeleteDashboard" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Delete Dashboard "${requestScope.selectedDashboard}" ? </h4>
            </div>
            <div class="modal-footer">
                <form name="CreateDashboard" action="mskDeleteDashboard" method="GET">
                    <input type="hidden" name="remoteConnection" value="${remoteLink}"/>
                    <input type="hidden" class="form-control" name="pName" value="${requestScope.selectedDashboard}">
                    <div class="text-center">
                        <button class="btn btn-default" type="button" data-dismiss="modal">Cancel</button>
                        <button class="btn btn-danger" type="button" onclick="submit();">Delete</button>
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
                var colors = [];

                if (!isEmptyObject(thresholdsGraph[idx])) {
                    addThresholdsToChart(thresholdsGraph[idx], {"data": data, "names": names, "colors": colors});
                }

                chartEngineIniter.d3charts.dispatch.refreshLineCharts({
                    "containerId": containerId,
                    "data": data,
                    "names": names,
                    "colors": colors
                });
            });
        }
    }(window.dashboard = window.dashboard || {}, jQuery));


    $(function () {
        dashboard.init();
    });
</script>

<script src="../moskito/int/js/dashboard.js" type="text/javascript"></script>

<jsp:include page="/net/anotheria/moskito/webui/shared/jsp/ChartEngine.jsp"/>
<jsp:include page="/net/anotheria/moskito/webui/producers/jsp/snippet/ProducerHelpModal.jsp" />
</body>
</html>



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
        <!-- gauges -->
        <div class="dashboard-line">
            <div class="row">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse3"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse3" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse4"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse4" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse5"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse5" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse6"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse6" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse7"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse7" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse10"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse10" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse8"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse8" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse9"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Gauges Name
                            </h3>
                        </div>
                        <div id="collapse9" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div class="gauge-content">
                                    <img src="../resources/moskito/int/img/gauge.png" alt="">
                                </div>
                                <div class="gauge-info row">
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Min
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Current
                                    </div>
                                    <div class="col-xs-4 gauge-info-item tooltip-bottom" title="Some info">
                                        Max
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <-- // end gauges -->
        </ano:equal>

        <ano:equal name="chartsPresent" value="true">
            <script type="text/javascript">
                var multipleGraphData = [];
                var multipleGraphNames = [];
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean">
                <ano:define id="singleChart" toScope="page" scope="page" name="chart" property="chartData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO"/>
                multipleGraphData.push([
                    <ano:iterate name="singleChart" property="data" id="value" indexId="i">
                    <ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
                    </ano:iterate>
                ]);
                multipleGraphNames.push([
                    <ano:iterate name="chart" property="lineNames" id="lineName" indexId="i">
                    <ano:notEqual name="i" value="0">,</ano:notEqual>'<ano:write name="lineName"/>'
                    </ano:iterate>
                ]);
                </ano:iterate>
            </script>

        <div class="dashboard-line">

            <div class="row">
                <ano:iterate id="chart" name="charts" type="net.anotheria.moskito.webui.dashboards.bean.DashboardChartBean" indexId="index">
                <div class="col-lg-6 col-md-12">
                    <div class="box">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse_chart${index}"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                ${chart.caption}
                            </h3>
                            <%--
                            <div class="box-right-nav">
                                <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
                            </div> --%>
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
                        type: 'LineChart',
                        title: names[index],
                        dataType: 'datetime',
                        options: {
                            legendsPerSlice: 5,
                            margin: {top: 20, right: 40, bottom: 20, left: 80}
                        }
                    };

                    chartEngineIniter[chartEngineName](chartParams);
                });
            </script>
        </ano:equal>

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



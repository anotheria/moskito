<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <ano:equal name="newThresholdAdded" value="true">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Threshold <ano:write name="newThresholdName"/> added!
        </div>
        <ano:equal name="newAccumulatorAdded" value="true">
            <div class="alert alert-success alert-dismissable">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                Accumulator <ano:write name="newAccumulatorName"/> added!
            </div>
        </ano:equal>
    </ano:equal>
    <div class="content">
    <ano:present name="thresholdsPresent">
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
                                </ul>
                            </div>
                        </div>
                    </div>
                </ano:iterate>
            </div>
        </div>
        <!-- Thresholds end -->
    </ano:present>

<div class="box">
    <div class="box-content paddner">
        <dl class="dl-horizontal pull-left">
            <dt>Producer:</dt>
            <dd>${producer.producerId}</dd>
            <dt>Category:</dt>
            <dd><mos:deepLink  href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</mos:deepLink></dd>
            <dt>Subsystem:</dt>
            <dd><mos:deepLink  href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</mos:deepLink></dd>
            <dt>Сlass:</dt>
            <dd>${producer.producerClassName}</dd>
        </dl>
        <div class="pull-right">
            <mos:deepLink  href="${linkToCurrentPage}&pForward=selection&target=Accumulator" class="btn btn-default" onclick="new_accumulator(); return false"><i class="fa fa-plus"></i> Accumulator</mos:deepLink>
            <mos:deepLink  href="${linkToCurrentPage}&pForward=selection&target=Threshold" class="btn btn-default" onclick="new_threshold(); return false"><i class="fa fa-plus"></i> Threshold</mos:deepLink>
            <c:if test="${producer.traceable}">
                <c:choose>
                <c:when test="${producer.traced}">
                    <mos:deepLink  href="mskTracer?pProducerId=${producer.producerId}" class="btn btn-success" onclick=""><i class="fa fa-binoculars"></i> Tracer</mos:deepLink>
                </c:when>
                <c:otherwise>
                    <mos:deepLink  href="mskCreateTracer?pProducerId=${producer.producerId}" class="btn btn-default" onclick=""><i class="fa fa-plus"></i> Tracer</mos:deepLink>
                </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${producer.loggingSupported}">
                <c:choose>
                    <c:when test="${producer.loggingEnabled}">
                        <mos:deepLink  href="mskDisableLogging?pProducerId=${producer.producerId}" class="btn btn-success" onclick=""><i class="fa fa-power-off"></i> Logging</mos:deepLink>
                    </c:when>
                    <c:otherwise>
                        <mos:deepLink  href="mskEnableLogging?pProducerId=${producer.producerId}" class="btn btn-default" onclick=""><i class="fa fa-power-off"></i> Logging</mos:deepLink>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${producer.sourceMonitoringSupported}">
                <c:choose>
                    <c:when test="${producer.sourceMonitoringEnabled}">
                        <mos:deepLink  href="mskDisableSourceMonitoring?pProducerId=${producer.producerId}" class="btn btn-success" onclick=""><i class="fa fa-power-off"></i> SourceMonitoring</mos:deepLink>
                    </c:when>
                    <c:otherwise>
                        <mos:deepLink  href="mskEnableSourceMonitoring?pProducerId=${producer.producerId}" class="btn btn-default" onclick=""><i class="fa fa-power-off"></i> SourceMonitoring</mos:deepLink>
                    </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${producer.inspectable}">
                <a href="#inspect" data-toggle="modal" data-target="#inspect" class="btn btn-success"><i class="fa fa-search"></i> Inspect</a>
            </c:if>

            <ano:notEmpty name="dashboardNames">
                <a onclick="addProducer('${producer.producerId}', '<ano:write name="dashboardNames" />');" href="#" class="btn btn-default"><i class="fa fa-plus"></i> Add to Dashboard</a>
            </ano:notEmpty>
        </div>
    </div>
</div>

<ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators">
<div class="box">
    <div class="box-title">
        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseproducer"><i class="fa fa-caret-down"></i></a>
        <h3 class="pull-left">
            ${producer.producerId}
        </h3>
        <div class="box-right-nav">
            <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
        </div>
    </div>
    <div id="collapseproducer" class="box-content accordion-body collapse in">
        <table class="table table-striped tablesorter">
            <thead>
            <tr>
                <th>Name <i class="fa fa-caret-down"></i></th>
                <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                    <th title="<ano:write name="caption" property="shortExplanation"/>" class="{sorter: 'commaNumber'} table-column">
                        <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                        <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption} <i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i></th>
                    </th>
                </ano:iterate>

            </tr>
            </thead>
            <tbody>
            <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                <tr>
                    <td>${stat.name}</td>
                    <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                        <td class="producers-table-stats_value" title="${stat.name}.${value.name}=${value.value}">
                            ${value.value}
                        </td>
                    </ano:iterate>
                </tr>
            </ano:iterate>
            </tbody>
            <ano:present name="decorator" property="cumulatedStat">
                <ano:define id="cumulatedStat" name="decorator" property="cumulatedStat"/>
                <tfoot>
                <tr>
                    <td>${cumulatedStat.name}</td>
                    <ano:iterate name="cumulatedStat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                        <td title="${cumulatedStat.name}.${value.name}=${value.value}">
                                ${value.value}
                        </td>
                    </ano:iterate>
                </tr>
                <tfoot>
            </ano:present>


        </table>
    </div>
</div>
</ano:iterate>


    <ano:present name="accumulatorsPresent">
    <ano:present name="accumulatorsColors">
        <script type="text/javascript">
            var accumulatorsColors = <ano:write name="accumulatorsColors"/>;
        </script>
    </ano:present>

    <script type="text/javascript">
        var multipleGraphData = [];
        <ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">
        multipleGraphData.push([
            <ano:iterate name="singleGraph" property="data" id="value" indexId="i">
            <ano:notEqual name="i" value="0">, </ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
            </ano:iterate>
        ]);
        </ano:iterate>
    </script>

    <script>
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

    <%-- Chart boxes for multiple charts --%>
    <div>
        <ano:iterate name="singleGraphData"
                         type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO"
                         id="singleGraph">
                <div class="box" id="parentBox">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                           href="#collapse-chart-${singleGraph.nameForJS}"><i class="fa fa-caret-down"></i></a>

                        <h3 class="pull-left chart-header">
                            Chart for ${singleGraph.name}
                        </h3>

                        <div class="box-right-nav dropdown">
                            <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                <li><a href="" class="save_as">Screenshot</a></li>
                                <ano:iF test="${chart.dashboardsToAdd != ''}">
                                    <li><a onclick="addChart('${singleGraph.name}','${requestScope.dashboardNames}')">Add to Dashboard</a></li>
                                </ano:iF>
                            </ul>
                        </div>
                    </div>
                    <div id="collapse-chart-${singleGraph.nameForJS}"
                         class="box-content accordion-body collapse in">
                        <div class="paddner">
                            <div id="chart_accum${singleGraph.nameForJS}" class="accumulator-chart"></div>
                        </div>
                    </div>
                </div>
            </ano:iterate>
    </div>
    <%-- /charts' boxes --%>

    <script type="text/javascript">
        //changing the order of multiple charts
        $(document).ready(function () {
            $(".up").click(function () {
                var pdiv = $(this).closest('#parentBox');
                pdiv.insertBefore(pdiv.prev());
                return false
            });
            $(".down").click(function () {
                var pdiv = $(this).closest('#parentBox');
                pdiv.insertAfter(pdiv.next());
                return false
            });
        });
    </script>

    <script type="text/javascript">
        // Many charts
        if ('multipleGraphData' in window) {
            var names = '${accNames}'.slice(1, -1).split(', ');
            var containerSelectors = $('.accumulator-chart').map(function () {
                return $(this).attr("id");
            });

            var thresholdsGraph = thresholdsGraph || [];
            var thresholdsGraphColors = thresholdsGraphColors || {};

            multipleGraphData.forEach(function (graphData, index) {
                var chartParams = {
                    container: containerSelectors[index],
                    names: [names[index]],
                    data: graphData,
                    colors: accumulatorsColors,
                    type: '<ano:write name="type"/>',
                    title: names[index],
                    dataType: 'datetime',
                    options: {
                        legendsPerSlice: 7,
                        margin: {top: 20, right: 40, bottom: 30, left: 40}
                    }
                };

                if (!isEmptyObject(thresholdsGraph[index])) {
                    addThresholdsToChart(thresholdsGraph[index], chartParams);
                }

                chartEngineIniter.init(chartParams);
            });

        }
        // One chart with one or more lines
        else {
            var names = ('${singleGraph.name}' && ['${singleGraph.name}']) || '${accNames}'.slice(1, -1).split(', ');

            var chartParams = {
                container: 'chart_accum${singleGraph.nameForJS}',
                names: names,
                data: data,
                colors: accumulatorsColors,
                type: '<ano:write name="type"/>',
                title: '',
                dataType: 'datetime',
                options: {
                    legendsPerSlice: 7,
                    margin: {top: 20, right: 40, bottom: 30, left: 40}
                }
            };

            chartEngineIniter.init(chartParams);
        }


        $('.refresh').click(function () {
            location.reload(true);
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

    </ano:present>

</div>
    <jsp:include page="../../shared/jsp/ChartEngine.jsp"/>

    <div class="modal fade inspect-list" id="inspect" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Inspect Producer '${producer.producerId}' creation location:</h4>
                </div>
                <div class="modal-body">
                    <ul>
                        <ano:iterate name="creationTrace" type="java.lang.String" id="line">
                            <li>${line}</li>
                        </ano:iterate>
                    </ul>
                </div>
            </div>
        </div>
    </div>

<div class="modal fade" id="createNewThreshold" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form modal-lg">
        <form name="CreateThreshold" action="mskThresholdCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Threshold"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>
            <input type="hidden" name="remoteConnection" value="${remoteLink}"/>

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Create new Threshold</h4>
            </div>
            <div class="modal-body">
                    <div class="form-group">
                        <label for="nameThresholds">Threshold name:</label>
                        <input type="text" class="form-control" id="nameThresholds" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Guards</label>
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Green"><i class="status status-green"></i></div>
                                    <select onchange="switchDirection();" name="greenDir" class="form-control"><option>below</option><option>above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label>Interval</label>
                                <input type="text" class="form-control" value="" placeholder="Value" name="greenValue" onchange="switchgreenvalue();">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Yellow"><i class="status status-yellow"></i></div>
                                    <select onchange="switchDirection();" name="yellowDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value=""  name="yellowValue" placeholder="Value">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Orange"><i class="status status-orange"></i></div>
                                    <select onchange="switchDirection();" name="orangeDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="orangeValue">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Red"><i class="status status-red"></i></div>
                                    <select onchange="switchDirection();" name="redDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value"  name="redValue" >
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Purple"><i class="status status-purple"></i></div>
                                    <select onchange="switchDirection();" name="purpleDir" class="form-control"><option>below</option><option  selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="purpleValue">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            ${interval.name}
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
            </div>
            <div class="form-group">
                <dl class="dl-horizontal">
                    <dt>Producer:</dt>
                    <dd>${producer.producerId}</dd>
                </dl>
            </div>

            <div class="modal-table">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                            <th>${caption.caption}</th>
                        </ano:iterate>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                        <tr>
                            <td>${stat.name}</td>
                            <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                <td>
                                    <a href="#" onclick="setandsubmit('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                </td>
                            </ano:iterate>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        </form>
    </div>
</div>

<!-- Accumulator Dialog -->
<div class="modal fade" id="createNewAccumulator" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form modal-lg">
        <form name="CreateAccumulator" action="mskAccumulatorCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Accumulator"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>
            <input type="hidden" name="remoteConnection" value="${remoteLink}"/>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Create new Accumulator</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nameAccumulator">Accumulator name:</label>
                        <input type="text" class="form-control" id="nameAccumulator" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            <ano:write name="interval" property="name"/>
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <dl class="dl-horizontal">
                        <dt>Producer:</dt>
                        <dd>${producer.producerId}</dd>
                    </dl>
                </div>

                <div class="modal-table">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                                <th>${caption.caption}</th>
                            </ano:iterate>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                            <tr>
                                <td>${stat.name}</td>
                                <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                    <td>
                                        <a href="#" onclick="setandsubmitAccumulator('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                    </td>
                                </ano:iterate>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </form>
    </div>
</div>


<jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
<jsp:include page="snippet/ProducerHelpModal.jsp"/>

    <script type="text/javascript">
        $('.save_as').click(function (event) {
            event.preventDefault();
            event.stopPropagation();
            var chartWidth = 1120,
                chartHeight = 300,
                margin = 40;

            var svgOrigin = $(event.target).closest(".box").find("svg")[0];
            //copy svg chart
            var svg = svgOrigin.cloneNode(true);

            svg.setAttribute("style", "background-color: #FFFFFF;");
            svg.setAttribute("x", margin);
            svg.setAttribute("y", margin);


            var css = '.axis path,' +
                '.axis line {' +
                'fill: none;' +
                'stroke: #000;' +
                'shape-rendering: crispEdges;' +
                '}' +
                '.legend, .tick {' +
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
            if (style.styleSheet) {
                style.styleSheet.cssText = css;
            } else {
                style.appendChild(document.createTextNode(css));
            }

            svg.appendChild(style);

            var svgData = new XMLSerializer().serializeToString(svg);
            svgData = '<svg xmlns="http://www.w3.org/2000/svg"  style="background-color: #FFFFFF;" width="1200" height="380" >' + svgData + '</svg>';

            var canvas = document.createElement("canvas");
            canvas.width = chartWidth + 2 * margin;
            canvas.height = chartHeight + 2 * margin;
            var ctx = canvas.getContext("2d");
            ctx.fillStyle = "white";
            ctx.fill();

            var img = document.createElement("img");
            window.unescape = window.unescape || window.decodeURI;
            var img = document.createElement("img");
            var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function (c) {
                return '&#' + c.charCodeAt(0) + ';';
            }));
            img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);
            var file_name = getChartFileName();

            img.onload = function () {
                ctx.drawImage(img, 0, 0);
                var canvasdata = canvas.toDataURL("image/png");
                var a = document.createElement("a");

                a.download = file_name + ".png";
                a.href = canvasdata;
                document.body.appendChild(a);
                a.click();

            };
        });

        function getChartFileName() {
            var t = new Date($.now());
            var current_date = t.getFullYear() + '-' + t.getMonth() + '-' + t.getDate() + '__' + t.getHours() + '-' + t.getMinutes() + '-' + t.getSeconds();
            return $.trim($(event.target).closest(".box").find('.chart-header').text()).split(' ').join('_') + '_' + current_date;
        }
    </script>

</section>

<script>
    function new_threshold(){
        $('#createNewThreshold').modal('show');
    }
    function new_accumulator(){
        $('#createNewAccumulator').modal('show');
    }


    function setandsubmitAccumulator(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateAccumulator.statName.value = statName;
        document.forms.CreateAccumulator.valueName.value = valueName;
        document.forms.CreateAccumulator.submit();
    }
    function setandsubmit(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateThreshold.statName.value = statName;
        document.forms.CreateThreshold.valueName.value = valueName;
        document.forms.CreateThreshold.submit();
    }
    function switchDirection(){
        if (document.forms.CreateThreshold.greenDir.value=='above')
            targetValue = 'below';
        else
            targetValue = 'above';

        document.forms.CreateThreshold.yellowDir.value = targetValue;
        document.forms.CreateThreshold.orangeDir.value = targetValue;
        document.forms.CreateThreshold.redDir.value = targetValue;
        document.forms.CreateThreshold.purpleDir.value = targetValue;
    }

    function switchgreenvalue(){
        document.forms.CreateThreshold.yellowValue.value=document.forms.CreateThreshold.greenValue.value;
    }
</script>

</body>
</html>

<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
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

    <ano:present name="decorator">
        <div class="box">
            <div class="box-content paddner">
                <dl class="dl-horizontal pull-left">
                    <dt>Decorator:</dt>
                    <dd><ano:write name="decorator" property="name" /></dd>

                    <dt>Producers:</dt>
                    <dd>
                        <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO" lastId="isLast">
                            <mos:deepLink href="mskShowProducer?pProducerId=${producer.producerId}" class="tooltip-bottom" title="Show details for producer ${producer.producerId}">${producer.producerId}</mos:deepLink>

                            <ano:iF test="${!isLast}">
                                ,&nbsp;
                            </ano:iF>
                        </ano:iterate>
                    </dd>
                </dl>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseproducer"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    <ano:write name="decorator" property="name" />
                </h3>
                <div class="box-right-nav">
                    <a onclick="showProducerHelpModal('<ano:write name="decorator" property="name" />');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapseproducer" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Producer ID</th>
                        <th>Stat Name <i class="fa fa-caret-down"></i></th>
                        <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                            <th title="<ano:write name="caption" property="shortExplanation"/>" class="{sorter: 'commaNumber'} table-column">
                                <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                                <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption} <i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i></th>
                            </th>
                        </ano:iterate>

                    </tr>
                    </thead>
                    <tbody>
                        <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
                            <ano:iterate name="producer" property="lines" id="statLine" type="net.anotheria.moskito.webui.producers.api.StatLineAO">
                                <tr>
                                    <td>${producer.producerId}</td>
                                    <td>${statLine.statName}</td>
                                    <ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                        <td title="${statLine.statName}.${value.name}=${value.value}">
                                                ${value.value}
                                        </td>
                                    </ano:iterate>
                                </tr>
                            </ano:iterate>
                        </ano:iterate>
                    </tbody>
                    <ano:present name="decorator" property="cumulatedStat">
                    <ano:define id="statLine" name="decorator" property="cumulatedStat" type="net.anotheria.moskito.webui.producers.api.StatLineAO"/>
                    <tfoot>
                        <tr class="cumulated-bold-row">
                            <td>${statLine.statName}</td>
                            <td></td>
                            <ano:iterate name="statLine" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                <td title="${statLine.statName}.${value.name}=${value.value}">
                                        ${value.value}
                                </td>
                            </ano:iterate>
                        </tr>
                    <tfoot>
                    </ano:present>
                </table>
            </div>
        </div>
    </ano:present>

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

    <%-- Chart boxes for multiple charts --%>
    <div>
        <ano:iterate name="singleGraphData"
                         type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO"
                         id="singleGraph">
                <div class="box" id="parentBox">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                           href="#collapse-chart-${singleGraph.nameForJS}"><i class="fa fa-caret-down"></i></a>

                        <h3 class="pull-left">
                            Chart for ${singleGraph.name}
                        </h3>

                        <div class="box-right-nav">
                            <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
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


    </script>

    </ano:present>

</div>

<jsp:include page="../../shared/jsp/ChartEngine.jsp"/>

<jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
<jsp:include page="snippet/ProducerHelpModal.jsp"/>
</section>
</body>
</html>
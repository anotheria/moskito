<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/Header.jsp"/>
<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">
        <ano:present name="analyzedJourney">
            <ano:iterate name="analyzedJourney" property="calls" type="net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO" id="call" indexId="index">
                <ano:equal name="call" property="empty" value="false">
                    <div class="box">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${index}"><i class="fa fa-caret-down"></i></a>
                            <h3 class="pull-left">
                                ${call.name}
                            </h3>
                            <div class="box-right-nav">
                                <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                            </div>
                        </div>
                        <div id="collapse${index}" class="box-content accordion-body collapse in">
                            <table class="table tablesorter table-left table-striped">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th title="Number of Calls" class="table-column">
                                        <!-- variable for this graph is ${call.name}_Calls -->
                                        <input type="hidden" value="${call.name}_Calls"/>Calls <i class="fa fa-caret-up"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                                    </th>
                                    <th title="Duration" class="table-column">
                                        <!-- variable for this graph is ${call.name}_Duration -->
                                        <input type="hidden" value="${call.name}_Duration"/>Duration <i class="fa fa-caret-up"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <ano:iterate name="call" property="producerCallsBeans" id="producerCallsBean" type="net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAO">
                                    <tr>
                                        <td>${producerCallsBean.producerId}</td>
                                        <td>${producerCallsBean.numberOfCalls}</td>
                                        <td>${producerCallsBean.totalTimeSpentTransformed}</td>
                                    </tr>
                                </ano:iterate>
                                <tr>
                                    <td><b>Total:</b></td>
                                    <td>${call.totalCalls}</td>
                                    <td>${call.totalDurationTransformed}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </ano:equal>
            </ano:iterate>
        </ano:present>
    </div>

<jsp:include page="../../shared/jsp/Footer.jsp"/>
<jsp:include page="../../shared/jsp/ChartEngine.jsp"/>
</section>
</body>
</html>

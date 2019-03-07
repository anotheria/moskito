<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags"
%><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
<%--
Commented out for now. We may add this later as welcome message (to all layers).
<div class="alert alert-success alert-dismissable">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <strong>Welcome</strong> to moskito WebUI. <a href="">How it work?</a>
</div>
--%>
<div class="content">

<ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" id="decorator" name="decorators">
<div class="box">
<div class="box-title">
    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-down"></i></a>
    <h3 class="pull-left">${decorator.name} (${decorator.producersAmount})</h3>

    <ano:equal name="betaMode" value="true">
        <div class="box-right-nav dropdown">
            <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                <li>
                    <mos:deepLink href="mskShowCumulatedProducers?pDecorator=${decorator.name}&pCategory=${currentCategory}&pSubsystem=${currentSubsystem}">Cumulate</mos:deepLink>
                </li>
                <li>
                    <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="">Help</a>
                </li>
            </ul>
        </div>
    </ano:equal>

    <ano:equal name="betaMode" value="false">
        <div class="box-right-nav">
            <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
        </div>
    </ano:equal>
</div>
<div id="collapse${decorator.decoratorNameForCss}" class="box-content accordion-body collapse in">
        <table class="table table-striped tablesorter producers-table">
            <thead>
            <tr>
                <th>Producer Id <i class="fa fa-caret-down"></i></th>
                <th>Category <i class="fa fa-caret-down"></i></th>
                <th>Subsystem <i class="fa fa-caret-down"></i></th>
                <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                    <th title="${caption.shortExplanation}" class="{sorter: 'commaNumber'} table-column">
                        <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                        <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption}<i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                    </th>
                </ano:iterate>
                <th>Class</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%-- writing out values --%>
            <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
            <tr>
                <td><mos:deepLink  href="mskShowProducer?pProducerId=${producer.producerIdEncoded}" class="tooltip-bottom" title="Show details for producer ${producer.producerId}">${producer.producerId}</mos:deepLink ></td>
                <td><mos:deepLink  href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</mos:deepLink ></td>
                <td><mos:deepLink  href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</mos:deepLink ></td>
                <ano:iterate name="producer" property="firstStatsValues" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                    <td class="tooltip-bottom" title="${producer.producerId}.${value.name}=${value.value}">${value.value}</td>
                </ano:iterate>
                <td>${producer.producerClassName}</td>

                <td class="actions-links">
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
</ano:iterate>


</div>

<jsp:include page="../../shared/jsp/Footer.jsp"/>
<jsp:include page="../../shared/jsp/ChartEngine.jsp"/>
</section>
<jsp:include page="snippet/ProducerHelpModal.jsp"/>
</body>
</html>
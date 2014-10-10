<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<script type="text/javascript" src="//www.google.com/jsapi"></script>
<!-- jqplot core + plugins -->
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jquery.jqplot.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.cursor.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.highlighter.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.dateAxisRenderer.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.donutRenderer.min.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="../moskito/int/js/charts/jqplot/jqplot.barRenderer.min.js"></script>

<script type="text/javascript" src="../moskito/int/js/charts/chartEngineIniter.js"></script>

<section id="main">
<%--
Commented out for now. We may add this later as welcome message (to all layers).
<div class="alert alert-warning alert-dismissable">
    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
    <strong>Welcome</strong> to moskito WebUI. <a href="">How it work?</a>
</div>
--%>
<div class="content">

<ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" id="decorator" name="decorators">
<div class="box">
<div class="box-title">
    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-right"></i></a>
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
                <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
                    <th title="${caption.shortExplanation}" class="{sorter: 'commaNumber'} table-column">
                        <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                        <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption}<i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                    </th>
                </ano:iterate>
                <th>Class</th>
            </tr>
            </thead>
            <tbody>
                    <%-- writing out values --%>
            <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
            <tr>
                <td class="headcol"><a href="mskShowProducer?pProducerId=${producer.producerId}" class="tooltip-bottom" title="Show details for producer ${producer.producerId}">${producer.producerId}</a></td>
                <td><a href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</a></td>
                <td><a href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</a></td>
                <ano:iterate name="producer" property="firstStatsValues" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
                    <td class="tooltip-bottom" title="${producer.producerId}.${value.name}=${value.value}">${value.value}</td>
                </ano:iterate>
                <td>${producer.producerClassName}</td>
            </tr>
            </ano:iterate>
            </tbody>
        </table>
    </div>

</div>
</div>
</ano:iterate>


</div>

<jsp:include page="../../shared/jsp/Footer.jsp"/>
<jsp:include page="ChartEngine.jsp"/>
</section>
<jsp:include page="snippet/ProducerHelpModal.jsp"/>
</body>
</html>
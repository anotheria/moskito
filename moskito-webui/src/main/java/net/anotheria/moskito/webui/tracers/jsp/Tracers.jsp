<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    MoSKito Tracers.
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    MoSKito Tracers are monitoring points that will log the usage of a certain producer whenever a call runs through them. Not all MoSKito producer support tracing. You can configure a new tracer either from producer detail view or via config.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Tracers (${tracersCount})
                </h3>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Name / ProducerId</th>
                        <th>Enabled</th>
                        <th>Current entries</th>
                        <th>Total calls seen</th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="tracers" type="net.anotheria.moskito.webui.tracers.api.TracerAO" id="tracer" indexId="index">
                        <tr>
                            <td><mos:deepLink  href="mskTracer?pProducerId=${tracer.producerId}">${tracer.producerId}</mos:deepLink ></td>
                            <td>${tracer.enabled}</td>
                            <td>${tracer.entryCount}</td>
                            <td>${tracer.totalEntryCount}</td>
                            <td>
                                <mos:deepLink  href="mskRemoveTracer?pProducerId=${tracer.producerId}" class="action-icon delete-icon tooltip-bottom" title="Delete"><i class="fa fa-ban"></i></mos:deepLink >
                                <c:if test="${tracer.enabled}">
                                    <mos:deepLink  href="mskDisableTracer?pProducerId=${tracer.producerId}" class="action-icon toggle-on-icon tooltip-bottom" title="On - Disable"><i class="fa fa-toggle-on"></i></mos:deepLink >
                                </c:if>
                                <c:if test="${tracer.disabled}">
                                    <mos:deepLink  href="mskEnableTracer?pProducerId=${tracer.producerId}" class="action-icon toggle-off-icon tooltip-bottom" title="Off - Enable"><i class="fa fa-toggle-off"></i></mos:deepLink >
                                </c:if>
                                <mos:deepLink  href="mskShowProducer?pProducerId=${tracer.producerId}" class="action-icon show-icon tooltip-bottom" title="ShowProducer"><i class="fa fa-search-plus"></i></mos:deepLink >
                                <mos:deepLink  href="mskAnalyzeJourney?pJourneyName=Traced-${tracer.producerId}" class="action-icon show-icon tooltip-bottom" title="" data-original-title="Analyze all traced calls in ${tracer.producerId}"><i class="fa fa-search-plus"></i></mos:deepLink >
                            </td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>
</body>
</html>



<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <div class="alert alert-warning alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        Please note that Tracers and Traces are currently beta. Use carefully.
        <a href="mailto:moskito-users@lists.anotheria.net">Feedback</a>!
    </div>

    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
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
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
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
                        <th>Entries</th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="tracers" type="net.anotheria.moskito.webui.tracers.api.TracerAO" id="tracer" indexId="index">
                        <tr>
                            <td><a href="mskTracer?pProducerId=${tracer.producerId}">${tracer.producerId}</a></td>
                            <td>${tracer.enabled}</td>
                            <td>${tracer.entryCount}</td>
                            <td>
                                <a href="mskRemoveTracer?pProducerId=${tracer.producerId}" class="action-icon delete-icon tooltip-bottom" title="Delete"><i class="fa fa-ban"></i></a>
                                <a href="mskDisableTracer?pProducerId=${tracer.producerId}" class="action-icon delete-icon tooltip-bottom" title="Disable"><i class="fa fa-toggle-off"></i></a>
                                <a href="mskRemoveTracer?pProducerId=${tracer.producerId}" class="action-icon delete-icon tooltip-bottom" title="ShowProducer"><i class="fa fa-search-plus"></i></a>
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



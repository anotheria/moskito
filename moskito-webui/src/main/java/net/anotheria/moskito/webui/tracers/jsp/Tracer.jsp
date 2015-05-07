<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<%--
    This page displays the traces of a single tracer.
--%>
<section id="main">
    <div class="content">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Please note that Tracers and Traces are currently beta. Use carefully.
            <a href="mailto:moskito-users@lists.anotheria.net">Feedback</a>!
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Tracer for ${tracer.producerId}, enabled: ${tracer.enabled}.
                </h3>
            </div>
            <div class="box-content">
                <table class="table table-striped table-tree tree">
                    <thead>
                    <tr>
                        <th>Call</th>
                        <th>Elements</th>
                        <th>Duration</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="traces" type="net.anotheria.moskito.webui.tracers.api.TraceAO" id="trace">
                        <tr data-level="0">
                            <td>
                                <div>
                                    <i class="minus">–</i><i class="plus">+</i><i class="vline"></i>${trace.call}
                                </div>
                            </td>
                            <td>${trace.elementCount}</td>
                            <td>${trace.duration}</td>
                        </tr>
                        <tr class="dump-list" data-level="1">
                            <td colspan="3">
                                <ul>
                                    <ano:iterate name="trace" property="elements" id="element" type="java.lang.StackTraceElement">
                                        <li>${element}</li>
                                    </ano:iterate>
                                </ul>
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



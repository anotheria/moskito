<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <div class="content">
        <ano:iterate name="callsList" type="net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO" id="call" indexId="index">
          <ano:equal name="call" property="empty" value="false">

        <div class="box">
            <div class="box-title">
                <h3 class="pull-left">
                    ${call.name}
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse${index}" class="box-content">
                <table class="table table-left table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>ProducerId</th>
                        <th>Calls</th>
                        <th>Duration</th>
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


    </div>

<jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>

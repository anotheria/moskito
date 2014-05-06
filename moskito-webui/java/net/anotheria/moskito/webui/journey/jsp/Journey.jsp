<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>
<section id="main">
    <div class="content">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    <a href="mskShowJourneys">Journeys</a> :: ${journey.name}
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>

            <p class="paddner">${journey}</p>
            <div id="collapse2" class="box-content accordion-body collapse in">

                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th># <i class="fa fa-caret-down"></i></th>
                        <th>Url</th>
                        <th>Date <i class="fa fa-caret-down"></i></th>
                        <th>Steps <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="recorded" type="net.anotheria.moskito.webui.journey.api.JourneySingleTracedCallAO" id="tracedCall" indexId="index">
                        <tr>
                            <td>${index}</td>
                            <td>
                                <a href="mskShowJourneyCall?pJourneyName=${journey.name}&pPos=${index}">
                                    ${tracedCall.name}
                                </a>
                            </td>
                            <td>${tracedCall.date}</td>
                            <td>${tracedCall.containedSteps}</td>

                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

<jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>
</section>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <ano:notPresent name="journeysPresent">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            You have no journeys yet, you can start a journey by following the instructions below or programmatically.
        </div>
    </ano:notPresent>


    <div class="content">

        <script type="text/javascript">
            function new_journey(name){
                var link = "<ano:write name="new_journey_url"/>"+name;
                window.open(link );
                //force reload
                location.href = location.href;
            }

            function stop_journey(name){
                var link = "<ano:write name="stop_journey_url"/>"+name;
                window.open(link);
                //force reload
                location.href = location.href;
            }
        </script>

        <div class="box">
            <div class="box-title">
                <i class="fa fa-caret-right"></i>
                <h3 class="pull-left">
                    Journeys
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div class="box-content">
                <div class="paddner">
                    <div class="jour-left form-inline">
                        <p>To start a new journey now enter the name for the journey</p>
                        <div class="form-group">
                            <form name="NEWJOURNEY">
                            <input type="text" name="name" class="form-control" placeholder="Enter Name">
                            <button class="btn btn-success" onclick="new_journey(document.NEWJOURNEY.name.value); return false">Start</button>
                            </form>
                        </div>
                    </div>
                    <div class="jour-right">
                        <p>To record a new journey add <span class="text-green">mskJourney=start&mskJourneyName=JOURNEY_NAME</span> to any url on this server.</p>
                        <p>To stop journey recording add <span class="text-green">mskJourney=stop&mskJourneyName=JOURNEY_NAME</span> to any url on this server.</p>
                    </div>
                </div>
            </div>
        </div>

        <ano:present name="journeysPresent">
        <div class="box">
            <div class="box-title">
                <h3 class="pull-left">
                    Recorded journeys.
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div class="box-content">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Journey <i class="fa fa-caret-down"></i></th>
                        <th>Started <i class="fa fa-caret-down"></i></th>
                        <th>Last activity <i class="fa fa-caret-down"></i></th>
                        <th>Calls <i class="fa fa-caret-down"></i></th>
                        <th>Active <i class="fa fa-caret-down"></i></th>
                        <th class="th-actions">&nbsp;</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="journeys" type="net.anotheria.moskito.webui.journey.api.JourneyListItemAO" id="journey" indexId="index">
                        <tr>
                            <td><a class="tooltip-bottom" title="Show steps in journey ${journey.name}" href="mskShowJourney?pJourneyName=${journey.name}">${journey.name}</a></td>
                            <td>${journey.created}</td>
                            <td>${journey.lastActivity}</td>
                            <td>${journey.numberOfCalls}</td>
                            <td>${journey.active}&nbsp;
                                <ano:equal name="journey" property="active" value="true">&nbsp;(<a href="#" onclick="stop_journey('${journey.name}')">stop</a>)</ano:equal>
                            </td>

                            <td>
                                <a class="action-icon delete-icon tooltip-bottom" title="" data-original-title="Delete ${journey.name}" href="mskDeleteJourney?pJourneyName=${journey.name}"><i class="fa fa-ban"></i></a>
                                <a class="action-icon show-icon tooltip-bottom" title="" data-original-title="Show steps in journey ${journey.name}"  href="mskShowJourney?pJourneyName=${journey.name}"><i class="fa fa-search"></i></a>
                                <a class="action-icon show-icon tooltip-bottom" title="" data-original-title="Analyze journey ${journey.name}"  href="mskAnalyzeJourney?pJourneyName=${journey.name}"><i class="fa fa-search-plus"></i></a>

                            </td>

                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
        </ano:present>

    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>
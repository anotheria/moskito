<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Journeys</title>
    <link rel="stylesheet" href="mskCSS"/>
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
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
            <h3>Journeys</h3>
            <form name="NEWJOURNEY" class="journeys-form" action=""><span>To start a new journey <b>now</b> enter the name for the journey <input type="text" name="name" size="10"> and <input type="button" value="Click here" class="btn-blue" onclick="new_journey(document.NEWJOURNEY.name.value); return false"/></span></form>
            <%--<form name="STOPJOURNEY"><span>If you want to stop a journey <input type="text" name="name" size="10"> and <input type="button" value="click here" onclick="stop_journey(document.STOPJOURNEY.name.value); return false"/></form><br/><br/>--%>
            <div class="journeys-page">
				<span>To record a new journey add <code>mskJourney=start&mskJourneyName=JOURNEY_NAME</code> to any url on this server.</span><br/>
				<span>To stop journey recording add <code>mskJourney=stop&mskJourneyName=JOURNEY_NAME</code> to any url on this server.</span>
            </div>
        </div>
        <div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<ano:present name="journeysPresent" scope="request">
	<div class="table_layout">
		<div class="top"><div><!-- --></div></div>
		<div class="in">		
			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">				
					<table cellpadding="4" cellspacing="0" width="100%">
						<thead>
							<tr class="stat_header">
								<th>Journey</th>
								<th>Started</th>
								<th>Last activity</th>
								<th>Calls</th>
								<th>Active</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<ano:iterate name="journeys" type="net.anotheria.moskito.webui.journey.api.JourneyListItemAO" id="journey" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><a href="mskShowJourney?pJourneyName=<ano:write name="journey" property="name"/>"><ano:write name="journey" property="name"/></a></td>
									<td><ano:write name="journey" property="created"/></td>
									<td><ano:write name="journey" property="lastActivity"/></td>
									<td><ano:write name="journey" property="numberOfCalls"/></td>
									<td><ano:write name="journey" property="active"/>
                                        <ano:equal name="journey" property="active" value="true">&nbsp;(<a href="#" onclick="stop_journey('<ano:write name="journey" property="name"/>')">stop</a>)</ano:equal>
									</td>
									<td><a title="analyze this journey" href="mskAnalyzeJourney?pJourneyName=<ano:write name="journey" property="name"/>" class="zoom"></a></td>
								</tr>
							</ano:iterate>
						</tbody>
					</table>		

				<div class="clear"><!-- --></div>
						</div>
								<div class="bot">
									<div class="left"><!-- --></div>
									<div class="right"><!-- --></div>
								</div>
							</div>
				   </div>	
	</ano:present>		
	<ano:notPresent name="journeysPresent" scope="request">
		<i>No journeys recorded.</i>
	</ano:notPresent>	
	<jsp:include page="../../shared/jsp/Footer.jsp" flush="false" />
</div>
</body>
</html>
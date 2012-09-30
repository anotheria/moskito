<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Journeys</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">	
	<div class="clear"><!-- --></div>
		
	<h3><span>Journeys</span></h3>
	
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>
				<span>To record a new journey add <code>mskJourney=start&mskJourneyName=JOURNEY_NAME</code> to any url on this server.</span>
				<span>To stop journey recording add <code>mskJourney=stop&mskJourneyName=JOURNEY_NAME</code> to any url on this server.</span>				
			</div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<msk:present name="journeysPresent" scope="request">
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
								<th>Session</th>
								<th>Started</th>
								<th>Last activity</th>
								<th>Calls</th>
								<th>Active</th>
								<th>&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<msk:iterate name="journeys" type="net.java.dev.moskito.webui.bean.JourneyListItemBean" id="journey" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><a href="mskShowJourney?pJourneyName=<msk:write name="journey" property="name"/>"><msk:write name="journey" property="name"/></td>
									<td><msk:write name="journey" property="created"/></td>
									<td><msk:write name="journey" property="lastActivity"/></td>
									<td><msk:write name="journey" property="numberOfCalls"/></td>
									<td><msk:write name="journey" property="active"/></td>
									<td><a title="analyze this journey" href="mskAnalyzeJourney?pJourneyName=<msk:write name="journey" property="name"/>" class="zoom"></a></td>
								</tr>
							</msk:iterate>
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
	</msk:present>		
	<msk:notPresent name="journeysPresent" scope="request">
		<i>No journeys recorded.</i>
	</msk:notPresent>	
	<jsp:include page="Footer.jsp" flush="false" />
</div>
</body>
</html>
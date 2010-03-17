<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito MonitoringSessions</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">	
	<div class="clear"><!-- --></div>
		
	<h3><span>Monitoring sessions</span></h3>
	
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>
				<span>To record a new session add <code>mskMonitoringSession=start&mskSessionName=SESSION_NAME</code> to any url on this server.</span>
				<span>To stop session recording add <code>mskMonitoringSession=stop&mskSessionName=SESSION_NAME</code> to any url on this server.</span>				
			</div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<msk:present name="monitoringSessionsPresent" scope="request">
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
							</tr>
						</thead>
						<tbody>
							<msk:iterate name="monitoringSessions" type="net.java.dev.moskito.webui.bean.MonitoringSessionListItemBean" id="ms" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><a href="mskShowMonitoringSession?pSessionName=<msk:write name="ms" property="name"/>"><msk:write name="ms" property="name"/></td>
									<td><msk:write name="ms" property="created"/></td>
									<td><msk:write name="ms" property="lastActivity"/></td>
									<td><msk:write name="ms" property="numberOfCalls"/></td>
									<td><msk:write name="ms" property="active"/></td>
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
	<msk:notPresent name="monitoringSessionsPresent" scope="request">
		<i>No monitoring sessions recorded.</i>
	</msk:notPresent>	
</div>
</body>
</html>
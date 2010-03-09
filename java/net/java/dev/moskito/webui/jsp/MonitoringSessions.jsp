<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito MonitoringSessions</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="MonitoringSessionMainMenu.jsp" flush="false"/>
<h3>Monitoring sessions</h3>
<msk:present name="monitoringSessionsPresent" scope="request">
<table cellpadding="4" cellspacing="0" border="0">
	<tr class="stat_header">
		<td>Session</td>
		<td>Started</td>
		<td>Last activity</td>
		<td>Calls</td>
		<td>Active</td>
	</tr>
	<msk:iterate name="monitoringSessions" type="net.java.dev.moskito.webui.bean.MonitoringSessionListItemBean" id="ms" indexId="index">
		<tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>">
			<td><a href="mskShowMonitoringSession?pSessionName=<msk:write name="ms" property="name"/>"><msk:write name="ms" property="name"/></td>
			<td><msk:write name="ms" property="created"/></td>
			<td><msk:write name="ms" property="lastActivity"/></td>
			<td><msk:write name="ms" property="numberOfCalls"/></td>
			<td><msk:write name="ms" property="active"/></td>
		</tr>
	</msk:iterate>
</table>
</msk:present>
<msk:notPresent name="monitoringSessionsPresent" scope="request">
	<i>No monitoring sessions recorded.</i>
</msk:notPresent>
<br/><br/><br/>
<i>To record a new session add <code>mskMonitoringSession=start&mskSessionName=SESSION_NAME</code> to any url on this server.<br/>
To stop session recording add <code>mskMonitoringSession=stop&mskSessionName=SESSION_NAME</code> to any url on this server.<br/>
</i>
</body>
</html>
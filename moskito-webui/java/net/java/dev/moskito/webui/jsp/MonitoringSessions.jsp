<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito MonitoringSessions</title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Monitoring sessions</h3>
<logic:present name="monitoringSessionsPresent" scope="request">
<table cellpadding="4" cellspacing="0" border="0">
	<tr class="stat_header">
		<td>Session</td>
		<td>Started</td>
		<td>Last activity</td>
		<td>Calls</td>
		<td>Active</td>
	</tr>
	<logic:iterate name="monitoringSessions" type="net.java.dev.moskito.webui.bean.MonitoringSessionListItemBean" id="ms" indexId="index">
		<tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>">
			<td><a href="mskShowMonitoringSession?pSessionName=<bean:write name="ms" property="name"/>"><bean:write name="ms" property="name"/></td>
			<td><bean:write name="ms" property="created"/></td>
			<td><bean:write name="ms" property="lastActivity"/></td>
			<td><bean:write name="ms" property="numberOfCalls"/></td>
			<td><bean:write name="ms" property="active"/></td>
		</tr>
	</logic:iterate>
</table>
</logic:present>
<logic:notPresent name="monitoringSessionsPresent" scope="request">
	<i>No monitoring sessions recorded.</i>
</logic:notPresent>
<br/><br/><br/>
<i>To record a new session add <code>mskMonitoringSession=start&mskSessionName=SESSION_NAME</code> to any url on this server.<br/>
To stop session recording add <code>mskMonitoringSession=stop&mskSessionName=SESSION_NAME</code> to any url on this server.<br/>
</i>
</body>
</html>
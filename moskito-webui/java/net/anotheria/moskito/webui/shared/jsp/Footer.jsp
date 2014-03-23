<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><div class="generated">Generated at <ano:write name="timestampAsDate"/>&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <ano:write name="timestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp;Interval updated at: <ano:write name="currentIntervalUpdateTimestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp; Interval age: <ano:write name="currentIntervalUpdateAge"/></div>
<div class="generated">App version: <ano:write name="application.maven.version"/>&nbsp;&nbsp;|&nbsp;&nbsp;MoSKito version: <ano:write name="moskito.maven.version"/>&nbsp;&nbsp;|&nbsp;&nbsp;Server: <ano:write name="servername" scope="application"/>
&nbsp;&nbsp;|&nbsp;&nbsp;Connect:&nbsp;<ano:write name="connection"/></div>
<ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/webui/<ano:write name="moskito.version_string"/>/<ano:write name="pagename"/>" class="ipix"> </ano:equal>

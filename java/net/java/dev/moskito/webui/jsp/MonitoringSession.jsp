<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito UseCases</title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Monitoring Session <bean:write name="msession"/></h3>
	<ul>
	<logic:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="iii">
		<li><a href="mskShowMonitoringSessionCall?pSessionName=<bean:write name="msession" property="name"/>&pPos=<bean:write name="iii"/>"><bean:write name="useCase" property="name"/></a>&nbsp;&nbsp;<bean:write name="useCase" property="date"/></li>
	</logic:iterate>
	</ul>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito UseCases</title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Monitoring Session <msk:write name="msession"/></h3>
	<ul>
	<logic:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="iii">
		<li><a href="mskShowMonitoringSessionCall?pSessionName=<msk:write name="msession" property="name"/>&pPos=<msk:write name="iii"/>"><msk:write name="useCase" property="name"/></a>&nbsp;&nbsp;<msk:write name="useCase" property="date"/></li>
	</logic:iterate>
	</ul>
</body>
</html>
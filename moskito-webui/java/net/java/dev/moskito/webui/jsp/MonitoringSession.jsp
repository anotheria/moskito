<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
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
	<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="iii">
		<li><a href="mskShowMonitoringSessionCall?pSessionName=<msk:write name="msession" property="name"/>&pPos=<msk:write name="iii"/>"><msk:write name="useCase" property="name"/></a>&nbsp;&nbsp;<msk:write name="useCase" property="date"/></li>
	</msk:iterate>
	</ul>
</body>
</html>
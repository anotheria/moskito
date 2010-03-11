<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito UseCases</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
<div class="clear"><!-- --></div>
	<h3>Show Monitoring Session <msk:write name="msession"/></h3>
		<ul>
		<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="iii">
			<li>
				<a href="mskShowMonitoringSessionCall?pSessionName=<msk:write name="msession" property="name"/>&pPos=<msk:write name="iii"/>">
					<msk:write name="useCase" property="name"/>
				</a>
				&nbsp;&nbsp;
				<msk:write name="useCase" property="date"/>
			</li>
		</msk:iterate>
		</ul>
</div>	
</body>
</html>  
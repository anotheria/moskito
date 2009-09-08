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
<h3>Show Use Cases</h3>
<h2>AdHoc Use Cases</h2>
<logic:present name="recordedAvailableFlag" scope="request">
	<ul>
	<logic:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase">
		<li><a href="mskShowRecordedUseCase?pUseCaseName=<bean:write name="useCase" property="nameEncoded"/>"><bean:write name="useCase" property="name"/></a>&nbsp;<bean:write name="useCase" property="date"/></li>
	</logic:iterate>
	</ul>
</logic:present>
<logic:notPresent name="recordedAvailableFlag" scope="request">
	<i>No use cases recorded yet</i>
</logic:notPresent>
<br><br>
<i>To record an addHoc use case add <code>mskCommand=recordUseCase&mskUseCaseName=USE-CASE-NAME</code> to any url on this server except this.</i>
<h2>Permanent Use Cases</h2>
<i>Display page is yet unimplemented</i>
</body>
</html>
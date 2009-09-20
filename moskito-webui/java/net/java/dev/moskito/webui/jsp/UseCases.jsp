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
<h3>Show Use Cases</h3>
<h2>AdHoc Use Cases</h2>
<msk:present name="recordedAvailableFlag" scope="request">
	<ul>
	<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase">
		<li><a href="mskShowRecordedUseCase?pUseCaseName=<msk:write name="useCase" property="nameEncoded"/>"><msk:write name="useCase" property="name"/></a>&nbsp;<msk:write name="useCase" property="date"/></li>
	</msk:iterate>
	</ul>
</msk:present>
<msk:notPresent name="recordedAvailableFlag" scope="request">
	<i>No use cases recorded yet</i>
</msk:notPresent>
<br><br>
<i>To record an addHoc use case add <code>mskCommand=recordUseCase&mskUseCaseName=USE-CASE-NAME</code> to any url on this server except this.</i>
<h2>Permanent Use Cases</h2>
<i>Display page is yet unimplemented</i>
</body>
</html>
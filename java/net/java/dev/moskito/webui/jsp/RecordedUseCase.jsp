<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>
<html>
<head>
	<title>Moskito Recorded UseCase: <msk:write name="recordedUseCase" property="name"/></title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Recorded Use Case: <msk:write name="recordedUseCase" property="name"/> @ <msk:write name="recordedUseCase" property="date"/>&nbsp; (<msk:write name="recordedUseCase" property="created"/>)</h3>
<br/>
<msk:present name="units" scope="request">
	<jsp:include page="UnitSelection.jsp" flush="false"/>
</msk:present>
<br/>
<br/><br/><br/><br/>

TREE


<br/><br/><br/><br/>
<table cellpadding="4" cellspacing="0" border="0">
	<tr class="stat_header">
		<td>Call</td>
		<td>Gross duration</td>
		<td>Net duration</td>
		<td width="1%">Aborted</td>
	</tr>
	<msk:iterate name="recordedUseCase" property="elements" type="net.java.dev.moskito.webui.bean.UseCasePathElementBean" id="element" indexId="index">
		<msk:equal name="element" property="aborted" value="true"><tr class="stat_error"></msk:equal>
		<msk:notEqual name="element" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>"></msk:notEqual>
			<td><% for (int i=1; i<element.getLayer(); i++){ %><img src="../img/s.gif"/><%}%><msk:equal name="element" property="root" value="false"><img src="../img/l.gif"/></msk:equal><msk:write name="element" property="call"/></td>
			<td><msk:write name="element" property="duration"/></td>
			<td><msk:write name="element" property="timespent"/></td>
			<td><msk:equal name="element" property="aborted" value="true">X</msk:equal>
		</tr>
	</msk:iterate>
</table>
<br><br>

</body>
</html>
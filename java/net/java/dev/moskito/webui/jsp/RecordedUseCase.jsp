<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito Recorded UseCase: <bean:write name="recordedUseCase" property="name"/></title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Recorded Use Case: <bean:write name="recordedUseCase" property="name"/> @ <bean:write name="recordedUseCase" property="date"/>&nbsp; (<bean:write name="recordedUseCase" property="created"/>)</h3>
<br/>
<logic:present name="units" scope="request">
	<jsp:include page="UnitSelection.jsp" flush="false"/>
</logic:present>
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
	<logic:iterate name="recordedUseCase" property="elements" type="net.java.dev.moskito.webui.bean.UseCasePathElementBean" id="element" indexId="index">
		<logic:equal name="element" property="aborted" value="true"><tr class="stat_error"></logic:equal>
		<logic:notEqual name="element" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>"></logic:notEqual>
			<td><% for (int i=1; i<element.getLayer(); i++){ %><img src="../img/s.gif"/><%}%><logic:equal name="element" property="root" value="false"><img src="../img/l.gif"/></logic:equal><bean:write name="element" property="call"/></td>
			<td><bean:write name="element" property="duration"/></td>
			<td><bean:write name="element" property="timespent"/></td>
			<td><logic:equal name="element" property="aborted" value="true">X</logic:equal>
		</tr>
	</logic:iterate>
</table>
<br><br>

</body>
</html>
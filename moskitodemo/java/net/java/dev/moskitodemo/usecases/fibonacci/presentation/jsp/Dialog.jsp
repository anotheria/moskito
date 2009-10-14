<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" 
%><%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" 
%><%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" 
%>
<html>
<head>
<title>Use Case example, fibonacci numbers calculation</title>
<jsp:include page="../../guestbook/presentation/jsp/Pragmas.jsp" flush="true"/>
<bean:message key="styles.gb.link"/>
</head>
<body>
<br/>
<p>This example will demonstrate moskito's ability to record a use case on the fly and produce a call tree 
for any call along all monitoring points by simple means. In the web interface the recording is triggered by simply
adding two parameters to and url. </p><br/><br/>
<p>By clicking the submit button the fibonacci number of the given order (max 20) will be calculated with by recursion,
and the complete call tree of the calculation will be saved under the name <b><bean:write name="useCaseName"/></b> in <a href="/moskitodemo/mui/mskShowUseCases">
the UseCase section</a>.</p><br/><br/>
<form action="fibonacci" method="get">
	Order: <input type="text" name="pOrder" size="3">&nbsp; values between 0 and 20 are valid.
	<input type="hidden" name="mskUseCaseName" value="<bean:write name="useCaseName"/>"/>
	<input type="hidden" name="mskCommand" value="recordUseCase"/>
<br/><br/><input type="submit" value="Calculate"/></form>
<br/>



</body></html>


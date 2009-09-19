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
<p>Thanx, the use case <b><bean:write name="useCaseName"/></b> is now recorded. Please click <a href="/moskitodemo/mui/mskShowRecordedUseCase?pUseCaseName=<bean:write name="useCaseName"/>&pUnit=micros">here</a> to inspect it. Alternatively you'll find it 
under UseCases in moskito webui from now on.
</p><br/><br/>
<p>Alas, fibonacci(<bean:write name="order"/>) = <bean:write name="result"/></p>
<br/><br/>
<a href="/moskitodemo">Return to the demo</a> or go to the <a href="/moskitodemo/mui/mskShowUseCases">web interface</a>.
</body></html>


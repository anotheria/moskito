<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<html>
<head>
<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/common.css"/>
<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/gb.css"/>
<title>TracedCall example, fibonacci numbers calculation</title>
</head>
<body>
<br/>
<p>Thanx, the use case <b><ano:write name="useCaseName"/></b> is now recorded.
Please click <a href="/moskitodemo/mui/mskShowRecordedUseCase?pUseCaseName=<ano:write name="useCaseName"/>&pUnit=micros">here</a> to inspect it. 
Alternatively you'll find it under UseCases in moskito webui from now on.
</p><br/><br/>
<p>Alas, fibonacci(<ano:write name="order"/>) = <ano:write name="result"/></p>
<br/><br/>
<a href="/moskitodemo">Return to the demo</a> or go to the <a href="/moskitodemo/mui/mskShowUseCases">web interface</a>.
</body></html>


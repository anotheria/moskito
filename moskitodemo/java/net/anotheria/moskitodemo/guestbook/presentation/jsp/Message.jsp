<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<html>
<head>
<title>Moskito Guestbook, Create Comment</title>
<jsp:include page="Pragmas.jsp" flush="true"/>
<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/common.css"/>
<link type="text/css" rel="stylesheet" rev="stylesheet" href="/moskitodemo/css/gb.css"/>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Message:</strong></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><ano:write name="message" property="messageText"/></td>
  </tr>
<ano:notEqual name="message" property="link" value="">
  <tr class="lineDark"> 
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">&nbsp;</td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><a href="<ano:write name="message" property="link"/>"><ano:write name="message" property="linkCaption"/></a></td>
  </tr>
</ano:notEqual>
</table>
<jsp:include page="Footer.jsp" flush="true"/>
</body>
</html>

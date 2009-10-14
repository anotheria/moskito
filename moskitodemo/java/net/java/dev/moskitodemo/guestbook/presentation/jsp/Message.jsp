<%@ page language="java" contentType="text/html;charset=iso-8859-1" session="true"
%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" 
%><%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" 
%><%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" 
%>
<html:xhtml/>
<html:html>
<head>
<title>Moskito Guestbook, Create Comment</title>
<jsp:include page="Pragmas.jsp" flush="true"/>
<bean:message key="styles.gb.link"/>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="3">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Message:</strong></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><bean:write name="message" property="messageText"/></td>
  </tr>
<logic:notEqual name="message" property="link" value="">
  <tr class="lineDark"> 
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">&nbsp;</td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><a href="<bean:write name="message" property="link"/>"><bean:write name="message" property="linkCaption"/></a></td>
  </tr>
</logic:notEqual>
</table>
<jsp:include page="Footer.jsp" flush="true"/>
</body>
</html:html>

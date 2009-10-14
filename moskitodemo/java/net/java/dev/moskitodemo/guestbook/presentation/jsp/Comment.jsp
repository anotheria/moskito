<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" 
%><%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" 
%><%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" 
%>
<html:xhtml/>
<html:html>
<head>
<title>Moskito Guestbook, Read Comment</title>
<jsp:include page="Pragmas.jsp" flush="true"/>
<bean:message key="styles.gb.link"/>
</head>
<body>
<table width="100%" cellpadding="3" cellspacing="0" border="0">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Read comment:</strong></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">Date:&nbsp;</td>
  	<td align="left" width="65%"><bean:write name="comment" property="date"/>&nbsp;<i>(Central European Time)</td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">Author:&nbsp;</td>
  	<td align="left" width="65%"><bean:write name="comment" property="firstName"/>&nbsp;<bean:write name="comment" property="lastName"/></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">Email:&nbsp;</td>
  	<td align="left" width="65%"><bean:write name="comment" property="email"/>&nbsp;</td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">Text:&nbsp;</td>
  	<td align="left" width="65%"><pre><bean:write name="comment" property="text"/></pre>
  	</td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">&nbsp;</td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><a href="gbookShowComments">Back</a></td>
  </tr>
</table>
<jsp:include page="Footer.jsp" flush="true"/>
</html:html>
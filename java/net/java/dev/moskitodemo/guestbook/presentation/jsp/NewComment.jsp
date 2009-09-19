<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
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
<table width="100%" cellpadding="3" cellspacing="0" border="0">
 <html:form action="gbookCreateComment">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Create a comment:</strong></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">First Name:&nbsp;</td>
  	<td align="left" width="65%"><html:text property="firstName" size="40" maxlength="100"/></td>
  </tr>
  <tr class="lineDark"> 
  	<td align="right" width="35%">Last Name:&nbsp;</td>
  	<td align="left" width="65%"><html:text property="lastName" size="40" maxlength="100"/></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">Email:&nbsp;</td>
  	<td align="left" width="65%"><html:text property="email" size="40" maxlength="100"/>&nbsp;<i>Your email will not be shown to other users</i></td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">Updates wished:&nbsp;</td>
  	<td align="left" width="65%"><html:checkbox property="updateFlagChecked"/>&nbsp;<i>Do you wish to be notified about new Moskito versions?</i></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">Text:&nbsp;</td>
  	<td align="left" width="65%"><html:textarea property="text" rows="5" cols="80"/></td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">&nbsp;</td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">
  		<a href="#" onClick="document.CommentForm.submit(); return false">&raquo;&nbsp;submit</a>&nbsp;&nbsp;
  		<a href="gbookShowComments">&raquo;&nbsp;back</a>&nbsp;
  	</td>
  </tr>
 </html:form>
</table>
<jsp:include page="Footer.jsp" flush="true"/>
</body>
</html:html>
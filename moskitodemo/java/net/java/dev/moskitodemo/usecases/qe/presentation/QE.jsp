<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" 
%><%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" 
%><%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" 
%>
<html:xhtml/>
<html:html>
<head>
<title>Use Case example, solve quadratic equation</title>
<jsp:include page="../../guestbook/presentation/jsp/Pragmas.jsp" flush="true"/>
<bean:message key="styles.gb.link"/>
</head>
<body>
<table width="100%" cellpadding="3" cellspacing="0" border="0">
 <html:form action="solveQE">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Enter a new equation of type <b>ax<sup>2</sup>+bx+c=0</b>:</strong></td>
  </tr>
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">
  		<html:text property="a" size="3" maxlength="10"/>&nbsp;x<sup>2</sup>+
  		<html:text property="b" size="3" maxlength="10"/>&nbsp;x+
  		<html:text property="c" size="3" maxlength="10"/>&nbsp;=&nbsp;0
  	</td>
  </tr>
  <tr class="lineDark">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">
  		<a href="#" onClick="document.QEParameterForm.submit(); return false">&raquo;&nbsp;solve</a>&nbsp;&nbsp;
  	</td>
  </tr>
 </html:form>
</table>
<br/><br/>
<table width="100%" cellpadding="3" cellspacing="0" border="0">
<logic:present name="errors">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Following errors occured:</td>
  </tr>
  <logic:iterate name="errors" type="java.lang.String" id="errmsg">
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">
  		<font color="red"><bean:write name="errmsg"/></font>
  	</td>
  </tr>
  </logic:iterate>
</logic:present>
<logic:present name="solutions">
  <tr class="lineCaptions">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%"><strong>Following solutions found:</td>
  </tr>
  <logic:iterate name="solutions" type="java.lang.Double" id="solution">
  <tr class="lineLight">
  	<td align="right" width="35%">&nbsp;</td>
  	<td align="left" width="65%">
  		<font color="green">x&nbsp;=&nbsp;<bean:write name="solution"/></font>
  	</td>
  </tr>
  </logic:iterate>
</logic:present>
</table>
<!--jsp:include page="Footer.jsp" flush="true"/-->
</body>
</html:html>
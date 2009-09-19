<%@ page language="java" contentType="text/html;charset=iso-8859-1" session="true"
%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" 
%><%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" 
%><%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" 
%>
<html:xhtml/>
<html:html>
<head>
<title>Moskito Guestbook Overview</title>
<jsp:include page="Pragmas.jsp"/>
<bean:message key="styles.gb.link"/>
</head>
<body>
<h2>Welcome to Moskito's Guestbook</h2>
Moskito's guestbook is an example application for moskito-struts integration. At the same time it offers us the possibility 
to get some feedback from you, dear user. Therefore we stronly encourage you to <a href="gbookNewComment">&raquo;&nbsp;post a comment</a>.<br/>
Thank you.<br/> The moskito team.<br/><br/>
Click the A/Z link in a column to resort the view. Click any text in the row to view the whole comment. 
<br/><br/><br/>
<table width="100%" cellpadding="3" cellspacing="0" border="0">
  <tr class="lineCaptions">
  	<logic:iterate name="headers" id="header" type="net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableHeaderBean">
		<td><bean:write name="header" property="caption"/>&nbsp;
		<logic:iterate name="header" property="links" type="net.java.dev.moskitodemo.guestbook.presentation.bean.SortLinkBean" id="link">
			<logic:equal name="link" property="active" value="true">
				<b><bean:write name="link" property="caption"/></b>
			</logic:equal>
			<logic:equal name="link" property="active" value="false">
				<a href="gbookShowComments?<bean:write name="link" property="link"/>"><bean:write name="link" property="caption"/></a>
			</logic:equal>
		</logic:iterate>
  	</logic:iterate>
  	<logic:equal name="authorized" value="true">
  		<td>&nbsp;</td>
  	</logic:equal>
  </tr>
  <logic:iterate name="comments" id="comment" type="net.java.dev.moskitodemo.guestbook.presentation.bean.CommentTableItemBean" indexId="i">
  	<tr class="<%=i.intValue()%2==1	 ? "lineDark" : "lineLight"%>">
  		<td><a href="gbookShowComment?pComment=<bean:write name="comment" property="id"/>"><bean:write name="comment" property="date"/></a></td>
  		<td><a href="gbookShowComment?pComment=<bean:write name="comment" property="id"/>"><bean:write name="comment" property="firstName"/></a></td>
  		<td><a href="gbookShowComment?pComment=<bean:write name="comment" property="id"/>"><bean:write name="comment" property="lastName"/></a></td>
  		<td><a href="gbookShowComment?pComment=<bean:write name="comment" property="id"/>"><bean:write name="comment" property="email"/></a></td>
  		<td><a href="gbookShowComment?pComment=<bean:write name="comment" property="id"/>"><bean:write name="comment" property="comment"/></a></td>
	  	<logic:equal name="authorized" value="true">
  			<td><a href="gbookDeleteComment?pComment=<bean:write name="comment" property="id"/>">Delete</a></td>
  		</logic:equal>
  	</tr>
  </logic:iterate> 
</table>
<br/><br/>
<a href="gbookNewComment">&raquo;&nbsp;post a comment!</a>
<jsp:include page="Footer.jsp" flush="true"/>
</body>
</html:html>
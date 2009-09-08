<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito Producer <bean:write name="producerId"/> </title>
<link rel="stylesheet" href="mskCSS?">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Inspect Producer</h3>
<i><a href="<bean:write name="linkToCurrentPage"/>&pForward=xml">This page as XML</a></i><br/><br/>
producer:&nbsp;<bean:write name="producerId"/>&nbsp;
created at:&nbsp;<bean:write name="creationTime"/>&nbsp;(<bean:write name="creationTimestamp"/>)
<br/>
<ul>
<logic:iterate name="creationTrace" type="java.lang.String" id="line">
	<li><bean:write name="line"/></li>
</logic:iterate>
</ul>

<br/><br/>
<small>Generated at <bean:write name="timestampAsDate"/>, timestamp: <bean:write name="timestamp"/></small>
</body>
</html> 


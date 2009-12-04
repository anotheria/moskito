<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<html>
<head>
	<title>Moskito Producer <msk:write name="producerId"/> </title>
<link rel="stylesheet" href="mskCSS?">
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Inspect Producer</h3>
<i><a href="<msk:write name="linkToCurrentPage"/>&pForward=xml">This page as XML</a></i><br/><br/>
producer:&nbsp;<msk:write name="producerId"/>&nbsp;
created at:&nbsp;<msk:write name="creationTime"/>&nbsp;(<msk:write name="creationTimestamp"/>)
<br/>
<ul>
<msk:iterate name="creationTrace" type="java.lang.String" id="line">
	<li><msk:write name="line"/></li>
</msk:iterate>
</ul>

<br/><br/>
<small>Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/></small>
</body>
</html> 


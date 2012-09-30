<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>
<html>
<head>
	<title>Moskito Producer <msk:write name="producerId"/> </title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
		<div>
			<h3>Inspect Producer</h3>
		</div>	
		<div>
			<span>producer:&nbsp;<msk:write name="producerId"/>&nbsp;</span>
		</div>
		<div>
			<span>created at:&nbsp;<msk:write name="creationTime"/>&nbsp;(<msk:write name="creationTimestamp"/>)</span>
		</div>	
		<div class="bot"><div><!-- --></div></div>	
	</div>
	<div class="clear"><!-- --></div>
	<div>
		<ul>
			<msk:iterate name="creationTrace" type="java.lang.String" id="line">
				<li><msk:write name="line"/></li>
			</msk:iterate>
		</ul>
	</div>
	 <div class="clear"><!-- --></div>
	<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/></div>
</div>
</div>
</body>
</html> 


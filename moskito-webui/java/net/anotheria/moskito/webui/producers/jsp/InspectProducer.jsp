<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>
<html>
<head>
	<title>Moskito Producer <msk:write name="producerId"/> </title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in ovh">
            <h3>Inspect Producer</h3>
            <dl class="dl-horizontal">
                <dt>Producer:</dt><dd><msk:write name="producerId"/>&nbsp;</dd>
                <dt>Created at:</dt><dd><msk:write name="creationTime"/>&nbsp;(<msk:write name="creationTimestamp"/>)</dd>
            </dl>
	    </div>
        <div class="bot"><div><!-- --></div></div>
    </div>
	<div class="clear"><!-- --></div>
    <div class="table_layout">
        <div class="top"><div><!-- --></div></div>
        <div class="in">
            <ul class="inspect-list">
                <msk:iterate name="creationTrace" type="java.lang.String" id="line">
                    <li><msk:write name="line"/></li>
                </msk:iterate>
            </ul>
        </div>
        <div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/></div>
</div>
</body>
</html> 


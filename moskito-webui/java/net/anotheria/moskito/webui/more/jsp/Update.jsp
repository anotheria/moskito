<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Moskito Libs</title>
    <link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<div class="main">
    <div class="additional">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="add_in">
            <h2>MoSKito Update</h2>

            <div><span>
            Your version: <ano:write name="moskito.maven.version"/><br/>
            Central version: <ano:write name="version"/> from <ano:write name="versionTimestamp"/><br/>
			</span></div>

        </div>
        <div class="bot">
            <div><!-- --></div>
        </div>
    </div>

    <div class="clear"><!-- --></div>

    <div class="clear"><!-- --></div>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false" />
</div>
</body>
</html>


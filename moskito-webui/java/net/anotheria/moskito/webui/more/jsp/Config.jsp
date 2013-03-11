<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MoSKito :: Config</title>
	<link rel="stylesheet" href="mskCSS"/>
    <script type="text/json" id="configstring-json">
        <ano:write name="configstring"/>
    </script>

</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="add_in">
			<h2>Current configuration</h2>

			<div>
                <pre><ano:write name="configstring"/></pre>
            </div>
            <%--
               <script type="text/javascript">
                   $().ready(function() {
                       var configstringJSON = $("#configstring-json").html();
                       $("#configstring-area").text(JSON.stringify(configstringJSON));
                   });
               </script>
            --%>
		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>

    <div class="additional">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="add_in">
            <h2>Configured thresholds</h2>
            <ano:iterate name="thresholdsStrings" id="t">
                <div>
                    <pre><ano:write name="t"/></pre>
                </div>
            </ano:iterate>
        </div>
        <div class="bot">
            <div><!-- --></div>
        </div>
    </div>

</div>
</body>
</html>
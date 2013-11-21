<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
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

    <div class="additional">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="add_in">
            <h2>Chart Engines</h2>
            <h3>Available engines:</h3>
            <ul>
            <ano:iterate name="availableChartEngines" id="engine">
                <li><ano:write name="engine"/>, parameters: <ano:iterate name="engine" property="names" id="n"> <ano:write name="n"/></ano:iterate></li>
            </ano:iterate>
            </ul>
            <h3>Selected engine:</h3>
            <ano:write name="chartEngine"/>
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
            <h2>WebUI Config (mskwebui.json)</h2>
            producerChartWidth:  <ano:write name="config" property="producerChartWidth"/><br>
            producerChartHeight: <ano:write name="config" property="producerChartHeight"/><br>
            defaultChartEngine: <ano:write name="config" property="defaultChartEngine"/><br>
        </div>
        <div class="bot">
            <div><!-- --></div>
        </div>
    </div>
</div>
</body>
</html>
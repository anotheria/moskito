<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Edit Threshold <ano:write name="definition" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

    <script type="text/javascript" src="../js/wz_tooltip.js"></script>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/function.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>
    <script>
        function switchDirection(){
            if (document.forms.UpdateForm.greenDir.value=='above')
                targetValue = 'below';
            else
                targetValue = 'above';

            document.forms.UpdateForm.yellowDir.value = targetValue;
            document.forms.UpdateForm.orangeDir.value = targetValue;
            document.forms.UpdateForm.redDir.value = targetValue;
            document.forms.UpdateForm.purpleDir.value = targetValue;
        }

        function switchgreenvalue(){
            document.forms.CreateForm.pYellowValue.value=document.forms.CreateForm.pGreenValue.value;
        }
    </script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<div class="main">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
            <form name="UpdateForm" action="mskThresholdUpdate" class="form-inline" method="GET">
                <input type="hidden" name="pId" value="<ano:write name="thresholdId"/>"/>
                <h3>Select producer for new Threshold.</h3>
                <div class="controls">
                    Name: <input type="text" value="<ano:write name="definition" property="name"/>" name="name" size="30">
                </div>
                <div class="controls">
                    Producer: <b><ano:write name="definition" property="producerName"/></b>.
                </div>
                <div class="controls">
                    Stat: <b><ano:write name="definition" property="statName"/></b>.
                </div>
                <div class="controls">
                    Value: <b><ano:write name="definition" property="valueName"/></b>.
                </div>
                <div class="controls">
                    Interval: <b><ano:write name="definition" property="intervalName"/></b>.
                </div>
                <div class="controls">
                    TimeUnit: <b><ano:write name="definition" property="timeUnit"/></b>.
                </div>
                <ano:equal name="target" value="Threshold">
                    <div class="clear"><!-- --></div>
                    <div class="controls">
                        <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_green.png"/>&nbsp; if &nbsp;<select name="greenDir" onchange="switchDirection();"><option>below</option><option>above</option></select>&nbsp;<input type="text" name="greenValue" onchange="switchgreenvalue();" value="<ano:write name="GREEN"/>"> (Green)</span><br/><br/>
                        <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_yellow.png"/>&nbsp; if &nbsp;<select name="yellowDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="yellowValue" value="<ano:write name="YELLOW"/>"> (Yellow)</span><br/><br/>
                        <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_orange.png"/>&nbsp; if &nbsp;<select name="orangeDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="orangeValue" value="<ano:write name="ORANGE"/>"> (Orange)</span><br/><br/>
                        <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_red.png"/>&nbsp; if &nbsp;<select name="redDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="redValue" value="<ano:write name="RED"/>"> (Red)</span><br/><br/>
                        <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_purple.png"/>&nbsp; if &nbsp;<select name="purpleDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="purpleValue" value="<ano:write name="PURPLE"/>"> (Purple)</span>
                    </div>
                </ano:equal>
                <div class="clear"><!-- --></div>
                <div class="controls">
                    <input type="button" value="Update" class="btn-blue" onclick="document.forms.UpdateForm.submit(); return false">
                </div>
            </form>
        </div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="bot"><div><!-- --></div></div>
	</div>
</div>
</body>
</html>


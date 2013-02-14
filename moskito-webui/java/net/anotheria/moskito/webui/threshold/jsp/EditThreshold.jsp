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
            if (document.forms.UpdateForm.pGreenDir.value=='above')
                targetValue = 'below';
            else
                targetValue = 'above';

            document.forms.UpdateForm.pYellowDir.value = targetValue;
            document.forms.UpdateForm.pOrangeDir.value = targetValue;
            document.forms.UpdateForm.pRedDir.value = targetValue;
            document.forms.UpdateForm.pPurpleDir.value = targetValue;
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
            <form name="UpdateForm" action="mskThresholdUpdate" method="GET">
                <input type="hidden" name="pId" value="<ano:write name="thresholdId"/>"/>
            <div>
                <span>Select producer for new Threshold.</span>                                                                     <br/><br/>
            </div>
            <div>
                <span>Name: <input type="text" value="<ano:write name="definition" property="name"/>" name="pName" size="30"></span><br/><br/>
            </div>
            <div>
                <span>Producer: <b><ano:write name="definition" property="producerName"/></b>.</span><br/><br/>
			</div>
            <div>
                <span>Stat: <b><ano:write name="definition" property="statName"/></b>.</span><br/><br/>
            </div>
            <div>
                <span>Value: <b><ano:write name="definition" property="valueName"/></b>.</span><br/><br/>
			</div>
            <div>
                <span>Interval: <b><ano:write name="definition" property="intervalName"/></b>.</span><br/><br/>
            </div>
            <div>
                <span>TimeUnit: <b><ano:write name="definition" property="timeUnit"/></b>.</span><br/><br/>
            </div>
                <ano:equal name="target" value="Threshold">
                <div>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_green.png"/>&nbsp; if &nbsp;<select name="pGreenDir" onchange="switchDirection();"><option>below</option><option>above</option></select>&nbsp;<input type="text" name="pGreenValue" onchange="switchgreenvalue();" value="<ano:write name="GREEN"/>"> (Green)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_yellow.png"/>&nbsp; if &nbsp;<select name="pYellowDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="pYellowValue" value="<ano:write name="YELLOW"/>"> (Yellow)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_orange.png"/>&nbsp; if &nbsp;<select name="pOrangeDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="pOrangeValue" value="<ano:write name="ORANGE"/>"> (Orange)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_red.png"/>&nbsp; if &nbsp;<select name="pRedDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="pRedValue" value="<ano:write name="RED"/>"> (Red)</span><br/><br/>
                    <span><img src="<ano:write name="mskPathToImages" scope="application"/>ind_purple.png"/>&nbsp; if &nbsp;<select name="pPurpleDir"><option>below</option><option selected="true">above</option></select>&nbsp;<input type="text" name="pPurpleValue" value="<ano:write name="PURPLE"/>"> (Purple)</span><br/><br/>
                </div>
            </ano:equal>
            </form>
            <div>
                <span><input type="button" value="Update" onclick="document.forms.UpdateForm.submit(); return false"></span>
            </div>
        </div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="bot"><div><!-- --></div></div>
	</div>
</div>
</body>
</html>


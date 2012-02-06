<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Recorded UseCase: <msk:write name="recordedUseCase" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
<msk:define id="IMG" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_l.gif" border="0" alt=""></msk:define
><msk:define id="EMPTY" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_s.gif" border="0" alt=""></msk:define
>
</head>
<body class="yui-skin-sam">
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="mskShowJourneys">Journeys</a></li>
		<li><a href="mskShowJourney?pJourneyName=<msk:write name="journeyName"/>"><msk:write name="journeyName"/></a></li>
		<li class="last"><span><msk:write name="tracedCall" property="name"/> </span></li>
	</ul>
	<div class="clear"><!-- --></div>

	<h1><msk:write name="tracedCall" property="name"/></h1>
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><msk:write name="tracedCall" property="created"/>&nbsp;&nbsp;<msk:write name="tracedCall" property="date"/> &nbsp;&nbsp;</span></div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top"><div><!-- --></div></div>
		<div class="in">			
			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
			
				
				<table cellpadding="0" cellspacing="0" width="100%">
				<thead>
						<tr class="stat_header">
							<th>Call</th>
							<th>Gross duration</th>
							<th>Net duration</th>
							<th>Aborted</th>
						</tr>
				</thead>
				<tbody>
						<msk:iterate name="tracedCall" property="elements" type="net.java.dev.moskito.webui.bean.TraceStepBean" id="traceStep" indexId="index">
						 <tr>
						   		<msk:equal name="traceStep" property="aborted" value="true"><tr class="stat_error"></msk:equal>
								<msk:notEqual name="traceStep" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>"></msk:notEqual>
								<td onmouseover="Tip('<msk:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 400)" onmouseout="UnTip()"><% for (int i=1; i<traceStep.getLayer(); i++){ %><%= EMPTY %><%}%><msk:equal name="traceStep" property="root" value="false"><%=IMG%></msk:equal><msk:write name="traceStep" property="call" filter="true"/></td>
								<td><msk:write name="traceStep" property="duration"/></td>
								<td><msk:write name="traceStep" property="timespent"/></td>
								<td><msk:equal name="traceStep" property="aborted" value="true">X</msk:equal></td>
						</tr>
						</msk:iterate>
				</tbody>
				</table>
				
		
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>

</div>
</body>
</html>
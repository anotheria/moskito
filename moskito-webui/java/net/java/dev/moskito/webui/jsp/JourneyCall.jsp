<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MoSKito Recorded Journey Call: <ano:write name="recordedUseCase" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
<ano:define id="IMG" type="java.lang.String"><img src="<ano:write name="mskPathToImages" scope="application"/>msk_l.gif" border="0" alt=""></ano:define
><ano:define id="EMPTY" type="java.lang.String"><img src="<ano:write name="mskPathToImages" scope="application"/>msk_s.gif" border="0" alt=""></ano:define
>
</head>
<body class="yui-skin-sam">
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="mskShowJourneys">Journeys</a></li>
		<li><a href="mskShowJourney?pJourneyName=<ano:write name="journeyName"/>"><ano:write name="journeyName"/></a></li>
		<li class="last"><span><ano:write name="tracedCall" property="name"/> </span></li>
	</ul>
	<div class="clear"><!-- --></div>

	<h1><ano:write name="tracedCall" property="name"/></h1>
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><ano:write name="tracedCall" property="created"/>&nbsp;&nbsp;<ano:write name="tracedCall" property="date"/> &nbsp;&nbsp;</span></div>
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
						<ano:iterate name="tracedCall" property="elements" type="net.java.dev.moskito.webui.bean.TraceStepBean" id="traceStep" indexId="index">
						 <tr>
						   		<ano:equal name="traceStep" property="aborted" value="true"><tr class="stat_error"></ano:equal>
								<ano:notEqual name="traceStep" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>"></ano:notEqual>
								<td onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 400)" onmouseout="UnTip()"><% for (int i=1; i<traceStep.getLayer(); i++){ %><%= EMPTY %><%}%><ano:equal name="traceStep" property="root" value="false"><%=IMG%></ano:equal><ano:write name="traceStep" property="call" filter="true"/></td>
								<td><ano:write name="traceStep" property="duration"/></td>
								<td><ano:write name="traceStep" property="timespent"/></td>
								<td><ano:equal name="traceStep" property="aborted" value="true">X</ano:equal></td>
						</tr>
						</ano:iterate>
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

		<!-- duplicates -->
		<ano:present name="dupStepBeansSize">
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
								<th>Duplicate (<ano:write name="dupStepBeansSize"/>)</th>
								<th>Positions</th>
							</tr>
					</thead>
					<tbody>
							<ano:iterate name="dupStepBeans" type="net.java.dev.moskito.webui.bean.JourneyCallDuplicateStepBean" id="dupStep" indexId="index">
							 <tr>
							 	<td><ano:write name="dupStep" property="call"/></td>
							 	<td><ano:write name="dupStep" property="positions"/></td>
							</tr>
							</ano:iterate>
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
	</ano:present>
</div>
</body>
</html>
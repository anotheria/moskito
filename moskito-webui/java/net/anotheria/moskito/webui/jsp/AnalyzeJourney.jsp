<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>MoSKito Analyze Journey</title>
<link rel="stylesheet" href="mskCSS"/>
<%--script type="text/javascript" src="../js/wz_tooltip.js"></script--%>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
<%--
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="mskShowJourneys">Journeys</a></li>
		<li class="last">Analyze <span><ano:write name="journeyName"/></span></li>
	</ul>
	<div class="clear"><!-- --></div>
--%>	
<%--		
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><msk:write name="journey"/></span></div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
--%>	

	<ano:iterate name="callsList" type="net.java.dev.moskito.webui.bean.AnalyzeProducerCallsMapBean" id="call">
	<ano:equal name="call" property="empty" value="false">
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	<h2 class="titel_open"><ano:write name="call" property="name"/></h2>
	
		<div class="clear"><!-- --></div>
		<div class="table_itseft">
			<div class="top">
				<div class="left"><!-- --></div>
				<div class="right"><!-- --></div>
			</div>
			<div class="in">			
	
		<table cellpadding="0" cellspacing="0" class="fll">
		<thead>
			<tr class="stat_header">
				<th>ProducerId</th>
			</tr>
		</thead>
		<tbody>		
			<ano:iterate name="call" property="producerCallsBeans" id="producerCallsBean" type="net.java.dev.moskito.webui.bean.AnalyzeProducerCallsBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td>
						<ano:write name="producerCallsBean" property="producerId"/>
					</td>				
				</tr>
			</ano:iterate>
			  <tr class="even">
					<td>
						<b>Total:</b>
					</td>				
				</tr>
		</tbody>			
	</table>
	<div class="table_right">
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>		  
				<th>Calls</th>
				<th>Duration</th>
			</tr>
		</thead>
		<tbody>
			<ano:iterate name="call" property="producerCallsBeans" id="producerCallsBean" type="net.java.dev.moskito.webui.bean.AnalyzeProducerCallsBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td>
						<ano:write name="producerCallsBean" property="numberOfCalls"/>
					</td>
					<td>
						<ano:write name="producerCallsBean" property="totalTimeSpentTransformed"/>
					</td>				
				</tr>
			</ano:iterate>
			  <tr class="even">
					<td>
						<ano:write name="call" property="totalCalls"/>
					</td>
					<td>
						<ano:write name="call" property="totalDurationTransformed"/>
					</td>				
				</tr>
		</tbody>
	</table>
    </div>

    <div class="clear"><!-- --></div>
	    </div>
	<div class="bot">
		<div class="left"><!-- --></div>
		<div class="right"><!-- --></div>
	</div>
   </div>
	   </div>
			<div class="bot"><div><!-- --></div></div>
		</div>	 
		 
		 

    <div class="clear"><!-- --></div>
    </ano:equal>
	</ano:iterate>

	
</div>	
</body>
</html>  
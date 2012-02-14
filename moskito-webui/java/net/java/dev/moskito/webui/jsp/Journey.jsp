<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>MoSKito Journey</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="mskShowJourneys">Journeys</a></li>
		<li class="last"><span><msk:write name="journey" property="name"/> </span></li>
	</ul>
	<div class="clear"><!-- --></div>
		
	<h1><span><msk:write name="journey" property="name"/></h1>
	
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><msk:write name="journey"/></span></div>
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
						<tr>
							<th>#</th>
							<th>Url</th>
							<th>Date</th>		
							<th>Steps</th>
						</tr>
					</thead>
					<tbody>
						
						<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.TracedCallListItemBean" id="tracedCall" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><msk:write name="index"/></td>
								 <td>
									<a href="mskShowJourneyCall?pJourneyName=<msk:write name="journey" property="name"/>&pPos=<msk:write name="index"/>">
										<msk:write name="tracedCall" property="name"/>
									</a>
								 </td>	
								 <td><msk:write name="tracedCall" property="date"/></td>
								 <td><msk:write name="tracedCall" property="containedSteps"/></td>
								 
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
</div>	
</body>
</html>  
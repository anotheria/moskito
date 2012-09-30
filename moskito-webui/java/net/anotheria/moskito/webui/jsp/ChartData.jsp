<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>ChartData</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="add_in">
			<h2>MoSKito instant chart data.</h2>

			<div><span>
TODO FIX: This page explains what the abreviations used on the producers overview page mean. However, it doesn't explain how to interpret
the data. Since the data is strongly use-case dependent, its mostly up to you to give it a correct interpretations, but 
for some use-cases <a href="http://moskito.anotheria.net/documentation.html">moskito's documentation and HOWTOs</a> are quite useful.
			</span></div>

		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>

		<div class="clear"><!-- --></div>
		<div class="table_layout">
			<div class="top">
				<div><!-- --></div>
			</div>
			<div class="in">

				<h2>Your request produced following data</h2>

				<div class="clear"><!-- --></div>
				<div class="table_itseft">
					<div class="top">
						<div class="left"><!-- --></div>
						<div class="right"><!-- --></div>
					</div>
					<div class="in">

						<table cellpadding="0" cellspacing="0" class="wrap_it"
							   id="<msk:write name="decorator" property="name"/>_table">
							<thead>
							<tr class="stat_header">
								<th>ProducerId</th>
								<th>StatName</th>
								<th>ValueName</th>
								<th>Value</th>
							</tr>
							</thead>
							<tbody>
							<msk:iterate name="data" id="entity"
										 type="net.java.dev.moskito.webui.bean.ChartDataEntityBean" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><msk:write name="entity" property="producerId"/></td>
									<td><msk:write name="entity" property="statName"/></td>
									<td><msk:write name="entity" property="statValueName"/></td>
									<td><msk:write name="entity" property="statValue"/></td>
								</tr>
							</msk:iterate>
							</tbody>
						</table>
					</div>
					<div class="bot">
						<div class="left"><!-- --></div>
						<div class="right"><!-- --></div>
					</div>
					<div class="clear"><!-- --></div>
				</div>
			</div>
			<div class="bot">
			<div><!-- --></div>

				<br><br><br>
				<h2>Your data in xml format (add pForward=xml to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartDataXML.jsp" flush="false"/>
				</textarea>
				
				<br><br><br>
				<h2>Your data in json format (add pForward=json to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartDataJSON.jsp" flush="false"/>
				</textarea>

				<br><br><br>
				<h2>Your data in csv format (add pForward=csv to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartDataCSV.jsp" flush="false"/>
				</textarea>
		</div>
		</div>



</div>
</body>
</html>
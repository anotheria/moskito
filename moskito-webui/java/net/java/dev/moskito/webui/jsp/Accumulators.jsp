<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Accumulators</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<ano:present name="dataBean">
<script type="text/javascript">
	var data = [<ano:iterate name="dataBean" property="values" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>["<ano:write name="value" property="timestamp"/>",<ano:write name="value" property="value"/>]</ano:iterate>]; 
</script>
</ano:present>

<div class="main">
	<div class="clear"><!-- --></div>

	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Accumulators</span></h2>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div class="scroller_x">
					<table cellpadding="0" cellspacing="0" width="100%">
						<thead>
						<tr>
							<th>Name</th>
							<th>Path</th>
							<th>Values</th>
							<th>Last Timestamp</th>
						</tr>
						</thead>
						<tbody>
						<ano:iterate name="accumulators" type="net.java.dev.moskito.webui.bean.AccumulatorInfoBean" id="accumulator" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
							<%--
								<td><a href="#" onclick="openOverlay(info<ano:write name="threshold" property="id"/>); return false"><ano:write name="threshold" property="name"/></a></td>
								<td><img src="<ano:write name="mskPathToImages" scope="application"/>ind_<ano:write name="threshold" property="colorCode"/>.<ano:equal name="threshold" property="colorCode" value="purple">gif</ano:equal><ano:notEqual name="threshold" property="colorCode" value="purple">png</ano:notEqual>" alt="<ano:write name="threshold" property="status"/>"/></td>
							--%>
								<td><a href="?id=<ano:write name="accumulator" property="id"/>"><ano:write name="accumulator" property="name"/></a></td>
								<td><ano:write name="accumulator" property="path"/></td>
								<td><ano:write name="accumulator" property="numberOfValues"/></td>
								<td><ano:write name="accumulator" property="lastValueTimestamp"/></td>
							</tr>
						</ano:iterate>
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
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	<div class="clear"><!-- --></div>

	<!-- chart section -->
	<ano:present name="dataBean">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Chart for <ano:write name="dataBean" property="description"/></span></h2><a class="refresh" href="#"></a>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div id="chart_accum"></div>
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	</ano:present>

	<div class="clear"><!-- --></div>
	
	<!-- data section -->
	<ano:present name="dataBean">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Data for <ano:write name="dataBean" property="description"/></span></h2>

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
							<th>Time</th>
							<th>Value</th>
						</tr>
						</thead>
						<tbody>
						<ano:iterate name="dataBean" type="net.java.dev.moskito.webui.bean.AccumulatedValueBean" id="value" property="values" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><ano:write name="value" property="timestamp"/></td>
								<td><ano:write name="value" property="value"/></td>
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
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
<script type="text/javascript">
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawLineChart);
	function drawLineChart() {
		var data2 = new google.visualization.DataTable();
		data2.addColumn('string', 'Time');
		data2.addColumn('number', '<ano:write name="dataBean" property="shortDescription"/>');
		data2.addRows(data);
		var options = {width: 1000, height: 300, title: ''};
		var chartInfo = {
			params: '',
			container: 'chart_accum',
			type: 'LineChart',
			data: data2,
			options: options 
		};
		drawChart(chartInfo);		
	}
	function drawChart(chartInfo) {
		document.getElementById(chartInfo.container).chartInfo = chartInfo;

		google.visualization.drawChart({
			"containerId": chartInfo.container,
			dataTable: chartInfo.data/*+chartInfo.params*/,
			"chartType": chartInfo.type,
			"options": chartInfo.options,
			"refreshInterval": 60
		});

	}

	$('.refresh').click(function() {
		location.reload(true);
	})


</script>
</ano:present>
</body>
</html>  
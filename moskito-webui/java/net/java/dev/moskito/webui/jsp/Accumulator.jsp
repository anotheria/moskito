<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Accumulator</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<script type="text/javascript">
	var singleGraphData<ano:write name="accumulatorData" property="nameForJS"/> = [<ano:iterate name="accumulatorData" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>]; ;
</script>

<div class="main">
	<div class="clear"><!-- --></div>
	<!-- chart section -->
	<ano:present name="accumulatorData">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Chart for <ano:write name="accumulatorData" property="name"/></span></h2><a class="refresh" href="#"></a>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div id="chart_accum<ano:write name="accumulatorData" property="nameForJS"/>"></div>
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
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Data for <ano:write name="accumulatorData" property="name"/></span></h2>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div class="scroller_x">
					<form action="" method="GET">
						<table cellpadding="0" cellspacing="0" width="100%">
							<thead>
							<tr>
								<th>Timestamp</th>
								<th>Value</th>
							</tr>
							</thead>
							<tbody>
								<ano:iterate name="accumulatorData" property="data" id="row">
									<tr>
										<td><ano:write name="row" property="timestamp"/></td>
										<td><ano:write name="row" property="firstValue"/></td>
									</tr>
								</ano:iterate>
							</tbody>
						</table>
					</form>						
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

	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>JSON for <ano:write name="accumulatorData" property="name"/></span></h2>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div class="scroller_x">
						<pre>
							{
								"accumulatorInfo" : {
									"id": "<ano:write name="accumulatorInfo" property="id"/>",
									"name": "<ano:write name="accumulatorInfo" property="name"/>",
									"path": "<ano:write name="accumulatorInfo" property="path"/>",
									"numberOfValues": "<ano:write name="accumulatorInfo" property="numberOfValues"/>",
									"lastValueTimestamp": "<ano:write name="accumulatorInfo" property="lastValueTimestamp"/>" 
								},
								"accumulatorData" : [
								<ano:iterate name="accumulatorData" property="data" id="row" indexId="ind"
									><ano:notEqual name="ind" value="0">,</ano:notEqual>
									{"timestamp": "<ano:write name="row" property="isoTimestamp"/>", "value": "<ano:write name="row" property="firstValue"/>"}</ano:iterate>
								]
							}
						</pre>
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
	<jsp:include page="Footer.jsp" flush="false" />


<script type="text/javascript">
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawLineChart);
	//combined chart
	function drawLineChart() {
			var chartData<ano:write name="accumulatorData" property="nameForJS"/> = new google.visualization.DataTable();
			chartData<ano:write name="accumulatorData" property="nameForJS"/>.addColumn('string', 'Time');
			chartData<ano:write name="accumulatorData" property="nameForJS"/>.addColumn('number', '<ano:write name="accumulatorData" property="name"/>');
			chartData<ano:write name="accumulatorData" property="nameForJS"/>.addRows(singleGraphData<ano:write name="accumulatorData" property="nameForJS"/>);
			var options = {width: 1000, height: 300, title: '<ano:write name="accumulatorData" property="name"/>'};
			var chartInfo = {
				params: '',
				container: 'chart_accum<ano:write name="accumulatorData" property="nameForJS"/>',
				type: 'LineChart',
				data: chartData<ano:write name="accumulatorData" property="nameForJS"/>,
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
</body>
</html>  
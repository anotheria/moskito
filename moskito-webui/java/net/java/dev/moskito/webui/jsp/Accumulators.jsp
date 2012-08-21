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

<ano:present name="data">
<script type="text/javascript">
	var data = [<ano:iterate name="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>]; 
</script>
</ano:present>
<ano:present name="singleGraphData">
<script type="text/javascript">
	<ano:iterate name="singleGraphData" type="net.java.dev.moskito.webui.bean.AccumulatedSingleGraphDataBean" id="singleGraph">
		var singleGraphData<ano:write name="singleGraph" property="nameForJS"/> = [<ano:iterate name="singleGraph" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>]; ;
	</ano:iterate>
</script>
</ano:present>

<div class="main">
	<div class="clear"><!-- --></div>
	<!-- chart section -->
	<ano:present name="data">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<ano:notPresent name="multiple_set">
		<div class="in">
			<h2><span>Chart for <ano:iterate name="accNames" type="java.lang.String" id="name"><ano:write name="name"/> </ano:iterate></span></h2><a class="refresh" href="#"></a>

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
		</ano:notPresent>
		<ano:present name="multiple_set">
		<ano:iterate name="singleGraphData" type="net.java.dev.moskito.webui.bean.AccumulatedSingleGraphDataBean" id="singleGraph">
		<div class="in">
			<h2><span>Chart for <ano:write name="singleGraph" property="name"/></span></h2><a class="refresh" href="#"></a>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div id="chart_accum<ano:write name="singleGraph" property="nameForJS"/>"></div>
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		</ano:iterate>
		</ano:present>
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
			<h2><span>Accumulators</span></h2>

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
								<th></th>
								<th>Name</th>
								<th>Path</th>
								<th>Values</th>
								<th>Last Timestamp</th>
								<th>&nbsp;</th>
                                <th>&nbsp;</th>
                            </tr>
							</thead>
							<tbody>
							<ano:iterate name="accumulators" type="net.java.dev.moskito.webui.bean.AccumulatorInfoBean" id="accumulator" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><input type="checkbox" name="id_<ano:write name="accumulator" property="id"/>" value="set" <ano:present name="<%=\"id_\"+accumulator.getId()+\"_set\"%>">checked="checked"</ano:present>/></td>
									<td><a href="?id_<ano:write name="accumulator" property="id"/>=set"><ano:write name="accumulator" property="name"/></a></td>
									<td><ano:write name="accumulator" property="path"/></td>
									<td><ano:write name="accumulator" property="numberOfValues"/></td>
									<td><ano:write name="accumulator" property="lastValueTimestamp"/></td>
									<td><a href="mskAccumulator?pId=<ano:write name="accumulator" property="id"/>" class="zoom"></a></td>
                                    <td><a href="mskAccumulatorDelete?pId=<ano:write name="accumulator" property="id"/>">D</a></td>
                                </tr>
							</ano:iterate>
								<tr>
									<td colspan="5">
										<input type="submit" value="Submit"/>
										&nbsp;(Mode:&nbsp;
											<input type="radio" name="mode" value="combined" <ano:present name="combined_set">checked="checked"</ano:present>/>&nbsp;combine
											<input type="radio" name="mode" value="normalized" <ano:present name="normalized_set">checked="checked"</ano:present>/>&nbsp;combine and normalize
											<input type="radio" name="mode" value="multiple" <ano:present name="multiple_set">checked="checked"</ano:present>/>&nbsp;multiple graphs
										)
										&nbsp;(Type:&nbsp;
											<input type="radio" name="type" value="LineChart" <ano:equal name="type" value="LineChart">checked="checked"</ano:equal	>/>&nbsp;Line
											<input type="radio" name="type" value="PieChart" <ano:equal name="type" value="PieChart">checked="checked"</ano:equal>/>&nbsp;Pie
											<input type="radio" name="type" value="BarChart" <ano:equal name="type" value="BarChart">checked="checked"</ano:equal>/>&nbsp;Bar
											<input type="radio" name="type" value="ColumnChart" <ano:equal name="type" value="ColumnChart">checked="checked"</ano:equal>/>&nbsp;Column
										)										
										<input type="hidden" name="normalizeBase" value="<ano:write name="normalizeBase"/>"/>
										<input type="hidden" name="maxValues" value="<ano:write name="maxValues"/>"/>
									</td>
								</tr>
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

	<jsp:include page="Footer.jsp" flush="false" />


<ano:present name="data">
<script type="text/javascript">
	google.load("visualization", "1", {packages:["corechart"]});
	google.setOnLoadCallback(drawLineChart);
	//combined chart
	<ano:notPresent name="singleGraphData">
	function drawLineChart() {
		var data2 = new google.visualization.DataTable();
		data2.addColumn('string', 'Time');
		<ano:iterate name="accNames" type="java.lang.String" id="name">
		data2.addColumn('number', '<ano:write name="name"/>');
		</ano:iterate>
		data2.addRows(data);
		var options = {width: 1000, height: 300, title: ''};
		var chartInfo = {
			params: '',
			container: 'chart_accum',
			type: '<ano:write name="type"/>',
			data: data2,
			options: options 
		};
		drawChart(chartInfo);		
	}
	</ano:notPresent>
	<ano:present name="singleGraphData">
	function drawLineChart() {
		<ano:iterate name="singleGraphData" type="net.java.dev.moskito.webui.bean.AccumulatedSingleGraphDataBean" id="singleGraph">
			var chartData<ano:write name="singleGraph" property="nameForJS"/> = new google.visualization.DataTable();
			chartData<ano:write name="singleGraph" property="nameForJS"/>.addColumn('string', 'Time');
			chartData<ano:write name="singleGraph" property="nameForJS"/>.addColumn('number', '<ano:write name="singleGraph" property="name"/>');
			chartData<ano:write name="singleGraph" property="nameForJS"/>.addRows(singleGraphData<ano:write name="singleGraph" property="nameForJS"/>);
			var options = {width: 1000, height: 300, title: '<ano:write name="singleGraph" property="name"/>'};
			var chartInfo = {
				params: '',
				container: 'chart_accum<ano:write name="singleGraph" property="nameForJS"/>',
				type: '<ano:write name="type"/>',
				data: chartData<ano:write name="singleGraph" property="nameForJS"/>,
				options: options 
			};
			drawChart(chartInfo);		
		</ano:iterate>
	}
	</ano:present>
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
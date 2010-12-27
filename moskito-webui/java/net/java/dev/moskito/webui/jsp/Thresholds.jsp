<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Thresholds</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<div class="clear"><!-- --></div>
	<%--<div class="additional">--%>
	<%--<div class="top"><div><!-- --></div></div>--%>
	<%--<div class="add_in">--%>
	<%--<div><span>System state</span></div>--%>
	<%--</div>--%>
	<%--<div class="bot"><div><!-- --></div></div>--%>
	<%--</div>--%>

	<!-- definition for overlays -->
		<script>
		<msk:iterate name="infos" type="net.java.dev.moskito.webui.bean.ThresholdInfoBean" id="info">
			var info<msk:write name="info" property="id"/> = {
				"name": "<msk:write name="info" property="name"/>",
				"producerName": "<msk:write name="info" property="producerName"/>",
				"statName": "<msk:write name="info" property="statName"/>",
				"valueName": "<msk:write name="info" property="valueName"/>",
				"intervalName": "<msk:write name="info" property="intervalName"/>",
				"descriptionString": "<msk:write name="info" property="descriptionString"/>",
				"guards": [<msk:iterate name="info" property="guards" id="guard" type="java.lang.String">"<msk:write name="guard"/>",</msk:iterate>],
			};
		</msk:iterate>
		</script>
		<script>
			function openOverlay(selectedInfo){
				alert("open overlay for "+selectedInfo.name);
			}
		</script>
	<!-- end of definition for overlays -->

	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>System state</span></h2>

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
							<th>Name</th>
							<th>Status</th>
							<th>Value</th>
							<th>Status Change</th>
							<th>Change Timestamp</th>
							<th>Path</th>
						</tr>
						</thead>
						<tbody>
						<%--
						<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><msk:write name="index"/></td>
								 <td>
									<a href="mskShowMonitoringSessionCall?pSessionName=<msk:write name="msession" property="name"/>&pPos=<msk:write name="index"/>">
										<msk:write name="useCase" property="name"/>
									</a>
								 </td>	
								 <td>	
									<msk:write name="useCase" property="date"/>
								  </td>
							</tr>
						</msk:iterate>--%>
						<msk:iterate name="thresholds" type="net.java.dev.moskito.webui.bean.ThresholdBean" id="threshold" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><a href="" onclick="openOverlay(info<msk:write name="threshold" property="id"/>); return false"><msk:write name="threshold" property="name"/></a></td>
								<td><img src="../img/ind_<msk:write name="threshold" property="colorCode"/>.png" alt="<msk:write name="threshold" property="status"/>"/></td>
								<td><msk:write name="threshold" property="value"/></td>
								<td><msk:write name="threshold" property="change"/></td>
								<td><msk:write name="threshold" property="timestamp"/></td>
								<td><msk:write name="threshold" property="description"/></td>
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
		<div class="bot">
			<div class="left"><!-- --></div>
			<div class="right"><!-- --></div>
		</div>
	</div>
	<%--<br><br><br>--%>
	<div class="clear"><!-- --></div>
	<%--<div class="additional">--%>
	<%--<div class="top"><div><!-- --></div></div>--%>
	<%--<div class="add_in">--%>
	<%--<div><span>History</span></div>--%>
	<%--</div>--%>
	<%--<div class="bot"><div><!-- --></div></div>--%>
	<%--</div>--%>

	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>History</span></h2>

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
							<th>Timestamp</th>
							<th>OldStatus</th>
							<th>OldStatus</th>
							<th>NewStatus</th>
							<th>ValueChange</th>
						</tr>
						</thead>
						<tbody>
						<%--
						<msk:iterate name="recorded" type="net.java.dev.moskito.webui.bean.RecordedUseCaseListItemBean" id="useCase" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><msk:write name="index"/></td>
								 <td>
									<a href="mskShowMonitoringSessionCall?pSessionName=<msk:write name="msession" property="name"/>&pPos=<msk:write name="index"/>">
										<msk:write name="useCase" property="name"/>
									</a>
								 </td>	
								 <td>	
									<msk:write name="useCase" property="date"/>
								  </td>
							</tr>
						</msk:iterate>--%>
						<tr class="even">
							<td>FreeMemory</td>
							<td><img src="../img/ind_yellow.png" alt="yellow"/></td>
							<td>350000</td>
							<td>2010-02-15T22:15:01</td>
							<td>HeapMemory.HeapMemory.Free/15m/MILLISECONDS</td>
						</tr>
						<tr class="odd">
							<td>UsedMemory</td>
							<td><img src="../img/ind_green.png" alt="green"/></td>
							<td>1000</td>
							<td>2010-02-15T22:15:01</td>
							<td>HeapMemory.HeapMemory.Used/5m/MILLISECONDS</td>
						</tr>

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
			<div class="left"><!-- --></div>
			<div class="right"><!-- --></div>
		</div>
	</div>
</body>
</html>  
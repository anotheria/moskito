<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito Threads <ano:write name="pageTitle" /></title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false" />
<div class="main">
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Threads stats</h2>

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
							<th>Stat</th>
							<th>Value</th>
							<th>Explanation</th>
 						</tr>
						</thead>
						<tbody>
							<tr class="even">
								<td>Thread count:</td>
								<td><ano:write name="info" property="threadCount"/></td>
								<td>The current number of live threads including both daemon and non-daemon threads.</td>
							</tr>
							<tr class="odd">
								<td>Daemon count:</td>
								<td><ano:write name="info" property="daemonThreadCount"/></td>
								<td>The current number of live daemon threads.</td>
							</tr>
							<tr class="even">
								<td>Peak:</td>
								<td><ano:write name="info" property="peakThreadCount"/></td>
								<td>The peak live thread count since the Java virtual machine started or peak was reset.</td>
							</tr>
							<tr class="odd">
								<td>Total started:</td>
								<td><ano:write name="info" property="totalStarted"/></td>
								<td>The total number of threads created and also started since the Java virtual machine started.</td>
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
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	<div class="clear"><!-- --></div>


	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Threads capabilities</h2>

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
							<th>Stat</th>
							<th>Value</th>
							<th>Explanation</th>
 						</tr>
						</thead>
						<tbody>
							<tr class="even">
								<td>CPU time supported:</td>
								<td><ano:write name="info" property="currentThreadCpuTimeSupported"/></td>
								<td>Does the Java virtual machine supports CPU time measurement for the current thread.</td>
							</tr>
							<tr class="odd">
								<td>Content monitoring supported:</td>
								<td><ano:write name="info" property="threadContentionMonitoringSupported"/></td>
								<td>Does the Java virtual machine supports thread contention monitoring.</td>
							</tr>
							<tr class="event">
								<td>Content monitoring enabled:</td>
								<td><ano:write name="info" property="threadContentionMonitoringEnabled"/></td>
								<td>Is thread contention monitoring enabled?</td>
							</tr>
							<tr class="odd">
								<td>CPU Time supported:</td>
								<td><ano:write name="info" property="threadCpuTimeSupported"/></td>
								<td>Does the Java virtual machine supports CPU time measurement for the current thread.</td>
							</tr>
							<tr class="even">
								<td>CPU Time enabled:</td>
								<td><ano:write name="info" property="threadCpuTimeEnabled"/></td>
								<td>Is thread CPU time measurement enabled?</td>
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
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	<div class="clear"><!-- --></div>
	<a href="http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/management/ThreadMXBean.html">See Javadoc for details</a>
<jsp:include page="Footer.jsp" flush="false" />
</div>	
</body>
</html>

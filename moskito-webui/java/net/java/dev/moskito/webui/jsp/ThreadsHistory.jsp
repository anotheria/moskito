<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito Threads History</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false" />
<div class="main">
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span>Thread History is <ano:equal name="active" value="true"><b>ON</b> (<a href="mskThreadsHistoryOff?">OFF</a>)</ano:equal><ano:equal name="active" value="false"><b>OFF</b> (<a href="mskThreadsHistoryOn?">ON</a>)</ano:equal></span></div>
			<div><span>Thread History size is <ano:write name="listsize"/>.</span></div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	
	<ano:equal name="active" value="true">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Threads</span></h2>

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
							<th>Time</th>
							<th>Operation</th>
							<th>Id</th>
							<th>Name</th>
 						</tr>
						</thead>
						<tbody>
						<ano:iterate name="history" type="net.java.dev.moskito.util.threadhistory.ThreadHistoryEvent" id="item" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><ano:write name="item" property="niceTimestamp"/></td>
								<td><ano:write name="item" property="operation"/></td>
								<td><ano:write name="item" property="threadId"/></td>
								<td><ano:write name="item" property="threadName"/></td>
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
	</ano:equal>
<jsp:include page="Footer.jsp" flush="false" />
</div>	
</body>
</html>


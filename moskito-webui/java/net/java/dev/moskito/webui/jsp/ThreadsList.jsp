<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito Threads List</title>
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
			<h2><span>Threads (<ano:write name="infosCount"/>)</span></h2>

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
							<th>ID</th>
							<th>Name</th>
							<th>State</th>
							<th>inNative</th>
							<th>suspened</th>
							<th>Lock name</th>
							<th>Lock owner id</th>
							<th>Lock owner name</th>
							<th>Blocked count</th>
							<th>Blocked time</th>
							<th>Waited count</th>
							<th>Waited time</th>
 						</tr>
						</thead>
						<tbody>

						<ano:iterate name="infos" type="java.lang.management.ThreadInfo" id="info" indexId="index">
							<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
								<td><ano:write name="info" property="threadId"/></td>
								<td><ano:write name="info" property="threadName"/></td>
								<td><ano:write name="info" property="threadState"/></td>
								<td align="center"><ano:equal name="info" property="inNative" value="true">X</ano:equal></td>
								<td align="center"><ano:equal name="info" property="suspended" value="true">X</ano:equal></td>
								<td><ano:write name="info" property="lockName"/></td>
								<td><ano:write name="info" property="lockOwnerId"/></td>
								<td><ano:write name="info" property="lockOwnerName"/></td>
								<td><ano:write name="info" property="blockedCount"/></td>
								<td><ano:write name="info" property="blockedTime"/></td>
								<td><ano:write name="info" property="waitedCount"/></td>
								<td><ano:write name="info" property="waitedTime"/></td>
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
<jsp:include page="Footer.jsp" flush="false" />
</div>	
</body>
</html>


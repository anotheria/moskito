<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>MoSKito &mdash; WebControl : <ano-tags:write name="view" property="viewName"/></title>
	<!-- link type="text/css" rel="stylesheet" rev="stylesheet" href="styles/common.css"/ -->
	<link rel="stylesheet" href="mwcCSS"/>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.4.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath()%>/js/function.js"></script>
</head>
<body>
<div class="main">
	<div class="header">
		<img src="<%= request.getContextPath()%>/images/logo.gif" alt="WebControl logo"/>
		<a href="#" class="settings">Settings</a>
	</div>
	<ul class="interval">
		<li>Interval:</li>
		<li class="active"><a href="#">1m</a></li>
		<li><a href="#">5m</a></li>
		<li><a href="#">15m</a></li>
		<li><a href="#">1h</a></li>
		<li class="last"><a href="#">1d</a></li>
	</ul>
	<div class="clear"></div>
	<div class="area">
		<div class="top">
			<div></div>
		</div>
		<div class="in">
			<div class="in_r">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="main_table">
					<tr>
						<td class="views_td">
							<ul class="views">
								<li class="active"><a href="#">Member</a></li>
								<li><a href="#">Guest</a></li>
								<li><a href="#">View name 3</a></li>
								<li><a href="#">View name 4</a></li>
								<li><a href="#">View name 5</a></li>
							</ul>
						</td>
						<td>
							<div class="inner">
								<div class="top">
									<div></div>
								</div>
								<div class="in">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<thead>
										<tr>
											<ano-tags:iterate name="view" property="rowNames" id="rowName">
												<th><a href="#"><ano-tags:write name="rowName"/></a></th>
											</ano-tags:iterate>
										</tr>
										</thead>
										<tbody>
											<ano-tags:iterate name="view" property="values" id="attrBean">
												<tr ${!attrBean.available ? 'class="stat_error" title="server has not provided a snapshot"' : ''}>
													<td><ano-tags:write name="attrBean" property="sourceName"/></td>
													<ano-tags:define name="attrBean" property="attributeValues" id="attributeValues"/>
													<ano-tags:iterate name="attributeValues" id="attrValue">
														<td><ano-tags:write name="attrValue"/></td>
													</ano-tags:iterate>
												</tr>
											</ano-tags:iterate>
										</tbody>
									</table>
									<h2>Member Totals</h2>
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th><a href="#">Sessions</a></th>
												<th><a href="#">Runtime Free</a></th>
												<th><a href="#">Heap Free</a></th>
												<th><a href="#">Heap Used</a></th>
												<th><a href="#">Usage, %</a></th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>55672</td>
												<td>100</td>
												<td>90</td>
												<td>560</td>
												<td>86</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="bot">
									<div></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="bot">
			<div></div>
		</div>
	</div>
</div>
</body>
</html>
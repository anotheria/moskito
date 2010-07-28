<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Refresh" content="30" />
		<title>MoSKito &mdash; WebControl : <ano-tags:write name="view" property="viewName"/></title>
		<!-- link type="text/css" rel="stylesheet" rev="stylesheet" href="styles/common.css"/ -->
		<link rel="stylesheet" href="mwcCSS"/>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/function.js"></script>
	</head>
<body>
<div class="main">
	<div class="header">
		<img src="<%= request.getContextPath() %>/images/logo.gif" alt="WebControl logo"/>
		<a href="viewConfig" class="settings">Settings</a>
	</div>
	<ul class="interval">
		<li>Interval:</li>
		<ano-tags:iterate id="intrvl" name="intervalNames" indexId="indx">
			<li ${intrvl eq param['pInterval'] ? 'class="active"' : ''}><a href="?pInterval=${intrvl}&pViewName=${view.viewName}">${intrvl}</a></li>
		</ano-tags:iterate>
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
								<ano-tags:iterate id="viewName" name="viewnames">
									<li ${viewName eq view.viewName ? 'class="active"' : ''}><a href="?pViewName=${viewName}">${viewName}</a></li>
								</ano-tags:iterate>
							</ul>
						</td>
						<td>
							<div class="inner">
								<div class="top">
									<div></div>
								</div>
								<div class="in">
									<div class="scroll">
									<table cellpadding="0" cellspacing="0" border="0" width="100%">
										<thead>
										<tr>
											<ano-tags:iterate name="view" property="rowNames" id="rowName">
												<ano-tags:equal value="${sortBy}" name="rowName" property="key">
													<th><a href="?pInterval=${interval}&pViewName=${view.viewName}&pFilterSortBy=${rowName.key}&pFilterSortOrder=${'desc' eq param['pFilterSortOrder'] ? 'asc' : 'desc'}">${rowName.name}</a></th>
												</ano-tags:equal>
												<ano-tags:notEqual value="${sortBy}" name="rowName" property="key">
													<th><a href="?pInterval=${interval}&pViewName=${view.viewName}&pFilterSortBy=${rowName.key}&pFilterSortOrder=asc">${rowName.name}</a></th>
												</ano-tags:notEqual>
											</ano-tags:iterate>
										</tr>
										</thead>
										<tbody>
											<ano-tags:iterate name="view" property="values" id="attrBean">
												<ano-tags:notEqual value="Totals" name="attrBean" property="sourceName">
												<tr ${!attrBean.available ? 'class="stat_error" title="server has not provided a snapshot"' : ''}>
													<!-- <td><ano-tags:write name="attrBean" property="sourceName"/></td> -->
													<ano-tags:define name="attrBean" property="attributeValues" id="attributeValues"/>
													<ano-tags:iterate name="attributeValues" id="attrValue">
														<td align="right" style="color: ${attrValue.color};">${attrValue.value}</td>
													</ano-tags:iterate>
												</tr>
												</ano-tags:notEqual>
											</ano-tags:iterate>
										</tbody>
									</table>
									<h2><ano-tags:write name="view" property="viewName"/> Totals</h2>
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<ano-tags:iterate name="view" property="rowNames" id="rowName">
													<th><a href="#">${rowName.name}</a></th>
												</ano-tags:iterate>
											</tr>
										</thead>
										<tbody>
											<ano-tags:iterate name="view" property="values" id="attrBean">
												<ano-tags:equal value="Totals" name="attrBean" property="sourceName">
												<tr ${!attrBean.available ? 'class="stat_error" title="server has not provided a snapshot"' : ''}>
													<!-- <td><ano-tags:write name="attrBean" property="sourceName"/></td> -->
													<ano-tags:define name="attrBean" property="attributeValues" id="attributeValues"/>
													<ano-tags:iterate name="attributeValues" id="attrValue">
														<td align="right" style="color: ${attrValue.color};">${attrValue.value}</td>
													</ano-tags:iterate>
												</tr>
												</ano-tags:equal>
											</ano-tags:iterate>
										</tbody>
									</table>
									</div>
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
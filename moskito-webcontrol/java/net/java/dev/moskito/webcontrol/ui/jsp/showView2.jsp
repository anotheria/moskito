<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<meta http-equiv="Refresh" content="30" />
		<title>MoSKito &mdash; WebControl : <ano-tags:write name="view" property="viewName"/></title>
		<link type="text/css" rel="stylesheet" rev="stylesheet" href="../v2/styles/common.css"/>
		<script type="text/javascript" src="../v2/js/jquery-1.4.min.js"></script>
		<script type="text/javascript" src="../v2/js/webControlController.js"></script>
		<script type="text/javascript" src="../v2/js/jquery.ui.core.js"></script>
	
		<script type="text/javascript" src="../v2/js/jquery-ui-1.8.5.custom.min.js"></script>
		<script type="text/javascript" src="../v2/js/jquery.ui.mouse.min.js"></script>
		<script type="text/javascript" src="../v2/js/jquery.ui.selectable.min.js"></script>
		<script type="text/javascript" src="../v2/js/jquery.ui.sortable.min.js"></script>
		<script type="text/javascript" src="../v2/js/jquery.ui.widget.min.js"></script>
		<script type="text/javascript" src="../v2/js/dragtable.js"></script>
		
	</head>
<body>
<div class="main">
	<div class="header">
		<img src="<%= request.getContextPath() %>/images/logo.gif" alt="WebControl logo"/>
		<a href="viewConfig2" class="settings">Settings</a>
	</div>
	<ul class="interval">
		<li>Interval:</li>
		<ano-tags:iterate id="intrvl" name="intervalNames" indexId="indx">
			<li ${intrvl eq interval ? 'class="active"' : ''}><a href="?pInterval=${intrvl}&pViewName=${view.viewName}">${intrvl}</a></li>
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
						<div class="hide"><a href="#" title="Hide tabs"></a></div>
							<ul class="views">
								<ano-tags:iterate id="vName" name="viewTable">
									<li ${vName.viewName eq view.viewName ? 'class="active"' : ''}><a href="?pViewName=${vName.viewName}" class="ind"><img src="../v2/images/ind_${vName.color}.png" alt="${vName.color}"/></a><a href="?pViewName=${vName.viewName}">${vName.viewName}</a></li>
								</ano-tags:iterate>
							</ul>
						</td>
						<td>
							<div class="inner">
								<div class="top">
									<div>&nbsp;</div>
								</div>
								<div class="in">
									<ano-tags:notEqual value="GREEN" name="view" property="color">
									<h2>Issues</h2>
									<table cellpadding="0" cellspacing="0" border="0">
										<thead>
											<tr>
												<th><a href="#">Server</a></th>
												<th><a href="#">Stat</a></th>
												<th><a href="#">Value</a></th>
											</tr>
										</thead>
										<tbody>
											<ano-tags:iterate name="view" property="values" id="orderedSourceAttributesBean">
											<ano-tags:define name="view" property="rowNames" id="rowName"/>
												<ano-tags:notEqual value="Totals" name="orderedSourceAttributesBean" property="sourceName">
													<ano-tags:define name="orderedSourceAttributesBean" property="attributeValues" id="attributeBeans"/>
													
													<ano-tags:iterate name="attributeBeans" id="attrBean" indexId="attrIndex">
														<ano-tags:notEqual value="green" name="attrBean" property="color">
														<ano-tags:notEqual value="black" name="attrBean" property="color">
														<tr>
															<td>${orderedSourceAttributesBean.sourceName}</td>
															<ano-tags:iterate name="rowName" id="row" indexId="rowIndex">
																<ano-tags:equal name="rowIndex" value="${attrIndex}"><td>${row.name}</td></ano-tags:equal>
															</ano-tags:iterate>
															<td>${attrBean.value}</td>
														</tr>
														</ano-tags:notEqual>
														</ano-tags:notEqual>
													</ano-tags:iterate>
												</ano-tags:notEqual>
											</ano-tags:iterate>
										</tbody>
									</table>
									</ano-tags:notEqual>
									<h2><ano-tags:write name="view" property="viewName"/></h2>
									<div class="scroll">
									<table cellpadding="0" cellspacing="0" border="0" width="100%" class="draggable">
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
														<td class="${attrValue.color}">${attrValue.value}</td>
													</ano-tags:iterate>
												</tr>
												</ano-tags:notEqual>
											</ano-tags:iterate>
										</tbody>
									</table>
									</div>
									<h2><ano-tags:write name="view" property="viewName"/> Totals</h2>
									<div class="scroll">
									<table width="100%" cellpadding="0" cellspacing="0" border="0" class="draggable">
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
														<td>${attrValue.value}</td>
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
			<div>&nbsp;</div>
		</div>
	</div>
</div>
</body>
</html>
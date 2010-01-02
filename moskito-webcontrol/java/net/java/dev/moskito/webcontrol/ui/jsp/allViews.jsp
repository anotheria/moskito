<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags"
%>
<html>
<head>
	<title>Moskito WebControl : all views</title>
	<link rel="stylesheet" href="mwcCSS"/>
</head>
<body>

<ano-tags:iterate name="views" id="view">

<h3>
	<a href="mwcShowView?pViewName=<ano-tags:write name="view" property="viewName"/>"><ano-tags:write name="view" property="viewName"/></a>
</h3>
<table cellspacing="0" cellpadding="4" border="0">
	<tr class="stat_header">
		<ano-tags:iterate name="view" property="rowNames" id="rowName">
			<th><ano-tags:write name="rowName"/></th>
		</ano-tags:iterate>
	</tr>
	<ano-tags:iterate name="view" property="values" id="attrBean">
		<tr ${!attrBean.available ? 'class="stat_error" title="server has not provided a snapshot"' : ''}>
			<td><ano-tags:write name="attrBean" property="sourceName"/></td>
				<ano-tags:define name="attrBean" property="attributeValues" id="attributeValues"/>
				<ano-tags:iterate name="attributeValues" id="attrValue">
					<td><ano-tags:write name="attrValue"/></td>
				</ano-tags:iterate>
		</tr>
	</ano-tags:iterate>
</table>

</ano-tags:iterate>
</body>
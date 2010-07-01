<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags"
%>
<html>
<head>
	<title>Moskito WebControl : <ano-tags:write name="view" property="viewName"/></title>
	<link rel="stylesheet" href="mwcCSS"/>
</head>
<body>

available views :
	<a href="mwcShowAll">show all views</a> 
<ano-tags:iterate name="viewnames" id="viewName">
	<a href="mwcShowView?pViewName=<ano-tags:write name="viewName"/>"><ano-tags:write name="viewName"/></a>
</ano-tags:iterate>

<h3>current view : <ano-tags:write name="view" property="viewName"/></h3>
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

</body>
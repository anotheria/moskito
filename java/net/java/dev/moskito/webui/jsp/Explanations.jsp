<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito stats explained</title>
<link rel="stylesheet" href="mskCSS?">
</head>
<body style="margin: 5px; padding: 10px">
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Explanations for moskito stats page</h3>
This page explains what the abreviations used on the producers overview page mean. However, it doesn't explain how to interpret
the data. Since the data is strongly use-case dependent, its mostly up to you to give it a correct interpretations, but 
for some use-cases <a href="http://moskito.anotheria.net/documentation.html">moskito's documentation and HOWTOs</a> are quite useful.
<hr size="1">
<logic:iterate name="decorators" type="net.java.dev.moskito.webui.bean.DecoratorExplanationBean" id="decorator">
<p>
	<h3><a name="<msk:write name="decorator" property="name"/>"><msk:write name="decorator" property="name"/></a></h3>
	<table width="100%" cellpadding="4" cellspacing="0" border="0" >
		<tr class="stat_header">
			<td><b>Abbreviation</b></td>
			<td><b>Meaning</b></td>
			<td><b>Expanation</b></td>
		</tr>
		<logic:iterate name="decorator" property="captions" id="caption" type="net.java.dev.moskito.webui.bean.StatCaptionBean" indexId="index">
			<tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>">
				<td><msk:write name="caption" property="caption"/></td>
				<td><msk:write name="caption" property="shortExplanation"/></td>
				<td><msk:write name="caption" property="explanation"/></td>
			</tr>
		</logic:iterate>
	</table> 
	<br/>	
	<br/>
</p>
</logic:iterate>


</body>
</html>
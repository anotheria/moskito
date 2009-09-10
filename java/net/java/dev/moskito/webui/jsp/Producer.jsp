<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ taglib uri="/tags/moskito" prefix="msk" 
%>
<html>
<head>
	<title>Moskito Producer <msk:write name="producer" property="id"/> </title>
	<link rel="stylesheet" href="mskCSS">
<% 
	String ASC = "<img src=\"/static/msk/img/msk_u.gif\" border=\"0\" alt=\"Sort ascending\">";
	String DESC = "<img src=\"/static/msk/img/msk_d.gif\" border=\"0\" alt=\"Sort descending\">";
%>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Producer</h3>
<i>This page as <a href="<msk:write name="linkToCurrentPageAsXml"/>&pForward=xml">XML</a>&nbsp;<a href="<msk:write name="linkToCurrentPageAsCsv"/>&pForward=csv">CSV</a></i><br/><br/>
producer:&nbsp;<msk:write name="producer" property="id"/>
<logic:present name="inspectableFlag">
	&nbsp;<a href="mskInspectProducer?pProducerId=<msk:write name="producer" property="id"/>">inspect</a>
</logic:present> 
<br/>
<a href="mskShowProducersByCategory">category</a>:&nbsp;<a href="mskShowProducersByCategory?pCategory=<msk:write name="producer" property="category"/>"><msk:write name="producer" property="category"/></a><br/>
<a href="mskShowProducersBySubsystem">subsystem</a>:&nbsp;<a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="producer" property="subsystem"/>"><msk:write name="producer" property="subsystem"/></a><br/>
class:&nbsp;<msk:write name="producer" property="className"/><br/>
<br/>
<logic:present name="intervals" scope="request">
	<jsp:include page="IntervalSelection.jsp" flush="false"/>
</logic:present>
<logic:present name="units" scope="request">
	<jsp:include page="UnitSelection.jsp" flush="false"/>
</logic:present>


<br/><br/>
<logic:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<h4><msk:write name="decorator" property="name"/></h4>
	<table cellpadding="4" cellspacing="0" border="0">
		<tr class="stat_header">
			<td>Name<%--
			--%>&nbsp;<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
			--%><a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
			--%></td>
			<logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
				<td><msk:write name="caption" property="caption"/><%--
					--%>&nbsp;<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
					--%><a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
				--%></td>
			</logic:iterate>
		</tr>
		<logic:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			<tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>">
				<td><msk:write name="stat" property="name"/></td>
				<logic:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
					<td title="<msk:write name="stat" property="name"/>.<msk:write name="value" property="name"/> = <msk:write name="value" property="value"/>"><msk:write name="value" property="value"/></td>
				</logic:iterate>
			</tr>
		</logic:iterate>
	</table>
</logic:iterate>
<br/><br/>
<small>Generated at <msk:write name="timestampAsDate"/>, timestamp: <msk:write name="timestamp"/></small>

</body>
</html>


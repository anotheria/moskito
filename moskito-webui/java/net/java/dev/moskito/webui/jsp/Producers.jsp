<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito Producers <bean:write name="pageTitle"/></title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<% 
	String ASC = "<img src=\"../img/up.gif\" border=\"0\" alt=\"Sort ascending\">";
	String DESC = "<img src=\"../img/down.gif\" border=\"0\" alt=\"Sort descending\">";
%>
<jsp:include page="Menu.jsp" flush="false"/>
<h3>Show Producers <bean:write name="pageTitle"/></h3>
<i>This page as&nbsp;<a href="<bean:write name="linkToCurrentPageAsXml"/>&pForward=xml">XML</a>&nbsp;<a href="<bean:write name="linkToCurrentPageAsCsv"/>&pForward=csv">CSV</a></i><br/>
<br/>
<logic:present name="intervals" scope="request">
	<jsp:include page="IntervalSelection.jsp" flush="false"/>
</logic:present>
<logic:present name="units" scope="request">
	<jsp:include page="UnitSelection.jsp" flush="false"/>
</logic:present>
<logic:present name="categories" scope="request">
	<jsp:include page="CategorySelection.jsp" flush="false"/>
</logic:present>
<logic:present name="subsystems" scope="request">
	<jsp:include page="SubsystemSelection.jsp" flush="false"/>
</logic:present>
<logic:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" id="decorator" name="decorators">
	<h4><bean:write name="decorator" property="name"/>&nbsp;&nbsp;<small>
		<a target="_blank" href="mskShowExplanations#<bean:write name="decorator" property="name"/>">Explain</a>&nbsp;
		<span><a >Off</a></span>
		<span><a>On</a></span>
	</small></h4>
	<table cellpadding="4" cellspacing="0" border="0" id="<bean:write name="decorator" property="name"/>_table">
		<tr class="stat_header">
			<td>Producer Id<%--
			--%>&nbsp;<a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1000&<bean:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
			--%><a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1000&<bean:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
			--%></td>
			<td>Category<%--
			--%>&nbsp;<a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1001&<bean:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
			--%><a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1001&<bean:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
			--%></td>
			<td>Subsystem<%--
			--%>&nbsp;<a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1002&<bean:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
			--%><a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1002&<bean:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
			--%></td>
			<logic:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">
				<td title="<bean:write name="caption" property="shortExplanation"/>">
					<bean:write name="caption" property="caption"/><%--
					--%>&nbsp;<a title="ascending sort by <bean:write name="caption" property="shortExplanationLowered"/>" href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=<bean:write name="ind"/>&<bean:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
					--%><a title="descending sort by <bean:write name="caption" property="shortExplanationLowered"/>" href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=<bean:write name="ind"/>&<bean:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a>
				</td>
			</logic:iterate>
			<td>class<%--
			--%>&nbsp;<a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1003&<bean:write name="decorator" property="sortOrderParameterName"/>=ASC"><%=ASC%></a><%--
			--%><a href="<bean:write name="linkToCurrentPage"/>&<bean:write name="decorator" property="sortByParameterName"/>=1003&<bean:write name="decorator" property="sortOrderParameterName"/>=DESC"><%=DESC%></a><%--
			--%></td>
		</tr>
		<logic:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
			<tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>">
				<td><a href="mskShowProducer?pProducerId=<bean:write name="producer" property="id"/>" title="Show details for this producer"><bean:write name="producer" property="id"/></a></td>
				<td><a href="mskShowProducersByCategory?pCategory=<bean:write name="producer" property="category"/>" title="Show all producers in category: <bean:write name="producer" property="category"/>"><bean:write name="producer" property="category"/></a></td>
				<td><a href="mskShowProducersBySubsystem?pSubsystem=<bean:write name="producer" property="subsystem"/>" title="Show all producers in subsystem: <bean:write name="producer" property="subsystem"/>"><bean:write name="producer" property="subsystem"/></a></td>
				<logic:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
					<td onmouseover="Tip('<bean:write name="producer" property="id"/>.<bean:write name="value" property="name"/><br><b><span align=center><bean:write name="value" property="value"/></span></b>', TEXTALIGN, 'center')" onmouseout="UnTip()"><bean:write name="value" property="value"/></td>
				</logic:iterate>
				<td><bean:write name="producer" property="className"/></td>
			</tr>
		</logic:iterate>
	</table>
</logic:iterate>
<br/><br/>
<small>Generated at <bean:write name="timestampAsDate"/>, timestamp: <bean:write name="timestamp"/></small>


</body>
</html>


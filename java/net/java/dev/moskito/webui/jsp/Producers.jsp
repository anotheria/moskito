<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags"	prefix="msk"%>
<html>
<head>
<title>Moskito Producers <msk:write name="pageTitle" /></title>
<link rel="stylesheet" href="mskCSS">
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<br />
<br />

<msk:define id="ASC" type="java.lang.String">
	<img
		src="<msk:write name="mskPathToImages" scope="application"/>msk_u.gif"
		border="0" alt="Sort ascending">
</msk:define>

<msk:define id="DESC" type="java.lang.String">
	<img
		src="<msk:write name="mskPathToImages" scope="application"/>msk_d.gif"
		border="0" alt="Sort descending">
</msk:define>
	<jsp:include page="Menu.jsp" flush="false" />
 
<br />	
<br />


<div class="main">
<msk:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" 	id="decorator" name="decorators">	
<div class="clear"><!-- --></div>
<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">

	<h2><a href="javascript:void(0);" class=""> <msk:write name="decorator" property="name" />&nbsp;&nbsp;</a></h2>			
		<ul class="filter">
		 <li>
			<msk:present name="intervals" scope="request">
		 		<jsp:include page="IntervalSelection.jsp" flush="false" />
			</msk:present>
		</li>				
		</ul>			
		<ul class="filter">
		<li>
			<msk:present name="units" scope="request">
				<jsp:include page="UnitSelection.jsp" flush="false" />
			</msk:present>
		</li>				
		</ul>
		<a href="#" class="help">Help</a>
		<div class="clear"><!-- --></div>
		<div class="table_itseft">
			<div class="top">
				<div class="left"><!-- --></div>
				<div class="right"><!-- --></div>
			</div>
			<div class="in">			
	
		<table cellpadding="0" cellspacing="0" class="fll" id="<msk:write name="decorator" property="name"/>_table">
		  <thead>
			<tr class="stat_header">
				<th>Producer Id<%--	--%>&nbsp;
					<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write
					   name="ASC" /></a><%-- --%>
					<a	href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
						name="DESC" /></a><%-- --%>
				</th>
				<th>Category<%-- --%>&nbsp;
					<a	href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1001&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write
						name="ASC" /></a><%-- --%>
					<a	href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1001&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
						name="DESC" /></a><%-- 	--%>
				</th>
				<th>Subsystem<%-- --%>&nbsp;
					<a	href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1002&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write
						name="ASC" /></a><%-- --%>
					<a	href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1002&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
						name="DESC" /></a><%--	--%>
				</th>
			</tr>	
		</thead>
		<tbody>	
			<msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
			 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<td>
					<a href="mskShowProducer?pProducerId=<msk:write name="producer" property="id"/>"
					   title="Show details for this producer">
					   <msk:write name="producer" property="id" />
					 </a>
				</td>
				<td>
					<a	href="mskShowProducersByCategory?pCategory=<msk:write name="producer" property="category"/>"
						title="Show all producers in category:	<msk:write name="producer" property="category"/>">
						<msk:write name="producer" property="category" />
					</a>
				</td>
				<td>
					<a	href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="producer" property="subsystem"/>"
						title="Show all producers in subsystem: <msk:write name="producer" property="subsystem"/>">
						<msk:write	name="producer" property="subsystem" />
					</a>
				</td>
			</tr>
			</msk:iterate>	
		</tbody>			
	</table>
		
	<div class="table_right">	
		<table cellpadding="0" cellspacing="0">
		 <thead>
		  <tr>		  
			<msk:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">				
			 <th title="<msk:write name="caption" property="shortExplanation"/>">
				<msk:write name="caption" property="caption" /><%--
					--%>&nbsp;
					<a 	class="up" title="ascending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
						href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write
						name="ASC" /></a><%--
					--%>
					<a	class="up" title="descending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
						href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
						name="DESC" /></a>
			 </th>
			</msk:iterate>			
			
			<th>class<%-- --%>&nbsp;
				<a	class="up" href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1003&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write
					name="ASC" /></a><%--	--%>
				<a	class="up" href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1003&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
					name="DESC" /></a><%-- 	--%>
			</th>
		 </tr>		
	   </thead>
	  <tbody>
	   <msk:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
			<msk:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
				<td onmouseover="Tip('<msk:write name="producer" property="id"/>.<msk:write name="value" property="name"/><br><b><span align=center><msk:write name="value" property="value"/></span></b>', TEXTALIGN, 'center')" onmouseout="UnTip()">
					<msk:write name="value" property="value" />
				</td>
			</msk:iterate>
			<td>
				<msk:write name="producer" property="className" />
			</td>
		</tr>	
		</msk:iterate>	
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

		<div class="bot"><div><!-- --></div></div>
	</div>
	</msk:iterate>
	<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/></div>
</div>	
</body>
</html>


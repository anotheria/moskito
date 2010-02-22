<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>
<html>
<head>
	<title>Moskito Producer <msk:write name="producer" property="id"/> </title>
	<link rel="stylesheet" href="mskCSS">
	<msk:define id="ASC" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_u.gif" border="0" alt="Sort ascending"></msk:define>
	<msk:define id="DESC" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_d.gif" border="0" alt="Sort descending"></msk:define>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<br />
<br />



producer:&nbsp;<msk:write name="producer" property="id"/>

<msk:present name="inspectableFlag">
	&nbsp;<a href="mskInspectProducer?pProducerId=<msk:write name="producer" property="id"/>">inspect</a>
</msk:present> 

<br/>
	<a href="mskShowProducersByCategory">category</a>:&nbsp;<a href="mskShowProducersByCategory?pCategory=<msk:write name="producer" property="category"/>"><msk:write name="producer" property="category"/></a><br/>
	<a href="mskShowProducersBySubsystem">subsystem</a>:&nbsp;<a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="producer" property="subsystem"/>"><msk:write name="producer" property="subsystem"/></a><br/>
	class:&nbsp;<msk:write name="producer" property="className"/><br/>
<br/>

<br/>

<div class="main">
<msk:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>
				<span>Subsystem: </span><a href="mskShowProducersBySubsystem?pSubsystem=<msk:write name="producer" property="subsystem"/>"><msk:write name="producer" property="subsystem"/></a>
			</div>
			<div>
				<span>class: </span><span><msk:write name="producer" property="className"/></span>
			</div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">

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
				<th>Name<%--	--%>&nbsp;
					<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"><msk:write name="ASC"/></a>
					<%-- --%>
					<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write name="DESC"/></a>
					<%-- --%>
					<a href="<msk:write name="linkToCurrentPage"/>&<msk:write name="decorator" property="sortByParameterName"/>=1000&<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"><msk:write
					   name="DESC" /></a>
				    <%-- --%>
				</th>									
			</tr>	
		</thead>
		<tbody>		
			<msk:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td class="even">
						<msk:write name="stat" property="name"/>
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
		  <msk:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			 <tr class="even">
				<msk:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
					<td>
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
	<div class="filter_2" style="display:none;">
			<ul class="filter">
				<li>Interval:</li>
				<li class="active"><a href="#">default</a></li>
				<li><a href="#">1m</a></li>
				<li><a href="#">5m</a></li>
				<li><a href="#">15m</a></li>
				<li><a href="#">1h</a></li>
				<li><a href="#">12h</a></li>
				<li class="last"><a href="#">1d</a></li>
			</ul>
			<ul class="filter">
				<li>Unit:</li>
				<li class="active"><a href="#">millisec</a></li>
				<li><a href="#">sec</a></li>
				<li><a href="#">microsec</a></li>
				<li class="last"><a href="#">nanosec</a></li>
			</ul>
			<div class="clear"><!-- --></div>
			</div>
		</div>

		<div class="bot"><div><!-- --></div></div>
	</div>
	</msk:iterate>
	<div class="generated">Generated at <msk:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <msk:write name="timestamp"/></div>
</div>	
</body>
</html>


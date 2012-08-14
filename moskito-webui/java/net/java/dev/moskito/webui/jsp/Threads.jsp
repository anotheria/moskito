<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito Threads <ano:write name="pageTitle" /></title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false" />

<%--
<div class="main">
<ano:iterate type="net.java.dev.moskito.webui.bean.ProducerDecoratorBean" 	id="decorator" name="decorators">	
<div class="clear"><!-- --></div>
<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	
	<ano:define id="sortType" type="net.java.dev.moskito.webui.bean.ProducerBeanSortType" name="<%=decorator.getSortTypeName()%>"/>
	<ano:define id="visibility" type="net.java.dev.moskito.webui.bean.ProducerVisibility" name="decorator" property="visibility"/>

	<ano:equal name="visibility" value="SHOW">
		<h2 class="titel_open">
		<a href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="producerVisibilityParameterName"/>=<ano:write name="visibility" property="opposite"/>">
			<ano:write name="decorator" property="name" />
		</a>
		</h2>
	</ano:equal>
	<ano:equal name="visibility" value="HIDE">
		<h2 class="title_collapsed">
			<a class="hidden" href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="producerVisibilityParameterName"/>=<ano:write name="visibility" property="opposite" />">
		<ano:write name="decorator" property="name" />
		</a>
		</h2>
	</ano:equal>
	<a target="_blank" class="help" href="mskShowExplanations#<ano:write name="decorator" property="name"/>">Help</a>&nbsp;
				
				
	
	<div class="clear"><!-- --></div>
	<ano:equal name="visibility" value="SHOW">
		<div class="table_itseft">
			<div class="top">
				<div class="left"><!-- --></div>
				<div class="right"><!-- --></div>
			</div>
			<div class="in">			
		<table cellpadding="0" cellspacing="0" class="fll" id="<ano:write name="decorator" property="name"/>_table">
		  <thead>
			<tr class="stat_header">
				<th>
					<ano:equal name="sortType" property="sortBy" value="1000">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC">Producer Id </a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Producer Id </a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="1000">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Producer Id </a>
					</ano:notEqual>
				</th>
				<th>
					<ano:equal name="sortType" property="sortBy" value="1001">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by category"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1001&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC">Category</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by category"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1001&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Category</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="1001">
						<a 	class="" title="ascending sort by category"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1001&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Category</a>
					</ano:notEqual>
				</th>
				<th>
					<ano:equal name="sortType" property="sortBy" value="1002">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by subsystem"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1002&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC">Subsystem</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by subsystem"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1002&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Subsystem</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="1002">
						<a 	class="" title="ascending sort by subsystem"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1002&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Subsystem</a>
					</ano:notEqual>
				</th>
			</tr>	
		</thead>
		<tbody>	
			<ano:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
			 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<td>
					<a href="mskShowProducer?pProducerId=<ano:write name="producer" property="id"/>"
					   title="Show details for this producer">
					   <ano:write name="producer" property="id" />
					 </a>
				</td>
				<td>
					<a	href="mskShowProducersByCategory?pCategory=<ano:write name="producer" property="category"/>"
						title="Show all producers in category:	<ano:write name="producer" property="category"/>">
						<ano:write name="producer" property="category" />
					</a>
				</td>
				<td>
					<a	href="mskShowProducersBySubsystem?pSubsystem=<ano:write name="producer" property="subsystem"/>"
						title="Show all producers in subsystem: <ano:write name="producer" property="subsystem"/>">
						<ano:write	name="producer" property="subsystem" />
					</a>
				</td>
			</tr>
			</ano:iterate>	
		</tbody>			
	</table>
		
	<div class="table_right">
		<table cellpadding="0" cellspacing="0">
		 <thead>
		  <tr>		  
			<ano:iterate name="decorator" property="captions" type="net.java.dev.moskito.webui.bean.StatCaptionBean" id="caption" indexId="ind">				
			 <th title="<ano:write name="caption" property="shortExplanation"/>">

					<!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
				 	<input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>
					<ano:equal name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC"><ano:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption"/></a><a href="#"
																								 onClick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
					</ano:notEqual>
			 </th>
			</ano:iterate>			
			
			<th>class
					<ano:equal name="sortType" property="sortBy" value="1003">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by producer class"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1002&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC"></a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by producer class"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1003&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="1003">
						<a 	class="" title="ascending sort by producer class"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1003&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
					</ano:notEqual>
			</th>
		 </tr>		
	   </thead>
	  <tbody>
	   <ano:iterate name="decorator" property="producers" id="producer" type="net.java.dev.moskito.webui.bean.ProducerBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
			<ano:iterate name="producer" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
				<td onmouseover="Tip('<ano:write name="producer" property="id"/>.<ano:write name="value" property="name"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;<ano:write name="value" property="value"/>&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
					<ano:write name="value" property="value" />
				</td>
			</ano:iterate>
			<td class="al_left" onmouseover="Tip('<ano:write name="producer" property="fullClassName"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
				<ano:write name="producer" property="className" />
			</td>
		</tr>	
		</ano:iterate>	
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
		</ano:equal>

   </div>

		<div class="bot"><div><!-- --></div></div>
	</div>
	</ano:iterate>
	<div class="generated">Generated at <ano:write name="timestampAsDate"/>&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <ano:write name="timestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp;Interval updated at: <ano:write name="currentIntervalUpdateTimestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp; Interval age: <ano:write name="currentIntervalUpdateAge"/></div>

--%>
<jsp:include page="Footer.jsp" flush="false" />
</div>	
</body>
</html>


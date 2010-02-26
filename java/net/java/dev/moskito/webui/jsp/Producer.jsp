<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producer <msk:write name="producer" property="id"/> </title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="ProducersMainMenu.jsp" flush="false" />

<div class="main">
<msk:iterate type="net.java.dev.moskito.webui.bean.StatDecoratorBean" id="decorator" name="decorators">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div>
				<span>Producer: <b><msk:write name="producer" property="id"/></b></span>				
				<msk:present name="inspectableFlag">
					&nbsp;<a href="mskInspectProducer?pProducerId=<msk:write name="producer" property="id"/>">inspect</a>
				</msk:present>
			</div>
			<div>
				<span>Category: </span><a href="mskShowProducersByCategory?pCategory=<msk:write name="producer" property="category"/>"><msk:write name="producer" property="category"/></a>
			</div>
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
	<h2><msk:write name="producer" property="id" /></h2>
	<a target="_blank" class="help" href="mskShowExplanations#<msk:write name="decorator" property="name"/>">Help</a>&nbsp;	
	<msk:define id="sortType" type="net.java.dev.moskito.webui.bean.StatBeanSortType" name="<%=decorator.getSortTypeName()%>"/>
	<msk:write name="sortType"/>
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
				<th>Name
					<msk:equal name="sortType" property="sortBy" value="1000">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"></a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="1000">
						<a 	class="up" title="ascending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=1000&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
					</msk:notEqual>
				</th>									
			</tr>	
		</thead>
		<tbody>		
			<msk:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td>
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
				<msk:write name="caption" property="caption" />
					<msk:equal name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<msk:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=DESC"></a>
						</msk:equal>
						<msk:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <msk:write name="caption" property="shortExplanationLowered"/>"
								href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
						</msk:equal>
					</msk:equal>   
					<msk:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="up" title="ascending sort by <msk:write name="caption" property="shortExplanationLowered"/>"
							href="<msk:write name="linkToCurrentPage"/>&amp;<msk:write name="decorator" property="sortByParameterName"/>=<msk:write name="ind"/>&amp;<msk:write name="decorator" property="sortOrderParameterName"/>=ASC"></a>
					</msk:notEqual>
			 </th>
			</msk:iterate>			
		 </tr>		
	   </thead>
	  <tbody>
		  <msk:iterate name="decorator" property="stats" id="stat" type="net.java.dev.moskito.webui.bean.StatBean" indexId="index">
			 <tr class="even">
				<msk:iterate name="stat" property="values" id="value" type="net.java.dev.moskito.webui.bean.StatValueBean">
					<td onmouseover="Tip('<msk:write name="stat" property="name"/>.<msk:write name="value" property="name"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;<msk:write name="value" property="value"/>&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
							<msk:write name="value" property="value" />
					</td>
				</msk:iterate>
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


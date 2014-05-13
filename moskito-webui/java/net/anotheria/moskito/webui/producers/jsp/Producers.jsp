<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producers <ano:write name="pageTitle" /></title>
	<link rel="stylesheet" href="mskCSS"/>
	<link rel="stylesheet" type="text/css" href="../css/charts.css">
    <link rel="stylesheet" type="text/css" href="../css/jquery.jqplot.css">
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<!-- jqplot core + plugins -->
<script type="text/javascript" src="../js/charts/jqplot/jquery.jqplot.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.highlighter.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.donutRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.barRenderer.min.js"></script>

<script type="text/javascript" src="../js/charts/chartEngineIniter.js"></script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<!--
 Data for action -->
 <script>
<ano:iterate type="net.anotheria.moskito.webui.shared.bean.GraphDataBean" id="graph" name="graphDatas">
	var <ano:write name="graph" property="jsVariableName"/>Caption = "<ano:write name="graph" property="caption"/>";
	var <ano:write name="graph" property="jsVariableName"/>Array = <ano:write name="graph" property="jsArrayValue"/>;
</ano:iterate> 

 </script>
<!-- -->


<div class="main">
<ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" 	id="decorator" name="decorators">
<div class="clear"><!-- --></div>
<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	
	<ano:define id="sortType" type="net.anotheria.moskito.webui.producers.api.ProducerAOSortType" name="<%=decorator.getSortTypeName()%>"/>
	<ano:define id="visibility" type="net.anotheria.moskito.webui.shared.bean.ProducerVisibility" name="decorator" property="visibility"/>

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
			<ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO" indexId="index">
			 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<td>
					<a href="mskShowProducer?pProducerId=<ano:write name="producer" property="producerId"/>"
					   title="Show details for this producer">
					   <ano:write name="producer" property="producerId" />
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
			<ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
			 <th title="<ano:write name="caption" property="shortExplanation"/>">

					<!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
				 	<input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>
					<ano:equal name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC"><ano:write name="caption" property="caption"/></a><a href="#"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption"/></a><a href="#"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption"/></a><a href="#"
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
	   <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
			<ano:iterate name="producer" property="firstStatsValues" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
				<td onmouseover="Tip('<ano:write name="producer" property="producerId"/>.<ano:write name="value" property="name"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;<ano:write name="value" property="value"/>&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
					<ano:write name="value" property="value" />
				</td>
			</ano:iterate>
			<td class="al_left" onmouseover="Tip('<ano:write name="producer" property="fullProducerClassName"/>', TEXTALIGN, 'center')" onmouseout="UnTip()">
				<ano:write name="producer" property="producerClassName" />
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
<div class="lightbox" style="display:none;">
	<div class="black_bg"><!-- --></div>
	<div class="box">
		<div class="box_top">
			<div><!-- --></div>
			<span><!-- --></span>
			<a class="close_box"><!-- --></a>

			<div class="clear"><!-- --></div>
		</div>
		<div class="box_in">
			<div class="right">
				<div class="text_here">
					<div id="chartcontainer"></div>
					<a href="#" class="pie_chart active"></a> <!-- changes to bar_chart -->
					<a href="#" class="bar_chart"></a>
				</div>
			</div>
		</div>
		<div class="box_bot">
			<div><!-- --></div>
			<span><!-- --></span>
		</div>
	</div>
</div>
<script type="text/javascript">

    var chartParams,
        chartEngineName = '<ano:write name="chartEngine"/>' || 'JQPlOT';


	$('.chart').click(function() {
		lightbox();
        chartParams = {
            container: 'chartcontainer',
            names: [eval($(this).parent().find('input').val()+'Caption')],
            data: eval($(this).parent().find('input').val()+'Array'),
            type: 'PieChart',
            title: ''
        };

        chartEngineIniter[chartEngineName](chartParams);

        return false;
	});

	$('.pie_chart').click(function() {
        chartParams.type = 'ColumnChart';
        chartEngineIniter[chartEngineName](chartParams);
        $('.bar_chart').addClass('active').siblings('.active').removeClass('active');

        return false;
	});

	$('.bar_chart').click(function() {
        chartParams.type = 'PieChart';

        chartEngineIniter[chartEngineName](chartParams);
		$('.pie_chart').addClass('active').siblings('.active').removeClass('active');

        return false;
	});

	function lightbox() {
	    var $lightbox = $('.lightbox');
	    var $modal = $('.box', $lightbox);

	    $lightbox.show();
	    $('.pie_chart').addClass('active').siblings('.active').removeClass('active');

	    $modal.css('width', 'auto').width($modal.width());

	    $modal.css({
	        left: '50%',
	        marginLeft: -$modal.width()/2,
	        top: '50%',
	        marginTop: -$modal.height()/2,
	        position: 'fixed'
	    });

	    return false;
	}
</script>
<jsp:include page="../../shared/jsp/Footer.jsp" flush="false" />
</div>	
</body>
</html>


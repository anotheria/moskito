<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producer <ano:write name="producer" property="id"/> </title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

    <script type="text/javascript" src="../js/wz_tooltip.js"></script>
    <script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
    <script type="text/javascript" src="../js/function.js"></script>
    <script type="text/javascript" src="http://www.google.com/jsapi"></script>

    <!--
     Data for charts
     -->
     <script>
    <ano:iterate type="net.anotheria.moskito.webui.shared.bean.GraphDataBean" 	id="graph" name="graphDatas">
        var <ano:write name="graph" property="jsVariableName"/>Caption = "<ano:write name="graph" property="caption"/>";
        var <ano:write name="graph" property="jsVariableName"/>Array = <ano:write name="graph" property="jsArrayValue"/>;
    </ano:iterate>

     </script>

    <script type="text/javascript">
        $(function() {
            var $dataTable = $('table.producer_filter_data_table'),
                dataTh = $dataTable.find('th'),
                dataTd = $dataTable.find('td'),
                flagDown = true,
                flagUp = true,
                activeCell = undefined,
                activeHeaderCell = undefined;

            dataTh.mouseenter(function() {
                activeHeaderCell = $(this).find('a');
            }).mouseleave(function() {
                activeHeaderCell = undefined;
            })

            dataTd.mouseenter(function() {
                activeCell = $(this);
              }).mouseleave(function() {
                activeCell = undefined;
            });

            $(document).keydown(
                function( e ){
                    if( e.keyCode == 65 && e.ctrlKey && flagDown ){
                        flagDown = false;
                        flagUp = true;

                        if (activeCell){
                            console.log(activeCell.text());
                            e.preventDefault();
                        }

                        if (activeHeaderCell){
                            console.log(activeHeaderCell.text());
                            e.preventDefault();
                        }
                    }
                }
            );

            $( document ).keyup(
                function( e ){
                    if( e.keyCode == 65 && flagUp ){
                        flagDown = true;
                        flagUp = false;
                        if (activeCell){
                            e.preventDefault();
                        }
                        if (activeHeaderCell){
                            e.preventDefault();
                        }
                    }
                }
            );
        });
    </script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<div class="main">
<ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators">
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in ovh">
            <dl class="dl-horizontal">
				<dt>Producer:</dt> <dd><b><ano:write name="producer" property="id"/></b></dd>

				<dt>Category: </dt> <dd><a href="mskShowProducersByCategory?pCategory=<ano:write name="producer" property="category"/>"><ano:write name="producer" property="category"/></a></dd>

				<dt>Subsystem: </dt> <dd><a href="mskShowProducersBySubsystem?pSubsystem=<ano:write name="producer" property="subsystem"/>"><ano:write name="producer" property="subsystem"/></a></dd>

				<dt>Сlass: </dt> <dd><ano:write name="producer" property="className"/></dd>
            </dl>
            <div class="btn-group flr">
                <a href="<ano:write name="linkToCurrentPage"/>&pForward=selection&target=Accumulator" class="btn fll">Add Accumulator</a>
                <a href="<ano:write name="linkToCurrentPage"/>&pForward=selection&target=Threshold" class="btn fll">Add Threshold</a>
                <ano:present name="inspectableFlag">
                    <a href="mskInspectProducer?pProducerId=<ano:write name="producer" property="id"/>" class="btn-blue fll">Inspect</a>
                </ano:present>
            </div>
        </div>
        <div class="bot"><div><!-- --></div></div>
    </div>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	<h2><ano:write name="producer" property="id" /></h2>
	<a target="_blank" class="help" href="mskShowExplanations#<ano:write name="decorator" property="name"/>">Help</a>&nbsp;	
	<ano:define id="sortType" type="net.anotheria.moskito.webui.shared.bean.StatBeanSortType" name="<%=decorator.getSortTypeName()%>"/>
		<div class="clear"><!-- --></div>
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
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC">Name</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Name</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="1000">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=1000&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC">Name</a>
					</ano:notEqual>
				</th>									
			</tr>	
		</thead>
		<tbody>		
			<ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
			  <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
					<td>
						<ano:write name="stat" property="name"/>
					</td>				
				</tr>
			</ano:iterate>
		</tbody>			
	</table>
		
	<div class="table_right">	
		<table class="producer_filter_data_table" cellpadding="0" cellspacing="0">
		 <thead>
		  <tr>		    
			<ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
			 <th title="<ano:write name="caption" property="shortExplanation"/>">
				<!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
				 <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>
					<ano:equal name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<ano:equal name="sortType" property="ASC" value="true">
							<a 	class="down" title="descending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=DESC"><ano:write name="caption" property="caption" /></a><a href="#"
																								 onclick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption" /></a><a href="#"
																								 onclick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption" /></a><a href="#"
																								 onclick="lightbox($(this));"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
					</ano:notEqual>
			 </th>
			</ano:iterate>			
		 </tr>		
	   </thead>
	  <tbody>
		  <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
		 <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
				<ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.shared.bean.StatValueBean">
					<td onmouseover="Tip('<ano:write name="stat" property="name"/>.<ano:write name="value" property="name"/>&lt;br/&gt;&lt;b&gt;&lt;span align=center&gt;<ano:write name="value" property="value"/>&lt;/span&gt;&lt;/b&gt;', TEXTALIGN, 'center')" onmouseout="UnTip()">
							<ano:write name="value" property="value" />
					</td>
				</ano:iterate>
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
	</div>

	<div class="bot"><div><!-- --></div></div>
	</div>
	</ano:iterate>
	<div class="generated">Generated at <ano:write name="timestampAsDate"/>&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <ano:write name="timestamp"/></div>

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
					<a href="#" class="pie_chart"></a> <!-- changes to bar_chart -->
					<a href="#" style="display:none;" class="bar_chart"></a>
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
	google.load('visualization', '1', {packages: ['piechart']});
	google.load('visualization', '1', {packages: ['columnchart']});
    function lightbox(link) {
        $('.lightbox').show();
        var el = $('.lightbox');
        $('.pie_chart').show();
        $('.bar_chart').hide();
        $('.lightbox .box').css('width', 'auto');
        $('.lightbox .box').width($('.lightbox .box_in').width());

        var wid = el.find('.box').width();
        var box = el.find('.box');
        var hig = el.find('.box').height();
        box.css('left', '50%');
        box.css('margin-left', -wid / 2);
        box.css('top', '50%');
        box.css('margin-top', -hig / 2);
        box.css('position', 'fixed');
        return false;
    }
    ;
	//var datas = new Array;
	var cap, mas, data;
	$('.chart').click(function() {
		cap = eval($(this).parent().find('input').val()+'Caption');
		mas = eval($(this).parent().find('input').val()+'Array');
		data = new google.visualization.DataTable();
        data.addColumn('string', 'Stat');
        data.addColumn('number', 'val');
		data.addRows(mas);
		new google.visualization.PieChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: <ano:write name="config" property="producerChartWidth"/>, height:<ano:write name="config" property="producerChartHeight"/>, title: cap, legendFontSize: 12, legend:'label'});
		return false;
	});

	$('.pie_chart').live('click', function() {
		new google.visualization.ColumnChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: <ano:write name="config" property="producerChartWidth"/>, height:<ano:write name="config" property="producerChartHeight"/>, title: cap, legendFontSize: 12, legend:'label'});
		$('.pie_chart').hide();
		$('.bar_chart').show();
		return false;
	});

	$('.bar_chart').live('click', function() {
		new google.visualization.PieChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: <ano:write name="config" property="producerChartWidth"/>, height:<ano:write name="config" property="producerChartHeight"/>, title: cap, legendFontSize: 12, legend:'label'});
		$('.pie_chart').show();
		$('.bar_chart').hide();
		return false;
	});
</script>
</div>	
</body>
</html>


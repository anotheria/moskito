<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>

<section id="main">
<div class="content">

<div class="box">
    <div class="box-content paddner">
        <dl class="dl-horizontal pull-left">
            <dt>Producer:</dt>
            <dd>${producer.producerId}</dd>
            <dt>Category:</dt>
            <dd><a href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</a></dd>
            <dt>Subsystem:</dt>
            <dd><a href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</a></dd>
            <dt>Сlass:</dt>
            <dd>${producer.producerClassName}</dd>
        </dl>
        <div class="pull-right">
            <a href="${linkToCurrentPage}&pForward=selection&target=Accumulator" class="btn btn-default" onclick="new_accumulator(); return false"><i class="fa fa-plus"></i> Accumulator</a>
            <a href="${linkToCurrentPage}&pForward=selection&target=Threshold" class="btn btn-default" onclick="new_threshold(); return false"><i class="fa fa-plus"></i> Threshold</a>
            <ano:equal name="producer" property="inspectable" value="true">
                <a href="#inspect" data-toggle="modal" data-target="#inspect" class="btn btn-success"><i class="fa fa-search"></i> Inspect</a>
            </ano:equal>
        </div>
    </div>
</div>

<ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators">
<div class="box">
    <div class="box-title">
        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseproducer"><i class="fa fa-caret-right"></i></a>
        <h3 class="pull-left">
            ${producer.producerId}
        </h3>
        <div class="box-right-nav">
            <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
        </div>
    </div>
    <div id="collapseproducer" class="box-content accordion-body collapse in">
        <table class="table table-striped tablesorter">
            <thead>
            <tr>
                <th>Name <i class="fa fa-caret-down"></i></th>
                <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
                    <th title="<ano:write name="caption" property="shortExplanation"/>" class="table-column">
                        <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                        <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption} <i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i></th>
                    </th>
                </ano:iterate>

            </tr>
            </thead>
            <tbody>
            <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                <tr>
                    <td>${stat.name}</td>
                    <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
                        <td title="${stat.name}.${value.name}=${value.value}">
                            ${value.value}
                        </td>
                    </ano:iterate>
                </tr>
            </ano:iterate>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</ano:iterate>


</div>

<jsp:include page="../../producers/jsp/ChartEngenine.jsp"/>

    <div class="modal fade inspect-list" id="inspect" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Inspect Producer '${producer.producerId}' creation location:</h4>
                </div>
                <div class="modal-body">
                    <ul>
                        <ano:iterate name="creationTrace" type="java.lang.String" id="line">
                            <li>${line}</li>
                        </ano:iterate>
                    </ul>
                </div>
            </div>
        </div>
    </div>

<div class="modal fade" id="createNewThreshold" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="CreateThreshold" action="mskThresholdCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Threshold"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Create new Threshold</h4>
            </div>
            <div class="modal-body">
                    <div class="form-group">
                        <label for="nameThresholds">Threshold name:</label>
                        <input type="text" class="form-control" id="nameThresholds" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Guards</label>
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Green"><i class="status status-green"></i></div>
                                    <select onchange="switchDirection();" name="greenDir" class="form-control"><option>below</option><option>above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label>Interval</label>
                                <input type="text" class="form-control" value="" placeholder="Value" name="greenValue" onchange="switchgreenvalue();">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Yellow"><i class="status status-yellow"></i></div>
                                    <select onchange="switchDirection();" name="yellowDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value=""  name="yellowValue" placeholder="Value">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Orange"><i class="status status-orange"></i></div>
                                    <select onchange="switchDirection();" name="orangeDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="orangeValue">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Red"><i class="status status-red"></i></div>
                                    <select onchange="switchDirection();" name="redDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value"  name="redValue" >
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Purple"><i class="status status-purple"></i></div>
                                    <select onchange="switchDirection();" name="purpleDir" class="form-control"><option>below</option><option  selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="purpleValue">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            ${interval.name}
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
            </div>
            <div class="form-group">
                <dl class="dl-horizontal">
                    <dt>Producer:</dt>
                    <dd>${producer.producerId}</dd>
                </dl>
            </div>

            <div class="modal-table">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
                            <th>${caption.caption}</th>
                        </ano:iterate>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                        <tr>
                            <td>${stat.name}</td>
                            <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
                                <td>
                                    <a href="#" onclick="setandsubmit('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                </td>
                            </ano:iterate>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        </form>
    </div>
</div>

<!-- Accumulator Dialog -->
<div class="modal fade" id="createNewAccumulator" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="CreateAccumulator" action="mskAccumulatorCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Accumulator"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Create new Accumulator</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nameAccumulator">Accumulator name:</label>
                        <input type="text" class="form-control" id="nameAccumulator" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            <ano:write name="interval" property="name"/>
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <dl class="dl-horizontal">
                        <dt>Producer:</dt>
                        <dd>${producer.producerId}</dd>
                    </dl>
                </div>

                <div class="modal-table">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
                                <th>${caption.caption}</th>
                            </ano:iterate>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                            <tr>
                                <td>${stat.name}</td>
                                <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
                                    <td>
                                        <a href="#" onclick="setandsubmitAccumulator('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                    </td>
                                </ano:iterate>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </form>
    </div>
</div>


<jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>
</section>

<script>
    function new_threshold(){
        $('#createNewThreshold').modal('show');
    }
    function new_accumulator(){
        $('#createNewAccumulator').modal('show');
    }


    function setandsubmitAccumulator(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateAccumulator.statName.value = statName;
        document.forms.CreateAccumulator.valueName.value = valueName;
        document.forms.CreateAccumulator.submit();
    }
    function setandsubmit(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateThreshold.statName.value = statName;
        document.forms.CreateThreshold.valueName.value = valueName;
        document.forms.CreateThreshold.submit();
    }
    function switchDirection(){
        if (document.forms.CreateThreshold.greenDir.value=='above')
            targetValue = 'below';
        else
            targetValue = 'above';

        document.forms.CreateThreshold.yellowDir.value = targetValue;
        document.forms.CreateThreshold.orangeDir.value = targetValue;
        document.forms.CreateThreshold.redDir.value = targetValue;
        document.forms.CreateThreshold.purpleDir.value = targetValue;
    }

    function switchgreenvalue(){
        document.forms.CreateThreshold.yellowValue.value=document.forms.CreateThreshold.greenValue.value;
    }
</script>

</script>

</body>
</html>


<%--
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Producer <ano:write name="producer" property="producerId"/> </title>
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
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.cursor.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.dateAxisRenderer.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.highlighter.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.pieRenderer.min.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.donutRenderer.min.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
    <script type="text/javascript" src="../js/charts/jqplot/jqplot.barRenderer.min.js"></script>


    <script type="text/javascript" src="../js/charts/chartEngineIniter.js"></script>

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
				<dt>Producer:</dt> <dd><b><ano:write name="producer" property="producerId"/></b></dd>

				<dt>Category: </dt> <dd><a href="mskShowProducersByCategory?pCategory=<ano:write name="producer" property="category"/>"><ano:write name="producer" property="category"/></a></dd>

				<dt>Subsystem: </dt> <dd><a href="mskShowProducersBySubsystem?pSubsystem=<ano:write name="producer" property="subsystem"/>"><ano:write name="producer" property="subsystem"/></a></dd>

				<dt>Сlass: </dt> <dd><ano:write name="producer" property="producerClassName"/></dd>
            </dl>
            <div class="btn-group flr">
                <a href="<ano:write name="linkToCurrentPage"/>&pForward=selection&target=Accumulator" class="btn fll">Add Accumulator</a>
                <a href="<ano:write name="linkToCurrentPage"/>&pForward=selection&target=Threshold" class="btn fll">Add Threshold</a>
                <ano:equal name="producer" property="inspectable" value="true">
                    <a href="mskInspectProducer?pProducerId=<ano:write name="producer" property="producerId"/>" class="btn-blue fll">Inspect</a>
                </ano:equal>
            </div>
        </div>
        <div class="bot"><div><!-- --></div></div>
    </div>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
	<div class="top"><div><!-- --></div></div>
	<div class="in">
	<h2><ano:write name="producer" property="producerId" /></h2>
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
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
						<ano:equal name="sortType" property="DESC" value="true">
							<a 	class="up" title="ascending resort by <ano:write name="caption" property="shortExplanationLowered"/>"
								href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption" /></a><a href="#"
																								 class="chart"
																								 title="chart">&nbsp;&nbsp;&nbsp;</a>
						</ano:equal>
					</ano:equal>   
					<ano:notEqual name="sortType" property="sortBy" value="<%=\"\"+ind%>">
						<a 	class="" title="ascending sort by <ano:write name="caption" property="shortExplanationLowered"/>"
							href="<ano:write name="linkToCurrentPage"/>&amp;<ano:write name="decorator" property="sortByParameterName"/>=<ano:write name="ind"/>&amp;<ano:write name="decorator" property="sortOrderParameterName"/>=ASC"><ano:write name="caption" property="caption" /></a><a href="#"
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
				<ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
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
    //var datas = new Array;
    //google.load('visualization', '1', {packages: ['piechart']});
    //google.load('visualization', '1', {packages: ['columnchart']});
    //var cap, mas, data;
    var chartParams,
        chartEngineName = '<ano:write name="chartEngine"/>' || 'JQPlOT';


    $('.chart').click(function() {
        /*
        mas = eval($(this).parent().find('input').val()+'Array');
        data = new google.visualization.DataTable();
        data.addColumn('string', 'Stat');
        data.addColumn('number', 'val');
        data.addRows(mas);
        new google.visualization.PieChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: <ano:write name="config" property="producerChartWidth"/>, height:<ano:write name="config" property="producerChartHeight"/>, title: cap, legendFontSize: 12, legend:'label'});
        */
        lightbox();
        chartParams = {
            container: 'chartcontainer',
            names: [eval($(this).parent().find('input').val()+'Caption')],
            data: eval($(this).parent().find('input').val()+'Array'),
            type: 'PieChart',
            title: eval($(this).parent().find('input').val()+'Caption')
        };

        chartEngineIniter[chartEngineName](chartParams);

        return false;
    });

    $('.pie_chart').click(function() {
        /*new google.visualization.ColumnChart(
          document.getElementById('chartcontainer')).
            draw(data, {is3D:true, width: <ano:write name="config" property="producerChartWidth"/>, height:<ano:write name="config" property="producerChartHeight"/>, title: cap, legendFontSize: 12, legend:'label'});
            */
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
</div>	
</body>
</html>

    --%>
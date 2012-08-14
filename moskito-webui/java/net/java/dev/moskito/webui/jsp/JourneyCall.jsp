<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MoSKito Recorded Journey Call: <ano:write name="recordedUseCase" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
<ano:define id="IMG" type="java.lang.String"><img src="<ano:write name="mskPathToImages" scope="application"/>msk_l.gif" border="0" alt=""></ano:define
><ano:define id="EMPTY" type="java.lang.String"><img src="<ano:write name="mskPathToImages" scope="application"/>msk_s.gif" border="0" alt=""></ano:define
>

    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"></script>

    <script>
        $(function() {
            var positionCheckbox = $('.select_current_row_positions_checkbox');

            positionCheckbox.on('change', function(){

                var $this = $(this),
                    $thisTr = $this.parents('table.journeys_summary_table tr'),
                    PositionListLink = $thisTr.find($('td.journeys_summary_table_positions_list a')),
                    actionSelect = $this.prop('checked');

                PositionListLink.each(function(a){
                    var lineNumber = ($(this).text()),
                        highlightedTr = $('a[name='+lineNumber+']').parents('tr');


                    if (actionSelect) {
                        highlightedTr.find('td').addClass('td_select_highlight') ;
                    }else {
                        highlightedTr.find('td').removeClass('td_select_highlight')
                    }
                })
            });

            var deselectPositionsButton = $('.deselect_all_journey_positions');
            deselectPositionsButton.on('click', function(){
                positionCheckbox.each(function() {
                    var $this = $(this);

                    $this.prop('checked', '');
                    $this.change();
                })
            })
        })
    </script>

    <style>
        .td_select_highlight {
            background: #D7E9CA!important;
        }

        .table_itseft .in table tr:hover .td_select_highlight {
            background: #E1EEFA!important;
        }

        .deselect_all_journey_positions {
            padding: 0 4px;
        }

        .select_current_row_positions_checkbox {
            display: block;
            margin: 0 auto;
        }
    </style>
    
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"></script>
    <script type="text/javascript" src="../js/jquery.treeTable.js"></script>

    <script>
        $(document).ready(function()  {
          $("#tree_table").treeTable();
        });

    </script>    
    <script type="text/javascript" src="../js/wz_tooltip.js"></script>
</head>
<body>
<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="mskShowJourneys">Journeys</a></li>
		<li><a href="mskShowJourney?pJourneyName=<ano:write name="journeyName"/>"><ano:write name="journeyName"/></a></li>
		<li class="last"><span><ano:write name="tracedCall" property="name"/> </span></li>
	</ul>
	<div class="clear"><!-- --></div>

	<h1><ano:write name="tracedCall" property="name"/></h1>
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><ano:write name="tracedCall" property="created"/>&nbsp;&nbsp;<ano:write name="tracedCall" property="date"/> &nbsp;&nbsp;</span></div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top"><div><!-- --></div></div>
		<div class="in">			
			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
			
				
				<table cellpadding="0" cellspacing="0" width="100%" id="tree_table">
				<thead>
						<tr class="stat_header" id="node--1">
							<th></th>
							<th>Call</th>
							<th>Gross duration</th>
							<th>Net duration</th>
							<th>Aborted</th>
						</tr>
				</thead>
				<tbody>
						<ano:iterate name="tracedCall" property="elements" type="net.java.dev.moskito.webui.bean.TraceStepBean" id="traceStep" indexId="index">
					   	<%--
					   		<ano:equal name="traceStep" property="aborted" value="true"><tr class="stat_error" id="node-<ano:write name="traceStep" property="id"/>"></ano:equal>
							<ano:notEqual name="traceStep" property="aborted" value="true"><tr class="< %= ((index & 1) == 0 )? "even" : "odd" % >" id="node-<ano:write name="id"/>"></ano:notEqual> 
						--%>
						<tr class="child-of-node-<ano:write name="traceStep" property="parentId"/>" id="node-<ano:write name="traceStep" property="id"/>">
							<td width="1%"><a name="<ano:write name="traceStep" property="niceId"/>"><ano:write name="traceStep" property="niceId"/></a></td>
							<td onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 500)" onmouseout="UnTip()"><% for (int i=1; i<traceStep.getLayer(); i++){ %><%= EMPTY %><%}%><ano:equal name="traceStep" property="root" value="false"><%=IMG%></ano:equal><ano:write name="traceStep" property="call" filter="true"/></td>
							<td><ano:write name="traceStep" property="duration"/></td>
							<td><ano:write name="traceStep" property="timespent"/></td>
							<td><ano:equal name="traceStep" property="aborted" value="true">X</ano:equal></td>
						</tr>
						</ano:iterate>
				</tbody>
				</table>
				
		
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		<br/><br/>
		<!-- duplicates -->
		<ano:present name="dupStepBeansSize">
		<div class="clear"><!-- --></div>
		<div class="table_layout">
			<div class="top"><div><!-- --></div></div>
			<div class="in">			
				<div class="clear"><!-- --></div>
				<div class="table_itseft">
					<div class="top">
						<div class="left"><!-- --></div>
						<div class="right"><!-- --></div>
					</div>
					<div class="in">
				
					
					<table class="journeys_summary_table" cellpadding="0" cellspacing="0" width="100%">
					<thead>
							<tr class="stat_header">
								<th>Duplicate (<ano:write name="dupStepBeansSize"/>)</th>
                                <th><button class="deselect_all_journey_positions">Reset</button></th>
                                <th>Calls</th>
								<th>Positions</th>
								<th>Time / Duration</th>
							</tr>
					</thead>
					<tbody>
							<ano:iterate name="dupStepBeans" type="net.java.dev.moskito.webui.bean.JourneyCallDuplicateStepBean" id="dupStep" indexId="index">
							 <tr>
							 	<td><ano:write name="dupStep" property="call"/></td>
                                <td><input type="checkbox" class="select_current_row_positions_checkbox" value=""/></td>
                                <td><ano:write name="dupStep" property="numberOfCalls"/></td>
							 	<td style="white-space: normal;" class="journeys_summary_table_positions_list">
							 		<ano:iterate name="dupStep" property="positions" type="java.lang.String" id="position"><a href="#<ano:write name="position"/>"><ano:write name="position"/></a> </ano:iterate>
							 	</td>
							 	<td><ano:write name="dupStep" property="timespent"/> / <ano:write name="dupStep" property="duration"/></td>
							 </tr>
							</ano:iterate>
					</tbody>
					</table>
					
			
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
	</ano:present>
</div>
</body>
</html>
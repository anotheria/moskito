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
    <script type="text/javascript" src="../js/jquery.treeTable.js"></script>

    <script type="text/javascript">
        $(function() {
            var button = $('.expand_table_tree');

            // TREE TABLE
            $("#tree_table").treeTable();

            //REMOVE EXPAND/CLOSE BUTTONS table_tree_closed CLASS
            function removeClassTableTreeClosed() {
                button.removeClass('table_tree_closed').addClass('table_tree_opened').text('Hide');
            };

            // EXPAND-COLLAPSE TABLE TREE
            function expandTableTree() {
                var table = $('#tree_table'),
                    mainParent = $('#node-0'),
                    mainParentExpander = $('#node-0 a.expander'),
                    selectPositionCheckbox = $('.select_current_row_positions_checkbox');

                button.on('click', function() {
                    var $this = $(this),
                        selectedRows = table.find($('tr.tr_select_highlight'));

                    if ($this.hasClass('table_tree_closed')) {
                        table.expandAll();
                        removeClassTableTreeClosed();
                    } else {
                        table.collapseAll();
                        $this.removeClass('table_tree_opened').addClass('table_tree_closed').text('Expand');

                        selectedRows.each(function() {
                            $(this).removeClass('tr_select_highlight')
                        });

                        selectPositionCheckbox.each(function() {
                            $(this).prop('checked', '');
                        });
                    };
                });

                mainParentExpander.on('click', function(){

                    if (mainParent.hasClass('expanded')) {
                        removeClassTableTreeClosed();
                    } else {
                        button.removeClass('table_tree_opened').addClass('table_tree_closed').text('Expand');
                    };
                });
            };

            expandTableTree()



            // JOURNEYS SELECTION
            var positionCheckbox = $('.select_current_row_positions_checkbox');

            positionCheckbox.on('change', function(){
                var $this = $(this),
                    $thisTr = $this.parents('table.journeys_summary_table tr'),
                    PositionListLink = $thisTr.find($('td.journeys_summary_table_positions_list a')),
                    actionSelect = $this.prop('checked'),
                    nodes = '';

                PositionListLink.each(function(a){
                    var lineNumber = ($(this).text()),
                        nodenum = parseInt(lineNumber, 10),
                        nodeid = '#node-' + nodenum;

                    nodes += ","+nodeid;
                });

                var highlightedTr = $(nodes);

                if (actionSelect) {
                    highlightedTr.addClass('tr_select_highlight');
                    highlightedTr.each(function(){
                        $(this).reveal();
                    })

                    removeClassTableTreeClosed();
                }else {
                    highlightedTr.removeClass('tr_select_highlight')
                }

            });


            // DESELECT JOURNEYS BUTTON
            var deselectPositionsButton = $('.deselect_all_journey_positions');
            deselectPositionsButton.on('click', function(){
                positionCheckbox.each(function() {
                    var $this = $(this);

                    $this.prop('checked', '');
                    $this.change();
                })
            })


            // SCROLL FIX
            var summaryPositionListLik = $('td.journeys_summary_table_positions_list').find('a')

            summaryPositionListLik.on('click', function(e){
                var lineNumber = ($(this).text()),
                    linkWeMovingTo = $('a[name='+lineNumber+']'),
                    trWeMoveTo = linkWeMovingTo.parents('tr');

                trWeMoveTo.reveal();
                var linkCoordinates = linkWeMovingTo.offset(),
                    scrollToCoordinates = linkCoordinates.top - 50;

                removeClassTableTreeClosed();
                $(window).scrollTop(scrollToCoordinates);
                e.preventDefault();
            })
        })
    </script>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
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
						<tr class="journey_stat_header">
							<th><button class="expand_table_tree table_tree_closed">Expand</button>Call</th>
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
						<tr <ano:equal name="traceStep" property="parentAvailable" value="true">class="child-of-node-<ano:write name="traceStep" property="parentId"/>"</ano:equal> id="node-<ano:write name="traceStep" property="id"/>">
							<td class="journey_stat_call_td" width="25%"><a class="journey_stat_position" name="<ano:write name="traceStep" property="niceId"/>"><ano:write name="traceStep" property="niceId"/></a><span
                                onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 500)" onmouseout="UnTip()">
                                <ano:write name="traceStep" property="call" filter="true"/>
							</span></td>
							<%--<td onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 500)" onmouseout="UnTip()"><% for (int i=1; i<traceStep.getLayer(); i++){ %><%= EMPTY %><%}%><ano:equal name="traceStep" property="root" value="false"><%=IMG%></ano:equal><ano:write name="traceStep" property="call" filter="true"/></td>--%>
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
							 	<td class="journeys_summary_table_positions_list" style="white-space: normal;">
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
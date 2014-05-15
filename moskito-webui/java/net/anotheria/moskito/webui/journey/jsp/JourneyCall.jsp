<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" 
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>
<script type="text/javascript" src="../ext/jquery-tree-table/jquery.treeTable.min.js"></script>
<section id="main">
    <div class="content">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    <a href="mskShowJourneys">Journeys</a> :: <a href="mskShowJourney?pJourneyName=${journeyName}">${journeyName}</a>  :: ${tracedCall.name}
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>

            <p class="paddner">
                <span><ano:write name="tracedCall" property="created"/>&nbsp;&nbsp;<ano:write name="tracedCall" property="date"/> &nbsp;&nbsp;</span>
            </p>
            <div id="collapse2" class="box-content accordion-body collapse in">

                <table class="table table-striped table-tree tree">
                    <thead>
                    <tr>
                        <th><button class="btn btn-primary tree-expand">Expand</button></th>
                        <th>Call</th>
                        <th>Gross duration</th>
                        <th>Net duration</th>
                        <th>Aborted</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="tracedCall" property="elements" type="net.anotheria.moskito.webui.journey.api.TracedCallStepAO" id="traceStep">
                        <%--
                            <ano:equal name="traceStep" property="aborted" value="true"><tr class="stat_error" id="node-<ano:write name="traceStep" property="id"/>"></ano:equal>
                         <ano:notEqual name="traceStep" property="aborted" value="true"><tr class="< %= ((index & 1) == 0 )? "even" : "odd" % >" id="node-<ano:write name="id"/>"></ano:notEqual>
                     --%>
                        <tr data-level="${traceStep.level}">
                            <td><div><i class="minus">–</i><i class="plus">+</i><i class="vline"></i>${traceStep.niceId}</div></td>
                            <td><span onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 500)" onmouseout="UnTip()">
                                <ano:write name="traceStep" property="call" filter="true"/>
							</span></td>
                                <%--<td onmouseover="Tip('<ano:write name="traceStep" property="fullCall" filter="true"/>', WIDTH, 500)" onmouseout="UnTip()"><% for (int i=1; i<traceStep.getLayer(); i++){ %><%= EMPTY %><%}%><ano:equal name="traceStep" property="root" value="false"><%=IMG%></ano:equal><ano:write name="traceStep" property="call" filter="true"/></td>--%>
                            <td>${traceStep.duration}</td>
                            <td>${traceStep.timespent}</td>
                            <td><ano:equal name="traceStep" property="aborted" value="true">X</ano:equal></td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
<jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>
</section>
<!-- autoexpand tree -->
<script>
    $(document).ready(function() {
        $('.table').on('click','.tree-expand', function() {
            $("table.tree tr.collapsed").removeClass('collapsed').addClass('expanded');
            $("table.tree tr[style='display: none;']").css('display','table-row');
            $('.tree-expand').addClass('tree-collapse').removeClass('tree-expand').html('Collapse');
        });

        $('.table').on('click','.tree-collapse', function() {
            $("table.tree tr.expanded").removeClass('expanded').addClass('collapsed');
            $("table.tree tr[style='display: table-row;']").css('display','none');
            $('.tree-collapse').addClass('tree-expand').removeClass('tree-collapse').html('Expand');
        });
    });
</script>
</body>
</html>

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
								<ano:iterate name="dupStepBeans" type="net.anotheria.moskito.webui.journey.api.TracedCallDuplicateStepsAO" id="dupStep" indexId="index">
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
		</div>
	</ano:present>
</div>
</div>
</body>
</html>
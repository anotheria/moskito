<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Moskito Dashboard <ano:write name="pageTitle"/></title>
    <link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/jquery-ui-1.8.5.custom.min.js"></script>
<script type="text/javascript" src="../js/jquery.ui.core.js"></script>
<script type="text/javascript" src="../js/jquery.ui.mouse.min.js"></script>
<script type="text/javascript" src="../js/jquery.ui.selectable.min.js"></script>
<script type="text/javascript" src="../js/jquery.ui.sortable.min.js"></script>
<script type="text/javascript" src="../js/jquery.ui.widget.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/jscharts.js"></script>
<script type="text/javascript" src="../js/dashboardController.js"></script>
<script type="text/javascript" src="../js/chartController.js"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<!--
     Data for charts -->
<%--
    <script>
    <msk:iterate type="net.anotheria.moskito.webui.bean.GraphDataBean" 	id="graph" name="graphDatas">
        var <msk:write name="graph" property="jsVariableName"/>Caption = "<msk:write name="graph" property="caption"/>";
        var <msk:write name="graph" property="jsVariableName"/>Array = <msk:write name="graph" property="jsArrayValue"/>;
    </msk:iterate>

    </script>--%>

<div class="main">
    <select class="dashes">
        <option class="create">Create dashboard</option>
        <!--<option disabled selected="selected"></option>-->
        <ano:iterate name="dashboards" id="dashboard">
            <option <ano:equal name="dashboard" property="name" value="${selectedDashboardName}">selected="selected"</ano:equal>>${dashboard.name}</option>
        </ano:iterate>
    </select>

    <a href="#" class="edit dash">Edit</a>
    <input type="button" value="Create widget" class="create_wdgt" <ano:iF test="${!isCanAddWidget}">style="display:none;"</ano:iF> />

    <div class="clear"></div>

    <div class="draggable">
        <div class="widgets_left">
            <ano:iterate id="widgetLeft" name="widgetsLeft">
                <ano:define id="widget" name="widgetLeft" toScope="request"/>
                <jsp:include page="DashboardShowWidget.jsp" flush="false"/>
            </ano:iterate>
        </div>
        <div class="widgets_right">
            <ano:iterate id="widgetRight" name="widgetsRight">
                <ano:define id="widget" name="widgetRight" toScope="request"/>
                <jsp:include page="DashboardShowWidget.jsp" flush="false"/>
            </ano:iterate>
        </div>
    </div>
    <div class="clear"></div>
    <div class="generated">Generated at <ano:write name="timestampAsDate"/>&nbsp;&nbsp;|&nbsp;&nbsp;timestamp: <ano:write name="timestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp;Interval updated at: <ano:write name="currentIntervalUpdateTimestamp"/>&nbsp;&nbsp;|&nbsp;&nbsp; Interval age: <ano:write name="currentIntervalUpdateAge"/></div>


    <!-- Creating widget(table) overlay-->
    <div class="create_widget_overlay" style="display:none;">
    <form action="mskDashBoard" class="create_widget_form" method="post">
		<h2>Create widget</h2>
		<label for="name">Widget name:</label>
		<input type="text" name="widgetName" value="" id="name"/>

		<div class="widget_type">
			<input type="radio" id="t_table" name="widgetType" checked="checked"/><label for="t_table">Table widget</label>
			<input type="radio" id="t_chart" name="widgetType"/><label for="t_chart">Chart widget</label>
			<input type="radio" id="t_threshold" name="widgetType"/><label for="t_threshold">Threshold widget</label>
			<div class="clear"></div>
		</div>

		<div class="t_table">

		<div class="t_table_prod_group">
			<h3>Producer group</h3>
			<ul>
				<ano:iterate name="decorators" id="decorator" indexId="indexId">
					<ano:equal name="decorator" property="visibility" value="SHOW">
						<li class="${indexId == 0 ? " active" : ""}">
							<div class="top_l"></div>
							<div class="bot_l"></div>
							<a href="#" class="uncheck">
								<img src="<ano:write name="mskPathToImages" scope="application"/>close.png" alt="Uncheck" title="Uncheck"/>&nbsp;
							</a>
							<a href="#" class="${decorator.name}">${decorator.name}</a>
						</li>
					</ano:equal>
				</ano:iterate>
			</ul>
		</div>

		<div class="right_part">
		<div class="top_r"></div>
		<div class="bot_r"></div>
		<div class="t_table_val">
			<h3>Value</h3>
			<!--  -->
			<ano:iterate name="decorators" id="decorator" indexId="indexId">
				<ano:equal name="decorator" property="visibility" value="SHOW">
					<ul class="<ano:write name="decorator" property="name"/>_val" ${indexId != 0 ? "style='display:none;'" : ""}>
						<li><input type="checkbox" id="c_all" /><label>Select all</label></li>
						<ano:iterate name="decorator" property="captions" id="caption">
							<li><input type="checkbox" name="${decorator.name}_${caption.caption}"/><label>${caption.caption}</label></li>
						</ano:iterate>
					</ul>
				</ano:equal>
			</ano:iterate>
		</div>

		<div class="t_table_prod">

			<h3>Producer</h3>
			<ano:iterate name="decorators" id="decorator" indexId="indexId">
				<ano:equal name="decorator" property="visibility" value="SHOW">
					<ul class="${decorator.name}_prod" ${indexId != 0 ? "style='display:none;'" : ""}>
						<li><input type="checkbox" id="c_all"/><label>Select all</label></li>

						<ano:iterate name="decorator" property="producers" id="producer">
							<li><input type="checkbox" name="${producer.id}"/><label>${producer.id}</label></li>
						</ano:iterate>
					</ul>
				</ano:equal>
			</ano:iterate>
		</div>

		<div class="clear"></div>
		</div>
		<div class="clear"></div>
		</div>

		<div class="clear"></div>

		<div class="flr">
			<input type="submit" value="Create"/><span>&nbsp;&nbsp;or&nbsp;&nbsp;</span><a
				onclick="closeLightbox(); return false;"
				href="#">Cancel</a>
		</div>
    </form>
    </div>
    <!-- Creating widget(table) overlay END-->
</div>


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

                </div>
            </div>

        </div>
        <div class="box_bot">
            <div><!-- --></div>
            <span><!-- --></span>
        </div>
    </div>
</div>

<!-- Edit dashboard name overlay -->
<div class="edit_dash_overlay" style="display:none;">
    <form action="mskDashBoard" class="edit_dash_form" method="post">

        <h2>Edit dashboard name</h2>
        <label for="name">Name:</label>
        <input type="text" name="dashboard" value="" id="name"/>

        <div class="clear"></div>
        <div class="flr">
            <input type="submit" value="Rename"/><span>&nbsp;&nbsp;or&nbsp;&nbsp;</span>
			<a onclick="closeLightbox(); return false;"href="#">Cancel</a>
        </div>
    </form>
</div>
<!-- Edit dashboard name end -->

<!-- Create dashboard overlay -->
<div class="create_dash_overlay" style="display:none;">
    <form action="mskDashBoard" class="edit_dash_form" method="post">
        <h2>Create dashboard</h2>
        <label for="name">Name:</label>
        <input type="text" name="dashboard" value="" id="name"/>

        <div class="clear"></div>
        <div class="flr">
            <input type="submit" value="Create"/><span>&nbsp;&nbsp;or&nbsp;&nbsp;</span><a
                onclick="closeLightbox(); return false;"
                href="#">Cancel</a>
        </div>
    </form>
</div>
<!-- Create dash overlay end -->



<!-- Create pie chart for adding -->
<div class="create_widget_chart" style="display:none;">
<div class="widget_type">
    <label class="mr_5">Choose chart type:</label>
    <input type="radio" id="pie" name="chart_type" checked="checked"/><label for="pie">Pie</label>
    <input type="radio" id="bar" name="chart_type"/><label for="bar">Bar</label>
    <input type="radio" id="timeline" name="chart_type"/><label for="timeline">Timeline</label>

    <div class="clear"></div>
</div>

<div class="clear"></div>
<table cellpadding="0" cellspacing="0" border="0" class="t_pie_bar">
<tbody>
<tr>

<%--Chart: write out producer groups BEGIN--%>
<td class="t_pie_bar_prod_group">
    <h3>Producer group</h3>
    <ul>
		<ano:iterate name="decorators" id="decorator" indexId="indexId">
			<ano:equal name="decorator" property="visibility" value="SHOW">
				<li class="${indexId == 0 ? " active": ""}">
					<a href="#" class="${decorator.name}">${decorator.name}</a>
				</li>
			</ano:equal>
		</ano:iterate>
    </ul>
</td>
<%--Chart: write out producer groups END--%>

<%--Chart: write out value BEGIN--%>
<td class="t_pie_bar_val">

    <h3>Value</h3>
	<ano:iterate name="decorators" id="decorator" indexId="indexId">
		<ano:equal name="decorator" property="visibility" value="SHOW">
			<ul class="${decorator.name}_val" ${indexId != 0 ? "style='display:none;'" : ""}>
				<li><input type="checkbox" id="c_all" /><label>Select all</label></li>
				<ano:iterate name="decorator" property="captions" id="caption">
					<li><input type="checkbox" name="${decorator.name}_${caption.caption}"/><label>${caption.caption}</label></li>
				</ano:iterate>
			</ul>
		</ano:equal>
	</ano:iterate>
</td>
<%--Chart: write out value END--%>

<%--Chart: write out producer BEGIN--%>
<td class="t_pie_bar_prod">

    <h3>Producer</h3>
    <ul class="filt_prod">
        <li><input type="checkbox" id="c_all" checked="checked"/><label>Select all</label></li>
        <li><input type="checkbox" checked="checked"/><label>DomainFilter</label></li>

        <li><input type="checkbox" checked="checked"/><label>MoskitoUI</label></li>
        <li><input type="checkbox" checked="checked"/><label>RefererFilter</label></li>
        <li><input type="checkbox" checked="checked"/><label>RequestURIFilter</label></li>
        <li><input type="checkbox" checked="checked"/><label>UserAgentFilter</label></li>
    </ul>
    <ul class="service_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>

        <li><input type="checkbox"/><label>AuthorizationServiceImpl</label></li>
        <li><input type="checkbox"/><label>CommentServiceImpl</label></li>
    </ul>
    <ul class="storage_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>storage-1</label></li>
    </ul>

    <ul class="servlet_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>net.anotheria.moskitodemoservlet.SimpleServlet</label></li>
    </ul>
    <ul class="action_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>gbook.ShowCommentsAction</label></li>

    </ul>
    <ul class="httpsession_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>SessionCount</label></li>
    </ul>
    <ul class="virtualmemorypool_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>

        <li><input type="checkbox"/><label>Heap memory</label></li>
        <li><input type="checkbox"/><label>Non-heap memory</label></li>
    </ul>
    <ul class="memory_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>JavaRuntimeFree</label></li>
        <li><input type="checkbox"/><label>JavaRuntimeMax</label></li>

        <li><input type="checkbox"/><label>JavaRuntimeTotal</label></li>
    </ul>
    <ul class="memorypool_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>MemoryPool-Code Cache-NonHeap</label></li>
        <li><input type="checkbox"/><label>MemoryPool-PS Eden Space-Heap</label></li>
        <li><input type="checkbox"/><label>MemoryPool-PS Old Gen-Heap</label></li>

        <li><input type="checkbox"/><label>MemoryPool-PS Perm Gen-NonHeap</label></li>
        <li><input type="checkbox"/><label>MemoryPool-PS Survivor Space-Heap</label></li>
    </ul>
    <ul class="threadcount_prod" style="display:none;">
        <li><input type="checkbox" id="c_all"/><label>Select all</label></li>
        <li><input type="checkbox"/><label>ThreadCount</label></li>
    </ul>

</td>
<%--Chart: write out producer BEGIN--%>

</tr>
</tbody>
</table>

</div>
<!-- Create pie chart for adding end-->

<!-- Create dash overlay timeline chart-->
<div class="create_widget_chart_timeline" style="display:none;">
    <div class="create_widget_chart_timeline_inner chart_overlay">
        <div class="widget_type">
            <label class="mr_5">Choose chart type:</label>

            <input type="radio" name="chart_type" id="pie"><label for="pie">Pie</label>
            <input type="radio" name="chart_type" id="bar"><label for="bar">Bar</label>
            <input type="radio" name="chart_type" id="timeline" checked="checked"><label for="timeline">Timeline</label>

            <div class="clear"></div>
        </div>
        <div class="widget_type"><label class="mr_5">Update interval:</label><select id="interval">

            <option>snapshot</option>
            <option>default</option>
            <option>1m</option>
            <option>5m</option>
            <option>15m</option>
            <option>1h</option>

            <option>12h</option>
            <option>1d</option>
        </select></div>
        <div class="clear"></div>
        <div class="widget_type">
            <div class="fll"><label class="mr_5">Producer:</label><select id="producer_sel">

            </select></div>
            <div class="fll"><label class="mr_5">Stats:</label><select id="stats_sel"></select></div>
            <div class="fll"><label class="mr_5">Value:</label><select id="value_sel"></select></div>
            <div class="fll"><input type="submit" class="add" value="Add" style=""></div>
            <div class="clear"></div>

        </div>
        <div class="table_layout">
            <div class="top">
                <div><!-- --></div>
            </div>
            <div class="in">
                <div class="clear"><!-- --></div>
                <div class="table_itseft">
                    <div class="top">

                        <div class="left"><!-- --></div>
                        <div class="right"><!-- --></div>
                    </div>
                    <div class="in" style="">
                        <table cellspacing="0" cellpadding="0" width="100%" style="">
                            <thead>
                            <tr class="edd">
                                <th>Producer</th>

                                <th>Stats</th>
                                <th>Value</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
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
            <div class="bot">

                <div><!-- --></div>
            </div>
        </div>
    </div>
</div>
<!-- Create dash overlay timeline chart end-->
<!-- Create dash overlay thresholds -->
<div class="create_widget_threshold" style="display:none;">
    <div class="create_widget_threshold_inner">
        <h3>Choose thresholds</h3>
        <ul class="thresholds_list">

            <li><input type="checkbox" checked="checked" id="c_all"><label>Select all</label></li>
            <li><input type="checkbox" checked="checked"><label>GuardedServiceAVG</label></li>
            <li><input type="checkbox" checked="checked"><label>Guestbook</label></li>
            <li><input type="checkbox" checked="checked"><label>GuardedService TotalRequest</label></li>
        </ul>
    </div>
</div>
<!-- Create dash overlay thresholds end -->

<script type="text/javascript">
    function odev(el) {
        var i = 0;
        el.find('tr').each(function() {
            $(this).removeClass();
            if (i % 2) {
                $(this).addClass('even');
            } else {
                $(this).addClass('edd');
            }
            i++;
        });
    }


    $('.chart_overlay .add').live('click', function() {
        var pr_s = $('.chart_overlay #producer_sel').val();
        var st_s = $('.chart_overlay #stats_sel').val();
        var val_s = $('.chart_overlay #value_sel').val();
        $('.chart_overlay table tbody').append("<tr><td>" + pr_s + "</td><td>" + st_s + "</td><td>" + val_s + "</td><td><input type='button' value='delete' class='delete_btn'/></td></tr>");
        odev($('.chart_overlay table'));
        return false;
    });

    $('.chart_overlay table tbody .delete_btn').live('click', function() {
        $(this).parent().parent().remove();
        odev($('.chart_overlay table'));
        return false;
    });

    //chart functions
    google.load('visualization', '1', {packages: ['columnchart']});
    $(function() {
        function drawBarChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Producer');
            data.addColumn('number', 'val');
            data.addRows([
                ['UserAgentFilter',74],
                ['UserAgentFilter',0],
                ['moskitoUI',2990],
                ['moskitoUI',0],
                ['RefererFilter',25],
                ['RefererFilter',0],
                ['RequestURIFilter',96],
                ['RequestURIFilter',0],
                ['DomainFilter',131],
                ['DomainFilter',0]
            ]);
            var options = {width: 450, height: 240, title: 'Filter - Time'};
            var chartInfo = {
                params: '',
                container: 'barChart',
                type: 'BarChart',
                data: data,
                options: options,
                maximized: false
            };
            addDragStopListener(chartInfo);
            drawChart(chartInfo);
        }

        function drawPieChart() {
            var data = new google.visualization.DataTable();
            data.addColumn('string', 'Producer');
            data.addColumn('number', 'val');
            data.addRows([
                ['UserAgentFilter',74],
                ['UserAgentFilter',0],
                ['moskitoUI',2990],
                ['moskitoUI',0],
                ['RefererFilter',25],
                ['RefererFilter',0],
                ['RequestURIFilter',96],
                ['RequestURIFilter',0],
                ['DomainFilter',131],
                ['DomainFilter',0]
            ]);
            var options = {width: 450, height: 240, title: 'Filter - Time'};
            var chartInfo = {
                params: '',
                container: 'pieChart',
                type: 'PieChart',
                data: data,
                options: options,
                maximized: false
            };
            addDragStopListener(chartInfo);
            drawChart(chartInfo);
        }

        function drawChart(chartInfo) {
            document.getElementById(chartInfo.container + '_container').chartInfo = chartInfo;

            google.visualization.drawChart({
                "containerId": chartInfo.container,
                dataTable: chartInfo.data/*+chartInfo.params*/,
                "chartType": chartInfo.type,
                "options": chartInfo.options,
                "refreshInterval": 60
            });
        }

        function addDragStopListener(chartInfo) {
            $(".widgets_left").sortable({
                stop: function(event, ui) {
                    drawChart(ui.item[0].chartInfo);
                }
            });
            $(".widgets_right").sortable({
                stop: function(event, ui) {
                    drawChart(ui.item[0].chartInfo);
                }
            });
        }

        drawBarChart();
        drawPieChart();
    });


</script>
</body>
</html>
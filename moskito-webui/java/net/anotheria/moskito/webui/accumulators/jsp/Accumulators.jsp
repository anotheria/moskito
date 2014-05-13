<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MoSKito Accumulators</title>
    <link rel="stylesheet" type="text/css" href="../css/jquery.jqplot.css">
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="//www.google.com/jsapi"></script>
<!-- jqplot core + plugins -->
<script type="text/javascript" src="../js/charts/jqplot/jquery.jqplot.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.cursor.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.highlighter.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.enhancedLegendRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.dateAxisRenderer.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.donutRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.barRenderer.min.js"></script>

<script type="text/javascript" src="../js/charts/chartEngineIniter.js"></script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>
<%--<script>    // temp
    window.autoReloadInterval = <ano: autoreload value>;
</script>--%>

<script type="text/javascript">
    $(function() {
        function deleteAccumulator() {
            var $del = $('a.del');

            $del.on('click', function(e) {
                var $this = $(this),
                    deleteLink = $this.attr('href'),
                    currentRow = $this.parents('table.accumulators_table tr'),
                    currentAcc = currentRow.find($('.acc_name')).text();
                console.log(deleteLink);

                var el = $('.popup_dialog'),
                    bg = $('.black_bg'),
                    close = $(".popup_box_close"),
                    accSpan  = $('.popup_box_item_name'),
                    confirm = $('.popup_box_confirm'),
                    cancel = $('.popup_box_cancel');

                function popupDialog() {


                    el.show();

                    $('.popup_box').width($('.popup_box_message').width());

                    var wid = el.find('.popup_box').width(),
                        box = el.find('.popup_box'),
                        hig = el.find('.popup_box').height();

                    box.css('left', '50%');
                    box.css('margin-left', -wid / 2);
                    box.css('top', '50%');
                    box.css('margin-top', -hig / 2);
                    box.css('position', 'fixed');

                    accSpan.text(currentAcc);

                    confirm.on('click', function() {
                        window.location = deleteLink;
                    });

                    bg.on('click', function() {
                        el.hide();
                    });

                    close.on('click', function() {
                        el.hide();
                    });

                    cancel.on('click', function() {
                        el.hide();
                    });

                    return false
                }

                popupDialog($this);

                e.preventDefault();
            })
        }

        function manageAutoreload() {
            var $toggleButton = $('.autoreload_toggle_button'),
                $settingsBox = $('.autoreload_settings'),
                $triangle = $('.autoreload_toggle_triangle');

            $toggleButton.on('click', function() {
                $triangle.toggleClass('autoreload_toggle_triangle_right');
                $settingsBox.slideToggle();
            });

            var $form = $('form.autoreload_settings'),
                $setButton = $('.autoreload_set_button'),
                $label = $('form.autoreload_settings label'),
                $resetButton = $('.autoreload_reset_button'),
                autoreloadInterval = $('.autoreload_current_interval_data').text(),
                autoreloadValue = parseInt(autoreloadInterval, 10),
                linkToCurrentPage = $('.linkToCurrentPage').text();


            $setButton.on('click', function() {
                var n = $('.autoreload_minutes_settings_input').val(),
                       newUrl = linkToCurrentPage+'&pReloadInterval='+n;

                if(!isNaN(parseInt(n,10)) && isFinite(n) && (n > 0)){
                    window.location = newUrl;
                }else{
                    $label.text('\u002AType number from 1 to 9999 to set intermal in minutes:');
                    $label.css({
                        'background':'#F7D9D9',
                        'display':'block',
                        'padding':'4px 6px',
                        'border-radius':'3px'
                    });
                }
                return false
            });

            $resetButton.on('click', function() {
                newUrl = linkToCurrentPage+'&pReloadInterval=OFF';
                window.location = newUrl;
                return false
            });

            if(autoreloadValue) {
                setTimeout('window.location = window.location', (autoreloadValue * 60000))
            }
        }

        deleteAccumulator();
        manageAutoreload();
    })
</script>

<%-- this is used for a single accumulator --%>
<ano:present name="data">
<script type="text/javascript">
	var data = [<ano:iterate name="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>]; 
</script>
</ano:present>
<%-- this is used for a multi accumulator selection--%>
<ano:present name="singleGraphData">
<script type="text/javascript">
    var multipleGraphData = [];
	<ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">
        multipleGraphData.push([<ano:iterate name="singleGraph" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>])
		//var singleGraphData<ano:write name="singleGraph" property="nameForJS"/> = [<ano:iterate name="singleGraph" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value"/></ano:iterate>] ;
	</ano:iterate>
</script>
</ano:present>

<div class="main">
	<div class="clear"><!-- --></div>
	<!-- chart section -->
	<ano:present name="data">
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<ano:notPresent name="multiple_set">
		<div class="in">
			<h2><span>Chart for <ano:iterate name="accNames" type="java.lang.String" id="name"><ano:write name="name"/> </ano:iterate></span></h2><a class="refresh" href="#"></a>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div id="chart_accum"></div>
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		</ano:notPresent>
		<ano:present name="multiple_set">
		<ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">
		<div class="in">
			<h2><span>Chart for <ano:write name="singleGraph" property="name"/></span></h2><a class="refresh" href="#"></a>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div id="chart_accum<ano:write name="singleGraph" property="nameForJS"/>"></div>
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		</ano:iterate>
		</ano:present>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	</ano:present>
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>Accumulators</span></h2>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div class="scroller_x">
					<form action="" method="get">
						<table class="accumulators_table" cellpadding="0" cellspacing="0" width="100%">
							<thead>
							<tr>
								<th></th>
								<th>Name</th>
								<th>Path</th>
								<th>Values</th>
								<th>Last Timestamp</th>
								<th>&nbsp;</th>
                                <th>&nbsp;</th>
                            </tr>
							</thead>
							<tbody>
							<ano:iterate name="accumulators" type="net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO" id="accumulator" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><input type="checkbox" name="id_<ano:write name="accumulator" property="id"/>" value="set" <ano:present name="<%=\"id_\"+accumulator.getId()+\"_set\"%>">checked="checked"</ano:present>/></td>
									<td><a class="acc_name" href="?id_<ano:write name="accumulator" property="id"/>=set"><ano:write name="accumulator" property="name"/></a></td>
									<td><ano:write name="accumulator" property="path"/></td>
									<td><ano:write name="accumulator" property="numberOfValues"/></td>
									<td><ano:write name="accumulator" property="lastValueTimestamp"/></td>
									<td><a href="mskAccumulator?pId=<ano:write name="accumulator" property="id"/>" class="zoom"></a></td>
                                    <td><a href="mskAccumulatorDelete?pId=<ano:write name="accumulator" property="id"/>" class="del"></a></td>
                                </tr>
							</ano:iterate>
								<tr>
									<td colspan="5">
										<input class="accumulators_submit_button" type="submit" value="Submit"/>
										&nbsp;(Mode:&nbsp;
											<input type="radio" name="mode" value="combined" <ano:present name="combined_set">checked="checked"</ano:present>/>&nbsp;combine
											<input type="radio" name="mode" value="normalized" <ano:present name="normalized_set">checked="checked"</ano:present>/>&nbsp;combine and normalize
											<input type="radio" name="mode" value="multiple" <ano:present name="multiple_set">checked="checked"</ano:present>/>&nbsp;multiple graphs
										)
										&nbsp;(Type:&nbsp;
											<input type="radio" name="type" value="LineChart" <ano:equal name="type" value="LineChart">checked="checked"</ano:equal	>/>&nbsp;Line
											<input type="radio" name="type" value="PieChart" <ano:equal name="type" value="PieChart">checked="checked"</ano:equal>/>&nbsp;Pie
											<input type="radio" name="type" value="BarChart" <ano:equal name="type" value="BarChart">checked="checked"</ano:equal>/>&nbsp;Bar
											<input type="radio" name="type" value="ColumnChart" <ano:equal name="type" value="ColumnChart">checked="checked"</ano:equal>/>&nbsp;Column
										)										
										<input type="hidden" name="normalizeBase" value="<ano:write name="normalizeBase"/>"/>
										<input type="hidden" name="maxValues" value="<ano:write name="maxValues"/>"/>
									</td>
								</tr>
							</tbody>
						</table>
					</form>						
						</div>
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
	<div class="clear"><!-- --></div>

	<jsp:include page="../../shared/jsp/Footer.jsp" flush="false" />

    <div class="popup_dialog" style="display:none;">
    	<div class="black_bg"><!-- --></div>
    	<div class="popup_box">
   			<a class="popup_box_close"><!-- --></a>
            <p class='popup_box_message acc_popup_box_message'>
                Are you sure that you want to delete Accumulator <span class="popup_box_item_name"></span>
            </p>
            <div class="popup_button_wrapper">
                <button class='popup_box_confirm popup_button'>Ok</button>
                <button class='popup_box_cancel popup_button'>Cancel</button>
            </div>
    	</div>
    </div>
    <div class="linkToCurrentPage"><ano:write name="linkToCurrentPage"/></div>
<ano:present name="data">
<script type="text/javascript">
    var chartEngineName = '<ano:write name="chartEngine"/>' || 'GOOGLE_CHART_API';

    // Many charts
    if ('multipleGraphData' in window){
        var names = '<ano:write name="accNames"/>'.slice(1, -1).split(', ');
        multipleGraphData.forEach(function(graphData, index){
            var chartParams = {
                container: ('chart_accum' + names[index]).split('-').join('_').split(' ').join('_'),
                names: [names[index]],
                data: graphData,
                type: '<ano:write name="type"/>',
                title: names[index]
            };

            chartEngineIniter[chartEngineName](chartParams);
        });

    }
    // One chart with one or more lines
    else{
        var names = ('<ano:write name="singleGraph" property="name"/>' && ['<ano:write name="singleGraph" property="name"/>']) || '<ano:write name="accNames"/>'.slice(1, -1).split(', ');

        var chartParams = {
            container: 'chart_accum<ano:write name="singleGraph" property="nameForJS"/>',
            names: names,
            data: data,
            type: '<ano:write name="type"/>',
            title: ''
        };

        chartEngineIniter[chartEngineName](chartParams);
    }


	$('.refresh').click(function() {
		location.reload(true);
	});


</script>
</ano:present>
</div>
</body>
</html>  

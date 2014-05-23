<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<script type="text/javascript" src="//www.google.com/jsapi"></script>
<!-- jqplot core + plugins -->
<script type="text/javascript" src="../moskito/js/charts/jqplot/jquery.jqplot.js"></script>
<script type="text/javascript" src="../moskito/js/charts/jqplot/jqplot.cursor.js"></script>
<script type="text/javascript" src="../moskito/js/charts/jqplot/jqplot.highlighter.js"></script>
<script type="text/javascript" src="../moskito/js/charts/jqplot/jqplot.dateAxisRenderer.js"></script>

<script type="text/javascript" src="../moskito/js/charts/chartEngineIniter.js"></script>

<section id="main">
    <div class="content">

        <%-- this is used for a single accumulator --%>
        <ano:present name="data">
            <script type="text/javascript">
                var data = [<ano:iterate name="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>${value}</ano:iterate>];
            </script>
        </ano:present>
        <%-- this is used for a multi accumulator selection--%>
        <ano:present name="singleGraphData">
            <script type="text/javascript">
                var multipleGraphData = [];
                <ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">
                multipleGraphData.push([<ano:iterate name="singleGraph" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>${value}</ano:iterate>])
                //var singleGraphData<ano:write name="singleGraph" property="nameForJS"/> = [<ano:iterate name="singleGraph" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>${value}</ano:iterate>] ;
                </ano:iterate>
            </script>
        </ano:present>

        <%-- single chart box with  charts --%>
        <ano:notPresent name="multiple_set">
            <div class="box">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-chart"><i class="fa fa-caret-right"></i></a>
                    <h3 class="pull-left">
                        Chart for <ano:iterate name="accNames" type="java.lang.String" id="name">${name}</ano:iterate>
                    </h3>
                    <div class="box-right-nav">
                        <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
                    </div>
                </div>
                <div id="collapse-chart" class="box-content accordion-body collapse in">
                    <div class="paddner"><div id="chart_accum${singleGraph.nameForJS}"></div></div>
                </div>
            </div>
        </ano:notPresent>
        <%-- /single chart box --%>

        <%-- Chart boxes for multiple charts --%>
        <ano:present name="data">
            <ano:present name="multiple_set">
                <ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">

                    <div class="box">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-chart-${singleGraph.nameForJS}"><i class="fa fa-caret-right"></i></a>
                            <h3 class="pull-left">
                                Chart for ${singleGraph.name}
                            </h3>
                            <div class="box-right-nav">
                                <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
                            </div>
                        </div>
                        <div id="collapse-chart-${singleGraph.nameForJS}" class="box-content accordion-body collapse in">
                            <div class="paddner"><div id="chart_accum${singleGraph.nameForJS}"></div></div>
                        </div>
                    </div>
                </ano:iterate>
            </ano:present>
        </ano:present>

        <ano:present name="data">
            <script type="text/javascript">
                var chartEngineName = '${chartEngine}' || 'GOOGLE_CHART_API';

                // Many charts
                if ('multipleGraphData' in window){
                    var names = '${accNames}'.slice(1, -1).split(', ');
                    multipleGraphData.forEach(function(graphData, index){
                        var chartParams = {
                            container: ('chart_accum' + names[index]).split('-').join('_').split(' ').join('_'),
                            names: [names[index]],
                            data: graphData,
                            type: 'LineChart',
                            title: names[index]
                        };

                        chartEngineIniter[chartEngineName](chartParams);
                    });

                }
                // One chart with one or more lines
                else{
                    var names = ('${singleGraph.name}' && ['${singleGraph.name}']) || '${accNames}'.slice(1, -1).split(', ');

                    var chartParams = {
                        container: 'chart_accum${singleGraph.nameForJS}',
                        names: names,
                        data: data,
                        type: 'LineChart',
                        title: ''
                    };

                    chartEngineIniter[chartEngineName](chartParams);
                }


                $('.refresh').click(function() {
                    location.reload(true);
                });


            </script>
        </ano:present>

        <div class="box">
            <form action="" method="get">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapselist"><i class="fa fa-caret-right"></i></a>
                    <h3 class="pull-left">
                        Accumulators
                    </h3>
                    <div class="box-right-nav">
                        <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                    </div>
                </div>

                <div id="collapselist" class="box-content accordion-body collapse in">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Name<i class="fa fa-caret-down"></i></th>
                            <th>Path <i class="fa fa-caret-down"></i></th>
                            <th>Values <i class="fa fa-caret-down"></i></th>
                            <th>Last Timestamp <i class="fa fa-caret-down"></i></th>
                            <th class="th-actions"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="accumulators" type="net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO" id="accumulator" indexId="index">
                            <tr>
                                <td><input type="checkbox" class="checktr" name="id_${accumulator.id}" value="set" <ano:present name="<%=\"id_\"+accumulator.getId()+\"_set\"%>">checked="checked"</ano:present>/></td>
                                <td><a href="?id_${accumulator.id}=set">${accumulator.name}</a></td>
                                <td>${accumulator.path}</td>
                                <td>${accumulator.numberOfValues}</td>
                                <td>${accumulator.lastValueTimestamp}</td>
                                <td class="actions-links">
                                    <a href="#mskAccumulatorDelete" data-toggle="modal" data-target="#mskAccumulatorDelete" data-id="${accumulator.id}" class="action-icon delete-icon tooltip-bottom" title="Delete"><i class="fa fa-ban"></i></a>
                                    <a href="?id_${accumulator.id}=set" class="action-icon show-icon tooltip-bottom" title="Show"><i class="fa fa-search-plus"></i></a>
                                </td>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>

                <div class="box-footer fixed">
                    <div class="fixed-box">
                        <div class="form-inline">
                            <div class="form-group btns">
                                <button class="btn btn-default btn-submit">Submit</button>
                                <button class="btn btn-default btn-clear hide">Clear</button>
                            </div>
                            <div class="form-group">
                                (Mode:
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" checked="checked" value="combined" name="mode"> combine
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="normalized" name="mode"> combine and normalize
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="multiple" name="mode"> multiple graphs
                                </label>
                            </div>
                            <div class="form-group">
                                )
                            </div>

                            <div class="form-group">
                                (Type:&nbsp;
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" checked="checked" value="LineChart" name="type">&nbsp;Line
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="PieChart" name="type">&nbsp;Pie
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="BarChart" name="type">&nbsp;Bar
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="ColumnChart" name="type">&nbsp;Column
                                </label>
                            </div>
                            <div class="form-group">
                                )
                            </div>
                            <input type="hidden" value="100" name="normalizeBase">
                            <input type="hidden" value="200" name="maxValues">
                        </div>
                     </div>
                </div>
            </form>
        </div>

    </div>

    <div class="modal fade modal-danger" id="mskAccumulatorDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Delete this Accumulator?</h4>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <a href="#" class="btn btn-danger accumulator-delete-confirm">Delete</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

    <script type="text/javascript">
        $('.actions-links').on('click','.delete-icon', function() {
            var dataid = $(this).attr('data-id');
            $('.accumulator-delete-confirm').attr("href", "mskAccumulatorDelete?pId=" + dataid);
        });

        $(window).scroll(function(){
            if ($(document).scrollTop() >= $(document).height() - $(window).height() - 180) {
                $('.box-footer').removeClass('fixed');
            } else if($(document).scrollTop() < $(document).height() - $(window).height()){
                $('.box-footer').addClass('fixed');
            }
        });//scroll

        $('.checktr:checked').closest('tr').addClass('checked');
        if($('.checktr').is(':checked')) {
            $('.fixed-box .btn-submit').addClass('btn-success');
            $('.fixed-box .btn-clear').removeClass('hide');
        }

        $('.fixed-box .btn-clear').click(function() {
            $('.table tr').removeClass('checked');
            $('.checktr').prop('checked', false);
            $(this).addClass('hide');
        });

        $('.table tr')
            .filter(':has(:checkbox:checked)')
            .addClass('checked')
            .end()
            .click(function(event) {
                $(this).toggleClass('checked');
                if (event.target.type !== 'checkbox') {
                    $(':checkbox', this).prop('checked', function() {
                        return !this.checked;
                    });
                }
                if ($('.checktr').is(':checked')) {
                    $('.fixed-box .btn-submit').addClass('btn-success');
                    $('.fixed-box .btn-clear').removeClass('hide');
                }
                else {
                    $('.fixed-box .btn-submit').removeClass('btn-success');
                    $('.fixed-box .btn-clear').addClass('hide');
                }
            });
    </script>

</section>
</body>
</html>


<%-- OLD




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


<div class="main">
	<div class="clear"><!-- --></div>
	<!-- chart section -->

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

</div>
</body>
</html>  
                    --%>
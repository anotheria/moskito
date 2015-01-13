<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <div class="content">

    <ano:present name="data">
        <%-- this data is used for a single (combined or combined&normalized) chart --%>
        <ano:notPresent name="multiple_set">
            <script type="text/javascript">
                var data = [
                    <ano:iterate name="data" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO" id="value" indexId="i">
                        <ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
                    </ano:iterate>
                ];
            </script>
        </ano:notPresent>
        <%-- this data is used for multiple charts --%>
        <ano:present name="multiple_set">
            <ano:present name="singleGraphData">
                <script type="text/javascript">
                    var multipleGraphData = [];
                    <ano:iterate name="singleGraphData" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO" id="singleGraph">
                    multipleGraphData.push([
                        <ano:iterate name="singleGraph" property="data" id="value" indexId="i">
                            <ano:notEqual name="i" value="0">,</ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
                        </ano:iterate>
                    ]);
                    </ano:iterate>
                </script>
            </ano:present>
        </ano:present>

        <%-- single chart box with charts --%>
            <ano:notPresent name="multiple_set">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-chart"><i class="fa fa-caret-right"></i></a>
                        <h3 class="pull-left">
                            Combined charts
                        </h3>
                        <div class="box-right-nav">
                            <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
                        </div>
                    </div>
                    <div id="collapse-chart" class="box-content accordion-body collapse in">
                        <div class="paddner"><div id="chart_accum${singleGraph.nameForJS}" class="accumulator-chart"></div></div>
                    </div>
                </div>
            </ano:notPresent>
        <%-- /single chart box --%>

        <%-- Chart boxes for multiple charts --%>
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
                            <div class="paddner"><div id="chart_accum${singleGraph.nameForJS}" class="accumulator-chart"></div></div>
                        </div>
                    </div>
                </ano:iterate>
            </ano:present>
            <%-- /charts' boxes --%>

            <script type="text/javascript">
                var chartEngineName = '${chartEngine}' || 'GOOGLE_CHART_API';

                // Many charts
                if ('multipleGraphData' in window){
                    var names = '${accNames}'.slice(1, -1).split(', ');
                    var containerSelectors = $('.accumulator-chart').map(function(){
                        return $(this).attr("id");
                    });

                    multipleGraphData.forEach(function(graphData, index){
                        var chartParams = {
                            container: containerSelectors[index],
                            names: [names[index]],
                            data: graphData,
                            type: '<ano:write name="type"/>',
                            title: names[index],
                            dataType: 'datetime'
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
                        type: '<ano:write name="type"/>',
                        title: '',
                        dataType: 'datetime'
                    };

                    chartEngineIniter[chartEngineName](chartParams);
                }


                $('.refresh').click(function() {
                    location.reload(true);
                });


            </script>
        </ano:present>

        <!-- selections of accumulators -->
        <div class="box">
            <form action="" method="get">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapselist2"><i class="fa fa-caret-right"></i></a>
                    <h3 class="pull-left">
                        Set of accumulators
                    </h3>
                    <div class="box-right-nav">
                        <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                    </div>
                </div>
                <div id="collapselist2" class="box-content accordion-body collapse in">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th>Name<i class="fa fa-caret-down"></i></th>
                            <th>Link <i class="fa fa-caret-down"></i></th>
                            <th class="th-actions"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach var="accumulator" items="${acc}">
                            <td>${accumulator.key}</td>
                            <td><a href="${accumulator.value}">${accumulator.value}</a></td>
                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </form>
        </div>

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

                            <%--<div class="form-group">
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
                            </div>--%>
                            <input type="hidden" value="LineChart" name="type">
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
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<div class="modal fade" id="chart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Title</h4>
            </div>
            <div class="modal-body">
                <div id="chart_div" style="width: ${config.producerChartWidth}px; height: ${config.producerChartHeight}px;"></div>
            </div>
            <div class="modal-footer">
                <div class="text-center">
                    <ul class="switcher">
                        <li><i title="" class="chart-icon pie_chart"></i></li>
                        <li><input type="checkbox" class="js-switch" /></li>
                        <li><i title="" class="chart-column-icon bar_chart"></i></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var chartParams,
            chartEngineName = '<ano:write name="chartEngine"/>' || 'JQPLOT';

    var $charSwitcher = $('ul.switcher input.js-switch');

    $('#chart').find('.modal-dialog').css("width", ${config.producerChartWidth}+40);//modal body padding 20px left+right

    $('.chart-icon').click(function() {
        var that = $(this);
        var $chart = $('#chart');
        chartParams = {
            container: 'chart_div',
            names: [eval(that.parent('.table-column').find('input').val()+'Caption')],
            data: eval(that.parent('.table-column').find('input').val()+'Array'),
            type: 'PieChart',
            title: '',
            width: ${config.producerChartWidth},
            height: ${config.producerChartHeight}
        };
        $chart.find('.modal-title').text(eval(that.parent('.table-column').find('input').val()+'Caption'));
        chartEngineIniter[chartEngineName](chartParams);
        $chart.modal('show');
    });

    $charSwitcher.on('change', function(){
        if ($(this).is(':checked')){

            chartParams.type = 'ColumnChart';
            chartEngineIniter[chartEngineName](chartParams);
        } else {

            chartParams.type = 'PieChart';
            chartEngineIniter[chartEngineName](chartParams);
        }
    });
</script>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <ano:equal name="newAccumulatorAdded" value="true">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Accumulator <ano:write name="newAccumulatorName"/> added!
        </div>
    </ano:equal>
    <div class="content">

        <ano:present name="data">
            <%-- this data is used for a single (combined or combined&normalized) chart --%>
            <ano:notPresent name="multiple_set">
                <script type="text/javascript">
                    var data = [
                        <ano:iterate name="data" type="net.anotheria.moskito.webui.accumulators.api.AccumulatedValueAO" id="value" indexId="i">
                        <ano:notEqual name="i" value="0">, </ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
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
                            <ano:notEqual name="i" value="0">, </ano:notEqual><ano:write name="value" property="JSONWithNumericTimestamp"/>
                            </ano:iterate>
                        ]);
                        </ano:iterate>
                    </script>
                </ano:present>
            </ano:present>

            <%-- accumulators colors data --%>
            <ano:present name="accumulatorsColors">
                <script type="text/javascript">
                    var accumulatorsColors = <ano:write name="accumulatorsColors"/>;
                </script>
            </ano:present>

            <%-- thresholds data --%>
            <ano:present name="thresholds">
                <script type="text/javascript">
                     var thresholds = [];
                     <ano:iterate name="thresholds" type="java.util.Map.Entry" id="threshold">
                        thresholds.push({
                            <ano:iterate name="threshold" property="value" id="guard" indexId="i">
                                <ano:notEqual name="i" value="0">,</ano:notEqual> <ano:write name="guard" property="status"/>:<ano:write name="guard" property="value"/>
                            </ano:iterate>
                        })
                     </ano:iterate>

                     var thresholdsColors = (thresholds.length > 0) ? getThresholdsColors() : {};

                     function getThresholdsColors() {
                        var colors = {};
                        try {
                            for (var i = 0, styleSheets = document.styleSheets; i < styleSheets.length; i++) {
                                if (styleSheets[i].cssRules && styleSheets[i].cssRules[0].href) {
                                    for (var j = 0, cssRules = styleSheets[i].cssRules; j < cssRules.length; j++) {
                                        if (cssRules[j].href && cssRules[j].href === "main.css") {
                                            if (cssRules[j].styleSheet && cssRules[j].styleSheet.cssRules) {
                                                for (var k = 0, rules = cssRules[j].styleSheet.cssRules; k < rules.length; k++) {
                                                    switch (rules[k].selectorText) {
                                                        case ".status.status-green" :colors.GREEN = RGBToHex(rules[k].style.background);break;
                                                        case ".status.status-yellow":colors.YELLOW = RGBToHex(rules[k].style.background);break;
                                                        case ".status.status-orange":colors.ORANGE = RGBToHex(rules[k].style.background);break;
                                                        case ".status.status-red"   :colors.RED = RGBToHex(rules[k].style.background);break;
                                                        case ".status.status-purple":colors.PURPLE = RGBToHex(rules[k].style.background);break;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (e) {/*ignore*/}

                        colors.GREEN = colors.GREEN || "#53d769";
                        colors.YELLOW = colors.YELLOW || "#ffde00";
                        colors.ORANGE = colors.ORANGE || "#ff8023";
                        colors.RED = colors.RED || "#fc3e39";
                        colors.PURPLE = colors.PURPLE || "#b44bc4";

                        return colors;

                        function RGBToHex(color) {
                            var rgb = color.match(/\d{1,3}/g);
                            return "#" + decToHex(rgb[0] - 0) + decToHex(rgb[1] - 0) + decToHex(rgb[2] - 0);

                            function decToHex(d) {return (d < 16) ? "0" + d.toString(16) : d.toString(16);}
                        }
                    }
                </script>
            </ano:present>

            <%-- single chart box with charts --%>
            <ano:notPresent name="multiple_set">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                           href="#collapse-chart"><i class="fa fa-caret-down"></i></a>

                        <h3 class="pull-left chart-header">
                            <ano:iF test="${fn:length(accNames) eq 1}">
                                ${accNames[0]}
                            </ano:iF>
                            <ano:iF test="${fn:length(accNames) gt 1}">
                                Combined charts
                            </ano:iF>
                        </h3>

                        <div class="box-right-nav dropdown">
                            <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                <li><a href="" class="save_as">Screenshot</a></li>
                                <ano:iF test="${chart.dashboardsToAdd != ''}">
                                    <li><a onclick="addChart('${requestScope.accNamesConcat}','${requestScope.dashboards}')">Add to Dashboard</a></li>
                                </ano:iF>
                            </ul>
                        </div>
                    </div>
                    <div id="collapse-chart" class="box-content accordion-body collapse in">
                        <div class="paddner">
                            <div id="chart_accum${singleGraph.nameForJS}" class="accumulator-chart"></div>
                        </div>
                    </div>
                </div>
            </ano:notPresent>
            <%-- /single chart box --%>

            <%-- Chart boxes for multiple charts --%>
            <div>
                <ano:present name="multiple_set">
                    <ano:iterate name="singleGraphData"
                                 type="net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO"
                                 id="singleGraph">
                        <div class="box" id="parentBox">
                            <div class="box-title">
                                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                                   href="#collapse-chart-${singleGraph.nameForJS}"><i class="fa fa-caret-down"></i></a>

                                <h3 class="pull-left chart-header">
                                    ${singleGraph.name}
                                </h3>

                                <div class="box-right-nav dropdown">
                                    <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                                    <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                        <li><a href="" class="save_as">Screenshot</a></li>
                                        <ano:iF test="${chart.dashboardsToAdd != ''}">
                                            <li><a onclick="addChart('${singleGraph.name}','${requestScope.dashboards}')">Add to Dashboard</a></li>
                                        </ano:iF>
                                    </ul>
                                </div>

                                <div class="box-right-nav">
                                    <a href="" class="tooltip-bottom" title="Refresh"><i class="fa fa-refresh"></i></a>
                                    <a class="up tooltip-bottom" title="Up" href="#"><i class="fa fa-angle-up"></i></a>
                                    <a class="down tooltip-bottom" title="Down" href="#"><i
                                            class="fa fa-angle-down"></i></a>
                                </div>
                            </div>
                            <div id="collapse-chart-${singleGraph.nameForJS}"
                                 class="box-content accordion-body collapse in">
                                <div class="paddner">
                                    <div id="chart_accum${singleGraph.nameForJS}" class="accumulator-chart"></div>
                                </div>
                            </div>
                        </div>
                    </ano:iterate>
                </ano:present>
            </div>
            <%-- /charts' boxes --%>

            <script type="text/javascript">
                //changing the order of multiple charts
                $(document).ready(function () {
                    $(".up").click(function () {
                        var pdiv = $(this).closest('#parentBox');
                        pdiv.insertBefore(pdiv.prev());
                        return false
                    });
                    $(".down").click(function () {
                        var pdiv = $(this).closest('#parentBox');
                        pdiv.insertAfter(pdiv.next());
                        return false
                    });
                });
            </script>

            <script type="text/javascript">
                var thresholds = thresholds || [];
                var thresholdsColors = thresholdsColors || {};

                // Many charts
                if ('multipleGraphData' in window) {
                    var names = '${accNames}'.slice(1, -1).split(', ');
                    var containerSelectors = $('.accumulator-chart').map(function () {
                        return $(this).attr("id");
                    });

                    multipleGraphData.forEach(function (graphData, index) {
                        var chartParams = {
                            container: containerSelectors[index],
                            names: [names[index]],
                            data: graphData,
                            colors: accumulatorsColors,
                            type: '<ano:write name="type"/>',
                            title: names[index],
                            dataType: 'datetime',
                            options: {
                                legendsPerSlice: 7,
                                margin: {top: 20, right: 40, bottom: 30, left: 40}
                            }
                        };

                        if (!isEmptyObject(thresholds[index])) {
                            addThresholdsToChart(thresholds[index], chartParams);
                        }

                        chartEngineIniter.init(chartParams);
                    });

                }
                // One chart with one or more lines
                else {
                    var names = ('${singleGraph.name}' && ['${singleGraph.name}']) || '${accNames}'.slice(1, -1).split(', ');

                    var chartParams = {
                        container: 'chart_accum${singleGraph.nameForJS}',
                        names: names,
                        data: data,
                        colors: accumulatorsColors,
                        type: '<ano:write name="type"/>',
                        title: '',
                        dataType: 'datetime',
                        options: {
                            legendsPerSlice: 7,
                            margin: {top: 20, right: 40, bottom: 30, left: 40}
                        }
                    };

                    if ((names.length === 1) && !isEmptyObject(thresholds[0])) {
                        addThresholdsToChart(thresholds[0], chartParams);
                    }

                    chartEngineIniter.init(chartParams);
                }


                $('.refresh').click(function () {
                    location.reload(true);
                });

                function addThresholdsToChart(thresholds, chartParams) {
                    for (var color in thresholds) {
                        if (thresholds.hasOwnProperty(color)) {
                            if (thresholdsColors.hasOwnProperty(color)) {
                                var gauge = thresholds[color];
                                var data = chartParams.data;
                                chartParams.colors.push({"color": thresholdsColors[color], "name": color});
                                chartParams.names.push(color);
                                for (var i = 0, length = data.length; i < length; i++) {
                                    data[i].push(gauge);
                                }
                            }
                        }
                    }
                }

                function isEmptyObject(obj) {
                    for (var property in obj) {
                        if (obj.hasOwnProperty(property)) {
                            return false;
                        }
                    }
                    return true;
                }

            </script>
        </ano:present>

        <!-- selections of accumulators -->
        <ano:present name="accumulatorSetBeans">
            <div class="box">
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                       href="#collapselist2"><i class="fa fa-caret-down"></i></a>

                    <h3 class="pull-left">
                        Accumulator sets
                    </h3>

                    <div class="box-right-nav">
                        <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                    </div>
                </div>
                <div id="collapselist2" class="box-content accordion-body collapse in">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th>Set<i class="fa fa-caret-down"></i></th>
                            <th>Accumulators <i class="fa fa-caret-down"></i></th>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate id="acSet" name="accumulatorSetBeans"
                                     type="net.anotheria.moskito.webui.accumulators.bean.AccumulatorSetBean">
                            <tr>
                                <td><a href="${acSet.link}">${acSet.name}</a></td>
                                <td>${acSet.accumulatorNames}</td>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>
            </div>
        </ano:present>
        <!-- /selections of accumulators -->

        <div class="box">
            <form action="" method="get">
                <input type="hidden" name="remoteConnection" value="${remoteLink}"/>
                <div class="box-title">
                    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse"
                       href="#collapselist"><i class="fa fa-caret-down"></i></a>

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
                            <th class="{sorter: false, filter: false}"><input type="checkbox" id="checkAll"/> </th>
                            <th>Name<i class="fa fa-caret-down"></i></th>
                            <th>Path <i class="fa fa-caret-down"></i></th>
                            <th>Values <i class="fa fa-caret-down"></i></th>
                            <th>Last Timestamp <i class="fa fa-caret-down"></i></th>
                            <th class="th-actions"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="accumulators"
                                     type="net.anotheria.moskito.webui.accumulators.api.AccumulatorDefinitionAO"
                                     id="accumulator" indexId="index">
                            <tr>
                                <td><input type="checkbox" class="checktr" name="id_${accumulator.id}" value="set"
                                           <ano:present
                                                   name="<%=\"id_\"+accumulator.getId()+\"_set\"%>">checked="checked"</ano:present>/>
                                </td>
                                <td><mos:deepLink  href="?id_${accumulator.id}=set">${accumulator.name}</mos:deepLink ></td>
                                <td>${accumulator.path}</td>
                                <td>${accumulator.numberOfValues}</td>
                                <td>${accumulator.lastValueTimestamp}</td>
                                <td class="actions-links">
                                    <a href="#mskAccumulatorDelete" data-toggle="modal"
                                       data-target="#mskAccumulatorDelete" data-id="${accumulator.id}"
                                       class="action-icon delete-icon tooltip-bottom" title="Delete"><i
                                            class="fa fa-ban"></i></a>
                                    <mos:deepLink  href="?id_${accumulator.id}=set" class="action-icon show-icon tooltip-bottom"
                                       title="Show"><i class="fa fa-search-plus"></i></mos:deepLink >
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
                                    <input type="radio"
                                           <ano:equal name="combined_set" value="true">checked="checked"</ano:equal>
                                           value="combined" name="mode"> combine
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="normalized" name="mode"
                                           <ano:equal name="normalized_set" value="true">checked="checked"</ano:equal>>
                                    combine and normalize
                                </label>
                            </div>
                            <div class="radio">
                                <label>
                                    <input type="radio" value="multiple" name="mode"
                                           <ano:equal name="multiple_set" value="true">checked="checked"</ano:equal>>
                                    multiple graphs
                                </label>
                            </div>
                            <div class="form-group">
                                )
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" name="withThresholds"
                                           <ano:equal name="withThresholds_set" value="true">checked="checked"</ano:equal>>
                                    With Thresholds
                                </label>
                            </div>

                            <input type="hidden" value="200" name="maxValues">
                        </div>
                    </div>
                </div>
            </form>
        </div>

    </div>

    <div class="modal fade modal-danger" id="mskAccumulatorDelete" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
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
        $('.actions-links').on('click', '.delete-icon', function () {
            var dataid = $(this).attr('data-id');
            $('.accumulator-delete-confirm').attr("href", "mskAccumulatorDelete?pId=" + dataid);
        });

        $(window).scroll(function () {
            if ($(document).scrollTop() >= $(document).height() - $(window).height() - 180) {
                $('.box-footer').removeClass('fixed');
            } else if ($(document).scrollTop() < $(document).height() - $(window).height()) {
                $('.box-footer').addClass('fixed');
            }
        });//scroll

        $('.checktr:checked').closest('tr').addClass('checked');
        if ($('.checktr').is(':checked')) {
            $('.fixed-box .btn-submit').addClass('btn-success');
            $('.fixed-box .btn-clear').removeClass('hide');
        }

        $('.fixed-box .btn-clear').click(function () {
            $('.table tr').removeClass('checked');
            $('.checktr').prop('checked', false);
            $(this).addClass('hide');
        });

        $('.table tbody tr')
                .filter(':has(:checkbox:checked)')
                .addClass('checked')
                .end()
                .click(function (event) {
                    $(this).toggleClass('checked');
                    if (event.target.type !== 'checkbox') {
                        $(':checkbox', this).prop('checked', function () {
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
                    if ($('.checktr:not(:checked)').length > 0) {
                        $('#checkAll').prop( "checked", false );
                    } else {
                        $('#checkAll').prop( "checked", true );
                    }
                });

        $('#checkAll').click(function() {
            var checked = $(this).prop('checked');
            var checkboxes;
            if (checked) {
                checkboxes = $('.checktr:not(:checked)');
            } else {
                checkboxes = $('.checktr:checked');
            }

            if (checkboxes.length > 0) {
                checkboxes.click();
            }
        });
    </script>



        <script type="text/javascript">
        $('.save_as').click( function(event) {
            event.preventDefault();
            event.stopPropagation();
            var chartWidth = 1120,
                    chartHeight = 300,
                    margin = 40;

            var svgOrigin = $(event.target).closest(".box").find("svg")[0];
            //copy svg chart
            var svg = svgOrigin.cloneNode(true);

            svg.setAttribute("style", "background-color: #FFFFFF;");
            svg.setAttribute("x",margin);
            svg.setAttribute("y",margin);


            var css = '.axis path,'+
            '.axis line {'+
                'fill: none;'+
                'stroke: #000;'+
                'shape-rendering: crispEdges;'+
            '}'+
            '.legend, .tick {'+
                'font: 12px sans-serif;'+
            '}'+

            '.line {'+
                'fill: none;'+
                'stroke: steelblue;'+
                'stroke-width: 1.5px;'+
            '}'+

            '.line.hover {'+
            'fill: none;'+
                'stroke: steelblue;'+
                'stroke-width: 3.0px;'+
            '}'+

            '.grid .tick {'+
                'stroke: lightgrey;'+
                'opacity: 0.7;'+
            '}'+
            '.grid path {'+
                'stroke-width: 0;'+
            '}';

            var style = document.createElement('style');
            style.type = 'text/css';
            if (style.styleSheet){
                style.styleSheet.cssText = css;
            } else {
                style.appendChild(document.createTextNode(css));
            }

            svg.appendChild(style);

            var svgData = new XMLSerializer().serializeToString(svg);
            svgData ='<svg xmlns="http://www.w3.org/2000/svg"  style="background-color: #FFFFFF;" width="1200" height="380" >' + svgData + '</svg>';

            var canvas = document.createElement("canvas");
            canvas.width  = chartWidth + 2*margin;
            canvas.height = chartHeight + 2*margin;
            var ctx = canvas.getContext("2d");
            ctx.fillStyle="white";
            ctx.fill();

            var img = document.createElement("img");
            window.unescape = window.unescape || window.decodeURI;
            var img = document.createElement("img");
            var encoded_svg = btoa(svgData.replace(/[\u00A0-\u2666]/g, function(c) {
                return '&#' + c.charCodeAt(0) + ';';
            }));
            img.setAttribute("src", "data:image/svg+xml;base64," + encoded_svg);
            var file_name = getChartFileName();

            img.onload = function () {
                ctx.drawImage(img, 0, 0);
                var canvasdata = canvas.toDataURL("image/png")
                var a = document.createElement("a");

                a.download = file_name + ".png";
                a.href = canvasdata;
                document.body.appendChild(a);
                a.click();

            };
        });

        function getChartFileName() {
            var t = new Date($.now());
            var current_date = t.getFullYear()+'-'+ t.getMonth()+'-'+ t.getDate()+'__'+t.getHours()+'-'+ t.getMinutes()+'-'+ t.getSeconds();
            return $.trim($(event.target).closest(".box").find('.chart-header').text()).split(' ').join('_')+'_'+current_date;
        }
    </script>

</section>
</body>
</html>
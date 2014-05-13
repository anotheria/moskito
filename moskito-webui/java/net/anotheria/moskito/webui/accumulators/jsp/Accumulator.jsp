<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Moskito Accumulator</title>
    <link rel="stylesheet" href="mskCSS"/>
    <link rel="stylesheet" type="text/css" href="../css/jquery.jqplot.css">
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
<script type="text/javascript" src="../js/charts/jqplot/jqplot.dateAxisRenderer.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.pieRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.donutRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="../js/charts/jqplot/jqplot.barRenderer.min.js"></script>

<script type="text/javascript" src="../js/charts/chartEngineIniter.js"></script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>

<!-- Chart Engine: <ano:write name="chartEngine"/> -->

<script type="text/javascript">
    var singleGraphData<ano:write name="accumulatorData" property="nameForJS"/> = [<ano:iterate name="accumulatorData" property="data" id="value" indexId="i"><ano:notEqual name="i" value="0">,</ano:notEqual>
        <ano:equal name="numericTimestamps" value="true"><ano:write name="value" property="JSONWithNumericTimestamp"/></ano:equal>
        <ano:equal name="numericTimestamps" value="false"><ano:write name="value" property="JSONWithStringTimestamp"/></ano:equal>
        </ano:iterate>]; ;
</script>

<div class="main">
    <div class="clear"><!-- --></div>
    <!-- chart section -->
    <ano:present name="accumulatorData">
    <div class="table_layout">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="in">
            <h2><span>Chart for <ano:write name="accumulatorData" property="name"/></span></h2><a class="refresh" href="#"></a>

            <div class="clear"><!-- --></div>
            <div class="table_itseft">
                <div class="top">
                    <div class="left"><!-- --></div>
                    <div class="right"><!-- --></div>
                </div>
                <div class="in">
                    <div id="chart_accum<ano:write name="accumulatorData" property="nameForJS"/>"></div>
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
    </ano:present>
    <div class="clear"><!-- --></div>
    <div class="table_layout">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="in">
            <h2><span>Data for <ano:write name="accumulatorData" property="name"/></span></h2>

            <div class="clear"><!-- --></div>
            <div class="table_itseft">
                <div class="top">
                    <div class="left"><!-- --></div>
                    <div class="right"><!-- --></div>
                </div>
                <div class="in">
                    <div class="scroller_x">
                    <form action="" method="get">
                        <table cellpadding="0" cellspacing="0" width="100%">
                            <thead>
                            <tr>
                                <th>Timestamp</th>
                                <th>Value</th>
                            </tr>
                            </thead>
                            <tbody>
                                <ano:iterate name="accumulatorData" property="data" id="row">
                                    <tr>
                                        <td><ano:write name="row" property="timestamp"/></td>
                                        <td><ano:write name="row" property="firstValue"/></td>
                                    </tr>
                                </ano:iterate>
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

    <div class="table_layout">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="in">
            <h2><span>JSON for <ano:write name="accumulatorData" property="name"/></span></h2>

            <div class="clear"><!-- --></div>
            <div class="table_itseft">
                <div class="top">
                    <div class="left"><!-- --></div>
                    <div class="right"><!-- --></div>
                </div>
                <div class="in">
                    <div class="scroller_x">
                        <pre>
                            {
                                "accumulatorInfo" : {
                                    "id": "<ano:write name="accumulatorInfo" property="id"/>",
                                    "name": "<ano:write name="accumulatorInfo" property="name"/>",
                                    "path": "<ano:write name="accumulatorInfo" property="path"/>",
                                    "numberOfValues": "<ano:write name="accumulatorInfo" property="numberOfValues"/>",
                                    "maxNumberOfValues": "<ano:write name="accumulatorInfo" property="maxNumberOfValues"/>",
                                    "lastValueTimestamp": "<ano:write name="accumulatorInfo" property="lastValueTimestamp"/>"
                                },
                                "accumulatorData" : [
                                <ano:iterate name="accumulatorData" property="data" id="row" indexId="ind"
                                    ><ano:notEqual name="ind" value="0">,</ano:notEqual>
                                    {"timestamp": "<ano:write name="row" property="isoTimestamp"/>", "value": "<ano:write name="row" property="firstValue"/>"}</ano:iterate>
                                ]
                            }
                        </pre>
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


<script type="text/javascript">
    var chartEngineName = '<ano:write name="chartEngine"/>' || 'GOOGLE_CHART_API';
    var chartParams = {
        container: 'chart_accum<ano:write name="accumulatorData" property="nameForJS"/>',
        names: ['<ano:write name="accumulatorData" property="name"/>'],
        data: singleGraphData<ano:write name="accumulatorData" property="nameForJS"/>,
        type: 'LineChart',
        title: ''
    };

    chartEngineIniter[chartEngineName](chartParams);

    $('.refresh').click(function() {
        location.reload(true);
    });


</script>
</div>
</body>
</html>  

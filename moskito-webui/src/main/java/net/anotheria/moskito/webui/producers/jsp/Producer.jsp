<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
<div class="content">

<div class="box">
    <div class="box-content paddner">
        <dl class="dl-horizontal pull-left">
            <dt>Producer:</dt>
            <dd>${producer.producerId}</dd>
            <dt>Category:</dt>
            <dd><a href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</a></dd>
            <dt>Subsystem:</dt>
            <dd><a href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</a></dd>
            <dt>Сlass:</dt>
            <dd>${producer.producerClassName}</dd>
        </dl>
        <div class="pull-right">
            <a href="${linkToCurrentPage}&pForward=selection&target=Accumulator" class="btn btn-default" onclick="new_accumulator(); return false"><i class="fa fa-plus"></i> Accumulator</a>
            <a href="${linkToCurrentPage}&pForward=selection&target=Threshold" class="btn btn-default" onclick="new_threshold(); return false"><i class="fa fa-plus"></i> Threshold</a>
            <c:if test="${producer.traceable}">
                <c:choose>
                <c:when test="${producer.traced}">
                    <a href="mskTracer?pProducerId=${producer.producerId}" class="btn btn-success" onclick=""><i class="fa fa-binoculars"></i> Tracer</a>
                </c:when>
                <c:otherwise>
                    <a href="mskCreateTracer?pProducerId=${producer.producerId}" class="btn btn-default" onclick=""><i class="fa fa-plus"></i> Tracer</a>
                </c:otherwise>
                </c:choose>
            </c:if>
            <c:if test="${producer.inspectable}">
                <a href="#inspect" data-toggle="modal" data-target="#inspect" class="btn btn-success"><i class="fa fa-search"></i> Inspect</a>
            </c:if>
        </div>
    </div>
</div>

<ano:iterate type="net.anotheria.moskito.webui.shared.bean.StatDecoratorBean" id="decorator" name="decorators">
<div class="box">
    <div class="box-title">
        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseproducer"><i class="fa fa-caret-right"></i></a>
        <h3 class="pull-left">
            ${producer.producerId}
        </h3>
        <div class="box-right-nav">
            <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
        </div>
    </div>
    <div id="collapseproducer" class="box-content accordion-body collapse in">
        <table class="table table-striped tablesorter">
            <thead>
            <tr>
                <th>Name <i class="fa fa-caret-down"></i></th>
                <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                    <th title="<ano:write name="caption" property="shortExplanation"/>" class="{sorter: 'commaNumber'} table-column">
                        <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                        <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption} <i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i></th>
                    </th>
                </ano:iterate>

            </tr>
            </thead>
            <tbody>
            <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                <tr>
                    <td>${stat.name}</td>
                    <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                        <td title="${stat.name}.${value.name}=${value.value}">
                            ${value.value}
                        </td>
                    </ano:iterate>
                </tr>
            </ano:iterate>
            </tbody>
            <ano:present name="decorator" property="cumulatedStat">
                <ano:define id="cumulatedStat" name="decorator" property="cumulatedStat"/>
                <tfoot>
                <tr>
                    <td>${cumulatedStat.name}</td>
                    <ano:iterate name="cumulatedStat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                        <td title="${stat.name}.${value.name}=${value.value}">
                                ${value.value}
                        </td>
                    </ano:iterate>
                </tr>
                <tfoot>
            </ano:present>


        </table>
    </div>
</div>
</ano:iterate>


</div>

<jsp:include page="../../producers/jsp/ChartEngine.jsp"/>

    <div class="modal fade inspect-list" id="inspect" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Inspect Producer '${producer.producerId}' creation location:</h4>
                </div>
                <div class="modal-body">
                    <ul>
                        <ano:iterate name="creationTrace" type="java.lang.String" id="line">
                            <li>${line}</li>
                        </ano:iterate>
                    </ul>
                </div>
            </div>
        </div>
    </div>

<div class="modal fade" id="createNewThreshold" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="CreateThreshold" action="mskThresholdCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Threshold"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>

        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Create new Threshold</h4>
            </div>
            <div class="modal-body">
                    <div class="form-group">
                        <label for="nameThresholds">Threshold name:</label>
                        <input type="text" class="form-control" id="nameThresholds" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Guards</label>
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Green"><i class="status status-green"></i></div>
                                    <select onchange="switchDirection();" name="greenDir" class="form-control"><option>below</option><option>above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <label>Interval</label>
                                <input type="text" class="form-control" value="" placeholder="Value" name="greenValue" onchange="switchgreenvalue();">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Yellow"><i class="status status-yellow"></i></div>
                                    <select onchange="switchDirection();" name="yellowDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value=""  name="yellowValue" placeholder="Value">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Orange"><i class="status status-orange"></i></div>
                                    <select onchange="switchDirection();" name="orangeDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="orangeValue">
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Red"><i class="status status-red"></i></div>
                                    <select onchange="switchDirection();" name="redDir" class="form-control"><option>below</option><option selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value"  name="redValue" >
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-md-6">
                                <div class="switch-direction">
                                    <div class="status-control pull-left tooltip-bottom" title="Purple"><i class="status status-purple"></i></div>
                                    <select onchange="switchDirection();" name="purpleDir" class="form-control"><option>below</option><option  selected="selected">above</option></select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="purpleValue">
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            ${interval.name}
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
            </div>
            <div class="form-group">
                <dl class="dl-horizontal">
                    <dt>Producer:</dt>
                    <dd>${producer.producerId}</dd>
                </dl>
            </div>

            <div class="modal-table">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                            <th>${caption.caption}</th>
                        </ano:iterate>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                        <tr>
                            <td>${stat.name}</td>
                            <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                <td>
                                    <a href="#" onclick="setandsubmit('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                </td>
                            </ano:iterate>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
            </div>
        </div>
        </form>
    </div>
</div>

<!-- Accumulator Dialog -->
<div class="modal fade" id="createNewAccumulator" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="CreateAccumulator" action="mskAccumulatorCreate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Accumulator"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Create new Accumulator</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nameAccumulator">Accumulator name:</label>
                        <input type="text" class="form-control" id="nameAccumulator" placeholder="Enter name" name="name">
                    </div>
                    <div class="form-group">
                        <div class="row">
                            <div class="col-md-6">
                                <ano:define name="currentInterval" id="currentInterval" toScope="page" type="java.lang.String"/>
                                <label for="Interval">Interval</label>
                                <select name="interval" class="form-control" id="Interval">
                                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                                        <option value="${interval.name}" <ano:equal name="interval" property="name" value="<%=currentInterval%>">selected="selected"</ano:equal>>
                                            <ano:write name="interval" property="name"/>
                                        </option>
                                    </ano:iterate>
                                </select>
                            </div>
                            <div class="col-md-6">
                                <ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>

                                <label for="TimeUnite">TimeUnit</label>
                                <select name="unit" id="TimeUnite" class="form-control">
                                    <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                                        <option value="<ano:write name="unit" property="unitName"/>" <ano:equal name="unit" property="unitName" value="<%=currentUnit%>">selected="selected"</ano:equal>>
                                            ${unit.unitName}
                                        </option>
                                    </ano:iterate>
                                </select>

                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <dl class="dl-horizontal">
                        <dt>Producer:</dt>
                        <dd>${producer.producerId}</dd>
                    </dl>
                </div>

                <div class="modal-table">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                                <th>${caption.caption}</th>
                            </ano:iterate>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                            <tr>
                                <td>${stat.name}</td>
                                <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                    <td>
                                        <a href="#" onclick="setandsubmitAccumulator('${value.name}', '${stat.name}'); return false"><i class="fa fa-plus"></i></a>
                                    </td>
                                </ano:iterate>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </form>
    </div>
</div>


<jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
<jsp:include page="snippet/ProducerHelpModal.jsp"/>
</section>

<script>
    function new_threshold(){
        $('#createNewThreshold').modal('show');
    }
    function new_accumulator(){
        $('#createNewAccumulator').modal('show');
    }


    function setandsubmitAccumulator(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateAccumulator.statName.value = statName;
        document.forms.CreateAccumulator.valueName.value = valueName;
        document.forms.CreateAccumulator.submit();
    }
    function setandsubmit(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.CreateThreshold.statName.value = statName;
        document.forms.CreateThreshold.valueName.value = valueName;
        document.forms.CreateThreshold.submit();
    }
    function switchDirection(){
        if (document.forms.CreateThreshold.greenDir.value=='above')
            targetValue = 'below';
        else
            targetValue = 'above';

        document.forms.CreateThreshold.yellowDir.value = targetValue;
        document.forms.CreateThreshold.orangeDir.value = targetValue;
        document.forms.CreateThreshold.redDir.value = targetValue;
        document.forms.CreateThreshold.purpleDir.value = targetValue;
    }

    function switchgreenvalue(){
        document.forms.CreateThreshold.yellowValue.value=document.forms.CreateThreshold.greenValue.value;
    }
</script>

</body>
</html>
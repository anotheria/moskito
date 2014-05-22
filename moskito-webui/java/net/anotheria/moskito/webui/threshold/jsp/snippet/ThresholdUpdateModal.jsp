<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>

<script type="text/javascript">
    function showThresholdUpdateModal(tresholdId){
        $.ajax({url: getBaseUrl() + "mskGetThresholdDefinition?pId=" + tresholdId,
            dataType: "json",
            success: function (data) {
                populateAndShowTresholdEditModal(data);
            }});
    }
    function populateAndShowTresholdEditModal(data){
        var $dialogTpl = $($('#updateThresholdTemplate')[0]);
        alert(data);
        /*
        if(!$dialogTpl) return;
        $dialogTpl.find('.modal-title').html(producerName);
        var $dialogTplBody = $($dialogTpl.find('.modal-body')[0]);
        var $box = $($dialogTplBody.find('.box')[0]);
        $dialogTplBody.empty();
        for(var i=0; i< data.length;i++){
            var $boxClone = $box.clone();
            $boxClone.find('.box-title h3').html(data[i].caption);
            $boxClone.find('.box-content .paddner').html(data[i].explanation);
            if(i === data.length - 1 ) $boxClone.addClass('last');
            $dialogTplBody.append($boxClone);
        }
        */
        $dialogTpl.modal('show');
    }

    function setandsubmit(valueName, statName){
        //alert('Value name is '+valueName+" in stat  "+statName);
        document.forms.UpdateThreshold.statName.value = statName;
        document.forms.UpdateThreshold.valueName.value = valueName;
        document.forms.UpdateThreshold.submit();
    }
    function switchDirection(){
        if (document.forms.UpdateThreshold.greenDir.value=='above')
            targetValue = 'below';
        else
            targetValue = 'above';

        document.forms.UpdateThreshold.yellowDir.value = targetValue;
        document.forms.UpdateThreshold.orangeDir.value = targetValue;
        document.forms.UpdateThreshold.redDir.value = targetValue;
        document.forms.UpdateThreshold.purpleDir.value = targetValue;
    }

    function switchgreenvalue(){
        document.forms.CreateThreshold.yellowValue.value=document.forms.CreateThreshold.greenValue.value;
    }
</script>

<div class="modal fade" id="updateThresholdTemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="UpdateThreshold" action="mskThresholdUpdate" method="GET">
            <input type="hidden" name="producerId" value="${producer.producerId}"/>
            <input type="hidden" name="target" value="Threshold"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>

            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Update Threshold</h4>
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
                            <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" id="caption" indexId="ind">
                                <th>${caption.caption}</th>
                            </ano:iterate>
                        </tr>
                        </thead>
                        <tbody>
                        <ano:iterate name="decorator" property="stats" id="stat" type="net.anotheria.moskito.webui.shared.bean.StatBean" indexId="index">
                            <tr>
                                <td>${stat.name}</td>
                                <ano:iterate name="stat" property="values" id="value" type="net.anotheria.moskito.webui.producers.api.StatValueAO">
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
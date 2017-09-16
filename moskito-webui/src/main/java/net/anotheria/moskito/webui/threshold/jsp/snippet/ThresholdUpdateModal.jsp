<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>

<script type="text/javascript">
    function showThresholdUpdateModal(tresholdId){
        $.ajax({url: "mskGetThresholdDefinition?pId=" + tresholdId,
            dataType: "json",
            success: function (data) {
                populateAndShowThresholdEditModal(data);
            }});
    }
    function populateAndShowThresholdEditModal(data){
        var $dialogTpl = $($('#updateThresholdTemplate')[0]);
        if(!$dialogTpl) return;
        document.forms.UpdateThreshold.pId.value = data.id;
        document.forms.UpdateThreshold.name.value = data.name;

        for(var i=0; i< data.guards.length;i++){
            var guard = data.guards[i];
            switch(data.guards[i].targetStatus) {
                case 'GREEN':
                    document.forms.UpdateThreshold.greenValue.value = guard.barrierValue;
                    selectOption('greenDir', guard.direction);
                    break;
                case 'YELLOW':
                    document.forms.UpdateThreshold.yellowValue.value = guard.barrierValue;
                    selectOption('yellowDir', guard.direction);
                    break;
                case 'ORANGE':
                    document.forms.UpdateThreshold.orangeValue.value = guard.barrierValue;
                    selectOption('orangeDir', guard.direction);
                    break;
                case 'RED':
                    document.forms.UpdateThreshold.redValue.value = guard.barrierValue;
                    selectOption('redDir', guard.direction);
                    break;
                case 'PURPLE':
                    document.forms.UpdateThreshold.purpleValue.value = guard.barrierValue;
                    selectOption('purpleDir', guard.direction);
                    break;
            }
        }
        $dialogTpl.find('#producerName').text(data.producerName);
        $dialogTpl.find('#interval').text(data.intervalName);
        $dialogTpl.find('#unit').text(data.timeUnit);

        $dialogTpl.modal('show');
    }

    function selectOption(selectName, optionValue) {
        $("select[name=" + selectName + "] option").filter(function() {
            return $(this).prop('value') == optionValue;
        }).prop('selected', true);
    }

    function switchDirection(){
        if (document.forms.UpdateThreshold.greenDir.value=='UP')
            targetValue = 'DOWN';
        else
            targetValue = 'UP';

        document.forms.UpdateThreshold.yellowDir.value = targetValue;
        document.forms.UpdateThreshold.orangeDir.value = targetValue;
        document.forms.UpdateThreshold.redDir.value = targetValue;
        document.forms.UpdateThreshold.purpleDir.value = targetValue;
    }

    function switchgreenvalue(){
        document.forms.UpdateThreshold.yellowValue.value = document.forms.UpdateThreshold.greenValue.value;
    }
</script>

<div class="modal fade" id="updateThresholdTemplate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog form">
        <form name="UpdateThreshold" action="mskThresholdUpdate" method="GET">
            <input type="hidden" name="target" value="Threshold"/>
            <input type="hidden" name="statName"/>
            <input type="hidden" name="valueName"/>
            <input type="hidden" name="pId"/>
            <input type="hidden" name="remoteConnection" value="${remoteLink}"/>

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
                                    <select onchange="switchDirection();" name="greenDir" class="form-control">
                                        <option value="DOWN">below</option>
                                        <option value="UP">above</option>
                                    </select>
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
                                    <select onchange="switchDirection();" name="yellowDir" class="form-control">
                                        <option value="DOWN">below</option>
                                        <option value="UP">above</option>
                                    </select>
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
                                    <select onchange="switchDirection();" name="orangeDir" class="form-control">
                                        <option value="DOWN">below</option>
                                        <option value="UP">above</option>
                                    </select>
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
                                    <select onchange="switchDirection();" name="redDir" class="form-control">
                                        <option value="DOWN">below</option>
                                        <option value="UP">above</option>
                                    </select>
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
                                    <select onchange="switchDirection();" name="purpleDir" class="form-control">
                                        <option value="DOWN">below</option>
                                        <option value="UP">above</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" value="" placeholder="Value" name="purpleValue">
                            </div>
                        </div>
                    </div>

                </div>

                <div class="form-group">
                    <dl class="dl-horizontal">
                        <dt>Producer:</dt>
                        <dd id="producerName"></dd>
                    </dl>
                    <dl class="dl-horizontal">
                        <dt>Interval:</dt>
                        <dd id="interval"></dd>
                    </dl>
                    <dl class="dl-horizontal">
                        <dt>TimeUnit:</dt>
                        <dd id="unit"></dd>
                    </dl>
                </div>

                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Update</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </div>
        </form>
    </div>
</div>
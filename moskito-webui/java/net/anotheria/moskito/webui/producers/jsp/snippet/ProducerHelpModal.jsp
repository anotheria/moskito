<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<script type="text/javascript">
    function showProducerHelpModal(producerName){
        $.ajax({url: getBaseUrl() + "mskGetExplanationsByName?pName=" + producerName,
            dataType: "json",
            success: function (data) {
                populateAndShowModal(data);
            }});
    }
    function populateAndShowModal(data){
        var $dialogTpl = $($('#help_template')[0]);
        if(!$dialogTpl) return;
        var $dialogTplBody = $($dialogTpl.find('.modal-body')[0]);
        var $box = $($dialogTplBody.find('.box')[0]);
        $dialogTplBody.empty();
        for(var i=0; i< data.length;i++){
            var $boxClone = $box.clone();
            $boxClone.find('.box-title h3')[0].innerHTML = data[i].caption;
            $boxClone.find('.box-content .paddner')[0].innerHTML = data[i].explanation;
            $dialogTplBody.append($boxClone);
        }
        $dialogTpl.modal('show');
    }
</script>
<div class="modal fade" id="help_template" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog gray">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Help</h4>
            </div>
            <div class="modal-body">
                <div class="box">
                    <div class="box-title">
                        <h3 class="pull-left">
                            __box_title__
                        </h3>
                    </div>
                    <div class="box-content">
                        <div class="paddner">
                            __box_content__
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
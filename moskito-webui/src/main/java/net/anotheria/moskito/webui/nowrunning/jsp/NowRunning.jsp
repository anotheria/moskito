<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>
    <div class="content">

        <%-- Overview box --%>
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsestatus"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Entry points (Beta Feature)
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapsestatus" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Name <i class="fa fa-caret-down"></i></th>
                        <th>Current Requests <i class="fa fa-caret-down"></i></th>
                        <th>Total Requests <i class="fa fa-caret-down"></i></th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="entryPoints" type="net.anotheria.moskito.webui.nowrunning.api.EntryPointAO" id="entrypoint" indexId="index">
                    <tr>
                        <td>${entrypoint.producerId}</td>
                        <td>${entrypoint.currentRequestCount}</td>
                        <td>${entrypoint.totalRequestCount}</td>
                        <td>&nbsp;</td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>



        <ano:iterate name="entryPoints" type="net.anotheria.moskito.webui.nowrunning.api.EntryPointAO" id="entrypoint" indexId="index">
        <ano:equal name="entrypoint" property="currentlyRunning" value="true">
            <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${entrypoint.producerId}"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Now Running in ${entrypoint.producerId}
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse${entrypoint.producerId}" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Start <i class="fa fa-caret-down"></i></th>
                        <th>Age<i class="fa fa-caret-down"></i></th>
                        <th>Description<i class="fa fa-caret-down"></i></th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate id="measurement" type="net.anotheria.moskito.webui.nowrunning.api.MeasurementAO" name="entrypoint" property="currentMeasurements">
                        <tr>
                            <td>${measurement.startTimestamp}</td>
                            <td>${measurement.age}</td>
                            <td>${measurement.description}</td>
                            <td>&nbsp;</td>
                        </tr>
                    </ano:iterate>

                    </tbody>
                </table>
            </div>
        </div>
        </ano:equal>
        </ano:iterate>


        <ano:iterate name="entryPoints" type="net.anotheria.moskito.webui.nowrunning.api.EntryPointAO" id="entrypoint" indexId="index">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsepast${entrypoint.producerId}"><i class="fa fa-caret-down"></i></a>
                        <h3 class="pull-left">
                            Past Requests in in ${entrypoint.producerId}
                        </h3>
                        <div class="box-right-nav">
                            <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                        </div>
                    </div>
                    <div id="collapsepast${entrypoint.producerId}" class="box-content accordion-body collapse in">
                        <table class="table table-striped tablesorter">
                            <thead>
                            <tr>
                                <th>Position <i class="fa fa-caret-down"></i></th>
                                <th>Start <i class="fa fa-caret-down"></i></th>
                                <th>End <i class="fa fa-caret-down"></i></th>
                                <th>Duration ms<i class="fa fa-caret-down"></i></th>
                                <th>Age sec<i class="fa fa-caret-down"></i></th>
                                <th>Description<i class="fa fa-caret-down"></i></th>
                                <th class="th-actions"></th>
                            </tr>
                            </thead>
                            <tbody>
                            <ano:iterate indexId="position" id="measurement" type="net.anotheria.moskito.webui.nowrunning.api.MeasurementAO" name="entrypoint" property="pastMeasurements">
                                <tr>
                                    <td>${position}</td>
                                    <td>${measurement.startTimestamp}</td>
                                    <td>${measurement.endTimestamp}</td>
                                    <td>${measurement.duration}</td>
                                    <td>${measurement.age}</td>
                                    <td>${measurement.description}</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </ano:iterate>

                            </tbody>
                        </table>
                    </div>
                </div>
        </ano:iterate>
        <%--
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsehistory"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    History (newest first)
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapsehistory" class="box-content accordion-body collapse in h-scrollbar">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Timestamp <i class="fa fa-caret-down"></i></th>
                        <th>Name <i class="fa fa-caret-down"></i></th>
                        <th>Status change <i class="fa fa-caret-down"></i></th>
                        <th>Value change <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="alerts" type="net.anotheria.moskito.webui.threshold.api.ThresholdAlertAO" id="alert" indexId="index">
                    <tr>
                        <td>${alert.timestamp}</td>
                        <td><a class="threshold-update-link" data-id="${alert.id}" href="#">${alert.name}</a></td>
                        <td><i class="status status-${alert.oldColorCode}"></i> <i class="fa fa-long-arrow-right"></i> <i class="status status-${alert.newColorCode}"></i></td>
                        <td>${alert.oldValue} <i class="fa fa-long-arrow-right"></i> ${alert.newValue}</td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
              --%>
    </div>

    <div class="modal fade modal-danger" id="mskThresholdDelete" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Delete this Threshold?</h4>
                </div>
                <div class="modal-footer text-center">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <a href="#" class="btn btn-danger threshold-delete-confirm">Delete</a>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
    <%-- jsp:include page="snippet/ThresholdUpdateModal.jsp"/ --%>

    <script type="text/javascript">
        $(function () {
            $('.actions-links').on('click','.delete-icon', function() {
                var dataid = $(this).attr('data-id');
                $('.threshold-delete-confirm').attr("href", "mskThresholdDelete?pId=" + dataid);
            });

            // Event listener for threshold update click
            $('.threshold-update-link').click(function (e) {
                e.preventDefault();

                var thresholdId = $(this).data('id');
                showThresholdUpdateModal(thresholdId);
            });
        });
    </script>

</section>

</body>
</html>



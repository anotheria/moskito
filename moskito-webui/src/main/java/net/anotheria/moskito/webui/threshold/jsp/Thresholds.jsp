<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <ano:equal name="newThresholdAdded" value="true">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Threshold <ano:write name="newThresholdName"/> added!
        </div>
    </ano:equal>
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsestatus"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    System state
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
                        <th>Status <i class="fa fa-caret-down"></i></th>
                        <th>Value <i class="fa fa-caret-down"></i></th>
                        <th>Status change <i class="fa fa-caret-down"></i></th>
                        <th>Change Timestamp <i class="fa fa-caret-down"></i></th>
                        <th>FlipCount <i class="fa fa-caret-down"></i></th>
                        <th>Path <i class="fa fa-caret-down"></i></th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="thresholds" type="net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean" id="threshold" indexId="index">
                    <tr>
                        <td><a class="threshold-update-link" data-id="${threshold.id}" href="#">${threshold.name}</a></td>
                        <td><i class="status status-${threshold.colorCode}"></i></td>
                        <td>${threshold.value}</td>
                        <td><i class="status status-${threshold.previousColorCode}"></i> <i class="fa fa-long-arrow-right"></i> <i class="status status-${threshold.colorCode}"></i></td>
                        <td>${threshold.timestamp}</td>
                        <td>${threshold.flipCount}</td>
                        <td>${threshold.description}</td>
                        <td class="actions-links">
                            <a href="#mskThresholdDelete" data-toggle="modal" data-target="#mskThresholdDelete" data-id="${threshold.id}" class="action-icon delete-icon tooltip-bottom" title="Delete ${threshold.name}"><i class="fa fa-ban"></i></a>
                            <a href="#" class="action-icon edit-icon tooltip-bottom threshold-update-link" data-id="${threshold.id}" title="Edit ${threshold.name}"><i class="fa fa-pencil"></i></a>
                            <ano:iF test="${threshold.dashboardsToAdd != ''}">
                                <a onclick="addTresholds('${threshold.name}', '${threshold.dashboardsToAdd}');" href="#" class="action-icon edit-icon tooltip-bottom" title="Add ${threshold.name} to dashboard"><i class="fa fa-cog"></i></a>
                            </ano:iF>
                        </td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>

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
    <jsp:include page="snippet/ThresholdUpdateModal.jsp"/>

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



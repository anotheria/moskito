<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>

<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsestatus"><i class="fa fa-caret-right"></i></a>
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
                    <ano:iterate name="thresholds" type="net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO" id="threshold" indexId="index">
                    <tr>
                        <td><a href="mskThresholdEdit?pId=${threshold.id}">${threshold.name}</a></td>
                        <td><i class="status status-${threshold.colorCode}"></i></td>
                        <td>${threshold.value}</td>
                        <td><i class="status status-${threshold.previousColorCode}"></i> <i class="fa fa-long-arrow-right"></i> <i class="status status-${threshold.colorCode}"></i></td>
                        <td>${threshold.timestamp}</td>
                        <td>${threshold.flipCount}</td>
                        <td>${threshold.description}</td>
                        <td>
                            <a href="mskThresholdDelete?pId=${threshold.id}" class="action-icon delete-icon tooltip-bottom" title="Delete ${threshold.name}"><i class="fa fa-ban"></i></a>
                            <a href="mskThresholdEdit?pId=${threshold.id}" class="action-icon edit-icon tooltip-bottom" title="Edit ${threshold.name}"><i class="fa fa-pencil"></i></a>
                        </td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapsehistory"><i class="fa fa-caret-right"></i></a>
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
                        <th>Value change <i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="alerts" type="net.anotheria.moskito.webui.threshold.api.ThresholdAlertAO" id="alert" indexId="index">
                    <tr>
                        <td>${alert.timestamp}</td>
                        <td><a href="#">${alert.name}</a></td>
                        <td><i class="status status-${alert.oldColorCode}"></i> <i class="fa fa-long-arrow-right"></i> <i class="status status-${alert.newColorCode}"></i></td>
                        <td>${alert.oldValue} <i class="fa fa-long-arrow-right"></i> ${alert.newValue}</td>
                    </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>

</section>
</body>
</html>



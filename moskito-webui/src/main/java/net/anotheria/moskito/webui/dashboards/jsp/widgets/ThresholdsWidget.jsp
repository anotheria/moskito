<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>

<div class="dashboard-line">
    <div class="row">
        <ano:iterate name="thresholds" type="net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean" id="threshold">
            <div class="col-lg-3 col-md-3 col-sm-4">
                <div class="box threshold-item tooltip-bottom">
                    <i class="status status-${threshold.colorCode}"></i>
                    <span class="threshold-title">${threshold.name}&nbsp;${threshold.value}</span>
                    <div class="box-right-nav dropdown threshold-body">
                        <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                            <ano:iF test="${threshold.dashboardsToAdd != ''}">
                                <li><a onclick="addTresholds('${threshold.name}', '${threshold.dashboardsToAdd}')" >Add to Dashboard</a></li>
                            </ano:iF>
                            <li><a onclick="removeTresholds('${threshold.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </ano:iterate>
    </div>
</div>

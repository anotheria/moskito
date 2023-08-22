<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>

<!-- gauges js -->
<script language="JavaScript">
    var gauges = [];
    <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.bean.GaugeBean" id="gauge">
    gauges.push({
        "name": '${gauge.name}',
        "caption": '${gauge.caption}',
        "complete": ${gauge.complete},
        "min": ${gauge.min.rawValue},
        "current": ${gauge.current.rawValue},
        "max": ${gauge.max.rawValue},
        "zones": <ano:equal name="gauge" property="customZonesAvailable" value="false">[]</ano:equal>
            <ano:equal name="gauge" property="customZonesAvailable" value="true">
            [
        <ano:iterate id="zone" name="gauge" property="zones" type="net.anotheria.moskito.webui.gauges.api.GaugeZoneAO" indexId="zoneIndex"><ano:greaterThan name="zoneIndex" value="0">,</ano:greaterThan>
    {
        "color": '${zone.color}',
        "colorCode": '',
        "enabled": true,
        "from": ${zone.left},
        "to": ${zone.right}
    }
    </ano:iterate>
    ]
    </ano:equal>
    });
    </ano:iterate>
</script>

<!-- gauges -->
<div class="dashboard-line">
    <div class="row">
        <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.bean.GaugeBean" id="gauge" indexId="index">
            <div class="col-lg-3 col-md-4 col-sm-6">
                <div class="box gauge-item">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#gauge_collapse_chart${index}"><i class="fa fa-caret-down"></i></a>

                        <h3 class="pull-left chart-header">
                                ${gauge.caption}
                        </h3>
                        <div class="box-right-nav dropdown">
                            <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                            <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                                <li><a href="" onclick="saveGaugesSvgAsPng(event, ${index}, ${index})">Screenshot</a></li>
                                <ano:iF test="${gauge.dashboardsToAdd != ''}">
                                    <li><a onclick="addGauge('${gauge.caption}', '${gauge.name}', '${gauge.dashboardsToAdd}')" >Add to Dashboard</a></li>
                                </ano:iF>
                                <li><a onclick="removeGauge('${gauge.caption}', '${gauge.name}', '${requestScope.selectedDashboard}')">Remove</a></li>
                            </ul>
                        </div>
                    </div>
                    <div id="gauge_collapse_chart${index}" class="box-content accordion-body collapse in">
                        <div class="paddner text-center">
                            <div id="gaugeChart${index}" class="gauge-content gauge-chart">Not enough data</div>
                        </div>
                    </div>
                </div>
            </div>
        </ano:iterate>
    </div>

    <div class="dashboard-line-footer text-right">
        <ul class="dashboard-line-nav-box list-unstyled">
            <li>
                <a onclick="saveGaugesSvgAsPng(event, 0, 10000)" class="save_as"><i class="fa fa-download"></i> Screenshot all Gauges</a>
            </li>
        </ul>
    </div>
</div>
<!-- // end gauges -->

<script type="text/javascript">
    var gaugeContainerSelectors = $('.gauge-chart').map(function () {
        return $(this).attr("id");
    });

    gauges.forEach(function (gaugeData, index) {
        var chartParams = {
            container: gaugeContainerSelectors[index],
            data: gaugeData,
            type: 'GaugeChart'
        };

        chartEngineIniterV2.init(chartParams);
    });
</script>
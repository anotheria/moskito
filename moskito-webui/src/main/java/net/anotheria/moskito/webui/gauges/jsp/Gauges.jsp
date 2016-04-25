<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            Gauges are a new feature since MoSKito 2.5.7. We are not sure, if they should become their top navigation point. If you feel that gauges should become it own top navigation point,
            <a href="mailto:moskito-users@lists.anotheria.net">tell us</a>!
        </div>

    <div class="content">

        <!-- preparing gauges data -->
        <script language="JavaScript">
            var gauges = [];
            <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.api.GaugeAO" id="gauge">
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
                            <ano:iterate id="zone" name="gauge" property="zones" type="net.anotheria.moskito.webui.gauges.api.GaugeZoneAO" indexId="zoneIndex"><ano:greaterThan name="zoneIndex" value="0">, </ano:greaterThan>
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

        <div class="row">
            <ano:iterate name="gauges" type="net.anotheria.moskito.webui.gauges.api.GaugeAO" id="gauge" indexId="index">
                <div class="col-lg-3 col-md-4 col-sm-6">
                    <div class="box gauge-item">
                        <div class="box-title">
                            <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#gauge_collapse_chart${index}"><i class="fa fa-caret-right"></i></a>

                            <h3 class="pull-left">
                                    ${gauge.caption}
                            </h3>
                        </div>
                        <div id="gauge_collapse_chart${index}" class="box-content accordion-body collapse in">
                            <div class="paddner text-center">
                                <div id="gaugeChart${index}" class="gauge-content gauge-chart"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </ano:iterate>
        </div>

        <script type="text/javascript">
            var chartEngineName = '${chartEngine}' || 'GOOGLE_CHART_API';

            var gaugeContainerSelectors = $('.gauge-chart').map(function () {
                return $(this).attr("id");
            });

            gauges.forEach(function (gaugeData, index) {
                var chartParams = {
                    container: gaugeContainerSelectors[index],
                    data: gaugeData,
                    type: 'GaugeChart'
                };

                chartEngineIniter[chartEngineName](chartParams);
            });
        </script>

    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>

</body>
</html>



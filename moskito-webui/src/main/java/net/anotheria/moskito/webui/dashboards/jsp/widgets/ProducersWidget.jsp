<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>

<div class="dashboard-line">
    <ano:iterate type="net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean" id="decorator" name="decorators">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse${decorator.decoratorNameForCss}"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">${decorator.name}</h3>

                <ano:equal name="betaMode" value="true">
                    <div class="box-right-nav dropdown">
                        <a href="#" data-target="#" data-toggle="dropdown"><i class="fa fa-cog"></i></a>
                        <ul class="dropdown-menu dropdown-menu-right" aria-labelledby="dLabel">
                            <li>
                                <mos:deepLink href="mskShowCumulatedProducers?pDecorator=${decorator.name}&pCategory=${currentCategory}&pSubsystem=${currentSubsystem}">Cumulate</mos:deepLink>
                            </li>
                            <li>
                                <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="">Help</a>
                            </li>
                        </ul>
                    </div>
                </ano:equal>

                <ano:equal name="betaMode" value="false">
                    <div class="box-right-nav">
                        <a onclick="showProducerHelpModal('${decorator.name}');return false;" href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                    </div>
                </ano:equal>
            </div>
            <div id="collapse${decorator.decoratorNameForCss}" class="box-content accordion-body collapse in">


                <div class="table1fixed">
                    <table class="table table-striped tablesorter">
                        <thead>
                        <tr>
                            <th class="headcol">Producer Id <i class="fa fa-caret-down"></i></th>
                            <th>Category <i class="fa fa-caret-down"></i></th>
                            <th>Subsystem <i class="fa fa-caret-down"></i></th>
                            <ano:iterate name="decorator" property="captions" type="net.anotheria.moskito.core.decorators.value.StatCaptionBean" id="caption" indexId="ind">
                                <th title="${caption.shortExplanation}" class="{sorter: 'commaNumber'} table-column">
                                    <!-- variable for this graph is <ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/> -->
                                    <input type="hidden" value="<ano:write name="decorator" property="name"/>_<ano:write name="caption" property="jsVariableName"/>"/>${caption.caption}<i class="fa fa-caret-down"></i><i class="chart-icon tooltip-bottom" title="Show chart"></i>
                                </th>
                            </ano:iterate>
                            <th>Class</th>
                            <th class="th-actions"></th>
                        </tr>
                        </thead>
                        <tbody>
                            <%-- writing out values --%>
                        <ano:iterate name="decorator" property="producers" id="producer" type="net.anotheria.moskito.webui.producers.api.ProducerAO">
                            <tr>
                                <td class="headcol"><mos:deepLink  href="mskShowProducer?pProducerId=${producer.producerId}" class="tooltip-bottom" title="Show details for producer ${producer.producerId}">${producer.producerId}</mos:deepLink ></td>
                                <td><mos:deepLink  href="mskShowProducersByCategory?pCategory=${producer.category}">${producer.category}</mos:deepLink ></td>
                                <td><mos:deepLink  href="mskShowProducersBySubsystem?pSubsystem=${producer.subsystem}">${producer.subsystem}</mos:deepLink ></td>
                                <ano:iterate name="producer" property="firstStatsValues" id="value" type="net.anotheria.moskito.core.decorators.value.StatValueAO">
                                    <td class="tooltip-bottom" title="${producer.producerId}.${value.name}=${value.value}">${value.value}</td>
                                </ano:iterate>
                                <td>${producer.producerClassName}</td>

                                <td class="actions-links">
                                    <a onclick="removeProducer('${producer.producerId}', '<ano:write name="selectedDashboard" />');" href="#" class="action-icon delete-icon tooltip-bottom" title="Remove ${producer.producerId} from dashboard"><i class="fa fa-close"></i></a>
                                    <ano:notEmpty name="dashboardNames">
                                        <a onclick="addProducer('${producer.producerId}', '<ano:write name="dashboardNames" />');" href="#" class="action-icon add-icon tooltip-bottom" title="Add ${producer.producerId} to dashboard"><i class="fa fa-plus"></i></a>
                                    </ano:notEmpty>
                                </td>
                            </tr>
                        </ano:iterate>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </ano:iterate>
</div>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <div class="content">
        <div class="box">
            <div class="box-title">
                <h3 class="pull-left">
                    Errors in inmemory error catchers. (BETA)
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>

            <div id="collapseErrors" class="box-content accordion-body collapse in hscrollbar errors-box">
                <ano:notPresent name="catchers">
                    No catchers active.
                </ano:notPresent>
                <ano:present name="catchers">
                <table class="table table-striped tree">
                    <thead>
                    <tr>
                        <th>Exception</th>
                        <th>Catches</th>
                        <th>Type</th>
                        <th>Target</th>
                        <th>Configuration parameter</th>
                        <th>Inspectable</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="catchers" type="net.anotheria.moskito.webui.shared.api.ErrorCatcherAO" id="catcher">
                        <tr>
                            <ano:equal name="catcher" property="inspectable" value="true">
                                <td><mos:deepLink  href="mskError?error=${catcher.name}">${catcher.name}</mos:deepLink ></td>
                            </ano:equal>
                            <ano:notEqual name="catcher" property="inspectable" value="true">
                                <td>${catcher.name}</td>
                            </ano:notEqual>
                            <td>${catcher.count}</td>
                            <td>${catcher.type}</td>
                            <td>${catcher.target}</td>
                            <td>${catcher.configurationParameter}</td>
                            <td>${catcher.inspectable}</td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
                </ano:present>
            </div>
        </div>

    <div class="box">
        <div class="box-title">
            <h3 class="pull-left">
                Configuration and interpretation help.
            </h3>
            <div class="box-right-nav">
                <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
            </div>
        </div>

        <div id="collapseConfig" class="box-content accordion-body collapse in errors-box">
            <p>
            Understanding the above table:
                <ul>
                    <li>Exception - is class of exception to catch, or null or * for all.</li>
                    <li>Catches - number of exception catches by this catcher.</li>
                    <li>Type - type of the catcher, currently supported types are: EXCEPTION_BOUND: (one specific exception class), DEFAULT: all exceptions, CUSTOM: own implementation.</li>
                    <li>Target - Catchers target, can be log/memory or both or custom.</li>
                    <li>Configuration parameter - parameter that has been provided to catchers configuration, this can be logger name for example</li>
                    <li>Inspectable - if catchers keeps exceptions in memory, they are inspectable - the catcher can be clicked.</li>
                </ul>
            </p>

            <p>Check the field <em>@errorHandlingConfig</em> in the <mos:deepLink  href="mskConfig">configuration section</mos:deepLink > for information about currently configured error catchers. For more help on configuration options visit <a href="https://confluence.opensource.anotheria.net/display/MSK/MoSKito-Essential+Configuration+Guide">the configuration guide.</a>
            </p>
        </div>
    </div>
    </div>

    </div>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>
</body>
</html>
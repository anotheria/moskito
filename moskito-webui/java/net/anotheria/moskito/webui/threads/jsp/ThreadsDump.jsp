<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <div class="content">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Threads (${infosCount})
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in hscrollbar">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>ID <i class="fa fa-caret-down"></i></th>
                        <th>Name <i class="fa fa-caret-down"></i></th>
                        <th>State <i class="fa fa-caret-down"></i></th>
                        <th>inNative <i class="fa fa-caret-down"></i></th>
                        <th>suspened <i class="fa fa-caret-down"></i></th>
                        <th>Lock name <i class="fa fa-caret-down"></i></th>
                        <th>Lock owner id <i class="fa fa-caret-down"></i></th>
                        <th>Lock owner name <i class="fa fa-caret-down"></i></th>
                        <th>Blocked count <i class="fa fa-caret-down"></i></th>
                        <th>Blocked time <i class="fa fa-caret-down"></i></th>
                        <th>Waited count <i class="fa fa-caret-down"></i></th>
                        <th>Waited time <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="infos" type="net.anotheria.moskito.webui.threads.api.ThreadInfoAO" id="info">
                        <tr>
                            <td>${info.threadId}</td>
                            <td>${info.threadName}</td>
                            <td><ano:write name="info" property="threadState"/></td>
                            <td align="center"><ano:equal name="info" property="inNative" value="true">X</ano:equal></td>
                            <td align="center"><ano:equal name="info" property="suspended" value="true">X</ano:equal></td>
                            <td>${info.lockName}</td>
                            <td>${info.lockOwnerId}</td>
                            <td>${info.lockOwnerName}</td>
                            <td>${info.blockedCount}</td>
                            <td>${info.blockedTime}</td>
                            <td>${info.waitedCount}</td>
                            <td>${info.waitedTime}</td>
                        </tr>
                        <tr class="dump-list">
                            <td colspan="11">
                                <ul>
                                    <ano:iterate name="info" property="stackTrace" id="traceElement" type="java.lang.StackTraceElement">
                                        <li>${traceElement}</li>
                                    </ano:iterate>
                                </ul>
                            </td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>
</body>
</html>

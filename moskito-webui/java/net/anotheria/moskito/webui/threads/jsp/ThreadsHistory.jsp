<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>
<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-content paddner">
                <dl class="dl-horizontal">
                    <dt>Thread History is <ano:equal name="active" value="true"><b>ON</b> (<a href="mskThreadsHistoryOff?">OFF</a>)</ano:equal><ano:equal name="active" value="false"><b>OFF</b> (<a href="mskThreadsHistoryOn?">ON</a>)</ano:equal><b>ON</b></dt>
                    <dd><input type="checkbox" class="js-switch" checked /></dd>
                    <dt>Thread History size is</dt>
                    <dd>${listsize}</dd>
                </dl>
            </div>
        </div>


        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Threads
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Time <i class="fa fa-caret-down"></i></th>
                        <th>Operation <i class="fa fa-caret-down"></i></th>
                        <th>Id <i class="fa fa-caret-down"></i></th>
                        <th>Name <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="history" type="net.anotheria.moskito.core.util.threadhistory.ThreadHistoryEvent" id="item">
                        <tr>
                            <td>${item.niceTimestamp}</td>
                            <td>${item.operation}</td>
                            <td>${item.threadId}</td>
                            <td>${item.threadName}</td>
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

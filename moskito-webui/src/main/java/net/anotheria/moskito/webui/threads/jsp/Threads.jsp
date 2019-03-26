<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>

<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Threads stats
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Stat <i class="fa fa-caret-down"></i></th>
                        <th>Value <i class="fa fa-caret-down"></i></th>
                        <th>Explanation <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>Thread count:</td>
                        <td>${info.threadCount}</td>
                        <td>The current number of live threads including both daemon and non-daemon threads.</td>
                    </tr>
                    <tr>
                        <td>Daemon count:</td>
                        <td>${info.daemonThreadCount}</td>
                        <td>The current number of live daemon threads.</td>
                    </tr>
                    <tr>
                        <td>Peak:</td>
                        <td>${info.peakThreadCount}</td>
                        <td>The peak live thread count since the Java virtual machine started or peak was reset.</td>
                    </tr>
                    <tr>
                        <td>Total started:</td>
                        <td>${info.totalStarted}</td>
                        <td>The total number of threads created and also started since the Java virtual machine started.</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Threads capabilities
                </h3>
                <div class="box-right-nav">
                    <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
                </div>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped tablesorter">
                    <thead>
                    <tr>
                        <th>Stat <i class="fa fa-caret-down"></i></th>
                        <th>Value <i class="fa fa-caret-down"></i></th>
                        <th>Explanation <i class="fa fa-caret-down"></i></th>
                    </tr>
                    </thead>
                    </thead>
                    <tbody>
                    <tr>
                        <td>CPU time supported:</td>
                        <td><ano:write name="info" property="currentThreadCpuTimeSupported"/></td>
                        <td>Does the Java virtual machine supports CPU time measurement for the current thread.</td>
                    </tr>
                    <tr>
                        <td>Contention monitoring supported:</td>
                        <td><ano:write name="info" property="threadContentionMonitoringSupported"/></td>
                        <td>Does the Java virtual machine supports thread contention monitoring.</td>
                    </tr>
                    <tr>
                        <td>Contention monitoring enabled:</td>
                        <td><ano:write name="info" property="threadContentionMonitoringEnabled"/></td>
                        <td>Is thread contention monitoring enabled?</td>
                    </tr>
                    <tr>
                        <td>CPU Time supported:</td>
                        <td><ano:write name="info" property="threadCpuTimeSupported"/></td>
                        <td>Does the Java virtual machine supports CPU time measurement for the current thread.</td>
                    </tr>
                    <tr>
                        <td>CPU Time enabled:</td>
                        <td><ano:write name="info" property="threadCpuTimeEnabled"/></td>
                        <td>Is thread CPU time measurement enabled?</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <p class="text-right">&nbsp;&nbsp;<a href="http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/management/ThreadMXBean.html" class="btn btn-primary">See Javadoc for details</a><br/><br/></p>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>

</section>
</body>
</html>



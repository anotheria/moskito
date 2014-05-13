<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>
<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    MoSKito libs view.
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    Jars that are part of the classpath are scanned and listed on this page. If they are maven built (contain pom.properties file), the version info is extracted and presented.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Libs (${beansCount})
                </h3>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Group</th>
                        <th>Artifact</th>
                        <th>Version</th>
                        <th>Timestamp</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="beans" type="net.anotheria.moskito.webui.shared.api.LibAO" id="bean">
                        <tr>
                            <td>${bean.name}</td>
                            <td>${bean.group}</td>
                            <td>${bean.artifact}</td>
                            <td>${bean.version}</td>
                            <td>${bean.timestamp}</td>
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
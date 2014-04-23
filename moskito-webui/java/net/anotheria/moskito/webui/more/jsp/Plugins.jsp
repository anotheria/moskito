<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>

<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    MoSKito plugins.
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    Plugins that are currently present and loaded.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    Plugins (${pluginsCount})
                </h3>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Class</th>
                        <th>Configuration</th>
                        <th>Description</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="plugins" type="net.anotheria.moskito.webui.shared.api.PluginAO" id="plugin" indexId="index">
                        <tr>
                            <td>${plugin.name}</td>
                            <td>${plugin.className}</td>
                            <td>${plugin.configurationName}</td>
                            <td>${plugin.description}</td>
                            <td><a href="mskRemovePlugin?pPluginName=${plugin.name}" class="action-icon delete-icon tooltip-bottom" title="Delete"><i class="fa fa-ban"></i></a></td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>
</body>
</html>



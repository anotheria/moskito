<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
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
                    MoSKito Remotes.
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    Remotes are preconfigured connections or connections you added via the left bottom connect box. You can remove unwanted remotes here.
                    Remotes should only work in local mode. Removing remotes only alert the runtime, after a restart the remotes are back.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse2"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Remotes
                </h3>
            </div>
            <div id="collapse2" class="box-content accordion-body collapse in">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Link</th>
                        <th class="th-actions"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="remotes" type="net.anotheria.moskito.webui.shared.bean.LabelValueBean" id="remote" indexId="index">
                        <tr>
                            <td>${remote.label}</td>
                            <td><mos:deepLink  href="mskRemoveRemote?pName=${remote.value}" class="action-icon delete-icon tooltip-bottom" title="Remove"><i class="fa fa-ban"></i></mos:deepLink ></td>
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



<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="true"/>
<section id="main">
    <div class="content">

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
                <h3 class="pull-left">
                    MoSKito Update Check
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <dl class="dl-horizontal">
                        <dt>Your version:</dt>
                        <dd><ano:write name="moskito_maven_version"/></dd>
                        <dt>Central version:</dt>
                        <dd><ano:write name="version"/> from <ano:write name="versionTimestamp"/></dd>
                    </dl>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="true"/>

</section>
</body>
</html>
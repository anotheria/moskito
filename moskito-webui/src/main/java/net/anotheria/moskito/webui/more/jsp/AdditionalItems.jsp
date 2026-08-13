<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
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
                    Everything else.
                </h3>
            </div>
            <div id="collapse1" class="box-content accordion-body collapse in">
                <div class="paddner">
                    This section contains everything that wasn't <i>important enough</i> to get it's own top navigation point.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-config"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Config.
                </h3>
            </div>
            <div id="collapse-config" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <mos:deepLink href="mskConfig">The config section</mos:deepLink> displays current configuration.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-beans"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    MBeans.
                </h3>
            </div>
            <div id="collapse-beans" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <mos:deepLink href="mskMBeans">The MBeans section</mos:deepLink> displays all management beans that are present in the current JVM along with their attributes and values.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-libs"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Libs.
                </h3>
            </div>
            <div id="collapse-libs" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <mos:deepLink href="mskLibs">The library display</mos:deepLink> scans all jars in the classpath and displays information about name and version. It's useful for informational purposes only.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-topproducers"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Top Producers.
                </h3>
            </div>
            <div id="collapse-topproducers" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <mos:deepLink href="mskTopProducers">The top producers</mos:deepLink> section ranks the producers that consume the most resources (requests, time, errors, error rate, max concurrent requests) over time, so you can spot optimization targets. The same ranking is exposed to LLMs via the MCP endpoint.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-plugins"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Plugins.
                </h3>
            </div>
            <div id="collapse-plugins" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <mos:deepLink href="mskPlugins">The plugin</mos:deepLink> section displays information about configured and loaded plugins.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-update"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Update.
                </h3>
            </div>
            <div id="collapse-update" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <a href="mskUpdate">The update check</a> connects to maven central and displays the last available version of MoSKito, in case you should update. It calls this <a target="_blank" href="http://search.maven.org/solrsearch/select?q=a:%22moskito-webui%22&rows=20&wt=json">central url</a>.
                </div>
            </div>
        </div>

        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse-remote"><i class="fa fa-caret-down"></i></a>
                <h3 class="pull-left">
                    Remotes.
                </h3>
            </div>
            <div id="collapse-remote" class="box-content accordion-body collapse in">
                <div class="paddner">
                    <a href="mskShowRemotes">Edit remote moskito-core instances</a>. The 'remote' moskito-core instances are other VMs that are preconfigured via moskito-inspect.json and can be accessed from this UI. This is useful for distributed applications.
                </div>
            </div>
        </div>

    </div>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>



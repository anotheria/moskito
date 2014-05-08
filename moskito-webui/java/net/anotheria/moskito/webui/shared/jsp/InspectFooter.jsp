<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<footer id="footer" class="navbar-default">
    <p class="text-center">Generated at ${timestampAsDate}  |  timestamp: ${timestamp}  |  Interval updated at: ${currentIntervalUpdateTimestamp}  |   Interval age: ${currentIntervalUpdateAge}</p>
    <p class="text-center">App version: ${application_maven_version}  |  MoSKito version: ${moskito_maven_version} | Server: ${servername} | Connection: ${connection}</p>
    <ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/inspect/${applicationScope.moskito_version_string}/${pagename}" class="ipix"> </ano:equal>
</footer>

<script src="../ext/tablesorter/jquery.tablesorter.js" type="text/javascript"></script>
<script src="../ext/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="../ext/select2-3.4.6/select2.js" type="text/javascript"></script>
<script src="../int/js/common.js" type="text/javascript"></script>
<script src="../ext/switchery/switchery.min.js" type="text/javascript"></script>

<ano:equal name="currentSubNaviItem" property="id" value="more_config">
    <script type="text/javascript" src="../ext/google-code-prettify/prettify.js"></script>
    <script type="text/javascript" src="../ext/google-code-prettify/application.js"></script>
</ano:equal>

<ano:equal name="currentNaviItem" property="id" value="journeys">
    <script type="text/javascript" src="../ext/treegrid/js/jquery.treegrid.js"></script>
    <script type="text/javascript" src="../ext/treegrid/js/jquery.treegrid.bootstrap3.js"></script>
</ano:equal>

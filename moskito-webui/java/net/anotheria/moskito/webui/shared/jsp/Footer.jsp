<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<footer id="footer" class="navbar-default">
    <p class="text-center">Generated at ${timestampAsDate}  |  timestamp: ${timestamp}  |  Interval updated at: ${currentIntervalUpdateTimestamp}  |   Interval age: ${currentIntervalUpdateAge}</p>
    <p class="text-center">App version: ${application_maven_version}  |  MoSKito version: ${moskito_maven_version} | Server: ${servername} | Connection: ${connection}</p>
    <ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/inspect/${applicationScope.moskito_version_string}/${pagename}" class="ipix"> </ano:equal>
</footer>

<script src="../moskito/ext/jquery-1.10.2/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../moskito/ext/bootstrap-3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../moskito/ext/tablesorter/jquery.metadata.js" type="text/javascript"></script>
<script src="../moskito/ext/tablesorter/jquery.tablesorter.js" type="text/javascript"></script>
<script src="../moskito/ext/select2-3.4.6/select2.js" type="text/javascript"></script>

<script src="../moskito/ext/switchery/switchery.min.js" type="text/javascript"></script>

<ano:equal name="currentSubNaviItem" property="id" value="more_config">
    <script type="text/javascript" src="../moskito/ext/google-code-prettify/prettify.js"></script>
    <script type="text/javascript" src="../moskito/ext/google-code-prettify/application.js"></script>
</ano:equal>

<ano:equal name="currentNaviItem" property="id" value="journeys">
    <script type="text/javascript" src="../moskito/ext/jquery-tree-table/jquery.treeTable.min.js"></script>
</ano:equal>

<ano:equal name="currentSubNaviItem" property="id" value="more_mbeans">
    <script type="text/javascript" src="../moskito/ext/jquery-tree-table/jquery.treeTable.min.js"></script>
</ano:equal>

<script src="../moskito/ext/custom-scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>

<script src="../moskito/int/js/common.js" type="text/javascript"></script>
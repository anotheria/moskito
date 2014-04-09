<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<footer id="footer" class="navbar-default">
    <p class="text-center">Generated at ${timestampAsDate}  |  timestamp: ${timestamp}  |  Interval updated at: ${currentIntervalUpdateTimestamp}  |   Interval age: ${currentIntervalUpdateAge}</p>
    <p class="text-center">App version: ${application.maven.version}  |  MoSKito version: ${moskito.maven.version} | Server: ${servername} || Connection: ${connection}</p>
    <ano:equal name="config" property="trackUsage" value="true"><img src="//counter.moskito.org/counter/webui/${moskito.version_string}/${pagename}" class="ipix"> </ano:equal>
</footer>

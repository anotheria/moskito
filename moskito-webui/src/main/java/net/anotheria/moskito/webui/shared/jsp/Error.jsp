<%@ page import="net.anotheria.maf.bean.ErrorBean" %>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="Header.jsp"/>
<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">

        <div class="container text-left error_page_details_content display_none_property" style="padding-bottom: 30px; display: block;">
            <div class="text-center" style="margin-bottom: 30px">
                <img src="../moskito/int/img/moskito.png" height="100">
                <h4>MoSKito encountered an error:</h4>

                <h1>
                    <ano:write name="maf.error" property="message"/>
                </h1>
            </div>

            <span class="liner"></span>

            <%
                // Preparing stack trace array
                String stackTraceString = ((ErrorBean) request.getAttribute("maf.error")).getStackTrace();
                String[] stackTrace = stackTraceString.replace("[", "").replace("]", "").split(", ");
                request.setAttribute("stackTrace", stackTrace);
            %>
            <div class="stack-trace-container">
                <ano:iterate name="stackTrace" id="traceStep">
                    <ano:write name="traceStep" /><br/>
                </ano:iterate>
            </div>
        </div>

    </div>
    <!-- FOOTER -->
    <jsp:include page="Footer.jsp" flush="false"/>
</section>
</body>
</html>

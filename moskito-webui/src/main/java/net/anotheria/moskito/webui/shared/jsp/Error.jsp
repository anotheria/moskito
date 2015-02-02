<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="Header.jsp"/>
<section id="main">
    <div class="content">

        <img src="../moskito/int/img/moskito.png" height="100">
        <h1>MoSKito encountered an error:</h1>
        <span class="liner"></span>
        <p>
            <ano:write name="maf.error" property="message"/>
        </p>
        <div class="container text-left error_page_details_content display_none_property" style="padding-bottom: 30px; display: block;">
            <ano:write name="maf.error" property="stackTrace"/>
        </div>

    </div>
    <!-- FOOTER -->
    <jsp:include page="Footer.jsp" flush="false"/>
</section>
</body>
</html>

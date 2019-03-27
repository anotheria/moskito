<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<%--
    This page displays the stack traces of a single error.
--%>
<section id="main">
    <jsp:include page="../../shared/jsp/Alerts.jsp"/>

    <div class="content">

        <div class="box">
            <div class="box-title">
                <h3 class="pull-left">
                    Caught errors for ${clazz}.
                </h3>
            </div>
            <div class="box-content">
                <table class="table table-striped table-tree tree">
                    <thead>
                    <tr>
                        <th></th>
                        <th>Date</th>
                        <th>Message</th>
                        <th>Tags</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate name="caughtErrors" type="net.anotheria.moskito.webui.shared.api.CaughtErrorAO " id="ce">
                        <tr data-level="0">
                            <td>
                                <div>
                                    <i class="minus">–</i><i class="plus">+</i><i class="vline"></i>
                                </div>
                            </td>
                            <td>${ce.date}</td>
                            <td>${ce.message}</td>
                            <td>
                                <ul class="tags-list tags-inline">
                                    <ano:iterate id="tag" name="ce" property="tags">
                                        <li>
                                            <span class="tag-name"><ano:write name="tag" property="tagName"/>: </span><ano:write name="tag" property="tagValue"/>
                                        </li>
                                    </ano:iterate>
                                </ul>
                            </td>
                        </tr>
                        <tr class="dump-list" data-level="1">
                            <td colspan="4">
                                <ul>
                                    <ano:iterate name="ce" property="elements" id="element" type="java.lang.StackTraceElement">
                                        <li>${element}</li>
                                    </ano:iterate>
                                </ul>
                            </td>
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



<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <ano:empty name="tags">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            You have no tags yet, you can add new tags using MoSKito configuration or form in modal window.
        </div>
    </ano:empty>

    <div class="content">
        <div class="box">
            <div class="box-content paddner">
                <div class="pull-right">
                    <a class="btn btn-default" data-toggle="modal" data-target="#addTagModal" href=""><i class="fa fa-plus"></i> Tag</a>
                </div>
            </div>
        </div>

        <ano:notEmpty name="tags">
        <div class="box">
            <div class="box-title">
                <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseTags">
                    <i class="fa fa-caret-down"></i>
                </a>
                <h3 class="pull-left">
                    Tags
                </h3>
            </div>
            <div id="collapseTags" class="box-content accordion-body collapse in">
                <table class="table table-striped tree">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Source</th>
                        <th>Last values</th>
                    </tr>
                    </thead>
                    <tbody>
                        <ano:iterate name="tags" type="net.anotheria.moskito.webui.tags.bean.TagBean" id="tag" indexId="index">
                            <tr data-level="0">
                                <td><i class="minus">–</i><i class="plus">+</i>${tag.name}</td>
                                <td>${tag.type.name}</td>
                                <td>${tag.source}</td>
                                <td>
                                    <ano:notEmpty name="tag" property="lastValues">
                                        ${tag.lastValuesTruncatedString}
                                    </ano:notEmpty>
                                </td>
                            </tr>
                            <ano:notEmpty name="tag" property="lastValues">
                                <tr data-level="1" class="treegrid-parent">
                                    <td><b>Last tag values:</b></td>
                                    <td colspan="3">
                                        <table class="table table-striped">
                                            <thead>
                                            <tr>
                                                <th>Value</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <ano:iterate name="tag" property="lastValues" id="value">
                                                <tr>
                                                    <td>${value}</td>
                                                </tr>
                                            </ano:iterate>
                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </ano:notEmpty>
                        </ano:iterate>
                    </tbody>
                </table>
            </div>
        </div>
        </ano:notEmpty>
    </div>

    <jsp:include page="AddTagModal.jsp"/>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <ano:empty name="tags">
        <div class="alert alert-success alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            You have no tags yet, you can add new tags using MoSKito configuration or form in modal window.
        </div>
    </ano:empty>

    <div class="content">
        <div class="tags-wrapper">
        <div>
            <ano:notEmpty name="tags">
                <ano:iterate name="tags" type="net.anotheria.moskito.webui.tags.bean.TagBean" id="tag" indexId="index">
                    <div class="card-box">
                        <div class="panel panel-default card-item">
                            <div class="panel-heading card-header card-color-${tag.type.name}">
                                <i class="fa fa-tag card-tag-icon"></i>${tag.name}
                            </div>
                            <div class="panel-body card-body">
                                <p><strong>Type: </strong>${tag.type.name}</p>
                                <p><strong>Source: </strong>${tag.source}</p>
                            </div>
                            <div id="tag-${index}-values" class="collapse-section collapse">
                                <ano:iterate name="tag" property="lastValues" id="value">
                                    <div class="card-section text-muted">${value}</div>
                                </ano:iterate>
                            </div>
                            <a href="#tag-${index}-values" class="collapse-toggle collapsed" role="button" data-toggle="collapse" aria-expanded="false" aria-controls="tag-${index}-values">
                                <div class="panel-footer card-footer">
                                    <i class="fa fa-chevron-down"></i>
                                </div>
                            </a>
                            <div class="card-footer-line card-color-${tag.type.name}"></div>
                        </div>
                    </div>
                </ano:iterate>
            </ano:notEmpty>

            <div class="add-tag-box">
                <a href="" data-toggle="modal" data-target="#addTagModal">
                    <div class="add-tag-card">
                        <div class="add-icon"><i class="fa fa-plus"></i></div>
                    </div>
                </a>
            </div>
        </div>
        </div>
    </div>

    <script src="../moskito/int/js/tags.js" type="text/javascript"></script>

    <jsp:include page="AddTagModal.jsp"/>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>
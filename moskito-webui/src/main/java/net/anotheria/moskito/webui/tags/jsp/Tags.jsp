<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>
<%@ taglib prefix="mos" uri="http://www.moskito.org/inspect/tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<jsp:include page="../../shared/jsp/Header.jsp" flush="false"/>
<section id="main">
    <ano:notPresent name="tags">
        <div class="alert alert-warning alert-dismissable">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            You have no tags yet, you can add new tags using MoSKito configuration or form below.
        </div>
    </ano:notPresent>

    <div class="content">
        <div class="row">
            <div class="col-md-9 tag-box">
                <div class="box">
                    <div class="box-title">
                        <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapseTags"><i class="fa fa-caret-down"></i></a>
                        <h3 class="pull-left">
                            Tags
                        </h3>
                    </div>
                    <div id="collapseTags" class="box-content">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>Name <i class="fa fa-caret-down"></i></th>
                                <th>Prefix <i class="fa fa-caret-down"></i></th>
                                <th>Attribute name <i class="fa fa-caret-down"></i></th>
                                <th colspan="<ano:write name="tagHistorySize" />">Last tag values</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ano:present name="tags">
                                <ano:iterate name="tags" type="net.anotheria.moskito.webui.tags.bean.TagBean" id="tag" indexId="index">
                                    <tr>
                                        <td>${tag.name}</td>
                                        <td>${tag.prefix.name}</td>
                                        <td>${tag.attributeName}</td>
                                        <ano:iterate name="tag" property="lastAttributeValues" id="value">
                                            <td>${value}</td>
                                        </ano:iterate>
                                    </tr>
                                </ano:iterate>
                            </ano:present>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <div class="col-md-3 tag-box">
                <div class="box tag-form-box">
                    <div class="box-title">
                        <h3 class="pull-left">
                            Add new tag
                        </h3>
                    </div>
                    <div class="box-content">
                        <p>Tag will be added to MoSKito context</p>
                        <div class="form-group">
                            <form id="tagForm" name="tagForm" action="mskAddTag" method="POST">
                                <div class="form-group">
                                    <input id="tagNameInput" type="text" name="pTagName" class="form-control" placeholder="Enter tag name" required>
                                </div>
                                <div class="form-group">
                                    <select id="prefixSelectInput" name="pPrefix" class="form-control" required>
                                        <option disabled value selected="selected">Select attribute prefix</option>
                                        <ano:iterate name="tagPrefixes" id="prefix" type="net.anotheria.moskito.core.config.tagging.TagPrefix">
                                            <option value="${prefix.name}">${prefix.name}</option>
                                        </ano:iterate>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input id="attributeNameInput" type="text" name="pAttributeName" class="form-control" placeholder="Enter attribute name" required>
                                </div>
                                <div class="form-group">
                                    <input type="submit" id="addTagButton" value="Add" class="btn btn-success btn-block" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false"/>
</section>
</body>
</html>
<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib prefix="ano" uri="http://www.anotheria.net/ano-tags" %>

<div class="modal fade" id="addTagModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Add new tag</h4>
            </div>
            <form id="tagForm" name="tagForm" action="mskAddTag" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <input id="tagNameInput" type="text" name="pTagName" class="form-control" placeholder="Enter tag name" required>
                    </div>
                    <div class="form-group">
                        <select id="attributeSourceSelectInput" name="pAttributeSource" class="form-control" required>
                            <option disabled value selected="selected">Select attribute source</option>
                            <ano:iterate name="attributeSources" id="source" type="net.anotheria.moskito.core.config.tagging.CustomTagSource">
                                <option value="${source.name}">${source.name}</option>
                            </ano:iterate>
                        </select>
                    </div>
                    <div class="form-group">
                        <input id="attributeNameInput" type="text" name="pAttributeName" class="form-control" placeholder="Enter attribute name" required>
                    </div>
                </div>
                <div class="modal-footer text-center">
                    <input type="submit" id="addTagButton" value="Add" class="btn btn-success" />
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                </div>
            </form>
        </div>
    </div>
</div>

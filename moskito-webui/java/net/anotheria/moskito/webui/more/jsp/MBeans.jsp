<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<jsp:include page="../../shared/jsp/InspectHeader.jsp" flush="false"/>
  <script type="text/javascript" src="../ext/treegrid/js/jquery.treegrid.js"></script>
<script type="text/javascript" src="../ext/treegrid/js/jquery.treegrid.bootstrap3.js"></script>

<section id="main">
<div class="content">

<div class="box">
<div class="box-title">
    <a class="accordion-toggle tooltip-bottom" title="Close/Open" data-toggle="collapse" href="#collapse1"><i class="fa fa-caret-right"></i></a>
    <h3 class="pull-left">
        MBeans (${mbeansCount})
    </h3>
    <div class="box-right-nav">
        <a href="" class="tooltip-bottom" title="Help"><i class="fa fa-info-circle"></i></a>
    </div>
</div>
<div id="collapse1" class="box-content accordion-body collapse in">
<table class="table table-striped tree">
<thead>
<tr>
    <th>Domain</th>
    <th>Type</th>
    <th>Description</th>
    <th>Class</th>
    <th>Attributes</th>
    <th>Operations</th>
    <th>Canonical name</th>
</tr>
</thead>
<tbody>
<ano:iterate name="mbeans" type="net.anotheria.moskito.webui.shared.api.MBeanWrapperAO" id="mbean" indexId="index">
    <tr class="treegrid-${index}">
        <td>${mbean.domain}</td>
        <td>${mbean.type}</td>
        <td>${mbean.description}</td>
        <td>${mbean.className}</td>
        <td>${mbean.attributesCount}</td>
        <td>${mbean.operationsCount}</td>
        <td>${mbean.canonicalName}</td>
    </tr>
    <ano:greaterThan name="mbean" property="attributesCount" value="0">
        <tr class="treegrid-${index}-sub treegrid-parent-${index} treegrid-parent">
            <td><b>Attributes:</b></td>
            <td colspan="6">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>Value</th>
                        <th>Description</th>
                        <th>Read</th>
                        <th>Write</th>
                    </tr>
                    </thead>
                    <tbody>
                    <ano:iterate id="info" name="mbean" property="attributes" type="net.anotheria.moskito.webui.shared.api.MBeanAttributeWrapperAO" indexId="index2">
                        <tr>
                            <td>${info.name}</td>
                            <td>${info.type}</td>
                            <td>${info.value}</td>
                            <td>${info.description}</td>
                            <td><ano:equal name="info" property="readable" value="true">+</ano:equal></td>
                            <td><ano:equal name="info" property="writable" value="true">+</ano:equal></td>
                        </tr>
                    </ano:iterate>
                    </tbody>
                </table>
            </td>
        </tr>
    </ano:greaterThan>
</ano:iterate>
</tbody>
</table>
</div>
</div>

</div>
<jsp:include page="../../shared/jsp/InspectFooter.jsp" flush="false"/>

</section>
</body>
</html>
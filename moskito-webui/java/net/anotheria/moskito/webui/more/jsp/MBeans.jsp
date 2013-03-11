<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito MBeans</title>
<link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />
<div class="main">
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="in">
			<h2><span>MBeans (<ano:write name="mbeansCount"/>)</span></h2>

			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
					<div class="scroller_x">
					<table cellpadding="0" cellspacing="0" width="100%">
						<thead>
						<tr>
							<th>Description</th>
							<th>Domain</th>
							<th>Class</th>
							<th>Attributes</th>
							<th>Operations</th>
							<th>Canonical name</th>
 						</tr>
						</thead>
						<tbody>
						<ano:iterate name="mbeans" type="net.anotheria.moskito.webui.more.bean.MBeanWrapperBean" id="mbean" indexId="index">
                        <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %> intree">
                            <td><ano:write name="mbean" property="domain"/></td>
                            <td><ano:write name="mbean" property="description"/></td>
                            <td><ano:write name="mbean" property="className"/></td>
                            <td><ano:write name="mbean" property="attributesCount"/></td>
                            <td><ano:write name="mbean" property="operationsCount"/></td>
                            <td><ano:write name="mbean" property="canonicalName"/></td>
                        </tr>
                        <ano:greaterThan name="mbean" property="attributesCount" value="0">
                        <tr class="odd intable">
                            <td valign="top">Attributes:&nbsp;</td>
                            <td colspan="5">
                            <table cellpadding="0" cellspacing="0" width="100%">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Type</th>
                                    <th>Description</th>
                                    <th>Read</th>
                                    <th>Write</th>
                                </tr>
                                </thead>
                                <tbody>
                                <ano:iterate id="info" name="mbean" property="attributes" type="javax.management.MBeanAttributeInfo" indexId="index2">
                                <tr class="<%= ((index2 & 1) == 0 )? "even" : "odd" %>">
                                    <td><ano:write name="info" property="name"/></td>
                                    <td><ano:write name="info" property="type"/></td>
                                    <td><ano:write name="info" property="description"/></td>
                                    <td><ano:equal name="info" property="readable" value="true">x</ano:equal></td>
                                    <td><ano:equal name="info" property="writable" value="true">x</ano:equal></td>
                                </tr>
                                </ano:iterate>
                                </tbody>
                            </table>
                            </td>
                        </tr>
                        </ano:greaterThan>
                            <%--
							<ano:iterate name="info" property="stackTrace" id="traceElement" type="java.lang.StackTraceElement">
							<tr class="odd">
								<td>&nbsp;</td>
								<td colspan="11">&nbsp;&nbsp;&nbsp;&nbsp;<ano:write name="traceElement"/></td>
							</tr>
							</ano:iterate>
							--%>
						</ano:iterate>
						</tbody>
					</table>
						</div>
					<div class="clear"><!-- --></div>
				</div>
				<div class="bot">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
			</div>
		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>
	<div class="clear"><!-- --></div>
    <jsp:include page="../../shared/jsp/Footer.jsp" flush="false" />
</div>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
    $(function() {
        $("table tr.intree").click(function(){
            $(this).next("tr.intable").toggle();
            $(this).toggleClass("active");
        });
    });
</script>
</body>
</html>


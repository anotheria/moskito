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
<div class="main inbl">
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
					<table cellpadding="0" cellspacing="0" width="100%" class="wrap-table">
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
						<ano:iterate name="mbeans" type="net.anotheria.moskito.webui.more.bean.MBeanWrapperBean" id="mbean" indexId="index">
                        <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %> intree">
                            <td valign="top"><ano:write name="mbean" property="domain"/></td>
                            <td valign="top"><ano:write name="mbean" property="type"/></td>
                            <td valign="top"><ano:write name="mbean" property="description"/></td>
                            <td valign="top"><ano:write name="mbean" property="className"/></td>
                            <td valign="top"><ano:write name="mbean" property="attributesCount"/></td>
                            <td valign="top"><ano:write name="mbean" property="operationsCount"/></td>
                            <td valign="top"><ano:write name="mbean" property="canonicalName"/></td>
                        </tr>
                        <ano:greaterThan name="mbean" property="attributesCount" value="0">
                        <tr class="odd intable">
                            <td valign="top" colspan="7">
                                <div class="oflayers">
                                    <table cellpadding="0" cellspacing="0" width="100%">
                                        <thead>
                                        <tr>
                                            <th width="200">Attributes:</th>
                                            <th width="200">Name</th>
                                            <th width="100">Type</th>
                                            <th width="200">Value</th>
                                            <th width="400">Description</th>
                                            <th width="100">Read</th>
                                            <th width="100">Write</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <ano:iterate id="info" name="mbean" property="attributes" type="net.anotheria.moskito.webui.more.bean.MBeanAttributeWrapper" indexId="index2">
                                        <tr class="<%= ((index2 & 1) == 0 )? "even" : "odd" %>">
                                            <td>&nbsp;</td>
                                            <td valign="top"><ano:write name="info" property="name"/></td>
                                            <td valign="top"><ano:write name="info" property="type"/></td>
                                            <td valign="top"><ano:write name="info" property="value"/></td>
                                            <td valign="top"><ano:write name="info" property="description"/></td>
                                            <td valign="top"><ano:equal name="info" property="readable" value="true">x</ano:equal></td>
                                            <td valign="top"><ano:equal name="info" property="writable" value="true">x</ano:equal></td>
                                        </tr>
                                        </ano:iterate>
                                        </tbody>
                                    </table>
                                </div>
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
<div class="black_bg hide"><!-- --></div>
<script type="text/javascript" src="../js/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
    $(function() {
        $('table tr.intree').click(function(){
            $(this).next('tr.intable').show();
            $(this).addClass('active');
            $('.black_bg').fadeIn();
            $('.main_menu').css("z-index","210");
        })
        $('.black_bg').click(function(){
            $('table tr.intree').next('tr.intable').hide();
            $('table tr.intree').removeClass('active');
            $('.black_bg').fadeOut();
            $('.main_menu').css("z-index","2");
        });
    });
</script>
</body>
</html>


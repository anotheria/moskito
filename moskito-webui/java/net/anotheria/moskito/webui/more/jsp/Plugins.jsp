<%@ page language="java" contentType="text/html;charset=UTF-8"	session="true"
        %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
        %><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Moskito Libs</title>
    <link rel="stylesheet" href="mskCSS"/>
</head>
<body>
<jsp:include page="../../shared/jsp/Menu.jsp" flush="false" />

<div class="main">
    <div class="additional">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="add_in">
            <h2>MoSKito config view</h2>

            <div><span>
Plugins that are currently present and loaded.
			</span></div>

        </div>
        <div class="bot">
            <div><!-- --></div>
        </div>
    </div>

    <div class="clear"><!-- --></div>
    <div class="table_layout">
        <div class="top">
            <div><!-- --></div>
        </div>
        <div class="in">
            <h2><span>Plugins (<ano:write name="pluginsCount"/>)</span></h2>

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
                                <th>Name</th>
                                <th>Class</th>
                                <th>Configuration</th>
                                <th>Description</th>
                                <th>&nbsp;</th>
                            </tr>
                            </thead>
                            <tbody>
                            <ano:iterate name="plugins" type="net.anotheria.moskito.webui.more.bean.PluginBean" id="plugin" indexId="index">
                                <tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
                                    <td><ano:write name="plugin" property="name"/></td>
                                    <td><ano:write name="plugin" property="className"/></td>
                                    <td><ano:write name="plugin" property="configurationName"/></td>
                                    <td><ano:write name="plugin" property="description"/></td>
                                    <td><a href="mskRemovePlugin?pPluginName=<ano:write name="plugin" property="name"/>">Remove</a></td>
                                </tr>
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
</body>
</html>


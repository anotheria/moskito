<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
    %><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"
    %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"
    %><%@ page isELIgnored="false" %>
<%@ taglib uri="http://www.moskito.org/inspect/tags" prefix="mos" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>${title}</title>
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/bootstrap-3.1.1/css/bootstrap.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/custom-scrollbar/jquery.mCustomScrollbar.css" />
    <link type="text/css" rel="stylesheet" href="../moskito/ext/font-awesome-4.3.0/css/font-awesome.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/font/style.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/select2-3.4.6/select2.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/switchery/switchery.min.css" />
    <ano:equal name="currentSubNaviItem" property="id" value="more_config">
        <link rel="stylesheet" type="text/css" href="../moskito/ext/google-code-prettify/prettify.css" />
    </ano:equal>
    <ano:equal name="currentSubNaviItem" property="id" value="more_mbeans">
        <link rel="stylesheet" type="text/css" href="../moskito/ext/treegrid/css/jquery.treegrid.css" />
    </ano:equal>
    <ano:equal name="currentNaviItem" property="id" value="journeys">
        <link rel="stylesheet" type="text/css" href="../moskito/ext/treegrid/css/jquery.treegrid.css" />
    </ano:equal>
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/int/css/common.css" />
    <!--[if lt IE 9]><script src="../int/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <!--[if lt IE 8]><link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/static-int/css/bootstrap-ie7.css" /><![endif]-->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <%--for d3ds--%>
    <style>
        .legend{
            margin-bottom:76px;
            display:inline-block;
            border-collapse: collapse;
            border-spacing: 0px;
        }
        .legend td{
            padding:4px 5px;
            vertical-align:bottom;
        }
    </style>

    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../moskito/ext/jquery.qtip2-3.0.3/jquery.qtip.min.css" />
</head>

<body class="status-${systemStatusColor}<ano:notEmpty name="isNavMenuCollapsed"><ano:iF test="${isNavMenuCollapsed}"> aside-collapse</ano:iF></ano:notEmpty>">
<ano:define name="moskito.CurrentUnit" property="unitName" id="currentUnit" toScope="page" type="java.lang.String"/>
<!-- currently for handle select only -->
<script type="text/javascript" src="../moskito/ext/jquery-1.10.2/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../moskito/ext/lodash-4.13.1/lodash.min.js"></script>
<script type="text/javascript" src="../moskito/ext/jquery.qtip2-3.0.3/jquery.qtip.min.js"></script>
<script type="text/javascript" src="../moskito/int/js/function.js"></script>
<script type="text/javascript" src="../moskito/ext/d3/d3.min.js" charset="utf-8"></script>
<script type="text/javascript" src="../moskito/int/js/chartEngineIniter.js?v=5"></script>

<ano:notEmpty name="graphDatas">
    <!-- Data for action -->
    <script>
        // Generating data for stat value charts, where key is graph jsVariableName and values: chart caption and array of chart values
        var graphData = {
            <ano:iterate type="net.anotheria.moskito.webui.shared.bean.GraphDataBean" id="graph" name="graphDatas">
            "<ano:write name="graph" property="jsVariableName"/>": {
                caption: "<ano:write name="graph" property="caption"/>",
                values: <ano:write name="graph" property="jsArrayValue"/>
            },
            </ano:iterate>
        };
    </script>
    <!-- -->
</ano:notEmpty>

<header id="header" class="navbar navbar-fixed-top navbar-default">
            <span class="caret-aside pull-left tooltip-bottom" title="Close/Open">
                <i class="fa fa-caret-left"></i>
            </span>

    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>

    <div class="navbar-collapse collapse">
        <div class="text-center"> 
            <form role="form" class="navbar-form navbar-left">
            <div class="form-group">
                <select class="select2" data-placeholder="Interval" onchange="handleSelect(this)">
                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                        <option value="${linkToCurrentPage}&amp;pInterval=${interval.name}" ${interval.name==requestScope.currentInterval ? "selected" : ""}>
                            <ano:write name="interval" property="name"/>
                        </option>
                    </ano:iterate>
                </select>
            </div>
            <div class="form-group">
                <select class="select2" data-placeholder="Unit" onchange="handleSelect(this)">
                <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                    <option value="${linkToCurrentPage}&amp;pUnit=${unit.unitName}" ${unit.unitName.equals(currentUnit) ? "selected" : ""}>
                        ${unit.unitName}
                    </option>
                </ano:iterate>
                </select>
            </div>
        </form>

        <span class="header-inform">Interval age: ${currentIntervalUpdateAge} | Server: ${servername} | Connection: ${connection}</span>

        <ul class="nav navbar-nav pull-right">
            <%-- removing autoreload feature for now, we can readd it later --%>
            <%-- <li><a href="">Autoreload OFF</a></li> --%>
            <ano:equal name="exportSupported" value="true">
            <li class="dropdown">
                <a data-toggle="dropdown" href="#">Export</a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                    <li><a target="_blank" href="<ano:write name="linkToCurrentPageAsXml"/>&amp;pForward=xml">XML</a></li>
                    <li><a target="_blank" href="<ano:write name="linkToCurrentPageAsJson"/>&amp;pForward=json">JSON</a></li>
                    <li><a target="_blank" href="<ano:write name="linkToCurrentPageAsCsv"/>&amp;pForward=csv">CSV</a></li>
                </ul>
            </li>
            </ano:equal>
                <ano:iF test="${mskIsAuthorized}">
                    <li>
                        <a href="mskSignOut">Sign Out</a>
                    </li>
                </ano:iF>
            <li><a href="mskShowExplanations"><i class="fa fa-info-circle icon-16"></i></a></li>
            <li class="iphone-app-link"><a href="https://itunes.apple.com/us/app/moskito-ui/id531387262?mt=8">Using iPhone? <img src="../moskito/int/img/iphone.svg" class="iphone-app-icon"></a></li>
        </ul>
    </div>
     </div>
</header>

<aside id="aside" class="scrollbar">

<%-- OLD STYLE LOGO
    <div class="header-box">
        <ano:notEmpty name="logoUrl">
        <a href="">
        <i class="logo-custom">
            <img src="${logoUrl}"></i>
            </a>
        </ano:notEmpty>
        <ano:empty name="logoUrl">
        <a href="">
            <i class="logo"></i>
            <span class="logo-title">MoSKito Inspect</span>
        </a>
        </ano:empty>
    </div>
    --%>

    <%-- NEW STYLE LOGO  --%>
        <div class="header-box">
            <%--
            <ano:notEmpty name="logoUrl">
            <a href="">
            <i class="logo-custom">
                <img src="${logoUrl}"></i>
                </a>
            </ano:notEmpty> --%>
            <ano:empty name="logoUrl">
                <div class="logo <ano:iF test="${betaMode}">beta-logo</ano:iF>">
                    <mos:deepLink href="mskDashboard">
                        <img src="../moskito/int/img/logo.png" class="logo-white">
                        <span>
                            <b>MoSKito</b>
                            <span>Inspect</span>
                            <ano:iF test="${betaMode}">
                                <span class="beta-logo__beta-title">Beta</span>
                            </ano:iF>
                        </span>
                    </mos:deepLink>
                </div>
            </ano:empty>
        </div>


    <ul class="nav nav-sidebar nav-status">
        <ano:equal name="currentNaviItem" property="id" value="dashboards">
            <li class="active">
                <mos:deepLink href="mskDashboard" title="Dashboards" class="sidebar-tooltip-right">Dashboards<i class="fa fa-tachometer"></i></mos:deepLink>
                <ul class="nav sub-menu">
                    <ano:iterate name="dashboardsMenuItems" id="item">
                        <li ${requestScope.selectedDashboard == item.name ? "class=\"active\"" : ""}><mos:deepLink href="mskDashboard?dashboard=${item.urlParameter}" title="${item.name}" class="sidebar-tooltip-right">${item.name} <i class="fa fa-tachometer"></i></mos:deepLink></li>
                    </ano:iterate>
                    <li><mos:deepLink href="#CreateDashboard" data-toggle="modal" data-target="#CreateDashboard" title="New Dashboard" class="sidebar-tooltip-right">New Dashboard <i class="fa fa-tachometer"></i></mos:deepLink></li>
                </ul>
            </li>
        </ano:equal>
        <ano:notEqual name="currentNaviItem" property="id" value="dashboards">
            <li><mos:deepLink href="mskDashboard" title="Dashboards" class="sidebar-tooltip-right">Dashboards<i class="fa fa-tachometer"></i></mos:deepLink></li>
        </ano:notEqual>
        <li ${requestScope.currentNaviItem.id == "producers" ? "class=\"active\"" : ""}><mos:deepLink href="mskShowAllProducers" title="Producers" class="sidebar-tooltip-right">Producers <i class="fa fa-wrench"></i></mos:deepLink></li>
        <li ${requestScope.currentNaviItem.id == "journeys" ? "class=\"active\"" : ""}><mos:deepLink href="mskShowJourneys" title="Journeys" class="sidebar-tooltip-right">Journeys <i class="fa fa-eye"></i></mos:deepLink></li>
        <li ${requestScope.currentNaviItem.id == "thresholds" ? "class=\"active\"" : ""}><mos:deepLink href="mskThresholds" title="Thresholds" class="sidebar-tooltip-right">Thresholds <i class="fa fa-dot-circle-o"></i></mos:deepLink></li>
        <li ${requestScope.currentNaviItem.id == "accumulators" ? "class=\"active\"" : ""}><mos:deepLink href="mskAccumulators" title="Accumulators" class="sidebar-tooltip-right">Accumulators <i class="fa fa-line-chart"></i></mos:deepLink></li>
        <ano:equal name="currentNaviItem" property="id" value="threads">
            <li class="active">
                <mos:deepLink href="mskThreads" title="Threads" class="sidebar-tooltip-right">Threads <i class="fa fa-bars"></i></mos:deepLink>
                <ul class="nav sub-menu">
                    <li ${currentSubNaviItem.isSelected("threads_list") ? "class=\"active\"" : ""}><mos:deepLink href="mskThreadsList" title="List" class="sidebar-tooltip-right">List <i class="fa fa-list"></i></mos:deepLink></li>
                    <li ${currentSubNaviItem.isSelected("threads_dump") ? "class=\"active\"" : ""}><mos:deepLink href="mskThreadsDump" title="Dump" class="sidebar-tooltip-right">Dump <i class="fa fa-upload"></i></mos:deepLink></li>
                    <li ${currentSubNaviItem.isSelected("threads_history") ? "class=\"active\"" : ""}><mos:deepLink href="mskThreadsHistory" title="History" class="sidebar-tooltip-right">History <i class="fa fa-file-text"></i></mos:deepLink></li>
                </ul>
            </li>
        </ano:equal>
        <ano:notEqual name="currentNaviItem" property="id" value="threads">
            <li><mos:deepLink href="mskThreads" title="Threads" class="sidebar-tooltip-right">Threads <i class="fa fa-bars"></i></mos:deepLink></li>
        </ano:notEqual>
        <!-- Tracers --->
        <ano:notEqual name="currentNaviItem" property="id" value="tracers">
            <li><mos:deepLink href="mskTracers" title="Tracers" class="sidebar-tooltip-right">Tracers <i class="fa fa-binoculars"></i></mos:deepLink></li>
        </ano:notEqual>
        <ano:equal name="currentNaviItem" property="id" value="tracers">
            <li class="active"><mos:deepLink href="mskTracers" title="Tracers" class="sidebar-tooltip-right">Tracers <i class="fa fa-binoculars"></i></mos:deepLink></li>
            <ul class="nav sub-menu">
                <ano:iterate id="subMenuElement" name="tracerSubmenuNames">
                    <li <%--${currentSubNaviItem.isSelected("threads_list") ? "class=\"active\"" : ""} --%>><mos:deepLink href="mskTracer?pProducerId=${subMenuElement}" title="${subMenuElement}" class="sidebar-tooltip-right">${subMenuElement} <i class="fa fa-arrow-circle-right"></i></mos:deepLink></li>
                </ano:iterate>
            </ul>
        </ano:equal>
        <!-- Tracers end -->


        <ano:notEqual name="currentNaviItem" property="id" value="plugins">
            <li><mos:deepLink href="mskPlugins" title="Plugins" class="sidebar-tooltip-right">Plugins <i class="fa fa-plug"></i></mos:deepLink></li>
        </ano:notEqual>
        <ano:equal name="currentNaviItem" property="id" value="plugins">
            <li class="active"><mos:deepLink href="mskPlugins" title="Plugins" class="sidebar-tooltip-right">Plugins <i class="fa fa-plug"></i></mos:deepLink></li>
            <ul class="nav sub-menu">
            <ano:iterate name="pluginsForNavi" id="plugin">
                <li <ano:equal name="plugin" property="webSelected" value="true">class="active"</ano:equal>><mos:deepLink href="${plugin.navigationEntryAction}" title="${plugin.subNaviItemText}" class="sidebar-tooltip-right">${plugin.subNaviItemText} <i class="fa fa-${plugin.subNaviItemIcon}"></i></mos:deepLink></li>
            </ano:iterate>
            </ul>

        </ano:equal>

        <!-- Tags menu item -->
        <li <ano:equal name="currentNaviItem" property="id" value="errors">class="active"</ano:equal>><mos:deepLink href="mskErrors" title="Errors" class="sidebar-tooltip-right">Errors <i class="fa fa-bug"></i></mos:deepLink></li>

        <ano:equal name="betaMode" value="true">
            <!-- Tags menu item -->
            <li <ano:equal name="currentNaviItem" property="id" value="tags">class="active"</ano:equal>><mos:deepLink href="mskTags" title="Tags" class="sidebar-tooltip-right">Tags <i class="fa fa-tags"></i></mos:deepLink></li>
        </ano:equal>

        <!-- Submenu for everything else -->
        <ano:equal name="currentNaviItem" property="id" value="more">
            <li class="active">
            <mos:deepLink href="mskMore" title="Everything else" class="sidebar-tooltip-right">Everything else <i class="fa fa-bookmark"></i></mos:deepLink>
            <ul class="nav sub-menu">
                <li ${currentSubNaviItem.isSelected("more_config")  ? "class=\"active\"" : ""}><mos:deepLink href="mskConfig" title="Config" class="sidebar-tooltip-right">Config <i class="fa fa-cog"></i></mos:deepLink></li>
                <li ${currentSubNaviItem.isSelected("more_mbeans")  ? "class=\"active\"" : ""}><mos:deepLink href="mskMBeans" title="MBeans" class="sidebar-tooltip-right">MBeans <i class="fa fa-coffee"></i></mos:deepLink></li>
                <li ${currentSubNaviItem.isSelected("more_libs")    ? "class=\"active\"" : ""}><mos:deepLink href="mskLibs" title="Libs" class="sidebar-tooltip-right">Libs <i class="fa fa-file-text"></i></mos:deepLink></li>
                <li ${currentSubNaviItem.isSelected("more_update")  ? "class=\"active\"" : ""}><mos:deepLink href="mskUpdate" title="Update" class="sidebar-tooltip-right">Update  <i class="fa fa-upload"></i></mos:deepLink></li>
                <li ${currentSubNaviItem.isSelected("more_gauges")  ? "class=\"active\"" : ""}><mos:deepLink href="mskGauges" title="Gauges" class="sidebar-tooltip-right">Gauges  <i class="fa fa-tachometer"></i></mos:deepLink></li>
                <%--<li ${currentSubNaviItem.isSelected("more_plugins") ? "class=\"active\"" : ""}><a href="mskPlugins" title="Plugins" class="sidebar-tooltip-right">Plugins <i class="fa fa-cloud"></i></a></li>--%>
            </ul>
        </li>
        </ano:equal>
        <ano:notEqual name="currentNaviItem" property="id" value="more">
            <li><mos:deepLink href="mskMore" title="Everything else" class="sidebar-tooltip-right">Everything else <i class="fa fa-bookmark"></i></mos:deepLink></li>
        </ano:notEqual>
    </ul>

    <ul class="nav nav-sidebar contacts-nav">
        <li>
            <a href="#">About<i class="fa fa-info-circle"></i></a>
            <ul class="nav sub-menu">
                <li><a href="#Version" data-toggle="modal" data-target="#Version">Version</a></li>
                <li><a href="#About" data-toggle="modal" data-target="#About">What is MoSkito?</a></li>
                <li><a href="http://moskito.org" target="_blank">Documentation</a></li>
                <li><a href="mskShowExplanations">Need help?</a></li>
                <li><a href="#Contacts" data-toggle="modal" data-target="#Contacts">Contacts</a></li>
            </ul>
        </li>
    </ul>

    <ano:equal name="pagename" value="producers">
    <div class="form-box">
        <label>Filter</label>

        <select class="select2" data-placeholder="Select category" onchange="handleSelect(this)">
            <option></option>
            <ano:iterate name="categories" id="category" type="net.anotheria.moskito.webui.producers.api.UnitCountAO">
                <option value="mskShowProducersByCategory?pCategory=${category.unitName}" ${category.unitName==requestScope.currentCategory ? "selected" : ""}>
                ${category.unitName} (${category.unitCount})
                </option>
            </ano:iterate>
        </select>

        <select class="select2" data-placeholder="Select subsystem" onchange="handleSelect(this)">
            <option></option>
            <ano:iterate name="subsystems" id="subsystem" type="net.anotheria.moskito.webui.producers.api.UnitCountAO">
                <option value="mskShowProducersBySubsystem?pSubsystem=${subsystem.unitName}" ${subsystem.unitName==requestScope.currentSubsystem ? "selected" : ""}>
                        ${subsystem.unitName} (${subsystem.unitCount})
                </option>
            </ano:iterate>
        </select>

        <form name="Filter" action="mskShowAllProducers" method="GET">
            <input type="text" name="pNameFilter" value="${nameFilter}" class="form-control" placeholder="Filter by name">
            <input type="hidden" name="remoteConnection" value="${remoteLink}"/>
        </form>
    </div>
    </ano:equal>

    <div class="form-box">
        <label>Server selector</label>
        <form name="SelectServer" action="mskSelectServer" method="GET">
            <select class="select2" data-placeholder="Select Server" onchange="handleSelect(this)">
                <ano:iterate name="connectivityOptions" id="option" type="net.anotheria.moskito.webui.shared.bean.LabelValueBean">
                    <option value="mskSelectServer?pTargetServer=${option.value}" ${option.value==requestScope.selectedConnectivity ? "selected" : ""}>${option.label}</option>
                </ano:iterate>
            </select>
        </form>
    </div>

    <div class="form-box">
        <label>Quick connect</label>
        <form name="QuickConnect" action="mskQuickConnect" method="GET">
            <div class="form-group">
                <input type="text" class="form-control" name="pServerName" placeholder="Host">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="pServerPort" placeholder="Port">
            </div>
            <div class="form-group text-right">
                <button class="btn btn-success" type="button" onclick="submit();">Connect</button>
            </div>
        </form>
    </div>

    <span class="shadow-line"></span>

</aside>
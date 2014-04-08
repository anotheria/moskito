<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%><%@
        taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%><%@
        page isELIgnored="false" %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>MoSKito Inspect</title>
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400,700' rel='stylesheet' type='text/css'>
    <link type="text/css" rel="stylesheet" href="http://fonts.googleapis.com/css?family=Mr+Bedfort">
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../ext/bootstrap-3.1.1/css/bootstrap.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../ext/custom-scrollbar/jquery.mCustomScrollbar.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../ext/bootstrap-sortable/contents/bootstrap-sortable.css" />
    <link type="text/css" rel="stylesheet" href="../ext/font-awesome-4.0.3/css/font-awesome.css">
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../ext/select2-3.4.6/select2.css" />
    <link type="text/css" rel="stylesheet" rev="stylesheet" href="../int/css/common.css" />

    <!--[if lt IE 9]><script src="../int/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <!--[if lt IE 8]><link type="text/css" rel="stylesheet" rev="stylesheet" href="../static-int/css/bootstrap-ie7.css" /><![endif]-->
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

<body class="status-${systemStatusColor}">

<script src="../ext/jquery-1.10.2/jquery-1.10.2.min.js" type="text/javascript"></script>
<script src="../ext/bootstrap-3.1.1/js/bootstrap.min.js" type="text/javascript"></script>
<script src="../ext/tablesorter/jquery.tablesorter.js" type="text/javascript"></script>
<script src="../ext/custom-scrollbar/jquery.mCustomScrollbar.js"></script>
<script src="../ext/select2-3.4.6/select2.js" type="text/javascript"></script>
<script src="../int/js/common.js" type="text/javascript"></script>
<!-- currently for handle select only -->
<script type="text/javascript" src="../js/function.js"></script>

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
        <form role="form" class="navbar-form navbar-left">
            <div class="form-group">
                <select class="select2" data-placeholder="Interval" onchange="javascript:handleSelect(this)">
                    <ano:iterate name="intervals" id="interval" type="net.anotheria.moskito.webui.shared.api.IntervalInfoAO">
                        <option value="${linkToCurrentPage}&amp;pInterval=${interval.name}" ${interval.name==requestScope.currentInterval ? "selected" : ""}>
                            <ano:write name="interval" property="name"/>
                        </option>
                    </ano:iterate>
                </select>
            </div>
            <div class="form-group">
                <select class="select2" data-placeholder="Unit" onchange="javascript:handleSelect(this)">
                <ano:iterate name="units" id="unit" type="net.anotheria.moskito.webui.shared.bean.UnitBean">
                    <option value="${linkToCurrentPage}&amp;pUnit=${unit.unitName}" ${unit.unitName==requestScope.currentUnit ? "selected" : ""}>
                        ${unit.unitName}
                    </option>
                </ano:iterate>
                </select>
            </div>
        </form>

        <ul class="nav navbar-nav pull-right">
            <li><a href="">Autoreload OF</a></li>
            <li><a href="">Export</a></li>
            <li><a href="">Help</a></li>
        </ul>
    </div>
</header>

<aside id="aside" class="scrollbar">

<div class="header-box">
    <span class="version">v 2.5.0-SNAPSHOT</span>
    <i class="logo"></i>
    <span class="logo-title">MoSKito Inspect</span>
</div>

<ul class="nav nav-sidebar">
    <li ${requestScope.currentNaviItem.id == "producers" ? "class=\"active\"" : ""}><a href="mskShowAllProducers">Producers <i class="fa fa-wrench"></i></a></li>
    <li ${requestScope.currentNaviItem.id == "journeys" ? "class=\"active\"" : ""}><a href="mskShowJourneys">Journeys <i class="fa fa-eye"></i></a></li>
    <li ${requestScope.currentNaviItem.id == "thresholds" ? "class=\"active\"" : ""}><a href="mskThresholds">Thresholds <i class="fa fa-dot-circle-o"></i></a></li>
    <li ${requestScope.currentNaviItem.id == "accumulators" ? "class=\"active\"" : ""}><a href="mskAccumulators">Accumulators <i class="fa fa-signal"></i></a></li>
    <ano:equal name="currentNaviItem" property="id" value="threads">
        <li class="active">
            <a href="mskThreads">Threads <i class="fa fa-bars"></i></a>
            <ul class="nav sub-menu">
                <li><a href="#">List <i class="fa fa-list"></i></a></li>
                <li><a href="#">Dump <i class="fa fa-upload"></i></a></li>
                <li><a href="#">History <i class="fa fa-file-text"></i></a></li>
            </ul>
        </li>
    </ano:equal>
    <ano:notEqual name="currentNaviItem" property="id" value="threads">
        <li><a href="mskThreads">Threads <i class="fa fa-bars"></i></a></li>
    </ano:notEqual>
</ul>

<ano:equal name="pagename" value="producers">
<div class="form-box">
    <label>Filter</label>

    <select class="select2" data-placeholder="Category" onchange="javascript:handleSelect(this)">
        <option>Select</option>
        <ano:iterate name="categories" id="category" type="net.anotheria.moskito.webui.producers.api.UnitCountAO">
            <option value="mskShowProducersByCategory?pCategory=${category.unitName}" ${category.unitName==requestScope.currentCategory ? "selected" : ""}>
            ${category.unitName} (${category.unitCount})
            </option>
        </ano:iterate>
    </select>

    <select class="select2" data-placeholder="Subsystem" onchange="javascript:handleSelect(this)">
        <option>Select</option>
        <ano:iterate name="subsystems" id="subsystem" type="net.anotheria.moskito.webui.producers.api.UnitCountAO">
            <option value="mskShowProducersBySubsystem?pSubsystem=${subsystem.unitName}" ${subsystem.unitName==requestScope.currentSubsystem ? "selected" : ""}>
                    ${subsystem.unitName} (${subsystem.unitCount})
            </option>
        </ano:iterate>
    </select>

    <input type="text" class="form-control" placeholder="Name Filter">
</div>
</ano:equal>

<div class="form-box">
    <label>Server selector</label>
    <form name="SelectServer" action="mskSelectServer" method="GET">
    <select class="select2" data-placeholder="Select Server" onchange="javascript:handleSelect(this)">
            <ano:iterate name="connectivityOptions" id="option" type="net.anotheria.moskito.webui.shared.bean.LabelValueBean">
                <option value="mskSelectServer?pTargetServer=${option.value}" ${option.value==requestScope.selectedConnectivity ? "selected" : ""}>${option.label}</option>
            </ano:iterate>
        </select>
        </form></li>
    </select>
</div>

<span class="shadow-line"></span>

</aside>
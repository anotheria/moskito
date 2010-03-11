<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"
%><%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito Recorded UseCase: <msk:write name="recordedUseCase" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
	<%-- 
	<!-- YIU TREE Required CSS --> 
	<link type="text/css" rel="stylesheet" href="http://yui.yahooapis.com/2.5.1/build/treeview/assets/skins/sam/treeview.css"> 
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.5.1/build/fonts/fonts-min.css" />
	--%>
<msk:define id="IMG" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_l.gif" border="0" alt=""></msk:define
><msk:define id="EMPTY" type="java.lang.String"><img src="<msk:write name="mskPathToImages" scope="application"/>msk_s.gif" border="0" alt=""></msk:define
><style type="text/css">
/* first or middle sibling, no children */
.ygtvtn { background: url(../yuiimg/tn.gif) 0 0 no-repeat; width:17px; height:22px; }

/* first or middle sibling, collapsable */
.ygtvtm { background: url(../yuiimg/tm.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* first or middle sibling, collapsable, hover */
.ygtvtmh { background: url(../yuiimg/tmh.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* first or middle sibling, expandable */
.ygtvtp { background: url(../yuiimg/tp.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* first or middle sibling, expandable, hover */
.ygtvtph { background: url(../yuiimg/tph.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* last sibling, no children */
.ygtvln { background: url(../yuiimg/ln.gif) 0 0 no-repeat; width:17px; height:22px; }

/* Last sibling, collapsable */
.ygtvlm { background: url(../yuiimg/lm.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* Last sibling, collapsable, hover */
.ygtvlmh { background: url(../yuiimg/lmh.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* Last sibling, expandable */
.ygtvlp { background: url(../yuiimg/lp.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* Last sibling, expandable, hover */
.ygtvlph { background: url(../yuiimg/lph.gif) 0 0 no-repeat; width:34px; height:22px; cursor:pointer }

/* Loading icon */
.ygtvloading { background: url(../yuiimg/loading.gif) 0 0 no-repeat; width:16px; height:22px; }

/* the style for the empty cells that are used for rendering the depth 
 * of the node */
.ygtvdepthcell { background: url(../yuiimg/vline.gif) 0 0 no-repeat; width:17px; height:22px; }

.ygtvblankdepthcell { width:17px; height:22px; }

/* the style of the div around each node */
.ygtvitem { }  

.ygtvitem  table{
    margin-bottom:0;
}
.ygtvitem  td {
    border:none;padding:0;
} 



/* the style of the div around each node's collection of children */
.ygtvchildren { }  
* html .ygtvchildren { height:1%; }  

/* the style of the text label in ygTextNode */
.ygtvlabel, .ygtvlabel:link, .ygtvlabel:visited, .ygtvlabel:hover { 
	margin-left:2px;
	text-decoration: none;
}

#tree td, #tree tr, #tree table{
border: none;
}


	
	</style>
	 <!-- Dependency source files -->  
	 <script src = "http://yui.yahooapis.com/2.5.1/build/yahoo/yahoo-min.js" ></script> 
	 <script src = "http://yui.yahooapis.com/2.5.1/build/event/event-min.js" ></script> 
	 
	 <!-- TreeView source file -->  
	 <script src = "http://yui.yahooapis.com/2.5.1/build/treeview/treeview-min.js" ></script> 
</head>
<body class="yui-skin-sam">
<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<jsp:include page="Menu.jsp" flush="false"/>




<%--
<br/><br/><br/><br/>
<div id="tree_outer">
TREE START
<div id="tree" class="tree"></div>
TREE END
</div>
--%>
<%--
<script>
    var tree; 
    //alert("1");
	function treeInit() { 
	    tree = new YAHOO.widget.TreeView("tree"); 
		var root = tree.getRoot();
		//var tmpNode0 = new YAHOO.widget.TextNode("ROOT", root, false);
		var tmpNode0 = tree.getRoot();
		var tmpNode1;
		<msk:iterate name="nodes" type="net.java.dev.moskito.webui.bean.UseCaseElementNodeBean" id="node" indexId="index">
			tmpNode1 = new YAHOO.widget.TextNode("<msk:write name="node" property="callShort"/>", tmpNode0, false);	
			<% request.setAttribute("subnodes", node.getChildren()); %>
			<% request.setAttribute("currentDepth", 1); %>
			<jsp:include page="TreeNode.jsp" flush="false" />
		</msk:iterate> 
	    
	     
		tree.draw();
	}
	 
	treeInit(); 
</script>
--%>


<div class="main">
	<ul class="breadcrumbs">
		<li class="home_br">You are here:</li>
		<li><a href="<msk:write name="mskShowMonitoringSessions"/>">Monitoring Sessions</a></li>
		<li class="last"><span><msk:write name="recordedUseCase" property="name"/> </span></li>
	</ul>
	<div class="clear"><!-- --></div>

	<h1><msk:write name="recordedUseCase" property="name"/></h1>
	<div class="clear"><!-- --></div>
	<div class="additional">
		<div class="top"><div><!-- --></div></div>
		<div class="add_in">
			<div><span><msk:write name="recordedUseCase" property="created"/>&nbsp;&nbsp;<msk:write name="recordedUseCase" property="date"/> &nbsp;&nbsp;</span></div>
		</div>
		<div class="bot"><div><!-- --></div></div>
	</div>
	
	<div class="clear"><!-- --></div>
	<div class="table_layout">
		<div class="top"><div><!-- --></div></div>
		<div class="in">			
			<div class="clear"><!-- --></div>
			<div class="table_itseft">
				<div class="top">
					<div class="left"><!-- --></div>
					<div class="right"><!-- --></div>
				</div>
				<div class="in">
			
				
				<table cellpadding="0" cellspacing="0" width="100%">
				<thead>
						<tr class="stat_header">
							<th>Call</th>
							<th>Gross duration</th>
							<th>Net duration</th>
							<th>Aborted</th>
						</tr>
				</thead>
				<tbody>
						<msk:iterate name="recordedUseCase" property="elements" type="net.java.dev.moskito.webui.bean.UseCasePathElementBean" id="element" indexId="index">
						 <tr>
						   		<msk:equal name="element" property="aborted" value="true"><tr class="stat_error"></msk:equal>
								<msk:notEqual name="element" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>"></msk:notEqual>
								<td onmouseover="Tip('<msk:write name="element" property="fullCall" filter="true"/>', WIDTH, 400)" onmouseout="UnTip()"><% for (int i=1; i<element.getLayer(); i++){ %><%= EMPTY %><%}%><msk:equal name="element" property="root" value="false"><%=IMG%></msk:equal><msk:write name="element" property="call" filter="true"/></td>
								<td><msk:write name="element" property="duration"/></td>
								<td><msk:write name="element" property="timespent"/></td>
								<td><msk:equal name="element" property="aborted" value="true">X</msk:equal></td>
						</tr>
						</msk:iterate>
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
</body>
</html>
<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%>
<html>
<head>
	<title>Moskito Recorded UseCase: <bean:write name="recordedUseCase" property="name"/></title>
	<link rel="stylesheet" href="mskCSS"/>
	<!-- YIU TREE Required CSS --> 
	<link type="text/css" rel="stylesheet" href="http://yui.yahooapis.com/2.5.1/build/treeview/assets/skins/sam/treeview.css"> 
	<link rel="stylesheet" type="text/css" href="http://yui.yahooapis.com/2.5.1/build/fonts/fonts-min.css" />
	
	<style type="text/css">
 
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
<h3>Show Recorded Use Case: <bean:write name="recordedUseCase" property="name"/> @ <bean:write name="recordedUseCase" property="date"/>&nbsp; (<bean:write name="recordedUseCase" property="created"/>)</h3>
<br/>
<logic:present name="units" scope="request">
	<jsp:include page="UnitSelection.jsp" flush="false"/>
</logic:present>
<br/>

<br/><br/><br/><br/>
<div id="tree_outer">
TREE START
<div id="tree" class="tree"></div>
TREE END
</div>

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
		<logic:iterate name="nodes" type="net.java.dev.moskito.webui.bean.UseCaseElementNodeBean" id="node" indexId="index">
			tmpNode1 = new YAHOO.widget.TextNode("<bean:write name="node" property="callShort"/>", tmpNode0, false);	
			<% request.setAttribute("subnodes", node.getChildren()); %>
			<% request.setAttribute("currentDepth", 1); %>
			<jsp:include page="TreeNode.jsp" flush="false" />
		</logic:iterate> 
	    
	     
		tree.draw();
	}
	 
	treeInit(); 
</script>
--%>
<br/><br/><br/><br/>
<table cellpadding="4" cellspacing="0" border="0">
	<tr class="stat_header">
		<td>Call</td>
		<td>Gross duration</td>
		<td>Net duration</td>
		<td width="1%">Aborted</td>
	</tr>
	<logic:iterate name="recordedUseCase" property="elements" type="net.java.dev.moskito.webui.bean.UseCasePathElementBean" id="element" indexId="index">
		<logic:equal name="element" property="aborted" value="true"><tr class="stat_error"></logic:equal>
		<logic:notEqual name="element" property="aborted" value="true"><tr class="<%= ((index & 1) == 0 )? "stat_even" : "stat_odd" %>"></logic:notEqual>
			<td onmouseover="Tip('<bean:write name="element" property="fullCall"/>', WIDTH, 400)" onmouseout="UnTip()"><% for (int i=1; i<element.getLayer(); i++){ %><img src="../img/s.gif"/><%}%><logic:equal name="element" property="root" value="false"><img src="../img/l.gif"/></logic:equal><bean:write name="element" property="call"/></td>
			<td><bean:write name="element" property="duration"/></td>
			<td><bean:write name="element" property="timespent"/></td>
			<td><logic:equal name="element" property="aborted" value="true">X</logic:equal></td>
		</tr>
	</logic:iterate>
</table>
<br><br>
</body>
</html>
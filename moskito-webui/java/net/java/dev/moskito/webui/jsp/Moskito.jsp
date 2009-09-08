<%@ page language="java" contentType="text/html;charset=iso-8859-15" session="true"
%><%@ taglib uri="/tags/struts-bean" prefix="bean" 
%><%@ taglib uri="/tags/struts-logic" prefix="logic" 
%><%@ page isELIgnored ="false" 
%>
<html>
<head>
	<title>Moskito Producer</title>
	
	
	<!-- Sam Skin CSS -->
	<link type="text/css" rel="stylesheet" href="../yui/core/build/fonts/fonts-min.css"/>
	<link type="text/css" rel="stylesheet" href="../yui/core/build/datatable/assets/skins/sam/datatable.css">
	<link type="text/css" rel="stylesheet" href="../yui/core/build/tabview/assets/skins/sam/tabview.css">
	<link type="text/css" rel="stylesheet" href="../yui/core/build/container/assets/skins/sam/container.css" />
	<link type="text/css" rel="stylesheet" href="../yui/core/build/resize/assets/skins/sam/resize.css" />
	<link type="text/css" rel="stylesheet" href="../yui/core/build/assets/skins/sam/layout.css">
	<link type="text/css" rel="stylesheet" href="../yui/core/build/button/assets/skins/sam/button.css">
	<link type="text/css" rel="stylesheet" href="../yui/core/build/menu/assets/skins/sam/menu.css">
	
	<script type="text/javascript" src="../yui/core/build/yahoo/yahoo-min.js" ></script>
	<script type="text/javascript" src="../yui/core/build/utilities/utilities.js"></script>
	<script type="text/javascript" src="../yui/core/build/get/get-min.js" ></script>
	<script type="text/javascript" src="../yui/core/build/yahoo-dom-event/yahoo-dom-event.js"></script>
	<script type="text/javascript" src="../yui/core/build/event/event-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/element/element-beta-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/json/json-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/connection/connection-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/datasource/datasource.js"></script>
	<script type="text/javascript" src="../yui/core/build/dragdrop/dragdrop-min.js"></script>

	<script type="text/javascript" src="../yui/core/build/datatable/datatable.js"></script>
	<script type="text/javascript" src="../yui/core/build/container/container-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/resize/resize-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/layout/layout-min.js"></script>	
	<script type="text/javascript" src="../yui/core/build/tabview/tabview-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/menu/menu-min.js"></script>
	<script type="text/javascript" src="../yui/core/build/button/button-min.js"></script>


	
	<link rel="stylesheet" type="text/css" href="../yui/bubbling/build/accordion/assets/accordion.css" />
	<script type="text/javascript" src="../yui/bubbling/build/bubbling/bubbling.js"></script>
	<script type="text/javascript" src="../yui/bubbling/build/dispatcher/dispatcher-min.js"></script>
	<script type="text/javascript" src="../yui/bubbling/build/accordion/accordion.js"></script>
	
	<script src="../yui/anoweb/xhr/XHRContentSource.js"></script>
	<script src="../yui/anoweb/xhr/XHRLink.js"></script>
	<script src="../yui/anoweb/widget/AnoDataTable.js"></script>
	<script src="../yui/anoweb/widget/AnoPanel.js"></script>
	<script src="../yui/anoweb/widget/PanelLink.js"></script>
	
	
	<link type="text/css" rel="stylesheet" href="../yui/core/build/logger/assets/skins/sam/logger.css">
	<script type="text/javascript" src="../yui/core/build/logger/logger-min.js"></script>
	
	<script type="text/javascript" src="../js/moskito/moskito.js"></script>
	
	<script type="text/javascript">
		var Dom = YAHOO.util.Dom;
		var Moskito = new WEBUI.utils.Moskito("<bean:write name='currentInterval'/>","<bean:write name='moskito.CurrentUnit' property="unitName"/>");

		var autoUpdateEvent = new YAHOO.util.CustomEvent("autoUpdate", this);
		
		YAHOO.util.Event.onDOMReady(function(){
			//var myLogReader = new YAHOO.widget.LogReader("myLogger");

			var updateNowButton = new YAHOO.widget.Button("UpdateNowButton");
			var updateButton = new YAHOO.widget.Button("UpdateButton", {type:"checkbox",checked:false});

			updateNowButton.addListener("click", function(event){
				Moskito.update();
			}, this, true);

			updateButton.addListener("checkedChange", function(event){
				var checked = event.newValue;
				var intervalEl = Dom.get("AutoUpdateIntervalInput");

				if(!checked){
					updateButton.set("label","Start");
					intervalEl.disabled = false;
					Moskito.autoUpdateStop();
					return;
				}
				var timeUnit = 1000;
				var interval = intervalEl.value * timeUnit;
				intervalEl.disabled = true;
				updateButton.set("label","Stop");
				Moskito.autoUpdate(interval);
			}, this, true);
			
		});
	</script>
	
	

<%-- 
<link type="text/css" rel="stylesheet" href="../yui/core/build/profilerviewer/assets/skins/sam/profilerviewer.css"> 

<!-- Combo-handled YUI JS files: -->

<script type="text/javascript" src="http://yui.yahooapis.com/combo?2.6.0/build/yuiloader-dom-event/yuiloader-dom-event.js&2.6.0/build/profiler/profiler-min.js&2.6.0/build/element/element-beta-min.js&2.6.0/build/profilerviewer/profilerviewer-beta-min.js"></script>

<script>
YAHOO.util.Event.onDOMReady(function(){
YAHOO.log("Creating PROFILER:");

YAHOO.tool.Profiler.registerConstructor("YAHOO.anoweb.widget.AnoPanel");
YAHOO.tool.Profiler.registerConstructor("YAHOO.anoweb.widget.PanelLink"); 
YAHOO.tool.Profiler.registerFunction("YAHOO.anoweb.widget.PanelLink.wrappAll");
var pv = new YAHOO.widget.ProfilerViewer("Profiler", {
    visible: true,
    tableHeight: "250px"
});


YAHOO.log("PROFILER created!");
});
</script>
--%>
	
	
<style type="text/css">
.yui-navset div.loading div {
    background:url(http://developer.yahoo.com/yui/examples/tabview/assets/loading.gif) no-repeat center center;
    height:8em; /* hold some space while loading */
}

.yui-skin-sam .yui-panel .bd {
	overflow:auto;
}
#Dashboard{
	overflow: auto;
}
#Interval{
	padding-left: 0px;
}
.selector{
	float:left;
	padding-left: 20px;
}

#AutoUpdate, #UpdateNow{
	margin: 10px 0;
}
#UpdateButton{
}
</style>

</head>

<body class="yui-skin-sam">

<div id="myLogger"></div>
<div id="Profiler"></div>

<div id="Header">
	<h1><img src="../img/moskito.jpg" style="width:50px"/>onitoring System Kit</h1>
</div>

<div id="container" class="yui-navset"> 
</div>

<div id="TabsShared">
	<div id="Dashboard">
	<jsp:include page="IntervalSelectionYUI.jsp" flush="false"/>
	<jsp:include page="UnitSelectionYUI.jsp" flush="false"/>
	<jsp:include page="SubsystemSelectionYUI.jsp" flush="false"/>
	<jsp:include page="CategorySelectionYUI.jsp" flush="false"/>
	</div>
	<div id="AutoUpdate">
		Update every(sec):
		<input id="AutoUpdateIntervalInput" type="text" value="10" size="4" maxlength="4" />
		<span id="UpdateButton" class="yui-button yui-checkbox-button">
		    <span class="first-child">
		        <button type="button" name="UpdateButton">Start</button>
		    </span>
		</span>
	</div>
	<div id="UpdateNow">
		<span id="UpdateNowButton" class="yui-button yui-push-button">
		    <span class="first-child">
		        <button type="button" name="UpdateNowButton">Update Now</button>
		    </span>
		</span>
	</div>
</div>
<jsp:include page="ProducersYUI.jsp" flush="false"/>
<script type="text/javascript">
(function() {  
	var onContentChanged = new YAHOO.util.CustomEvent("onContentChanged"); 
        
    this.tabView = new YAHOO.widget.TabView();

    var sharedContent = Dom.get("TabsShared");
	this.tabView._contentParent.appendChild(sharedContent);
<%--
	<logic:iterate name="menu" id="item" type="net.java.dev.moskito.webui.bean.MenuItemBean">	
	


    var tab = new YAHOO.widget.Tab({
	    label: '<bean:write name="item" property="caption"/>',
	    dataSrc: '<bean:write name="item" property="link"/>YUI',
	    cacheData: true,
	    active: <bean:write name="item" property="active"/>
    });

    YAHOO.plugin.Dispatcher.delegate (tab, tabView);
    //tabView.addTab(tab); 
    var contentEl = new YAHOO.util.Element(tab.get('contentEl'));
    var contentElId = <bean:write name="item" property="active"/>? 'MoskitoActiveTab':'MoskitoTab';
    contentEl.set('id',contentElId);   
    tab.addListener('contentChange', function(e){
    	onContentChanged.fire();
    });
	</logic:iterate>
   --%>
    tabView.appendTo('container');
    tabView.addListener('activeTabChange', function(e){
    	var prevTab = e.prevValue;
    	var newTab = e.newValue;
    	
    	var prevContentEl = new YAHOO.util.Element(prevTab.get('contentEl'));
    	prevContentEl.set('id', 'MoskitoTab');
    	
    	var newContentEl = new YAHOO.util.Element(newTab.get('contentEl'));
    	newContentEl.set('id', 'MoskitoActiveTab');
    });
    onContentChanged.subscribe(function(){
    	YAHOO.anoweb.XHRLink.wrappAll('a','ConfigurationBox','MoskitoActiveTab', function(){onContentChanged.fire();});
    	//YAHOO.anoweb.widget.PanelLink.wrappAll('a','DashboardBox','PanelsContainer', function(){onContentChanged.fire();});
    });
})();
</script>


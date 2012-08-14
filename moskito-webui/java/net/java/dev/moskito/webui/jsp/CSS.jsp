<%@ page language="java" contentType="text/css;charset=UTF-8" session="false"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>

* {margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; outline:none; color:black;}
img {border:0;}
body {background:white; font-size: 62.5%;}
.clear {clear:both !important; height:0 !important; float:none !important; padding:0 !important;}
.fll {float:left;}
a {text-decoration:none; color:#005fc1;}
a:hover {text-decoration:none; color:#55cc00; cursor:pointer;}

.main {padding:101px 10px 0 10px; min-width:960px;}
.main_menu {min-width:980px; position:fixed; top:0; width:100%; z-index:2;}
.white_pl {background:white; padding:0 10px;}
.top {height:50px; background:white; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -103px repeat-x;}
.top .left_bg {width:10px; float:left; height:50px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 0 no-repeat;}
.logo {display:block; float:left; margin-top:8px; padding-bottom:16px;}
.top .right {width:10px; height:50px; float:right; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) -1px -203px no-repeat;}
.top ul {float:left; margin-left:45px;}
.top ul li {float:left; list-style-type:none; display:block;}
.top ul li a {font-size:1.4em; padding:20px 15px 14px 15px; display:block; float:left;	}
.top ul li.active {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -305px no-repeat #f7f7f7;}
.top ul li.active a {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -416px no-repeat; font-size:1.4em; display:block; float:left; padding:20px 15px 14px 15px; color:black;}
.top ul li .sub_menu {position:absolute; top:50px; height:30px; overflow:hidden; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -533px no-repeat; left:150px; display:none;}
.top ul li .over_color {display:none; }
.top ul li.active .over_color {display:block;} 
.top ul li .sub_menu ul {margin:0; margin-left:5px; white-space:nowrap; padding-right:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -602px no-repeat; height:30px;}
.top ul li .sub_menu ul li {display:block; float:left; list-style-type:none; padding:7px 11px 4px 11px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -669px repeat-x; //padding:5px 11px 4px 11px; height:19px;}
.top ul li .sub_menu ul li.separator {border-right:1px solid #bebebe;}
.top ul li .sub_menu ul li a {padding:0; height:auto; white-space:nowrap; color:#005fc1; cursor:pointer; background:none;}
.top ul li .sub_menu ul li.active a {color:black !important;}
.top ul li .sub_menu ul li a:hover {color:#55cc00 !important; text-decoration:none;}
.top ul li .sub_menu ul li.active a:hover {color:black !important;}
.top ul li .sub_menu ul li a span {font-size:0.8em; padding:0; color:#666666; display:inline; float:none; cursor:pointer; background:none;}
.top ul li .sub_menu ul li span {font-size:1.4em; margin-right:5px; //position:relative; //top:-2px;}
.top ul li .sub_menu ul li select {font-size:1.2em; padding:0; position:relative; top:-1px;}
.top ul li .sub_menu ul li select option {padding:0 7px;}
.top ul li .sub_menu ul li input {font-size:1.2em; padding:0; position:relative; top:-1px;}
.top ul li .sub_menu ul li input option {padding:0 7px;}
.top ul li.active .sub_menu {display:block; overflow:hidden;}
.top .settings {float:right;}
.top .settings li a {font-size:1.2em; padding-top:22px;}
.top .settings li.active a {font-size:1.2em; padding-top:22px;}
.top .settings li .sub_menu {left:auto; margin-left:-54px;}
.top .settings li .sub_menu a {padding:0; font-size:1.4em;}


.over_color {height:3px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -743px no-repeat #f7f7f7; float:left; width:100%; position:absolute; top:50px;}
.over_color div {height:3px; float:right; width:8px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) -3px -767px no-repeat;}

.main h1 {font-weight:normal; font-size:1.8em; margin-bottom:10px; display:block; float:left; overflow:hidden;}

.table_layout { margin-bottom:10px; overflow:hidden;}
.table_layout .top {height:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -790px no-repeat #e9f0f7;}
.table_layout .top div {height:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -809px no-repeat;}
.table_layout .in {background:#e9f0f7; padding:5px 10px;}
.table_layout .bot {height:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -850px no-repeat #e9f0f7;}
.table_layout .bot div {height:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -829px no-repeat;}
.table_layout h2 {float:left;}
.table_layout h2 span, .table_layout h2, .table_layout h2 a {font-size:15px;}

.filter {display:block; float:left; margin:0 35px 7px 0;}
.filter li {list-style-type:none; display:inline; }
.filter li {font-size:1.2em; padding:0 5px 0 0; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -868px no-repeat;}
.filter li:first-child, .filter li.last {background:none; padding:0;}
.filter li.active a {color:black; font-weight:bold;}

.table_itseft {}
.table_itseft .top, .table_itseft .bot {height:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -939px repeat-x;}
.table_itseft .top .left, .table_itseft .bot .left {height:5px; width:5px; float:left; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -894px no-repeat;}
.table_itseft .top .right, .table_itseft .bot .right {height:5px; width:5px; float:right; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -919px no-repeat;}
.table_itseft .in {background:white; border:1px solid #99cccc; border-top:none; border-bottom:none; padding:0;}
.table_itseft .bot {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -1000px repeat-x;}
.table_itseft .bot .left {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -960px no-repeat;}
.table_itseft .bot .right {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -979px no-repeat;}
.table_itseft .in table {border-collapse:collapse;}
.table_itseft .in table td, .table_itseft .in table th {text-align:left; font-size:1.2em; padding:5px 5px 5px 5px; white-space:nowrap;}
.table_itseft .in table td {padding-right:25px;}
.table_itseft .in table th a {color:black; padding-right:15px;}
.table_itseft .in table th a:hover {padding-right:15px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -1015px no-repeat;}
.table_itseft .in table th {background:white;}
.table_itseft .in table th a.up {padding-right:15px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -1040px no-repeat;}
.table_itseft .in table th a.down {padding-right:15px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -1015px no-repeat;}
.table_itseft .in table tr.even td {background:#f7f7f7; //background:f7f7f7;}
.table_itseft .in table tr.odd td {background:white;} 
.table_itseft .in table tr:hover td, .table_itseft .in table tr.hover_it td  {background:#e1eefa;}
.table_right {float:left; overflow-x:scroll;}
.table_right tbody td {cursor:default; text-align:right !important;}

.generated {font-size:1.2em; color:#666666; margin-bottom:1em;}
h2 {margin-top:-4px; height:20px; display:block;}
h2 a {font-size:1.1em; color:black; padding-left:17px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -1073px no-repeat; //font-size:0.7em;}
h2 a:hover {color:black;}
h2 a.hidden {background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 0 -1103px no-repeat;}
.title_collapsed {background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 0 -1103px no-repeat;}
.title_open {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -1073px no-repeat;}
.filter_2 {margin-top:8px;}
.filter_2 ul {margin-bottom:0;}
.help {float:right; font-size:1.2em; margin-top:-2px;}

.breadcrumbs {display:block; margin-bottom:18px; overflow:hidden;}
.breadcrumbs li {display:block; float:left; font-size:1.2em; list-style-type:none; color:#666666; padding-right:15px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 100% -1135px no-repeat; margin-right:10px;}
.breadcrumbs li.home_br {padding-right:3px; margin-right:0; background:none;}
.breadcrumbs li.last {padding-right:0; margin-right:0; background:none;}
.breadcrumbs li a span {font-size:0.8em; color:#666666;}
.inspect {float:left; display:block; margin:6px 0 0 15px; font-size:1.2em;}

.additional {float:left; margin-bottom:10px;}
.additional .top {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 0 -1160px no-repeat #f7f7f7;}
.additional .top div {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 100% -1180px no-repeat;}
.additional .add_in {padding:5px 10px; background:#f7f7f7;}
.additional .add_in span {font-size:1.2em;}
.additional .add_in a {font-size:1em;} 
.additional .bot {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 0 -1196px no-repeat #f7f7f7;}
.additional .bot div {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 100% -1212px no-repeat;}


.head_list {float:left; font-size:1.2em; padding:5px; font-weight:bold;}
.head_list.aborted {width:55px;}
.head_list.net_d {width:80px;}
.head_list.gross {width:95px;}
.head_list.call {width:953px;}

.call_list {}
.call_list a {color:black;}
.call_list a:hover {color:black;}
.call_list li {list-style-type:none; display:block; padding:0 5px; overflow:hidden; position:relative; z-index:1;}
.call_list li div {float:left; font-size:1.2em; padding:6px 0 5px 0; height:14px; overflow:hidden;}

.call_list li ul li {padding:0 5px 0 20px; margin:0 -5px;}
.call_list li img.up, .call_list li img.down {float:left; position:absolute; top:8px; left:6px; cursor:pointer;}
.call_list li img.up {top:6px;}
.call_list li div.aborted {width:55px; float:right;}
.call_list li div.aborted img {margin-top:1px;}
.call_list li div.net_d {width:80px; float:right;}
.call_list li div.gross {width:95px; float:right;}
.call_list li div.call {max-width:800px; float:left;}

.call_list {background:url("<ano:write name="mskPathToImages" scope="application"/>ul_bg.gif") 0 0 repeat;}
.call_error {background:#ffcccc;}
.black_bg {background:black; opacity:0.5; width:100%; height:100%; position:fixed; top:0; left:0; z-index:5; filter:alpha(opacity = 50);}
.box {float:left; position:absolute; z-index:6; min-width:1200px;}
.box_top {height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_top_l.png") 0 0 no-repeat; float:left; width:100%; position:relative;}
.box_top div {height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_top_r.png") 100% 0 no-repeat; float:right; width:30px;}
.box_top span {display:block; height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_top_m.png") 0 0 repeat-x; margin:0 30px;}

.box_in {clear:both; overflow:hidden; background:url("<ano:write name="mskPathToImages" scope="application"/>box_in_l.png") 0 0 repeat-y;}
.box_in .right {background:url("<ano:write name="mskPathToImages" scope="application"/>box_in_r.png") 100% 0 repeat-y;}
.text_here {margin:0 30px; clear:both; overflow:hidden; font-size:1.2em; background:white; padding:10px 10px 5px 10px; line-height:2.1em;}

.box_bot {height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_bot_l.png") 0 0 no-repeat; float:left; width:100%; overflow:hidden; position:relative;}
.box_bot div {height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_bot_r.png") 100% 0 no-repeat; float:right; width:30px;}
.box_bot span {display:block; height:22px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_bot_m.png") 0 0 repeat-x; margin:0 30px;}
.close_box {height:30px; width:30px; background:url("<ano:write name="mskPathToImages" scope="application"/>box_close.png") 0 0 no-repeat; top:-25px; left:7px; display:block; position:relative;}
.wrap_it td {white-space:normal !important;}
.al_left {text-align:left !important;}
.table_itseft .in table th:hover .chart {background:url("<ano:write name="mskPathToImages" scope="application"/>chart.gif") 2px 2px no-repeat;}
.chart {background:none; padding:0 !important;}
.lightbox #chartcontainer {float:left;}
.lightbox .pie_chart, .lightbox .bar_chart {float:left; display:block; width:16px; height:16px;}

.bar_chart {background:url("<ano:write name="mskPathToImages" scope="application"/>chart.gif") 2px 2px no-repeat;}
.pie_chart {background:url("<ano:write name="mskPathToImages" scope="application"/>bar.gif") 2px 2px no-repeat;}

/*------- charts page --------*/

.chart_fix {padding:10px 15px;}
.chart_fix tr, .chart_fix th, .chart_fix td {padding:0 !important;}
.chart_fix tr:hover td {background:none !important;}
.create_chart_btn {margin-bottom:10px; font-size:12px;}

.chart_overlay {}
.chart_overlay h2 {margin-bottom:10px; font-size:16px;}
.chart_overlay .fll {margin:0 20px 10px 0; height:25px; }
.chart_overlay hr {color:#99cccc; margin-bottom:10px;}
.chart_overlay input, .chart_overlay select {font-size:12px; max-width:195px;}
.chart_overlay input.name_ch {width:200px;}
.chart_overlay label {margin-right:2px;}
.chart_overlay .add { margin-top:2px;}
.chart_overlay .delete_btn {}
.chart_overlay table td, .chart_overlay table th {font-size:12px !important; line-height:normal !important;}
.chart_overlay .create_ch_btn {margin-top:10px; float:right;}

.error {border:1px solid red; padding:2px;}
.name_ch {//padding:2px;}
.top ul li .sub_menu ul li {//height:21px;}
.refresh {display:block; float:left; height:16px; width:16px; background:url("<ano:write name="mskPathToImages" scope="application"/>refresh.gif") 0 0 no-repeat; position:relative; left:8px;}

.thresholdOverlay {}
.thresholdOverlay span {display:block; float:left; width:105px; clear:both;}
.thresholdOverlay b {display:block; float:left; width:500px;}
.thresholdOverlay table {width:100%;}
.thresholdOverlay table thead tr th {border-bottom:1px solid #99CCCC; text-align:left;}
.thresholdOverlay table tr.odd {background:#F7F7F7;}
.thresholdOverlay table tr.even {background:white;}
.scroll {overflow-y:scroll; overflow-x:hidden; max-height:200px; clear:both; max-width:600px;}
.scroller_x {width:100%; overflow-x:scroll;}


/* -- Dashboard css -- */
.login_block {width:230px; margin:0 auto 10px auto;}
.login_block .in {padding:0 5px; overflow:hidden;}
.login_block label {font-size:12px; display:block; margin-bottom:3px;}
.login_block input {width:200px; margin-bottom:8px; font-size:12px;}
.login_block .login_btn {width:auto; display:block; float:right; clear:both; margin:0; padding:0 5px;}
.login_block h2 {margin:2px; font-size:15px;}
.edit {float:right; margin:-2px 12px 0 0; font-size:12px; padding-left:15px; background:url("<ano:write name="mskPathToImages" scope="application"/>edit.png") 0 1px no-repeat; font-size:12px; line-height:14px;}
.edit.dash {float:left; margin:4px 12px 0 6px; font-size:12px; background-position:0 1px;}
.widgets_left, .widgets_right {float:left; width:49.49%; min-height:200px;}
.widgets_left .help, .widgets_right .help {padding-left:15px; background:url("<ano:write name="mskPathToImages" scope="application"/>close.png") 0 1px no-repeat; font-size:12px; line-height:14px;;}
.widgets_left {margin-right:1%;}
.dashes {float:left;display:block; font-size:13px; min-width:150px; margin-bottom:10px;}
.dashes option {padding:0 7px; font-size:12px;}
.dashes .create {background:url("<ano:write name="mskPathToImages" scope="application"/>add.png") 7px 3px no-repeat; padding:0 0 0 22px;}
.create_wdgt {float:left; font-size:12px; padding:0 5px;}
.ui-sortable-placeholder {border:1px dashed #99CCCC; visibility:visible !important; border-radius:5px; -moz-border-radius:5px; -webkit-border-radius:5px;}
.draggable .table_layout {cursor:move;}
.draggable .table_itseft {cursor:default;}
.draggable .scroller_x {width:100%; overflow-x:scroll; margin-bottom:10px;}
.draggable .table_itseft .in {overflow:hidden;}
.draggable .table_itseft .in th {color:#4d4d4d;}
.draggable h3 {margin-left:5px; font-size:14px; }

.edit_dash_form {line-height:normal;}
.edit_dash_form h2, .create_widget_form h2 {margin-bottom:10px;}
.edit_dash_form label {font-size:12px; display:block; margin-bottom:3px;}
.edit_dash_form input {width:180px; margin-bottom:10px; font-size:12px;}
.edit_dash_form .flr input, .edit_dash_form .flr span, .edit_dash_form .flr a {font-size:12px;}
.edit_dash_form .flr input {width:auto; padding:0 5px; margin:0;}

.create_widget_form {font-size:1em; line-height:normal;}
.create_widget_form #name {font-size:12px;}
.create_widget_form .flr input {font-size:12px;}
.t_table {margin-bottom:10px;}
.t_table_prod_group {margin-right:-1px; position:relative; z-index:2;}
.t_table_prod_group h3, .right_part h3 {padding-left:5px; margin-top:5px; margin-bottom:7px;}
.right_part h3 {padding:0; margin-bottom:7px;}
.right_part li {display:block; margin-bottom:5px;}
.right_part li input {margin-right:5px; position:relative; top:-1px;}
.t_table_prod_group, .t_table_val, .t_table_prod {float:left;}
.t_table_prod_group li, .t_table_val li, .t_table_prod li {list-style-type:none;}
.t_table_prod_group .uncheck {display:none; position:relative; top:1px;}
.t_table_prod_group .checked .uncheck {display:inline;}
.t_table_prod_group li {padding:6px 11px 0 22px; position:relative; height:19px;}
.t_table_prod_group li.checked {padding:6px 11px 0 5px;}
.t_table_prod {padding-left:30px;}

.t_table_prod_group li.checked.active {padding:5px 11px 0 4px;}
.top_l, .bot_l, .top_r, .bot_r {width:5px; height:5px; position:absolute; background:url("<ano:write name="mskPathToImages" scope="application"/>widget_bg.gif") 0 0 no-repeat; top:-1px; left:-1px; display:none;}
.bot_l {background-position:0 -15px; top:19px;}
.top_r {left:100%; display:block; background-position:0 -45px; margin-left:-4px;}
.bot_r {left:100%; top:100%;display:block; background-position:0 -30px; margin-left:-4px; margin-top:-4px;}
.right_part {border:1px solid #99CCCC; float:left; position:relative; padding:0 20px 0 10px;}
.t_table_prod_group li.active {background:white; border:1px solid #99CCCC; border-width:1px 0 1px 1px; height:18px; padding:5px 11px 0 21px;}
.t_table_prod_group li.active .top_l, .t_table_prod_group li.active .bot_l {display:block;} 
.widget_type {margin:10px 0;}
.widget_type input, .widget_type label {float:left;}
.widget_type label {margin:0 15px 0 5px;}
.mr_5 {margin-right:5px !important;}

.t_pie_bar {border-collapse:collapse;}
.t_pie_bar td {padding:3px 0 0 0; border:1px solid #99CCCC; vertical-align:top;}
.t_pie_bar td h3 {margin:3px 0 7px 0; padding:0 10px;}
.t_pie_bar td li {list-style-type:none; display:block; margin-bottom:5px;}
.t_pie_bar td li a {display:block; padding:3px 10px;}
.t_pie_bar td li.active {background:#e1eefa; border:solid #99cccc; border-width:1px 0; }
.t_pie_bar td li.active a {padding:2px 10px;}
.t_pie_bar td li input {margin-right:5px; position:relative; top:-1px;}
.t_pie_bar .t_pie_bar_prod {padding:3px 10px 0 10px; min-width:220px;}
.t_pie_bar td.t_pie_bar_prod h3 {padding:0;}
.widget_type select {font-size:12px;}
.create_widget_chart_timeline_inner select {position:relative; top:-2px; margin-right:15px; max-width:150px;}
.create_widget_chart_timeline_inner.chart_overlay .fll {margin:0;}
.create_widget_chart_timeline_inner #stats_sel {max-width:120px;}
.create_widget_chart_timeline_inner .add {position:relative; top:-2px;}
.create_widget_chart_timeline_inner .table_itseft td, .create_widget_chart_timeline_inner .table_itseft th {font-size:1em !important;}
.thresholds_list li {list-style-type:none; display:block; margin-bottom:5px;}
.thresholds_list li input {margin-right:5px;}
.create_widget_threshold_inner h3 {margin-bottom:7px;}
.zoom {
	background: url("<ano:write name="mskPathToImages" scope="application"/>zoom_in_icon_12x12.png") 0 1px no-repeat;
	padding-left: 16px;
}
.zoom:hover {
	background: url("<ano:write name="mskPathToImages" scope="application"/>zoom_in_icon_12x12_hovered.png") 0 1px no-repeat;
}

/* jQuery treeTable stylesheet
*
* This file contains styles that are used to display the tree table. Each tree
* table is assigned the +treeTable+ class.
* ========================================================================= */

/* jquery.treeTable.collapsible
* ------------------------------------------------------------------------- */
.treeTable tr td .expander {
 cursor: pointer;
 padding: 0;
 zoom: 1; /* IE7 Hack */
}

.treeTable tr td a.expander {
 background-position: left center;
 background-repeat: no-repeat;
 color: #000;
 text-decoration: none;
}

.treeTable tr.collapsed td a.expander {
   background: #0000cc;
 /*background-image: url(../images/toggle-expand-dark.png);*/
}

.treeTable tr.expanded td a.expander {
   background: #0000cc;
 /*background-image: url(../images/toggle-collapse-dark.png);*/
}

/* jquery.treeTable.sortable
* ------------------------------------------------------------------------- */
.treeTable tr.selected, .treeTable tr.accept {
 background-color: #3875d7;
}

.treeTable tr.selected a.expander, .treeTable tr.accept a.expander {
 color: #fff;
}

.treeTable tr.collapsed.selected td a.expander, .treeTable tr.collapsed.accept td a.expander {
   background: #0000cc;
 /*background-image: url(../images/toggle-expand-light.png);*/
}

.treeTable tr.expanded.selected td a.expander, .treeTable tr.expanded.accept td a.expander {
   background: #0000cc;
 /*background-image: url(../images/toggle-collapse-light.png);*/
}

.treeTable .ui-draggable-dragging {
 color: #000;
 z-index: 1;
}

/* Layout helper taken from jQuery UI. This way I don't have to require the
* full jQuery UI CSS to be loaded. */
.ui-helper-hidden { display: none; }


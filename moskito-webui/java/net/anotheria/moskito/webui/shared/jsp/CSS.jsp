<%@ page language="java" contentType="text/css;charset=UTF-8" session="false"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano"%>

* {margin:0; padding:0; font-family:Arial, Helvetica, sans-serif; outline:none; color:black;}
img {border:0;}
body {background:white; font-size: 62.5%;}
.clear {clear:both !important; height:0 !important; float:none !important; padding:0 !important;}
.fll {float:left;}
.flr {float:right;}
.ovh {overflow:hidden;}
a {text-decoration:none; color:#005fc1;}
a:hover {text-decoration:none; color:#55cc00; cursor:pointer;}

/* MAIN MENU */
.main {padding:101px 10px 0 10px; min-width:960px;}
.main_menu {min-width:980px; position:fixed; top:0; width:100%; z-index:2;}
.white_pl {background:none; padding:0 10px;}
.top {height:50px; background:white; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -103px repeat-x;}
.top .left_bg {width:10px; float:left; height:50px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 0 no-repeat;}
.logo {display:block; float:left; margin-top:8px; padding-bottom:16px; margin-left: 15px;}
.top .right {width:10px; height:50px; float:right; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) -1px -203px no-repeat;}
.top ul {float:left; margin-left:30px;}
.top ul li {float:left; list-style-type:none; display:block;}
.top ul li a {font-size:1.4em; padding:18px 15px 16px 43px; display:block; float:left;	position: relative;}
.top ul li.active {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -305px no-repeat #f7f7f7;}
.top ul li.active a {background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -416px no-repeat; font-size:1.4em; display:block; float:left; color:black;}
.top ul li .sub_menu {position:absolute; top:50px; height:30px; overflow:hidden; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -533px no-repeat; left:150px; display:none;}
.top ul li .over_color {display:none; }
.top ul li.active .over_color {display:block;} 
.top ul li .sub_menu ul {margin:0; margin-left:5px; white-space:nowrap; padding-right:5px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 100% -602px no-repeat; height:30px;}
.top ul li .sub_menu ul li {display:block; float:left; list-style-type:none; padding:7px 11px 4px 11px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -669px repeat-x; //padding:5px 11px 4px 11px; height:19px; position: relative;}
.top ul li .sub_menu ul li a {padding:0; height:auto; white-space:nowrap; color:#005fc1; cursor:pointer; background:none;}
.top ul li .sub_menu ul li.active a {color:black !important;}
.top ul li .sub_menu ul li a:hover {color:#55cc00 !important; text-decoration:none;}
.top ul li .sub_menu ul li.active a:hover {color:black !important;}
.top ul li .sub_menu ul li a span {font-size:0.8em; padding:0; color:#666666; display:inline; float:none; cursor:pointer; background:none;}
.top ul li .sub_menu ul li span {font-size:1.2em; margin-right:5px; //position:relative; //top:-2px;}
.top ul li .sub_menu ul li select {font-size:1.2em; padding:0; position:relative; top:-1px;}
.top ul li .sub_menu ul li select option {padding:0 7px;}
.top ul li .sub_menu ul li input {font-size:1.2em; padding:0; position:relative; top:-1px;}
.top ul li .sub_menu ul li input option {padding:0 7px;}
.top ul li.active .sub_menu {display:block; overflow:hidden;}
.top .settings {float:right;}
.top .settings li a {font-size:1.2em; padding-top:22px; padding-left: 15px;}
.top .settings li.active a {font-size:1.2em; padding-top:22px;}
.top .settings li .sub_menu {left:auto; margin-left:-54px;}
.top .settings li .sub_menu a {padding:0; font-size:1.4em;}
.top ul li .sub_menu
{
    -webkit-border-bottom-right-radius: 5px;
    -webkit-border-bottom-left-radius: 5px;
    -moz-border-radius-bottomright: 5px;
    -moz-border-radius-bottomleft: 5px;
    border-bottom-right-radius: 5px;
    border-bottom-left-radius: 5px;
    box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

.over_color {height:3px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) 0 -743px no-repeat #f7f7f7; float:left; width:100%; position:absolute; top:50px;}
.over_color div {height:3px; float:right; width:8px; background:url(<ano:write name="mskPathToImages" scope="application"/>bgs.gif) -3px -767px no-repeat;}

    /*THREADS*/
.main_menu_threads {position: relative;}

.top ul li.main_menu_threads .sub_menu {left: -50%; margin-right: -200px; border-top: 1px solid #BEBEBE;}

.top ul li.active.main_menu_threads .over_color {
    display: none;
}

.main_menu > .white_pl > .top
{
    -webkit-border-bottom-right-radius: 10px;
    -webkit-border-bottom-left-radius: 10px;
    -moz-border-radius-bottomright: 10px;
    -moz-border-radius-bottomleft: 10px;
    border-bottom-right-radius: 10px;
    border-bottom-left-radius: 10px;
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.main_menu > .white_pl > .top > .left_bg
{
    -webkit-border-bottom-left-radius: 9px;
    -moz-border-radius-bottomleft: 9px;
    border-bottom-left-radius: 9px;
}

.main_menu > .white_pl > .top > .right
{
    -webkit-border-bottom-right-radius: 9px;
    -moz-border-radius-bottomright: 9px;
    border-bottom-right-radius: 9px;
}

/* /MAIN MENU */

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

table.producer_filter_data_table tbody td:hover {
    background: #D7E9CA!important;
}

.producer_filter_data_table thead th:hover {
    background: #EAF7E1!important;
}

.table_right {float:left; overflow-x:auto;}
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

.additional {margin-bottom:10px;}
.additional .top {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 0 -1160px no-repeat #f7f7f7;}
.additional .top div {height:5px; background:url("<ano:write name="mskPathToImages" scope="application"/>bgs.gif") 100% -1180px no-repeat;}
.additional .add_in {padding:5px 10px; background:#f7f7f7;}
.additional .add_in span {font-size:1.2em;}
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
.chart {background:none; padding:0 !important; display: inline-block; width:14px;}
.lightbox #chartcontainer {float:left; min-height: 600px;}
.lightbox .pie_chart, .lightbox .bar_chart {display:block; width:16px; height:16px; position: absolute; top: 30px; right: 45px;}

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
.scroll {overflow-y:auto; overflow-x:hidden; max-height:200px; clear:both; max-width:600px;}
.scroller_x {width:100%; overflow-x:auto;}
.lightbox .scroll {max-width: none;}

/* -- Dashboard css -- */
.login_block {width:230px; margin:0 auto 10px auto;}
.login_block .in {padding:0 5px; overflow:hidden;}
.login_block label {font-size:12px; display:block; margin-bottom:3px;}
.login_block input {width:200px; margin-bottom:8px; font-size:12px;}
.login_block .login_btn {width:auto; display:block; float:right; clear:both; margin:0; padding:0 5px;}
.login_block h2 {margin:2px; font-size:15px;}

.edit {
    display: inline-block;
    width: 16px;
    height: 12px;
    background:url("<ano:write name="mskPathToImages" scope="application"/>edit.png") 50% 1px no-repeat;
}

.edit:hover {
    background-position: 50% 2px;
}

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
    display: inline-block;
    height: 14px;
    width: 16px;
}
.zoom:hover {
	background: url("<ano:write name="mskPathToImages" scope="application"/>zoom_in_icon_12x12_hovered.png") 0 2px no-repeat;
}

.del {
	background: url("<ano:write name="mskPathToImages" scope="application"/>delete_icon_12x12.png") 0 0 no-repeat;
    display: inline-block;
    height: 13px;
    width: 16px;
}
.del:hover {
	background: url("<ano:write name="mskPathToImages" scope="application"/>delete_icon_12x12_hovered.png") 0 1px no-repeat;
}


/* JOURNEY */
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

    /* Layout helper taken from jQuery UI. This way I don't have to require the
    * full jQuery UI CSS to be loaded. */
.ui-helper-hidden { display: none; }

    /* /TABLE TREE*/

.table_itseft .in table td, .table_itseft .in table th {
    padding: 5px 10px;
}

.select_current_row_positions_checkbox {
    margin: 0 auto;
    display: block;
}

#tree_table tr:nth-child(odd), .journeys_summary_table tr:nth-child(odd) {
    background:#f7f7f7;
}

.deselect_all_journey_positions, .expand_table_tree, .accumulators_submit_button {
    font-size: 12px;
    padding: 1px 8px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;

    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.2);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.2);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.2);
    background-color: #4D9CC9;
    background-image: -ms-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#7FBFE4), to(#4D9CC9));
    background-image: -webkit-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -o-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -moz-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: linear-gradient(top, #7FBFE4, #4D9CC9);
    border: 1px solid #CCC;
    border-color: #6093CA #3F92B9 #2A628F;
    color: white;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.4);
    cursor: pointer;
}

.deselect_all_journey_positions:hover, .expand_table_tree:hover, .accumulators_submit_button:hover {
    background-color: #0D77B4;
    background-image: -ms-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6EB8E4), to(#0D77B4));
    background-image: -webkit-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -o-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -moz-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: linear-gradient(top, #6EB8E4, #0D77B4);
}

.deselect_all_journey_positions {
    display: block;
    margin: 0 auto;
}

.expand_table_tree {
    margin: 0 30px 0 15px;
}

.journey_stat_call_td span {
    padding-right: 100px;
}

.journey_stat_position {
    padding-right: 37px;
}

.treeTable tr .journey_stat_position {
    padding-left: 20px;
}

.treeTable tr.parent .journey_stat_position {
    padding-left: 0;
}

.treeTable tr.collapsed td a.expander {
    background: url('<ano:write name="mskPathToImages" scope="application"/>toggle-collapse-dark.png');
}

.treeTable tr.expanded td a.expander {
    background: url('<ano:write name="mskPathToImages" scope="application"/>toggle-expand-dark.png');
}

.tr_select_highlight {
    background: #D7E9CA!important;
}

.table_itseft .in table tr.tr_select_highlight:hover {
    background: #E1EEFA!important;
}
/* /JOURNEY*/


/* ACCUMULATORS POPUP */
.popup_box {
    -webkit-box-shadow: 0px 2px 40px rgba(0, 0, 0, 0.4);
    -moz-box-shadow: 0px 2px 40px rgba(0, 0, 0, 0.4);
    box-shadow: 0px 2px 40px rgba(0, 0, 0, 0.4);
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    padding: 20px 40px 40px;
    background: #fff;
    z-index: 6;
}

.popup_button {
    margin-left: 20px;
    font-size: 16px;
    padding: 6px 12px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;

    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    background-color: #4D9CC9;
    background-image: -ms-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#5C0), to(#3E93C4));
    background-image: -webkit-linear-gradient(top, #5C0, #3E93C4);
    background-image: -o-linear-gradient(top, #5C0, #3E93C4);
    background-image: -moz-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: linear-gradient(top, #6FB7E0, #3E93C4);
    border: 1px solid #CCC;
    border-color: #6093CA #3F92B9 #2A628F;
    color: white;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.5);
    cursor: pointer;
}

.popup_button:hover {
    background-color: #0D77B4;
    background-image: -ms-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6EB8E4), to(#0D77B4));
    background-image: -webkit-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -o-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -moz-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: linear-gradient(top, #6EB8E4, #0D77B4);
}

.popup_box button.popup_button:first-child {
    margin-left: 0;
}

.popup_box_message {
    font-size: 1.6em;
    color: #222;
    margin-bottom: 30px;
    width: 1200px;
}

.acc_popup_box_message {
    width: 520px;
}

.popup_box_item_name {
    font-weight: bold;
}

.popup_box_close {
    height: 30px;
    width: 30px;
    background: url("<ano:write name="mskPathToImages" scope="application"/>box_close.png") 0 0 no-repeat;
    top: -32px;
    left: -52px;
    display: block;
    position: relative;
}
/* ACCUMULATORS POPUP */

/* ACCUMULATORS SET INTERVAL */
.autoreload_wrapper {
    float: right;
    position: relative;
    padding: 16px 30px 0;
    margin: 0  -50px  0 -60px;
}

.autoreload_settings {
    display: none;
    position: absolute;
    top: 40px;
    right: -22px;
    background: white;
    width: 160px;
    -webkit-box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);
    -moz-box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);
    box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.3);
    padding: 20px;
    -webkit-border-radius: 5px;
    -moz-border-radius: 5px;
    border-radius: 5px;
    border: 1px solid #9CC;
}

.autoreload_settings label {
    color: #252525;
    font-size: 11px;
    padding: 0 6px;
}

.autoreload_minutes_settings_input {
    margin: 10px auto;
    display: block;
    padding: 2px;
    width: 56px;
}

.autoreload_set_button {
    display: block;
    margin: 0 auto;
    font-size: 16px;
    padding: 6px 12px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    background-color: #3F9700;
    background-image: -ms-linear-gradient(top, #5C0, #3F9700);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#5C0), to(#3F9700));
    background-image: -webkit-linear-gradient(top, #5C0, #3F9700 );
    background-image: -o-linear-gradient(top, #5C0, #3F9700);
    background-image: -moz-linear-gradient(top, #5C0, #3F9700);
    background-image: linear-gradient(top, #5C0, #3F9700);
    border: 1px solid #CCC;
    border-color: #4AB100 #3F9700 #327900;
    color: white;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.5);
    cursor: pointer;
}

.autoreload_set_button:hover {
    background-color: #0D77B4;
    background-image: -ms-linear-gradient(top, #4FBE00, #378300);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#4FBE00), to(#378300));
    background-image: -webkit-linear-gradient(top, #4FBE00, #378300);
    background-image: -o-linear-gradient(top, #4FBE00, #378300);
    background-image: -moz-linear-gradient(top, #4FBE00, #378300);
    background-image: linear-gradient(top, #4FBE00, #378300);
}

.autoreload_reset_button {
    display: block;
    margin: 10px auto 0;
    font-size: 14px;
    padding: 2px 16px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;

    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 3px rgba(0, 0, 0, 0.4);
    background-color: #4D9CC9;
    background-image: -ms-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6FB7E0), to(#3E93C4));
    background-image: -webkit-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: -o-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: -moz-linear-gradient(top, #6FB7E0, #3E93C4);
    background-image: linear-gradient(top, #6FB7E0, #3E93C4);
    border: 1px solid #CCC;
    border-color: #6093CA #3F92B9 #2A628F;
    color: white;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.5);
    cursor: pointer;
}
.autoreload_reset_button:hover {
    background-color: #0D77B4;
    background-image: -ms-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6EB8E4), to(#0D77B4));
    background-image: -webkit-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -o-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -moz-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: linear-gradient(top, #6EB8E4, #0D77B4);
}

.autoreload_toggle_button {
    position: relative;
    padding: 4px 6px 4px 6px;
    font-size: 12px;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.1);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.1);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.1);
    background-color: #EEE;
    background-image: -ms-linear-gradient(top, #EEE, #C5C5C5);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#EEE), to(#C5C5C5));
    background-image: -webkit-linear-gradient(top, #EEE, #C5C5C5);
    background-image: -o-linear-gradient(top, #EEE, #C5C5C5);
    background-image: -moz-linear-gradient(top, #EEE, #C5C5C5);
    background-image: linear-gradient(top, #EEE, #C5C5C5);
    border: 1px solid #D1D1D1;
    border-color: #D1D1D1 #C5C5C5 #AAA;
    color: #202020;
    text-shadow: 0 -1px 0 rgba(255, 255, 255, 0.7);
    cursor: pointer;
}

.autoreload_toggle_button:hover {
    background-color: #DADADA;
    background-image: -ms-linear-gradient(top, #F3F3F3, #DADADA);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#F3F3F3), to(#DADADA));
    background-image: -webkit-linear-gradient(top, #F3F3F3, #DADADA);
    background-image: -o-linear-gradient(top, #F3F3F3, #DADADA);
    background-image: -moz-linear-gradient(top, #F3F3F3, #DADADA);
    background-image: linear-gradient(top, #F3F3F3, #DADADA);
    border-color: #DDD #DADADA #C4C4C4;
}

.autoreload_off_indicator, .autoreload_on_indicator {
    background: url('<ano:write name="mskPathToImages" scope="application"/>ind_off_small.png') no-repeat 0 0;
    vertical-align: -12%;
    margin-top: -6px;
    display: inline-block;
    height: 12px;
    width: 13px;
}

.autoreload_on_indicator {
    background: url('<ano:write name="mskPathToImages" scope="application"/>ind_green_small.png') no-repeat 0 0;
}

.autoreload_toggle_triangle_right {
    vertical-align: -40%;
    background: url('<ano:write name="mskPathToImages" scope="application"/>arrow_black_right.png') no-repeat 50% -60%;
}

.autoreload_current_interval {
    text-align: center;
    margin-bottom: 8px;
    font-size: 11px;
    padding: 4px 6px;
    background: #E9F0F7;
    border-radius: 3px;
}

.autoreload_current_interval_data {
    padding-left: 5px;
}
/* /ACCUMULATORS SET INTERVAL */

/* COMMON STYLES */
.linkToCurrentPage {
    display: none;
}
/* /COMMON STYLES */

    /*ERROR PAGE*/
.error_page:after {
    content: "";
    position: fixed;
    width: 100%;
    height: 200px;
    background-image: -webkit-linear-gradient(top, #fff, #E9F0F7);
    bottom: 0;
}

.error_page_box {
    border-radius: 10px;
    -webkit-border-radius: 10px;
    width: 680px;
    margin: 160px auto 60px;
    border: 1px solid #98b9da;
    -moz-background-clip: padding;
    -webkit-background-clip: padding-box;
    background-clip: padding-box;
    position: relative;
    z-index: 1;
}

.error_page_box:after {
    content: "";
    background: url('../img/moskito_vertical_error_page.png');
    height: 188px;
    width: 78px;
    position: absolute;
    left: -79px;
    top: 42px;
}

.error_page_status {
    background-color: #F7F7F7;
    border-bottom: 1px solid #DAE7F3;
    background-color: #F5F8FC;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#F5F8FC), to(#E9F0F7));
    background-image: -webkit-linear-gradient(top, #F5F8FC, #E9F0F7);
    background-image: -moz-linear-gradient(top, #F5F8FC, #E9F0F7);
    background-image: -o-linear-gradient(top, #F5F8FC, #E9F0F7);
    background-image: linear-gradient(to bottom, #F5F8FC, #E9F0F7);
    border-radius: 10px 10px 0 0;
    -webkit-border-radius: 10px 10px 0 0;
    color: #292929;
    font-size: 26px;
    line-height: 2.4em;
    text-align: center;
    text-shadow: 0px 1px 0px rgba(255, 255, 255, 0.3);
    padding: 0 30px;
}

.error_page_data_hint {
    font-size: 17px;
    padding: 20px;
    text-align: center;
    min-height: 48px;
}

.error_page_data {
    color: #292929;
    font-size: 20px;
    text-align: center;
    line-height: 2em;
}

.error_page_details_wrapper {
    background: #F7F7F7;
    padding: 20px 30px 10px;
    border-radius: 0 0 10px 10px;
    -webkit-border-radius: 0 0 10px 10px ;
    border-top: 1px solid #ECECEC;
}

.error_page_details_button {
    font-size: 12px;
    padding: 1px 8px;
    margin-bottom: 15px;

    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;

    -webkit-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.1);
    -moz-box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.1);
    box-shadow: inset 1px 0 0 rgba(255, 255, 255, 0.125), inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.2);
    background-color: #4D9CC9;
    background-image: -ms-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#7FBFE4), to(#4D9CC9));
    background-image: -webkit-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -o-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: -moz-linear-gradient(top, #7FBFE4, #4D9CC9);
    background-image: linear-gradient(top, #7FBFE4, #4D9CC9);
    border: 1px solid #CCC;
    border-color: #6093CA #3F92B9 #2A628F;
    color: white;
    text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.4);
    cursor: pointer;
}

.error_page_details_button:hover {
    background-color: #0D77B4;
    background-image: -ms-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#6EB8E4), to(#0D77B4));
    background-image: -webkit-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -o-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: -moz-linear-gradient(top, #6EB8E4, #0D77B4);
    background-image: linear-gradient(top, #6EB8E4, #0D77B4);
}

.error_page_details_content {
    padding-top: 15px;
    border-top: 1px solid #98B9DA;
    font-size: 1.4em;
    line-height: 1.5em;
}

    /* COMMON STYLES */
.display_block_property {
    display: block;
}

.display_none_property {
    display: none;
}

    /* /ERROR PAGE*/

dt,
dd {
    line-height: 20px;
    font-size: 12px;
}

dt {
    font-weight: bold;
}

dd {
    margin-left: 10px;
}

.dl-horizontal {
    *zoom: 1;
    float: left;
}

.dl-horizontal:before,
.dl-horizontal:after {
    display: table;
    line-height: 0;
    content: "";
}

.dl-horizontal:after {
    clear: both;
}

.dl-horizontal dt {
    float: left;
    width: 90px;
    overflow: hidden;
    clear: left;
    text-align: right;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.dl-horizontal dd {
    margin-left: 100px;
}

.dl-horizontal.bw dt
{
   width: 120px;
}

.dl-horizontal.bw dd
{
    margin-left: 130px;
}

.dl-horizontal.bw
{
    margin-right: 50px;
}

.add_in .btn
{
   margin-left: 5px;
}

.add_in .btn-blue
{
    margin-left: 10px;
}

.btn {
    display: inline-block;
    *display: inline;
    padding: 1px 12px;
    margin-bottom: 0;
    *margin-left: .3em;
    font-size: 12px;
    line-height: 20px;
    color: #333333;
    text-align: center;
    text-shadow: 0 1px 1px rgba(255, 255, 255, 0.75);
    vertical-align: middle;
    cursor: pointer;
    background-color: #f5f5f5;
    *background-color: #e6e6e6;
    background-image: -moz-linear-gradient(top, #ffffff, #e6e6e6);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#ffffff), to(#e6e6e6));
    background-image: -webkit-linear-gradient(top, #ffffff, #e6e6e6);
    background-image: -o-linear-gradient(top, #ffffff, #e6e6e6);
    background-image: linear-gradient(to bottom, #ffffff, #e6e6e6);
    background-repeat: repeat-x;
    border: 1px solid #bbbbbb;
    *border: 0;
    border-color: #e6e6e6 #e6e6e6 #bfbfbf;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    border-bottom-color: #a2a2a2;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
    filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
    *zoom: 1;
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
}

button.btn::-moz-focus-inner,
input[type="submit"].btn::-moz-focus-inner {
    padding: 0;
    border: 0;
}

.btn:hover,
.btn:active,
.btn.active,
.btn.disabled,
.btn[disabled] {
    color: #333333;
    background-color: #e6e6e6;
    *background-color: #d9d9d9;
}

.btn:active,
.btn.active {
    background-color: #cccccc \9;
}

.btn:first-child {
    *margin-left: 0;
}

.btn:hover {
    color: #333333;
    text-decoration: none;
    background-position: 0 -15px;
    -webkit-transition: background-position 0.1s linear;
    -moz-transition: background-position 0.1s linear;
    -o-transition: background-position 0.1s linear;
    transition: background-position 0.1s linear;
}

.btn:focus {
    outline: thin dotted #333;
    outline: 5px auto -webkit-focus-ring-color;
    outline-offset: -2px;
}

.btn.active,
.btn:active {
    background-image: none;
    outline: 0;
    -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
}

.btn.disabled,
.btn[disabled] {
    cursor: default;
    background-image: none;
    opacity: 0.65;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
}

.btn-blue {
    display: inline-block;
    *display: inline;
    padding: 1px 12px;
    margin-bottom: 0;
    *margin-left: .3em;
    font-size: 12px;
    line-height: 20px;
    color: #FFF;
    text-align: center;
    text-shadow: 0 -1px 0px #5162cb;
    vertical-align: middle;
    cursor: pointer;
    background-color: #7abce2;
    *background-color: #4e9dca;
    background-image: -moz-linear-gradient(top, #7abce2, #4e9dca);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#7abce2), to(#4e9dca));
    background-image: -webkit-linear-gradient(top, #7abce2, #4e9dca);
    background-image: -o-linear-gradient(top, #7abce2, #4e9dca);
    background-image: linear-gradient(to bottom, #7abce2, #4e9dca);
    background-repeat: repeat-x;
    border: 1px solid #6093ca;
    *border: 0;
    border-color: #6093ca #6093ca #505fcb;
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.25);
    border-bottom-color: #505fcb;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffffff', endColorstr='#ffe6e6e6', GradientType=0);
    filter: progid:DXImageTransform.Microsoft.gradient(enabled=false);
    *zoom: 1;
    -webkit-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.2), 0 1px 2px rgba(0, 0, 0, 0.05);
}

.btn-blue:hover,
.btn-blue:active,
.btn-blue.active,
.btn-blue.disabled,
.btn-blue[disabled] {
    color: #333333;
    background-color: #7abce2;
    *background-color: #7abce2;
}

.btn-blue:active,
.btn-blue.active {
    background-color: #7abce2 \9;
}

.btn-blue:first-child {
    *margin-left: 0;
}

.btn-blue:hover {
    background-image: none;
    color: #FFF;
    text-decoration: none;
    -webkit-transition: background-position 0.1s linear;
    -moz-transition: background-position 0.1s linear;
    -o-transition: background-position 0.1s linear;
    transition: background-position 0.1s linear;
}

.btn-blue.active,
.btn-blue:active {
    background-image: none;
    outline: 0;
    -webkit-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
    -moz-box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.15), 0 1px 2px rgba(0, 0, 0, 0.05);
}

.btn-blue.disabled,
.btn-blue[disabled] {
    cursor: default;
    background-image: none;
    opacity: 0.65;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    -moz-box-shadow: none;
    box-shadow: none;
}

.inspect-list
{
    font-size: 12px;
    margin-left: 40px;
}

.inspect-list li
{
    padding: 2px 0;
}

.additional .add_in h3
{
    margin-bottom: 5px;
}

.form-inline input,
.form-inline textarea,
.form-inline select,
.form-inline .help-inline,
.form-inline .uneditable-input,
.form-inline .input-prepend,
.form-inline .input-append,
.form-inline .controls {
    display: inline-block;
    *display: inline;
    margin-bottom: 0;
    vertical-align: top;
    *zoom: 1;
    font-size: 12px;
}

.form-inline .hide {
    display: none;
}

.form-inline label,
.form-inline .btn-group {
    display: inline-block;
}

.form-inline .input-append,
.form-inline .input-prepend {
    margin-bottom: 0;
}

.form-inline .radio,
.form-inline .checkbox {
    padding-left: 0;
    margin-bottom: 0;
    vertical-align: middle;
}

.form-inline .radio input[type="radio"],
.form-inline .checkbox input[type="checkbox"] {
    float: left;
    margin-right: 3px;
    margin-left: 0;
}

.control-group {
    margin-bottom: 10px;
}

.form-inline .controls
{
    margin: 10px;
    margin-left: 20px;
}

.form-inline .controls span
{
    font-size: 12px;
}

.form-inline .controls label
{
    margin-right: 5px;
    padding: 1px;
}

.journeys-form input[type="text"] {
    display: inline-block;
    padding: 1px 6px;
    font-size: 12px;
    line-height: 20px;
    height: 20px;
    color: #555555;
    vertical-align: middle;
    -webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
    background-color: #ffffff;
    border: 1px solid #cccccc;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -webkit-transition: border linear 0.2s, box-shadow linear 0.2s;
    -moz-transition: border linear 0.2s, box-shadow linear 0.2s;
    -o-transition: border linear 0.2s, box-shadow linear 0.2s;
    transition: border linear 0.2s, box-shadow linear 0.2s;
    min-width: 120px;
}

code
{
    color: #008000;
}

.table_layout .in i
{
    font-size: 12px;
}

.journeys-form.form-inline .controls
{
    margin: 5px 20px 5px 0px;
    padding-left: 20px;
    border-left: 1px solid #e1e1e1;
    line-height: 18px;
}

.journeys-form.form-inline .controls.jfields
{
    border: 0px;
    margin-top: 12px;
}

.journeys-form.form-inline .controls.jinfo
{
    padding-left: 0px;
    padding-right: 20px;
    margin-right: 0px;
    border: 0px;
    text-align: right;
    border-right: 1px solid #e1e1e1;
}

.main_menu i
{
    display: inline-block;
    width: 30px;
    height: 30px;
    background-image: url('<ano:write name="mskPathToImages" scope="application"/>icons.png');
    background-repeat: no-repeat;
    left: 15px;
    position: absolute;
    top: 16px;
}

.top ul li a.producers
{
    padding-left: 45px;
}

.main_menu .producers i
{
    background-position: 0 0;
}

.main_menu ul li.active:hover .producers i,
.main_menu ul li.active .producers i
{
    background-position: -91px 0;
}

.main_menu li:hover .producers i,
{
    background-position: -182px 0;
}

.top ul li a.journeys
{
    padding-left: 48px;
}

.main_menu .journeys i
{
    background-position: 0 -59px;
}

.main_menu ul li.active:hover .journeys i,
.main_menu ul li.active .journeys i
{
    background-position: -90px -59px;
}

.main_menu li:hover .journeys i
{
    background-position: -180px -59px;
}

.main_menu .thresholds i
{
     background-position: 0 -113px;
}

.main_menu ul li.active:hover .thresholds i,
.main_menu ul li.active .thresholds i
{
    background-position: -90px -113px;
}

.main_menu li:hover .thresholds i
{
    background-position: -180px -113px;
}

.main_menu .thresholds img {
    left: 25px;
    position: absolute;
    top: 4px;
}

.top ul li a.thresholds
{
    padding-left: 60px;
}

.main_menu .accumulators i
{
    background-position: 0 -171px;
    top: 16px;
}

.top ul li a.accumulators
{
    padding-left: 45px;
}

.main_menu ul li.active:hover .accumulators i,
.main_menu ul li.active .accumulators i
{
    background-position: -94px -171px;
    top: 16px;
}

.main_menu li:hover .accumulators i
{
    background-position: -184px -171px;
}

.main_menu .threads i
{
    background-position: 0 -227px;
}

.main_menu ul li.active:hover .threads i,
.main_menu ul li.active .threads i
{
    background-position: -94px -227px;
}

.main_menu li:hover .threads i
{
    background-position: -184px -227px;
}

.top ul li .sub_menu ul li span.vline
{
    display: block;
    width: 1px;
    height: 29px;
    background-image: url('<ano:write name="mskPathToImages" scope="application"/>vline.png');
    background-repeat: no-repeat;
    position: absolute;
    top: 0px;
    right: 0px;
    margin: 0px;
}
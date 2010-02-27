<%@ page language="java" contentType="text/css;charset=UTF-8" session="false"%>


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
.top {height:50px; background:white; background:url(../img/bgs.gif) 0 -103px repeat-x;}
.top .left_bg {width:10px; float:left; height:50px; background:url(../img/bgs.gif) 0 0 no-repeat;}
.logo {display:block; float:left; margin-top:8px; padding-bottom:16px;}
.top .right {width:10px; height:50px; float:right; background:url(../img/bgs.gif) -1px -203px no-repeat;}
.top ul {float:left; margin-left:45px;}
.top ul li {float:left; list-style-type:none; display:block;}
.top ul li a {font-size:1.4em; padding:20px 15px 14px 15px; display:block; float:left;	}
.top ul li.active {background:url(../img/bgs.gif) 0 -305px no-repeat #f7f7f7;}
.top ul li.active a {background:url(../img/bgs.gif) 100% -416px no-repeat; font-size:1.4em; display:block; float:left; padding:20px 15px 14px 15px; color:black;}
.top ul li .sub_menu {position:absolute; top:50px; height:30px; overflow:hidden; background:url(../img/bgs.gif) 0 -533px no-repeat; left:150px; display:none;}
.top ul li .over_color {display:none; }
.top ul li.active .over_color {display:block;} 
.top ul li .sub_menu ul {margin:0; margin-left:5px; white-space:nowrap; padding-right:5px; background:url(../img/bgs.gif) 100% -602px no-repeat; height:30px;}
.top ul li .sub_menu ul li {display:block; float:left; list-style-type:none; padding:7px 11px 4px 11px; background:url(../img/bgs.gif) 0 -669px repeat-x; //padding:5px 11px 4px 11px; height:19px;}
.top ul li .sub_menu ul li.separator {border-right:1px solid #bebebe;}
.top ul li .sub_menu ul li a {padding:0; height:auto; white-space:nowrap; color:#005fc1; cursor:pointer; background:none;}
.top ul li .sub_menu ul li.active a {color:black !important;}
.top ul li .sub_menu ul li a:hover {color:#55cc00 !important; text-decoration:none;}
.top ul li .sub_menu ul li.active a:hover {color:black !important;}
.top ul li .sub_menu ul li a span {font-size:0.8em; padding:0; color:#666666; display:inline; float:none; cursor:pointer; background:none;}
.top ul li .sub_menu ul li span {font-size:1.4em; margin-right:5px; //position:relative; //top:-2px;}
.top ul li .sub_menu ul li select {font-size:1.2em; padding:0; position:relative; top:-1px;}
.top ul li .sub_menu ul li select option {padding:0 7px;}
.top ul li.active .sub_menu {display:block; overflow:hidden;}
.top .settings {float:right;}
.top .settings li a {font-size:1.2em; padding-top:22px;}
.top .settings li.active a {font-size:1.2em; padding-top:22px;}
.top .settings li .sub_menu {left:auto; margin-left:-18px;}
.top .settings li .sub_menu a {padding:0; font-size:1.4em;}


.over_color {height:3px; background:url(../img/bgs.gif) 0 -743px no-repeat #f7f7f7; float:left; width:100%; position:absolute; top:50px;}
.over_color div {height:3px; float:right; width:8px; background:url(../img/bgs.gif) -3px -767px no-repeat;}

.main h1 {font-weight:normal; font-size:1.8em; margin-bottom:10px; display:block; float:left; overflow:hidden;}

.table_layout { margin-bottom:10px;}
.table_layout .top {height:5px; background:url(../img/bgs.gif) 0 -790px no-repeat #e9f0f7;}
.table_layout .top div {height:5px; background:url(../img/bgs.gif) 100% -809px no-repeat;}
.table_layout .in {background:#e9f0f7; padding:5px 10px;}
.table_layout .bot {height:5px; background:url(../img/bgs.gif) 0 -850px no-repeat #e9f0f7;}
.table_layout .bot div {height:5px; background:url(../img/bgs.gif) 100% -829px no-repeat;}
.table_layout h2 {float:left;}

.filter {display:block; float:left; margin:0 35px 7px 0;}
.filter li {list-style-type:none; display:inline; }
.filter li {font-size:1.2em; padding:0 5px 0 0; background:url(../img/bgs.gif) 100% -868px no-repeat;}
.filter li:first-child, .filter li.last {background:none; padding:0;}
.filter li.active a {color:black; font-weight:bold;}

.table_itseft {}
.table_itseft .top, .table_itseft .bot {height:5px; background:url(../img/bgs.gif) 0 -939px repeat-x;}
.table_itseft .top .left, .table_itseft .bot .left {height:5px; width:5px; float:left; background:url(../img/bgs.gif) 0 -894px no-repeat;}
.table_itseft .top .right, .table_itseft .bot .right {height:5px; width:5px; float:right; background:url(../img/bgs.gif) 100% -919px no-repeat;}
.table_itseft .in {background:white; border:1px solid #99cccc; border-top:none; border-bottom:none; padding:0;}
.table_itseft .bot {background:url(../img/bgs.gif) 0 -1000px repeat-x;}
.table_itseft .bot .left {background:url(../img/bgs.gif) 0 -960px no-repeat;}
.table_itseft .bot .right {background:url(../img/bgs.gif) 100% -979px no-repeat;}
.table_itseft .in table {border-collapse:collapse;}
.table_itseft .in table td, .table_itseft .in table th {text-align:left; font-size:1.2em; padding:5px 10px 5px 5px; white-space:nowrap;}
.table_itseft .in table td {padding-right:25px;}
.table_itseft .in table th a {color:black; padding-right:15px;}
.table_itseft .in table th a:hover {padding-right:15px; background:url(../img/bgs.gif) 100% -1015px no-repeat;}
.table_itseft .in table th {background:white;}
.table_itseft .in table th a.up {padding-right:15px; background:url(../img/bgs.gif) 100% -1040px no-repeat;}
.table_itseft .in table th a.down {padding-right:15px; background:url(../img/bgs.gif) 100% -1015px no-repeat;}
.table_itseft .in table tr.even td {background:#f7f7f7; //background:f7f7f7;}
.table_itseft .in table tr.odd td {background:white;} 
.table_itseft .in table tr:hover td, .table_itseft .in table tr.hover_it td  {background:#e1eefa;}
.table_right {float:left; overflow-x:scroll;}
.table_right tbody td {cursor:default; text-align:right !important;}

.generated {font-size:1.2em; color:#666666; margin-bottom:1em;}
h2 {margin-top:-4px; height:20px; display:block;}
h2 a {font-size:1.1em; color:black; padding-left:17px; background:url(../img/bgs.gif) 0 -1073px no-repeat; //font-size:0.7em;}
h2 a:hover {color:black;}
h2 a.hidden {background:url("../img/bgs.gif") 0 -1103px no-repeat;}
.filter_2 {margin-top:8px;}
.filter_2 ul {margin-bottom:0;}
.help {float:right; font-size:1.2em; margin-top:-2px;}

.breadcrumbs {display:block; margin-bottom:18px; overflow:hidden;}
.breadcrumbs li {display:block; float:left; font-size:1.2em; list-style-type:none; color:#666666; padding-right:15px; background:url("../img/bgs.gif") 100% -1135px no-repeat; margin-right:10px;}
.breadcrumbs li.home_br {padding-right:3px; margin-right:0; background:none;}
.breadcrumbs li.last {padding-right:0; margin-right:0; background:none;}
.breadcrumbs li a span {font-size:0.8em; color:#666666;}
.inspect {float:left; display:block; margin:6px 0 0 15px; font-size:1.2em;}

.additional {float:left; margin-bottom:10px;}
.additional .top {height:5px; background:url("../img/bgs.gif") 0 -1160px no-repeat #f7f7f7;}
.additional .top div {height:5px; background:url("../img/bgs.gif") 100% -1180px no-repeat;}
.additional .add_in {padding:5px 10px; background:#f7f7f7;}
.additional .add_in span {font-size:1.2em;}
.additional .add_in a {font-size:1em;} 
.additional .bot {height:5px; background:url("../img/bgs.gif") 0 -1196px no-repeat #f7f7f7;}
.additional .bot div {height:5px; background:url("../img/bgs.gif") 100% -1212px no-repeat;}


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

.call_list {background:url("../img/ul_bg.gif") 0 0 repeat;}
.call_error {background:#ffcccc;}
.black_bg {background:black; opacity:0.1; width:100%; height:100%; position:fixed; top:0; left:0; z-index:4;}
.box {float:left; position:absolute; z-index:6; min-width:600px;}
.box_top {height:22px; background:url("../img/box_top_l.png") 0 0 no-repeat; float:left; width:100%; position:relative;}
.box_top div {height:22px; background:url("../img/box_top_r.png") 100% 0 no-repeat; float:right; width:30px;}
.box_top span {display:block; height:22px; background:url("../img/box_top_m.png") 0 0 repeat-x; margin:0 30px;}

.box_in {clear:both; overflow:hidden; background:url("../img/box_in_l.png") 0 0 repeat-y;}
.box_in .right {background:url("../img/box_in_r.png") 100% 0 repeat-y;}
.text_here {margin:0 30px; clear:both; overflow:hidden; font-size:1.2em; background:white; padding:10px 10px 5px 10px; line-height:2.1em;}

.box_bot {height:22px; background:url("../img/box_bot_l.png") 0 0 no-repeat; float:left; width:100%; overflow:hidden; position:relative;}
.box_bot div {height:22px; background:url("../img/box_bot_r.png") 100% 0 no-repeat; float:right; width:30px;}
.box_bot span {display:block; height:22px; background:url("../img/box_bot_m.png") 0 0 repeat-x; margin:0 30px;}
.close_box {height:30px; width:30px; background:url("../img/box_close.png") 0 0 no-repeat; top:-25px; left:7px; display:block; position:relative;}
.wrap_it td {white-space:normal !important;}
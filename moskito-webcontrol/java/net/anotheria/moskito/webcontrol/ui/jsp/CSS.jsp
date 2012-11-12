<%@ page language="java" contentType="text/css;charset=UTF-8" session="false" %>
* {margin:0; padding:0; outline:none;}
body {background:white; font-family:Arial, Tahoma, sans-serif; color:black; font-size:14px;}
img {border:0;}
a {color:#006699; text-decoration:none; cursor:pointer;}
a:hover {text-decoration:underline;}
.clear {height:0; clear:both; float:none;}
.main {padding:10px 12px; min-width:960px;}

.header {border-bottom:1px solid #8eceeb; padding:0 0 12px 0; margin:0 8px;}

.settings {float:right; display:block; padding-left:20px; background:url("../images/settings.gif") 0 0 no-repeat; margin:13px 0 0 0;}
.interval {padding-top:12px; margin-left:8px;}
.interval li {display:block; float:left; margin-right:7px; list-style-type:none;}
.interval li a {padding-right:9px; background:url("../images/sep.gif") 100% 8px no-repeat; }
.interval li.last a {background:none;}
.interval li.active a {color:black; font-weight:bold;}
.interval li.active a:hover {text-decoration:none;}
.area {}
.area .top {height:16px; background:url("../images/borders.gif") 100% -16px no-repeat; padding-right:16px;}
.area .top div {height:16px; background:url("../images/borders.gif") 0 0 no-repeat;}
.area .in {background:url("../images/gr_l.gif") 100% 0 repeat-y; padding-right:8px;}
.area .in .in_r {background:url("../images/gr_r.gif") 0 0 repeat-y #e2f6ff; padding-left:8px;}
.area .bot {height:16px; background:url("../images/borders.gif") 100% -32px no-repeat; padding-right:16px; margin-top:-8px; position:relative; z-index:3;}
.area .bot div {height:16px; background:url("../images/borders.gif") 0 -48px no-repeat;}

.views li {list-style-type:none; line-height:30px; display:block; min-width:120px; overflow:hidden; max-width:280px; height:30px;}
.views li a {padding:0 10px;}
.views li.active {background:url("../images/li_active.gif") 0 0 no-repeat; position:relative; z-index:6;}
.views li.active a {color:black;}
.views li.active a:hover {text-decoration:none;}
.main_table {margin-top:-8px; }
.main_table td {vertical-align:top;}
.main_table th {text-align:left;}

.views_td {width:145px;}

.inner {margin-left:-1px;}
.inner .top {height:5px; background:url("../images/borders_in.gif") 100% -5px no-repeat; padding-right:5px;}
.inner .top div {height:5px; background:url("../images/borders_in.gif") 0 0 no-repeat;}
.inner .in {border:1px solid #8eceeb; border-bottom:none; border-top:none; background:white; padding:3px 10px; min-height:200px; position:relative; z-index:5; }
.inner .bot {height:5px; background:url("../images/borders_in.gif") 100% -10px no-repeat; padding-right:5px; position:relative; z-index:5; margin:0;}
.inner .bot div {height:5px; background:url("../images/borders_in.gif") 0 -15px no-repeat;}

.inner .in table {}
.inner .in table th {border-bottom:2px solid #bdbdbd; padding:8px 10px;}
.inner .in table th a {color:black; font-size:12px; padding-right:12px; //display:block; //overflow:hidden;}
.inner .in table th a:hover {text-decoration:none; background:url("../images/down.gif") 100% 5px no-repeat;}
.inner .in table th a.down {background:url("../images/down.gif") 100% 5px no-repeat;}
.inner .in table th a.up {background:url("../images/down.gif") 100% -31px no-repeat;}

.inner .in table tbody td {padding:4px 10px; border-bottom:1px solid #e8e8e8;}
.inner .in table tbody td.red {color:red;}
.inner .in table tbody tr.red td {background:#ffe4e4 !important; border-bottom:1px solid #ff7e7e;}
.inner .in table tbody tr:hover td {background:#f4f4f4;}

h2 {color:#470075; font-size:18px; font-weight:normal; margin:20px 0 0;}

.scroll {width:818px; overflow-x:scroll; padding-bottom:10px;}

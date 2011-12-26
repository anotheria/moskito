<%@ page language="java" contentType="text/html;charset=UTF-8"
	session="true"%><%@ taglib uri="http://www.anotheria.net/ano-tags"
	prefix="ano"%><%@page isELIgnored="false"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Moskito-Contorol Monitor: Details view</title>
<link href="styles/common.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<script type="text/javascript" src="js/wz_tooltip.js"></script>

	<div class="header">
	<div class="logo"><img src="images/d48.png" alt=""/>&nbsp;WebAppTemplate</div>
		<ul>
			<li><a href="allcomponents.html">All monitored components</a>
			</li>
			<li class="active"><a href="details.html">Details</a>
			</li>
			<li><a href="views.html">Additional views</a>
			</li>
		</ul>
	</div>
	<div class="main">
		<div class="widgetsHolder">
		
		<ano:iterate id="groupInClaster" name="cluster">
			<div class="box">
				<strong class="ml_9">
					<ano:write name="groupInClaster" property="name"/>
				</strong>
				<div class="clear"></div>		
				<ano:iterate id="e" name="groupInClaster" property="instance">
				<ul class="instanceList">
					<li>
						<ano:write name="e" property="name"/>
					</li>
				</ul>
				</ano:iterate>
			</div>
		</ano:iterate>						

		</div>

		<div class="clear"></div>
		<div class="line"></div>
	</div>
</body>
</html>
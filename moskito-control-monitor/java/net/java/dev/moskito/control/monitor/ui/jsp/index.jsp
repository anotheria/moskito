<%@ page language="java" contentType="text/html;charset=UTF-8"
	session="true"%><%@ taglib uri="http://www.anotheria.net/ano-tags"
	prefix="ano"%><%@page isELIgnored="false"%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Anotheria WebAppTemplate</title>
	<link href="styles/common.css" type="text/css" rel="stylesheet"/>
</head>
<body>
<div class="header">
	<div class="logo"><img src="images/d48.png" alt=""/>&nbsp;Moskito-Control-Monitor</div>
	<ul>
		<li class="active"><a href="allcomponents.html">All monitored components</a></li>
		<li><a href="details.html">Details</a></li>
		<li><a href="views.html">Additional views</a></li>
	</ul>
</div>
<div class="content">
	<h1>Tab #1</h1>
	<div class="main">
		<div class="widgetsHolder">
		
		<ano:iterate id="group" name="groups">
			<div class="box">
				<strong class="ml_9">
					<ano:write name="group" property="name"/>
				</strong>
				
				<div class="clear"></div>
						
				<ano:iterate id="e" name="group" property="monitoredInstancesList">
				<ul class="instanceList">
					<li>
						<ano:write name="e" property="instanceName"/>
					</li>
				</ul>
				</ano:iterate>
			</div>
		</ano:iterate>						

		</div>

		<div class="clear"></div>
		<div class="line"></div>
	</div>	
	<a href="http://anotheria.net"><img src="images/powered.png" alt=""/></a>
</div>
</body>
</html>
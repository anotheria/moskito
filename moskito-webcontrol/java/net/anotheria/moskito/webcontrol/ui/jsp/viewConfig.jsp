<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title>MoSKito &mdash; WebControl : Configs</title>		
		<link rel="stylesheet" href="mwcCSS"/>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/function.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath()%>/js/config.js"></script>
		
		<script type="text/javascript">

			/****************************************/
			/****** Application *********************/
			/****************************************/
			var views = [];
			var availableColumns = [];
			var viewsCounter = 0;

			<ano-tags:iterate id="col" name="availableColumns">
			availableColumns[availableColumns.length] = new ViewField("${col.fieldName}", "${col.attributeName}", "${col.type.type}", "${col.javaType}", ${col.visible}, "${col.path}", "${col.total}", "${col.guard.class.name}", "${col.format}");
			</ano-tags:iterate>
			<%--
				
			<c:forEach items="${availableColumns}" var="col" varStatus="stat">
			
			</c:forEach>
			--%>
			
			<%--<c:forEach items="${views}" var="view" varStatus="stat">--%>
			<ano-tags:iterate id="view" name="views">
			{
				var view = new ViewConfiguration("${view.name}");
				<ano-tags:iterate name="view" property="fields" id="col">
					view.addField(
						new ViewField("${col.fieldName}", "${col.attributeName}", "${col.type.type}", "${col.javaType}", ${col.visible}, "${col.path}", "${col.total}", "${col.guard.class.name}", "${col.format}")
					);
				</ano-tags:iterate>
				views[views.length] = view;
			}
			</ano-tags:iterate>
			<%-- </c:forEach> --%>

			var currentView = "";
			ViewConfiguration.prototype.printOnPage = function(boxObj) {
				var li = document.createElement("li");
				if (currentView == this.name.value) {
					li.className = "active";
				} else {
					li.className = "";
				}
				
				var a = document.createElement("a");
				a.appendChild(document.createTextNode(this.name.value));
				a.setAttribute("href", "javascript:currentView='" + this.name.value + "';viewAllViews();editView('" + this.name.value + "')");
				li.appendChild(a);
				boxObj.appendChild(li);
			}

			</script>
			<style type="text/css">
				* {
					padding: 0px;
					margin: 0px;
					font-family: sans-serif;
					font-size: 12pt;
				}
				#page {
					/*
					width: 500px;
					*/
				}
				#add_view {
					/*
					width: 500px;
					*/
					border: 1px dotted green;
					cursor: pointer;
				}
				#current_config {
				}
				
				.fieldName {
					border: 1px solid black;
				}
				.attributeName {
					border: 1px solid black;
				}
				.type {
					border: 1px solid black;
				}
				.javaType {
					border: 1px solid black;
				}
				.visible {
					border: 1px solid black;
				}
				.path {
					border: 1px solid black;
				}
				.total {
					border: 1px solid black;
				}
				.guard {
					border: 1px solid black;
				}
				.format {
					border: 1px solid black;
				}
				.viewname {
					border: 1px solid black;
				}
				
				
				.delete {
					float: right;
					display: block;
				}
				
				.add_field {
					float: right;
					display: block;
				}
				
				.edit {
					cursor: hand;
				}
				
				.view {
					border: 0px solid black;
				}
				
				.label {
					float: left;
				}
				
				.field_value {
					display: block;
				}
				
				.view_name {
					border: 0px solid gray;
					display: block;
				}
				
				.fields_length {
					border: 0px solid gray;
					display: block;
				}
				#winId {
					/*
					width: 100%;
					*/
				}
				#props {
					display: none;
					border: 2px solid blue;
					float: left;
					
					position: absolute;
					top: 50px;
					left: 200px;
					background-color: white;
					
				}
				#props .view {
					height: 100%;
					border: 0px solid red;
				}
				
				#fields {
					position: absolute;
					z-index: 200;
					top: 100px;
					left: 810px;					
					background-color: white;
					display: none;
				}
				#available_fields {
					overflow: auto;
					width: 600px;
					height: 600px;
				}
				#generate {
					width: 500px;
					border: 1px dotted green;
					cursor: pointer;
				}
				#output {
					width: 800px;
					border: 2px dotted red;
					display: none;
				}
				#hidden_form {
					display: none;
				}
			</style>
	</head>
	<body onload="viewAllViews();">
	
		<div class="main">
			<div class="header">
				<img src="<%= request.getContextPath() %>/images/logo.gif" alt="WebControl logo"/>
				<a href="viewConfig" class="settings">Settings</a>
			</div>
			<div class="clear"></div>
			<div id="add_view" onclick="addView(getById('current_config'))">Add View</div>
			<div id="generate" onclick="generate()">Generate and save</div>
			<div class="clear"></div>
			<div class="area">
				<div class="top">
					<div></div>
				</div>
				<div class="in">
					<div class="in_r">
						<table cellpadding="0" cellspacing="0" border="0" width="100%" class="main_table">
							<tr>
								<td class="views_td">
									<ul class="views" id="current_config">
									</ul>
								</td>
								<td>
									<div class="inner">
										<div class="top">
											<div>&nbsp;</div>
										</div>
										<div class="in">
											<div class="scroll">
												<table cellpadding="0" cellspacing="0" border="0" width="100%">
													<tr>
														<td>
															<div id="winId"></div>
														</td>
													</tr>
												</table>
											</div>
										</div>
										<div class="bot">
											<div></div>
										</div>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="bot">
					<div>&nbsp;</div>
				</div>
			</div>
		</div>
		<div id="fields">
			<div>
				&nbsp;
				<div style="float: left; display: block; cursor: pointer;" onclick="addFields()">Add</div>
				<div style="float: right; display: block; cursor: pointer;" onclick="hide($('fields'))">Close</div>
			</div>
			<div id="available_fields">
			</div>
		</div>
	

		<!-- 
		<div id="page">
			<div id="current_config">
			</div>
		</div>
		
		<div id="props">
			<div style="text-align: right; background-color: green; cursor: hand;" onclick="hide($('props'))">Close</div>
			<div id="winId">
			</div>
		</div>
		<div id="fields">
			<div>
				&nbsp;
				<div style="float: left; display: block; cursor: pointer;" onclick="addFields()">Add</div>
				<div style="float: right; display: block; cursor: pointer;" onclick="hide($('fields'))">Close</div>
			</div>
			<div id="available_fields">
			</div>
		</div>
		-->
		
		
		<br />
		<pre id="output"></pre>
		<div id="hidden_form">
			<form id="saving_form" action="viewConfig?action=save" method="post">
				<input type="text" id="json" name="json" />
			</form>
		</div>
		
	</body>
</html>

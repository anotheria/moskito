<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="ano-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>MoSKito &mdash; WebControl</title>
	<link type="text/css" rel="stylesheet" rev="stylesheet" href="../v2/styles/common.css"/>
	<script type="text/javascript" src="../v2/js/jquery-1.4.min.js"></script>
	<script type="text/javascript" src="../v2/js/webControlController.js"></script>
	<script type="text/javascript" src="../v2/js/jquery.ui.core.js"></script>

	<script type="text/javascript" src="../v2/js/jquery-ui-1.8.5.custom.min.js"></script>
	<script type="text/javascript" src="../v2/js/jquery.ui.mouse.min.js"></script>
	<script type="text/javascript" src="../v2/js/jquery.ui.selectable.min.js"></script>
	<script type="text/javascript" src="../v2/js/jquery.ui.sortable.min.js"></script>
	<script type="text/javascript" src="../v2/js/jquery.ui.widget.min.js"></script>
	<script type="text/javascript" src="../v2/js/dragtable.js"></script>
	

</head>
<body>
<div class="main">
	<div class="header">
		<img src="../v2/images/logo.gif" alt="WebControl logo"/>
		<a class="button exit" href="mwcShowView2"><span>Exit from settings</span></a>
	</div>
	<h2 class="h2">Settings</h2>
	<p class="description">You can drag'n'drop rows. The higher row will be the left one.</p>

	<div class="clear"></div>
	<div class="area">
		<div class="top">
			<div></div>
		</div>
		<div class="in">
			<div class="in_r">
			<form name="addViews" method="post" action="viewConfig2?currentViewConfiguration=<ano-tags:write name="currentViewConfiguration" property="name"/>">
				<table cellpadding="0" cellspacing="0" border="0" width="100%" class="main_table">
					<tr>

						<td class="views_td">
							<ul class="views">
								<li><a href="#" class="button add"><span><img src="../v2/images/add_plus.gif" alt=""/>&nbsp;&nbsp;Add view</span></a><img src="images/spinner.gif" class="spinner" alt="Loading..."/></li>
								<ano-tags:iterate id="view" name="views">
									<li <ano-tags:equal name="view" property="name" value="${currentViewConfiguration.name}">class="active"</ano-tags:equal>><a href="viewConfig2?currentViewConfiguration=${view.name}">${view.name}</a><a href="#" class="delete"></a><input name="view" value="${view.name}" type="text"/><a href="#" class="edit"></a><a href="#" class="ok"></a><a href="#" class="rename"></a></li>
								</ano-tags:iterate>
							</ul>
						</td>

						<td>
							<div class="inner">
								<div class="top">
									<div></div>
								</div>
								<div class="in">
									<div class="scroll">
										<a href="#" onclick="lightbox();" class="button add"><span><img src="../v2/images/add_plus.gif" alt=""/>&nbsp;&nbsp;Add row</span></a>

										<a href="#" onclick="document.addViews.submit();" class="button save"><span>Save</span></a>
										<input type="hidden" name="category" value="${currentViewConfiguration}"/>
										<table id="settingsTable" cellpadding="0" cellspacing="0" border="0" width="100%">
											<thead>
											<tr>
												<th><img src="../v2/images/visible.gif" alt=""/></th>
												<th>Field name</th>
												<th>Display name</th>

												<th>Java type</th>

												<th>Formula</th>
												<th>Guard</th>
												<th>Format</th>
												<th></th>
													
											</tr>

											</thead>
											<tbody>
													<ano-tags:iterate name="currentViewConfiguration" property="fields" id="col">
														<tr>
															<td>
																<input type="checkbox" <ano-tags:equal name="col" property="visible" value="true">checked="checked"</ano-tags:equal>/>
																<input type="hidden" name="visible" value="<ano-tags:equal name="col" property="visible" value="true">true</ano-tags:equal><ano-tags:equal name="col" property="visible" value="false">false</ano-tags:equal>"/>
															</td>
															<td><span>${col.attributeName}</span><input type="hidden" name="attributeName" value="${col.attributeName}"/></td>
															<td><input type="text" name="fieldName" value="${col.fieldName}"/></td>
			
															<td>
																<select name="javaType">
																	<option <ano-tags:equal name="col" property="javaType" value="int">selected</ano-tags:equal>>int</option>
																	<option <ano-tags:equal name="col" property="javaType" value="float">selected</ano-tags:equal>>float</option>
																	<option <ano-tags:equal name="col" property="javaType" value="byte">selected</ano-tags:equal>>byte</option>
																</select>
															</td>
			
															</td>
															<td><select name="total">
																<option <ano-tags:equal name="col" property="total" value="SUM">selected</ano-tags:equal>>sum</option>
																<option <ano-tags:equal name="col" property="total" value="AVG">selected</ano-tags:equal>>avg</option>
																<option <ano-tags:equal name="col" property="total" value="MIN">selected</ano-tags:equal>>min</option>
																<option <ano-tags:equal name="col" property="total" value="MAX">selected</ano-tags:equal>>max</option>
			
															</select></td>
															<td>
																<select><option>TrafficLightsGuard</option></select>
																<input type="hidden" name="guard" value="${col.guard}"/>
																<input type="hidden" name="path" class="path" value="${col.path}"/>
																<input type="hidden" name="type" value="${col.type}"/>
																<input type="hidden" class="category" value="${col.category}"/>
															</td>
															<td><span><input type="text" name="format" value="${col.format}" class="format"/></span></td>
															<td><a href="#"><img src="../v2/images/delete.gif" alt=""/></a></td>
														</tr>
													</ano-tags:iterate>
											</tbody>
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
			</form>
			</div>
		</div>

		<div class="bot">
			<div></div>
		</div>
	</div>
</div>
<div class="lightbox" style="display:none;">
	<div class="black_bg"></div>
	<div class="box">
		<div class="box_top">
			<div></div>

			<span></span>
			<a class="close_box"></a>

			<div class="clear"></div>
		</div>
		<div class="box_in">
			<div class="right">
				<div class="text_here">
					
					<div class="category">

						<ul class="tabs">
							<ano-tags:iterate id="category" name="allCategories" indexId="index">
								<li <ano-tags:equal name="index" value="0">class="active"</ano-tags:equal> class="" id="${category}"><a href="#"><span>${category}</span></a></li>
							</ano-tags:iterate>
						</ul>
						<!--<input type="radio" id="cache" name="category" checked="checked"/><label for="cache">Cache</label>-->
						<!--<input type="radio" id="filter" name="category"/><label for="filter">Filter</label>-->
						<!--<input type="radio" id="memory" name="category"/><label for="memory">Memory pool</label>-->
						<!--<input type="radio" id="service" name="category"/><label for="service">Service</label>-->
						<!--<input type="radio" id="storage" name="category"/><label for="storage">Storage</label>-->
						<!--<input type="radio" id="vmemory" name="category"/><label for="vmemory">Virtual memory</label>-->

					</div>
					<ano-tags:iterate id="category" name="allCategories" indexId="index">
						<ul class="${category}" <ano-tags:notEqual name="index" value="0">style="display:none;"</ano-tags:notEqual>>
						<ano-tags:iterate id="column" name="availableColumns">
							<ano-tags:equal name="column" property="category" value="${category}">
								<li>
								<input class="fieldName" type="checkbox" id="${column.fieldName}" value="${column.fieldName}" name="${column.fieldName}"/><label for="${column.fieldName}">${column.fieldName}</label>
								<input class="attributeName" type="hidden" value="${column.attributeName}" name="${column.attributeName}"/>
								<input class="type" type="hidden" value="${column.type}" name="${column.type}"/>
								<input class="javaType" type="hidden" value="${column.javaType}" name="${column.javaType}"/>
								<input class="path" type="hidden" value="${column.path}" name="${column.path}"/>
								<input class="formula" type="hidden" value="${column.total}" name="${column.total}"/>
								<input class="guard" type="hidden" value="${column.guard}" name="${column.guard}"/>
								<input class="format" type="hidden" value="${column.format}" name="${column.format}"/>
								</li>
							</ano-tags:equal>
						</ano-tags:iterate>
						</ul>
					</ano-tags:iterate>
					<div class="overlay_buttons"><a href="#" onclick="addRow(); return false;" class="button" id="ok_button"><span><img alt="" src="images/add_plus.gif">&nbsp;&nbsp;Add</span></a><a href="#" class="button" id="cancel_button"><span>Cancel</span></a></div>
					
				</div>

			</div>
		</div>

		<div class="box_bot">
			<div></div>
			<span></span>
		</div>
	</div>
</div>

</body>
</html>
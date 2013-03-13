<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MoSKito :: More</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="../../shared/jsp/Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="add_in">
			<h2>Additional items</h2>

			<div><span>
This menu item and the submenu items contain different additional information and functions which aren't important enough to get its own top menu point.       <br/>
<i>Warning:</i> many of the functions in this menu are not yet stable and should be used at own risk.

			</span></div>

		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>

	<msk:iterate name="decorators" type="net.anotheria.moskito.webui.shared.bean.DecoratorExplanationBean" id="decorator">
		<div class="clear"><!-- --></div>
		<div class="table_layout">
			<div class="top">
				<div><!-- --></div>
			</div>
			<div class="in">

				<h2><a name="<msk:write name="decorator" property="name"/>"><msk:write name="decorator"
																					   property="name"/></a></h2>

				<div class="clear"><!-- --></div>
				<div class="table_itseft">
					<div class="top">
						<div class="left"><!-- --></div>
						<div class="right"><!-- --></div>
					</div>
					<div class="in">

						<table cellpadding="0" cellspacing="0" class="wrap_it"
							   id="<msk:write name="decorator" property="name"/>_table">
							<thead>
							<tr class="stat_header">
								<th>Abbreviation</th>
								<th>Meaning</th>
								<th>Explanation</th>
							</tr>
							</thead>
							<tbody>
							<msk:iterate name="decorator" property="captions" id="caption"
										 type="net.anotheria.moskito.webui.shared.bean.StatCaptionBean" indexId="index">
								<tr class="<%= ((index & 1) == 0 )? "even" : "odd" %>">
									<td><msk:write name="caption" property="caption"/></td>
									<td><msk:write name="caption" property="shortExplanation"/></td>
									<td><msk:write name="caption" property="explanation"/></td>
								</tr>
							</msk:iterate>
							</tbody>
						</table>
					</div>
					<div class="bot">
						<div class="left"><!-- --></div>
						<div class="right"><!-- --></div>
					</div>
					<div class="clear"><!-- --></div>
				</div>

			</div>
			<div class="bot">
			<div><!-- --></div>
		</div>
		</div>



	</msk:iterate>
</div>
</body>
</html>
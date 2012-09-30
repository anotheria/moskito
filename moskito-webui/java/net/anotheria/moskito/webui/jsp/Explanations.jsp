<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Moskito stats explained</title>
	<link rel="stylesheet" href="mskCSS"/>
</head>
<body>

<script type="text/javascript" src="../js/wz_tooltip.js"></script>
<script type="text/javascript" src="../js/jquery-1.4.min.js"></script>
<script type="text/javascript" src="../js/function.js"></script>

<jsp:include page="Menu.jsp" flush="false"/>

<div class="main">
	<div class="additional">
		<div class="top">
			<div><!-- --></div>
		</div>
		<div class="add_in">
			<h2>Explanations for moskito stats page</h2>

			<div><span>
This page explains what the abreviations used on the producers overview page mean. However, it doesn't explain how to interpret
the data. Since the data is strongly use-case dependent, its mostly up to you to give it a correct interpretations, but 
for some use-cases <a href="http://moskito.anotheria.net/documentation.html">moskito's documentation and HOWTOs</a> are quite useful.
			</span></div>

		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>

	<msk:iterate name="decorators" type="net.java.dev.moskito.webui.bean.DecoratorExplanationBean" id="decorator">
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
										 type="net.java.dev.moskito.webui.bean.StatCaptionBean" indexId="index">
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
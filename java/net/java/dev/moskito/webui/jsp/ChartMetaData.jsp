<%@ page language="java" contentType="text/html;charset=UTF-8" session="true" %>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>ChartMetaData</title>
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
			<h2>MoSKito metadata for instant charts.</h2>

			<div><span>
			This page demonstrates how to get the metadata from moskito webui which is needed to construct chat requests. 
			</span></div>

		</div>
		<div class="bot">
			<div><!-- --></div>
		</div>
	</div>

		<div class="clear"><!-- --></div>
		<div class="table_layout">
			<div class="top">
				<div><!-- --></div>
			</div>
			<div class="bot">
			<div><!-- --></div>

				<br><br><br>
				<h2>Your data in xml format (add pForward=xml to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartMetaDataXML.jsp" flush="false"/>
				</textarea>
				
				<br><br><br>
				<h2>Your data in json format (add pForward=json to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartMetaDataJSON.jsp" flush="false"/>
				</textarea>

				<br><br><br>
				<h2>Your data in csv format (add pForward=csv to the current link)</h2>
				<br><br>
				<textarea rows="20" cols="80">
				<jsp:include page="ChartMetaDataCSV.jsp" flush="false"/>
				</textarea>
		</div>
		</div>



</div>
</body>
</html>
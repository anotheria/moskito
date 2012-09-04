<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ page isELIgnored ="false" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Moskito Web Control - Dashboard</title>
	<link rel="stylesheet" type="text/css" href="css/redmond/jquery-ui-1.8.18.custom.css" />
	<link rel="stylesheet" type="text/css" href="css/dashboard.css" />
</head>
<body>
<div id="dashboard-container">
	<jsp:include page="/net/anotheria/moskito/webcontrol/ui/shared/jsp/Header.jsp" />
	
	<div id="dashboard-content">
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">First</a></li>
				<li><a href="#tabs-2">Second</a></li>
				<li><a href="#tabs-3">Third</a></li>
			</ul>
			<div id="tabs-1">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</div>
			<div id="tabs-2">Phasellus mattis tincidunt nibh. Cras orci urna, blandit id, pretium vel, aliquet ornare, felis. Maecenas scelerisque sem non nisl. Fusce sed lorem in enim dictum bibendum.</div>
			<div id="tabs-3">Nam dui erat, auctor a, dignissim quis, sollicitudin eu, felis. Pellentesque nisi urna, interdum eget, sagittis et, consequat vestibulum, lacus. Mauris porttitor ullamcorper augue.</div>
		</div>
	</div>
	
	<jsp:include page="/net/anotheria/moskito/webcontrol/ui/shared/jsp/Footer.jsp" />
</div>
<script type="text/javascript" src="js/jquery-1.7.1.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
<script type="text/javascript">
	$(function(){
		$("#tabs").tabs();
	});
</script>
</body>
</html>
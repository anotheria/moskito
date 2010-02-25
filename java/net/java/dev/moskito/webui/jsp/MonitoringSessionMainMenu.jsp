<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<%@ taglib uri="http://www.anotheria.net/ano-tags" prefix="msk"%>

<div class="main_menu">
	<div class="white_pl">
		<div class="top">
			<div class="left_bg"><!-- --></div>
				<a href="#" class="logo">
					<img alt="MoSKito WebUI" src="<msk:write name="mskPathToImages" scope="application"/>moskito_webui_logo.gif" width="131"	height="25"/>
				</a>
				<ul>
					<li><a href="<msk:write name="mskShowAllProducers"/>">Producers</a></li>					
					<li><a href="<msk:write name="mskShowUseCases"/>">Use Cases</a></li>
					<li class="active"><a href="<msk:write name="mskShowMonitoringSessions"/>">Monitoring Sessions</a></li>
				</ul>
				<div class="right"></div>
				<jsp:include page="ExportMenu.jsp" flush="false" />
		</div>
	</div>
</div>